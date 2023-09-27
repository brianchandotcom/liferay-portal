/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.util.PropsFiles;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationFactoryImpl implements ConfigurationFactory {

	public static final Configuration CONFIGURATION_PORTAL;

	static {
		ClassLoader classLoader = PropsUtil.class.getClassLoader();

		Class<?> clazz = classLoader.getClass();

		ClassLoader classLoaderClassLoader = clazz.getClassLoader();

		if (classLoaderClassLoader != null) {
			classLoader = AggregateClassLoader.getAggregateClassLoader(
				classLoader, classLoaderClassLoader);
		}

		CONFIGURATION_PORTAL = new ConfigurationImpl(
			classLoader, PropsFiles.PORTAL, CompanyConstants.SYSTEM, null);
	}

	@Override
	public Configuration getConfiguration(
		ClassLoader classLoader, String name) {

		if (classLoader.getResource(name + ".properties") == null) {
			return null;
		}

		return new ConfigurationImpl(
			classLoader, name, CompanyConstants.SYSTEM, null);
	}

}