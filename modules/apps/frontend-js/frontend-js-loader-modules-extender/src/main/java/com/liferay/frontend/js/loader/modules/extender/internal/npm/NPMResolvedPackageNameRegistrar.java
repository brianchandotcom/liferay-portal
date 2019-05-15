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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.petra.string.StringBundler;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class NPMResolvedPackageNameRegistrar
	implements ServiceTrackerCustomizer<ServletContext, ServletContext> {

	public NPMResolvedPackageNameRegistrar(
			BundleContext bundleContext, Bundle bundle,
			String npmResolvedPackageName)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;
		_npmResolvedPackageName = npmResolvedPackageName;

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, _getServletContextFilter(bundle), this);
	}

	@Override
	public ServletContext addingService(
		ServiceReference<ServletContext> serviceReference) {

		try {
			ServletContext servletContext = _bundleContext.getService(
				serviceReference);

			NPMResolvedPackageNameUtil.set(
				servletContext, _npmResolvedPackageName);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}

		close();

		return null;
	}

	public void close() {
		_serviceTracker.close();
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> reference, ServletContext service) {
	}

	public void open() {
		_serviceTracker.open();
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> reference, ServletContext service) {
	}

	private Filter _getServletContextFilter(Bundle bundle)
		throws InvalidSyntaxException {

		return _bundleContext.createFilter(
			StringBundler.concat(
				"(&(objectClass=", ServletContext.class.getName(), ")",
				"(service.bundleid=", String.valueOf(bundle.getBundleId()),
				"))"));
	}

	private final BundleContext _bundleContext;
	private final String _npmResolvedPackageName;
	private final ServiceTracker<ServletContext, ServletContext>
		_serviceTracker;

}