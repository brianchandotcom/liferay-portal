/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io.unsync;

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
		buffer = new byte[size];
	}

	public void reset() {
		index = 0;
	}

	public int size() {
		return index;
	}

	public byte[] toByteArray() {
		byte[] newBuffer = new byte[index];

		System.arraycopy(buffer, 0, newBuffer, 0, index);

		return newBuffer;
	}

	@Override
	public String toString() {
		return new String(buffer, 0, index);
	}

	public String toString(String charsetName)
		throws UnsupportedEncodingException {

		return new String(buffer, 0, index, charsetName);
	}

	public byte[] unsafeGetByteArray() {
		return buffer;
	}

	public ByteBuffer unsafeGetByteBuffer() {
		return ByteBuffer.wrap(buffer, 0, index);
	}

	@Override
	public void write(byte[] bytes) {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) {
		if (length <= 0) {
			return;
		}

		int newIndex = index + length;

		if (newIndex > buffer.length) {
			int newBufferSize = Math.max(buffer.length << 1, newIndex);

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, index);

			buffer = newBuffer;
		}

		System.arraycopy(bytes, offset, buffer, index, length);

		index = newIndex;
	}

	@Override
	public void write(int b) {
		int newIndex = index + 1;

		if (newIndex > buffer.length) {
			int newBufferSize = Math.max(buffer.length << 1, newIndex);

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

			buffer = newBuffer;
		}

		buffer[index] = (byte)b;

		index = newIndex;
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		outputStream.write(buffer, 0, index);
	}

	protected byte[] buffer;
	protected int index;

}