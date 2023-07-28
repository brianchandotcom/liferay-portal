/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.util;

import java.math.BigDecimal;

/**
 * @author Crescenzo Rega
 */
public class BigDecimalUtil {

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

}