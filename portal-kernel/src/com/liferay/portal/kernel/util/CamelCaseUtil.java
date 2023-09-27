/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Igor Spasic
 * @author Eduardo Lundgren
 */
public class CamelCaseUtil {

	public static String fromCamelCase(String s) {
		return fromCamelCase(s, CharPool.DASH);
	}

	public static String fromCamelCase(String s, char delimiter) {
		StringBundler sb = new StringBundler();

		boolean upperCase = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase ||
					((i < (s.length() - 1)) &&
					 !Character.isUpperCase(s.charAt(i + 1)))) {

					sb.append(delimiter);
				}

				c = Character.toLowerCase(c);

				upperCase = true;
			}
			else {
				upperCase = false;
			}

			sb.append(c);
		}

		return sb.toString();
	}

	public static String normalizeCamelCase(String s) {
		return normalizeCamelCase(s, false);
	}

	public static String normalizeCamelCase(
		String s, boolean normalizeInnerTerms) {

		StringBuilder sb = new StringBuilder(s);

		boolean upperCase = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!normalizeInnerTerms && (c == CharPool.PERIOD)) {
				return sb.toString();
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				boolean nextUpperCase = true;

				if (i < (s.length() - 1)) {
					char nextChar = s.charAt(i + 1);

					if ((nextChar != CharPool.PERIOD) &&
						!Character.isUpperCase(nextChar)) {

						nextUpperCase = false;
					}
				}

				if (upperCase && nextUpperCase) {
					sb.setCharAt(i, Character.toLowerCase(c));
				}

				upperCase = true;
			}
			else {
				upperCase = false;
			}
		}

		return sb.toString();
	}

	public static String toCamelCase(String s) {
		return toCamelCase(s, CharPool.DASH);
	}

	public static String toCamelCase(String s, char delimiter) {
		StringBundler sb = new StringBundler(s.length());

		boolean upperCase = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == delimiter) {
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