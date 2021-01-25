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

package com.liferay.commerce.frontend.model;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Gianmarco Brunialti Masera
 */
public class CPContentListEntryModel extends CPContentModel {

	public CPContentListEntryModel(
		long accountId, long channelId, boolean compareCheckboxVisible,
		boolean compareDeleteButtonVisible, JSONObject compareStateJSONObject,
		long cpDefinitionId, String currencyCode, String description,
		boolean inCart, boolean inWishList, boolean lowStock, String name,
		long orderId, PriceModel prices, String productDetailURL,
		String productImageURL, ProductSettingsModel settings, String sku,
		long skuId, String spritemap, int stockQuantity) {

		super(
			accountId, channelId, cpDefinitionId, currencyCode, inCart,
			inWishList, lowStock, orderId, spritemap, stockQuantity);

		_compareCheckboxVisible = compareCheckboxVisible;
		_compareDeleteButtonVisible = compareDeleteButtonVisible;
		_compareStateJSONObject = compareStateJSONObject;
		_description = description;
		_name = name;
		_prices = prices;
		_productDetailURL = productDetailURL;
		_productImageURL = productImageURL;
		_settings = settings;
		_sku = sku;
		_skuId = skuId;
	}

	public JSONObject getCompareStateJSONObject() {
		return _compareStateJSONObject;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public PriceModel getPrices() {
		return _prices;
	}

	public String getProductDetailURL() {
		return _productDetailURL;
	}

	public String getProductImageURL() {
		return _productImageURL;
	}

	public ProductSettingsModel getSettings() {
		return _settings;
	}

	public String getSku() {
		return _sku;
	}

	public long getSkuId() {
		return _skuId;
	}

	public boolean isCompareCheckboxVisible() {
		return _compareCheckboxVisible;
	}

	public boolean isCompareDeleteButtonVisible() {
		return _compareDeleteButtonVisible;
	}

	private final boolean _compareCheckboxVisible;
	private final boolean _compareDeleteButtonVisible;
	private final JSONObject _compareStateJSONObject;
	private final String _description;
	private final String _name;
	private final PriceModel _prices;
	private final String _productDetailURL;
	private final String _productImageURL;
	private final ProductSettingsModel _settings;
	private final String _sku;
	private final long _skuId;

}