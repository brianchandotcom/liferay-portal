/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStream extends UnsyncFilterInputStream {

	public UnsyncBufferedInputStream(InputStream inputStream) {
		this(inputStream, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedInputStream(InputStream inputStream, int size) {
		super(inputStream);

		if (size <= 0) {
			throw new IllegalArgumentException("Size is less than 1");
		}

		_buffer = new byte[size];
	}

	@Override
	public int available() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		return inputStream.available() + (_firstInvalidIndex - _index);
	}

	@Override
	public void close() throws IOException {
		if (inputStream != null) {
			inputStream.close();

			inputStream = null;
			_buffer = null;
		}
	}

	@Override
	public void mark(int readLimit) {
		if (readLimit <= 0) {
			return;
		}

		_markLimitIndex = readLimit;

		if (_index == 0) {
			return;
		}

		int available = _firstInvalidIndex - _index;

		if (available > 0) {

			// Shuffle mark beginning to buffer beginning

			System.arraycopy(_buffer, _index, _buffer, 0, available);

			_index = 0;

			_firstInvalidIndex = available;
		}
		else {

			// Reset buffer states

			_index = _firstInvalidIndex = 0;
		}
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (_index >= _firstInvalidIndex) {
			_fillInBuffer();

			if (_index >= _firstInvalidIndex) {
				return -1;
			}
		}

		return _buffer[_index++] & 0xff;
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (length < 0) {
			throw new IndexOutOfBoundsException("{length=" + length + "}");
		}

		if (length == 0) {
			return 0;
		}

		int read = 0;

		while (true) {

			// Try to at least read some data

			int currentRead = _readOnce(bytes, offset + read, length - read);

			if (currentRead <= 0) {
				if (read == 0) {
					read = currentRead;
				}

				break;
			}

			read += currentRead;

			if ((read >= length) || (inputStream.available() <= 0)) {

				// Read enough or further reading may be blocked, stop reading

				break;
			}
		}

		return read;
	}

	@Override
	public void reset() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (_markLimitIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		_index = 0;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (skip <= 0) {
			return 0;
		}

		long available = _firstInvalidIndex - _index;

		if (available <= 0) {
			if (_markLimitIndex < 0) {

				// No mark required, skip the underlying input stream

				return inputStream.skip(skip);
			}

			// Mark required, save the skipped data

			_fillInBuffer();

			available = _firstInvalidIndex - _index;

			if (available <= 0) {
				return 0;
			}
		}

		// Skip the data in buffer

		if (available < skip) {
			skip = available;
		}

		_index += skip;

		return skip;
	}

	private void _fillInBuffer() throws IOException {
		if (_markLimitIndex < 0) {

			// No mark required, fill the buffer

			_index = _firstInvalidIndex = 0;

			int number = inputStream.read(_buffer);

			if (number > 0) {
				_firstInvalidIndex = number;
			}

			return;
		}

		// Mark required

		if (_index >= _markLimitIndex) {

			// Passed mark limit indexs, get rid of all cache data

			_markLimitIndex = -1;

			_index = _firstInvalidIndex = 0;
		}
		else if (_index == _buffer.length) {

			// Cannot get rid of cache data and there is no room to read in any
			// more data, so grow the buffer

			int newBufferSize = _buffer.length * 2;

			if (newBufferSize > _markLimitIndex) {
				newBufferSize = _markLimitIndex;
			}

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);

			_buffer = newBuffer;
		}

		// Read underlying input stream since the buffer has more space

		_firstInvalidIndex = _index;

		int number = inputStream.read(_buffer, _index, _buffer.length - _index);

		if (number > 0) {
			_firstInvalidIndex += number;
		}
	}

	private int _readOnce(byte[] bytes, int offset, int length)
		throws IOException {

		int available = _firstInvalidIndex - _index;

		if (available <= 0) {

			// Buffer is empty, read from under input stream

			if ((_markLimitIndex < 0) && (length >= _buffer.length)) {

				// No mark required, left read block is no less than buffer,
				// read through buffer is inefficient, so directly read from
				// underlying input stream

				return inputStream.read(bytes, offset, length);
			}

			// Mark is required, has to read through the buffer to remember
			// data

			_fillInBuffer();

			available = _firstInvalidIndex - _index;

			if (available <= 0) {
				return -1;
			}
		}

		if (length > available) {
			length = available;
		}

		System.arraycopy(_buffer, _index, bytes, offset, length);

		_index += length;

		return length;
	}

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

	private byte[] _buffer;
	private int _firstInvalidIndex;
	private int _index;
	private int _markLimitIndex = -1;

}