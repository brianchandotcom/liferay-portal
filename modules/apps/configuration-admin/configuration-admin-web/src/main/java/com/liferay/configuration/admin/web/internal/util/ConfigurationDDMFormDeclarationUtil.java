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

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationDDMFormDeclarationUtil {

	public static Class<?> getConfigurationDDMFormClass(
		ConfigurationModel configurationModel) {

		String pid = configurationModel.getFactoryPid();

		if (Validator.isNull(pid)) {
			pid = configurationModel.getID();
		}

		return getConfigurationDDMFormClass(pid);
	}

	public static Class<?> getConfigurationDDMFormClass(String pid) {
		ConfigurationDDMFormDeclaration configurationDDMFormDeclaration =
			_serviceTrackerMap.getService(pid);

		if (configurationDDMFormDeclaration != null) {
			return configurationDDMFormDeclaration.getDDMFormClass();
		}

		return null;
	}

	private static final ServiceTrackerMap
		<String, ConfigurationDDMFormDeclaration> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationDDMFormDeclarationUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ConfigurationDDMFormDeclaration.class,
			"configurationPid");
	}

}