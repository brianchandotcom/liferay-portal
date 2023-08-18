/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Warehouse {

	public Warehouse(
		long commerceInventoryWarehouseItemId, String warehouse,
		BigDecimal incoming, BigDecimal reserved, BigDecimal quantity) {

		_commerceInventoryWarehouseItemId = commerceInventoryWarehouseItemId;
		_warehouse = warehouse;
		_incoming = incoming;
		_reserved = reserved;
		_quantity = quantity;

		if (BigDecimalUtil.gt(quantity, BigDecimal.ZERO) &&
			BigDecimalUtil.gte(reserved, BigDecimal.ZERO)) {

			_available = quantity.subtract(reserved);
		}
		else {
			_available = BigDecimal.ZERO;
		}
	}

	public BigDecimal getAvailable() {
		return _available;
	}

	public long getCommerceInventoryWarehouseItemId() {
		return _commerceInventoryWarehouseItemId;
	}

	public BigDecimal getIncoming() {
		return _incoming;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public BigDecimal getReserved() {
		return _reserved;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final BigDecimal _available;
	private final long _commerceInventoryWarehouseItemId;
	private final BigDecimal _incoming;
	private final BigDecimal _quantity;
	private final BigDecimal _reserved;
	private final String _warehouse;

}