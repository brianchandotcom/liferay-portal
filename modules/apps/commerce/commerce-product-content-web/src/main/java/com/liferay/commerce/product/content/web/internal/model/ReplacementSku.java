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

package com.liferay.commerce.product.content.web.internal.model;

import com.liferay.commerce.frontend.model.PriceModel;

/**
 * @author Alessio Antonio Rendina
 */
public class ReplacementSku {

	public ReplacementSku(
		String name, PriceModel priceModel, long replacementSkuId, String sku) {

		_name = name;
		_priceModel = priceModel;
		_replacementSkuId = replacementSkuId;
		_sku = sku;
	}

	public String getName() {
		return _name;
	}

	public PriceModel getPriceModel() {
		return _priceModel;
	}

	public long getReplacementSkuId() {
		return _replacementSkuId;
	}

	public String getSku() {
		return _sku;
	}

	private final String _name;
	private final PriceModel _priceModel;
	private final long _replacementSkuId;
	private final String _sku;

}