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

package com.liferay.portal.kernel.configuration.module;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

/**
 * @author Jorge Ferrer
 */
public class ModuleConfigurationFactoryUtil {

	public static <T> T getModuleConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ModuleConfigurationException {

		ModuleConfigurationFactory moduleConfigurationFactory =
			getModuleConfigurationFactory();

		return moduleConfigurationFactory.getModuleConfiguration(
			clazz, settingsLocator);
	}

	public static ModuleConfigurationFactory getModuleConfigurationFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ModuleConfigurationFactoryUtil.class);

		return _moduleConfigurationFactories.get(0);
	}

	private static final ServiceTrackerList<ModuleConfigurationFactory>
		_moduleConfigurationFactories = ServiceTrackerCollections.list(
			ModuleConfigurationFactory.class);

}