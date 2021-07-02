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

		return new ConfigurationImpl(
			classLoader, name, CompanyConstants.SYSTEM, null);
	}

}