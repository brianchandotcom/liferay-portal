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

	public static long difference(Long value1, Long value2) {
		return value1 - value2;
	}

	public static boolean isGreaterThan(Long value1, Long value2) {
		if (value1 > value2) {
			return true;
		}

		return false;
	}

	public static boolean isGreaterThanOrEqualTo(Long value1, Long value2) {
		if (value1 >= value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThan(Long value1, Long value2) {
		if (value1 < value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThanOrEqualTo(Long value1, Long value2) {
		if (value1 <= value2) {
			return true;
		}

		return false;
	}

	public static long percent(Long percent, Long value) {
		return quotient(product(percent, value), 100L, true);
	}

	public static long product(Long value1, Long value2) {
		return value1 * value2;
	}

	public static long quotient(Long value1, Long value2) {
		return value1 / value2;
	}

	public static long quotient(Long value1, Long value2, boolean ceil) {
		if (ceil) {
			return (value1 + value2 - 1) / value2;
		}

		return quotient(value1, value2);
	}

	public static long randomNumber(Long maxValue) {
		ThreadLocalRandom current = ThreadLocalRandom.current();

		return current.nextLong(maxValue) + 1;
	}

	public static long sum(Long value1, Long value2) {
		return value1 + value2;
	}

}