/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.function.Supplier;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class AssertUtils {

	public static void assertEquals(
		String message, JSONObject expectedJSONObject,
		JSONObject actualJSONObject) {

		String actual = _toString(actualJSONObject);

		Assert.assertEquals(
			_getMessage(message, actual), _toString(expectedJSONObject),
			actual);
	}

	public static void assertEquals(
		String message, List<?> expectedList, List<?> actualList) {

		String actual = actualList.toString();

		Assert.assertEquals(
			_getMessage(message, actual), expectedList.toString(), actual);
	}

	public static void assertEquals(
		String message, Map<?, ?> expectedMap, Map<?, ?> actualMap) {

		assertEquals(
			() -> _getMessage(message, actualMap), expectedMap, actualMap);
	}

	public static void assertEquals(
		Supplier<String> messageSupplier, long expected, long actual) {

		try {
			Assert.assertEquals(expected, actual);
		}
		catch (AssertionError assertionError) {
			Assert.assertEquals(messageSupplier.get(), expected, actual);
		}
	}

	public static void assertEquals(
		Supplier<String> messageSupplier, Map<?, ?> expectedMap,
		List<?> actualList) {

		assertEquals(
			messageSupplier, _toMapString(expectedMap),
			String.valueOf(actualList));
	}

	public static void assertEquals(
		Supplier<String> messageSupplier, Map<?, ?> expectedMap,
		Map<?, ?> actualMap) {

		assertEquals(
			messageSupplier, _toMapString(expectedMap),
			_toMapString(actualMap));
	}

	public static void assertEquals(
		Supplier<String> messageSupplier, String expected, String actual) {

		try {
			Assert.assertEquals(expected, actual);
		}
		catch (AssertionError assertionError) {
			Assert.assertEquals(messageSupplier.get(), expected, actual);
		}
	}

	private static String _getMessage(String message, Object object) {
		return message + "->" + object;
	}

	private static String _toMapString(Map<?, ?> map) {
		List<String> list = new ArrayList<>(map.size());

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			list.add(entry.toString());
		}

		Collections.sort(list);

		return list.toString();
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
		Iterator<String> iterator = jsonObject.keys();

		iterator.forEachRemaining(
			key -> list.add(
				_toString(key) + ":" + _toString(jsonObject.get(key))));

		Collections.sort(list);

		return StringPool.OPEN_CURLY_BRACE + StringUtil.merge(list, ",") +
			StringPool.CLOSE_CURLY_BRACE;
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

		return object.toString();
	}

	private static String _toString(String string) {
		String escapedString = StringUtil.replace(
			string, CharPool.QUOTE, StringPool.BACK_SLASH + StringPool.QUOTE);

		return StringPool.QUOTE + escapedString + StringPool.QUOTE;
	}

}