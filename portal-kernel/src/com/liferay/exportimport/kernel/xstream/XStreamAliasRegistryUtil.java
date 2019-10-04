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

package com.liferay.exportimport.kernel.xstream;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Máté Thurzó
 */
public class XStreamAliasRegistryUtil {

	public static Map<Class<?>, String> getAliases() {
		return new HashMap<>(_xStreamAliasRegistryUtil._getAliases());
	}

	public static void register(Class<?> clazz, String name) {
		_xStreamAliasRegistryUtil._register(clazz, name);
	}

	public static void unregister(Class<?> clazz, String name) {
		_xStreamAliasRegistryUtil._unregister(clazz, name);
	}

	private XStreamAliasRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			XStreamAlias.class, new XStreamAliasServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Map<Class<?>, String> _getAliases() {
		return _xstreamAliases;
	}

	private void _register(Class<?> clazz, String name) {
		XStreamAlias xStreamAlias = new XStreamAlias(clazz, name);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<XStreamAlias> serviceRegistration =
			registry.registerService(XStreamAlias.class, xStreamAlias);

		_serviceRegistrations.put(xStreamAlias, serviceRegistration);
	}

	private void _unregister(Class<?> clazz, String name) {
		XStreamAlias xStreamAlias = new XStreamAlias(clazz, name);

		ServiceRegistration<XStreamAlias> serviceRegistration =
			_serviceRegistrations.remove(xStreamAlias);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final XStreamAliasRegistryUtil _xStreamAliasRegistryUtil =
		new XStreamAliasRegistryUtil();

	private final ServiceRegistrationMap<XStreamAlias> _serviceRegistrations =
		new ServiceRegistrationMapImpl<>();
	private final ServiceTracker<XStreamAlias, XStreamAlias> _serviceTracker;
	private final Map<Class<?>, String> _xstreamAliases =
		new ConcurrentHashMap<>();

	private class XStreamAliasServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<XStreamAlias, XStreamAlias> {

		@Override
		public XStreamAlias addingService(
			ServiceReference<XStreamAlias> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			XStreamAlias xStreamAlias = registry.getService(serviceReference);

			_xstreamAliases.put(
				xStreamAlias.getClazz(), xStreamAlias.getName());

			return xStreamAlias;
		}

		@Override
		public void modifiedService(
			ServiceReference<XStreamAlias> serviceReference,
			XStreamAlias xStreamAlias) {
		}

		@Override
		public void removedService(
			ServiceReference<XStreamAlias> serviceReference,
			XStreamAlias xStreamAlias) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_xstreamAliases.remove(xStreamAlias.getClazz());
		}

	}

}