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

package com.liferay.commerce.payment.engine.money.order.internal.util;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.payment.engine.money.order.internal.MoneyOrderCommercePaymentEngine;
import com.liferay.commerce.payment.engine.money.order.internal.display.context.MoneyOrderCheckoutStepDisplayContext;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;
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
		"commerce.checkout.step.name=" + MoneyOrderCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 1)
	},
	service = CommerceCheckoutStep.class
)
public class MoneyOrderCommerceCheckoutStep extends BaseCommerceCheckoutStep {

	public static final String NAME = "money-order";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long commercePaymentMethodId = 0;

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(commerceOrderId);

			commercePaymentMethodId =
				commerceOrder.getCommercePaymentMethodId();
		}
		else {
			CommerceCart commerceCart =
				_commerceCartHelper.getCurrentCommerceCart(
					httpServletRequest, httpServletResponse);

			commercePaymentMethodId = commerceCart.getCommercePaymentMethodId();
		}

		if (commercePaymentMethodId <= 0) {
			return false;
		}

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodService.getCommercePaymentMethod(
				commercePaymentMethodId);

		if (MoneyOrderCommercePaymentEngine.KEY.equals(
				commercePaymentMethod.getEngineKey())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOrder() {
		return true;
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

		MoneyOrderCheckoutStepDisplayContext
			moneyOrderCheckoutStepDisplayContext =
				new MoneyOrderCheckoutStepDisplayContext(
					_commerceOrderService, _configurationProvider,
					httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			moneyOrderCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/checkout_step/money_order.jsp");
	}

	@Reference
	private CommerceCartHelper _commerceCartHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.money.order)"
	)
	private ServletContext _servletContext;

}