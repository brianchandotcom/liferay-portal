/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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