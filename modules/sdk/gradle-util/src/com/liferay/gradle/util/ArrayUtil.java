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

package com.liferay.gradle.util;

/**
 * @author Brian Wing Shun Chan
 */
public class ArrayUtil {

	public static boolean contains(Object[] array, Object value) {
		if (isEmpty(array)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == null) {
				if (array[i] == null) {
					return true;
				}
			}
			else if (value.equals(array[i])) {
				return true;
			}
		}

		return false;
	}

	public static boolean isEmpty(Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return true;
		}

		return false;
	}

	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

}