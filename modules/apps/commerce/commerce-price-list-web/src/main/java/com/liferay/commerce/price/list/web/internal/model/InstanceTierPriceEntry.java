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

package com.liferay.commerce.price.list.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class InstanceTierPriceEntry {

	public InstanceTierPriceEntry(
		long priceTierEntryId, String price, int minQuantity,
		String createDate) {

		_priceTierEntryId = priceTierEntryId;
		_price = price;
		_minQuantity = minQuantity;
		_createDate = createDate;
	}

	public String getCreateDate() {
		return _createDate;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public String getPrice() {
		return _price;
	}

	public long getTierPriceEntryId() {
		return _priceTierEntryId;
	}

	private final String _createDate;
	private final int _minQuantity;
	private final String _price;
	private final long _priceTierEntryId;

}