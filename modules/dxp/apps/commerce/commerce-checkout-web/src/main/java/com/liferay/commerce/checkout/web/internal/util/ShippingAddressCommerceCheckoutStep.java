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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.checkout.web.internal.display.context.BaseAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.internal.display.context.ShippingAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.name=" + ShippingAddressCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=20"
	},
	service = CommerceCheckoutStep.class
)
public class ShippingAddressCommerceCheckoutStep
	extends BaseAddressCommerceCheckoutStep {

	public static final String NAME = "shipping-address";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected BaseAddressCheckoutStepDisplayContext
			getBaseAddressCheckoutStepDisplayContext(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
		throws PortalException {

		return new ShippingAddressCheckoutStepDisplayContext(
			commerceAddressService, _commerceCartHelper, httpServletRequest,
			httpServletResponse);
	}

	@Override
	protected String getParamName() {
		return "shippingAddressId";
	}

	@Override
	protected void updateCommerceCartAddress(
			CommerceCart commerceCart, long commerceAddressId)
		throws PortalException {

		commerceCartService.updateCommerceCart(
			commerceCart.getCommerceCartId(),
			commerceCart.getBillingAddressId(), commerceAddressId,
			commerceCart.getCommerceShippingMethodId(),
			commerceCart.getShippingOptionName());
	}

	@Reference
	private CommerceCartHelper _commerceCartHelper;

}