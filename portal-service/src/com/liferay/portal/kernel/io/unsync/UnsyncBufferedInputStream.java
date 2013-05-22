/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
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
			throw new IllegalArgumentException("Size is less than 0");
		}

		buffer = new byte[size];
	}

	@Override
	public int available() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		return inputStream.available() + (firstInvalidIndex - index);
	}

	@Override
	public void close() throws IOException {
		if (inputStream != null) {
			inputStream.close();

			inputStream = null;
			buffer = null;
		}
	}

	@Override
	public void mark(int readLimit) {
		if (readLimit <= 0) {
			return;
		}

		markLimitIndex = readLimit;

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
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (index >= firstInvalidIndex) {
			fillInBuffer();

			if (index >= firstInvalidIndex) {
				return -1;
			}
		}

		return buffer[index++] & 0xff;
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

		if (length <= 0) {
			return 0;
		}

		int read = 0;

		while (true) {

			// Try to at least read some data

			int currentRead = readOnce(bytes, offset + read, length - read);

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

		if (markLimitIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		index = 0;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (skip <= 0) {
			return 0;
		}

		long available = firstInvalidIndex - index;

		if (available <= 0) {
			if (markLimitIndex < 0) {

				// No mark required, skip the underlying input stream

				return inputStream.skip(skip);
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

			int number = inputStream.read(buffer);

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

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

			buffer = newBuffer;
		}

		// Read underlying input stream since the buffer has more space

		firstInvalidIndex = index;

		int number = inputStream.read(buffer, index, buffer.length - index);

		if (number > 0) {
			firstInvalidIndex += number;
		}
	}

	protected int readOnce(byte[] bytes, int offset, int length)
		throws IOException {

		int available = firstInvalidIndex - index;

		if (available <= 0) {

			// Buffer is empty, read from under input stream

			if ((markLimitIndex < 0) && (length >= buffer.length)) {

				// No mark required, left read block is no less than buffer,
				// read through buffer is inefficient, so directly read from
				// underlying input stream

				return inputStream.read(bytes, offset, length);
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

		System.arraycopy(buffer, index, bytes, offset, length);

		index += length;

		return length;
	}

	protected byte[] buffer;
	protected int firstInvalidIndex;
	protected int index;
	protected int markLimitIndex = -1;

	private static final int _DEFAULT_BUFFER_SIZE = 8192;

}