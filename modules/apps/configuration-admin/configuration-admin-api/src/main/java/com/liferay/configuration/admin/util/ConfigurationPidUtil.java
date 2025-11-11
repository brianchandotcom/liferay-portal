/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Thiago Buarque
 */
public class ConfigurationPidUtil {

	public static String getRawPid(String pid) {

		// Factory entry: com.acme.MyConfiguration~1234
		// Factory scoped entry: com.acme.MyConfiguration.scoped~1234
		// Raw: com.acme.MyConfiguration
		// Scoped: com.acme.MyConfiguration.scoped

		pid = pid.replaceFirst("~.*", StringPool.BLANK);

		if (pid.endsWith(_SUFFIX)) {
			pid = pid.substring(0, pid.length() - _SUFFIX.length());
		}

		return pid;
	}

	private static final String _SUFFIX = ".scoped";

}