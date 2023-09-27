/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecast;

/**
 * @author Riccardo Ferrari
 */
public class AssetCategoryCommerceMLForecastImpl
	extends BaseCommerceMLForecastImpl
	implements AssetCategoryCommerceMLForecast {

	@Override
	public long getAssetCategoryId() {
		return _assetCategoryId;
	}

	@Override
	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		_assetCategoryId = assetCategoryId;
	}

	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	private long _assetCategoryId;
	private long _commerceAccountId;

}