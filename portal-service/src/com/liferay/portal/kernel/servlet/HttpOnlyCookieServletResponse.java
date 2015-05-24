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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Samuel Kong
 */
public class HttpOnlyCookieServletResponse extends HttpServletResponseWrapper {

	public static HttpServletResponse getHttpOnlyCookieServletResponse(
		HttpServletResponse response) {

		HttpServletResponse wrappedResponse = response;

		while (wrappedResponse instanceof HttpServletResponseWrapper) {
			if (wrappedResponse instanceof HttpOnlyCookieServletResponse) {
				return response;
			}

			HttpServletResponseWrapper httpServletResponseWrapper =
				(HttpServletResponseWrapper)wrappedResponse;

			wrappedResponse =
				(HttpServletResponse)httpServletResponseWrapper.getResponse();
		}

		return new HttpOnlyCookieServletResponse(response);
	}

	public HttpOnlyCookieServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {
		if (!_cookieHttpOnlyCookieNamesExcludes.contains(cookie.getName())) {
			cookie.setHttpOnly(true);
		}

		super.addCookie(cookie);
	}

	private static final Set<String> _cookieHttpOnlyCookieNamesExcludes =
		SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.COOKIE_HTTP_ONLY_NAMES_EXCLUDES));

}