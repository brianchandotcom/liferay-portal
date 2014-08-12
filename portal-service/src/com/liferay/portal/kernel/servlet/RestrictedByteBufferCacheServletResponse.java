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

import com.liferay.portal.kernel.io.RestrictedByteArrayCacheOutputStream;
import com.liferay.portal.kernel.io.RestrictedByteArrayCacheOutputStream.FlushPreAction;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.ByteBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class RestrictedByteBufferCacheServletResponse
	extends MetaInfoCacheServletResponse {

	public RestrictedByteBufferCacheServletResponse(
		HttpServletResponse response, int cacheCapacity) {

		super(response);

		_cacheCapacity = cacheCapacity;
	}

	public void flushCache() throws IOException {
		if (_restrictedByteArrayCacheOutputStream != null) {
			_restrictedByteArrayCacheOutputStream.flush();
		}
	}

	@Override
	public int getBufferSize() {
		if (_restrictedByteArrayCacheOutputStream == null) {
			return _cacheCapacity;
		}

		if (_restrictedByteArrayCacheOutputStream.isOverflowed()) {
			return 0;
		}

		return _restrictedByteArrayCacheOutputStream.getCacheCapacity();
	}

	public ByteBuffer getByteBuffer() {
		if (_restrictedByteArrayCacheOutputStream == null) {
			return _emptyByteBuffer;
		}

		return _restrictedByteArrayCacheOutputStream.unsafeGetByteBuffer();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (calledGetWriter) {
			throw new IllegalStateException(
				"Unable to obtain OutputStream because Writer is already in " +
					"use");
		}

		if (_servletOutputStream != null) {
			return _servletOutputStream;
		}

		_restrictedByteArrayCacheOutputStream =
			new RestrictedByteArrayCacheOutputStream(
				super.getOutputStream(), _cacheCapacity,
				new FinishResponseFlushPreAction());

		_servletOutputStream = new ServletOutputStreamAdapter(
			_restrictedByteArrayCacheOutputStream);

		calledGetOutputStream = true;

		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (calledGetOutputStream) {
			throw new IllegalStateException(
				"Unable to obtain Writer because OutputStream is already in " +
					"use");
		}

		if (_printWriter != null) {
			return _printWriter;
		}

		ServletResponse servletResponse = getResponse();

		_restrictedByteArrayCacheOutputStream =
			new RestrictedByteArrayCacheOutputStream(
				servletResponse.getOutputStream(), _cacheCapacity,
				new FinishResponseFlushPreAction());

		_printWriter = UnsyncPrintWriterPool.borrow(
			_restrictedByteArrayCacheOutputStream, getCharacterEncoding());

		calledGetWriter = true;

		return _printWriter;
	}

	public boolean isOverflowed() {
		if (_restrictedByteArrayCacheOutputStream == null) {
			return false;
		}

		return _restrictedByteArrayCacheOutputStream.isOverflowed();
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (isCommitted()) {
			throw new IllegalStateException("Set buffer size after commit");
		}

		// Restricted byte buffer cache response cannot accept buffer size
		// because it has an fixed size internal buffer.

	}

	@Override
	protected void resetBuffer(boolean nullOutReferences) {
		if (nullOutReferences) {
			calledGetOutputStream = false;
			calledGetWriter = false;

			_printWriter = null;
			_servletOutputStream = null;
			_restrictedByteArrayCacheOutputStream = null;
		}
		else if (_restrictedByteArrayCacheOutputStream != null) {
			_restrictedByteArrayCacheOutputStream.reset();
		}
	}

	private static final ByteBuffer _emptyByteBuffer = ByteBuffer.allocate(0);

	private final int _cacheCapacity;
	private PrintWriter _printWriter;
	private RestrictedByteArrayCacheOutputStream
		_restrictedByteArrayCacheOutputStream;
	private ServletOutputStream _servletOutputStream;

	private class FinishResponseFlushPreAction implements FlushPreAction {

		@Override
		public void beforeFlush() throws IOException {
			finishResponse();
		}
	}

}