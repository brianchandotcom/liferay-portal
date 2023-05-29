/*******************************************************************************
 * Copyright (c) 2014, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.util;

import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceFactory;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServletBundleActivator;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ProxyServlet;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.runtime.HttpServiceRuntime;

/**
 * @author Raymond Augé
 */
public class HttpTuple {

	public HttpTuple(
		ProxyServlet proxyServlet, HttpServiceFactory httpServiceFactory,
		ServiceRegistration<?> httpServiceFactoryServiceRegistration,
		HttpServiceRuntimeImpl httpServiceRuntimeImpl,
		ServiceRegistration<HttpServiceRuntime>
			httpServiceRuntimeServiceRegistration) {

		_proxyServlet = proxyServlet;
		_httpServiceFactoryServiceRegistration =
			httpServiceFactoryServiceRegistration;
		_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
		_httpServiceRuntimeServiceRegistration =
			httpServiceRuntimeServiceRegistration;
	}

	public void destroy() {
		HttpServletBundleActivator.unregisterHttpService(_proxyServlet);

		_proxyServlet.setHttpServiceRuntimeImpl(null);

		_httpServiceFactoryServiceRegistration.unregister();
		_httpServiceRuntimeServiceRegistration.unregister();
		_httpServiceRuntimeImpl.destroy();
	}

	private final ServiceRegistration<?> _httpServiceFactoryServiceRegistration;
	private final HttpServiceRuntimeImpl _httpServiceRuntimeImpl;
	private final ServiceRegistration<HttpServiceRuntime>
		_httpServiceRuntimeServiceRegistration;
	private final ProxyServlet _proxyServlet;

}