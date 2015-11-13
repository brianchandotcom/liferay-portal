/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cxf.jaxrs.common.activator;

import java.util.Collection;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.Bus;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Carlos Sierra Andrés
 */
public class CXFJaxRsBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

		thread.setContextClassLoader(bundleClassLoader);

		try {

			// Initialize instance so it is never looked up again

			RuntimeDelegate.getInstance();
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

		// Clean up the cached instance so a new bundle can its place

		Collection<ServiceReference<Bus>> serviceReferences =
			bundleContext.getServiceReferences(Bus.class, null);

		for (ServiceReference<Bus> serviceReference : serviceReferences) {
			Bus bus = bundleContext.getService(serviceReference);

			bus.setProperty("jaxrs.shared.server.factory", null);
		}
	}

}