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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.HttpUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author László Csontos
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class SecureHttpServletResponseWrapper
	extends HttpServletResponseWrapper {

	public SecureHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addHeader(String name, String value) {
		if (_sanitizeHeaders) {
			super.addHeader(
				HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
		}
		else {
			super.addHeader(name, value);
		}
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (_sanitizeHeaders) {
			super.sendRedirect(HttpUtil.sanitizeHeader(location));
		}
		else {
			super.sendRedirect(location);
		}
	}

	@Override
	public void setCharacterEncoding(String charset) {
		if (_sanitizeHeaders) {
			super.setCharacterEncoding(HttpUtil.sanitizeHeader(charset));
		}
		else {
			super.setCharacterEncoding(charset);
		}
	}

	@Override
	public void setContentType(String type) {
		if (_sanitizeHeaders) {
			super.setContentType(HttpUtil.sanitizeHeader(type));
		}
		else {
			super.setContentType(type);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		if (_sanitizeHeaders) {
			super.setHeader(
				HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
		}
		else {
			super.setHeader(name, value);
		}
	}

	public void setSanitizeHeaders(boolean sanitizeHeaders) {
		this._sanitizeHeaders = sanitizeHeaders;
	}

	private boolean _sanitizeHeaders;

}