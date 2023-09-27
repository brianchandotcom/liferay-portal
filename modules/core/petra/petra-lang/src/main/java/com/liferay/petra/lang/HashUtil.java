/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.lang;

/**
 * @author Shuyang Zhou
 */
public class HashUtil {

	public static int hash(int seed, boolean value) {
		return (seed * 11) + (value ? 1 : 0);
	}

	public static int hash(int seed, int value) {
		return (seed * 11) + value;
	}

	public static int hash(int seed, long value) {
		return (int)((seed * 11) + value);
	}

	public static int hash(int seed, Object value) {
		return (seed * 11) + ((value == null) ? 0 : value.hashCode());
	}

}