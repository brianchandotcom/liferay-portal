/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.internal.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Joan Kim
 */
public class ContextUtil {

	public static String getContextName(String contextPath) {
		String contextName = contextPath;

		if (contextName.length() == 0) {
			return StringPool.BLANK;
		}

		if (contextName.startsWith(StringPool.FORWARD_SLASH)) {
			contextName = contextName.substring(1);
		}

		if (contextName.endsWith(StringPool.FORWARD_SLASH)) {
			contextName = contextName.substring(0, contextName.length() - 1);
		}

		int index = _getPluginTypeIndex(contextName);

		if (index >= 0) {
			contextName = contextName.substring(0, index);
		}

		return contextName;
	}

	private static int _getPluginTypeIndex(String contextName) {
		for (String pluginType : _PLUGIN_TYPES) {
			int index = contextName.lastIndexOf(pluginType);

			if (index >= 0) {
				return index + pluginType.length();
			}
		}

		return -1;
	}

	private static final String[] _PLUGIN_TYPES = {
		"-ext", "-hook", "-layouttpl", "-portlet", "-theme", "-web"
	};

}