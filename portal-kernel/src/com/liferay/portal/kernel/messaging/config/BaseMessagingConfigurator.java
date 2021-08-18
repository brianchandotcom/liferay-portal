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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistrar;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		Registry registry = RegistryUtil.getRegistry();

		_messageListenerServiceRegistrar = registry.getServiceRegistrar(
			MessageListener.class);

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
						Map<String, Object> properties =
							HashMapBuilder.<String, Object>put(
								"destination.name", destinationName
							).put(
								"message.listener.operating.class.loader",
								getOperatingClassLoader()
							).build();

						for (MessageListener messageListener :
								messageListeners.getValue()) {

							_messageListenerServiceRegistrar.registerService(
								MessageListener.class, messageListener,
								properties);
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
		if (_messageListenerServiceRegistrar != null) {
			_messageListenerServiceRegistrar.destroy();
		}

		if (_destinationEventListenerServiceRegistrar != null) {
			_destinationEventListenerServiceRegistrar.destroy();
		}

		if (_destinationServiceRegistrar != null) {
			_destinationServiceRegistrar.destroy();
		}

		if (_messageBusEventListenerServiceRegistrar != null) {
			_messageBusEventListenerServiceRegistrar.destroy();
		}

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
						_log.debug(exception, exception);
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
						_log.debug(exception, exception);
					}
				}
			}
		}

		_messageListeners.putAll(messageListeners);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getOperatingClassLoader()}
	 */
	@Deprecated
	protected ClassLoader getOperatingClassloader() {
		return getOperatingClassLoader();
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

		Registry registry = RegistryUtil.getRegistry();

		_destinationEventListenerServiceRegistrar =
			registry.getServiceRegistrar(DestinationEventListener.class);

		for (final Map.Entry<String, List<DestinationEventListener>> entry :
				_destinationEventListeners.entrySet()) {

			final String destinationName = entry.getKey();

			ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

			serviceLatch.waitFor(
				StringBundler.concat(
					"(&(destination.name=", destinationName, ")(objectClass=",
					Destination.class.getName(), "))"));

			serviceLatch.openOn(
				() -> {
					Map<String, Object> properties =
						HashMapBuilder.<String, Object>put(
							"destination.name", destinationName
						).build();

					for (DestinationEventListener destinationEventListener :
							entry.getValue()) {

						_destinationEventListenerServiceRegistrar.
							registerService(
								DestinationEventListener.class,
								destinationEventListener, properties);
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

		Registry registry = RegistryUtil.getRegistry();

		_destinationServiceRegistrar = registry.getServiceRegistrar(
			Destination.class);

		for (Destination destination : _destinations) {
			_destinationServiceRegistrar.registerService(
				Destination.class, destination,
				HashMapBuilder.<String, Object>put(
					"destination.name", destination.getName()
				).build());
		}
	}

	protected void registerMessageBusEventListeners() {
		if (_messageBusEventListeners.isEmpty()) {
			return;
		}

		Registry registry = RegistryUtil.getRegistry();

		_messageBusEventListenerServiceRegistrar = registry.getServiceRegistrar(
			MessageBusEventListener.class);

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			_messageBusEventListenerServiceRegistrar.registerService(
				MessageBusEventListener.class, messageBusEventListener);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessagingConfigurator.class);

	private final Set<DestinationConfiguration> _destinationConfigurations =
		new HashSet<>();
	private final Map<String, List<DestinationEventListener>>
		_destinationEventListeners = new HashMap<>();
	private ServiceRegistrar<DestinationEventListener>
		_destinationEventListenerServiceRegistrar;
	private final List<Destination> _destinations = new ArrayList<>();
	private ServiceRegistrar<Destination> _destinationServiceRegistrar;
	private volatile MessageBus _messageBus;
	private final List<MessageBusEventListener> _messageBusEventListeners =
		new ArrayList<>();
	private ServiceRegistrar<MessageBusEventListener>
		_messageBusEventListenerServiceRegistrar;
	private final Map<String, List<MessageListener>> _messageListeners =
		new HashMap<>();
	private ServiceRegistrar<MessageListener> _messageListenerServiceRegistrar;
	private boolean _portalMessagingConfigurator;

}