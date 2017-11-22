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

import com.liferay.commerce.cart.CommerceCartValidatorRegistry;
import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.display.context.OrderSummaryCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

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
		"commerce.checkout.step.name=" + OrderSummaryCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 1)
	},
	service = CommerceCheckoutStep.class
)
public class OrderSummaryCommerceCheckoutStep implements CommerceCheckoutStep {

	public static final String NAME = "order-summary";

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, NAME);
	}

	@Override
	public String getName() {
		return NAME;
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

		OrderSummaryCheckoutStepDisplayContext
			orderSummaryCheckoutStepDisplayContext =
				new OrderSummaryCheckoutStepDisplayContext(
					_commerceCartService, _commerceCartValidatorRegistry,
					_commercePriceCalculator, _commercePriceFormatter,
					_cpInstanceHelper, httpServletRequest);

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
			long commerceCartId = ParamUtil.getLong(
				httpServletRequest, "commerceCartId");

			CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
				commerceCartId);

			return _commerceCartValidatorRegistry.isValid(commerceCart);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrderSummaryCommerceCheckoutStep.class);

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private CommerceCartValidatorRegistry _commerceCartValidatorRegistry;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceCalculator _commercePriceCalculator;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSPRenderer _jspRenderer;

}