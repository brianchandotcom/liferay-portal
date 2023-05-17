/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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