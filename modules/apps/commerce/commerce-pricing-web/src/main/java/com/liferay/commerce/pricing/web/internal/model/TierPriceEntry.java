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
public class TierPriceEntry {

	public TierPriceEntry(
		String discountLevels, String endDate, String override, String price,
		int quantity, String startDate, long tierPriceEntryId) {

		_discountLevels = discountLevels;
		_endDate = endDate;
		_override = override;
		_price = price;
		_quantity = quantity;
		_startDate = startDate;
		_tierPriceEntryId = tierPriceEntryId;
	}

	public String getDiscountLevels() {
		return _discountLevels;
	}

	public String getEndDate() {
		return _endDate;
	}

	public String getOverride() {
		return _override;
	}

	public String getPrice() {
		return _price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getStartDate() {
		return _startDate;
	}

	public long getTierPriceEntryId() {
		return _tierPriceEntryId;
	}

	private final String _discountLevels;
	private final String _endDate;
	private final String _override;
	private final String _price;
	private final int _quantity;
	private final String _startDate;
	private final long _tierPriceEntryId;

}