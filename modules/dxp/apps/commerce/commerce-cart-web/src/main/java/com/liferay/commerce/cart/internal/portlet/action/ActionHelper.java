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

package com.liferay.commerce.cart.internal.portlet.action;

import com.liferay.commerce.cart.constants.CCartWebKeys;
import com.liferay.commerce.cart.model.CCart;
import com.liferay.commerce.cart.model.CCartItem;
import com.liferay.commerce.cart.service.CCartItemLocalService;
import com.liferay.commerce.cart.service.CCartLocalService;
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

	public CCart getCCart(RenderRequest renderRequest) throws PortalException {
		CCart cCart = (CCart)renderRequest.getAttribute(CCartWebKeys.C_CART);

		if (cCart != null) {
			return cCart;
		}

		long cCartId = ParamUtil.getLong(renderRequest, "cCartId");

		if (cCartId > 0) {
			cCart = _cCartLocalService.fetchCCart(cCartId);
		}

		if (cCart != null) {
			renderRequest.setAttribute(CCartWebKeys.C_CART, cCart);
		}

		return cCart;
	}

	public List<CCart> getCCarts(ResourceRequest resourceRequest)
		throws PortalException {

		List<CCart> cCarts = new ArrayList<>();

		long[] cCartIds = ParamUtil.getLongValues(resourceRequest, "rowIds");

		for (long cCartId : cCartIds) {
			CCart cCart = _cCartLocalService.getCCart(cCartId);

			cCarts.add(cCart);
		}

		return cCarts;
	}

	public List<CCartItem> getCCartItems(ResourceRequest resourceRequest)
			throws PortalException {

		List<CCartItem> cCartItems = new ArrayList<>();

		long[] cCartItemIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cCartItemId : cCartItemIds) {
			CCartItem cCartItem = _cCartItemLocalService.getCCartItem(
				cCartItemId);

			cCartItems.add(cCartItem);
		}

		return cCartItems;
	}

	@Reference
	private CCartLocalService _cCartLocalService;

	@Reference
	private CCartItemLocalService _cCartItemLocalService;

}