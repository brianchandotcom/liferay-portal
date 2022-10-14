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

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {})
public class AnalyticsMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSerialDestinationConfiguration(
				AnalyticsMessagesDestinationNames.ANALYTICS_MESSAGES_PROCESSOR);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination, dictionary);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_serviceRegistration.getReference());

			_serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _serviceRegistration;

}