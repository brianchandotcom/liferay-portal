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
 */
public class SecureServletResponseWrapper extends HttpServletResponseWrapper {

	public SecureServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addHeader(String name, String value) {
		super.addHeader(name, HttpUtil.sanitizeHeader(value));
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		super.sendRedirect(HttpUtil.sanitizeHeader(location));
	}

	@Override
	public void setHeader(String name, String value) {
		super.setHeader(name, HttpUtil.sanitizeHeader(value));
	}

}