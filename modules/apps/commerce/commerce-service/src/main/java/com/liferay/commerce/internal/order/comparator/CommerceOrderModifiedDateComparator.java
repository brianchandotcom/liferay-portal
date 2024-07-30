/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.comparator;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrderModifiedDateComparator
	extends OrderByComparator<CommerceOrder> {

	public static final String ORDER_BY_ASC = "modifiedDate ASC";

	public static final String ORDER_BY_DESC = "modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static CommerceOrderModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(CommerceOrder o1, CommerceOrder o2) {
		int value = DateUtil.compareTo(
			o1.getModifiedDate(), o2.getModifiedDate());

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

	private CommerceOrderModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceOrderModifiedDateComparator
		_INSTANCE_ASCENDING = new CommerceOrderModifiedDateComparator(true);

	private static final CommerceOrderModifiedDateComparator
		_INSTANCE_DESCENDING = new CommerceOrderModifiedDateComparator(false);

	private final boolean _ascending;

}