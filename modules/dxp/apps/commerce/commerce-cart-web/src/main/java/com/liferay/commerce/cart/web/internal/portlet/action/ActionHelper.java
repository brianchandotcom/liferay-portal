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

package com.liferay.commerce.cart.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CommerceCart getCommerceCart(RenderRequest renderRequest)
		throws PortalException {

		CommerceCart commerceCart = (CommerceCart)renderRequest.getAttribute(
			CommerceWebKeys.COMMERCE_ORDER);

		if (commerceCart != null) {
			return commerceCart;
		}

		long commerceCartId = ParamUtil.getLong(
			renderRequest, "commerceCartId");

		if (commerceCartId > 0) {
			commerceCart = _commerceCartService.fetchCommerceCart(
				commerceCartId);
		}

		if (commerceCart != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ORDER, commerceCart);
		}

		return commerceCart;
	}

	public CommerceCartItem getCommerceCartItem(RenderRequest renderRequest)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			(CommerceCartItem)renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_ORDER_ITEM);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		long commerceCartItemId = ParamUtil.getLong(
			renderRequest, "commerceCartItemId");

		if (commerceCartItemId > 0) {
			commerceCartItem = _commerceCartItemService.fetchCommerceCartItem(
				commerceCartItemId);
		}

		if (commerceCartItem != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ORDER_ITEM, commerceCartItem);
		}

		return commerceCartItem;
	}

	public List<CommerceCartItem> getCommerceCartItems(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceCartItem> commerceCartItems = new ArrayList<>();

		long[] commerceCartItemIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceCartItemId : commerceCartItemIds) {
			CommerceCartItem commerceCartItem =
				_commerceCartItemService.fetchCommerceCartItem(
					commerceCartItemId);

			commerceCartItems.add(commerceCartItem);
		}

		return commerceCartItems;
	}

	public List<CommerceCart> getCommerceCarts(ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceCart> commerceCarts = new ArrayList<>();

		long[] commerceCartIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceCartId : commerceCartIds) {
			CommerceCart commerceCart = _commerceCartService.getCommerceCart(
				commerceCartId);

			commerceCarts.add(commerceCart);
		}

		return commerceCarts;
	}

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommerceCartService _commerceCartService;

}