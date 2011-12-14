/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Igor Spasic
 */
public class CamelCaseUtil {

	/**
	 * Converts camel case word to dash-separated-words.
	 * Examples:
	 * <li>camelCase --&gt; camel-case
	 * <li>camelCASEWord --&gt; camel-case-word
	 */
	public static String fromCamelCase(String s) {
		StringBundler sb = new StringBundler();

		boolean upperCase = false;

		int len = s.length();

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;
			if (i < len - 1) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(CharPool.DASH);
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

	/**
	 * Normalizes inner uppercase words in camel case string.
	 * Examples:
	 * <li>camelCase --&gt; camelCase
	 * <li>camelCASEWord --&gt; camelCaseWord
	 */
	public static String normalizeCamelCase(String s) {
		StringBundler sb = new StringBundler();

		boolean upperCase = false;

		int len = s.length();

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;
			if (i < len - 1) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (upperCase && nextUpperCase) {
					c = Character.toLowerCase(c);
				}

				upperCase = true;
			}
			else {
				upperCase = false;
			}

			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Converts a dash-separated-words into camel case. Only the first
	 * letter of words is converted into upper case, all others
	 * remains unchanged.Examples:
	 *
	 * <li>camel-case --&gt; camelCase
	 * <li>camel-CASE-word --&gt; camelCASEWord
	 */
	public static String toCamelCase(String s) {
		StringBundler sb = new StringBundler(s.length());

		boolean upperCase = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == CharPool.DASH) {
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