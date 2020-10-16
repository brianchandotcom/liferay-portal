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

import com.liferay.commerce.checkout.web.internal.display.context.OrderSummaryCheckoutStepDisplayContext;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.discount.exception.CommerceDiscountLimitationTimesException;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderGuestCheckoutException;
import com.liferay.commerce.exception.CommerceOrderPaymentMethodException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.util.CommercePaymentUtils;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.checkout.step.name=" + OrderSummaryCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 150)
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
			String commerceOrderUuid = ParamUtil.getString(
				actionRequest, "commerceOrderUuid");

			_validateCommerceOrder(actionRequest, commerceOrderUuid);

			_checkoutCommerceOrder(
				_portal.getHttpServletRequest(actionRequest));
		}
		catch (Exception exception) {
			Throwable throwable = exception.getCause();

			if (throwable instanceof CommerceDiscountLimitationTimesException ||
				throwable instanceof CommerceOrderBillingAddressException ||
				throwable instanceof CommerceOrderGuestCheckoutException ||
				throwable instanceof CommerceOrderPaymentMethodException ||
				throwable instanceof CommerceOrderShippingAddressException ||
				throwable instanceof CommerceOrderShippingMethodException ||
				throwable instanceof NoSuchDiscountException) {

				SessionErrors.add(actionRequest, throwable.getClass());

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

		OrderSummaryCheckoutStepDisplayContext
			orderSummaryCheckoutStepDisplayContext =
				new OrderSummaryCheckoutStepDisplayContext(
					_commerceChannelLocalService, _commerceOrderHttpHelper,
					_commerceOrderPriceCalculation,
					_commerceOrderValidatorRegistry, _commercePaymentEngine,
					_commerceProductPriceCalculation, _cpInstanceHelper,
					_commerceOptionValueHelper, _percentageFormatter,
					httpServletRequest);

		CommerceOrder commerceOrder =
			orderSummaryCheckoutStepDisplayContext.getCommerceOrder();

		String goToConfirmation = (String)httpServletRequest.getAttribute(
			"goToConfirmation");

		if (!commerceOrder.isOpen()) {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_ORDER_DETAIL_URL,
				_commerceCheckoutStepHelper.getOrderDetailURL(
					httpServletRequest, commerceOrder));

			_jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse, "/error.jsp");
		}
		else if (Validator.isNotNull(goToConfirmation)) {
			while (httpServletResponse instanceof HttpServletResponseWrapper) {
				HttpServletResponseWrapper httpServletResponseWrapper =
					(HttpServletResponseWrapper)httpServletResponse;

				httpServletResponse =
					(HttpServletResponse)
						httpServletResponseWrapper.getResponse();
			}

			goToConfirmation = _portal.escapeRedirect(
				URLCodec.encodeURL(goToConfirmation));

			httpServletResponse.sendRedirect(goToConfirmation);
		}
		else {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
				orderSummaryCheckoutStepDisplayContext);

			_jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse,
				"/checkout_step/order_summary.jsp");
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

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _commerceOrderValidatorRegistry.isValid(
				themeDisplay.getLocale(), commerceOrder);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private void _checkoutCommerceOrder(HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (commerceOrder.isOpen()) {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _portal.getUserId(httpServletRequest));
		}
	}

	private void _validateCommerceOrder(
			ActionRequest actionRequest, String commerceOrderUuid)
		throws Exception {

		long groupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(actionRequest));

		CommerceOrder commerceOrder =
			_commerceOrderService.getCommerceOrderByUuidAndGroupId(
				commerceOrderUuid, groupId);

		if ((commerceOrder.getShippingAddressId() <= 0) &&
			_commerceShippingHelper.isShippable(commerceOrder)) {

			throw new CommerceOrderShippingAddressException();
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		if ((commerceOrder.getBillingAddressId() <= 0) &&
			_commerceCheckoutStepHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					httpServletRequest)) {

			throw new CommerceOrderBillingAddressException();
		}

		if ((commerceOrder.getCommerceShippingMethodId() <= 0) &&
			_commerceCheckoutStepHelper.
				isActiveShippingMethodCommerceCheckoutStep(
					httpServletRequest)) {

			throw new CommerceOrderShippingMethodException();
		}

		String commercePaymentMethodKey =
			commerceOrder.getCommercePaymentMethodKey();

		if (commercePaymentMethodKey.isEmpty() &&
			_commerceCheckoutStepHelper.
				isActivePaymentMethodCommerceCheckoutStep(httpServletRequest)) {

			throw new CommerceOrderPaymentMethodException();
		}

		int subscriptionCommerceOrderItemsCount =
			_commerceOrderItemService.countSubscriptionCommerceOrderItems(
				commerceOrder.getCommerceOrderId());

		BigDecimal subtotal = commerceOrder.getSubtotal();

		if ((subscriptionCommerceOrderItemsCount > 0) &&
			commercePaymentMethodKey.isEmpty() &&
			(subtotal.compareTo(BigDecimal.ZERO) > 0)) {

			throw new CommerceOrderPaymentMethodException();
		}

		if (commerceOrder.isSubscriptionOrder() &&
			!commercePaymentMethodKey.isEmpty()) {

			CommercePaymentMethod commercePaymentMethod =
				_commercePaymentUtils.getCommercePaymentMethod(
					commerceOrder.getCommerceOrderId());

			if (!commercePaymentMethod.isProcessRecurringEnabled()) {
				throw new CommerceOrderPaymentMethodException();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrderSummaryCommerceCheckoutStep.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCheckoutStepHelper _commerceCheckoutStepHelper;

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePaymentUtils _commercePaymentUtils;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PercentageFormatter _percentageFormatter;

	@Reference
	private Portal _portal;

}