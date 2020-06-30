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

package com.liferay.commerce.inventory.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CIWarehouseItem {

	public CIWarehouseItem(
		String skuCode, int stockQuantity, int bookedQuantity,
		int replenishmentQuantity) {

		_skuCode = skuCode;
		_stockQuantity = stockQuantity;
		_bookedQuantity = bookedQuantity;
		_replenishmentQuantity = replenishmentQuantity;
	}

	public int getBookedQuantity() {
		return _bookedQuantity;
	}

	public int getReplenishmentQuantity() {
		return _replenishmentQuantity;
	}

	public String getSkuCode() {
		return _skuCode;
	}

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public void setBookedQuantity(int bookedQuantity) {
		_bookedQuantity = bookedQuantity;
	}

	public void setReplenishmentQuantity(int replenishmentQuantity) {
		_replenishmentQuantity = replenishmentQuantity;
	}

	public void setSkuCode(String skuCode) {
		_skuCode = skuCode;
	}

	public void setStockQuantity(int stockQuantity) {
		_stockQuantity = stockQuantity;
	}

	private int _bookedQuantity;
	private int _replenishmentQuantity;
	private String _skuCode;
	private int _stockQuantity;

}