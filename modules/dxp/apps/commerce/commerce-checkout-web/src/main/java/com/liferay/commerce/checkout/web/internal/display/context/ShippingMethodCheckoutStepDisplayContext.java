/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.util.ShippingMethodCommerceCheckoutStep;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShippingOptionLabelComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
			_commerceOrder.getSiteGroupId(), true);
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

		CommerceContext commerceContext =
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, _commerceOrder, themeDisplay.getLocale());

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