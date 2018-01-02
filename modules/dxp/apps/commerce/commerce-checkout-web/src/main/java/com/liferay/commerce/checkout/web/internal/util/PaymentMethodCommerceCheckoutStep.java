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

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.display.context.PaymentMethodCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.exception.CommerceCartPaymentMethodException;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"commerce.checkout.step.name=" + PaymentMethodCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=40"
	},
	service = CommerceCheckoutStep.class
)
public class PaymentMethodCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final String NAME = "payment-method";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_commercePaymentMethodService.getCommercePaymentMethodsCount(
				themeDisplay.getScopeGroupId(), true) > 0) {

			return true;
		}

		return false;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCommerceCartPaymentMethod(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceCartPaymentMethodException) {
				SessionErrors.add(actionRequest, e.getClass());

				return;
			}

			throw e;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PaymentMethodCheckoutStepDisplayContext
			paymentMethodCheckoutStepDisplayContext =
				new PaymentMethodCheckoutStepDisplayContext(
					_commerceCartHelper, _commercePaymentMethodService,
					httpServletRequest, httpServletResponse);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			paymentMethodCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/payment_method.jsp");
	}

	protected void updateCommerceCartPaymentMethod(ActionRequest actionRequest)
		throws Exception {

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");

		if (commercePaymentMethodId <= 0) {
			throw new CommerceCartPaymentMethodException();
		}

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		CommerceCart commerceCart = _commerceCartService.getCommerceCart(
			commerceCartId);

		_commerceCartService.updateCommerceCart(
			commerceCart.getCommerceCartId(),
			commerceCart.getBillingAddressId(),
			commerceCart.getShippingAddressId(), commercePaymentMethodId,
			commerceCart.getCommerceShippingMethodId(),
			commerceCart.getShippingOptionName(),
			commerceCart.getShippingPrice());
	}

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

	@Reference
	private JSPRenderer _jspRenderer;

}