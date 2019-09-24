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