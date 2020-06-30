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

package com.liferay.commerce.product.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class JsonHelper {

	public static String getFirstElementStringValue(String jsonArrayString) {
		if (!isArray(jsonArrayString)) {
			throw new IllegalArgumentException(
				String.format(
					"%s is not a valid JSON array expression",
					jsonArrayString));
		}

		int start = jsonArrayString.indexOf(StringPool.QUOTE);

		if (start == -1) {
			throw new IndexOutOfBoundsException(
				String.format(
					"%s JSON array has no first element", jsonArrayString));
		}

		return jsonArrayString.substring(
			start + 1, jsonArrayString.indexOf(StringPool.QUOTE, start + 1));
	}

	public static JSONArray getValueAsJSONArray(
		String key, JSONObject jsonObject) {

		JSONArray valueJSONArray = jsonObject.getJSONArray(key);

		if (valueJSONArray != null) {
			return valueJSONArray;
		}

		valueJSONArray = JSONFactoryUtil.createJSONArray();

		String valueString = jsonObject.getString(key);

		if (valueString == null) {
			return valueJSONArray;
		}

		valueJSONArray.put(valueString);

		return valueJSONArray;
	}

	public static boolean isArray(String json) {
		if (Validator.isNull(json)) {
			return false;
		}

		if (json.startsWith(StringPool.OPEN_BRACKET) &&
			json.endsWith(StringPool.CLOSE_BRACKET)) {

			return true;
		}

		return false;
	}

	public static boolean isEmpty(String json) {
		if (Validator.isNull(json)) {
			return true;
		}

		if (Objects.equals(json, "[]") || Objects.equals(json, "{}")) {
			return true;
		}

		return false;
	}

}