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
		this.buffer = buffer;

		index = 0;
		capacity = buffer.length;
	}

	public UnsyncByteArrayInputStream(byte[] buffer, int offset, int length) {
		this.buffer = buffer;

		index = offset;
		capacity = Math.min(buffer.length, offset + length);
		markIndex = offset;
	}

	@Override
	public int available() {
		return capacity - index;
	}

	@Override
	public void mark(int readAheadLimit) {
		markIndex = index;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() {
		if (index < capacity) {
			return buffer[index++] & 0xff;
		}

		return -1;
	}

	@Override
	public int read(byte[] bytes) {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) {
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

		System.arraycopy(buffer, index, bytes, offset, read);

		index += read;

		return read;
	}

	@Override
	public void reset() {
		index = markIndex;
	}

	@Override
	public long skip(long skip) {
		if (skip < 0) {
			return 0;
		}

		if ((skip + index) > capacity) {
			skip = capacity - index;
		}

		index += skip;

		return skip;
	}

	protected byte[] buffer;
	protected int capacity;
	protected int index;
	protected int markIndex;

}