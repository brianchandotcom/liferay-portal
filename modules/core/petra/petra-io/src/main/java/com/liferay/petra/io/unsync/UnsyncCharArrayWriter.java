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

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayWriter extends Writer {

	public UnsyncCharArrayWriter() {
		this(32);
	}

	public UnsyncCharArrayWriter(int initialSize) {
		_buffer = new char[initialSize];
	}

	@Override
	public Writer append(char c) {
		write(c);

		return this;
	}

	@Override
	public Writer append(CharSequence charSequence) {
		String string = null;

		if (charSequence == null) {
			string = StringPool.NULL;
		}
		else {
			string = charSequence.toString();
		}

		write(string, 0, string.length());

		return this;
	}

	@Override
	public Writer append(CharSequence charSequence, int start, int end) {
		String string = null;

		if (charSequence == null) {
			string = StringPool.NULL;
		}
		else {
			charSequence = charSequence.subSequence(start, end);

			string = charSequence.toString();
		}

		write(string, 0, string.length());

		return this;
	}

	@Override
	public void close() {
	}

	@Override
	public void flush() {
	}

	public void reset() {
		_index = 0;
	}

	public int size() {
		return _index;
	}

	public CharBuffer toCharBuffer() {
		return CharBuffer.wrap(_buffer, 0, _index);
	}

	@Override
	public String toString() {
		return new String(_buffer, 0, _index);
	}

	@Override
	public void write(char[] chars) {
		write(chars, 0, chars.length);
	}

	@Override
	public void write(char[] chars, int offset, int length) {
		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (length == 0) {
			return;
		}

		int newIndex = _index + length;

		if (newIndex > _buffer.length) {
			int newBufferSize = Math.max(_buffer.length << 1, newIndex);

			char[] newBuffer = new char[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _index);

			_buffer = newBuffer;
		}

		System.arraycopy(chars, offset, _buffer, _index, length);

		_index = newIndex;
	}

	@Override
	public void write(int c) {
		int newIndex = _index + 1;

		if (newIndex > _buffer.length) {
			int newBufferSize = Math.max(_buffer.length << 1, newIndex);

			char[] newBuffer = new char[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);

			_buffer = newBuffer;
		}

		_buffer[_index] = (char)c;

		_index = newIndex;
	}

	@Override
	public void write(String string) {
		write(string, 0, string.length());
	}

	@Override
	public void write(String string, int offset, int length) {
		BoundaryCheckerUtil.check(string.length(), offset, length);

		if (length == 0) {
			return;
		}

		int newIndex = _index + length;

		if (newIndex > _buffer.length) {
			int newBufferSize = Math.max(_buffer.length << 1, newIndex);

			char[] newBuffer = new char[newBufferSize];

			System.arraycopy(_buffer, 0, newBuffer, 0, _index);

			_buffer = newBuffer;
		}

		string.getChars(offset, offset + length, _buffer, _index);

		_index = newIndex;
	}

	public int writeTo(CharBuffer charBuffer) {
		int length = charBuffer.remaining();

		if (length > _index) {
			length = _index;
		}

		if (length == 0) {
			return 0;
		}

		charBuffer.put(_buffer, 0, length);

		return length;
	}

	public int writeTo(OutputStream outputStream, String charsetName)
		throws IOException {

		ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
			charsetName, CharBuffer.wrap(_buffer, 0, _index));

		int length = byteBuffer.limit();

		outputStream.write(byteBuffer.array(), 0, length);

		return length;
	}

	public int writeTo(Writer writer) throws IOException {
		writer.write(_buffer, 0, _index);

		return _index;
	}

	private char[] _buffer;
	private int _index;

}