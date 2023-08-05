/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

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
		_registerDestination(
			bundleContext, DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC,
			DestinationConfiguration.DESTINATION_TYPE_SERIAL);
		_registerDestination(
			bundleContext, DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC,
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
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

	@Reference
	private DestinationFactory _destinationFactory;

	private final List<ServiceRegistration<Destination>> _serviceRegistrations =
		new ArrayList<>();

}