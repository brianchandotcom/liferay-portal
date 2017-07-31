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

package com.liferay.commerce.cart.content.web.internal.theme.contributor;

import com.liferay.commerce.cart.constants.CommerceCartConstants;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.CommerceCartItemService;
import com.liferay.commerce.cart.util.CommerceCartHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {"type=" + TemplateContextContributor.TYPE_THEME},
	service = TemplateContextContributor.class
)
public class CommerceCartTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		int cartItemsCount = 0;
		int wishListItemsCount = 0;

		try {
			CommerceCart currentCart = _commerceCartHelper.getCurrentCart(
				request, CommerceCartConstants.COMMERCE_CART_TYPE_CART);

			if (currentCart != null) {
				cartItemsCount =
					_commerceCartItemService.getCommerceCartItemsCount(
						currentCart.getCommerceCartId());
			}

			CommerceCart currentWishList = _commerceCartHelper.getCurrentCart(
				request, CommerceCartConstants.COMMERCE_CART_TYPE_WISH_LIST);

			if (currentWishList != null) {
				wishListItemsCount =
					_commerceCartItemService.getCommerceCartItemsCount(
						currentWishList.getCommerceCartId());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		contextObjects.put("cartItemsCount", cartItemsCount);
		contextObjects.put("wishListItemsCount", wishListItemsCount);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCartTemplateContextContributor.class);

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceCartItemService _commerceCartItemService;

}