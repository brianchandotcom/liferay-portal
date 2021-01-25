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

/**
 * @author Gianmarco Brunialti Masera
 */
public class CPContentModel {

	public CPContentModel(
		long accountId, long channelId, long cpDefinitionId,
		String currencyCode, boolean inCart, boolean inWishList,
		boolean lowStock, long orderId, String spritemap, int stockQuantity) {

		_accountId = accountId;
		_channelId = channelId;
		_cpDefinitionId = cpDefinitionId;
		_currencyCode = currencyCode;
		_inCart = inCart;
		_inWishList = inWishList;
		_lowStock = lowStock;
		_orderId = orderId;
		_spritemap = spritemap;
		_stockQuantity = stockQuantity;
	}

	public long getAccountId() {
		return _accountId;
	}

	public long getChannelId() {
		return _channelId;
	}

	public long getCpDefinitionId() {
		return _cpDefinitionId;
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public long getOrderId() {
		return _orderId;
	}

	public String getSpritemap() {
		return _spritemap;
	}

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public boolean isInCart() {
		return _inCart;
	}

	public boolean isInWishList() {
		return _inWishList;
	}

	public boolean isLowStock() {
		return _lowStock;
	}

	private final long _accountId;
	private final long _channelId;
	private final long _cpDefinitionId;
	private final String _currencyCode;
	private final boolean _inCart;
	private final boolean _inWishList;
	private final boolean _lowStock;
	private final long _orderId;
	private final String _spritemap;
	private final int _stockQuantity;

}