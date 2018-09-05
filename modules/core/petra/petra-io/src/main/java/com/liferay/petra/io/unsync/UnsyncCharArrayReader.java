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

import java.io.IOException;
import java.io.Reader;

import java.nio.CharBuffer;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayReader extends Reader {

	public UnsyncCharArrayReader(char[] chars) {
		_buffer = chars;

		_capacity = chars.length;
		_index = 0;
	}

	public UnsyncCharArrayReader(char[] chars, int offset, int length) {
		_buffer = chars;
		_index = offset;
		_markIndex = offset;

		_capacity = Math.min(chars.length, offset + length);
	}

	@Override
	public void close() {
		_buffer = null;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		_markIndex = _index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		if (_index >= _capacity) {
			return -1;
		}

		return _buffer[_index++];
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (_index >= _capacity) {
			return -1;
		}

		int read = length;

		if ((_index + read) > _capacity) {
			read = _capacity - _index;
		}

		System.arraycopy(_buffer, _index, chars, offset, read);

		_index += read;

		return read;
	}

	@Override
	public int read(CharBuffer charBuffer) throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		int length = charBuffer.remaining();

		if (length <= 0) {
			return 0;
		}

		if (_index >= _capacity) {
			return -1;
		}

		if ((_index + length) > _capacity) {
			length = _capacity - _index;
		}

		charBuffer.put(_buffer, _index, length);

		_index += length;

		return length;
	}

	@Override
	public boolean ready() throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		if (_capacity > _index) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		_index = _markIndex;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (_buffer == null) {
			throw new IOException("Stream closed");
		}

		if (skip < 0) {
			throw new IllegalArgumentException("skip value is negative");
		}

		if ((_index + skip) > _capacity) {
			skip = _capacity - _index;
		}

		_index += skip;

		return skip;
	}

	private char[] _buffer;
	private final int _capacity;
	private int _index;
	private int _markIndex;

}