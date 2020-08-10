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

package com.liferay.commerce.shipment.web.internal.model;

/**
 * @author Alec Sloan
 */
public class WarehouseItem {

	public WarehouseItem(
		String inputName, int maxQuantity, int minQuantity, int quantity) {

		_inputName = inputName;
		_maxQuantity = maxQuantity;
		_minQuantity = minQuantity;
		_quantity = quantity;
	}

	public String getInputName() {
		return _inputName;
	}

	public int getMaxQuantity() {
		return _maxQuantity;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public int getQuantity() {
		return _quantity;
	}

	private final String _inputName;
	private final int _maxQuantity;
	private final int _minQuantity;
	private final int _quantity;

}