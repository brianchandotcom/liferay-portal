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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.util.StringPlus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AuthTokenIgnoreActionsRegistry {

	public static Set<String> getAuthTokenIgnoreActions() {
		return _instance._getAuthTokenIgnoreActions();
	}

	public static void register(String... tokenIgnoreActions) {
		_instance._register(tokenIgnoreActions);
	}

	public static void unregister() {
		_instance._unregister();
	}

	public static void unregister(String... tokenIgnoreActions) {
		_instance._unregister(tokenIgnoreActions);
	}

	private AuthTokenIgnoreActionsRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&("+PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS+"=*)"+
					"(objectClass=java.lang.Object))"),
			new AuthTokenIgnoreActionsCustomizer());

		_serviceTracker.open();
	}

	private Set<String> _getAuthTokenIgnoreActions() {
		return _tokenIgnoreActions;
	}

	private void _register(String... tokenIgnoreActions) {
		Registry registry = RegistryUtil.getRegistry();

		for (String tokenIgnoreAction : tokenIgnoreActions) {
			Map<String, Object> properties = new HashMap<>();

			properties.put(
				PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, tokenIgnoreAction);

			properties.put("objectClass", Object.class.getName());

			ServiceRegistration<Object> serviceRegistration =
				registry.registerService(
					Object.class, new Object(), properties);

			_serviceRegistrations.put(tokenIgnoreAction, serviceRegistration);
		}
	}

	private void _unregister() {
		Set<String> tokenIgnoreActions = _serviceRegistrations.keySet();

		String[] tokenIgnoreActionsArray = tokenIgnoreActions.toArray(
			new String[tokenIgnoreActions.size()]);

		_unregister(tokenIgnoreActionsArray);
	}

	private void _unregister(String... tokenIgnoreActions) {
		for (String tokenIgnoreAction : tokenIgnoreActions) {
			ServiceRegistration<Object> serviceRegistration =
				_serviceRegistrations.remove(tokenIgnoreAction);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private static final AuthTokenIgnoreActionsRegistry _instance =
		new AuthTokenIgnoreActionsRegistry();

	private final StringServiceRegistrationMap<Object> _serviceRegistrations =
		new StringServiceRegistrationMap<>();
	private final ServiceTracker<Object, Object> _serviceTracker;
	private final Set<String> _tokenIgnoreActions = new ConcurrentHashSet<>();

	private class AuthTokenIgnoreActionsCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> tokenIgnoreActions = StringPlus.asList(
				serviceReference.getProperty(
					PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));

			for (String tokenIgnoreAction : tokenIgnoreActions) {
				_tokenIgnoreActions.add(tokenIgnoreAction);
			}

			Registry registry = RegistryUtil.getRegistry();

			return registry.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			List<String> tokenIgnoreActions = StringPlus.asList(
				serviceReference.getProperty(
					PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));

			for (String tokenIgnoreAction : tokenIgnoreActions) {
				_tokenIgnoreActions.remove(tokenIgnoreAction);
			}
		}

	}

}