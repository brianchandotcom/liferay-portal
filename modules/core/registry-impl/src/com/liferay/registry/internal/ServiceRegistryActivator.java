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

package com.liferay.registry.internal;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
public class ServiceRegistryActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Registry registry = new RegistryImpl(bundleContext);

		RegistryUtil.setRegistry(registry);

		_registryRegistration = bundleContext.registerService(
			Registry.class, registry, null);

		ServiceTrackerMapFactoryImpl serviceTrackerMapFactory =
			new ServiceTrackerMapFactoryImpl(bundleContext);

		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(
			serviceTrackerMapFactory);

		_serviceTrackerMapFactoryRegistration = bundleContext.registerService(
			ServiceTrackerMapFactory.class, serviceTrackerMapFactory, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_registryRegistration.unregister();
		_serviceTrackerMapFactoryRegistration.unregister();

		RegistryUtil.setRegistry(null);

		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(null);
	}

	private ServiceRegistration<Registry> _registryRegistration;
	private ServiceRegistration<ServiceTrackerMapFactory>
		_serviceTrackerMapFactoryRegistration;

}