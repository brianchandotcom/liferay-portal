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

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderTotalComparator
	extends OrderByComparator<CommerceOrder> {

	public static final String ORDER_BY_ASC = "CommerceOrder.total ASC";

	public static final String ORDER_BY_DESC = "CommerceOrder.total DESC";

	public static final String[] ORDER_BY_FIELDS = {"total"};

	public CommerceOrderTotalComparator() {
		this(false);
	}

	public CommerceOrderTotalComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceOrder commerceOrder1, CommerceOrder commerceOrder2) {

		BigDecimal total1 = commerceOrder1.getTotal();

		int value = total1.compareTo(commerceOrder2.getTotal());

		if (_ascending) {
			return value;
		}
		else {
			return Math.negateExact(value);
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}