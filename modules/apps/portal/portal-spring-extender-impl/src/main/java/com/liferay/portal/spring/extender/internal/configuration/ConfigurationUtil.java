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

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;

import java.net.URL;

/**
 * @author Preston Crary
 */
public class ConfigurationUtil {

	public static Configuration getConfiguration(
		ClassLoader classLoader, String name) {

		if (hasConfiguration(classLoader, name)) {
			return ConfigurationFactoryUtil.getConfiguration(classLoader, name);
		}

		return null;
	}

	public static boolean hasConfiguration(
		ClassLoader classLoader, String name) {

		URL url = classLoader.getResource(name + ".properties");

		if (url == null) {
			return false;
		}

		return true;
	}

}