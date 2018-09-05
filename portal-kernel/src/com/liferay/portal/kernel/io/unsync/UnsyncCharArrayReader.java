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

import java.io.IOException;
import java.io.Reader;

import java.nio.CharBuffer;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayReader extends Reader {

	public UnsyncCharArrayReader(char[] chars) {
		buffer = chars;

		capacity = chars.length;
		index = 0;
	}

	public UnsyncCharArrayReader(char[] chars, int offset, int length) {
		buffer = chars;
		index = offset;
		markIndex = offset;

		capacity = Math.min(chars.length, offset + length);
	}

	@Override
	public void close() {
		buffer = null;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		markIndex = index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		if (index >= capacity) {
			return -1;
		}

		return buffer[index++];
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		if (length <= 0) {
			return 0;
		}

		if (index >= capacity) {
			return -1;
		}

		int read = length;

		if ((index + read) > capacity) {
			read = capacity - index;
		}

		System.arraycopy(buffer, index, chars, offset, read);

		index += read;

		return read;
	}

	@Override
	public int read(CharBuffer charBuffer) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		int length = charBuffer.remaining();

		if (length <= 0) {
			return 0;
		}

		if (index >= capacity) {
			return -1;
		}

		if ((index + length) > capacity) {
			length = capacity - index;
		}

		charBuffer.put(buffer, index, length);

		index += length;

		return length;
	}

	@Override
	public boolean ready() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		if (capacity > index) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		index = markIndex;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}

		if (skip < 0) {
			return 0;
		}

		if ((index + skip) > capacity) {
			skip = capacity - index;
		}

		index += skip;

		return skip;
	}

	protected char[] buffer;
	protected int capacity;
	protected int index;
	protected int markIndex;

}