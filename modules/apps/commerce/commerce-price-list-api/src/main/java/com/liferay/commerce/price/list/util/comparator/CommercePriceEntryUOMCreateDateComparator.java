/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.util.comparator;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceEntryUOMCreateDateComparator
	extends OrderByComparator<CommercePriceEntry> {

	public static final String ORDER_BY_ASC =
		"unitOfMeasureKey ASC, createDate ASC";

	public static final String ORDER_BY_DESC =
		"unitOfMeasureKey DESC, createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommercePriceEntryUOMCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommercePriceEntry commercePriceEntry1,
		CommercePriceEntry commercePriceEntry2) {

		String uom1 = commercePriceEntry1.getUnitOfMeasureKey();
		String uom2 = commercePriceEntry2.getUnitOfMeasureKey();

		int value = uom1.compareTo(uom2);

		if (value == 0) {
			value = DateUtil.compareTo(
				commercePriceEntry1.getCreateDate(),
				commercePriceEntry2.getCreateDate());
		}

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

	private CommercePriceEntryUOMCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommercePriceEntryUOMCreateDateComparator
		_INSTANCE_ASCENDING = new CommercePriceEntryUOMCreateDateComparator(
			true);

	private static final CommercePriceEntryUOMCreateDateComparator
		_INSTANCE_DESCENDING = new CommercePriceEntryUOMCreateDateComparator(
			false);

	private final boolean _ascending;

}