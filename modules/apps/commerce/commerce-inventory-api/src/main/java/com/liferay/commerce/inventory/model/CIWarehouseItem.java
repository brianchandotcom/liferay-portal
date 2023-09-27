/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CIWarehouseItem {

	public CIWarehouseItem(
		String skuCode, String unitOfMeasureKey, BigDecimal bookedQuantity,
		BigDecimal replenishmentQuantity, BigDecimal stockQuantity) {

		_skuCode = skuCode;
		_unitOfMeasureKey = unitOfMeasureKey;
		_bookedQuantity = bookedQuantity;
		_replenishmentQuantity = replenishmentQuantity;
		_stockQuantity = stockQuantity;
	}

	public BigDecimal getBookedQuantity() {
		return _bookedQuantity;
	}

	public BigDecimal getReplenishmentQuantity() {
		return _replenishmentQuantity;
	}

	public String getSkuCode() {
		return _skuCode;
	}

	public BigDecimal getStockQuantity() {
		return _stockQuantity;
	}

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	public void setBookedQuantity(BigDecimal bookedQuantity) {
		_bookedQuantity = bookedQuantity;
	}

	public void setReplenishmentQuantity(BigDecimal replenishmentQuantity) {
		_replenishmentQuantity = replenishmentQuantity;
	}

	public void setSkuCode(String skuCode) {
		_skuCode = skuCode;
	}

	public void setStockQuantity(BigDecimal stockQuantity) {
		_stockQuantity = stockQuantity;
	}

	public void setUnitOfMeasureKey(String unitOfMeasureKey) {
		_unitOfMeasureKey = unitOfMeasureKey;
	}

	private BigDecimal _bookedQuantity;
	private BigDecimal _replenishmentQuantity;
	private String _skuCode;
	private BigDecimal _stockQuantity;
	private String _unitOfMeasureKey;

}