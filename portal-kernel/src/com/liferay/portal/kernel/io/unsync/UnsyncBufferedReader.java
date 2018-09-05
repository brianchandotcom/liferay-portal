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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

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
			throw new IllegalArgumentException("Size is less than 0");
		}

		this.reader = reader;

		buffer = new char[size];
	}

	@Override
	public void close() throws IOException {
		if (reader != null) {
			reader.close();

			reader = null;
			buffer = null;
		}
	}

	@Override
	public void mark(int markLimit) throws IOException {
		if (markLimit < 0) {
			throw new IllegalArgumentException("Mark limit is less than 0");
		}

		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (markLimit == 0) {
			return;
		}

		markLimitIndex = markLimit;

		if (index == 0) {
			return;
		}

		int available = firstInvalidIndex - index;

		if (available > 0) {

			// Shuffle mark beginning to buffer beginning

			System.arraycopy(buffer, index, buffer, 0, available);

			index = 0;

			firstInvalidIndex = available;
		}
		else {

			// Reset buffer states

			index = firstInvalidIndex = 0;
		}
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (index >= firstInvalidIndex) {
			fillInBuffer();

			if (index >= firstInvalidIndex) {
				return -1;
			}
		}

		return buffer[index++];
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (length <= 0) {
			return 0;
		}

		int read = 0;

		while (true) {

			// Try to at least read some data

			int currentRead = readOnce(chars, offset + read, length - read);

			if (currentRead <= 0) {
				if (read == 0) {
					read = currentRead;
				}

				break;
			}

			read += currentRead;

			if (!reader.ready() || (read >= length)) {

				// Read enough or further reading may be blocked, stop reading

				break;
			}
		}

		return read;
	}

	public String readLine() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		StringBundler sb = null;

		while (true) {
			if (index >= firstInvalidIndex) {
				fillInBuffer();
			}

			if (index >= firstInvalidIndex) {
				if ((sb != null) && (sb.index() > 0)) {
					return sb.toString();
				}

				return null;
			}

			boolean hasLineBreak = false;
			char lineEndChar = 0;

			int x = index;
			int y = index;

			while (y < firstInvalidIndex) {
				lineEndChar = buffer[y];

				if ((lineEndChar == CharPool.NEW_LINE) ||
					(lineEndChar == CharPool.RETURN)) {

					hasLineBreak = true;

					break;
				}

				y++;
			}

			String line = new String(buffer, x, y - x);

			index = y;

			if (hasLineBreak) {
				index++;

				if (lineEndChar == CharPool.RETURN) {
					if ((index < buffer.length) &&
						(buffer[index] == CharPool.NEW_LINE)) {

						index++;
					}
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
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if ((index < firstInvalidIndex) || reader.ready()) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (markLimitIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		index = 0;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (skip < 0) {
			throw new IllegalArgumentException("Skip is less than 0");
		}

		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (skip == 0) {
			return 0;
		}

		long available = firstInvalidIndex - index;

		if (available <= 0) {
			if (markLimitIndex < 0) {

				// No mark required, skip the underlying input stream

				return reader.skip(skip);
			}
			else {

				// Mark required, save the skipped data

				fillInBuffer();

				available = firstInvalidIndex - index;

				if (available <= 0) {
					return 0;
				}
			}
		}

		// Skip the data in buffer

		if (available < skip) {
			skip = available;
		}

		index += skip;

		return skip;
	}

	protected void fillInBuffer() throws IOException {
		if (markLimitIndex < 0) {

			// No mark required, fill the buffer

			index = firstInvalidIndex = 0;

			int number = reader.read(buffer);

			if (number > 0) {
				firstInvalidIndex = number;
			}

			return;
		}

		// Mark required

		if (index >= markLimitIndex) {

			// Passed mark limit indexs, get rid of all cache data

			markLimitIndex = -1;

			index = firstInvalidIndex = 0;
		}
		else if (index == buffer.length) {

			// Cannot get rid of cache data and there is no room to read in any
			// more data, so grow the buffer

			int newBufferSize = buffer.length * 2;

			if (newBufferSize > markLimitIndex) {
				newBufferSize = markLimitIndex;
			}

			char[] newBuffer = new char[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

			buffer = newBuffer;
		}

		// Read the underlying input stream since the buffer has more space

		firstInvalidIndex = index;

		int number = reader.read(buffer, index, buffer.length - index);

		if (number > 0) {
			firstInvalidIndex += number;
		}
	}

	protected int readOnce(char[] chars, int offset, int length)
		throws IOException {

		int available = firstInvalidIndex - index;

		if (available <= 0) {

			// Buffer is empty, read from underlying reader

			if ((markLimitIndex < 0) && (length >= buffer.length)) {

				// No mark required, left read block is no less than buffer,
				// read through buffer is inefficient, so directly read from
				// underlying reader

				return reader.read(chars, offset, length);
			}
			else {

				// Mark is required, has to read through the buffer to remember
				// data

				fillInBuffer();

				available = firstInvalidIndex - index;

				if (available <= 0) {
					return -1;
				}
			}
		}

		if (length > available) {
			length = available;
		}

		System.arraycopy(buffer, index, chars, offset, length);

		index += length;

		return length;
	}

	protected char[] buffer;
	protected int firstInvalidIndex;
	protected int index;
	protected int markLimitIndex = -1;
	protected Reader reader;

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

}