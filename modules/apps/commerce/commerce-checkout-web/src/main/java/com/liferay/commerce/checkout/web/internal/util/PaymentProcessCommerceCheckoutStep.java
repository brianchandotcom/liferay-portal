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

import com.liferay.commerce.checkout.web.internal.display.context.PaymentProcessCheckoutStepDisplayContext;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStepServicesTracker;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.checkout.step.name=" + PaymentProcessCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 90)
	},
	service = CommerceCheckoutStep.class
)
public class PaymentProcessCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final String NAME = "payment-process";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return false;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		PaymentProcessCheckoutStepDisplayContext
			paymentProcessCheckoutStepDisplayContext =
				new PaymentProcessCheckoutStepDisplayContext(
					_commerceCheckoutStepServicesTracker, commerceOrder,
					httpServletRequest, _portal);

		// Redirection only works with the original servlet response

		HttpServletResponse originalHttpServletResponse = httpServletResponse;

		while (originalHttpServletResponse instanceof
					HttpServletResponseWrapper) {

			HttpServletResponseWrapper httpServletResponseWrapper =
				(HttpServletResponseWrapper)originalHttpServletResponse;

			originalHttpServletResponse =
				(HttpServletResponse)httpServletResponseWrapper.getResponse();
		}

		String redirect = _portal.escapeRedirect(
			paymentProcessCheckoutStepDisplayContext.getPaymentServletUrl());

		if (Validator.isNotNull(redirect) &&
			!originalHttpServletResponse.isCommitted()) {

			originalHttpServletResponse.sendRedirect(redirect);
		}

		if (originalHttpServletResponse.isCommitted()) {
			return;
		}

		// Backend redirection has failed: fall back to frontend redirect

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			paymentProcessCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/payment_process.jsp");
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return false;
	}

	@Reference
	private CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}