/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.comparator;

import com.liferay.commerce.order.CommerceReturnReason;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Crescenzo Rega
 */
public class CommerceReturnReasonOrderComparator
	implements Comparator<CommerceReturnReason>, Serializable {

	public CommerceReturnReasonOrderComparator() {
		this(true);
	}

	public CommerceReturnReasonOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceReturnReason commerceReturnReason1,
		CommerceReturnReason commerceReturnReason2) {

		int priority1 = commerceReturnReason1.getPriority();
		int priority2 = commerceReturnReason2.getPriority();

		int value = Integer.compare(priority1, priority2);

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