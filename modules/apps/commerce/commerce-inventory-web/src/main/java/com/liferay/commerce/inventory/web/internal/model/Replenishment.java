/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Replenishment {

	public Replenishment(
		long commerceInventoryReplenishmentItemId, String warehouse,
		String date, BigDecimal quantity, String unitOfMeasureKey) {

		_commerceInventoryReplenishmentItemId =
			commerceInventoryReplenishmentItemId;
		_warehouse = warehouse;
		_date = date;
		_quantity = quantity;
		_unitOfMeasureKey = unitOfMeasureKey;
	}

	public long getCommerceInventoryReplenishmentItemId() {
		return _commerceInventoryReplenishmentItemId;
	}

	public String getDate() {
		return _date;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final long _commerceInventoryReplenishmentItemId;
	private final String _date;
	private final BigDecimal _quantity;
	private final String _unitOfMeasureKey;
	private final String _warehouse;

}