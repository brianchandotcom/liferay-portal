/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.Reader;

import java.nio.CharBuffer;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringReader extends Reader {

	public UnsyncStringReader(String string) {
		this.string = string;

		stringLength = string.length();
	}

	@Override
	public void close() {
		string = null;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		markIndex = index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		if (index >= stringLength) {
			return -1;
		}

		return string.charAt(index++);
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		if (length <= 0) {
			return 0;
		}

		if (index >= stringLength) {
			return -1;
		}

		int read = length;

		if ((index + read) > stringLength) {
			read = stringLength - index;
		}

		string.getChars(index, index + read, chars, offset);

		index += read;

		return read;
	}

	@Override
	public int read(CharBuffer charBuffer) throws IOException {
		int remaining = charBuffer.remaining();

		char[] chars = new char[remaining];

		int read = read(chars, 0, remaining);

		if (read > 0) {
			charBuffer.put(chars, 0, read);
		}

		return read;
	}

	@Override
	public boolean ready() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		return true;
	}

	@Override
	public void reset() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		index = markIndex;
	}

	@Override
	public long skip(long skip) {
		if (index >= stringLength) {
			return 0;
		}

		if ((skip + index) > stringLength) {
			skip = stringLength - index;
		}

		index += skip;

		return skip;
	}

	protected int index;
	protected int markIndex;
	protected String string;
	protected int stringLength;

}