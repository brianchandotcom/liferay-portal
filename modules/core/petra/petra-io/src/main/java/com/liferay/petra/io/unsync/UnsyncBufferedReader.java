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

package com.liferay.petra.io.unsync;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

import java.io.IOException;
import java.io.Reader;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReader extends Reader {

	public UnsyncBufferedReader(Reader reader) {
		this(reader, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedReader(Reader reader, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size is less than 1");
		}

		_reader = reader;

		_buffer = new char[size];
	}

	@Override
	public void close() throws IOException {
		if (_reader != null) {
			_reader.close();

			_reader = null;
			_buffer = null;
		}
	}

	@Override
	public void mark(int markLimit) throws IOException {
		if (markLimit < 0) {
			throw new IllegalArgumentException("Mark limit is less than 0");
		}

		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		if (markLimit == 0) {
			return;
		}

		_markLimitIndex = markLimit;

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
		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		if (_index >= _firstInvalidIndex) {
			_fillInBuffer();

			if (_index >= _firstInvalidIndex) {
				return -1;
			}
		}

		return _buffer[_index++];
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (length == 0) {
			return 0;
		}

		int read = 0;

		while (true) {

			// Try to at least read some data

			int currentRead = _readOnce(chars, offset + read, length - read);

			if (currentRead <= 0) {
				if (read == 0) {
					read = currentRead;
				}

				break;
			}

			read += currentRead;

			if (!_reader.ready() || (read >= length)) {

				// Read enough or further reading may be blocked, stop reading

				break;
			}
		}

		return read;
	}

	public String readLine() throws IOException {
		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		StringBundler sb = null;

		while (true) {
			if (_index >= _firstInvalidIndex) {
				_fillInBuffer();
			}

			if (_index >= _firstInvalidIndex) {
				if (sb == null) {
					return null;
				}

				return sb.toString();
			}

			boolean hasLineBreak = false;
			char lineEndChar = 0;

			int x = _index;

			int y = _index;

			while (y < _firstInvalidIndex) {
				lineEndChar = _buffer[y];

				if ((lineEndChar == CharPool.NEW_LINE) ||
					(lineEndChar == CharPool.RETURN)) {

					hasLineBreak = true;

					break;
				}

				y++;
			}

			String line = new String(_buffer, x, y - x);

			_index = y;

			if (hasLineBreak) {
				_index++;

				if ((lineEndChar == CharPool.RETURN) &&
					(_index < _buffer.length) &&
					(_buffer[_index] == CharPool.NEW_LINE)) {

					_index++;
				}

				if (sb == null) {
					return line;
				}

				sb.append(line);

				return sb.toString();
			}

			if (sb == null) {
				sb = new StringBundler();
			}

			sb.append(line);
		}
	}

	@Override
	public boolean ready() throws IOException {
		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		if ((_index < _firstInvalidIndex) || _reader.ready()) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() throws IOException {
		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		if (_markLimitIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		_index = 0;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (skip < 0) {
			throw new IllegalArgumentException("Skip is less than 0");
		}

		if (_reader == null) {
			throw new IOException("Reader is null");
		}

		if (skip == 0) {
			return 0;
		}

		long available = _firstInvalidIndex - _index;

		if (available <= 0) {
			if (_markLimitIndex < 0) {

				// No mark required, skip the underlying input stream

				return _reader.skip(skip);
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

			int number = _reader.read(_buffer);

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

			char[] newBuffer = new char[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);

			_buffer = newBuffer;
		}

		// Read the underlying input stream since the buffer has more space

		_firstInvalidIndex = _index;

		int number = _reader.read(_buffer, _index, _buffer.length - _index);

		if (number > 0) {
			_firstInvalidIndex += number;
		}
	}

	private int _readOnce(char[] chars, int offset, int length)
		throws IOException {

		int available = _firstInvalidIndex - _index;

		if (available <= 0) {

			// Buffer is empty, read from underlying reader

			if ((_markLimitIndex < 0) && (length >= _buffer.length)) {

				// No mark required, left read block is no less than buffer,
				// read through buffer is inefficient, so directly read from
				// underlying reader

				return _reader.read(chars, offset, length);
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

		System.arraycopy(_buffer, _index, chars, offset, length);

		_index += length;

		return length;
	}

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

	private char[] _buffer;
	private int _firstInvalidIndex;
	private int _index;
	private int _markLimitIndex = -1;
	private Reader _reader;

}