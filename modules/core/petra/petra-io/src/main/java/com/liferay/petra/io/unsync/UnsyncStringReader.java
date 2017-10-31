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

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringReader extends Reader {

	public UnsyncStringReader(String string) {
		_string = string;

		_stringLength = string.length();
	}

	@Override
	public void close() {
		_string = null;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		if (_string == null) {
			throw new IOException("String is null");
		}

		_markIndex = _index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (_string == null) {
			throw new IOException("String is null");
		}

		if (_index >= _stringLength) {
			return -1;
		}

		return _string.charAt(_index++);
	}

	@Override
	public int read(char[] chars) throws IOException {
		return read(chars, 0, chars.length);
	}

	@Override
	public int read(char[] chars, int offset, int length) throws IOException {
		if (_string == null) {
			throw new IOException("String is null");
		}

		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (length == 0) {
			return 0;
		}

		if (_index >= _stringLength) {
			return -1;
		}

		int read = length;

		if ((_index + read) > _stringLength) {
			read = _stringLength - _index;
		}

		_string.getChars(_index, _index + read, chars, offset);

		_index += read;

		return read;
	}

	@Override
	public boolean ready() throws IOException {
		if (_string == null) {
			throw new IOException("String is null");
		}

		return true;
	}

	@Override
	public void reset() throws IOException {
		if (_string == null) {
			throw new IOException("String is null");
		}

		_index = _markIndex;
	}

	@Override
	public long skip(long skip) throws IOException {
		if (skip < 0) {
			throw new IllegalArgumentException("skip value is negative");
		}

		if (_string == null) {
			throw new IOException("String is null");
		}

		if (_index >= _stringLength) {
			return 0;
		}

		if ((skip + _index) > _stringLength) {
			skip = _stringLength - _index;
		}

		_index += skip;

		return skip;
	}

	private int _index;
	private int _markIndex;
	private String _string;
	private final int _stringLength;

}