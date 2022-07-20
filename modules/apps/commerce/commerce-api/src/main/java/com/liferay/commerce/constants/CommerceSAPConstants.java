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

package com.liferay.commerce.constants;

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.StringBundler;

/**
 * @author Luca Pellizzon
 */
public class CommerceSAPConstants {

	public static final String CLASS_NAME_COMMERCE_CART_RESOURCE =
		"com.liferay.commerce.frontend.internal.cart.CommerceCartResource";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE =
		"com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0." +
			"CartItemResourceImpl";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE =
		"com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0." +
			"CartResourceImpl";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_CHANNEL_RESOURCE =
		"com.liferay.headless.commerce.delivery.catalog.internal.resource." +
			"v1_0.ChannelResourceImpl";

	public static final String
		CLASS_NAME_COMMERCE_HEADLESS_MAPPED_PRODUCT_RESOURCE =
			"com.liferay.headless.commerce.delivery.catalog.internal." +
				"resource.v1_0.MappedProductResourceImpl";

	public static final String CLASS_NAME_COMMERCE_HEADLESS_PIN_RESOURCE =
		"com.liferay.headless.commerce.delivery.catalog.internal.resource." +
			"v1_0.PinResourceImpl";

	public static final String CLASS_NAME_COMMERCE_SEARCH_RESOURCE =
		"com.liferay.commerce.frontend.internal.search.CommerceSearchResource";

	public static final String SAP_ENTRY_NAME = "COMMERCE_DEFAULT";

	public static final String[][] SAP_ENTRY_OBJECT_ARRAYS = {
		{
			SAP_ENTRY_NAME,
			StringBundler.concat(
				CommerceAccountService.class.getName(), "#getCommerceAccount\n",
				CommerceOrderItemService.class.getName(),
				"#addOrUpdateCommerceOrderItem\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItem\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItems\n",
				CommerceOrderItemService.class.getName(),
				"#getCommerceOrderItemsQuantity\n",
				CommerceOrderService.class.getName(), "#addCommerceOrder\n",
				CommerceOrderService.class.getName(), "#fetchCommerceOrder\n",
				CommerceOrderService.class.getName(), "#getCommerceOrder\n",
				CLASS_NAME_COMMERCE_CART_RESOURCE, "*\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#deleteCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#getCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#patchCartItem\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_ITEM_RESOURCE,
				"#postCartItem\n", CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#getCart\n", CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#getChannelCartsPage\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE, "#patchCart\n",
				CLASS_NAME_COMMERCE_HEADLESS_CART_RESOURCE,
				"#postChannelCart\n",
				CLASS_NAME_COMMERCE_HEADLESS_CHANNEL_RESOURCE,
				"#getChannelsPage\n",
				CLASS_NAME_COMMERCE_HEADLESS_MAPPED_PRODUCT_RESOURCE,
				"#getChannelProductMappedProductsPage\n",
				CLASS_NAME_COMMERCE_HEADLESS_PIN_RESOURCE,
				"#getChannelProductPinsPage\n",
				CLASS_NAME_COMMERCE_SEARCH_RESOURCE)
		}
	};

}