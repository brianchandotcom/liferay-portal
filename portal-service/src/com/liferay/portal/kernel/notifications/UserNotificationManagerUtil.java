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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jonathan Lee
 */
public class UserNotificationManagerUtil {

	public static void addUserNotificationDefinition(
		String portletId,
		UserNotificationDefinition userNotificationDefinition) {

		_instance._addUserNotificationDefinition(
			portletId, userNotificationDefinition);
	}

	public static void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		_instance._addUserNotificationHandler(userNotificationHandler);
	}

	public static void deleteUserNotificationDefinitions(String portletId) {
		_instance._deleteUserNotificationDefinitions(portletId);
	}

	public static void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		_instance._deleteUserNotificationHandler(userNotificationHandler);
	}

	public static UserNotificationDefinition fetchUserNotificationDefinition(
		String portletId, long classNameId, int notificationType) {

		return _instance._fetchUserNotificationDefinition(
			portletId, classNameId, notificationType);
	}

	public static Map<String, List<UserNotificationDefinition>>
		getUserNotificationDefinitions() {

		return Collections.unmodifiableMap(
			_instance._userNotificationDefinitions);
	}

	public static Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers() {

		return Collections.unmodifiableMap(_instance._userNotificationHandlers);
	}

	public static UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		return _instance._interpret(
			selector, userNotificationEvent, serviceContext);
	}

	public static boolean isDeliver(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws PortalException {

		return _instance._isDeliver(
			userId, StringPool.BLANK, portletId, classNameId, notificationType,
			deliveryType, null);
	}

	public static boolean isDeliver(
			long userId, String selector, String portletId, long classNameId,
			int notificationType, int deliveryType,
			ServiceContext serviceContext)
		throws PortalException {

		return _instance._isDeliver(
			userId, selector, portletId, classNameId, notificationType,
			deliveryType, serviceContext);
	}

	private UserNotificationManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_userNotificationDefinitionServiceTracker = registry.trackServices(
			UserNotificationDefinition.class,
			new UserNotificationDefinitionServiceTrackerCustomizer());

		_userNotificationDefinitionServiceTracker.open();

		_userNotificationHandlerServiceTracker = registry.trackServices(
			UserNotificationHandler.class,
			new UserNotificationHandlerServiceTrackerCustomizer());

		_userNotificationHandlerServiceTracker.open();
	}

	private void _addUserNotificationDefinition(
		String portletId,
		UserNotificationDefinition userNotificationDefinition) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", portletId);

		ServiceRegistration<UserNotificationDefinition> serviceRegistration =
			registry.registerService(
				UserNotificationDefinition.class, userNotificationDefinition,
				properties);

		_userNotificationDefinitionServiceRegistrations.put(
			portletId, serviceRegistration);
	}

	private void _addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<UserNotificationHandler> serviceRegistration =
			registry.registerService(
				UserNotificationHandler.class, userNotificationHandler);

		_userNotificationHandlerServiceRegistrations.put(
			userNotificationHandler, serviceRegistration);
	}

	private void _deleteUserNotificationDefinitions(String portletId) {
		ServiceRegistration<UserNotificationDefinition> serviceRegistration =
			_userNotificationDefinitionServiceRegistrations.get(portletId);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private void _deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		ServiceRegistration<UserNotificationHandler> serviceRegistration =
			_userNotificationHandlerServiceRegistrations.get(
				userNotificationHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private UserNotificationDefinition _fetchUserNotificationDefinition(
		String portletId, long classNameId, int notificationType) {

		List<UserNotificationDefinition> userNotificationDefinitions =
			_userNotificationDefinitions.get(portletId);

		if (userNotificationDefinitions == null) {
			return null;
		}

		for (UserNotificationDefinition userNotificationDefinition :
				userNotificationDefinitions) {

			if ((userNotificationDefinition.getClassNameId() == classNameId) &&
				(userNotificationDefinition.getNotificationType() ==
					notificationType)) {

				return userNotificationDefinition;
			}
		}

		return null;
	}

	private UserNotificationFeedEntry _interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, UserNotificationHandler> userNotificationHandlers =
			_userNotificationHandlers.get(selector);

		if (userNotificationHandlers == null) {
			return null;
		}

		UserNotificationHandler userNotificationHandler =
			userNotificationHandlers.get(userNotificationEvent.getType());

		if (userNotificationHandler == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No interpreter found for " + userNotificationEvent);
			}

			return null;
		}

		return userNotificationHandler.interpret(
			userNotificationEvent, serviceContext);
	}

	private boolean _isDeliver(
			long userId, String selector, String portletId, long classNameId,
			int notificationType, int deliveryType,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, UserNotificationHandler> userNotificationHandlers =
			_userNotificationHandlers.get(selector);

		if (userNotificationHandlers == null) {
			return false;
		}

		UserNotificationHandler userNotificationHandler =
			userNotificationHandlers.get(portletId);

		if (userNotificationHandler == null) {
			if (deliveryType == UserNotificationDeliveryConstants.TYPE_EMAIL) {
				return true;
			}

			return false;
		}

		return userNotificationHandler.isDeliver(
			userId, classNameId, notificationType, deliveryType,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationManagerUtil.class);

	private static final UserNotificationManagerUtil _instance =
		new UserNotificationManagerUtil();

	private final Map<String, List<UserNotificationDefinition>>
		_userNotificationDefinitions =
			new ConcurrentHashMap<String, List<UserNotificationDefinition>>();
	private final StringServiceRegistrationMap<UserNotificationDefinition>
		_userNotificationDefinitionServiceRegistrations =
			new StringServiceRegistrationMap<UserNotificationDefinition>();
	private final ServiceTracker
		<UserNotificationDefinition, UserNotificationDefinition>
			_userNotificationDefinitionServiceTracker;
	private final Map<String, Map<String, UserNotificationHandler>>
		_userNotificationHandlers = new ConcurrentHashMap
			<String, Map<String, UserNotificationHandler>>();
	private final ServiceRegistrationMap<UserNotificationHandler>
		_userNotificationHandlerServiceRegistrations =
			new ServiceRegistrationMap<UserNotificationHandler>();
	private final
		ServiceTracker<UserNotificationHandler, UserNotificationHandler>
			_userNotificationHandlerServiceTracker;

	private class UserNotificationDefinitionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<UserNotificationDefinition, UserNotificationDefinition> {

		@Override
		public UserNotificationDefinition addingService(
			ServiceReference<UserNotificationDefinition> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			UserNotificationDefinition userNotificationDefinition =
				registry.getService(serviceReference);

			List<UserNotificationDefinition> userNotificationDefinitions =
				_userNotificationDefinitions.get(portletId);

			if (userNotificationDefinitions == null) {
				userNotificationDefinitions =
					new ArrayList<UserNotificationDefinition>();

				_userNotificationDefinitions.put(
					portletId, userNotificationDefinitions);
			}

			userNotificationDefinitions.add(userNotificationDefinition);

			return userNotificationDefinition;
		}

		@Override
		public void modifiedService(
			ServiceReference<UserNotificationDefinition> serviceReference,
			UserNotificationDefinition userNotificationHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<UserNotificationDefinition> serviceReference,
			UserNotificationDefinition userNotificationHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			List<UserNotificationDefinition> userNotificationDefinitions =
				_userNotificationDefinitions.get(portletId);

			if (userNotificationDefinitions != null) {
				userNotificationDefinitions.remove(userNotificationHandler);

				if (userNotificationDefinitions.isEmpty()) {
					_userNotificationDefinitions.remove(portletId);
				}
			}
		}

	}

	private class UserNotificationHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<UserNotificationHandler, UserNotificationHandler> {

		@Override
		public UserNotificationHandler addingService(
			ServiceReference<UserNotificationHandler> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			UserNotificationHandler userNotificationHandler =
				registry.getService(serviceReference);

			String selector = userNotificationHandler.getSelector();

			Map<String, UserNotificationHandler> userNotificationHandlers =
				_userNotificationHandlers.get(selector);

			if (userNotificationHandlers == null) {
				userNotificationHandlers =
					new HashMap<String, UserNotificationHandler>();

				_userNotificationHandlers.put(
					selector, userNotificationHandlers);
			}

			userNotificationHandlers.put(
				userNotificationHandler.getPortletId(),
				userNotificationHandler);

			return userNotificationHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<UserNotificationHandler> serviceReference,
			UserNotificationHandler userNotificationHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<UserNotificationHandler> serviceReference,
			UserNotificationHandler userNotificationHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			Map<String, UserNotificationHandler> userNotificationHandlers =
				_userNotificationHandlers.get(
					userNotificationHandler.getSelector());

			if (userNotificationHandlers == null) {
				return;
			}

			userNotificationHandlers.remove(
				userNotificationHandler.getPortletId());
		}

	}

}