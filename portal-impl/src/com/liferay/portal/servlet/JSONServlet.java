/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
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
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		ServletContext servletContext = servletConfig.getServletContext();

		_pluginClassLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		_jsonAction = getJSONAction(servletContext);
	}

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		try {
			AccessControlThreadLocal.setRemoteAccess(true);

			if (_pluginClassLoader == null) {
				_jsonAction.execute(
					null, httpServletRequest, httpServletResponse);
			}
			else {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_pluginClassLoader);

					_jsonAction.execute(
						null, httpServletRequest, httpServletResponse);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}
		}
		catch (IOException ioException) {
			if (!ServletResponseUtil.isClientAbortException(ioException)) {
				throw ioException;
			}
		}
		catch (SecurityException securityException) {
			throw new ServletException(securityException);
		}
		catch (Exception exception) {
			_log.error(exception);
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

	private static final Log _log = LogFactoryUtil.getLog(JSONServlet.class);

	private JSONAction _jsonAction;
	private ClassLoader _pluginClassLoader;

}