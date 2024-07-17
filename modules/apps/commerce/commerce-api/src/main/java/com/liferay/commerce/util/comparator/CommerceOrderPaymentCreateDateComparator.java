/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderPaymentCreateDateComparator
	extends OrderByComparator<CommerceOrderPayment> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceOrderPaymentCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceOrderPayment commerceOrderPayment1,
		CommerceOrderPayment commerceOrderPayment2) {

		int value = DateUtil.compareTo(
			commerceOrderPayment1.getCreateDate(),
			commerceOrderPayment2.getCreateDate());

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

	private CommerceOrderPaymentCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceOrderPaymentCreateDateComparator
		_INSTANCE_ASCENDING = new CommerceOrderPaymentCreateDateComparator(
			true);

	private static final CommerceOrderPaymentCreateDateComparator
		_INSTANCE_DESCENDING = new CommerceOrderPaymentCreateDateComparator(
			false);

	private final boolean _ascending;

}