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

import com.liferay.commerce.frontend.model.ImageField;

/**
 * @author Alessio Antonio Rendina
 */
public class PriceEntry {

	public PriceEntry(
		String basePrice, ImageField image, String name, long priceEntryId,
		String sku, String tieredPrice, String unitDiscount, String unitPrice) {

		_basePrice = basePrice;
		_image = image;
		_name = name;
		_priceEntryId = priceEntryId;
		_sku = sku;
		_tieredPrice = tieredPrice;
		_unitDiscount = unitDiscount;
		_unitPrice = unitPrice;
	}

	public String getBasePrice() {
		return _basePrice;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getName() {
		return _name;
	}

	public long getPriceEntryId() {
		return _priceEntryId;
	}

	public String getSku() {
		return _sku;
	}

	public String getTieredPrice() {
		return _tieredPrice;
	}

	public String getUnitDiscount() {
		return _unitDiscount;
	}

	public String getUnitPrice() {
		return _unitPrice;
	}

	private final String _basePrice;
	private final ImageField _image;
	private final String _name;
	private final long _priceEntryId;
	private final String _sku;
	private final String _tieredPrice;
	private final String _unitDiscount;
	private final String _unitPrice;

}