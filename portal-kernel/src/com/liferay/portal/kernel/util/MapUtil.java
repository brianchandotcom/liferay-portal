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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MapUtil {

	public static <K, V> void copy(
		Map<? extends K, ? extends V> master, Map<? super K, ? super V> copy) {

		copy.clear();

		merge(master, copy);
	}

	public static <K> PredicateFilter<Map.Entry<K, ?>> entryKeyPredicateFilter(
		final PredicateFilter<K> predicateFilter) {

		return new PredicateFilter<Map.Entry<K, ?>>() {

			@Override
			public boolean filter(Map.Entry<K, ?> entry) {
				return predicateFilter.filter(entry.getKey());
			}

		};
	}

	public static <V> PredicateFilter<Map.Entry<?, V>>
		entryValuePredicateFilter(final PredicateFilter<V> predicateFilter) {

		return new PredicateFilter<Map.Entry<?, V>>() {

			@Override
			public boolean filter(Map.Entry<?, V> entry) {
				return predicateFilter.filter(entry.getValue());
			}

		};
	}

	public static <K1, V1, K2 extends K1, V2 extends V1> void filter(
		Map<? extends K2, ? extends V2> inputMap,
		Map<? super K2, ? super V2> outputMap,
		PredicateFilter<? super Map.Entry<K1, V1>> predicateFilter) {

		for (Map.Entry<? extends K2, ? extends V2> entry :
				inputMap.entrySet()) {

			if (predicateFilter.filter((Map.Entry<K1, V1>)entry)) {
				outputMap.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public static <K1, V1, K2 extends K1, V2 extends V1> Map<K2, V2> filter(
		Map<K2, V2> inputMap,
		PredicateFilter<? super Map.Entry<K1, V1>> predicateFilter) {

		Map<K2, V2> outputMap = new HashMap<>();

		filter(inputMap, outputMap, predicateFilter);

		return outputMap;
	}

	public static <K, V> void filterByKeys(
		Map<? extends K, ? extends V> inputMap,
		Map<? super K, ? super V> outputMap,
		PredicateFilter<? super K> keyPredicateFilter) {

		filter(
			inputMap, outputMap, entryKeyPredicateFilter(keyPredicateFilter));
	}

	public static <K, V> Map<K, V> filterByKeys(
		Map<K, V> inputMap, PredicateFilter<? super K> keyPredicateFilter) {

		return filter(inputMap, entryKeyPredicateFilter(keyPredicateFilter));
	}

	public static <K, V> void filterByValues(
		Map<? extends K, ? extends V> inputMap,
		Map<? super K, ? super V> outputMap,
		PredicateFilter<? super V> valuePredicateFilter) {

		filter(
			inputMap, outputMap,
			entryValuePredicateFilter(valuePredicateFilter));
	}

	public static <K, V> Map<K, V> filterByValues(
		Map<K, V> inputMap, PredicateFilter<? super V> keyPredicateFilter) {

		return filter(inputMap, entryValuePredicateFilter(keyPredicateFilter));
	}

	public static <T> Map<T, T> fromArray(T... array) {
		if ((array.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Array length is not an even number");
		}

		Map<T, T> map = new HashMap<>();

		for (int i = 0; i < array.length; i += 2) {
			T key = array[i];
			T value = array[i + 1];

			map.put(key, value);
		}

		return map;
	}

	public static boolean getBoolean(Map<String, ?> map, String key) {
		return getBoolean(map, key, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Map<String, ?> map, String key, boolean defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Boolean) {
			return (Boolean)value;
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getBoolean(array[0], defaultValue);
		}

		return GetterUtil.getBoolean(String.valueOf(value), defaultValue);
	}

	public static double getDouble(Map<String, ?> map, String key) {
		return getDouble(map, key, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Map<String, ?> map, String key, double defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Double) {
			return (Double)value;
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getDouble(array[0], defaultValue);
		}

		return GetterUtil.getDouble(String.valueOf(value), defaultValue);
	}

	public static int getInteger(Map<String, ?> map, String key) {
		return getInteger(map, key, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Map<String, ?> map, String key, int defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Integer) {
			return (Integer)value;
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getInteger(array[0], defaultValue);
		}

		return GetterUtil.getInteger(String.valueOf(value), defaultValue);
	}

	public static long getLong(Map<Long, Long> map, long key) {
		return getLong(map, key, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Map<Long, Long> map, long key, long defaultValue) {

		Long value = map.get(key);

		if (value == null) {
			return defaultValue;
		}
		else {
			return value;
		}
	}

	public static long getLong(Map<String, ?> map, String key) {
		return getLong(map, key, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Map<String, ?> map, String key, long defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Long) {
			return (Long)value;
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getLong(array[0], defaultValue);
		}

		return GetterUtil.getLong(String.valueOf(value), defaultValue);
	}

	public static short getShort(Map<String, ?> map, String key) {
		return getShort(map, key, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		Map<String, ?> map, String key, short defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof Short) {
			return (Short)value;
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getShort(array[0], defaultValue);
		}

		return GetterUtil.getShort(String.valueOf(value), defaultValue);
	}

	public static String getString(Map<String, ?> map, String key) {
		return getString(map, key, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Map<String, ?> map, String key, String defaultValue) {

		Object value = map.get(key);

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return GetterUtil.getString((String)value, defaultValue);
		}

		if (value instanceof String[]) {
			String[] array = (String[])value;

			if (array.length == 0) {
				return defaultValue;
			}

			return GetterUtil.getString(array[0], defaultValue);
		}

		return GetterUtil.getString(String.valueOf(value), defaultValue);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if ((map == null) || map.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static <K, V> void merge(
		Map<? extends K, ? extends V> master, Map<? super K, ? super V> copy) {

		copy.putAll(master);
	}

	public static <T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params) {

		return toLinkedHashMap(params, StringPool.COLON);
	}

	public static <T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params, String delimiter) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		if (params == null) {
			return (LinkedHashMap<String, T>)map;
		}

		for (String param : params) {
			String[] kvp = StringUtil.split(param, delimiter);

			if (kvp.length == 2) {
				map.put(kvp[0], kvp[1]);
			}
			else if (kvp.length == 3) {
				String type = kvp[2];

				if (StringUtil.equalsIgnoreCase(type, "boolean") ||
					type.equals(Boolean.class.getName())) {

					map.put(kvp[0], Boolean.valueOf(kvp[1]));
				}
				else if (StringUtil.equalsIgnoreCase(type, "double") ||
						 type.equals(Double.class.getName())) {

					map.put(kvp[0], Double.valueOf(kvp[1]));
				}
				else if (StringUtil.equalsIgnoreCase(type, "int") ||
						 type.equals(Integer.class.getName())) {

					map.put(kvp[0], Integer.valueOf(kvp[1]));
				}
				else if (StringUtil.equalsIgnoreCase(type, "long") ||
						 type.equals(Long.class.getName())) {

					map.put(kvp[0], Long.valueOf(kvp[1]));
				}
				else if (StringUtil.equalsIgnoreCase(type, "short") ||
						 type.equals(Short.class.getName())) {

					map.put(kvp[0], Short.valueOf(kvp[1]));
				}
				else if (type.equals(String.class.getName())) {
					map.put(kvp[0], kvp[1]);
				}
				else {
					try {
						Class<?> clazz = Class.forName(type);

						Constructor<?> constructor = clazz.getConstructor(
							String.class);

						map.put(kvp[0], constructor.newInstance(kvp[1]));
					}
					catch (Exception e) {
						_log.error(e.getMessage(), e);
					}
				}
			}
		}

		return (LinkedHashMap<String, T>)map;
	}

	public static String toString(Map<?, ?> map) {
		return toString(map, null, null);
	}

	public static String toString(
		Map<?, ?> map, String hideIncludesRegex, String hideExcludesRegex) {

		if (isEmpty(map)) {
			return StringPool.OPEN_CURLY_BRACE + StringPool.CLOSE_CURLY_BRACE;
		}

		StringBundler sb = new StringBundler(map.size() * 4 + 1);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();

			String keyString = String.valueOf(key);

			if (hideIncludesRegex != null) {
				if (!keyString.matches(hideIncludesRegex)) {
					value = "********";
				}
			}

			if (hideExcludesRegex != null) {
				if (keyString.matches(hideExcludesRegex)) {
					value = "********";
				}
			}

			sb.append(keyString);
			sb.append(StringPool.EQUAL);

			if (value instanceof Map<?, ?>) {
				sb.append(MapUtil.toString((Map<?, ?>)value));
			}
			else if (value instanceof String[]) {
				String valueString = StringUtil.merge(
					(String[])value, StringPool.COMMA_AND_SPACE);

				sb.append(
					StringPool.OPEN_BRACKET.concat(valueString).concat(
						StringPool.CLOSE_BRACKET));
			}
			else {
				sb.append(value);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setStringAt(StringPool.CLOSE_CURLY_BRACE, sb.index() - 1);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(MapUtil.class);

}