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
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class BillingAddressCheckoutStepDisplayContext
	extends BaseAddressCheckoutStepDisplayContext {

	public BillingAddressCheckoutStepDisplayContext(
			CommerceAddressService commerceAddressService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		super(commerceAddressService, httpServletRequest, httpServletResponse);
	}

	@Override
	public String getCommerceCountrySelectionColumnName() {
		return "billingAllowed";
	}

	@Override
	public String getCommerceCountrySelectionMethodName() {
		return "get-billing-commerce-countries";
	}

	@Override
	public long getDefaultCommerceAddressId() throws PortalException {
		long defaultCommerceAddressId = 0;

		List<CommerceAddress> commerceAddresses = getCommerceAddresses();

		for (CommerceAddress commerceAddress : commerceAddresses) {
			if (commerceAddress.isDefaultBilling()) {
				defaultCommerceAddressId =
					commerceAddress.getCommerceAddressId();

				break;
			}
		}

		if ((defaultCommerceAddressId == 0) && !commerceAddresses.isEmpty()) {
			CommerceAddress commerceAddress = commerceAddresses.get(0);

			defaultCommerceAddressId = commerceAddress.getCommerceAddressId();
		}

		return defaultCommerceAddressId;
	}

	@Override
	public String getParamName() {
		return "billingAddressId";
	}

	@Override
	public String getTitle() {
		return "billing-address";
	}

}