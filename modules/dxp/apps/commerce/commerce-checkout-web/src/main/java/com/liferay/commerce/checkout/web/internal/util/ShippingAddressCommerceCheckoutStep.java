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
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.security.permission.resource.CommerceOrderPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

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
			commerceAddressService, httpServletRequest, httpServletResponse);
	}

	@Override
	protected String getParamName() {
		return "shippingAddressId";
	}

	@Override
	protected void updateCommerceOrderAddress(
			User user, CommerceOrder commerceOrder, long commerceAddressId)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		if (!CommerceOrderPermission.contains(
				permissionChecker, commerceOrder,
				CommerceOrderActionKeys.CHECKOUT_OPEN_COMMERCE_ORDERS)) {

			return;
		}

		commerceOrderLocalService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(), commerceAddressId,
			commerceOrder.getCommercePaymentMethodId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingPrice(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus());
	}

}