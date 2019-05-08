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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.nio.CharsetDecoderUtil;
import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class BufferCacheServletResponse extends MetaInfoCacheServletResponse {

	public BufferCacheServletResponse(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
	}

	/**
	 * This method is very expensive when used in char mode because it has to
	 * encode every char to byte in order to calculate the final byte size.
	 *
	 * @return used buffer size in byte.
	 */
	@Override
	public int getBufferSize() {
		if (_byteBuffer != null) {
			return _byteBuffer.limit();
		}

		if (_charBuffer != null) {
			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				getCharacterEncoding(), _charBuffer.duplicate());

			return byteBuffer.limit();
		}

		try {
			_flushInternalBuffer();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		if (_unsyncByteArrayOutputStream != null) {
			return _unsyncByteArrayOutputStream.size();
		}

		if (_unsyncStringWriter != null) {
			String content = _unsyncStringWriter.toString();

			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				getCharacterEncoding(), CharBuffer.wrap(content));

			return byteBuffer.limit();
		}

		return 0;
	}

	/**
	 * In char mode, this method will encode every char to byte using the
	 * character set from {@link #getCharacterEncoding()}. Generally, this
	 * method should be called after the last text based post-processing.
	 * Otherwise, you may need decode the byte back to char again which will
	 * result in a lot of unnecessary CPU overhead.
	 */
	public ByteBuffer getByteBuffer() throws IOException {
		if (_byteBuffer != null) {
			return _byteBuffer;
		}

		if (_charBuffer != null) {
			return CharsetEncoderUtil.encode(
				getCharacterEncoding(), _charBuffer.duplicate());
		}

		_flushInternalBuffer();

		if (_unsyncByteArrayOutputStream != null) {
			return _unsyncByteArrayOutputStream.unsafeGetByteBuffer();
		}

		if (_unsyncStringWriter != null) {
			String content = _unsyncStringWriter.toString();

			return CharsetEncoderUtil.encode(
				getCharacterEncoding(), CharBuffer.wrap(content));
		}

		return _emptyByteBuffer;
	}

	/**
	 * In char mode, this method will encode every byte to char using the
	 * character set from {@link #getCharacterEncoding()}. Make sure the byte
	 * data is actually encoded chars. Otherwise, the decoding will generate
	 * meaningless data, or worse, even encode the output back and the exact
	 * same character set may not able to retrieve the exact same byte data. For
	 * example, decode an image byte data to char, then encode the chars back to
	 * byte with same character set, will most likely create a corrupted image.
	 */
	public CharBuffer getCharBuffer() throws IOException {
		if (_charBuffer != null) {
			return _charBuffer;
		}

		if (_byteBuffer != null) {
			return CharsetDecoderUtil.decode(
				getCharacterEncoding(), _byteBuffer.duplicate());
		}

		_flushInternalBuffer();

		if (_unsyncStringWriter != null) {
			return CharBuffer.wrap(_unsyncStringWriter.toString());
		}

		if (_unsyncByteArrayOutputStream != null) {
			ByteBuffer byteBuffer =
				_unsyncByteArrayOutputStream.unsafeGetByteBuffer();

			return CharsetDecoderUtil.decode(
				getCharacterEncoding(), byteBuffer);
		}

		return _emptyCharBuffer;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (calledGetWriter) {
			throw new IllegalStateException(
				"Unable to obtain OutputStream because Writer is already in " +
					"use");
		}

		if (_servletOutputStream != null) {
			return _servletOutputStream;
		}

		resetBuffer(true);

		_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		_servletOutputStream = new ServletOutputStreamAdapter(
			_unsyncByteArrayOutputStream);

		calledGetOutputStream = true;

		return _servletOutputStream;
	}

	/**
	 * @see #getCharBuffer()
	 */
	public String getString() throws IOException {
		if (_charBuffer != null) {
			return _charBuffer.toString();
		}

		if (_byteBuffer != null) {
			CharBuffer charBuffer = CharsetDecoderUtil.decode(
				getCharacterEncoding(), _byteBuffer.duplicate());

			return charBuffer.toString();
		}

		_flushInternalBuffer();

		if (_unsyncStringWriter != null) {
			return _unsyncStringWriter.toString();
		}

		if (_unsyncByteArrayOutputStream != null) {
			ByteBuffer byteBuffer =
				_unsyncByteArrayOutputStream.unsafeGetByteBuffer();

			CharBuffer charBuffer = CharsetDecoderUtil.decode(
				getCharacterEncoding(), byteBuffer);

			return charBuffer.toString();
		}

		return StringPool.BLANK;
	}

	/**
	 * @see #getCharBuffer()
	 */
	public StringBundler getStringBundler() throws IOException {
		if (_charBuffer != null) {
			StringBundler sb = new StringBundler(1);

			sb.append(_charBuffer.toString());

			return sb;
		}

		if (_byteBuffer != null) {
			CharBuffer charBuffer = CharsetDecoderUtil.decode(
				getCharacterEncoding(), _byteBuffer.duplicate());

			StringBundler sb = new StringBundler(1);

			sb.append(charBuffer.toString());

			return sb;
		}

		_flushInternalBuffer();

		if (_unsyncStringWriter != null) {
			return _unsyncStringWriter.getStringBundler();
		}

		if (_unsyncByteArrayOutputStream != null) {
			ByteBuffer byteBuffer =
				_unsyncByteArrayOutputStream.unsafeGetByteBuffer();

			CharBuffer charBuffer = CharsetDecoderUtil.decode(
				getCharacterEncoding(), byteBuffer);

			StringBundler sb = new StringBundler(1);

			sb.append(charBuffer.toString());

			return sb;
		}

		return new StringBundler(1);
	}

	@Override
	public PrintWriter getWriter() {
		if (calledGetOutputStream) {
			throw new IllegalStateException(
				"Unable to obtain Writer because OutputStream is already in " +
					"use");
		}

		if (_printWriter != null) {
			return _printWriter;
		}

		resetBuffer(true);

		_unsyncStringWriter = new UnsyncStringWriter();

		_printWriter = UnsyncPrintWriterPool.borrow(_unsyncStringWriter);

		calledGetWriter = true;

		return _printWriter;
	}

	public boolean isByteMode() {
		if ((_byteBuffer != null) || (_unsyncByteArrayOutputStream != null)) {
			return true;
		}

		return false;
	}

	public boolean isCharMode() {
		if ((_charBuffer != null) || (_unsyncStringWriter != null)) {
			return true;
		}

		return false;
	}

	public void outputBuffer() throws IOException {
		_flushInternalBuffer();

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)getResponse();

		if ((_byteBuffer != null) || calledGetOutputStream) {
			ServletResponseUtil.write(httpServletResponse, getByteBuffer());
		}
		else if ((_charBuffer != null) || calledGetWriter) {
			ServletResponseUtil.write(httpServletResponse, getCharBuffer());
		}
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (isCommitted()) {
			throw new IllegalStateException("Set buffer size after commit");
		}

		// Buffered response cannot accept buffer size because it has an
		// internal buffer that grows as needed

	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		resetBuffer(true);

		_byteBuffer = byteBuffer;

		if (byteBuffer != null) {
			_servletOutputStream = new ServletOutputStreamAdapter(
				new DummyOutputStream());

			calledGetOutputStream = true;
		}
	}

	public void setCharBuffer(CharBuffer charBuffer) {
		resetBuffer(true);

		_charBuffer = charBuffer;

		if (charBuffer != null) {
			_printWriter = UnsyncPrintWriterPool.borrow(new DummyWriter());

			calledGetWriter = true;
		}
	}

	@Override
	public void setContentLength(int contentLength) {

		// Buffered response cannot accept content length because content post
		// processing may cause length change

	}

	@Override
	public void setContentLengthLong(long contentLength) {

		// Buffered response cannot accept content length because content post
		// processing may cause length change

	}

	public void setString(String string) {
		setCharBuffer(CharBuffer.wrap(string));
	}

	@Override
	protected void resetBuffer(boolean nullOutReferences) {
		if (nullOutReferences) {
			calledGetOutputStream = false;
			calledGetWriter = false;

			_printWriter = null;
			_servletOutputStream = null;
			_unsyncByteArrayOutputStream = null;
			_unsyncStringWriter = null;
		}
		else {
			if (_unsyncByteArrayOutputStream != null) {
				_unsyncByteArrayOutputStream.reset();
			}

			if (_unsyncStringWriter != null) {
				_unsyncStringWriter.reset();
			}

			if (_byteBuffer != null) {
				_servletOutputStream = null;

				calledGetOutputStream = false;
			}

			if (_charBuffer != null) {
				_printWriter = null;

				calledGetWriter = false;
			}
		}

		_byteBuffer = null;
		_charBuffer = null;
	}

	private void _flushInternalBuffer() throws IOException {
		if (calledGetOutputStream) {
			_servletOutputStream.flush();
		}
		else if (calledGetWriter) {
			_printWriter.flush();
		}
	}

	private static final ByteBuffer _emptyByteBuffer = ByteBuffer.allocate(0);
	private static final CharBuffer _emptyCharBuffer = CharBuffer.allocate(0);

	private ByteBuffer _byteBuffer;
	private CharBuffer _charBuffer;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;
	private UnsyncStringWriter _unsyncStringWriter;

}