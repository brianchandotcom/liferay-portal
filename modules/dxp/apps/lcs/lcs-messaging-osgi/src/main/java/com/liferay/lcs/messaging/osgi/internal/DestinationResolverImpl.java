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

package com.liferay.lcs.messaging.osgi.internal;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = DestinationResolver.class)
public class DestinationResolverImpl implements DestinationResolver {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	public void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_destinationNamesServiceRegistrations.values()) {

			Destination destination = _bundleContext.getService(
				serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();
		}

		_destinationNamesServiceRegistrations.clear();
	}

	@Override
	public void resolveLocalDestination(String destinationName) {
		Destination destination = _messageBus.getDestination(destinationName);

		if (destination != null) {
			return;
		}

		_registerDestination(destinationName, false);
	}

	@Override
	public void resolveRemoteDestination(String destinationName) {
		Destination destination = _messageBus.getDestination(destinationName);

		if (destination != null) {
			return;
		}

		_registerDestination(destinationName, true);
	}

	private void _registerDestination(String destinationName, boolean remote) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				destinationName);

		destinationConfiguration.setMaximumQueueSize(5);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"destination.name", destinationConfiguration.getDestinationName());

		if (remote) {
			properties.put(RemoteDestinationType.OSB_LCS, true);
		}

		destinationConfiguration.setRejectedExecutionHandler(
			new LCSCallerRunsPolicy());

		Destination destination = DestinationFactoryUtil.createDestination(
			destinationConfiguration);

		ServiceRegistration<Destination> serviceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, properties);

		_destinationNamesServiceRegistrations.put(
			destination.getName(), serviceRegistration);
	}

	@Reference
	private BundleContext _bundleContext;

	private final Map<String, ServiceRegistration<Destination>>
		_destinationNamesServiceRegistrations = new HashMap<>();

	@Reference
	private MessageBus _messageBus;

}