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

package com.liferay.portal.messaging.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;
import com.liferay.portal.kernel.messaging.MessageBusInterceptor;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration",
	service = {ManagedServiceFactory.class, MessageBus.class}
)
public class DefaultMessageBus implements ManagedServiceFactory, MessageBus {

	@Override
	public boolean addMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.add(messageBusEventListener);
	}

	@Override
	public void deleted(String factoryPid) {
		String destinationName = _factoryPidsToDestinationName.remove(
			factoryPid);

		_destinationWorkerConfigurations.remove(destinationName);
	}

	@Override
	public Destination getDestination(String destinationName) {
		return _destinations.get(destinationName);
	}

	@Override
	public int getDestinationCount() {
		return _destinations.size();
	}

	@Override
	public Collection<String> getDestinationNames() {
		return _destinations.keySet();
	}

	@Override
	public Collection<Destination> getDestinations() {
		return _destinations.values();
	}

	@Override
	public String getName() {
		return "Default Message Bus";
	}

	@Override
	public boolean hasDestination(String destinationName) {
		return _destinations.containsKey(destinationName);
	}

	@Override
	public boolean hasMessageListener(String destinationName) {
		Destination destination = _destinations.get(destinationName);

		if ((destination != null) && destination.isRegistered()) {
			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean registerMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination != null) {
			return destination.register(messageListener);
		}

		List<MessageListener> queuedMessageListeners =
			_queuedMessageListeners.get(destinationName);

		if (queuedMessageListeners == null) {
			queuedMessageListeners = new ArrayList<>();

			_queuedMessageListeners.put(
				destinationName, queuedMessageListeners);
		}

		queuedMessageListeners.add(messageListener);

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Queuing message listener until destination " +
					destinationName + " is added");
		}

		return false;
	}

	@Override
	public boolean removeMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.remove(messageBusEventListener);
	}

	@Override
	public void sendMessage(String destinationName, Message message) {
		MessageBusThreadLocalUtil.populateMessageFromThreadLocals(message);

		for (MessageBusInterceptor messageBusInterceptor :
				_serviceTrackerList) {

			if (messageBusInterceptor.intercept(
					this, destinationName, message)) {

				return;
			}
		}

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Destination " + destinationName + " is not configured");
			}

			return;
		}

		message.setDestinationName(destinationName);

		if (message.get("companyId") == null) {
			Long[] companyIds = (Long[])message.get("companyIds");

			if (companyIds != null) {
				long orignalCompanyId = CompanyThreadLocal.getCompanyId();

				try {
					for (Long id : companyIds) {
						CompanyThreadLocal.setCompanyId(id);

						message.put("companyId", id);

						destination.send(message);
					}
				}
				finally {
					CompanyThreadLocal.setCompanyId(orignalCompanyId);
				}

				return;
			}
		}

		destination.send(message);
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	@Override
	public synchronized boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination != null) {
			return destination.unregister(messageListener);
		}

		List<MessageListener> queuedMessageListeners =
			_queuedMessageListeners.get(destinationName);

		if (ListUtil.isEmpty(queuedMessageListeners)) {
			return false;
		}

		return queuedMessageListeners.remove(messageListener);
	}

	@Override
	public void updated(String factoryPid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		DestinationWorkerConfiguration destinationWorkerConfiguration =
			ConfigurableUtil.createConfigurable(
				DestinationWorkerConfiguration.class, dictionary);

		_factoryPidsToDestinationName.put(
			factoryPid, destinationWorkerConfiguration.destinationName());

		_destinationWorkerConfigurations.put(
			destinationWorkerConfiguration.destinationName(),
			destinationWorkerConfiguration);

		Destination destination = _destinations.get(
			destinationWorkerConfiguration.destinationName());

		_updateDestination(destination, destinationWorkerConfiguration);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_messageListenerServiceTracker = new ServiceTracker<>(
			bundleContext, MessageListener.class,
			new ServiceTrackerCustomizer
				<MessageListener, ObjectValuePair<String, MessageListener>>() {

				@Override
				public ObjectValuePair<String, MessageListener> addingService(
					ServiceReference<MessageListener> serviceReference) {

					String destinationName =
						(String)serviceReference.getProperty(
							"destination.name");

					if (destinationName == null) {
						return null;
					}

					MessageListener messageListener = bundleContext.getService(
						serviceReference);

					Thread currentThread = Thread.currentThread();

					ClassLoader contextClassLoader =
						currentThread.getContextClassLoader();

					try {
						ClassLoader operatingClassLoader =
							(ClassLoader)serviceReference.getProperty(
								"message.listener.operating.class.loader");

						if (operatingClassLoader != null) {
							currentThread.setContextClassLoader(
								operatingClassLoader);
						}

						registerMessageListener(
							destinationName, messageListener);
					}
					finally {
						currentThread.setContextClassLoader(contextClassLoader);
					}

					return new ObjectValuePair<>(
						destinationName, messageListener);
				}

				@Override
				public void modifiedService(
					ServiceReference<MessageListener> serviceReference,
					ObjectValuePair<String, MessageListener> objectValuePair) {

					removedService(serviceReference, objectValuePair);

					ObjectValuePair<String, MessageListener>
						newObjectValuePair = addingService(serviceReference);

					objectValuePair.setKey(newObjectValuePair.getKey());
				}

				@Override
				public void removedService(
					ServiceReference<MessageListener> serviceReference,
					ObjectValuePair<String, MessageListener> objectValuePair) {

					unregisterMessageListener(
						objectValuePair.getKey(), objectValuePair.getValue());

					bundleContext.ungetService(serviceReference);
				}

			});

		_messageListenerServiceTracker.open();

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, MessageBusInterceptor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();

		_messageListenerServiceTracker.close();

		shutdown(true);

		for (Destination destination : _destinations.values()) {
			destination.destroy();
		}

		_messageBusEventListeners.clear();

		_destinations.clear();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)"
	)
	protected synchronized void registerDestination(
		Destination destination, Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		if (BaseDestination.class.isInstance(destination)) {
			BaseDestination baseDestination = (BaseDestination)destination;

			baseDestination.setName(destinationName);

			baseDestination.afterPropertiesSet();
		}

		_addDestination(destination);

		DestinationWorkerConfiguration destinationWorkerConfiguration =
			_destinationWorkerConfigurations.get(destinationName);

		_updateDestination(destination, destinationWorkerConfiguration);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)"
	)
	protected synchronized void registerDestinationEventListener(
		DestinationEventListener destinationEventListener,
		Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to unregister destination event listener for " +
						destinationName);
			}

			return;
		}

		destination.addDestinationEventListener(destinationEventListener);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void registerMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		addMessageBusEventListener(messageBusEventListener);
	}

	protected synchronized void unregisterDestination(
		Destination destination, Map<String, Object> properties) {

		_removeDestination(destination.getName());
	}

	protected synchronized void unregisterDestinationEventListener(
		DestinationEventListener destinationEventListener,
		Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to unregister destination event listener for " +
						destinationName);
			}

			return;
		}

		destination.removeDestinationEventListener(destinationEventListener);
	}

	protected void unregisterMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		removeMessageBusEventListener(messageBusEventListener);
	}

	private void _addDestination(Destination destination) {
		Destination oldDestination = _destinations.get(destination.getName());

		if (oldDestination != null) {
			oldDestination.copyDestinationEventListeners(destination);
			oldDestination.copyMessageListeners(destination);
		}
		else {
			List<MessageListener> messageListeners =
				_queuedMessageListeners.remove(destination.getName());

			if (!ListUtil.isEmpty(messageListeners)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Registering ", messageListeners.size(),
							" queued message listeners for destination ",
							destination.getName()));
				}

				for (MessageListener messageListener : messageListeners) {
					destination.register(messageListener);
				}
			}
		}

		destination.open();

		_destinations.put(destination.getName(), destination);

		if (oldDestination != null) {
			oldDestination.destroy();

			for (MessageBusEventListener messageBusEventListener :
					_messageBusEventListeners) {

				messageBusEventListener.destinationRemoved(oldDestination);
			}
		}

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			messageBusEventListener.destinationAdded(destination);
		}
	}

	private Destination _removeDestination(String destinationName) {
		Destination destination = _destinations.remove(destinationName);

		if (destination == null) {
			return null;
		}

		destination.destroy();

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			messageBusEventListener.destinationRemoved(destination);
		}

		return destination;
	}

	private void _updateDestination(
		Destination destination,
		DestinationWorkerConfiguration destinationWorkerConfiguration) {

		if ((destination == null) || (destinationWorkerConfiguration == null)) {
			return;
		}

		if (destination instanceof BaseAsyncDestination) {
			BaseAsyncDestination baseAsyncDestination =
				(BaseAsyncDestination)destination;

			baseAsyncDestination.setMaximumQueueSize(
				destinationWorkerConfiguration.maxQueueSize());
			baseAsyncDestination.setWorkersSize(
				destinationWorkerConfiguration.workerCoreSize(),
				destinationWorkerConfiguration.workerMaxSize());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMessageBus.class);

	private final Map<String, Destination> _destinations =
		new ConcurrentHashMap<>();
	private final Map<String, DestinationWorkerConfiguration>
		_destinationWorkerConfigurations = new ConcurrentHashMap<>();
	private final Map<String, String> _factoryPidsToDestinationName =
		new ConcurrentHashMap<>();
	private final Set<MessageBusEventListener> _messageBusEventListeners =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private ServiceTracker
		<MessageListener, ObjectValuePair<String, MessageListener>>
			_messageListenerServiceTracker;
	private final Map<String, List<MessageListener>> _queuedMessageListeners =
		new HashMap<>();
	private ServiceTrackerList<MessageBusInterceptor> _serviceTrackerList;

}