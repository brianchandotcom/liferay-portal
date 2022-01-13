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

/**
 * @author Alessio Antonio Rendina
 */
public class ReplacementSku {

	public ReplacementSku(
		String name, String price, long replacementSkuId, String sku) {

		_name = name;
		_price = price;
		_replacementSkuId = replacementSkuId;
		_sku = sku;
	}

	public String getName() {
		return _name;
	}

	public String getPrice() {
		return _price;
	}

	public long getReplacementSkuId() {
		return _replacementSkuId;
	}

	public String getSku() {
		return _sku;
	}

	private final String _name;
	private final String _price;
	private final long _replacementSkuId;
	private final String _sku;

}