/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = {})
public class CMSMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		List<ServiceRegistration<Destination>> serviceRegistrations =
			new ArrayList<>();

		for (String destinationName : _DESTINATION_NAMES) {
			Destination destination = _destinationFactory.createDestination(
				DestinationConfiguration.
					createSynchronousDestinationConfiguration(destinationName));

			serviceRegistrations.add(
				bundleContext.registerService(
					Destination.class, destination,
					MapUtil.singletonDictionary(
						"destination.name", destination.getName())));
		}

		_serviceRegistrations = serviceRegistrations;
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	private static final String[] _DESTINATION_NAMES = {
		DestinationNames.COMMERCE_BASE_PRICE_LIST,
		DestinationNames.COMMERCE_ORDER_STATUS,
		DestinationNames.COMMERCE_PAYMENT_STATUS,
		DestinationNames.COMMERCE_SHIPMENT_STATUS,
		DestinationNames.COMMERCE_SUBSCRIPTION_STATUS
	};

	@Reference
	private DestinationFactory _destinationFactory;

	private List<ServiceRegistration<Destination>> _serviceRegistrations;

}