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

package com.liferay.portal.tools.bundle.support.internal.util;

import java.util.Properties;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupportUtil {

	public static String getDeployDirName(String fileName) {
		if (fileName.endsWith(".jar")) {
			return "osgi/modules/";
		}

		if (fileName.endsWith(".war")) {
			return "osgi/war/";
		}

		return "deploy/";
	}

	public static Integer setSystemProperty(String key, Integer value) {
		String valueString = null;

		if (value != null) {
			valueString = value.toString();
		}

		valueString = setSystemProperty(key, valueString);

		if ((valueString == null) || valueString.isEmpty()) {
			return null;
		}

		return Integer.valueOf(valueString);
	}

	public static String setSystemProperty(String key, String value) {
		String oldValue = System.getProperty(key);

		if (value == null) {
			Properties properties = System.getProperties();

			properties.remove(key);
		}
		else {
			System.setProperty(key, value);
		}

		return oldValue;
	}

}