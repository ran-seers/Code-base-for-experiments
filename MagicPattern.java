import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class MagicPattern extends JPanel implements ActionListener {
    private float t = 0;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private final Timer timer;

    public MagicPattern() {
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(9, 9, 9));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set translucent stroke color
        g2d.setColor(new Color(144, 144, 144, 96));

        for (int i = 0; i < 10000; i++) {
            drawPoint(g2d, i, i / 235.0f);
        }
    }

    private void drawPoint(Graphics2D g, float x, float y) {
        float k = (4 + (float)Math.sin(y * 2 - t) * 3) * (float)Math.cos(x / 29.0f);
        float e = y / 8.0f - 13;
        float d = (float)Math.sqrt(k * k + e * e);
        float c = d - t;

        // Avoid division by zero
        float kSafe = Math.abs(k) < 0.001f ? 0.001f : k;
        float q = 3 * (float)Math.sin(k * 2) + 0.3f / kSafe +
                (float)Math.sin(y / 25.0f) * k * (9 + 4 * (float)Math.sin(e * 9 - d * 3 + t * 2));

        float px = q * 30 * (float)Math.cos(c) + 200;
        float py = q * (float)Math.sin(c) + d * 39 - 220;

        // Draw point as small circle
        g.fill(new Ellipse2D.Float(px, py, 1.5f, 1.5f));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t += Math.PI / 240;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Magic Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MagicPattern());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}