/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.module.configuration.configurator.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.module.configuration.configurator.internal.TestFactoryConfiguration",
	service = {}
)
public class TestFactoryConfigurationConsumer {

	@Activate
	protected void activate(ComponentContext componentContext) {
		TestFactoryConfiguration testFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				TestFactoryConfiguration.class,
				componentContext.getProperties());

		if (testFactoryConfiguration.enabled()) {
			BundleContext bundleContext = componentContext.getBundleContext();

			_serviceRegistration = bundleContext.registerService(
				Object.class, new Object(),
				MapUtil.singletonDictionary(
					"type", testFactoryConfiguration.type()));
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private ServiceRegistration<?> _serviceRegistration;

}