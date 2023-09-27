/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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