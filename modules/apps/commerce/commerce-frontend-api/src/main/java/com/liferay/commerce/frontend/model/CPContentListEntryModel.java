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
public class CPContentListEntryModel extends CPContentModel {

	public CPContentListEntryModel(
		long accountId, long channelId, long cpDefinitionId,
		String currencyCode, boolean inCart, boolean inWishList, long orderId,
		long skuId, String spritemap, int stockQuantity) {

		super(
			accountId, channelId, cpDefinitionId, currencyCode, inCart,
			inWishList, orderId, spritemap, stockQuantity);

		_skuId = skuId;
	}

	public long getSkuId() {
		return _skuId;
	}

	private final long _skuId;

}