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

import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Raymond Augé
 */
public class PACLRequestDispatcherWrapper implements RequestDispatcher {

	public PACLRequestDispatcherWrapper(
		ServletContext servletContext, RequestDispatcher requestDispatcher) {

		_servletContext = servletContext;
		_requestDispatcher = requestDispatcher;
	}

	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		doDispatch(servletRequest, servletResponse, false);
	}

	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		doDispatch(servletRequest, servletResponse, true);
	}

	protected void doDispatch(
			ServletRequest servletRequest, ServletResponse servletResponse,
			boolean include)
		throws IOException, ServletException {

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		ClassLoader pluginClassLoader =
			(ClassLoader)_servletContext.getAttribute(
				PluginContextListener.PLUGIN_CLASS_LOADER);

		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			if (pluginClassLoader == null) {
				PortalSecurityManagerThreadLocal.setPACLPolicy(null);

				PACLClassLoaderUtil.setContextClassLoader(
					PACLClassLoaderUtil.getPortalClassLoader());
			}
			else {
				PACLPolicy pluginPACLPolicy = PACLPolicyManager.getPACLPolicy(
					pluginClassLoader);

				PortalSecurityManagerThreadLocal.setPACLPolicy(
					pluginPACLPolicy);

				PACLClassLoaderUtil.setContextClassLoader(pluginClassLoader);
			}

			if (include) {
				_requestDispatcher.include(servletRequest, servletResponse);
			}
			else {
				_requestDispatcher.forward(servletRequest, servletResponse);
			}
		}
		finally {
			PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);

			PortalSecurityManagerThreadLocal.setPACLPolicy(paclPolicy);
		}
	}

	private RequestDispatcher _requestDispatcher;
	private ServletContext _servletContext;

}