/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStream extends UnsyncFilterOutputStream {

	public UnsyncBufferedOutputStream(OutputStream outputStream) {
		this(outputStream, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedOutputStream(OutputStream outputStream, int size) {
		super(outputStream);

		if (size <= 0) {
			throw new IllegalArgumentException("Size is less than 1");
		}

		_buffer = new byte[size];
	}

	@Override
	public void flush() throws IOException {
		_flushBuffer();

		outputStream.flush();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		if (length >= _buffer.length) {
			_flushBuffer();

			outputStream.write(bytes, offset, length);

			return;
		}

		if (length > (_buffer.length - _count)) {
			_flushBuffer();
		}

		System.arraycopy(bytes, offset, _buffer, _count, length);

		_count += length;
	}

	@Override
	public void write(int b) throws IOException {
		if (_count >= _buffer.length) {
			outputStream.write(_buffer, 0, _count);

			_count = 0;
		}

		_buffer[_count++] = (byte)b;
	}

	private void _flushBuffer() throws IOException {
		if (_count > 0) {
			outputStream.write(_buffer, 0, _count);

			_count = 0;
		}
	}

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

	private byte[] _buffer;
	private int _count;

}