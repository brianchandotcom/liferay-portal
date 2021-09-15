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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ListUtil {

	public static void add(List<String> list, String item) {
		list.add(item);
	}

	public static <E> List<E> copy(List<? extends E> master) {
		if (master == null) {
			return null;
		}

		return new ArrayList<>(master);
	}

	public static String get(List<String> list, int index) {
		return list.get(index);
	}

	public static boolean isEmpty(List<?> list) {
		if ((list == null) || list.isEmpty()) {
			return true;
		}

		return false;
	}

	public static List<String> newList() {
		return new ArrayList<>();
	}

	public static List<String> newListFromString(String s) {
		return newListFromString(s, StringPool.COMMA);
	}

	public static List<String> newListFromString(String s, String delimiter) {
		s = s.trim();

		if (delimiter.equals(",") && s.endsWith("]") && s.startsWith("[")) {
			try {
				JSONArray jsonArray = new JSONArray(s);

				List<String> list = new ArrayList<>();

				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						list.add(jsonArray.getString(i));
					}
				}

				return list;
			}
			catch (JSONException jsonException) {
			}
		}

		List<String> list = new ArrayList<>();

		for (String item : s.split(delimiter)) {
			list.add(item.trim());
		}

		return list;
	}

	public static void remove(List<String> list, String item) {
		list.remove(item);
	}

	public static <E> List<E> sort(List<E> list) {
		return sort(list, null);
	}

	public static <E> List<E> sort(
		List<E> list, Comparator<? super E> comparator) {

		list = copy(list);

		Collections.sort(list, comparator);

		return list;
	}

	public static String sort(String s) {
		return sort(s, StringPool.COMMA);
	}

	public static String sort(String s, String delimiter) {
		List<String> list = Arrays.asList(s.split(delimiter));

		return toString(sort(list), delimiter);
	}

	public static String toString(List<?> list) {
		return toString(list, StringPool.COMMA);
	}

	public static String toString(List<?> list, String delimiter) {
		if (isEmpty(list)) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder((2 * list.size()) - 1);

		for (int i = 0; i < list.size(); i++) {
			Object value = list.get(i);

			if (value != null) {
				sb.append(value);
			}

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

}