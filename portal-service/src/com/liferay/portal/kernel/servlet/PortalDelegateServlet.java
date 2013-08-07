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

import com.liferay.portal.kernel.util.InstanceFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 * <p>
 * See http://issues.liferay.com/browse/LEP-2297.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortalDelegateServlet extends SecureServlet {

	@Override
	protected void doPortalDestroy() {
		PortalDelegatorServlet.removeDelegate(_subContext);

		_servlet.destroy();
	}

	@Override
	protected void doPortalInit() throws Exception {
		ServletContext servletContext = _servletConfig.getServletContext();

		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		String servletClass = _servletConfig.getInitParameter("servlet-class");

		_subContext = _servletConfig.getInitParameter("sub-context");

		if (_subContext == null) {
			_subContext = getServletName();
		}

		_servlet = (Servlet)InstanceFactory.newInstance(
			classLoader, servletClass);

		if (!(_servlet instanceof HttpServlet)) {
			throw new IllegalArgumentException(
				"servlet-class is not an instance of " +
					"javax.servlet.http.HttpServlet");
		}

		_servlet.init(_servletConfig);

		PortalDelegatorServlet.addDelegate(_subContext, (HttpServlet)_servlet);
	}

	private String _subContext;

}