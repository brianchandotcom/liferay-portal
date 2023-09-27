/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.extensions.util;

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

}