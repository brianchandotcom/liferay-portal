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

/**
 * @author Igor Spasic
 */
public class CamelCaseUtil {

	public static String fixCamelCase(String string) {
		StringBuilder s = new StringBuilder();

		int length = string.length();

		boolean isPreviousCharUppercase = false;

		for (int i = 0; i < length; i++) {

			char ch = string.charAt(i);

			if (Character.isUpperCase(ch) && (i > 0)) {
				if (isPreviousCharUppercase) {
					ch = Character.toLowerCase(ch);
				}
				isPreviousCharUppercase = true;
			}
			else {
				isPreviousCharUppercase = false;
			}

			s.append(ch);
		}

		return s.toString();
	}

	public static String toCamelCase(String string) {

		int length = string.length();

		StringBuilder s = new StringBuilder(length);

		boolean upperCase = false;

		for (int i = 0; i < length; i++) {
			char ch = string.charAt(i);

			if (ch == CharPool.DASH) {
				upperCase = true;
			}
			else if (upperCase) {
				s.append(Character.toUpperCase(ch));

				upperCase = false;
			}
			else {
				s.append(ch);
			}
		}
		return s.toString();

	}

	public static String toSeparatedWords(String string) {
		StringBuilder s = new StringBuilder();

		int length = string.length();

		boolean isPreviousCharUppercase = false;

		for (int i = 0; i < length; i++) {

			char ch = string.charAt(i);

			if (Character.isUpperCase(ch) && (i > 0)) {
				if (!isPreviousCharUppercase) {
					s.append(CharPool.DASH);
				}

				ch = Character.toLowerCase(ch);

				isPreviousCharUppercase = true;
			}
			else {
				isPreviousCharUppercase = false;
			}

			s.append(ch);
		}

		return s.toString();
	}

}
