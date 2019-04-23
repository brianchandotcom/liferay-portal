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

package com.liferay.arquillian.extension.junit.bridge.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Matthew Tambara
 */
public class StringUtil {

	public static <T> String merge(Collection<T> col, String delimiter) {
		if (col == null) {
			return null;
		}

		if (col.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder(2 * col.size());

		for (T t : col) {
			sb.append(String.valueOf(t));
			sb.append(delimiter);
		}

		int delimeterLength = delimiter.length();

		sb.setLength(sb.length() - delimeterLength);

		return sb.toString();
	}

	public static List<String> split(String s) {
		return split(s, ',');
	}

	public static List<String> split(String s, char delimiter) {
		if ((s == null) || s.isEmpty()) {
			return Collections.emptyList();
		}

		s = s.trim();

		if (s.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> elements = new ArrayList<>();

		int offset = 0;
		int pos;

		while ((pos = s.indexOf(delimiter, offset)) != -1) {
			if (offset < pos) {
				elements.add(s.substring(offset, pos));
			}

			offset = pos + 1;
		}

		if (offset < s.length()) {
			elements.add(s.substring(offset));
		}

		return elements;
	}

}