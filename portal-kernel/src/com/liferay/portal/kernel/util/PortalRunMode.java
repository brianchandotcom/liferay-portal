/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Michael C. Han
 */
public class PortalRunMode {

	public static boolean isTestMode() {
		String liferayMode = SystemProperties.get("liferay.mode");

		if (Validator.isNotNull(liferayMode) && liferayMode.equals("test")) {
			return true;
		}

		return false;
	}

}