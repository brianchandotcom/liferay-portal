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

package com.liferay.talend.util;

/**
 * @author Zoltán Takács
 */
public class StringUtils {

	/**
	 * Returns <code>true</code> if the string is <code>null</code>, meaning it
	 * is a <code>null</code> reference, an empty string, whitespace, or if the
	 * trimmed string is empty string.
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNull(String s) {
		if ((s == null) || s.isEmpty()) {
			return true;
		}

		s = s.trim();

		if (s.isEmpty()) {
			return true;
		}

		return false;
	}

	public static String stripPath(String path) {
		if (!path.contains("/")) {
			return path;
		}

		return path.substring(path.lastIndexOf("/") + 1);
	}

	private StringUtils() {
	}

}