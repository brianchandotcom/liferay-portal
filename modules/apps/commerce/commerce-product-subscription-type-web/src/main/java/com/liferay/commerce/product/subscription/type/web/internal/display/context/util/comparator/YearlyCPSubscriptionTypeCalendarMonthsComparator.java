/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.subscription.type.web.internal.display.context.util.comparator;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class YearlyCPSubscriptionTypeCalendarMonthsComparator
	implements Comparator<Integer> {

	public YearlyCPSubscriptionTypeCalendarMonthsComparator() {
		this(true);
	}

	public YearlyCPSubscriptionTypeCalendarMonthsComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Integer month1, Integer month2) {
		int value = Integer.compare(month1, month2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	private final boolean _ascending;

}