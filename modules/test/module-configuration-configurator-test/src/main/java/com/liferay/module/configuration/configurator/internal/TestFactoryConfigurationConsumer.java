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

package com.liferay.module.configuration.configurator.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.module.configuration.configurator.internal.TestFactoryConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
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