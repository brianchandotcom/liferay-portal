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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceCheckoutStepHelper.class
)
public class CommerceCheckoutStepHelper {

	public String getOrderDetailURL(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				httpServletRequest, commerceOrder);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		return portletURL.toString();
	}

	public boolean isActiveBillingAddressCommerceCheckoutStep(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();
		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
		CommerceAddress billingAddress = commerceOrder.getBillingAddress();

		if ((commerceAccount != null) &&
			(commerceAccount.getDefaultBillingAddressId() ==
				commerceAccount.getDefaultShippingAddressId()) &&
			(billingAddress != null) && (shippingAddress != null) &&
			(shippingAddress.getCommerceAddressId() ==
				billingAddress.getCommerceAddressId())) {

			return false;
		}

		return true;
	}

	public boolean isActivePaymentMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		long commercePaymentMethodGroupRelsCount =
			_commercePaymentEngine.getCommercePaymentMethodGroupRelsCount(
				commerceOrder.getGroupId());

		if (commercePaymentMethodGroupRelsCount < 1) {
			return false;
		}

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrderPrice commerceOrderPrice =
			_commerceOrderPriceCalculation.getCommerceOrderPrice(
				commerceOrder, commerceContext);

		CommerceMoney orderPriceTotalCommerceMoney =
			commerceOrderPrice.getTotal();

		if (CommerceBigDecimalUtil.isZero(
				orderPriceTotalCommerceMoney.getPrice())) {

			return false;
		}

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentEngine.getEnabledCommercePaymentMethodsForOrder(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId());

		if (commercePaymentMethods.isEmpty()) {
			return false;
		}

		if (commercePaymentMethods.size() == 1) {
			CommercePaymentMethod commercePaymentMethod =
				commercePaymentMethods.get(0);

			_updateCommerceOrder(
				httpServletRequest, commerceOrder,
				commercePaymentMethod.getKey());

			return false;
		}

		return true;
	}

	public boolean isActiveShippingMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (!_commerceShippingHelper.isShippable(commerceOrder) ||
			_commerceShippingHelper.isFreeShipping(commerceOrder)) {

			return false;
		}

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				commerceOrder.getGroupId(), true);

		if (commerceShippingMethods.isEmpty()) {
			return false;
		}

		if (commerceShippingMethods.size() == 1) {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceShippingMethod commerceShippingMethod =
				commerceShippingMethods.get(0);

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getCommerceShippingOptions(
					commerceContext, commerceOrder,
					_portal.getLocale(httpServletRequest));

			if (commerceShippingOptions.size() == 1) {
				_updateCommerceOrder(
					commerceContext, commerceOrder,
					commerceShippingMethod.getCommerceShippingMethodId(),
					commerceShippingOptions.get(0), httpServletRequest);

				return false;
			}
		}

		return true;
	}

	private void _updateCommerceOrder(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			long commerceShippingMethodId,
			CommerceShippingOption commerceShippingOption,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if (commerceAddress == null) {
			return;
		}

		commerceOrder = _commerceOrderService.updateCommerceShippingMethod(
			commerceOrder.getCommerceOrderId(), commerceShippingMethodId,
			commerceShippingOption.getName(), commerceContext,
			_portal.getLocale(httpServletRequest));

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	private void _updateCommerceOrder(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder,
			String commercePaymentMethodKey)
		throws PortalException {

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if (commerceAddress == null) {
			return;
		}

		if (commercePaymentMethodKey.equals(
				commerceOrder.getCommercePaymentMethodKey())) {

			return;
		}

		commerceOrder = _commerceOrderService.updateCommercePaymentMethodKey(
			commerceOrder.getCommerceOrderId(), commercePaymentMethodKey);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private Portal _portal;

}