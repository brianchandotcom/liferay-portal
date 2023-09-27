/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.upgrade.client.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author David Truong
 */
public class TeePrintStream extends PrintStream {

	public TeePrintStream(OutputStream outputStream, PrintStream printStream) {
		super(outputStream);

		_printStream = printStream;
	}

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
	public void write(byte[] bytes, int offset, int length) {
		super.write(bytes, offset, length);

		_printStream.write(bytes, offset, length);
	}

	@Override
	public void write(int integer) {
		super.write(integer);

		_printStream.write(integer);
	}

	private final PrintStream _printStream;

}