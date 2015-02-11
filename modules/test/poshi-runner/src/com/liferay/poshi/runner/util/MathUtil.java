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

/**
 * @author Brian Wing Shun Chan
 */
public class MathUtil {

	public static int difference(Integer...values) {
		int difference = values[0];

		for (int i = 1; i < values.length; i++) {
			difference -= values[i];
		}

		return difference;
	}

	public static int product(Integer... values) {
		int product = 1;

		for (int value : values) {
			product *= value;
		}

		return product;
	}

	public static int quotient(Integer value1, Integer value2) {
		return value1 / value2;
	}

	public static int sum(Integer... values) {
		int sum = 0;

		for (int value : values) {
			sum += value;
		}

		return sum;
	}

}