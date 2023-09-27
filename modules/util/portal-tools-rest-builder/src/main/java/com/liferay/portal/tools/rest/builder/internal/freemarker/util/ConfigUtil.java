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

	public static boolean isVersionCompatible(
		ConfigYAML configYAML, int version) {

		if (configYAML.getCompatibilityVersion() >= version) {
			return true;
		}

		return false;
	}

}