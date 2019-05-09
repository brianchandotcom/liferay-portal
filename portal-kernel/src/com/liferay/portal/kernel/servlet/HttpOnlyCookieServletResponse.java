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

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Samuel Kong
 */
public class HttpOnlyCookieServletResponse extends HttpServletResponseWrapper {

	public static HttpServletResponse getHttpOnlyCookieServletResponse(
		HttpServletResponse httpServletResponse) {

		HttpServletResponse wrappedHttpServletResponse = httpServletResponse;

		while (wrappedHttpServletResponse instanceof
					HttpServletResponseWrapper) {

			if (wrappedHttpServletResponse instanceof
					HttpOnlyCookieServletResponse) {

				return httpServletResponse;
			}

			HttpServletResponseWrapper httpServletResponseWrapper =
				(HttpServletResponseWrapper)wrappedHttpServletResponse;

			wrappedHttpServletResponse =
				(HttpServletResponse)httpServletResponseWrapper.getResponse();
		}

		return new HttpOnlyCookieServletResponse(httpServletResponse);
	}

	public HttpOnlyCookieServletResponse(
		HttpServletResponse httpServletResponse) {

		super(httpServletResponse);
	}

	@Override
	public void addCookie(Cookie cookie) {
		if (!_cookieHttpOnlyCookieNamesExcludes.contains(cookie.getName())) {
			cookie.setHttpOnly(true);
		}

		super.addCookie(cookie);
	}

	private static final Set<String> _cookieHttpOnlyCookieNamesExcludes;

	static {
		Set<String> cookieHttpOnlyCookieNamesExcludes = SetUtil.fromArray(
			StringUtil.split(
				SystemProperties.get("cookie.http.only.names.excludes")));

		if (cookieHttpOnlyCookieNamesExcludes.isEmpty()) {
			_cookieHttpOnlyCookieNamesExcludes = Collections.emptySet();
		}
		else {
			_cookieHttpOnlyCookieNamesExcludes =
				cookieHttpOnlyCookieNamesExcludes;
		}
	}

}