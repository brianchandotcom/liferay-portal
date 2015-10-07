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

package com.liferay.exportimport.xstream.configurator;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.Set;

/**
 * @author Mate Thurzo
 */
public class XStreamConfiguratorRegistryUtil {

	public static Set<XStreamConfigurator> getXStreamConfigurators() {
		return _instance._getXStreamConfigurators();
	}

	public static void register(XStreamConfigurator xStreamConfigurator) {
		_instance._register(xStreamConfigurator);
	}

	public static void unregister(XStreamConfigurator xStreamConfigurator) {
		_instance._unregister(xStreamConfigurator);
	}

	private XStreamConfiguratorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<XStreamConfigurator>)(Class<?>)
				XStreamConfigurator.class,
			new XStreamConfiguratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Set<XStreamConfigurator> _getXStreamConfigurators() {
		return _xStreamConfigurators;
	}

	private void _register(XStreamConfigurator xStreamConfigurator) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<XStreamConfigurator>serviceRegistration =
			registry.registerService(
				(Class<XStreamConfigurator>)(Class<?>)XStreamConfigurator.class,
				xStreamConfigurator);

		_serviceRegistrations.put(xStreamConfigurator, serviceRegistration);
	}

	private void _unregister(XStreamConfigurator xStreamConfigurator) {
		ServiceRegistration<XStreamConfigurator> serviceRegistration =
			_serviceRegistrations.remove(xStreamConfigurator);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final XStreamConfiguratorRegistryUtil
		_instance = new XStreamConfiguratorRegistryUtil();

	private final ServiceRegistrationMap<XStreamConfigurator>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker<XStreamConfigurator, XStreamConfigurator>
		_serviceTracker;
	private final Set<XStreamConfigurator>
		_xStreamConfigurators = new ConcurrentHashSet<>();

	private class XStreamConfiguratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<XStreamConfigurator, XStreamConfigurator> {

		@Override
		public XStreamConfigurator addingService(
			ServiceReference<XStreamConfigurator>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			XStreamConfigurator xStreamConfigurator = registry.getService(
				serviceReference);

			_xStreamConfigurators.add(xStreamConfigurator);

			return xStreamConfigurator;
		}

		@Override
		public void modifiedService(
			ServiceReference<XStreamConfigurator> serviceReference,
			XStreamConfigurator xStreamConfigurator) {
		}

		@Override
		public void removedService(
			ServiceReference<XStreamConfigurator> serviceReference,
			XStreamConfigurator xStreamConfigurator) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_xStreamConfigurators.remove(xStreamConfigurator);
		}

	}

}