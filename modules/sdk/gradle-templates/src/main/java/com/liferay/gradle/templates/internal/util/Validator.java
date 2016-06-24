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

package com.liferay.gradle.templates.internal.util;

/**
 * @author Brian Wing Shun Chan
 */
public class Validator {

	public static boolean isNotNull(String s) {
		return !isNull(s);
	}

	public static boolean isNull(String s) {
		if (s == null) {
			return true;
		}

		int counter = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (Character.isWhitespace(c)) {
				continue;
			}
			else if (counter > 3) {
				return false;
			}

			if (counter == 0) {
				if (c != 'n') {
					return false;
				}
			}
			else if (counter == 1) {
				if (c != 'u') {
					return false;
				}
			}
			else if ((counter == 2) || (counter == 3)) {
				if (c != 'l') {
					return false;
				}
			}

			counter++;
		}

		if ((counter == 0) || (counter == 4)) {
			return true;
		}

		return false;
	}

}