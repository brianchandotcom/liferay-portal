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

import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.checkout.web.internal.display.context.BillingAddressCheckoutStepDisplayContext;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceAddressZipException;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.checkout.step.name=" + BillingAddressCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=30"
	},
	service = CommerceCheckoutStep.class
)
public class BillingAddressCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

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

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		return _commerceCheckoutStepHttpHelper.
			isActiveBillingAddressCommerceCheckoutStep(
				httpServletRequest, commerceOrder);
	}

	@Override
	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		BillingAddressCheckoutStepDisplayContext
			billingAddressCheckoutStepDisplayContext =
				new BillingAddressCheckoutStepDisplayContext(
					commerceAddressService, httpServletRequest);

		CommerceOrder commerceOrder =
			billingAddressCheckoutStepDisplayContext.getCommerceOrder();

		if ((commerceOrder == null) ||
			(commerceOrder.getBillingAddressId() ==
				commerceOrder.getShippingAddressId())) {

			return false;
		}

		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			AddressCommerceCheckoutStepUtil addressCommerceCheckoutStepUtil =
				new AddressCommerceCheckoutStepUtil(
					commerceAccountLocalService,
					CommerceAddressConstants.ADDRESS_TYPE_BILLING,
					commerceOrderService, commerceAddressService,
					commerceOrderModelResourcePermission);

			addressCommerceCheckoutStepUtil.updateCommerceOrderAddress(
				actionRequest,
				CommerceCheckoutWebKeys.BILLING_ADDRESS_PARAM_NAME);
		}
		catch (Exception exception) {
			if (exception instanceof CommerceAddressCityException ||
				exception instanceof CommerceAddressCountryException ||
				exception instanceof CommerceAddressNameException ||
				exception instanceof CommerceAddressStreetException ||
				exception instanceof CommerceAddressZipException ||
				exception instanceof CommerceOrderBillingAddressException ||
				exception instanceof CommerceOrderShippingAddressException) {

				SessionErrors.add(actionRequest, exception.getClass());

				return;
			}

			throw exception;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		BillingAddressCheckoutStepDisplayContext
			billingAddressCheckoutStepDisplayContext =
				new BillingAddressCheckoutStepDisplayContext(
					commerceAddressService, httpServletRequest);

		CommerceOrder commerceOrder =
			billingAddressCheckoutStepDisplayContext.getCommerceOrder();

		if (!commerceOrder.isOpen()) {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_ORDER_DETAIL_URL,
				_commerceCheckoutStepHttpHelper.getOrderDetailURL(
					httpServletRequest, commerceOrder));

			jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse, "/error.jsp");
		}
		else {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
				billingAddressCheckoutStepDisplayContext);

			jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse,
				"/checkout_step/address.jsp");
		}
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (!commerceOrder.isOpen()) {
			return false;
		}

		return super.showControls(httpServletRequest, httpServletResponse);
	}

	@Reference
	protected CommerceAccountLocalService commerceAccountLocalService;

	@Reference
	protected CommerceAddressService commerceAddressService;

	@Reference
	protected CommerceOrderLocalService commerceOrderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	protected ModelResourcePermission<CommerceOrder>
		commerceOrderModelResourcePermission;

	@Reference
	protected CommerceOrderService commerceOrderService;

	@Reference
	protected JSPRenderer jspRenderer;

	@Reference
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

}