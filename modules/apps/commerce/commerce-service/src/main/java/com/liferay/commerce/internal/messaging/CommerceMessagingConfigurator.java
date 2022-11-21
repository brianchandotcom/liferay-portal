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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

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
@Component(service = {})
public class CommerceMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_basePriceListServiceRegistration = _registerDestination(
			bundleContext, DestinationNames.COMMERCE_BASE_PRICE_LIST,
			CommercePriceList.class.getName());
		_orderStatusServiceRegistration = _registerDestination(
			bundleContext, DestinationNames.COMMERCE_ORDER_STATUS,
			CommerceOrder.class.getName());
		_paymentStatusServiceRegistration = _registerDestination(
			bundleContext, DestinationNames.COMMERCE_PAYMENT_STATUS,
			CommerceOrder.class.getName());
		_shipmentStatusServiceRegistration = _registerDestination(
			bundleContext, DestinationNames.COMMERCE_SHIPMENT_STATUS,
			CommerceShipment.class.getName());
		_subscriptionStatusServiceRegistration = _registerDestination(
			bundleContext, DestinationNames.COMMERCE_SUBSCRIPTION_STATUS,
			CommerceOrder.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		if (_basePriceListServiceRegistration != null) {
			_basePriceListServiceRegistration.unregister();
		}

		if (_orderStatusServiceRegistration != null) {
			_orderStatusServiceRegistration.unregister();
		}

		if (_paymentStatusServiceRegistration != null) {
			_paymentStatusServiceRegistration.unregister();
		}

		if (_shipmentStatusServiceRegistration != null) {
			_shipmentStatusServiceRegistration.unregister();
		}

		if (_subscriptionStatusServiceRegistration != null) {
			_subscriptionStatusServiceRegistration.unregister();
		}
	}

	private ServiceRegistration<Destination> _registerDestination(
		BundleContext bundleContext, String destinationName, String className) {

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createParallelDestinationConfiguration(
				destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).put(
				"object.action.trigger.class.name", className
			).build();

		return bundleContext.registerService(
			Destination.class, destination, dictionary);
	}

	private volatile ServiceRegistration<Destination>
		_basePriceListServiceRegistration;

	@Reference
	private DestinationFactory _destinationFactory;

	private volatile ServiceRegistration<Destination>
		_orderStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_paymentStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_shipmentStatusServiceRegistration;
	private volatile ServiceRegistration<Destination>
		_subscriptionStatusServiceRegistration;

}