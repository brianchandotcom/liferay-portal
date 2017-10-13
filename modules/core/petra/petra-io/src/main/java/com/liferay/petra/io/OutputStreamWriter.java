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

package com.liferay.petra.io;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;

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
		if (!_open) {
			return;
		}

		try {
			_flushEncoder();

			_flushBuffer();

			_outputStream.close();
		}
		finally {
			_open = false;
		}
	}

	@Override
	public void flush() throws IOException {
		_ensureOpen();

		_flushBuffer();

		_outputStream.flush();
	}

	public String getEncoding() {
		return _charsetName;
	}

	@Override
	public void write(char[] chars) throws IOException {
		_write(CharBuffer.wrap(chars, 0, chars.length));
	}

	@Override
	public void write(char[] chars, int offset, int length) throws IOException {
		_write(CharBuffer.wrap(chars, offset, length));
	}

	@Override
	public void write(int c) throws IOException {
		_inputCharBuffer.put((char)c);

		_write(_EMPTY_CHAR_BUFFER);
	}

	@Override
	public void write(String string) throws IOException {
		_write(CharBuffer.wrap(string, 0, string.length()));
	}

	@Override
	public void write(String string, int offset, int length)
		throws IOException {

		_write(CharBuffer.wrap(string, offset, offset + length));
	}

	private void _encodeLeftoverChar(
			CharBuffer inputCharBuffer, boolean endOfInput)
		throws IOException {

		if (_inputCharBuffer.position() == 0) {
			return;
		}

		if ((_inputCharBuffer.position() == 1) &&
			inputCharBuffer.hasRemaining()) {

			_inputCharBuffer.put(inputCharBuffer.get());
		}

		_inputCharBuffer.flip();

		_encodeLoop(_inputCharBuffer, endOfInput);

		_inputCharBuffer.compact();
	}

	private void _encodeLoop(CharBuffer inputCharBuffer, boolean endOfInput)
		throws IOException {

		while (inputCharBuffer.hasRemaining()) {
			CoderResult coderResult = _charsetEncoder.encode(
				inputCharBuffer, _outputByteBuffer, endOfInput);

			if (coderResult.isError()) {
				coderResult.throwException();
			}

			if (coderResult.isUnderflow()) {
				if (_autoFlush) {
					_flushBuffer();
				}

				if ((_inputCharBuffer != inputCharBuffer) &&
					inputCharBuffer.hasRemaining()) {

					_inputCharBuffer.put(inputCharBuffer.get());
				}

				break;
			}

			// Must be overflow, no need to check

			_flushBuffer();
		}
	}

	private void _ensureOpen() throws IOException {
		if (!_open) {
			throw new IOException("Stream closed");
		}
	}

	private void _flushBuffer() throws IOException {
		if (_outputByteBuffer.position() > 0) {
			_outputStream.write(
				_outputByteBuffer.array(), 0, _outputByteBuffer.position());

			_outputByteBuffer.rewind();
		}
	}

	private void _flushEncoder() throws IOException {
		_encodeLeftoverChar(_EMPTY_CHAR_BUFFER, true);

		// Ensure encoder transit to END state before flushing, in case the
		// encoder was never used and still in RESET state.

		_charsetEncoder.encode(_EMPTY_CHAR_BUFFER, _outputByteBuffer, true);

		while (true) {
			CoderResult coderResult = _charsetEncoder.flush(_outputByteBuffer);

			if (coderResult.isError()) {
				coderResult.throwException();
			}

			if (coderResult.isUnderflow()) {
				break;
			}

			// Must be overflow, no need to check

			_flushBuffer();
		}
	}

	private void _write(CharBuffer inputCharBuffer) throws IOException {
		_ensureOpen();

		_encodeLeftoverChar(inputCharBuffer, false);

		_encodeLoop(inputCharBuffer, false);
	}

	private static final int _DEFAULT_OUTPUT_BUFFER_SIZE = 8192;

	private static final CharBuffer _EMPTY_CHAR_BUFFER = CharBuffer.allocate(0);

	private final boolean _autoFlush;
	private final CharsetEncoder _charsetEncoder;
	private final String _charsetName;
	private final CharBuffer _inputCharBuffer = CharBuffer.allocate(2);
	private boolean _open = true;
	private final ByteBuffer _outputByteBuffer;
	private final OutputStream _outputStream;

}