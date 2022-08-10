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

package com.liferay.poshi.core.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Brian Wing Shun Chan
 */
public class MathUtil {

	public static long difference(long value1, long value2) {
		return value1 - value2;
	}

	public static boolean isGreaterThan(long value1, long value2) {
		if (value1 > value2) {
			return true;
		}

		return false;
	}

	public static boolean isGreaterThanOrEqualTo(long value1, long value2) {
		if (value1 >= value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThan(long value1, long value2) {
		if (value1 < value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThanOrEqualTo(long value1, long value2) {
		if (value1 <= value2) {
			return true;
		}

		return false;
	}

	public static long percent(long percent, long value) {
		return quotient(product(percent, value), 100L, true);
	}

	public static long product(long value1, long value2) {
		return value1 * value2;
	}

	public static long quotient(long value1, long value2) {
		return value1 / value2;
	}

	public static long quotient(long value1, long value2, boolean ceil) {
		if (ceil) {
			return (value1 + value2 - 1) / value2;
		}

		return quotient(value1, value2);
	}

	public static long randomNumber(long maxValue) {
		ThreadLocalRandom current = ThreadLocalRandom.current();

		return current.nextLong(maxValue) + 1;
	}

	public static long sum(long value1, long value2) {
		return value1 + value2;
	}

}