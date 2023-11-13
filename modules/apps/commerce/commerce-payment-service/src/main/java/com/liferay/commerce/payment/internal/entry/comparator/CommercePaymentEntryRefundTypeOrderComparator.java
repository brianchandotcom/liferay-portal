/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.entry.comparator;

import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundType;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePaymentEntryRefundTypeOrderComparator
	implements Comparator<CommercePaymentEntryRefundType>, Serializable {

	public CommercePaymentEntryRefundTypeOrderComparator() {
		this(true);
	}

	public CommercePaymentEntryRefundTypeOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommercePaymentEntryRefundType commercePaymentEntryRefundType1,
		CommercePaymentEntryRefundType commercePaymentEntryRefundType2) {

		int displayOrder1 = commercePaymentEntryRefundType1.getPriority();
		int displayOrder2 = commercePaymentEntryRefundType2.getPriority();

		int value = Integer.compare(displayOrder1, displayOrder2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}