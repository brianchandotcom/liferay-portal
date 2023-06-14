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

package com.liferay.poshi.core.util;

import java.util.HashMap;
import java.util.Map;

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

	public static Map<String, Object> newMap() {
		return new HashMap<>();
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