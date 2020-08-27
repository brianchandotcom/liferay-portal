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
public class InstancePriceEntry {

	public InstancePriceEntry(
		long priceEntryId, String name, String unitPrice, String createDate) {

		_priceEntryId = priceEntryId;
		_name = name;
		_unitPrice = unitPrice;
		_createDate = createDate;
	}

	public String getCreateDate() {
		return _createDate;
	}

	public String getName() {
		return _name;
	}

	public long getPriceEntryId() {
		return _priceEntryId;
	}

	public String getUnitPrice() {
		return _unitPrice;
	}

	private final String _createDate;
	private final String _name;
	private final long _priceEntryId;
	private final String _unitPrice;

}