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

package com.liferay.portal.pop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.pop.messaging.POPNotificationsMessageListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class POPServerUtil {

	public static void addListener(MessageListener listener) throws Exception {
		_instance._addListener(listener);
	}

	public static void deleteListener(MessageListener listener)
		throws Exception {

		_instance._deleteListener(listener);
	}

	public static List<MessageListener> getListeners() throws Exception {
		return _instance._getListeners();
	}

	public static void start() {
		_instance._start();
	}

	private POPServerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MessageListener.class,
			new MessageListenerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _addListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not add null listener");
			}

			return;
		}

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<MessageListener> serviceRegistration =
			registry.registerService(MessageListener.class, listener);

		_serviceRegistrations.put(listener, serviceRegistration);
	}

	private void _deleteListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not delete null listener");
			}

			return;
		}

		ServiceRegistration<MessageListener> serviceRegistration =
			_serviceRegistrations.remove(listener);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private List<MessageListener> _getListeners() {
		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}

		return Collections.unmodifiableList(_listeners);
	}

	private void _start() {
		if (_log.isDebugEnabled()) {
			_log.debug("Start");
		}

		try {
			SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

			schedulerEntry.setEventListenerClass(
				POPNotificationsMessageListener.class.getName());
			schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
			schedulerEntry.setTriggerType(TriggerType.SIMPLE);
			schedulerEntry.setTriggerValue(
				PropsValues.POP_SERVER_NOTIFICATIONS_INTERVAL);

			SchedulerEngineHelperUtil.schedule(
				schedulerEntry, StorageType.MEMORY_CLUSTERED, null, 0);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(POPServerUtil.class);

	private static final POPServerUtil _instance = new POPServerUtil();

	private final List<MessageListener> _listeners =
		new ArrayList<MessageListener>();
	private final ServiceRegistrationMap<MessageListener>
		_serviceRegistrations = new ServiceRegistrationMap<MessageListener>();
	private final ServiceTracker<MessageListener, MessageListenerWrapper>
		_serviceTracker;

	private class MessageListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<MessageListener, MessageListenerWrapper> {

		@Override
		public MessageListenerWrapper addingService(
			ServiceReference<MessageListener> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MessageListener messageListener = registry.getService(
				serviceReference);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add listener " + messageListener.getClass().getName());
			}

			MessageListenerWrapper messageListenerWrapper =
				new MessageListenerWrapper(messageListener);

			_deleteListener(messageListenerWrapper);

			_listeners.add(messageListenerWrapper);

			if (_log.isDebugEnabled()) {
				_log.debug("Listeners size " + _listeners.size());
			}

			return messageListenerWrapper;
		}

		@Override
		public void modifiedService(
			ServiceReference<MessageListener> serviceReference,
			MessageListenerWrapper messageListenerWrapper) {
		}

		@Override
		public void removedService(
			ServiceReference<MessageListener> serviceReference,
			MessageListenerWrapper messageListenerWrapper) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			MessageListener messageListener =
				messageListenerWrapper.getMessageListener();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delete listener " +
						ClassUtil.getClassName(messageListener));
			}

			_listeners.remove(messageListenerWrapper);

			if (_log.isDebugEnabled()) {
				_log.debug("Listeners size " + _listeners.size());
			}
		}

	}

}