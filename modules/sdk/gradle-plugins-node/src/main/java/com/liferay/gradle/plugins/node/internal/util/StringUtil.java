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

package com.liferay.gradle.plugins.node.internal.util;

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