/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lcs.util;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Ivica Cardic
 */
public class SystemEnvironmentUtil {

	public static final String OSB_LCS_GATEWAY_WEB_HOST_NAME =
		"OSB_LCS_GATEWAY_WEB_HOST_NAME";

	public static final String OSB_LCS_GATEWAY_WEB_HOST_PORT =
		"OSB_LCS_GATEWAY_WEB_HOST_PORT";

	public static final String OSB_LCS_GATEWAY_WEB_PROTOCOL =
		"OSB_LCS_GATEWAY_WEB_PROTOCOL";

	public static final String OSB_LCS_PORTLET_HOST_NAME =
		"OSB_LCS_PORTLET_HOST_NAME";

	public static final String OSB_LCS_PORTLET_HOST_PORT =
		"OSB_LCS_PORTLET_HOST_PORT";

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY =
		"OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY";

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET =
		"OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET";

	public static final String OSB_LCS_PORTLET_PROTOCOL =
		"OSB_LCS_PORTLET_PROTOCOL";

	public static String getValue(String name, String defaultValue) {
		String value = System.getenv(name);

		if (Validator.isNull(value)) {
			return defaultValue;
		}

		return value;
	}

}