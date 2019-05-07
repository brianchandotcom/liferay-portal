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

import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * See https://issues.liferay.com/browse/LEP-2297.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortalDelegatorServlet extends HttpServlet {

	public static void addDelegate(String subcontext, HttpServlet delegate) {
		if (subcontext == null) {
			throw new IllegalArgumentException();
		}

		if (delegate == null) {
			throw new IllegalArgumentException();
		}

		_delegates.put(subcontext, delegate);
	}

	public static void removeDelegate(String subcontext) {
		if (subcontext == null) {
			throw new IllegalArgumentException();
		}

		_delegates.remove(subcontext);
	}

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String uri = httpServletRequest.getPathInfo();

		if ((uri == null) || (uri.length() == 0)) {
			httpServletResponse.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				"Path information is not specified");

			return;
		}

		String[] paths = uri.split(StringPool.SLASH);

		if (paths.length < 2) {
			httpServletResponse.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				"Path " + uri + " is invalid");

			return;
		}

		HttpServlet delegate = _delegates.get(paths[1]);

		if (delegate == null) {
			httpServletResponse.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				"No servlet registred for context " + paths[1]);

			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<?> clazz = delegate.getClass();

			ClassLoader delegateClassLoader = clazz.getClassLoader();

			currentThread.setContextClassLoader(delegateClassLoader);

			delegate.service(httpServletRequest, httpServletResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final Map<String, HttpServlet> _delegates = new HashMap<>();

}