/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductDisplayPage {

	public ProductDisplayPage(
		String name, long productDisplayPageId, String productName,
		String type) {

		_name = name;
		_productDisplayPageId = productDisplayPageId;
		_productName = productName;
		_type = type;
	}

	public String getName() {
		return _name;
	}

	public long getProductDisplayPageId() {
		return _productDisplayPageId;
	}

	public String getProductName() {
		return _productName;
	}

	public String getType() {
		return _type;
	}

	private final String _name;
	private final long _productDisplayPageId;
	private final String _productName;
	private final String _type;

}