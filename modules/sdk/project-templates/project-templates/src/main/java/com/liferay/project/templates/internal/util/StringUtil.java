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

package com.liferay.project.templates.internal.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil {

	public static String capitalize(String s, char separator) {
		StringBuilder sb = new StringBuilder(s.length());

		sb.append(s);

		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);

			if ((i == 0) || (sb.charAt(i - 1) == separator)) {
				c = Character.toUpperCase(c);
			}

			sb.setCharAt(i, c);
		}

		return sb.toString();
	}

	public static boolean contains(String s, Pattern pattern) {
		if ((s == null) || s.isEmpty()) {
			return false;
		}

		Matcher matcher = pattern.matcher(s);

		return matcher.find();
	}

	public static String removeChar(String s, char c) {
		int y = s.indexOf(c);

		if (y == -1) {
			return s;
		}

		StringBuilder sb = new StringBuilder(s.length());

		int x = 0;

		while (x <= y) {
			sb.append(s.substring(x, y));

			x = y + 1;

			y = s.indexOf(c, x);
		}

		sb.append(s.substring(x));

		return sb.toString();
	}

	public static String toLowerCase(String s) {
		return toLowerCase(s, null);
	}

	public static String toLowerCase(String s, Locale locale) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = null;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c > 127) {

				// Found non-ascii char, fallback to the slow unicode detection

				if (locale == null) {
					return s.toLowerCase();
				}

				return s.toLowerCase(locale);
			}

			if ((c >= 'A') && (c <= 'Z')) {
				if (sb == null) {
					sb = new StringBuilder(s);
				}

				sb.setCharAt(i, (char)(c + 32));
			}
		}

		if (sb == null) {
			return s;
		}

		return sb.toString();
	}

}