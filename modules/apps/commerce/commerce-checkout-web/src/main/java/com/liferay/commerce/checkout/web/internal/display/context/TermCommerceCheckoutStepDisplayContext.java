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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 */
public class TermCommerceCheckoutStepDisplayContext {

	public TermCommerceCheckoutStepDisplayContext(
		CommercePaymentMethodGroupRelLocalService
			commercePaymentMethodGroupRelLocalService,
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		CommerceShippingFixedOptionLocalService
			commerceShippingFixedOptionLocalService,
		CommerceShippingMethodLocalService commerceShippingMethodLocalService,
		CommerceTermEntryLocalService commerceTermEntryLocalService,
		HttpServletRequest httpServletRequest) {

		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_commerceShippingFixedOptionLocalService =
			commerceShippingFixedOptionLocalService;
		_commerceShippingMethodLocalService =
			commerceShippingMethodLocalService;
		_commerceTermEntryLocalService = commerceTermEntryLocalService;
		_httpServletRequest = httpServletRequest;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		return (CommerceOrder)_httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public List<CommerceTermEntry> getDeliveryCommerceTermEntries()
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.getCommerceShippingMethod(
				_commerceOrder.getCommerceShippingMethodId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		CommerceContext commerceContext =
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, _commerceOrder, themeDisplay.getLocale());

		String shippingOptionName = _commerceOrder.getShippingOptionName();

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (shippingOptionName.equals(commerceShippingOption.getName())) {
				CommerceShippingFixedOption commerceShippingFixedOption =
					_commerceShippingFixedOptionLocalService.
						fetchCommerceShippingFixedOption(
							_commerceOrder.getCompanyId(),
							commerceShippingOption.getName());

				if (commerceShippingFixedOption != null) {
					return _commerceTermEntryLocalService.
						getDeliveryCommerceTermEntries(
							_commerceOrder.getCompanyId(),
							_commerceOrder.getCommerceOrderTypeId(),
							commerceShippingFixedOption.
								getCommerceShippingFixedOptionId());
				}
			}
		}

		return Collections.emptyList();
	}

	public List<CommerceTermEntry> getPaymentCommerceTermEntries()
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					_commerceOrder.getGroupId(),
					_commerceOrder.getCommercePaymentMethodKey());

		return _commerceTermEntryLocalService.getPaymentCommerceTermEntries(
			_commerceOrder.getCompanyId(),
			_commerceOrder.getCommerceOrderTypeId(),
			commercePaymentMethodGroupRel.getCommercePaymentMethodGroupRelId());
	}

	private final CommerceOrder _commerceOrder;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private final CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;
	private final CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;
	private final CommerceTermEntryLocalService _commerceTermEntryLocalService;
	private final HttpServletRequest _httpServletRequest;

}