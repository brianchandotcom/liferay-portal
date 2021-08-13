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

package com.liferay.talend.common.util;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class StringUtil {

	public static boolean isEmpty(String value) {
		if (value == null) {
			return true;
		}

		String trimmedValue = value.trim();

		if (trimmedValue.isEmpty()) {
			return true;
		}

		return false;
	}

	public static String removeQuotes(String s) {
		return s.replace("\"", "");
	}

	public static String replace(String pattern, String... tplArgs) {
		String replaced = pattern;

		for (int i = 0; i < tplArgs.length;) {
			replaced = replaced.replace(tplArgs[i], tplArgs[i + 1]);

			i = i + 2;
		}

		return replaced;
	}

	public static Set<String> stripPrefix(String prefix, Set<String> values) {
		Stream<String> stream = values.stream();

		return stream.map(
			t -> {
				if (t.startsWith(prefix)) {
					return t.substring(prefix.length());
				}

				return t;
			}
		).collect(
			Collectors.toSet()
		);
	}

	public static String toLowerCase(String value) {
		return value.toLowerCase(Locale.getDefault());
	}

	public static String toUpperCase(String value) {
		return value.toUpperCase(Locale.getDefault());
	}

	private StringUtil() {
	}

}