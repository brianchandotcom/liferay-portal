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

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.util.StringPlus;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gergely Mathe
 */
public class PermissionUpdateHandlerRegistryUtil {

	public static PermissionUpdateHandler getPermissionUpdateHandler(
		String modelClassName) {

		return _permissionUpdateHandlerRegistryUtil._getPermissionUpdateHandler(
			modelClassName);
	}

	public static List<PermissionUpdateHandler> getPermissionUpdateHandlers() {
		return _permissionUpdateHandlerRegistryUtil.
			_getPermissionUpdateHandlers();
	}

	public static void register(
		PermissionUpdateHandler permissionUpdateHandler) {

		_permissionUpdateHandlerRegistryUtil._register(permissionUpdateHandler);
	}

	public static void unregister(
		List<PermissionUpdateHandler> permissionUpdateHandlers) {

		for (PermissionUpdateHandler permissionUpdateHandler :
				permissionUpdateHandlers) {

			unregister(permissionUpdateHandler);
		}
	}

	public static void unregister(
		PermissionUpdateHandler permissionUpdateHandler) {

		_permissionUpdateHandlerRegistryUtil._unregister(
			permissionUpdateHandler);
	}

	private PermissionUpdateHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<PermissionUpdateHandler>)
				(Class<?>)PermissionUpdateHandler.class,
			new PermissionUpdateHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private PermissionUpdateHandler _getPermissionUpdateHandler(
		String modelClassName) {

		return _permissionUpdateHandlers.get(modelClassName);
	}

	private List<PermissionUpdateHandler> _getPermissionUpdateHandlers() {
		Collection<PermissionUpdateHandler> values =
			_permissionUpdateHandlers.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(PermissionUpdateHandler permissionUpdateHandler) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<PermissionUpdateHandler> serviceRegistration =
			registry.registerService(
				(Class<PermissionUpdateHandler>)
					(Class<?>)PermissionUpdateHandler.class,
				permissionUpdateHandler);

		_serviceRegistrations.put(permissionUpdateHandler, serviceRegistration);
	}

	private void _unregister(PermissionUpdateHandler permissionUpdateHandler) {
		ServiceRegistration<PermissionUpdateHandler> serviceRegistration =
			_serviceRegistrations.remove(permissionUpdateHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final PermissionUpdateHandlerRegistryUtil
		_permissionUpdateHandlerRegistryUtil =
			new PermissionUpdateHandlerRegistryUtil();

	private final Map<String, PermissionUpdateHandler>
		_permissionUpdateHandlers = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<PermissionUpdateHandler>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker
		<PermissionUpdateHandler, PermissionUpdateHandler> _serviceTracker;

	private class PermissionUpdateHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PermissionUpdateHandler, PermissionUpdateHandler> {

		@Override
		public PermissionUpdateHandler addingService(
			ServiceReference<PermissionUpdateHandler> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PermissionUpdateHandler permissionUpdateHandler =
				registry.getService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_permissionUpdateHandlers.put(
					modelClassName, permissionUpdateHandler);
			}

			return permissionUpdateHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<PermissionUpdateHandler> serviceReference,
			PermissionUpdateHandler permissionUpdateHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<PermissionUpdateHandler> serviceReference,
			PermissionUpdateHandler permissionUpdateHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_permissionUpdateHandlers.remove(modelClassName);
			}
		}

	}

}