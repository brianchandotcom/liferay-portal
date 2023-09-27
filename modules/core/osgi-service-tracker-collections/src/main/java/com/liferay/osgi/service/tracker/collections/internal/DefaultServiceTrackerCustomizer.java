/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.internal;

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