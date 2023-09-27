/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * @author Bryan Engler
 */
public class JSONUtil {

	public static String getPrettyPrintedJSONString(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.setPrettyPrinting();

		Gson gson = gsonBuilder.create();

		JsonParser jsonParser = new JsonParser();

		return gson.toJson(jsonParser.parse(object.toString()));
	}

}