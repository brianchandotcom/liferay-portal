/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseItemQuantityComparator
	extends OrderByComparator<CommerceWarehouseItem> {

	public static final String ORDER_BY_ASC =
		"CommerceWarehouseItem.quantity ASC";

	public static final String ORDER_BY_DESC =
		"CommerceWarehouseItem.quantity DESC";

	public static final String[] ORDER_BY_FIELDS = {"quantity"};

	public CommerceWarehouseItemQuantityComparator() {
		this(false);
	}

	public CommerceWarehouseItemQuantityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceWarehouseItem commerceWarehouseItem1,
		CommerceWarehouseItem commerceWarehouseItem2) {

		int value = Integer.compare(
			commerceWarehouseItem1.getQuantity(),
			commerceWarehouseItem2.getQuantity());

		if (_ascending) {
			return value;
		}
		else {
			return Math.negateExact(value);
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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