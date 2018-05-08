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

package com.liferay.apio.architect.wiring.osgi.internal.service.tracker.customizer;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getProperties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Creates a {@code ServiceRegistration} {@code ServiceTrackerCustomizer} with a
 * lambda instead of an anonymous class.
 *
 * @author Alejandro Hernández
 */
@FunctionalInterface
public interface ServiceRegistrationServiceTrackerCustomizer<T>
	extends ServiceTrackerCustomizer<T, ServiceRegistration<?>> {

	@Override
	public default void modifiedService(
		ServiceReference<T> serviceReference,
		ServiceRegistration<?> serviceRegistration) {

		serviceRegistration.setProperties(getProperties(serviceReference));
	}

	@Override
	public default void removedService(
		ServiceReference<T> serviceReference,
		ServiceRegistration<?> serviceRegistration) {

		Bundle bundle = FrameworkUtil.getBundle(
			ServiceRegistrationServiceTrackerCustomizer.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.ungetService(serviceReference);

		serviceRegistration.unregister();
	}

}