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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
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
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class AuthPublicPathRegistry {

	public static boolean contains(String path) {
		return _instance._contains(path);
	}

	public static void register(String... paths) {
		_instance._register(paths);
	}

	public static void unregister(String... paths) {
		_instance._unregister(paths);
	}

	private AuthPublicPathRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(auth.public.path=*)(objectClass=java.lang.Object))"),
			new AuthPublicTrackerCustomizer());

		_serviceTracker.open();
	}

	private boolean _contains(String path) {
		return _paths.contains(path);
	}

	private void _register(String... paths) {
		Registry registry = RegistryUtil.getRegistry();

		for (String path : paths) {
			Map<String, Object> properties = new HashMap<>();

			properties.put("auth.public.path", path);
			properties.put("objectClass", Object.class.getName());

			ServiceRegistration<Object> serviceRegistration =
				registry.registerService(
					Object.class, new Object(), properties);

			_serviceRegistrations.put(path, serviceRegistration);
		}
	}

	private void _unregister(String... paths) {
		for (String path : paths) {
			ServiceRegistration<Object> serviceRegistration =
				_serviceRegistrations.remove(path);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private static final AuthPublicPathRegistry _instance =
		new AuthPublicPathRegistry();

	private final Set<String> _paths = new ConcurrentHashSet<>();
	private final StringServiceRegistrationMap<Object>
		_serviceRegistrations = new StringServiceRegistrationMap<>();
	private final ServiceTracker<Object, Object> _serviceTracker;

	private class AuthPublicTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> paths = StringPlus.asList(
				serviceReference.getProperty("auth.public.path"));

			for (String path : paths) {
				_paths.add(path);
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

			List<String> paths = StringPlus.asList(
				serviceReference.getProperty("auth.public.path"));

			for (String path : paths) {
				_paths.remove(path);
			}
		}

	}

}