/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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