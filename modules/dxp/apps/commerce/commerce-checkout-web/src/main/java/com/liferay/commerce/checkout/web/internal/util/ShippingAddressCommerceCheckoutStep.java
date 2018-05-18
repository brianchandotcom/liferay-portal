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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.checkout.web.internal.display.context.BaseAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.internal.display.context.ShippingAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

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
			CommerceOrder commerceOrder, long commerceAddressId)
		throws Exception {

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