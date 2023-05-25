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

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Raymond Augé
 */
public class HttpServletResponseWrapperImpl extends HttpServletResponseWrapper {

	public static HttpServletResponseWrapperImpl findHttpRuntimeResponse(
		HttpServletResponse httpServletResponse) {

		while (httpServletResponse instanceof HttpServletResponseWrapper) {
			if (httpServletResponse instanceof HttpServletResponseWrapperImpl) {
				return (HttpServletResponseWrapperImpl)httpServletResponse;
			}

			HttpServletResponseWrapper httpServletResponseWrapper =
				(HttpServletResponseWrapper)httpServletResponse;

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
		if (_status == -1) {
			return super.getStatus();
		}

		return _status;
	}

	@Override
	public boolean isCommitted() {
		if (_status != -1) {
			return true;
		}

		return super.isCommitted();
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

	private String _message;
	private int _status = -1;

}