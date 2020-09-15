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

package com.liferay.poshi.runner.util;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class JSONUtil {

	public static String formatJSONString(String json) {
		JSONObject jsonObject = toJSONObject(json);

		return jsonObject.toString();
	}

	public static Object get(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.opt(name);
	}

	public static boolean getBoolean(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optBoolean(name);
	}

	public static double getDouble(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optDouble(name);
	}

	public static int getInt(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optInt(name);
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optJSONArray(name);
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optJSONObject(name);
	}

	public static long getLong(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optLong(name);
	}

	public static String getString(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optString(name);
	}

	public static String getWithJSONPath(String jsonString, String jsonPath) {
		DocumentContext documentContext = JsonPath.parse(jsonString);

		Object object = documentContext.read(jsonPath);

		if (object == null) {
			throw new RuntimeException(
				"Invalid JSON path " + jsonPath + " in " + jsonString);
		}

		return object.toString();
	}

	public static JSONArray toJSONArray(String json) {
		try {
			return new JSONArray(json);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException("Invalid JSON: '" + json + "'");
		}
	}

	public static JSONObject toJSONObject(String json) {
		try {
			return new JSONObject(json);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException("Invalid JSON: '" + json + "'");
		}
	}

}