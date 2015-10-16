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

package com.liferay.sync.engine.util;

/**
 * @author Shinn Lok
 */
public class GetterUtil {

	public static final boolean DEFAULT_BOOLEAN = false;

	public static final int DEFAULT_INTEGER = 0;

	public static final long DEFAULT_LONG = 0;

	public static boolean getBoolean(Object value) {
		return getBoolean(String.valueOf(value));
	}

	public static boolean getBoolean(String value) {
		return getBoolean(value, DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(String value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return Boolean.valueOf(value);
	}

	public static int getInteger(String value) {
		return getInteger(value, DEFAULT_INTEGER);
	}

	public static int getInteger(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return Integer.parseInt(value);
	}

	public static long getLong(String value) {
		return getLong(value, DEFAULT_LONG);
	}

	public static long getLong(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return Long.parseLong(value);
	}

}