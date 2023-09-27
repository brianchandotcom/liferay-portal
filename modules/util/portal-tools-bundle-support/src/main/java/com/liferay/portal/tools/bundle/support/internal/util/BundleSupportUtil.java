/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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