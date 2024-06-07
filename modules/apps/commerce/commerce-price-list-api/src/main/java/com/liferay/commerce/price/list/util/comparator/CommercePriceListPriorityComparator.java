/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.util.comparator;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListPriorityComparator
	extends OrderByComparator<CommercePriceList> {

	public static final String ORDER_BY_ASC = "CommercePriceList.priority ASC";

	public static final String ORDER_BY_DESC =
		"CommercePriceList.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static CommercePriceListPriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommercePriceList commercePriceList1,
		CommercePriceList commercePriceList2) {

		int value = Double.compare(
			commercePriceList1.getPriority(), commercePriceList2.getPriority());

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

	private CommercePriceListPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommercePriceListPriorityComparator
		_INSTANCE_ASCENDING = new CommercePriceListPriorityComparator(true);

	private static final CommercePriceListPriorityComparator
		_INSTANCE_DESCENDING = new CommercePriceListPriorityComparator(false);

	private final boolean _ascending;

}