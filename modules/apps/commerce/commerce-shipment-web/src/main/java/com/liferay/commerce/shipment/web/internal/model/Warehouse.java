/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Alec Sloan
 */
public class Warehouse {

	public Warehouse(
		long warehouseId, WarehouseItem warehouseItem, BigDecimal available,
		String distance, String name) {

		_warehouseId = warehouseId;
		_warehouseItem = warehouseItem;
		_available = available;
		_distance = distance;
		_name = name;
	}

	public BigDecimal getAvailable() {
		return _available;
	}

	public String getDistance() {
		return _distance;
	}

	public String getName() {
		return _name;
	}

	public long getWarehouseId() {
		return _warehouseId;
	}

	public WarehouseItem getWarehouseItem() {
		return _warehouseItem;
	}

	private final BigDecimal _available;
	private final String _distance;
	private final String _name;
	private final long _warehouseId;
	private final WarehouseItem _warehouseItem;

}