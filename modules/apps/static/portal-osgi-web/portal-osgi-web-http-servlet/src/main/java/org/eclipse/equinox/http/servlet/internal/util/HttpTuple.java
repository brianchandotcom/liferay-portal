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

package org.eclipse.equinox.http.servlet.internal.util;

import org.eclipse.equinox.http.servlet.internal.*;
import org.eclipse.equinox.http.servlet.internal.servlet.ProxyServlet;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.runtime.HttpServiceRuntime;

public class HttpTuple {

	public HttpTuple(
		ProxyServlet proxyServlet,
		HttpServiceFactory httpServiceFactory,
		ServiceRegistration<?> hsfRegistration,
		HttpServiceRuntimeImpl httpServiceRuntime,
		ServiceRegistration<HttpServiceRuntime> hsrRegistration) {

		this.proxyServlet = proxyServlet;
		this.httpServiceFactory = httpServiceFactory;
		this.hsfRegistration = hsfRegistration;
		this.httpServiceRuntime = httpServiceRuntime;
		this.hsrRegistration = hsrRegistration;
	}

	public void destroy() {
		Activator.unregisterHttpService(proxyServlet);
		proxyServlet.setHttpServiceRuntimeImpl(null);
		hsfRegistration.unregister();
		hsrRegistration.unregister();
		httpServiceRuntime.destroy();
	}

	final HttpServiceFactory httpServiceFactory;
	final ServiceRegistration<?> hsfRegistration;
	final HttpServiceRuntimeImpl httpServiceRuntime;
	final ServiceRegistration<HttpServiceRuntime> hsrRegistration;
	final ProxyServlet proxyServlet;

}