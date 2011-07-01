/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.PrintWriter;

import java.nio.ByteBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class ByteBufferServletResponse extends HeaderCacheServletResponse {

	public ByteBufferServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public int getBufferSize() {
		if (_byteBuffer != null) {
			return _byteBuffer.remaining();
		}
		else if (_unsyncByteArrayOutputStream != null) {
			return _unsyncByteArrayOutputStream.size();
		}
		else {
			return 0;
		}
	}

	public ByteBuffer getByteBuffer() {
		if (_byteBuffer != null) {
			return _byteBuffer;
		}
		else if (_unsyncByteArrayOutputStream != null) {
			return _unsyncByteArrayOutputStream.unsafeGetByteBuffer();
		}
		else {
			return ByteBuffer.wrap(new byte[0]);
		}
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (_printWriter != null) {
			throw new IllegalStateException(
				"Cannot obtain OutputStream because Writer is already in use");
		}

		if (_servletOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
			_servletOutputStream = new PipingServletOutputStream(
				_unsyncByteArrayOutputStream);
		}

		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() {
		if (_printWriter != null) {
			return _printWriter;
		}

		if (_servletOutputStream != null) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
		_servletOutputStream = new PipingServletOutputStream(
			_unsyncByteArrayOutputStream);
		_printWriter = UnsyncPrintWriterPool.borrow(
			_unsyncByteArrayOutputStream);

		return _printWriter;
	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		_byteBuffer = byteBuffer;
	}

	private ByteBuffer _byteBuffer;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}