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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class BillingAddressCheckoutStepDisplayContext {

	public BillingAddressCheckoutStepDisplayContext(
			CommerceAddressService commerceAddressService,
			CommerceCartHelper commerceCartHelper,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commerceAddressService = commerceAddressService;

		_commerceCart = commerceCartHelper.getCurrentCommerceCart(
			httpServletRequest, httpServletResponse,
			CommerceConstants.COMMERCE_CART_TYPE_CART);
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		return _commerceAddressService.getCommerceAddresses(
			_commerceCart.getGroupId(), _commerceCart.getUserId());
	}

	public CommerceCart getCommerceCart() {
		return _commerceCart;
	}

	public long getDefaultBillingAddressId() throws PortalException {
		long defaultBillingAddressId = 0;

		List<CommerceAddress> commerceAddresses = getCommerceAddresses();

		for (CommerceAddress commerceAddress : commerceAddresses) {
			if (commerceAddress.isDefaultBilling()) {
				defaultBillingAddressId =
					commerceAddress.getCommerceAddressId();

				break;
			}
		}

		if ((defaultBillingAddressId == 0) && !commerceAddresses.isEmpty()) {
			CommerceAddress commerceAddress = commerceAddresses.get(0);

			defaultBillingAddressId = commerceAddress.getCommerceAddressId();
		}

		return defaultBillingAddressId;
	}

	private final CommerceAddressService _commerceAddressService;
	private final CommerceCart _commerceCart;

}