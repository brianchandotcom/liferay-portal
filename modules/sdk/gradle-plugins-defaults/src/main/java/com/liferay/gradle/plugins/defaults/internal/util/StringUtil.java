/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil extends com.liferay.gradle.util.StringUtil {

	public static String merge(Iterable<String> iterable, String separator) {
		StringBuilder sb = new StringBuilder();

		boolean first = true;

		for (String s : iterable) {
			if (!first) {
				sb.append(separator);
			}

			first = false;

			sb.append(s);
		}

		return sb.toString();
	}

	public static String uncapitalize(String s) {
		char firstChar = s.charAt(0);

		if (Character.isUpperCase(firstChar)) {
			s = Character.toLowerCase(firstChar) + s.substring(1);
		}

		return s;
	}

}