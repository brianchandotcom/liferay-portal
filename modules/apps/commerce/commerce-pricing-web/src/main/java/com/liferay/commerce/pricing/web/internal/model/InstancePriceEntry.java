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

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class InstancePriceEntry {

	public InstancePriceEntry(
		long priceEntryId, String createDateString, String name,
		String unitPrice) {

		_priceEntryId = priceEntryId;
		_createDateString = createDateString;
		_name = name;
		_unitPrice = unitPrice;
	}

	public String getCreateDateString() {
		return _createDateString;
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

	private final String _createDateString;
	private final String _name;
	private final long _priceEntryId;
	private final String _unitPrice;

}