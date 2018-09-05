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

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;

/**
 * @author Tina Tian
 */
public class ReaderInputStream extends InputStream {

	public ReaderInputStream(Reader reader) {
		this(
			reader, StringPool.UTF8, _DEFAULT_INTPUT_BUFFER_SIZE,
			_DEFAULT_OUTPUT_BUFFER_SIZE);
	}

	public ReaderInputStream(Reader reader, String charsetName) {
		this(
			reader, charsetName, _DEFAULT_INTPUT_BUFFER_SIZE,
			_DEFAULT_OUTPUT_BUFFER_SIZE);
	}

	public ReaderInputStream(
		Reader reader, String charsetName, int inputBufferSize,
		int outputBufferSize) {

		_reader = reader;
		_charsetName = charsetName;

		if (inputBufferSize <= 0) {
			throw new IllegalArgumentException(
				"Input buffer size " + inputBufferSize +
					" must be a positive number");
		}

		_inputBuffer = CharBuffer.allocate(inputBufferSize);

		_charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(charsetName);

		_maxBytesPerChar = (int)Math.ceil(_charsetEncoder.maxBytesPerChar());

		if (outputBufferSize < _maxBytesPerChar) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Output buffer size ", String.valueOf(outputBufferSize),
					" is less than ", String.valueOf(_maxBytesPerChar)));
		}

		_outputBuffer = ByteBuffer.allocate(outputBufferSize);

		_outputBuffer.flip();
	}

	@Override
	public int available() {
		return _outputBuffer.remaining() + _inputBuffer.position();
	}

	@Override
	public void close() throws IOException {
		if (_inputBuffer != null) {
			_inputBuffer.clear();

			_inputBuffer = null;
		}

		if (_outputBuffer != null) {
			_outputBuffer.clear();

			_outputBuffer = null;
		}

		_reader.close();
	}

	public String getEncoding() {
		return _charsetName;
	}

	@Override
	public int read() throws IOException {
		byte[] bytes = new byte[1];

		int result = read(bytes, 0, 1);

		if (result == 1) {
			return bytes[0];
		}

		return -1;
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (bytes == null) {
			throw new NullPointerException();
		}
		else if ((offset < 0) || (length < 0) ||
				 (length > (bytes.length - offset))) {

			throw new IndexOutOfBoundsException();
		}
		else if (length == 0) {
			return 0;
		}

		int originalLength = length;

		while (length > 0) {
			int blockSize = Math.min(_outputBuffer.remaining(), length);

			if (blockSize > 0) {
				_outputBuffer.get(bytes, offset, blockSize);

				length -= blockSize;
				offset += blockSize;

				if (length == 0) {
					break;
				}
			}

			int inputPosition = _inputBuffer.position();

			int result = _reader.read(
				_inputBuffer.array(), inputPosition, _inputBuffer.remaining());

			if (result != -1) {
				_inputBuffer.position(inputPosition + result);
			}

			_inputBuffer.flip();

			int inputRemaining = _inputBuffer.remaining();

			if (inputRemaining <= 0) {
				break;
			}

			if ((inputRemaining * _maxBytesPerChar) < length) {
				ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, offset, length);

				_charsetEncoder.encode(_inputBuffer, byteBuffer, true);

				int outputRemaining = byteBuffer.remaining();

				offset += length - outputRemaining;
				length = outputRemaining;
			}
			else {
				_outputBuffer.clear();

				_charsetEncoder.encode(_inputBuffer, _outputBuffer, true);

				_outputBuffer.flip();
			}

			_inputBuffer.compact();
		}

		int result = originalLength - length;

		if (result == 0) {
			return -1;
		}

		return result;
	}

	@Override
	public long skip(long length) throws IOException {
		if (length < 0) {
			throw new IllegalArgumentException();
		}

		long originalLength = length;

		while (length > 0) {
			int blockSize = (int)Math.min(_outputBuffer.remaining(), length);

			if (blockSize > 0) {
				_outputBuffer.position(_outputBuffer.position() + blockSize);

				length -= blockSize;

				if (length == 0) {
					break;
				}
			}

			int inputPosition = _inputBuffer.position();

			int result = _reader.read(
				_inputBuffer.array(), inputPosition, _inputBuffer.remaining());

			if (result != -1) {
				_inputBuffer.position(inputPosition + result);
			}

			_inputBuffer.flip();

			if (_inputBuffer.remaining() <= 0) {
				break;
			}

			_outputBuffer.clear();

			_charsetEncoder.encode(_inputBuffer, _outputBuffer, true);

			_outputBuffer.flip();

			_inputBuffer.compact();
		}

		return originalLength - length;
	}

	private static final int _DEFAULT_INTPUT_BUFFER_SIZE = 128;

	private static final int _DEFAULT_OUTPUT_BUFFER_SIZE = 1024;

	private final CharsetEncoder _charsetEncoder;
	private final String _charsetName;
	private CharBuffer _inputBuffer;
	private final int _maxBytesPerChar;
	private ByteBuffer _outputBuffer;
	private final Reader _reader;

}