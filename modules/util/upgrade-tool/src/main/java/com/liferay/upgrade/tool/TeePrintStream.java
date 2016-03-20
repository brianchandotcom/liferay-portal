/* @generated */

package com.liferay.upgrade.tool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Carlos Heuberger
 */
public class TeePrintStream extends PrintStream {
	private final PrintStream second;

	public TeePrintStream(OutputStream main, PrintStream second) {
		super(main);
		this.second = second;
	}

	/**
	 * Closes the main stream.
	 * The second stream is just flushed but <b>not</b> closed.
	 * @see java.io.PrintStream#close()
	 */
	@Override
	public void close() {
		// just for documentation
		super.close();
	}

	@Override
	public void flush() {
		super.flush();
		second.flush();
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
		second.write(buf, off, len);
	}

	@Override
	public void write(int b) {
		super.write(b);
		second.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		super.write(b);
		second.write(b);
	}
}