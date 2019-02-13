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

package com.liferay.portal.tools.rest.builder.internal.util;

/**
 * @author Javier Gamarra
 */
public class PathUtil {

	public static String getLastSegmentFromPath(String path, int number) {
		int index = _nthIndexOf(path, "/", number);

		if (index == -1) {
			return "";
		}

		String pattern = path.substring(index);

		return CamelCaseUtil.toCamelCase(pattern, true);
	}

	private static int _nthIndexOf(String source, String search, int n) {
		int index = source.indexOf(search);

		if (index == -1) {
			return -1;
		}

		for (int i = 1; i < n; i++) {
			index = source.indexOf(search, index + 1);

			if (index == -1) {
				return -1;
			}
		}

		return index;
	}

}