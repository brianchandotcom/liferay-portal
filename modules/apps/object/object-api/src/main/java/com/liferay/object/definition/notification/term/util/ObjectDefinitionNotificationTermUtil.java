/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.notification.term.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectDefinitionNotificationTermUtil {

	public static String getObjectFieldTermName(
		String termNamePrefix, String termNameSuffix) {

		return StringBundler.concat(
			"[%", StringUtil.toUpperCase(termNamePrefix + "_" + termNameSuffix),
			"%]");
	}

}