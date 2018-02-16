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
import com.liferay.commerce.checkout.web.internal.display.context.BillingAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;

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
		"commerce.checkout.step.name=" + BillingAddressCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=10"
	},
	service = CommerceCheckoutStep.class
)
public class BillingAddressCommerceCheckoutStep
	extends BaseAddressCommerceCheckoutStep {

	public static final String NAME = "billing-address";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		if (organization != null) {
			return false;
		}

		return true;
	}

	@Override
	protected BaseAddressCheckoutStepDisplayContext
			getBaseAddressCheckoutStepDisplayContext(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
		throws PortalException {

		return new BillingAddressCheckoutStepDisplayContext(
			commerceAddressService, _commerceOrderHelper, httpServletRequest,
			httpServletResponse);
	}

	@Override
	protected String getParamName() {
		return "billingAddressId";
	}

	@Override
	protected void updateCommerceOrderAddress(
			CommerceOrder commerceOrder, long commerceAddressId)
		throws PortalException {

		commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(), commerceAddressId,
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingPrice(), commerceOrder.getTotal(),
			commerceOrder.getPaymentStatus(), commerceOrder.getOrderStatus());
	}

	@Reference
	private CommerceOrderHelper _commerceOrderHelper;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

}