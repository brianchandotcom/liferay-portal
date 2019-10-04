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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jonathan Lee
 * @author Roberto Díaz
 */
public class UserNotificationManagerUtil {

	public static void addUserNotificationDefinition(
		String portletId,
		UserNotificationDefinition userNotificationDefinition) {

		_userNotificationManagerUtil._addUserNotificationDefinition(
			portletId, userNotificationDefinition);
	}

	public static void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		_userNotificationManagerUtil._addUserNotificationHandler(
			userNotificationHandler);
	}

	public static void deleteUserNotificationDefinitions(String portletId) {
		_userNotificationManagerUtil._deleteUserNotificationDefinitions(
			portletId);
	}

	public static void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		_userNotificationManagerUtil._deleteUserNotificationHandler(
			userNotificationHandler);
	}

	public static UserNotificationDefinition fetchUserNotificationDefinition(
		String portletId, long classNameId, int notificationType) {

		return _userNotificationManagerUtil._fetchUserNotificationDefinition(
			portletId, classNameId, notificationType);
	}

	public static Map<String, List<UserNotificationDefinition>>
		getActiveUserNotificationDefinitions() {

		return _userNotificationManagerUtil._getUserNotificationDefinitions(
			true);
	}

	public static Map<String, List<UserNotificationDefinition>>
		getUserNotificationDefinitions() {

		return _userNotificationManagerUtil._getUserNotificationDefinitions(
			false);
	}

	public static Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers() {

		return Collections.unmodifiableMap(
			_userNotificationManagerUtil._userNotificationHandlers);
	}

	public static UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		return _userNotificationManagerUtil._interpret(
			selector, userNotificationEvent, serviceContext);
	}

	public static boolean isDeliver(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws PortalException {

		return _userNotificationManagerUtil._isDeliver(
			userId, StringPool.BLANK, portletId, classNameId, notificationType,
			deliveryType, null);
	}

	public static boolean isDeliver(
			long userId, String selector, String portletId, long classNameId,
			int notificationType, int deliveryType,
			ServiceContext serviceContext)
		throws PortalException {

		return _userNotificationManagerUtil._isDeliver(
			userId, selector, portletId, classNameId, notificationType,
			deliveryType, serviceContext);
	}

	private UserNotificationManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_userNotificationHandlerServiceTracker = registry.trackServices(
			UserNotificationHandler.class,
			new UserNotificationHandlerServiceTrackerCustomizer());

		_userNotificationHandlerServiceTracker.open();
	}

	private void _addUserNotificationDefinition(
		String portletId,
		UserNotificationDefinition userNotificationDefinition) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("javax.portlet.name", portletId);

		ServiceRegistration<UserNotificationDefinition> serviceRegistration =
			registry.registerService(
				UserNotificationDefinition.class, userNotificationDefinition,
				properties);

		List<ServiceRegistration<UserNotificationDefinition>>
			serviceRegistrations = new ArrayList<>();

		List<ServiceRegistration<UserNotificationDefinition>>
			userNotificationServiceRegistrations =
				_userNotificationDefinitionServiceRegistrations.get(portletId);

		if ((userNotificationServiceRegistrations != null) &&
			!userNotificationServiceRegistrations.isEmpty()) {

			serviceRegistrations.addAll(userNotificationServiceRegistrations);
		}

		serviceRegistrations.add(serviceRegistration);

		_userNotificationDefinitionServiceRegistrations.put(
			portletId, serviceRegistrations);
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
		List<ServiceRegistration<UserNotificationDefinition>>
			serviceRegistrations =
				_userNotificationDefinitionServiceRegistrations.get(portletId);

		for (ServiceRegistration<UserNotificationDefinition>
				serviceRegistration : serviceRegistrations) {

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
			_userNotificationDefinitions.getService(portletId);

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

	private Map<String, List<UserNotificationDefinition>>
		_getUserNotificationDefinitions(boolean active) {

		Map<String, List<UserNotificationDefinition>>
			userNotificationDefinitionsMap = new ConcurrentHashMap<>();

		ServiceTrackerMap<String, List<UserNotificationDefinition>>
			userNotificationDefinitionsServiceTrackerMap =
				_userNotificationManagerUtil._userNotificationDefinitions;

		for (String portletId :
				userNotificationDefinitionsServiceTrackerMap.keySet()) {

			if (active) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					portletId);

				if (portlet == null) {
					continue;
				}
			}

			userNotificationDefinitionsMap.put(
				portletId,
				userNotificationDefinitionsServiceTrackerMap.getService(
					portletId));
		}

		return Collections.unmodifiableMap(userNotificationDefinitionsMap);
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

	private static final UserNotificationManagerUtil
		_userNotificationManagerUtil = new UserNotificationManagerUtil();

	private final ServiceTrackerMap<String, List<UserNotificationDefinition>>
		_userNotificationDefinitions =
			ServiceTrackerCollections.openMultiValueMap(
				UserNotificationDefinition.class, "javax.portlet.name");
	private final ConcurrentHashMap
		<String, List<ServiceRegistration<UserNotificationDefinition>>>
			_userNotificationDefinitionServiceRegistrations =
				new ConcurrentHashMap<>();
	private final Map<String, Map<String, UserNotificationHandler>>
		_userNotificationHandlers = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<UserNotificationHandler>
		_userNotificationHandlerServiceRegistrations =
			new ServiceRegistrationMapImpl<>();
	private final ServiceTracker
		<UserNotificationHandler, UserNotificationHandler>
			_userNotificationHandlerServiceTracker;

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
				userNotificationHandlers = new HashMap<>();

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