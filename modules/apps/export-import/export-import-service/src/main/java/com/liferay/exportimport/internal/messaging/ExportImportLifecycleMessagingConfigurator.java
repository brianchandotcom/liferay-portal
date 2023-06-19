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

package com.liferay.exportimport.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class ExportImportLifecycleMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_registerDestination(
			bundleContext,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC,
			DestinationConfiguration.DESTINATION_TYPE_SERIAL);
		_registerDestination(
			bundleContext,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC,
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			Destination destination = _bundleContext.getService(
				serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	private ServiceRegistration<Destination> _registerDestination(
		BundleContext bundleContext, String destinationName,
		String destinationType) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		ServiceRegistration<Destination> serviceRegistration =
			bundleContext.registerService(
				Destination.class, destination, dictionary);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private final Set<ServiceRegistration<Destination>> _serviceRegistrations =
		new HashSet<>();

}