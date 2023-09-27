/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		for (Object object : array) {
			if (value == null) {
				if (object == null) {
					return true;
				}
			}
			else if (value.equals(object)) {
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