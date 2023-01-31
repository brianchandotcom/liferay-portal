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

package com.liferay.portal.configuration.plugin.internal.activator;

import com.liferay.portal.configuration.plugin.internal.WebIdToCompanyConfigurationPluginImpl;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationPlugin;

/**
 * @author Raymond Augé
 */
public class ConfigurationPluginImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceRegistration = bundleContext.registerService(
			ConfigurationPlugin.class,
			new WebIdToCompanyConfigurationPluginImpl(bundleContext),
			HashMapDictionaryBuilder.<String, Object>put(
				ConfigurationPlugin.CM_RANKING, 400
			).put(
				"config.plugin.id",
				WebIdToCompanyConfigurationPluginImpl.class.getName()
			).build());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	private ServiceRegistration<ConfigurationPlugin> _serviceRegistration;

}