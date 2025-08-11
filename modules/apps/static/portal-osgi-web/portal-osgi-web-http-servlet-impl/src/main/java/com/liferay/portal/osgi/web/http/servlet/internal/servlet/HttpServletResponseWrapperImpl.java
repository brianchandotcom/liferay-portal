/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

/**
 * @author Dante Wang
 */
public class HttpServletResponseWrapperImpl extends HttpServletResponseWrapper {

	public static HttpServletResponseWrapperImpl findHttpRuntimeResponse(
		HttpServletResponse httpServletResponse) {

		while (httpServletResponse instanceof
					HttpServletResponseWrapper httpServletResponseWrapper) {

			if (httpServletResponseWrapper instanceof
					HttpServletResponseWrapperImpl
						httpServletResponseWrapperImpl) {

				return httpServletResponseWrapperImpl;
			}

			httpServletResponse =
				(HttpServletResponse)httpServletResponseWrapper.getResponse();
		}

		return null;
	}

	public HttpServletResponseWrapperImpl(
		HttpServletResponse httpServletResponse) {

		super(httpServletResponse);
	}

	@Override
	public void flushBuffer() throws IOException {
		if (_status != -1) {
			HttpServletResponse httpServletResponse =
				(HttpServletResponse)getResponse();

			httpServletResponse.sendError(_status, getMessage());
		}

		super.flushBuffer();
	}

	public int getInternalStatus() {
		return _status;
	}

	public String getMessage() {
		return _message;
	}

	@Override
	public int getStatus() {
		if (_status != -1) {
			return _status;
		}

		return super.getStatus();
	}

	@Override
	public boolean isCommitted() {
		if (_status != -1) {
			return true;
		}

		return super.isCommitted();
	}

	public boolean isCompleted() {
		return _completed;
	}

	@Override
	public void sendError(int status) {
		_status = status;
	}

	@Override
	public void sendError(int status, String message) {
		_status = status;
		_message = message;
	}

	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	private boolean _completed;
	private String _message;
	private int _status = -1;

	private class InternalServletOutputStream extends ServletOutputStream {

		public InternalServletOutputStream(
			ServletOutputStream originalServletOutputStream) {

			_originalServletOutputStream = originalServletOutputStream;
		}

		@Override
		public void close() throws IOException {
			_originalServletOutputStream.close();
		}

		@Override
		public void flush() throws IOException {
			if (getInternalStatus() != -1) {
				HttpServletResponse httpServletResponse =
					(HttpServletResponse)getResponse();

				httpServletResponse.sendError(
					getInternalStatus(), getMessage());
			}

			_originalServletOutputStream.flush();
		}

		@Override
		public boolean isReady() {
			return _originalServletOutputStream.isReady();
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			_originalServletOutputStream.setWriteListener(writeListener);
		}

		@Override
		public void write(byte[] bytes) throws IOException {
			if (!isCompleted()) {
				_originalServletOutputStream.write(bytes);
			}
		}

		@Override
		public void write(byte[] bytes, int offset, int length)
			throws IOException {

			if (!isCompleted()) {
				_originalServletOutputStream.write(bytes, offset, length);
			}
		}

		@Override
		public void write(int b) throws IOException {
			if (!isCompleted()) {
				_originalServletOutputStream.write(b);
			}
		}

		private final ServletOutputStream _originalServletOutputStream;

	}

}