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

package com.liferay.portal.kernel.messaging.config;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.lang.reflect.Method;

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
public abstract class BaseMessagingConfigurator
	implements MessagingConfigurator {

	public void afterPropertiesSet() {
		ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

		serviceLatch.waitFor(DestinationFactory.class);
		serviceLatch.waitFor(
			MessageBus.class, messageBus -> _messageBus = messageBus);
		serviceLatch.openOn(this::initialize);
	}

	@Override
	public void connect() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getOperatingClassLoader());

			for (Map.Entry<String, List<MessageListener>> messageListeners :
					_messageListeners.entrySet()) {

				String destinationName = messageListeners.getKey();

				ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

				serviceLatch.waitFor(
					StringBundler.concat(
						"(&(destination.name=", destinationName,
						")(objectClass=", Destination.class.getName(), "))"));

				serviceLatch.openOn(
					bundleContext -> {
						Dictionary<String, Object> properties =
							HashMapDictionaryBuilder.<String, Object>put(
								"destination.name", destinationName
							).put(
								"message.listener.operating.class.loader",
								getOperatingClassLoader()
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
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
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

		for (Destination destination : _destinations) {
			destination.destroy();
		}

		_destinations.clear();
		_messageBusEventListeners.clear();

		String servletContextName =
			ServletContextClassLoaderPool.getServletContextName(
				getOperatingClassLoader());

		if (servletContextName != null) {
			MessagingConfiguratorRegistry.unregisterMessagingConfigurator(
				servletContextName, this);
		}
	}

	@Override
	public void disconnect() {
		for (Map.Entry<String, List<MessageListener>> messageListeners :
				_messageListeners.entrySet()) {

			String destinationName = messageListeners.getKey();

			for (MessageListener messageListener :
					messageListeners.getValue()) {

				_messageBus.unregisterMessageListener(
					destinationName, messageListener);
			}
		}
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

		for (List<MessageListener> messageListenersList :
				messageListeners.values()) {

			for (MessageListener messageListener : messageListenersList) {
				Class<?> messageListenerClass = messageListener.getClass();

				try {
					Method setMessageBusMethod = messageListenerClass.getMethod(
						"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(messageListener, _messageBus);

					continue;
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}

				try {
					Method setMessageBusMethod =
						messageListenerClass.getDeclaredMethod(
							"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(messageListener, _messageBus);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}
		}

		_messageListeners.putAll(messageListeners);
	}

	protected abstract ClassLoader getOperatingClassLoader();

	protected void initialize() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader operatingClassLoader = getOperatingClassLoader();

		if (contextClassLoader == operatingClassLoader) {
			_portalMessagingConfigurator = true;
		}

		registerMessageBusEventListeners();

		registerDestinations();

		registerDestinationEventListeners();

		connect();

		String servletContextName =
			ServletContextClassLoaderPool.getServletContextName(
				operatingClassLoader);

		if (servletContextName != null) {
			MessagingConfiguratorRegistry.registerMessagingConfigurator(
				servletContextName, this);
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
				DestinationFactoryUtil.createDestination(
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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessagingConfigurator.class);

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
	private boolean _portalMessagingConfigurator;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}