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

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseAddressCheckoutStepDisplayContext {

	public BaseAddressCheckoutStepDisplayContext(
			CommerceAddressService commerceAddressService,
			CommerceCartHelper commerceCartHelper,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commerceAddressService = commerceAddressService;

		_commerceCart = commerceCartHelper.getCurrentCommerceCart(
			httpServletRequest, httpServletResponse,
			CommerceCartConstants.TYPE_CART);
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		return _commerceAddressService.getCommerceAddresses(
			_commerceCart.getGroupId(), _commerceCart.getUserId());
	}

	public CommerceCart getCommerceCart() {
		return _commerceCart;
	}

	public abstract String getCommerceCountrySelectionColumnName();

	public abstract String getCommerceCountrySelectionMethodName();

	public abstract long getDefaultCommerceAddressId() throws PortalException;

	public abstract String getParamName();

	public abstract String getTitle();

	private final CommerceAddressService _commerceAddressService;
	private final CommerceCart _commerceCart;

}