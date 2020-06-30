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

/**
 * @author Alessio Antonio Rendina
 */
public class ProductDisplayPage {

	public ProductDisplayPage(
		String layout, long productDisplayPageId, String productName) {

		_layout = layout;
		_productDisplayPageId = productDisplayPageId;
		_productName = productName;
	}

	public String getLayout() {
		return _layout;
	}

	public long getProductDisplayPageId() {
		return _productDisplayPageId;
	}

	public String getProductName() {
		return _productName;
	}

	private final String _layout;
	private final long _productDisplayPageId;
	private final String _productName;

}