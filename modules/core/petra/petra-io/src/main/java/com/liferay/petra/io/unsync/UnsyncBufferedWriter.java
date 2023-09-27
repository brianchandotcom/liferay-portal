/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriter extends Writer {

	public UnsyncBufferedWriter(Writer writer) {
		this(writer, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedWriter(Writer writer, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size is less than 1");
		}

		_writer = writer;
		_size = size;

		_buffer = new char[size];
	}

	@Override
	public void close() throws IOException {
		if (_writer == null) {
			return;
		}

		flush();

		_writer.close();

		_writer = null;
		_buffer = null;
	}

	@Override
	public void flush() throws IOException {
		if (_writer == null) {
			throw new IOException("Writer is null");
		}

		if (_count > 0) {
			_writer.write(_buffer, 0, _count);

			_count = 0;
		}

		_writer.flush();
	}

	public void newLine() throws IOException {
		write(_LINE_SEPARATOR);
	}

	@Override
	public void write(char[] chars, int offset, int length) throws IOException {
		if (_writer == null) {
			throw new IOException("Writer is null");
		}

		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (length >= _size) {
			if (_count > 0) {
				_writer.write(_buffer, 0, _count);

				_count = 0;
			}

			_writer.write(chars, offset, length);

			return;
		}

		if ((_count > 0) && (length > (_size - _count))) {
			_writer.write(_buffer, 0, _count);

			_count = 0;
		}

		System.arraycopy(chars, offset, _buffer, _count, length);

		_count += length;
	}

	@Override
	public void write(int c) throws IOException {
		if (_writer == null) {
			throw new IOException("Writer is null");
		}

		if (_count >= _size) {
			_writer.write(_buffer);

			_count = 0;
		}

		_buffer[_count++] = (char)c;
	}

	@Override
	public void write(String string, int offset, int length)
		throws IOException {

		if (_writer == null) {
			throw new IOException("Writer is null");
		}

		BoundaryCheckerUtil.check(string.length(), offset, length);

		int x = offset;
		int y = offset + length;

		while (x < y) {
			if (_count >= _size) {
				_writer.write(_buffer);

				_count = 0;
			}

			int leftFreeSpace = _size - _count;
			int leftDataSize = y - x;

			if (leftFreeSpace > leftDataSize) {
				string.getChars(x, y, _buffer, _count);

				_count += leftDataSize;

				break;
			}

			int copyEnd = x + leftFreeSpace;

			string.getChars(x, copyEnd, _buffer, _count);

			_count += leftFreeSpace;

			x = copyEnd;
		}
	}

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private char[] _buffer;
	private int _count;
	private int _size;
	private Writer _writer;

}