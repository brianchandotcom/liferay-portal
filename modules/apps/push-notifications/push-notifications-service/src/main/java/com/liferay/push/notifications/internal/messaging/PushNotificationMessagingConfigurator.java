/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.internal.messaging;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalService;

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
 * @author Michael C. Han
 */
@Component(service = PushNotificationMessagingConfigurator.class)
public class PushNotificationMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		DestinationConfiguration pushNotificationDestinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				PushNotificationsDestinationNames.PUSH_NOTIFICATION);

		Destination pushNotificationDestination = _registerDestination(
			pushNotificationDestinationConfiguration);

		_serviceRegistrations.add(
			bundleContext.registerService(
				MessageListener.class,
				new PushNotificationsMessageListener(
					_pushNotificationsDeviceLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", pushNotificationDestination.getName()
				).build()));

		DestinationConfiguration
			pushNotificationResponseDestinationConfiguration =
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_SERIAL,
					PushNotificationsDestinationNames.
						PUSH_NOTIFICATION_RESPONSE);

		Destination pushNotificationResponseDestination = _registerDestination(
			pushNotificationResponseDestinationConfiguration);

		_serviceRegistrations.add(
			bundleContext.registerService(
				MessageListener.class,
				new PushNotificationsResponseMessageListener(_jsonFactory),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name",
					pushNotificationResponseDestination.getName()
				).build()));
	}

	@Deactivate
	protected void deactivate() {
		if (!_serviceRegistrations.isEmpty()) {
			for (ServiceRegistration<?> serviceRegistration :
					_serviceRegistrations) {

				serviceRegistration.unregister();
			}

			_serviceRegistrations.clear();
		}

		_bundleContext = null;
	}

	private Destination _registerDestination(
		DestinationConfiguration destinationConfiguration) {

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		_serviceRegistrations.add(
			_bundleContext.registerService(
				Destination.class, destination, properties));

		return destination;
	}

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PushNotificationsDeviceLocalService
		_pushNotificationsDeviceLocalService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}