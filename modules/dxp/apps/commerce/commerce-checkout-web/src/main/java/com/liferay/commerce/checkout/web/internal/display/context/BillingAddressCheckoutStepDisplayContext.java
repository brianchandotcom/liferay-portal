/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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