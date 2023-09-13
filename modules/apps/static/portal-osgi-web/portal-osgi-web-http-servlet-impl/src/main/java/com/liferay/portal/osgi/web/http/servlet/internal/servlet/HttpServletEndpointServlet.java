/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringPool;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpServletRequestWrapperImpl;

/**
 * @author Dante Wang
 */
public class HttpServletEndpointServlet extends HttpServlet {

	public HttpServletEndpointServlet(
		HttpServletEndpointController httpServletEndpointController,
		ServletConfig servletConfig) {

		_httpServletEndpointController = httpServletEndpointController;
		_servletConfig = servletConfig;
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	@Override
	public void init(ServletConfig servletConfig) {
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String dispatchPathInfo =
			HttpServletRequestWrapperImpl.getDispatchPathInfo(
				httpServletRequest);

		if (dispatchPathInfo == null) {
			dispatchPathInfo = StringPool.SLASH;
		}

		DispatchTargets dispatchTargets =
			_httpServletEndpointController.getDispatchTargets(dispatchPathInfo);

		if ((dispatchTargets != null) &&
			dispatchTargets.doDispatch(
				httpServletRequest, httpServletResponse, dispatchPathInfo,
				httpServletRequest.getDispatcherType())) {

			return;
		}

		httpServletResponse.sendError(
			HttpServletResponse.SC_NOT_FOUND, dispatchPathInfo);
	}

	private final HttpServletEndpointController _httpServletEndpointController;
	private final ServletConfig _servletConfig;

}