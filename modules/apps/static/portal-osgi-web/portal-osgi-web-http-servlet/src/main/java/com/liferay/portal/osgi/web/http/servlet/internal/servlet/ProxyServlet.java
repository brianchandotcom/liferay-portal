/*******************************************************************************
 * Copyright (c) 2005, 2014 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServletBundleActivator;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class ProxyServlet extends HttpServlet {

	@Override
	public void destroy() {
		HttpServletBundleActivator.unregisterHttpService(this);

		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		HttpServletBundleActivator.addProxyServlet(this);
	}

	public void setHttpServiceRuntimeImpl(
		HttpServiceRuntimeImpl httpServiceRuntimeImpl) {

		_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		_checkRuntime();

		String alias = HttpServletRequestWrapperImpl.getDispatchPathInfo(
			httpServletRequest);

		if (alias == null) {
			alias = Const.SLASH;
		}

		if (_httpServiceRuntimeImpl.doDispatch(
				httpServletRequest, httpServletResponse, alias)) {

			return;
		}

		httpServletResponse.sendError(
			HttpServletResponse.SC_NOT_FOUND, "ProxyServlet: " + alias);
	}

	private void _checkRuntime() {
		if (_httpServiceRuntimeImpl == null) {
			throw new IllegalStateException(
				"Proxy servlet not properly initialized. " +
					"httpServiceRuntimeImpl is null");
		}
	}

	private static final long serialVersionUID = 4117456123807468871L;

	private HttpServiceRuntimeImpl _httpServiceRuntimeImpl;

}