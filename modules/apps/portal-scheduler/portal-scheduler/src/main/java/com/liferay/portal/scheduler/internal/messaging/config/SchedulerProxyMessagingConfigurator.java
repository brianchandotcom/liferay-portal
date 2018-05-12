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

package com.liferay.portal.scheduler.internal.messaging.config;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	enabled = false, immediate = true,
	service = SchedulerProxyMessagingConfigurator.class
)
public class SchedulerProxyMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.SCHEDULER_ENGINE);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		destination.register(_proxyMessageListener);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination, properties);

		properties.put("destination.ready", true);

		_destinationReadyServiceRegistration = bundleContext.registerService(
			Object.class, new Object(), properties);
	}

	@Deactivate
	protected void deactivate() {
		_destinationReadyServiceRegistration.unregister();

		Destination destination = _bundleContext.getService(
			_destinationServiceRegistration.getReference());

		_destinationServiceRegistration.unregister();

		destination.destroy();
	}

	@Reference(unbind = "-")
	protected void setDestinationFactory(
		DestinationFactory destinationFactory) {

		_destinationFactory = destinationFactory;
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
	}

	@Reference(
		service = ProxyMessageListener.class,
		target = "(destination.name=" + DestinationNames.SCHEDULER_ENGINE + ")",
		unbind = "-"
	)
	protected void setProxyMessageListener(
		ProxyMessageListener proxyMessageListener) {

		_proxyMessageListener = proxyMessageListener;
	}

	private BundleContext _bundleContext;
	private DestinationFactory _destinationFactory;
	private ServiceRegistration<Object> _destinationReadyServiceRegistration;
	private ServiceRegistration<Destination> _destinationServiceRegistration;
	private ProxyMessageListener _proxyMessageListener;

}