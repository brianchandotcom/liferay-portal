/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 */
public class InventoryItem {

	public InventoryItem(
		String sku, String unitOfMeasureKey, BigDecimal booked,
		BigDecimal incoming, BigDecimal stock) {

		_sku = sku;
		_unitOfMeasureKey = unitOfMeasureKey;
		_booked = booked;
		_incoming = incoming;
		_stock = stock;

		if (BigDecimalUtil.gt(stock, BigDecimal.ZERO) &&
			BigDecimalUtil.gte(booked, BigDecimal.ZERO)) {

			_available = stock.subtract(booked);
		}
		else {
			_available = BigDecimal.ZERO;
		}
	}

	public BigDecimal getAvailable() {
		return _available;
	}

	public BigDecimal getBooked() {
		return _booked;
	}

	public BigDecimal getIncoming() {
		return _incoming;
	}

	public String getSku() {
		return _sku;
	}

	public BigDecimal getStock() {
		return _stock;
	}

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	private final BigDecimal _available;
	private final BigDecimal _booked;
	private final BigDecimal _incoming;
	private final String _sku;
	private final BigDecimal _stock;
	private final String _unitOfMeasureKey;

}