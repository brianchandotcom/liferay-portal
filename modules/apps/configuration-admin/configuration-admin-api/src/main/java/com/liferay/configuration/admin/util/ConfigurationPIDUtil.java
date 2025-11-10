/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Thiago Buarque
 */
public class ConfigurationPIDUtil {

	public static String getRawPid(String pid) {

		// Factory entry: com.liferay.MyConfiguration~1234
		// Factory scoped entry: com.liferay.MyConfiguration.scoped~1234
		// Raw: com.liferay.MyConfiguration
		// Scoped: com.liferay.MyConfiguration.scoped

		pid = pid.replaceFirst("~.*", StringPool.BLANK);

		if (pid.endsWith(_DOT_SCOPED)) {
			pid = pid.substring(0, pid.length() - _DOT_SCOPED.length());
		}

		return pid;
	}

	private static final String _DOT_SCOPED = ".scoped";

}