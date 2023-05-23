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
package org.eclipse.equinox.http.servlet.internal.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.eclipse.equinox.http.servlet.internal.Activator;
import org.eclipse.equinox.http.servlet.internal.HttpServiceRuntimeImpl;
import org.eclipse.equinox.http.servlet.internal.util.Const;

/**
 * The ProxyServlet is the private side of a Servlet that when registered (and init() called) in a servlet container
 * will in-turn register and provide an OSGi Http Service implementation.
 * This class is not meant for extending or even using directly and is purely meant for registering
 * in a servlet container.
 */
public class ProxyServlet extends HttpServlet {

	private static final long serialVersionUID = 4117456123807468871L;
	private HttpServiceRuntimeImpl httpServiceRuntimeImpl;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		Activator.addProxyServlet(this);
	}

	public void destroy() {
		Activator.unregisterHttpService(this);

		super.destroy();
	}

	public void setHttpServiceRuntimeImpl(
		HttpServiceRuntimeImpl httpServiceRuntimeImpl) {

		this.httpServiceRuntimeImpl = httpServiceRuntimeImpl;
	}

	public void sessionIdChanged(String oldSessionId) {
		httpServiceRuntimeImpl.fireSessionIdChanged(oldSessionId);
	}

	/**
	 * @see HttpServlet#service(ServletRequest, ServletResponse)
	 */
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		checkRuntime();

		String alias = HttpServletRequestWrapperImpl.getDispatchPathInfo(request);

		if (alias == null) {
			alias = Const.SLASH;
		}

		if (httpServiceRuntimeImpl.doDispatch(request, response, alias)) {
			return;
		}

		response.sendError(
			HttpServletResponse.SC_NOT_FOUND, "ProxyServlet: " + alias);
	}

	private void checkRuntime() {
		if (httpServiceRuntimeImpl == null) {
			throw new IllegalStateException(
				"Proxy servlet not properly initialized. " +
					"httpServiceRuntimeImpl is null");
		}
	}

}
