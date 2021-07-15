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

package com.liferay.commerce.util;

import java.math.BigDecimal;

/**
 * @author Igor Beslic
 */
public class CommerceBigDecimalUtil {

	public static boolean eq(BigDecimal value1, BigDecimal value2) {
		if (value1.compareTo(value2) == 0) {
			return true;
		}

		return false;
	}

	public static boolean gt(BigDecimal value1, BigDecimal value2) {
		if (value1 == null) {
			return false;
		}

		if (value1.compareTo(value2) > 0) {
			return true;
		}

		return false;
	}

	public static boolean gte(BigDecimal value1, BigDecimal value2) {
		if (value1 == null) {
			return false;
		}

		if (value1.compareTo(value2) >= 0) {
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
		if (value1.compareTo(value2) < 0) {
			return true;
		}

		return false;
	}

	public static boolean lte(BigDecimal value1, BigDecimal value2) {
		if (value1.compareTo(value2) <= 0) {
			return true;
		}

		return false;
	}

}