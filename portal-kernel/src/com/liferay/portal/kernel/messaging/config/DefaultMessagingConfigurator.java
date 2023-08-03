/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging.config;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
public class DefaultMessagingConfigurator implements MessagingConfigurator {

	public void afterPropertiesSet() {
		ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

		serviceLatch.waitFor(DestinationFactory.class);
		serviceLatch.waitFor(
			MessageBus.class, messageBus -> _messageBus = messageBus);
		serviceLatch.openOn(this::initialize);
	}

	@Override
	public void destroy() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		_destinationConfigurations.clear();
		_destinationEventListeners.clear();
		_messageListeners.clear();
		_destinations.clear();
		_messageBusEventListeners.clear();
	}

	@Override
	public void setDestinationConfigurations(
		Set<DestinationConfiguration> destinationConfigurations) {

		_destinationConfigurations.addAll(destinationConfigurations);
	}

	@Override
	public void setDestinationEventListeners(
		Map<String, List<DestinationEventListener>> destinationEventListeners) {

		_destinationEventListeners.putAll(destinationEventListeners);
	}

	@Override
	public void setDestinations(List<Destination> destinations) {
		_destinations.addAll(destinations);
	}

	@Override
	public void setMessageBusEventListeners(
		List<MessageBusEventListener> messageBusEventListeners) {

		_messageBusEventListeners.addAll(messageBusEventListeners);
	}

	@Override
	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners) {

		_messageListeners.putAll(messageListeners);
	}

	protected void initialize() {
		registerMessageBusEventListeners();

		registerDestinations();

		registerDestinationEventListeners();

		for (Map.Entry<String, List<MessageListener>> messageListeners :
				_messageListeners.entrySet()) {

			String destinationName = messageListeners.getKey();

			ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

			serviceLatch.waitFor(
				StringBundler.concat(
					"(&(destination.name=", destinationName, ")(objectClass=",
					Destination.class.getName(), "))"));

			serviceLatch.openOn(
				bundleContext -> {
					Dictionary<String, Object> properties =
						HashMapDictionaryBuilder.<String, Object>put(
							"destination.name", destinationName
						).build();

					for (MessageListener messageListener :
							messageListeners.getValue()) {

						_serviceRegistrations.add(
							bundleContext.registerService(
								MessageListener.class, messageListener,
								properties));
					}
				});
		}
	}

	protected void registerDestinationEventListeners() {
		if (_destinationEventListeners.isEmpty()) {
			return;
		}

		for (final Map.Entry<String, List<DestinationEventListener>> entry :
				_destinationEventListeners.entrySet()) {

			String destinationName = entry.getKey();

			ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

			serviceLatch.waitFor(
				StringBundler.concat(
					"(&(destination.name=", destinationName, ")(objectClass=",
					Destination.class.getName(), "))"));

			serviceLatch.openOn(
				bundleContext -> {
					Dictionary<String, Object> properties =
						MapUtil.singletonDictionary(
							"destination.name", destinationName);

					for (DestinationEventListener destinationEventListener :
							entry.getValue()) {

						_serviceRegistrations.add(
							bundleContext.registerService(
								DestinationEventListener.class,
								destinationEventListener, properties));
					}
				});
		}
	}

	protected void registerDestinations() {
		for (DestinationConfiguration destinationConfiguration :
				_destinationConfigurations) {

			_destinations.add(
				_destinationFactory.createDestination(
					destinationConfiguration));
		}

		if (_destinations.isEmpty()) {
			return;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Destination destination : _destinations) {
			_serviceRegistrations.add(
				bundleContext.registerService(
					Destination.class, destination,
					MapUtil.singletonDictionary(
						"destination.name", destination.getName())));
		}
	}

	protected void registerMessageBusEventListeners() {
		if (_messageBusEventListeners.isEmpty()) {
			return;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			_serviceRegistrations.add(
				bundleContext.registerService(
					MessageBusEventListener.class, messageBusEventListener,
					null));
		}
	}

	private static volatile DestinationFactory _destinationFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			DestinationFactory.class, DefaultMessagingConfigurator.class,
			"_destinationFactory", false);

	private final Set<DestinationConfiguration> _destinationConfigurations =
		new HashSet<>();
	private final Map<String, List<DestinationEventListener>>
		_destinationEventListeners = new HashMap<>();
	private final List<Destination> _destinations = new ArrayList<>();
	private volatile MessageBus _messageBus;
	private final List<MessageBusEventListener> _messageBusEventListeners =
		new ArrayList<>();
	private final Map<String, List<MessageListener>> _messageListeners =
		new HashMap<>();
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}