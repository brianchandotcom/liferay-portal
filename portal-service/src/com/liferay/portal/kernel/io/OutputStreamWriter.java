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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * @author Shuyang Zhou
 */
public class OutputStreamWriter extends Writer {

	public OutputStreamWriter(OutputStream outputStream) {
		this(
			outputStream, StringPool.DEFAULT_CHARSET_NAME,
			_DEFAULT_OUTPUT_BUFFER_SIZE, false);
	}

	public OutputStreamWriter(OutputStream outputStream, String charsetName) {
		this(outputStream, charsetName, _DEFAULT_OUTPUT_BUFFER_SIZE, false);
	}

	public OutputStreamWriter(
		OutputStream outputStream, String charsetName, boolean autoFlush) {

		this(outputStream, charsetName, _DEFAULT_OUTPUT_BUFFER_SIZE, autoFlush);
	}

	public OutputStreamWriter(
		OutputStream outputStream, String charsetName, int outputBufferSize) {

		this(outputStream, charsetName, outputBufferSize, false);
	}

	public OutputStreamWriter(
		OutputStream outputStream, String charsetName, int outputBufferSize,
		boolean autoFlush) {

		if (outputBufferSize < 4) {
			throw new IllegalArgumentException(
				"Output buffer size " + outputBufferSize + " is less than 4");
		}

		if (charsetName == null) {
			charsetName = StringPool.DEFAULT_CHARSET_NAME;
		}

		_outputStream = outputStream;
		_charsetName = charsetName;
		_charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(charsetName);
		_outputByteBuffer = ByteBuffer.allocate(outputBufferSize);
		_autoFlush = autoFlush;
	}

	@Override
	public void close() throws IOException {
		_flushBuffer();

		_outputStream.close();
	}

	@Override
	public void flush() throws IOException {
		_flushBuffer();

		_outputStream.flush();
	}

	public String getEncoding() {
		return _charsetName;
	}

	@Override
	public void write(char[] chars) throws IOException {
		_doWrite(CharBuffer.wrap(chars, 0, chars.length));
	}

	@Override
	public void write(char[] chars, int offset, int length) throws IOException {
		_doWrite(CharBuffer.wrap(chars, offset, length));
	}

	@Override
	public void write(int c) throws IOException {
		_inputArray[0] = (char)c;

		_doWrite(_inputCharBuffer);

		_inputCharBuffer.clear();
	}

	@Override
	public void write(String string) throws IOException {
		_doWrite(CharBuffer.wrap(string, 0, string.length()));
	}

	@Override
	public void write(String string, int offset, int length)
		throws IOException {

		_doWrite(CharBuffer.wrap(string, offset, offset + length));
	}

	private void _doWrite(CharBuffer inputCharBuffer) throws IOException {
		while (true) {
			CoderResult coderResult = _charsetEncoder.encode(
				inputCharBuffer, _outputByteBuffer, true);

			if (coderResult.isOverflow()) {
				_flushBuffer();
			}
			else if (coderResult.isUnderflow()) {
				if (_autoFlush) {
					_flushBuffer();
				}

				break;
			}
			else {
				throw new IOException("Unexcepted coder result " + coderResult);
			}
		}
	}

	private void _flushBuffer() throws IOException {
		if (_outputByteBuffer.position() > 0) {
			_outputStream.write(
				_outputByteBuffer.array(), 0, _outputByteBuffer.position());

			_outputByteBuffer.rewind();
		}
	}

	private static final int _DEFAULT_OUTPUT_BUFFER_SIZE = 8192;

	private final boolean _autoFlush;
	private final CharsetEncoder _charsetEncoder;
	private final String _charsetName;
	private final char[] _inputArray = new char[1];
	private final CharBuffer _inputCharBuffer = CharBuffer.wrap(_inputArray);
	private final ByteBuffer _outputByteBuffer;
	private final OutputStream _outputStream;

}