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

package com.liferay.portal.lpkg.deployer.internal.wrapperbundle;

import com.liferay.portal.lpkg.deployer.LPKGWarBundleRegistry;

import java.net.URL;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class WarBundleWrapperBundleActivator implements BundleActivator {

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders();

		String contextName = headers.get("wab-contextName");

		if (contextName == null) {
			throw new IllegalArgumentException(
				"Missing wab-contextName header");
		}

		String lpkgURLString = headers.get("wab-lpkg-url");

		if (lpkgURLString == null) {
			throw new IllegalArgumentException("Missing wab-lpkg-url header");
		}

		String startLevelString = headers.get("wab-startLevel");

		if (startLevelString == null) {
			throw new IllegalArgumentException("Missing wab-startLevel header");
		}

		int startLevel = Integer.parseInt(startLevelString);

		LPKGWarBundleRegistry lpkgWarBundleRegistry = bundleContext.getService(
			bundleContext.getServiceReference(LPKGWarBundleRegistry.class));

		// Defer war bundle installation until wab protocol handler is ready.

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(objectClass=" + URLStreamHandlerService.class.getName() +
					")(" + URLConstants.URL_HANDLER_PROTOCOL + "=webbundle))"),
			new URLStreamHandlerServiceServiceTrackerCustomizer(
				bundleContext, contextName, new URL(lpkgURLString), startLevel,
				lpkgWarBundleRegistry));

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	private ServiceTracker<URLStreamHandlerService, Bundle> _serviceTracker;

}