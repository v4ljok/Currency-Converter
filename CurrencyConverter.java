import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class CurrencyConverter extends JFrame implements ActionListener {
    private JLabel label1, label2;
    private JTextField textField1, textField2;
    private JComboBox<String> comboBox1, comboBox2;
    private JButton button;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        label1 = new JLabel("Amount:");
        add(label1);

        textField1 = new JTextField();
        add(textField1);

        label2 = new JLabel("Converted Amount:");
        add(label2);

        textField2 = new JTextField();
        add(textField2);

        String[] currencies = {"USD", "EUR", "RUB"};
        comboBox1 = new JComboBox<>(currencies);
        add(comboBox1);

        comboBox2 = new JComboBox<>(currencies);
        add(comboBox2);

        button = new JButton("Convert");
        button.addActionListener(this);
        add(button);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String fromCurrency = (String) comboBox1.getSelectedItem();
            String toCurrency = (String) comboBox2.getSelectedItem();
            double amount = Double.parseDouble(textField1.getText());
            URL url = new URL("https://v6.exchangerate-api.com/v6/3203eb2ebeb4a76b1091899d/latest/" + fromCurrency);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            int startRateIndex = content.indexOf(toCurrency) + 5;
            int endRateIndex = content.indexOf(",", startRateIndex);
            double rate = Double.parseDouble(content.substring(startRateIndex, endRateIndex));
            double convertedAmount = amount * rate;
            textField2.setText(String.format("%.2f", convertedAmount));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
    }
}