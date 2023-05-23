/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.servlet;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Raymond Augé
 */
public class HttpServletResponseWrapperImpl extends HttpServletResponseWrapper {

	public HttpServletResponseWrapperImpl(HttpServletResponse response) {
		super(response);
	}

	@Override
	public boolean isCommitted() {
		if (this.status != -1) {
			return true;
		}

		return super.isCommitted();
	}

	@Override
	public void sendError(int status) {
		this.status = status;
	}

	@Override
	public void sendError(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int getStatus() {
		if (status == -1) {
			return super.getStatus();
		}
		return status;
	}

	public int getInternalStatus() {
		return status;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (status != -1) {
			HttpServletResponse wrappedResponse = (HttpServletResponse)this.getResponse();
			wrappedResponse.sendError(status, getMessage());
		}
		super.flushBuffer();
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public static HttpServletResponseWrapperImpl findHttpRuntimeResponse(
			HttpServletResponse response) {
		while (response instanceof HttpServletResponseWrapper) {
			if (response instanceof HttpServletResponseWrapperImpl) {
				return (HttpServletResponseWrapperImpl)response;
			}
			response = (HttpServletResponse)((HttpServletResponseWrapper)response).getResponse();
		}
		return null;
	}

	private int status = -1;
	private String message;
	private boolean completed;

	private class InternalOutputStream extends ServletOutputStream {
		public InternalOutputStream(ServletOutputStream originalOutputStream) {
			this.originalOutputStream = originalOutputStream;
		}
		@Override
		public void close() throws IOException {
			originalOutputStream.close();
		}

		@Override
		public void flush() throws IOException {
			if (getInternalStatus() != -1) {
				HttpServletResponse wrappedResponse = (HttpServletResponse) HttpServletResponseWrapperImpl.this.getResponse();
				wrappedResponse.sendError(getInternalStatus(), getMessage());
			}
			originalOutputStream.flush();
		}

		@Override
		public boolean isReady() {
			return originalOutputStream.isReady();
		}
		@Override
		public void setWriteListener(WriteListener writeListener) {
			originalOutputStream.setWriteListener(writeListener);
		}
		@Override
		public void write(int b) throws IOException {
			if (isCompleted()) {
				return;
			}
			originalOutputStream.write(b);
		}
		@Override
		public void write(byte[] b) throws IOException {
			if (isCompleted()) {
				return;
			}
			originalOutputStream.write(b);
		}
		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			if (isCompleted()) {
				return;
			}
			originalOutputStream.write(b, off, len);
		}
		private final ServletOutputStream originalOutputStream;
	}

}