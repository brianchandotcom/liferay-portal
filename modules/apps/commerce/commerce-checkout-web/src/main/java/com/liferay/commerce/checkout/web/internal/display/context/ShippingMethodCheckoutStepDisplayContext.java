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

import com.liferay.commerce.checkout.web.internal.util.ShippingMethodCommerceCheckoutStep;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShippingOptionLabelComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class ShippingMethodCheckoutStepDisplayContext {

	public ShippingMethodCheckoutStepDisplayContext(
		CommercePriceFormatter commercePriceFormatter,
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		CommerceShippingMethodLocalService commerceShippingMethodLocalService,
		CommerceShippingFixedOptionLocalService
			commerceShippingFixedOptionLocalService,
		HttpServletRequest httpServletRequest) {

		_commercePriceFormatter = commercePriceFormatter;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_commerceShippingMethodLocalService =
			commerceShippingMethodLocalService;
		_commerceShippingFixedOptionLocalService =
			commerceShippingFixedOptionLocalService;
		_httpServletRequest = httpServletRequest;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public List<CommerceShippingMethod> getCommerceShippingMethods()
		throws PortalException {

		CommerceAddress shippingAddress = _commerceOrder.getShippingAddress();

		return _commerceShippingMethodLocalService.getCommerceShippingMethods(
			_commerceOrder.getGroupId(), shippingAddress.getCountryId(), true);
	}

	public String getCommerceShippingOptionKey(
		long commerceShippingMethodId, String shippingOptionName) {

		char separator =
			ShippingMethodCommerceCheckoutStep.
				COMMERCE_SHIPPING_OPTION_KEY_SEPARATOR;

		return String.valueOf(commerceShippingMethodId) + separator +
			shippingOptionName;
	}

	public String getCommerceShippingOptionLabel(
			CommerceShippingOption commerceShippingOption)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return StringBundler.concat(
			commerceShippingOption.getLabel(), " (+",
			_commercePriceFormatter.format(
				_commerceOrder.getCommerceCurrency(),
				commerceShippingOption.getAmount(), themeDisplay.getLocale()),
			CharPool.CLOSE_PARENTHESIS);
	}

	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceShippingMethod commerceShippingMethod)
		throws CommerceShippingEngineException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				_getCommerceContext(), _commerceOrder,
				themeDisplay.getLocale());

		return ListUtil.sort(
			commerceShippingOptions,
			new CommerceShippingOptionLabelComparator());
	}

	public List<CommerceShippingFixedOption>
			getFilteredCommerceShippingFixedOptions()
		throws PortalException {

		List<CommerceShippingFixedOption> filteredCommerceShippingFixedOptions =
			new ArrayList<>();

		CommerceOrder commerceOrder = getCommerceOrder();

		for (CommerceShippingMethod commerceShippingMethod :
				getCommerceShippingMethods()) {

			List<CommerceShippingFixedOption> commerceShippingFixedOptions =
				_commerceShippingFixedOptionLocalService.
					getCommerceOrderTypeCommerceShippingFixedOptions(
						commerceOrder.getCompanyId(),
						commerceOrder.getCommerceOrderTypeId(),
						commerceShippingMethod.getCommerceShippingMethodId());

			filteredCommerceShippingFixedOptions.addAll(
				commerceShippingFixedOptions);
		}

		return filteredCommerceShippingFixedOptions;
	}

	public List<CommerceShippingOption> getFilteredCommerceShippingOptions(
			CommerceShippingMethod commerceShippingMethod)
		throws PortalException {

		List<CommerceShippingOption> filteredCommerceShippingOptions =
			new ArrayList<>();

		List<CommerceShippingOption> commerceShippingOptions =
			getCommerceShippingOptions(commerceShippingMethod);

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				getFilteredCommerceShippingFixedOptions()) {

			for (CommerceShippingOption commerceShippingOption :
					commerceShippingOptions) {

				String key = commerceShippingFixedOption.getKey();

				if (key.equals(commerceShippingOption.getName())) {
					filteredCommerceShippingOptions.add(commerceShippingOption);
				}
			}
		}

		return filteredCommerceShippingOptions;
	}

	private CommerceContext _getCommerceContext() {
		return (CommerceContext)_httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
	}

	private final CommerceOrder _commerceOrder;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private final CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;
	private final CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;
	private final HttpServletRequest _httpServletRequest;

}