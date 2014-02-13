/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.registry.impl;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

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

		_serviceRegistration = bundleContext.registerService(
			Registry.class, registry, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();

		RegistryUtil.setRegistry(null);
	}

	private ServiceRegistration<Registry> _serviceRegistration;

}