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

package com.liferay.registry.collections.internal;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class RegistryServiceTrackerCustomizer<S>
	implements ServiceTrackerCustomizer<S, S> {

	@Override
	public S addingService(ServiceReference<S> serviceReference) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(serviceReference);
	}

	@Override
	public void modifiedService(
		ServiceReference<S> serviceReference, S service) {

		addingService(serviceReference);

		removedService(serviceReference, service);
	}

	@Override
	public void removedService(
		ServiceReference<S> serviceReference, S service) {

		Registry registry = RegistryUtil.getRegistry();

		registry.ungetService(serviceReference);
	}

}