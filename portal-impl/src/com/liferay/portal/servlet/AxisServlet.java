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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class AxisServlet extends com.liferay.util.axis.AxisServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();

		_portletClassLoader = (ClassLoader)servletContext.getAttribute(
			PortletServlet.PORTLET_CLASS_LOADER);

		if (_portletClassLoader == null) {
			super.init(servletConfig);
		}
		else {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(_portletClassLoader);

				super.init(servletConfig);
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			PortalInstances.getCompanyId(request);

			String remoteUser = request.getRemoteUser();

			if (_log.isDebugEnabled()) {
				_log.debug("Remote user " + remoteUser);
			}

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				User user = UserLocalServiceUtil.getUserById(userId);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}

			if (_portletClassLoader == null) {
				super.service(request, response);
			}
			else {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_portletClassLoader);

					super.service(request, response);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AxisServlet.class);

	private ClassLoader _portletClassLoader;

}