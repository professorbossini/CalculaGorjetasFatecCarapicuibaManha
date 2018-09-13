package br.com.bossini.calculagorjetasfateccarapicuibamanha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    private double billAmount;
    private double percent = 0.15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountTextView = (TextView)
                findViewById(R.id.amountTextView);
        percentTextView = (TextView)
                findViewById (R.id.percentTextView);
        tipTextView = (TextView)
                findViewById(R.id.tipTextView);
        totalTextView = (TextView)
                findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));
        EditText amountEditText = (EditText)
                findViewById(R.id.amountEditText);
        SeekBar percentSeekBar = (SeekBar)
                findViewById(R.id.percentSeekBar);
        ObservadorDoEditText observadorDoEditText =
                new ObservadorDoEditText();
        amountEditText.addTextChangedListener(observadorDoEditText);
        ObservadorDaSeekBar observadorDaSeekBar =
                new ObservadorDaSeekBar();
        percentSeekBar.setOnSeekBarChangeListener(observadorDaSeekBar);
    }

    class ObservadorDoEditText implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                double aquiloQueOUsuarioDigitou = Double.parseDouble(s.toString()) / 100;
                billAmount = aquiloQueOUsuarioDigitou;
                String formatado = currencyFormat.format(aquiloQueOUsuarioDigitou);
                amountTextView.setText(formatado);
                double tip = aquiloQueOUsuarioDigitou * percent;
                double totalGeral = tip + aquiloQueOUsuarioDigitou;
                tipTextView.setText(currencyFormat.format(tip));
                totalTextView.setText(currencyFormat.format(totalGeral));
            }
            catch (NumberFormatException e){
                amountTextView.setText(currencyFormat.format(0));
                tipTextView.setText(currencyFormat.format(0));
                totalTextView.setText(currencyFormat.format(0));
                Toast.makeText(MainActivity.this, getString(R.string.sem_consumo), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class ObservadorDaSeekBar implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //Toast.makeText(MainActivity.this, Integer.toString(progress), Toast.LENGTH_LONG).show();
            percent = progress / 100.;
            percentTextView.setText(percentFormat.format(percent));
            double tip = billAmount * percent;
            double totalGeral = tip + billAmount;
            tipTextView.setText(currencyFormat.format(tip));
            totalTextView.setText(currencyFormat.format(totalGeral));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
