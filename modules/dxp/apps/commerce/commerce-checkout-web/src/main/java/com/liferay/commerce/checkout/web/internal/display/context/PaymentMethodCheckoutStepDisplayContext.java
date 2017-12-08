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

import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.commerce.util.comparator.CommercePaymentMethodNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class PaymentMethodCheckoutStepDisplayContext {

	public PaymentMethodCheckoutStepDisplayContext(
			CommerceCartHelper commerceCartHelper,
			CommercePaymentMethodService commercePaymentMethodService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commercePaymentMethodService = commercePaymentMethodService;
		_httpServletRequest = httpServletRequest;

		_commerceCart = commerceCartHelper.getCurrentCommerceCart(
			httpServletRequest, httpServletResponse,
			CommerceCartConstants.TYPE_CART);
	}

	public CommerceCart getCommerceCart() {
		return _commerceCart;
	}

	public List<CommercePaymentMethod> getCommercePaymentMethods() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentMethodService.getCommercePaymentMethods(
				_commerceCart.getGroupId(), true);

		return ListUtil.sort(
			commercePaymentMethods,
			new CommercePaymentMethodNameComparator(themeDisplay.getLocale()));
	}

	private final CommerceCart _commerceCart;
	private final CommercePaymentMethodService _commercePaymentMethodService;
	private final HttpServletRequest _httpServletRequest;

}