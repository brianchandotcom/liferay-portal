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

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

/**
 * <p>
 * See http://issues.liferay.com/browse/LEP-2297.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortalDelegateServlet extends HttpServlet {

	@Override
	public void destroy() {
		PortalDelegatorServlet.removeDelegate(_subContext);
	}

	@Override
	public void init(ServletConfig servletConfig) {
		String servletClass = servletConfig.getInitParameter("servlet-class");

		_subContext = servletConfig.getInitParameter("sub-context");

		if (_subContext == null) {
			_subContext = getServletName();
		}

		try {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			HttpServlet servlet = (HttpServlet)contextClassLoader.loadClass(
				servletClass).newInstance();

			servlet.init(servletConfig);

			PortalDelegatorServlet.addDelegate(_subContext, servlet);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalDelegateServlet.class);

	private String _subContext;

}