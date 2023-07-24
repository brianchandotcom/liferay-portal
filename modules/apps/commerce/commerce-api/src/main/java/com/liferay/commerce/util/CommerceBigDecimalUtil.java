/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import java.math.BigDecimal;

/**
 * @author Igor Beslic
 */
public class CommerceBigDecimalUtil {

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

}