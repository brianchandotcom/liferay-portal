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

package com.liferay.portal.kernel.messaging;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public class DestinationFactoryUtil {

	public static Destination createDestination(
		DestinationConfig destinationConfig) {

		return _instance.getDestinationFactory().createDestination(
			destinationConfig);
	}

	public static Collection<String> getTypes() {
		return _instance.getDestinationFactory().getTypes();
	}

	protected DestinationFactory getDestinationFactory() {
		try {
			while (_serviceTracker.getService() == null) {
				Thread.currentThread().sleep(500);
			}

			return _serviceTracker.getService();
		}
		catch (InterruptedException e) {
			throw new IllegalStateException(
				"Unable to obtain reference for DestinationFactory", e);
		}
	}

	private DestinationFactoryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DestinationFactory.class);

		_serviceTracker.open();
	}

	private static final DestinationFactoryUtil _instance =
		new DestinationFactoryUtil();

	private final ServiceTracker<DestinationFactory, DestinationFactory>
		_serviceTracker;

}