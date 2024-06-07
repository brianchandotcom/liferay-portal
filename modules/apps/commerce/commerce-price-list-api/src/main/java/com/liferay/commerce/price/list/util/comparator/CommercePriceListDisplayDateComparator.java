/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.util.comparator;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListDisplayDateComparator
	extends OrderByComparator<CommercePriceList> {

	public static final String ORDER_BY_ASC = "displayDate ASC";

	public static final String ORDER_BY_DESC = "displayDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"displayDate"};

	public static CommercePriceListDisplayDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	public CommercePriceListDisplayDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommercePriceList commercePriceList1,
		CommercePriceList commercePriceList2) {

		int value = DateUtil.compareTo(
			commercePriceList1.getDisplayDate(),
			commercePriceList2.getDisplayDate());

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

	private static final CommercePriceListDisplayDateComparator
		_INSTANCE_ASCENDING = new CommercePriceListDisplayDateComparator(true);

	private static final CommercePriceListDisplayDateComparator
		_INSTANCE_DESCENDING = new CommercePriceListDisplayDateComparator(
			false);

	private final boolean _ascending;

}