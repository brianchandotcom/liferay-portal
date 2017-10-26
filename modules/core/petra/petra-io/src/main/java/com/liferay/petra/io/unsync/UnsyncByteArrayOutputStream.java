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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayOutputStream extends OutputStream {

	public UnsyncByteArrayOutputStream() {
		this(32);
	}

	public UnsyncByteArrayOutputStream(int size) {
		_buffer = new byte[size];
	}

	public void reset() {
		_index = 0;
	}

	public int size() {
		return _index;
	}

	public byte[] toByteArray() {
		byte[] newBuffer = new byte[_index];

		System.arraycopy(_buffer, 0, newBuffer, 0, _index);

		return newBuffer;
	}

	@Override
	public String toString() {
		return new String(_buffer, 0, _index);
	}

	public String toString(String charsetName)
		throws UnsupportedEncodingException {

		return new String(_buffer, 0, _index, charsetName);
	}

	public byte[] unsafeGetByteArray() {
		return _buffer;
	}

	public ByteBuffer unsafeGetByteBuffer() {
		return ByteBuffer.wrap(_buffer, 0, _index);
	}

	@Override
	public void write(byte[] bytes) {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) {
		BoundaryCheckerUtil.check(bytes.length, offset, length);

		if (length == 0) {
			return;
		}

		int newIndex = _index + length;

		_ensureCapacity(newIndex);

		System.arraycopy(bytes, offset, _buffer, _index, length);

		_index = newIndex;
	}

	@Override
	public void write(int b) {
		int newIndex = _index + 1;

		_ensureCapacity(newIndex);

		_buffer[_index] = (byte)b;

		_index = newIndex;
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		outputStream.write(_buffer, 0, _index);
	}

	private void _ensureCapacity(int minCapacity) {
		if (minCapacity > _buffer.length) {
			int newBufferSize = Math.max(_buffer.length << 1, minCapacity);

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);

			_buffer = newBuffer;
		}
	}

	private byte[] _buffer;
	private int _index;

}