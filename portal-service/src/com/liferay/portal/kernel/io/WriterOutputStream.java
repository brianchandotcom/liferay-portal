/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.nio.charset.CharsetDecoderUtil;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * @author Shuyang Zhou
 */
public class WriterOutputStream extends OutputStream {

	public WriterOutputStream(Writer writer) {
		this(
			writer, StringPool.DEFAULT_CHARSET_NAME,
			_DEFAULT_OUTPUT_BUFFER_SIZE, false);
	}

	public WriterOutputStream(Writer writer, String charsetName) {
		this(writer, charsetName, _DEFAULT_OUTPUT_BUFFER_SIZE, false);
	}

	public WriterOutputStream(
		Writer writer, String charsetName, boolean autoFlush) {

		this(writer, charsetName, _DEFAULT_OUTPUT_BUFFER_SIZE, autoFlush);
	}

	public WriterOutputStream(
		Writer writer, String charsetName, int outputBufferSize) {

		this(writer, charsetName, outputBufferSize, false);
	}

	public WriterOutputStream(
		Writer writer, String charsetName, int outputBufferSize,
		boolean autoFlush) {

		if (outputBufferSize <= 0) {
			throw new IllegalArgumentException(
				"Output buffer size " + outputBufferSize +
					" must be a positive number");
		}

		if (charsetName == null) {
			charsetName = StringPool.DEFAULT_CHARSET_NAME;
		}

		_writer = writer;
		_charsetName = charsetName;
		_charsetDecoder = CharsetDecoderUtil.getCharsetDecoder(charsetName);

		CharsetEncoder charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
			charsetName);

		_inputBuffer = ByteBuffer.allocate(
			(int)Math.ceil(charsetEncoder.maxBytesPerChar()));
		_inputBuffer.limit(0);

		_outputBuffer = CharBuffer.allocate(outputBufferSize);
		_autoFlush = autoFlush;
	}

	@Override
	public void close() throws IOException {
		_doDecode(_inputBuffer, true);

		flush();

		_writer.close();
	}

	@Override
	public void flush() throws IOException {
		if (_outputBuffer.position() > 0) {
			_writer.write(_outputBuffer.array(), 0, _outputBuffer.position());

			_writer.flush();

			_outputBuffer.rewind();
		}
	}

	public String getEncoding() {
		return _charsetName;
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		while (_inputBuffer.hasRemaining()) {
			write(bytes[offset++]);

			length--;
		}

		ByteBuffer inputBuffer = ByteBuffer.wrap(bytes, offset, length);

		_doDecode(inputBuffer, false);

		if (inputBuffer.hasRemaining()) {
			_inputBuffer.limit(inputBuffer.remaining());
			_inputBuffer.put(inputBuffer);

			_inputBuffer.flip();
		}
	}

	@Override
	public void write(int b) throws IOException {
		int limit = _inputBuffer.limit();

		_inputBuffer.limit(limit + 1);

		_inputBuffer.put(limit, (byte)b);

		_doDecode(_inputBuffer, false);

		if (!_inputBuffer.hasRemaining()) {
			_inputBuffer.position(0);
			_inputBuffer.limit(0);
		}
	}

	private void _doDecode(ByteBuffer inputBuffer, boolean endOfInput)
		throws IOException {

		while (true) {
			CoderResult coderResult = _charsetDecoder.decode(
				inputBuffer, _outputBuffer, endOfInput);

			if (coderResult.isOverflow()) {
				flush();
			}
			else if (coderResult.isUnderflow()) {
				if (_autoFlush) {
					flush();
				}

				break;
			}
			else {
				throw new IOException("Unexcepted coder result " + coderResult);
			}
		}
	}

	private static final int _DEFAULT_OUTPUT_BUFFER_SIZE = 8192;

	private boolean _autoFlush;
	private CharsetDecoder _charsetDecoder;
	private String _charsetName;
	private ByteBuffer _inputBuffer;
	private CharBuffer _outputBuffer;
	private Writer _writer;

}