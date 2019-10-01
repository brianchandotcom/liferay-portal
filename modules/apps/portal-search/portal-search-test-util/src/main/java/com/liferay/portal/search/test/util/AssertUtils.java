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

package com.liferay.portal.search.test.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class AssertUtils {

	public static void assertEquals(
		String message, JSONObject expectedJSONObject,
		JSONObject actualJSONObject) {

		String actual = _toString(actualJSONObject);

		Assert.assertEquals(message, _toString(expectedJSONObject), actual);
	}

	public static void assertEquals(
		String message, List<?> expectedList, List<?> actualList) {

		Assert.assertEquals(
			message, expectedList.toString(), actualList.toString());
	}

	public static void assertEquals(
		String message, Map<?, ?> expectedMap, Map<?, ?> actualMap) {

		String actual = _toString(actualMap);

		Assert.assertEquals(
			message + "->" + actual, _toString(expectedMap), actual);
	}

	private static String _toString(JSONArray jsonArray) {
		List<String> list = new ArrayList<>(jsonArray.length());

		jsonArray.forEach(value -> list.add(_toString(value)));

		Collections.sort(list);

		return StringPool.OPEN_BRACKET + StringUtil.merge(list, ",") +
			StringPool.CLOSE_BRACKET;
	}

	private static String _toString(JSONObject jsonObject) {
		List<String> list = new ArrayList<>(jsonObject.length());
		Iterator<String> keys = jsonObject.keys();

		keys.forEachRemaining(
			key -> list.add(
				_toString(key) + ":" + _toString(jsonObject.get(key))));

		Collections.sort(list);

		return StringPool.OPEN_CURLY_BRACE + StringUtil.merge(list, ",") +
			StringPool.CLOSE_CURLY_BRACE;
	}

	private static String _toString(Map<?, ?> map) {
		List<String> list = new ArrayList<>(map.size());

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			list.add(entry.toString());
		}

		Collections.sort(list);

		return list.toString();
	}

	private static String _toString(Object object) {
		if (object instanceof JSONObject) {
			return _toString((JSONObject)object);
		}
		else if (object instanceof JSONArray) {
			return _toString((JSONArray)object);
		}
		else if (object instanceof String) {
			return _toString((String)object);
		}
		else {
			return object.toString();
		}
	}

	private static String _toString(String string) {
		String escapedString = StringUtil.replace(
			string, CharPool.QUOTE, StringPool.BACK_SLASH + StringPool.QUOTE);

		return StringPool.QUOTE + escapedString + StringPool.QUOTE;
	}

}