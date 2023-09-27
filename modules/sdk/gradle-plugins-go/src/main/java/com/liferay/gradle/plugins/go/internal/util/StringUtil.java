/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.go.internal.util;

/**
 * @author Peter Shin
 */
public class StringUtil extends com.liferay.gradle.util.StringUtil {

	public static String camelCase(String s, boolean capitalize) {
		StringBuilder sb = new StringBuilder(s.length());

		boolean upperCase = capitalize;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!Character.isDigit(c) && !Character.isLetter(c)) {
				upperCase = true;
			}
			else if (upperCase) {
				sb.append(Character.toUpperCase(c));

				upperCase = false;
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

}