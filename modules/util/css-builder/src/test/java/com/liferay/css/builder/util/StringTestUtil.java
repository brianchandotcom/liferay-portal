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

package com.liferay.css.builder.util;

/**
 * @author David Truong
 */
public class StringTestUtil {

	public static String merge(Object[] array) {
		return _merge(array, ",");
	}

	private static String _merge(Object[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return "";
		}

		if (array.length == 1) {
			return String.valueOf(array[0]);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(delimiter);
			}

			sb.append(String.valueOf(array[i]).trim());
		}

		return sb.toString();
	}

}