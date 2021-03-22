/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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