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

package com.liferay.portal.app.resolver;

import org.eclipse.osgi.internal.hookregistry.ActivatorHookFactory;
import org.eclipse.osgi.internal.hookregistry.HookConfigurator;
import org.eclipse.osgi.internal.hookregistry.HookRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.resolver.ResolverHookFactory;

/**
 * @author Amos Fong
 */
public class AppResolverHookConfigurator
	implements ActivatorHookFactory, BundleActivator, HookConfigurator {

	@Override
	public void addHooks(HookRegistry hookRegistry) {
		hookRegistry.addActivatorHookFactory(this);
	}

	@Override
	public BundleActivator createActivator() {
		return this;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_appResolverHookFactory = new AppResolverHookFactory(bundleContext);

		_serviceRegistration = bundleContext.registerService(
			ResolverHookFactory.class, _appResolverHookFactory, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_appResolverHookFactory.close();
	}

	private AppResolverHookFactory _appResolverHookFactory;
	private ServiceRegistration<ResolverHookFactory> _serviceRegistration;

}