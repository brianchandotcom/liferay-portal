/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.util.comparator;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 */
public class CommerceInventoryWarehouseItemQuantityComparator
	extends OrderByComparator<CommerceInventoryWarehouseItem> {

	public static final String ORDER_BY_ASC = "CIWarehouseItem.quantity ASC";

	public static final String ORDER_BY_DESC = "CIWarehouseItem.quantity DESC";

	public static final String[] ORDER_BY_FIELDS = {"quantity"};

	public CommerceInventoryWarehouseItemQuantityComparator() {
		this(false);
	}

	public CommerceInventoryWarehouseItemQuantityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem1,
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem2) {

		BigDecimal commerceInventoryWarehouseItemQuantity =
			commerceInventoryWarehouseItem1.getQuantity();

		int value = commerceInventoryWarehouseItemQuantity.compareTo(
			commerceInventoryWarehouseItem2.getQuantity());

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