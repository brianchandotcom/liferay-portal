/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Calvin Keum
 */
public class BigDecimalUtil {

	public static double add(double x, double y) {
		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));
		BigDecimal yBigDecimal = new BigDecimal(String.valueOf(y));

		BigDecimal resultBigDecimal = xBigDecimal.add(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

	public static double divide(
		double x, double y, int scale, RoundingMode roundingMode) {

		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));
		BigDecimal yBigDecimal = new BigDecimal(String.valueOf(y));

		BigDecimal resultBigDecimal = xBigDecimal.divide(
			yBigDecimal, scale, roundingMode);

		return resultBigDecimal.doubleValue();
	}

	public static double divide(
		int x, int y, int scale, RoundingMode roundingMode) {

		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));
		BigDecimal yBigDecimal = new BigDecimal(String.valueOf(y));

		BigDecimal resultBigDecimal = xBigDecimal.divide(
			yBigDecimal, scale, roundingMode);

		return resultBigDecimal.doubleValue();
	}

	public static double multiply(double x, double y) {
		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));
		BigDecimal yBigDecimal = new BigDecimal(String.valueOf(y));

		BigDecimal resultBigDecimal = xBigDecimal.multiply(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

	public static double scale(double x, int scale, RoundingMode roundingMode) {
		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));

		xBigDecimal = xBigDecimal.setScale(scale, roundingMode);

		return xBigDecimal.doubleValue();
	}

	public static double subtract(double x, double y) {
		BigDecimal xBigDecimal = new BigDecimal(String.valueOf(x));
		BigDecimal yBigDecimal = new BigDecimal(String.valueOf(y));

		BigDecimal resultBigDecimal = xBigDecimal.subtract(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

}