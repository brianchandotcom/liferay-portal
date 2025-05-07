/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;

/**
 * @author Carlos Correa
 */
public class ConfigUtil {

	public static boolean isUseJavax() {
		return _useJavax;
	}

	public static boolean isUseJavax(ConfigYAML configYAML) {
		if (!isVersionCompatible(configYAML, 9) || _useJavax) {
			return true;
		}

		return false;
	}

	public static boolean isVersionCompatible(
		ConfigYAML configYAML, int version) {

		if (configYAML.getCompatibilityVersion() >= version) {
			return true;
		}

		return false;
	}

	public static String packageName(ConfigYAML configYAML) {
		if (isUseJavax(configYAML)) {
			return "javax";
		}

		return "jakarta";
	}

	public static void setUseJavax(boolean useJavax) {
		_useJavax = useJavax;
	}

	private static boolean _useJavax;

}