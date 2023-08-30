/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;

/**
 * @author Alessio Antonio Rendina
 */
public class PriceEntry {

	public PriceEntry(
		String basePrice, ImageField image, String name, long priceEntryId,
		String sku, String tieredPrice, String unitDiscount,
		String unitOfMeasureKey, String unitPrice) {

		_basePrice = basePrice;
		_image = image;
		_name = name;
		_priceEntryId = priceEntryId;
		_sku = sku;
		_tieredPrice = tieredPrice;
		_unitDiscount = unitDiscount;
		_unitOfMeasureKey = unitOfMeasureKey;
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

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
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
	private final String _unitOfMeasureKey;
	private final String _unitPrice;

}