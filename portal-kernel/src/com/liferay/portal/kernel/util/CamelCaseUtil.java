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

import com.liferay.petra.string.CharPool;

/**
 * @author Igor Spasic
 * @author Eduardo Lundgren
 */
public class CamelCaseUtil {

	public static String fromCamelCase(String s) {
		return fromCamelCase(s, CharPool.DASH);
	}

	public static String fromCamelCase(String s, char delimiter) {
		StringBuilder sb = new StringBuilder();

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
		StringBuilder sb = new StringBuilder(s.length());

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