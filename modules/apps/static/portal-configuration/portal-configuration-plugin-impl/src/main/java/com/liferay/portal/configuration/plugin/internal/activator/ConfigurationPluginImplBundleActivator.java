/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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