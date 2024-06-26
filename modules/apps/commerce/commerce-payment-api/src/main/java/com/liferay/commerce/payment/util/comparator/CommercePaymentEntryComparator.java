/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.util.comparator;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Sbarra
 */
public class CommercePaymentEntryComparator
	extends OrderByComparator<CommercePaymentEntry> {

	public static final String ORDER_BY_ASC = "paymentStatus ASC";

	public static final String ORDER_BY_DESC = "paymentStatus DESC";

	public static final String[] ORDER_BY_FIELDS = {"paymentStatus"};

	public CommercePaymentEntryComparator() {
		_ascending = false;
	}

	public CommercePaymentEntryComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommercePaymentEntry commercePaymentEntry1,
		CommercePaymentEntry commercePaymentEntry2) {

		int value = Double.compare(
			commercePaymentEntry1.getPaymentStatus(),
			commercePaymentEntry2.getPaymentStatus());

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