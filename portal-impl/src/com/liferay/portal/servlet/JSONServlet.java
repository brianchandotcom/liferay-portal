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

package com.liferay.portal.servlet;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.security.ac.AccessControlThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.struts.JSONAction;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) {
		ServletContext servletContext = servletConfig.getServletContext();

		_pluginClassLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		_jsonAction = getJSONAction(servletContext);
	}

	@Override
	@SuppressWarnings("unused")
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		try {
			AccessControlThreadLocal.setRemoteAccess(true);

			if (_pluginClassLoader == null) {
				_jsonAction.execute(null, null, request, response);
			}
			else {
				ClassLoader contextClassLoader =
					PACLClassLoaderUtil.getContextClassLoader();

				try {
					PACLClassLoaderUtil.setContextClassLoader(
						_pluginClassLoader);

					_jsonAction.execute(null, null, request, response);
				}
				finally {
					PACLClassLoaderUtil.setContextClassLoader(
						contextClassLoader);
				}
			}
		}
		catch (SecurityException se) {
			throw new ServletException(se);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			AccessControlThreadLocal.setRemoteAccess(remoteAccess);
		}
	}

	protected JSONAction getJSONAction(ServletContext servletContext) {
		JSONAction jsonAction = new JSONServiceAction();

		jsonAction.setServletContext(servletContext);

		return jsonAction;
	}

	private static Log _log = LogFactoryUtil.getLog(JSONServlet.class);

	private JSONAction _jsonAction;
	private ClassLoader _pluginClassLoader;

}