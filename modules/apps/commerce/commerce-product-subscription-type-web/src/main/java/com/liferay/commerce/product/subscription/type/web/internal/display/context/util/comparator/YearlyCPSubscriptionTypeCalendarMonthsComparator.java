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