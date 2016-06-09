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

package com.liferay.gradle.plugins.cache.util;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil extends com.liferay.gradle.util.StringUtil {

	public static String getCommonPrefix(char separator, String... strings) {
		String firstString = strings[0];

		int index = 0;
		boolean match = true;

		while (match) {
			int nextIndex = firstString.indexOf(separator, index + 1);

			if (nextIndex == -1) {
				break;
			}

			for (int i = 1; i < strings.length; i++) {
				if (!firstString.regionMatches(
						index, strings[i], index, (nextIndex - index + 1))) {

					match = false;

					break;
				}
			}

			if (match) {
				index = nextIndex;
			}
		}

		if (index == 0) {
			return null;
		}

		return firstString.substring(0, index);
	}

}