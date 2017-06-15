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

package com.liferay.poshi.runner.util;

import java.util.Random;

/**
 * @author Brian Wing Shun Chan
 */
public class MathUtil {

	public static int difference(Integer value1, Integer value2) {
		return value1 - value2;
	}

	public static boolean isGreaterThan(Integer value1, Integer value2) {
		if (value1 > value2) {
			return true;
		}

		return false;
	}

	public static boolean isGreaterThanOrEqualTo(
		Integer value1, Integer value2) {

		if (value1 >= value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThan(Integer value1, Integer value2) {
		if (value1 < value2) {
			return true;
		}

		return false;
	}

	public static boolean isLessThanOrEqualTo(Integer value1, Integer value2) {
		if (value1 <= value2) {
			return true;
		}

		return false;
	}

	public static int percent(Integer percent, Integer value) {
		return quotient(product(percent, value), 100, true);
	}

	public static int product(Integer value1, Integer value2) {
		return value1 * value2;
	}

	public static int quotient(Integer value1, Integer value2) {
		return value1 / value2;
	}

	public static int quotient(Integer value1, Integer value2, boolean ceil) {
		if (ceil) {
			return (value1 + value2 - 1) / value2;
		}

		return quotient(value1, value2);
	}

	public static int randomNumber(Integer maxValue) {
		Random random = new Random(System.nanoTime());

		return random.nextInt(maxValue) + 1;
	}

	public static int sum(Integer value1, Integer value2) {
		return value1 + value2;
	}

}