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
import com.liferay.commerce.checkout.web.internal.display.context.OrderSummaryCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderPaymentMethodException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.name=" + OrderSummaryCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 100)
	},
	service = CommerceCheckoutStep.class
)
public class OrderSummaryCommerceCheckoutStep extends BaseCommerceCheckoutStep {

	public static final String NAME = "order-summary";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isSennaDisabled() {
		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			startPayment(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof CommerceOrderBillingAddressException ||
				e instanceof CommerceOrderPaymentMethodException ||
				e instanceof CommerceOrderShippingAddressException ||
				e instanceof CommerceOrderShippingMethodException) {

				SessionErrors.add(actionRequest, e.getClass());

				return;
			}

			_log.error(e, e);

			throw e;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		OrderSummaryCheckoutStepDisplayContext
			orderSummaryCheckoutStepDisplayContext =
				new OrderSummaryCheckoutStepDisplayContext(
					_commerceOrderHttpHelper, _commerceOrderValidatorRegistry,
					_commercePriceCalculation, _cpInstanceHelper,
					httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			orderSummaryCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/order_summary.jsp");
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			CommerceOrder commerceOrder =
				(CommerceOrder)httpServletRequest.getAttribute(
					CommerceCheckoutWebKeys.COMMERCE_ORDER);

			return _commerceOrderValidatorRegistry.isValid(commerceOrder);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return false;
		}
	}

	protected void startPayment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrder commerceOrder =
			_commerceOrderService.checkoutCommerceOrder(
				commerceOrderId, commerceContext, serviceContext);

		_actionHelper.startPayment(
			commerceOrder.getCommerceOrderId(), actionRequest, actionResponse,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrderSummaryCommerceCheckoutStep.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePriceCalculation _commercePriceCalculation;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private Http _http;

	@Reference
	private JSPRenderer _jspRenderer;

}