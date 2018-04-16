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
import com.liferay.commerce.checkout.web.internal.display.context.ShippingMethodCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

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
		"commerce.checkout.step.name=" + ShippingMethodCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=30"
	},
	service = CommerceCheckoutStep.class
)
public class ShippingMethodCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final char COMMERCE_SHIPPING_OPTION_KEY_SEPARATOR =
		CharPool.POUND;

	public static final String NAME = "shipping-method";

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

		if (!_commerceShippingHelper.isShippable(commerceOrder)) {
			return false;
		}

		if (_commerceShippingMethodService.getCommerceShippingMethodsCount(
				commerceOrder.getSiteGroupId(), true) > 0) {

			return true;
		}

		return false;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCommerceOrderShippingMethod(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceOrderShippingMethodException) {
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

		ShippingMethodCheckoutStepDisplayContext
			shippingMethodCheckoutStepDisplayContext =
				new ShippingMethodCheckoutStepDisplayContext(
					_commercePriceFormatter, _commerceShippingEngineRegistry,
					_commerceShippingMethodService, httpServletRequest,
					httpServletResponse);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			shippingMethodCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/shipping_method.jsp");
	}

	protected double getShippingPrice(
			CommerceOrder commerceOrder, long commerceShippingMethodId,
			String shippingOptionName, Locale locale)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		if (!commerceShippingMethod.isActive()) {
			throw new CommerceOrderShippingMethodException(
				"Shipping method " +
					commerceShippingMethod.getCommerceShippingMethodId() +
						" is not active");
		}

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceOrder, locale);

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (shippingOptionName.equals(commerceShippingOption.getName())) {
				return commerceShippingOption.getAmount();
			}
		}

		throw new CommerceOrderShippingMethodException(
			"Unable to get amount of option \"" + shippingOptionName +
				"\" for shipping method " + commerceShippingMethodId);
	}

	protected void updateCommerceOrderShippingMethod(
			ActionRequest actionRequest)
		throws Exception {

		String commerceShippingOptionKey = ParamUtil.getString(
			actionRequest, "commerceShippingOptionKey");

		if (Validator.isNull(commerceShippingOptionKey)) {
			throw new CommerceOrderShippingMethodException();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		int pos = commerceShippingOptionKey.indexOf(
			COMMERCE_SHIPPING_OPTION_KEY_SEPARATOR);

		long commerceShippingMethodId = Long.parseLong(
			commerceShippingOptionKey.substring(0, pos));
		String shippingOptionName = commerceShippingOptionKey.substring(
			pos + 1);

		double shippingPrice = getShippingPrice(
			commerceOrder, commerceShippingMethodId, shippingOptionName,
			themeDisplay.getLocale());

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(),
			commerceShippingMethodId, shippingOptionName,
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			shippingPrice, commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus());
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private JSPRenderer _jspRenderer;

}