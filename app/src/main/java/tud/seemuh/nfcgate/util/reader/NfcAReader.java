package tud.seemuh.nfcgate.util.reader;

import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.util.Log;

import java.io.IOException;
import java.util.Hashtable;

import tud.seemuh.nfcgate.util.NFCTagReader;

/**
 * Implements an NFCTagReader using the NfcA technology
 *
 * Created by Max on 27.10.14.
 */
public class NfcAReader implements NFCTagReader {
    private NfcA adapter = null;

    /**
     * Constructor of NfcAReader-Class, providing an NFC reader interface using the NfcA-Tech.
     *
     * @param tag: A tag using the NfcA technology.
     */
    public NfcAReader(Tag tag) {
        adapter = NfcA.get(tag);
    }

    /**
     * Send a raw command to the NFC chip, receiving the answer as a byte[]
     *
     * @param command: byte[]-representation of the command to be sent
     * @return byte[]-representation of the answer of the NFC chip
     *
     * TODO: Note to self: "Applications must not append the EoD (CRC) to the payload, it will be
     *       automatically calculated. "
     */
    public byte[] sendCmd(byte[] command) {
        try {
            adapter.connect();
            byte[] retval = adapter.transceive(command);
            adapter.close();
            Log.i("NFC_READER_NFCA", "Transceived succesfully, returned: " + retval.toString());
            return retval;
        } catch(IOException e) {
            // TODO: Handle Exception properly
            Log.e("NFC_READER_NFCA", "Encountered IOException in sendCmd: " + e.toString());
            return new byte[1];
        }
    }

    /**
     * Returns a HashTable containing meta-bytes. The exact bytes are protocol-specific.
     * An exact list of which bytes will be returned will be added as soon as the functionality
     * is finished.
     *
     * @return A Hashtable<String, byte[]> containing a mapping between the meta-byte names
     *     and their values.
     *
     * TODO: Add list of provided bytes.
     */
    public Hashtable<String, byte[]> getMetaBytes() {
        Hashtable<String, byte[]> retval = new Hashtable<String, byte[]>();
        retval.put("Atqa", adapter.getAtqa());
        // retval.put("Sak", adapter.getSak());
        // TODO Add Sak bytes
        return retval;
    }

    /**
     * Returns the integer representation of the protocol the used implementation is speaking.
     * This will be one of the READER_* constants defined by the util.NFCTagReader interface.
     *
     * @return integer representation of the underlying NFC tag reader protocol
     */
    public int getProtocol() {
        return READER_NFC_A;
    }

}