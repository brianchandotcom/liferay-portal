/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

/**
 * @author Shuyang Zhou
 */
public class DirectRequestDispatcherUtil {

	public static RequestDispatcher getRequestDispatcher(
		ServletContext servletContext, String path) {

		if (!_DIRECT_SERVLET_CONTEXT_ENABLED) {
			return servletContext.getRequestDispatcher(path);
		}

		if ((path == null) || (path.length() == 0)) {
			return null;
		}

		if (path.charAt(0) != CharPool.SLASH) {
			throw new IllegalArgumentException(
				"path is not relative to context root : " + path);
		}

		String contextPath = ContextPathUtil.getContextPath(servletContext);

		String fullPath = contextPath.concat(path);
		String queryString = null;

		int pos = fullPath.indexOf(CharPool.QUESTION);

		if (pos != -1) {
			queryString = fullPath.substring(pos + 1);
			fullPath = fullPath.substring(0, pos);
		}

		Servlet servlet = DirectServletRegistry.getServlet(fullPath);

		if (servlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No servlet found for " + fullPath);
			}

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(path);

			return new DirectServletPathRegisterDispatcher(
				path, requestDispatcher);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Servlet found for " + fullPath);
			}

			return new DirectRequestDispatcher(servlet, queryString);
		}
	}

	public static RequestDispatcher getRequestDispatcher(
		ServletRequest servletRequest, String path) {

		if (!_DIRECT_SERVLET_CONTEXT_ENABLED) {
			return servletRequest.getRequestDispatcher(path);
		}

		ServletContext servletContext =
			(ServletContext)servletRequest.getAttribute(WebKeys.CTX);

		if (servletContext == null) {
			throw new IllegalStateException(
				"Can not find ServletContext in request attributes");
		}

		return getRequestDispatcher(servletContext, path);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DirectRequestDispatcherUtil.class);

	static private final boolean _DIRECT_SERVLET_CONTEXT_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

}