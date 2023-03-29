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

package com.liferay.osb.faro.engine.client.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * @author André Miranda
 */
public class TokenConstants {

	public static final String OSB_ASAH_SECURITY_TOKEN;

	static {
		if (StringUtils.isNotBlank(System.getenv("OSB_ASAH_SECURITY_TOKEN"))) {
			OSB_ASAH_SECURITY_TOKEN = System.getenv("OSB_ASAH_SECURITY_TOKEN");
		}
		else {
			OSB_ASAH_SECURITY_TOKEN = System.getenv("OSB_ASAH_TOKEN");
		}
	}

}