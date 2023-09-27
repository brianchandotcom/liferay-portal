/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			String s = String.valueOf(array[i]);

			sb.append(s.trim());
		}

		return sb.toString();
	}

}