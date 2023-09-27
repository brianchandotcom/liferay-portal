/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

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

	private static final Map<String, String> _urls = HashMapBuilder.put(
		"addLiferayDataSource", DATA_SOURCE_ADD_LIFERAY
	).put(
		"base", BASE_URL
	).build();

}