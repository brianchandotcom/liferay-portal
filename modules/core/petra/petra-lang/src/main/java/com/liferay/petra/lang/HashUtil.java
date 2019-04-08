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

package com.liferay.petra.lang;

/**
 * @author Shuyang Zhou
 */
public class HashUtil {

	public static int hash(int seed, boolean value) {
		return seed * 11 + (value ? 1 : 0);
	}

	public static int hash(int seed, int value) {
		return seed * 11 + value;
	}

	public static int hash(int seed, long value) {
		return (int)(seed * 11 + value);
	}

	public static int hash(int seed, Object value) {
		return seed * 11 + ((value == null) ? 0 : value.hashCode());
	}

}