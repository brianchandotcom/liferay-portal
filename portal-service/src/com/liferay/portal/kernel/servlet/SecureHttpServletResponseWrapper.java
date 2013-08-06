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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author László Csontos
 */
public class SecureHttpServletResponseWrapper
	extends HttpServletResponseWrapper {

	public SecureHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {
		String header = createCookieHeader(cookie);
		super.addHeader("Set-Cookie", HttpUtil.sanitizeHeader(header));
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
	public void setCharacterEncoding(String charset) {
		super.setCharacterEncoding(HttpUtil.sanitizeHeader(charset));
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(HttpUtil.sanitizeHeader(type));
	}

	@Override
	public void setHeader(String name, String value) {
		super.setHeader(name, HttpUtil.sanitizeHeader(value));
	}

	private String createCookieHeader(Cookie cookie) {
		String domain = cookie.getDomain();
		boolean httpOnly = true;
		String name = cookie.getName();
		String value = cookie.getValue();
		int maxAge = cookie.getMaxAge();
		String path = cookie.getPath();
		boolean secure = cookie.getSecure();

		if (ArrayUtil.contains(
				_HTTPONLY_IGNORE_COOKIE_NAMES, cookie.getName())) {

			httpOnly = false;
		}

		StringBundler result = new StringBundler();
		result.append(name);
		result.append(StringPool.EQUAL);
		result.append(value);
		result.append("; Max-Age");
		result.append(StringPool.EQUAL);
		result.append(String.valueOf(maxAge));

		if (domain != null) {
			result.append("; Domain");
			result.append(StringPool.EQUAL);
			result.append(domain);
		}

		if (path != null) {
			result.append("; Path");
			result.append(StringPool.EQUAL);
			result.append(path);
		}

		if (secure) {
			result.append("; Secure");
		}

		if (httpOnly) {
			result.append("; HttpOnly");
		}

		return result.toString();
	}

	private static final String[] _HTTPONLY_IGNORE_COOKIE_NAMES =
		PropsUtil.getArray(PropsKeys.HTTPONLY_IGNORE_COOKIE_NAMES);

}