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

package com.liferay.project.templates.util;

/**
 * @author Andrea Di Giorgi
 */
public class StringTestUtil {

	public static String merge(String... strings) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < strings.length; i++) {
			if (i > 0) {
				sb.append(',');
			}

			sb.append(strings[i]);
		}

		return sb.toString();
	}

}