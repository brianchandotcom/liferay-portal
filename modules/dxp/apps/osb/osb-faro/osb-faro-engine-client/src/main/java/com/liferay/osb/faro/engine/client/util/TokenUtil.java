/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.util;

import com.liferay.osb.faro.util.FaroPropsValues;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Leilany Ulisses
 * @author Marcos Martins
 */
public class TokenUtil {

	public static String getOSBAsahSecurityToken() {
		if (StringUtils.isNotBlank(FaroPropsValues.OSB_ASAH_SECURITY_TOKEN)) {
			return FaroPropsValues.OSB_ASAH_SECURITY_TOKEN;
		}

		return FaroPropsValues.OSB_ASAH_TOKEN;
	}

}