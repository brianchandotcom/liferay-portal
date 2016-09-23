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

import com.liferay.portal.kernel.util.InstanceFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 * <p>
 * See https://issues.liferay.com/browse/LEP-2297.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortalDelegateServlet extends SecureServlet {

	@Override
	protected void doPortalDestroy() {
		PortalDelegatorServlet.removeDelegate(_subcontext);

		servlet.destroy();
	}

	@Override
	protected void doPortalInit() throws Exception {
		ServletContext servletContext = servletConfig.getServletContext();

		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		String servletClass = servletConfig.getInitParameter("servlet-class");

		_subcontext = servletConfig.getInitParameter("sub-context");

		if (_subcontext == null) {
			_subcontext = getServletName();
		}

		servlet = (Servlet)InstanceFactory.newInstance(
			classLoader, servletClass);

		if (!(servlet instanceof HttpServlet)) {
			throw new IllegalArgumentException(
				"servlet-class is not an instance of " +
					HttpServlet.class.getName());
		}

		servlet.init(servletConfig);

		PortalDelegatorServlet.addDelegate(_subcontext, (HttpServlet)servlet);
	}

	private String _subcontext;

}