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

package com.liferay.deploy.hot.internal.tracker;

import com.liferay.deploy.hot.internal.handlers.ApplicationsHandler;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This Service tracker tries to mimic the behaviour of our current hot deploy
 * mechanism, but removing the dependencies with the Servlet Specs
 *
 * This is not the final solution: the next step will be removing the
 * auto-deploy and hot-deploy mechanisms, so we can decouple "service only"
 * applications from the Servlet API (mainly the ServletContext dependency).
 *
 * The newer code will fit within this module but the #HotDeployBundleListener
 * will be removed from the source code
 *
 * @author Miguel Pastor
 */
public class ServletContextTracker
	extends ServiceTracker<ServletContext, ServletContext> {

	public ServletContextTracker(BundleContext bundleContext) {
		super(bundleContext, ServletContext.class, null);
	}

	@Override
	public ServletContext addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		if (bundle.getBundleId() == 0) {
			return null;
		}

		ServletContext servletContext = super.addingService(serviceReference);

		_applicationsHandler.registerApplication(bundle, servletContext);

		return servletContext;
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServletContext servletContext) {

		Bundle bundle = serviceReference.getBundle();

		if (bundle.getBundleId() == 0) {
			return;
		}

		_applicationsHandler.unregisterApplication(bundle, servletContext);

		super.removedService(serviceReference, servletContext);
	}

	private ApplicationsHandler _applicationsHandler =
		ApplicationsHandler.getInstance();

}