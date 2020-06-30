/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Replenishment {

	public Replenishment(
		long commerceInventoryReplenishmentItemId, String warehouse,
		String date, int quantity) {

		_commerceInventoryReplenishmentItemId =
			commerceInventoryReplenishmentItemId;
		_warehouse = warehouse;
		_date = date;
		_quantity = quantity;
	}

	public long getCommerceInventoryReplenishmentItemId() {
		return _commerceInventoryReplenishmentItemId;
	}

	public String getDate() {
		return _date;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final long _commerceInventoryReplenishmentItemId;
	private final String _date;
	private final int _quantity;
	private final String _warehouse;

}