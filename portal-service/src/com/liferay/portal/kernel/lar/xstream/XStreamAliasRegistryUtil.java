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

package com.liferay.portal.kernel.lar.xstream;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mate Thurzo
 */
public class XStreamAliasRegistryUtil {

	public static Map<Class<?>, String> getAliases() {
		return _instance._getAliases();
	}

	public static void register(Class<?> clazz, String alias) {
		_instance._register(clazz, alias);
	}

	public static void unregister(Class<?> clazz, String alias) {
		_instance._unregister(clazz, alias);
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

	private void _register(Class<?> clazz, String alias) {
		XStreamAlias xStreamAlias = new XStreamAlias(clazz, alias);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<XStreamAlias> serviceRegistration =
			registry.registerService(XStreamAlias.class, xStreamAlias);

		_serviceRegistrations.put(xStreamAlias, serviceRegistration);
	}

	private void _unregister(Class<?> clazz, String alias) {
		XStreamAlias xStreamAlias = new XStreamAlias(clazz, alias);

		ServiceRegistration<XStreamAlias> serviceRegistration =
			_serviceRegistrations.remove(xStreamAlias);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static XStreamAliasRegistryUtil _instance =
		new XStreamAliasRegistryUtil();

	private ServiceRegistrationMap<XStreamAlias> _serviceRegistrations =
		new ServiceRegistrationMap<XStreamAlias>();
	private ServiceTracker<XStreamAlias, XStreamAlias> _serviceTracker;
	private Map<Class<?>, String> _xstreamAliases =
		new ConcurrentHashMap<Class<?>, String>();

	private class XStreamAlias {

		public XStreamAlias(Class<?> clazz, String alias) {
			_aliasClass = clazz;
			_aliasName = alias;
		}

		public Class<?> getAliasClass() {
			return _aliasClass;
		}

		public String getAliasName() {
			return _aliasName;
		}

		private Class<?> _aliasClass;
		private String _aliasName;

	}

	private class XStreamAliasServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<XStreamAlias, XStreamAlias> {

		@Override
		public XStreamAlias addingService(
			ServiceReference<XStreamAlias> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			XStreamAlias xStreamAlias = registry.getService(serviceReference);

			_xstreamAliases.put(
				xStreamAlias.getAliasClass(), xStreamAlias.getAliasName());

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

			_xstreamAliases.remove(xStreamAlias.getAliasClass());
		}

	}

}