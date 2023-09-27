/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		if ((subcontext == null) || (delegate == null)) {
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