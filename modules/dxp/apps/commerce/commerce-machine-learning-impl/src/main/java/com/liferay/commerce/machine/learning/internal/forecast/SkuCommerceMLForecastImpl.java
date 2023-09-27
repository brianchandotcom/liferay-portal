/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;

/**
 * @author Riccardo Ferrari
 */
public class SkuCommerceMLForecastImpl
	extends BaseCommerceMLForecastImpl implements SkuCommerceMLForecast {

	@Override
	public String getSku() {
		return _sku;
	}

	@Override
	public void setSku(String sku) {
		_sku = sku;
	}

	private String _sku;

}