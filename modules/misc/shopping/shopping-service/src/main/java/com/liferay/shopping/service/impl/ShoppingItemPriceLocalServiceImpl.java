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

package com.liferay.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.model.ShoppingItemPriceConstants;
import com.liferay.shopping.service.base.ShoppingItemPriceLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemPriceLocalServiceImpl
	extends ShoppingItemPriceLocalServiceBaseImpl {

	@Override
	public List<ShoppingItemPrice> getItemPrices(long itemId)
		throws PortalException {

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		List<ShoppingItemPrice> itemPrices =
			shoppingItemPricePersistence.findByItemId(itemId);

		if (itemPrices.isEmpty()) {
			itemPrices = new ArrayList<>();

			ShoppingItemPrice itemPrice = shoppingItemPricePersistence.create(
				0);

			itemPrice.setItemId(itemId);
			itemPrice.setMinQuantity(item.getMinQuantity());
			itemPrice.setMaxQuantity(item.getMaxQuantity());
			itemPrice.setPrice(item.getPrice());
			itemPrice.setDiscount(item.getDiscount());
			itemPrice.setTaxable(item.isTaxable());
			itemPrice.setShipping(item.getShipping());
			itemPrice.setUseShippingFormula(item.isUseShippingFormula());
			itemPrice.setStatus(
				ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT);

			itemPrices.add(itemPrice);
		}

		return itemPrices;
	}

}