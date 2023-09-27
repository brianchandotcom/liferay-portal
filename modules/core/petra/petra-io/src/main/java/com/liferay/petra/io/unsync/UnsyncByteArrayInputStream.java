/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import java.io.InputStream;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStream extends InputStream {

	public UnsyncByteArrayInputStream(byte[] buffer) {
		_buffer = buffer;

		_index = 0;
		_capacity = buffer.length;
	}

	public UnsyncByteArrayInputStream(byte[] buffer, int offset, int length) {
		_buffer = buffer;

		_index = offset;
		_markIndex = offset;

		_capacity = Math.min(buffer.length, offset + length);
	}

	@Override
	public int available() {
		return _capacity - _index;
	}

	@Override
	public void mark(int readAheadLimit) {
		_markIndex = _index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() {
		if (_index < _capacity) {
			return _buffer[_index++] & 0xff;
		}

		return -1;
	}

	@Override
	public int read(byte[] bytes) {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) {
		if (length < 0) {
			throw new IndexOutOfBoundsException("{length=" + length + "}");
		}

		if (length == 0) {
			return 0;
		}

		if (_index >= _capacity) {
			return -1;
		}

		int read = length;

		if ((_index + read) > _capacity) {
			read = _capacity - _index;
		}

		System.arraycopy(_buffer, _index, bytes, offset, read);

		_index += read;

		return read;
	}

	@Override
	public void reset() {
		_index = _markIndex;
	}

	@Override
	public long skip(long skip) {
		if (skip < 0) {
			return 0;
		}

		if ((skip + _index) > _capacity) {
			skip = _capacity - _index;
		}

		_index += skip;

		return skip;
	}

	private final byte[] _buffer;
	private final int _capacity;
	private int _index;
	private int _markIndex;

}