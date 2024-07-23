/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.util.comparator;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountCreateDateComparator
	extends OrderByComparator<CommerceDiscount> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceDiscountCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceDiscount commerceDiscount1,
		CommerceDiscount commerceDiscount2) {

		int value = DateUtil.compareTo(
			commerceDiscount1.getCreateDate(),
			commerceDiscount2.getCreateDate());

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

	private CommerceDiscountCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceDiscountCreateDateComparator
		_INSTANCE_ASCENDING = new CommerceDiscountCreateDateComparator(true);

	private static final CommerceDiscountCreateDateComparator
		_INSTANCE_DESCENDING = new CommerceDiscountCreateDateComparator(false);

	private final boolean _ascending;

}