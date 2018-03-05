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

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.util.ShippingMethodCommerceCheckoutStep;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShippingOptionLabelComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class ShippingMethodCheckoutStepDisplayContext {

	public ShippingMethodCheckoutStepDisplayContext(
			CommercePriceFormatter commercePriceFormatter,
			CommerceShippingEngineRegistry commerceShippingEngineRegistry,
			CommerceShippingMethodService commerceShippingMethodService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commercePriceFormatter = commercePriceFormatter;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_commerceShippingMethodService = commerceShippingMethodService;
		_httpServletRequest = httpServletRequest;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public List<CommerceShippingMethod> getCommerceShippingMethods() {
		return _commerceShippingMethodService.getCommerceShippingMethods(
			_commerceOrder.getGroupId(), true);
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

		StringBundler sb = new StringBundler(4);

		sb.append(commerceShippingOption.getLabel());
		sb.append(" (+");
		sb.append(
			_commercePriceFormatter.format(
				themeDisplay.getScopeGroupId(),
				commerceShippingOption.getAmount()));
		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
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
				_commerceOrder, themeDisplay.getLocale());

		return ListUtil.sort(
			commerceShippingOptions,
			new CommerceShippingOptionLabelComparator());
	}

	private final CommerceOrder _commerceOrder;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private final CommerceShippingMethodService _commerceShippingMethodService;
	private final HttpServletRequest _httpServletRequest;

}