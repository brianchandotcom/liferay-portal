/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.client.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Rafael Praxedes
 */
public class SCIMClientUtil {

	public static String generateSCIMClientId(String applicationName) {
		String clientId = StringUtil.replace(
			StringUtil.toLowerCase(applicationName), CharPool.SPACE,
			CharPool.DASH);

		return "SCIM_" + clientId;
	}

}