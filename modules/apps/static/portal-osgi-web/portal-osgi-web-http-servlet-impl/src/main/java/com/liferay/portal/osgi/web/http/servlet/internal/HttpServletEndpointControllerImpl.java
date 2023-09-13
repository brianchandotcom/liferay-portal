/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.eclipse.equinox.http.servlet.internal.HttpServiceRuntimeImpl;
import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Dante Wang
 */
public class HttpServletEndpointControllerImpl
	implements HttpServletEndpointController {

	public HttpServletEndpointControllerImpl(
		BundleContext bundleContext, ServletContext servletContext,
		Map<String, Object> attributesMap) {

		_equinoxHttpServletEndpointController = new HttpServiceRuntimeImpl(
			bundleContext, bundleContext, servletContext, attributesMap);
	}

	@Override
	public void destroy() {
		_equinoxHttpServletEndpointController.destroy();
	}

	@Override
	public Collection<ContextController> getContextControllers() {
		return _equinoxHttpServletEndpointController.getContextControllers();
	}

	@Override
	public DispatchTargets getDispatchTargets(String s) {
		return _equinoxHttpServletEndpointController.getDispatchTargets(s);
	}

	@Override
	public List<String> getHttpServiceEndpoints() {
		return _equinoxHttpServletEndpointController.getHttpServiceEndpoints();
	}

	@Override
	public ServletContext getParentServletContext() {
		return _equinoxHttpServletEndpointController.getParentServletContext();
	}

	@Override
	public Set<Object> getRegisteredObjects() {
		return _equinoxHttpServletEndpointController.getRegisteredObjects();
	}

	@Override
	public void log(String s, Throwable throwable) {
		_equinoxHttpServletEndpointController.log(s, throwable);
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		return _equinoxHttpServletEndpointController.matches(serviceReference);
	}

	private final HttpServletEndpointController
		_equinoxHttpServletEndpointController;

}