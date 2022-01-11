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

package com.liferay.commerce.product.definitions.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class Sku {

	public Sku(
		long cpInstanceId, String sku, String options, String price,
		String productName, int availableQuantity, LabelField status,
		String discontinued) {

		_cpInstanceId = cpInstanceId;
		_sku = sku;
		_options = options;
		_price = price;
		_productName = productName;
		_availableQuantity = availableQuantity;
		_status = status;
		_discontinued = discontinued;
	}

	public int getAvailableQuantity() {
		return _availableQuantity;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public String getDiscontinued() {
		return _discontinued;
	}

	public String getOptions() {
		return _options;
	}

	public String getPrice() {
		return _price;
	}

	public String getProductName() {
		return _productName;
	}

	public String getSku() {
		return _sku;
	}

	public LabelField getStatus() {
		return _status;
	}

	private final int _availableQuantity;
	private final long _cpInstanceId;
	private final String _discontinued;
	private final String _options;
	private final String _price;
	private final String _productName;
	private final String _sku;
	private final LabelField _status;

}