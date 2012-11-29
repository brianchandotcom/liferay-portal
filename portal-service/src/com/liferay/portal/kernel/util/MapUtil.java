/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MapUtil {

	public static <K, V> void copy(
		Map<K, V> master, Map<? super K, ? super V> copy) {

		copy.clear();

		merge(master, copy);
	}

	public static boolean getBoolean(Map<String, ?> map, String key) {
		return getBoolean(map, key, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Map<String, ?> map, String key, boolean defaultValue) {

		return GetterUtil.getBoolean(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static double getDouble(Map<String, ?> map, String key) {
		return getDouble(map, key, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		Map<String, ?> map, String key, double defaultValue) {

		return GetterUtil.getDouble(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static int getInteger(Map<String, ?> map, String key) {
		return getInteger(map, key, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Map<String, ?> map, String key, int defaultValue) {

		return GetterUtil.getInteger(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
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

		return GetterUtil.getLong(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static short getShort(Map<String, ?> map, String key) {
		return getShort(map, key, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		Map<String, ?> map, String key, short defaultValue) {

		return GetterUtil.getShort(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static String getString(Map<String, ?> map, String key) {
		return getString(map, key, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Map<String, ?> map, String key, String defaultValue) {

		Object value = map.get(key);

		if (value != null) {
			if (value instanceof String[]) {
				String[] array = (String[])value;

				if (array.length > 0) {
					return GetterUtil.getString(array[0], defaultValue);
				}
			}
			else if (value instanceof String) {
				return GetterUtil.getString((String)value, defaultValue);
			}
			else {
				return GetterUtil.getString(
					String.valueOf(value), defaultValue);
			}
		}

		return defaultValue;
	}

	public static <K, V> void merge(
		Map<K, V> master, Map<? super K, ? super V> copy) {

		copy.putAll(master);
	}

	public static <T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params) {

		return toLinkedHashMap(params, StringPool.COLON);
	}

	public static <T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params, String delimiter) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		for (String param : params) {
			String[] kvp = StringUtil.split(param, delimiter);

			if (kvp.length == 2) {
				map.put(kvp[0], kvp[1]);
			}
			else if (kvp.length == 3) {
				String type = kvp[2];

				if (type.equalsIgnoreCase("boolean") ||
					type.equals(Boolean.class.getName())) {

					map.put(kvp[0], Boolean.valueOf(kvp[1]));
				}
				else if (type.equalsIgnoreCase("double") ||
						 type.equals(Double.class.getName())) {

					map.put(kvp[0], new Double(kvp[1]));
				}
				else if (type.equalsIgnoreCase("int") ||
						 type.equals(Integer.class.getName())) {

					map.put(kvp[0], new Integer(kvp[1]));
				}
				else if (type.equalsIgnoreCase("long") ||
						 type.equals(Long.class.getName())) {

					map.put(kvp[0], new Long(kvp[1]));
				}
				else if (type.equalsIgnoreCase("short") ||
						 type.equals(Short.class.getName())) {

					map.put(kvp[0], new Short(kvp[1]));
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

		if ((map == null) || map.isEmpty()) {
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

	private static Log _log = LogFactoryUtil.getLog(MapUtil.class);

}