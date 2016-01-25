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

package com.liferay.resources.importer.internal;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.resources.importer.messaging.DestinationNames;

import java.util.Dictionary;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Kocsis
 */
public class ResourcesImporterBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		ServiceReference<DestinationFactory> serviceReference =
			bundleContext.getServiceReference(DestinationFactory.class);

		try {
			DestinationFactory destinationFactory = bundleContext.getService(
				serviceReference);

			DestinationConfiguration destinationConfiguration =
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_SERIAL,
					DestinationNames.RESOURCES_IMPORTER);

			Destination destination = destinationFactory.createDestination(
				destinationConfiguration);

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			dictionary.put("destination.name", destination.getName());

			_serviceRegistration = bundleContext.registerService(
				Destination.class, destination, dictionary);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		Destination destination = bundleContext.getService(
			_serviceRegistration.getReference());

		_serviceRegistration.unregister();

		destination.destroy();
	}

	private ServiceRegistration<Destination> _serviceRegistration;

}