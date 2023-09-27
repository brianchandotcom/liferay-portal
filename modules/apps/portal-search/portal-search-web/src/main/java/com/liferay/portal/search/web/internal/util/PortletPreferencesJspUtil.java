/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.settings.ParameterMapSettings;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesJspUtil {

	public static String getInputName(String key) {
		return ParameterMapSettings.PREFERENCES_PREFIX + key +
			StringPool.DOUBLE_DASH;
	}

}