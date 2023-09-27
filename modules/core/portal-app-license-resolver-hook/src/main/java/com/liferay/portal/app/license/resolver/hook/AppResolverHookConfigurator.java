/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.app.license.resolver.hook;

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