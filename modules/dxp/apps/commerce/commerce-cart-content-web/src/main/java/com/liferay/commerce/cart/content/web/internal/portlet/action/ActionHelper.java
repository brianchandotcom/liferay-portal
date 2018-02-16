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

package com.liferay.commerce.cart.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

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

	@Reference
	private CommerceCartService _commerceCartService;

}