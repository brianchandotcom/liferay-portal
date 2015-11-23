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

package com.liferay.portal.kernel.module.configuration;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationFactoryUtil {

	public static <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		ConfigurationFactory configurationFactory = getConfigurationFactory();

		return configurationFactory.getConfiguration(clazz, settingsLocator);
	}

	public static ConfigurationFactory getConfigurationFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ConfigurationFactoryUtil.class);

		return _configurationFactories.get(0);
	}

	private static final ServiceTrackerList<ConfigurationFactory>
		_configurationFactories = ServiceTrackerCollections.openList(
			ConfigurationFactory.class);

}