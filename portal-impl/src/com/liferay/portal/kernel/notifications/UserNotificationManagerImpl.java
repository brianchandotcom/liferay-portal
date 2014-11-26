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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roberto Díaz
 */
public class UserNotificationManagerImpl implements UserNotificationManager {

	public UserNotificationManagerImpl() {
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

	@Override
	public void addUserNotificationDefinition(
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

	@Override
	public void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<UserNotificationHandler> serviceRegistration =
			registry.registerService(
				UserNotificationHandler.class, userNotificationHandler);

		_userNotificationHandlerServiceRegistrations.put(
			userNotificationHandler, serviceRegistration);
	}

	@Override
	public void deleteUserNotificationDefinitions(String portletId) {
		ServiceRegistration<UserNotificationDefinition> serviceRegistration =
			_userNotificationDefinitionServiceRegistrations.get(portletId);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	public void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		ServiceRegistration<UserNotificationHandler> serviceRegistration =
			_userNotificationHandlerServiceRegistrations.get(
				userNotificationHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	public UserNotificationDefinition fetchUserNotificationDefinition(
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

	@Override
	public Map<String, List<UserNotificationDefinition>>
		getUserNotificationDefinitions() {

		return _userNotificationDefinitions;
	}

	@Override
	public Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers() {

		return _userNotificationHandlers;
	}

	@Override
	public UserNotificationFeedEntry interpret(
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

	@Override
	public boolean isDeliver(
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

	protected static final Log _log = LogFactoryUtil.getLog(
		UserNotificationManagerImpl.class);

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