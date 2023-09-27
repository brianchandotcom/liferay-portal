/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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