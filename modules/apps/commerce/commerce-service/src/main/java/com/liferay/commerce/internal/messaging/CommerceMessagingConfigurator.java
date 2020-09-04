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

package com.liferay.commerce.internal.messaging;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceMessagingConfigurator.class
)
public class CommerceMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_orderStatusServiceRegistration = _registerDestination(
			bundleContext, CommerceDestinationNames.ORDER_STATUS);
		_paymentStatusServiceRegistration = _registerDestination(
			bundleContext, CommerceDestinationNames.PAYMENT_STATUS);
		_shipmentStatusServiceRegistration = _registerDestination(
			bundleContext, CommerceDestinationNames.SHIPMENT_STATUS);
		_stockQuantityServiceRegistration = _registerDestination(
			bundleContext, CommerceDestinationNames.STOCK_QUANTITY);
		_subscriptionStatusServiceRegistration = _registerDestination(
			bundleContext, CommerceDestinationNames.SUBSCRIPTION_STATUS);
	}

	@Deactivate
	protected void deactivate() {
		if (_orderStatusServiceRegistration != null) {
			_orderStatusServiceRegistration.unregister();
		}

		if (_paymentStatusServiceRegistration != null) {
			_paymentStatusServiceRegistration.unregister();
		}

		if (_shipmentStatusServiceRegistration != null) {
			_shipmentStatusServiceRegistration.unregister();
		}

		if (_stockQuantityServiceRegistration != null) {
			_stockQuantityServiceRegistration.unregister();
		}

		if (_subscriptionStatusServiceRegistration != null) {
			_subscriptionStatusServiceRegistration.unregister();
		}
	}

	private ServiceRegistration<Destination> _registerDestination(
		BundleContext bundleContext, String destinationName) {

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createParallelDestinationConfiguration(
				destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("destination.name", destination.getName());

		return bundleContext.registerService(
			Destination.class, destination, dictionary);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private volatile ServiceRegistration<Destination>
		_orderStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_paymentStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_shipmentStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_stockQuantityServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_subscriptionStatusServiceRegistration;

}