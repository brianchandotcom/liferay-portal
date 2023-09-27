/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io;

import com.liferay.petra.nio.CharsetDecoderUtil;
import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;

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
			if (autoFlush) {
				outputBufferSize = _DEFAULT_OUTPUT_BUFFER_SIZE;
			}
			else {
				throw new IllegalArgumentException(
					"Output buffer size " + outputBufferSize +
						" must be a positive number");
			}
		}

		if (charsetName == null) {
			charsetName = StringPool.DEFAULT_CHARSET_NAME;
		}

		_writer = writer;
		_charsetName = charsetName;

		_charsetDecoder = CharsetDecoderUtil.getCharsetDecoder(charsetName);

		CharsetEncoder charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
			charsetName);

		_inputByteBuffer = ByteBuffer.allocate(
			(int)Math.ceil(charsetEncoder.maxBytesPerChar()));

		_inputByteBuffer.limit(0);

		_autoFlush = autoFlush;

		_outputCharBuffer = CharBuffer.allocate(outputBufferSize);
	}

	@Override
	public void close() throws IOException {
		_decode(_inputByteBuffer, true);

		_flushBuffer();

		_writer.close();
	}

	@Override
	public void flush() throws IOException {
		_flushBuffer();

		_writer.flush();
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
		while (_inputByteBuffer.hasRemaining()) {
			write(bytes[offset++]);

			length--;
		}

		ByteBuffer inputByteBuffer = ByteBuffer.wrap(bytes, offset, length);

		_decode(inputByteBuffer, false);

		if (inputByteBuffer.hasRemaining()) {
			_inputByteBuffer.limit(inputByteBuffer.remaining());

			_inputByteBuffer.put(inputByteBuffer);

			_inputByteBuffer.flip();
		}
	}

	@Override
	public void write(int b) throws IOException {
		int limit = _inputByteBuffer.limit();

		_inputByteBuffer.limit(limit + 1);

		_inputByteBuffer.put(limit, (byte)b);

		_decode(_inputByteBuffer, false);

		if (!_inputByteBuffer.hasRemaining()) {
			_inputByteBuffer.position(0);

			_inputByteBuffer.limit(0);
		}
	}

	private void _decode(ByteBuffer inputByteBuffer, boolean endOfInput)
		throws IOException {

		while (true) {
			CoderResult coderResult = _charsetDecoder.decode(
				inputByteBuffer, _outputCharBuffer, endOfInput);

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
		if (_outputCharBuffer.position() > 0) {
			_writer.write(
				_outputCharBuffer.array(), 0, _outputCharBuffer.position());

			_outputCharBuffer.rewind();
		}
	}

	private static final int _DEFAULT_OUTPUT_BUFFER_SIZE = 8192;

	private final boolean _autoFlush;
	private final CharsetDecoder _charsetDecoder;
	private final String _charsetName;
	private final ByteBuffer _inputByteBuffer;
	private final CharBuffer _outputCharBuffer;
	private final Writer _writer;

}