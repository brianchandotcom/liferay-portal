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

import com.liferay.commerce.checkout.web.internal.display.context.CheckoutStepOrderSummaryDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePriceCalculationHelper;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.name=orderSummary",
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 1)
	},
	service = CommerceCheckoutStep.class
)
public class OrderSummaryCommerceCheckoutStep implements CommerceCheckoutStep {

	@Override
	public String getLabel(Locale locale) {
		return "ORDER_SUMMARY_TO_CHANGE";
	}

	@Override
	public String getName() {
		return "orderSummary";
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return true;
	}

	@Override
	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return true;
	}

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		CommerceOrder commerceOrder =
			_commerceOrderService.addCommerceOrderFromCart(
				commerceCartId, serviceContext);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, CommercePortletKeys.COMMERCE_CHECKOUT,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"commerceOrderId",
			String.valueOf(commerceOrder.getCommerceOrderId()));
		portletURL.setParameter("checkoutStepName", "orderConfirmation");

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CheckoutStepOrderSummaryDisplayContext
			checkoutStepOrderSummaryDisplayContext =
				new CheckoutStepOrderSummaryDisplayContext(
					_commerceAddressService, _commerceCartService,
					_commercePriceCalculationHelper, _commercePriceFormatter,
					_cpInstanceHelper, httpServletRequest);

		httpServletRequest.setAttribute(
			"CommerceCheckoutStepDisplayContext",
			checkoutStepOrderSummaryDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/order_summary.jsp");
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return true;
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceCalculationHelper _commercePriceCalculationHelper;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSPRenderer _jspRenderer;

}