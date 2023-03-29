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

package com.liferay.osb.faro.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class DocumentationConstants {

	public static final String BASE_URL =
		"https://learn.liferay.com/analytics-cloud/latest/en/";

	public static final String DATA_SOURCE_ADD_LIFERAY = BASE_URL.concat(
		"connecting_data_sources.html");

	public static Map<String, String> getURLs() {
		return _urls;
	}

	private static final Map<String, String> _urls =
		new HashMap<String, String>() {
			{
				put("addLiferayDataSource", DATA_SOURCE_ADD_LIFERAY);
				put("base", BASE_URL);
			}
		};

}