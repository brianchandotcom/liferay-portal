/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Calum Ragan
 */
public class MapUtil {

	public static boolean containsKey(Map<String, Object> map, String key) {
		return map.containsKey(key);
	}

	public static boolean containsValue(Map<String, Object> map, Object value) {
		return map.containsValue(value);
	}

	public static Object get(Map<String, Object> map, String key) {
		return map.get(key);
	}

	public static boolean isEmpty(Map<String, Object> map) {
		if ((map == null) || map.isEmpty()) {
			return true;
		}

		return false;
	}

	public static Map<String, Object> newHashMap() {
		return new HashMap<>();
	}

	public static Map<String, Object> newTreeMap() {
		return new TreeMap<>();
	}

	public static void put(Map<String, Object> map, String key, Object value) {
		map.put(key, value);
	}

	public static void remove(Map<String, Object> map, String key) {
		map.remove(key);
	}

	public static String size(Map<String, Object> map) {
		int size = map.size();

		return String.valueOf(size);
	}

}