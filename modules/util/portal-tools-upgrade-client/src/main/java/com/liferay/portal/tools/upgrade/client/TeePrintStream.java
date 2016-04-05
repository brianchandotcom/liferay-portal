/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.upgrade.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Inspired by code written by Carlos Heuberger at
 * http://stackoverflow.com/questions/1994255/how-to-write-console-output-to-a-txt-file
 *
 * @author David Truong
 *
 */
public class TeePrintStream extends PrintStream {

	public TeePrintStream(OutputStream outputStream, PrintStream printStream) {
		super(outputStream);

		_printStream = printStream;
	}

	/**
	 * Closes the main stream.
	 * The _printStream stream is just flushed but <b>not</b> closed.
	 *
	 * @see java.io.PrintStream#close()
	 */
	@Override
	public void close() {
		super.close();

		_printStream.flush();
	}

	@Override
	public void flush() {
		super.flush();

		_printStream.flush();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		super.write(bytes);

		_printStream.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int off, int len) {
		super.write(bytes, off, len);

		_printStream.write(bytes, off, len);
	}

	@Override
	public void write(int integer) {
		super.write(integer);

		_printStream.write(integer);
	}

	private final PrintStream _printStream;

}