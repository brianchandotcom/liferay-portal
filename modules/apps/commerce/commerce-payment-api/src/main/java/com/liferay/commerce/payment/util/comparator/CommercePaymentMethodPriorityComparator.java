/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.util.comparator;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentMethodPriorityComparator
	implements Comparator<CommercePaymentMethodGroupRel>, Serializable {

	public CommercePaymentMethodPriorityComparator() {
		_ascending = false;
	}

	public CommercePaymentMethodPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommercePaymentMethodGroupRel commercePaymentMethod1,
		CommercePaymentMethodGroupRel commercePaymentMethod2) {

		int value = Double.compare(
			commercePaymentMethod1.getPriority(),
			commercePaymentMethod2.getPriority());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	private final boolean _ascending;

}