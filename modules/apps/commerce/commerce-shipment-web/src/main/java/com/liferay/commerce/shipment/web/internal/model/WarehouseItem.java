/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.web.internal.model;

import java.math.BigDecimal;

/**
 * @author Alec Sloan
 */
public class WarehouseItem {

	public WarehouseItem(
		String inputName, BigDecimal maxQuantity, BigDecimal minQuantity,
		BigDecimal quantity) {

		_inputName = inputName;
		_maxQuantity = maxQuantity;
		_minQuantity = minQuantity;
		_quantity = quantity;
	}

	public String getInputName() {
		return _inputName;
	}

	public BigDecimal getMaxQuantity() {
		return _maxQuantity;
	}

	public BigDecimal getMinQuantity() {
		return _minQuantity;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	private final String _inputName;
	private final BigDecimal _maxQuantity;
	private final BigDecimal _minQuantity;
	private final BigDecimal _quantity;

}