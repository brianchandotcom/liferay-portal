/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.util.comparator;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemCreateDateComparator
	extends OrderByComparator<CommerceVirtualOrderItem> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceVirtualOrderItemCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceVirtualOrderItem commerceVirtualOrderItem1,
		CommerceVirtualOrderItem commerceVirtualOrderItem2) {

		int value = DateUtil.compareTo(
			commerceVirtualOrderItem1.getCreateDate(),
			commerceVirtualOrderItem2.getCreateDate());

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

	private CommerceVirtualOrderItemCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceVirtualOrderItemCreateDateComparator
		_INSTANCE_ASCENDING = new CommerceVirtualOrderItemCreateDateComparator(
			true);

	private static final CommerceVirtualOrderItemCreateDateComparator
		_INSTANCE_DESCENDING = new CommerceVirtualOrderItemCreateDateComparator(
			false);

	private final boolean _ascending;

}