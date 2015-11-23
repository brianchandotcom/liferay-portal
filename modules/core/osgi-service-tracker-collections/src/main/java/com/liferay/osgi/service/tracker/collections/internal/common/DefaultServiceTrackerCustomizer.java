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

package com.liferay.osgi.service.tracker.collections.internal.common;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class DefaultServiceTrackerCustomizer<S>
	implements ServiceTrackerCustomizer<S, S> {

	public DefaultServiceTrackerCustomizer(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public S addingService(ServiceReference<S> serviceReference) {
		return _bundleContext.getService(serviceReference);
	}

	@Override
	public void modifiedService(
		ServiceReference<S> serviceReference, S service) {
	}

	@Override
	public void removedService(
		ServiceReference<S> serviceReference, S service) {

		_bundleContext.ungetService(serviceReference);
	}

	private final BundleContext _bundleContext;

}