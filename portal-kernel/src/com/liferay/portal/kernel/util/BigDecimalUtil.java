/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static double add(Number x, Number y) {
		if (x == null) {
			x = 0;
		}

		if (y == null) {
			y = 0;
		}

		BigDecimal xBigDecimal = new BigDecimal(x.toString());
		BigDecimal yBigDecimal = new BigDecimal(y.toString());

		BigDecimal resultBigDecimal = xBigDecimal.add(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

	public static double divide(
		Number x, Number y, int scale, RoundingMode roundingMode) {

		if (x == null) {
			x = 0;
		}

		if (y == null) {
			y = 0;
		}

		BigDecimal xBigDecimal = new BigDecimal(x.toString());
		BigDecimal yBigDecimal = new BigDecimal(y.toString());

		BigDecimal resultBigDecimal = xBigDecimal.divide(
			yBigDecimal, scale, roundingMode);

		return resultBigDecimal.doubleValue();
	}

	public static boolean eq(BigDecimal value1, BigDecimal value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;
		}

		if ((value1 != null) && (value2 != null) &&
			(value1.compareTo(value2) == 0)) {

			return true;
		}

		return false;
	}

	public static BigDecimal get(Object newValue, BigDecimal defaultValue) {
		if (newValue != null) {
			if (newValue instanceof Integer) {
				return BigDecimal.valueOf((Integer)newValue);
			}

			if (newValue instanceof Double) {
				return BigDecimal.valueOf((Double)newValue);
			}

			if (newValue instanceof BigDecimal) {
				return (BigDecimal)newValue;
			}
		}

		return defaultValue;
	}

	public static boolean gt(BigDecimal value1, BigDecimal value2) {
		if ((value1 == null) || (value2 == null)) {
			return false;
		}

		if (value1.compareTo(value2) > 0) {
			return true;
		}

		return false;
	}

	public static boolean gte(BigDecimal value1, BigDecimal value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;
		}

		if ((value1 != null) && (value2 != null) &&
			(value1.compareTo(value2) >= 0)) {

			return true;
		}

		return false;
	}

	public static boolean isZero(BigDecimal value) {
		if ((value == null) || (value.compareTo(BigDecimal.ZERO) == 0)) {
			return true;
		}

		return false;
	}

	public static boolean lt(BigDecimal value1, BigDecimal value2) {
		if ((value1 == null) || (value2 == null)) {
			return false;
		}

		if (value1.compareTo(value2) < 0) {
			return true;
		}

		return false;
	}

	public static boolean lte(BigDecimal value1, BigDecimal value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;
		}

		if ((value1 != null) && (value2 != null) &&
			(value1.compareTo(value2) <= 0)) {

			return true;
		}

		return false;
	}

	public static double multiply(Number x, Number y) {
		if (x == null) {
			x = 0;
		}

		if (y == null) {
			y = 0;
		}

		BigDecimal xBigDecimal = new BigDecimal(x.toString());
		BigDecimal yBigDecimal = new BigDecimal(y.toString());

		BigDecimal resultBigDecimal = xBigDecimal.multiply(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

	public static double scale(Number x, int scale, RoundingMode roundingMode) {
		if (x == null) {
			x = 0;
		}

		BigDecimal xBigDecimal = new BigDecimal(x.toString());

		xBigDecimal = xBigDecimal.setScale(scale, roundingMode);

		return xBigDecimal.doubleValue();
	}

	public static BigDecimal stripTrailingZeros(BigDecimal x) {
		if (x == null) {
			return null;
		}

		x = x.stripTrailingZeros();

		if (x.scale() < 0) {
			return x.setScale(0, RoundingMode.HALF_UP);
		}

		return x;
	}

	public static double subtract(Number x, Number y) {
		if (x == null) {
			x = 0;
		}

		if (y == null) {
			y = 0;
		}

		BigDecimal xBigDecimal = new BigDecimal(x.toString());
		BigDecimal yBigDecimal = new BigDecimal(y.toString());

		BigDecimal resultBigDecimal = xBigDecimal.subtract(yBigDecimal);

		return resultBigDecimal.doubleValue();
	}

}