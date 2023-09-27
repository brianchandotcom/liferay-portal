/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return Math.negateExact(value);
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
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