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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Drew Brokke
 */
public class ConfigurationVisibilityUtil {

	public static boolean isVisible(
		ConfigurationModel configurationModel,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (configurationModel.isStrictScope() &&
			!scope.equals(configurationModel.getScope())) {

			return false;
		}

		return isVisible(configurationModel.getBaseID(), scope, scopePK);
	}

	public static boolean isVisible(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		ConfigurationVisibilityController configurationVisibilityController =
			_getConfigurationVisibilityController(pid);

		return configurationVisibilityController.isVisible(scope, scopePK);
	}

	private static ConfigurationVisibilityController
		_getConfigurationVisibilityController(String pid) {

		if (_serviceTrackerMap.containsKey(pid)) {
			return _serviceTrackerMap.getService(pid);
		}

		return _configurationVisibilityController;
	}

	private static final ConfigurationVisibilityController
		_configurationVisibilityController = (scope, scopePK) -> true;
	private static final ServiceTrackerMap
		<String, ConfigurationVisibilityController> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationVisibilityController.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), ConfigurationVisibilityController.class,
			"configuration.pid");
	}

}