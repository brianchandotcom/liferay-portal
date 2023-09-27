/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.subscription.type.web.internal.constants;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSubscriptionTypeConstants {

	public static final int MODE_EXACT_DAY_OF_MONTH = 1;

	public static final int MODE_EXACT_DAY_OF_YEAR = 1;

	public static final int MODE_LAST_DAY_OF_MONTH = 2;

	public static final int MODE_ORDER_DATE = 0;

	public static final int[] MONTHLY_MODES = {
		MODE_ORDER_DATE, MODE_EXACT_DAY_OF_MONTH, MODE_LAST_DAY_OF_MONTH
	};

	public static final int[] YEARLY_MODES = {
		MODE_ORDER_DATE, MODE_EXACT_DAY_OF_YEAR
	};

	public static String getMonthlyCPSubscriptionTypeModeLabel(int mode) {
		if (mode == MODE_ORDER_DATE) {
			return "order-date";
		}
		else if (mode == MODE_EXACT_DAY_OF_MONTH) {
			return "exact-day-of-month";
		}
		else if (mode == MODE_LAST_DAY_OF_MONTH) {
			return "last-day-of-month";
		}

		return null;
	}

	public static String getYearlyCPSubscriptionTypeModeLabel(int mode) {
		if (mode == MODE_EXACT_DAY_OF_YEAR) {
			return "exact-day-of-year";
		}
		else if (mode == MODE_ORDER_DATE) {
			return "order-date";
		}

		return null;
	}

}