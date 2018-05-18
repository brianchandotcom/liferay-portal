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
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.CommercePaymentMethodService;
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
			CommercePaymentMethodService commercePaymentMethodService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_commercePaymentMethodService = commercePaymentMethodService;
		_httpServletRequest = httpServletRequest;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public List<CommercePaymentMethod> getCommercePaymentMethods()
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceAddress commerceAddress = _commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = _commerceOrder.getShippingAddress();
		}

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentMethodService.getCommercePaymentMethods(
				_commerceOrder.getSiteGroupId(),
				commerceAddress.getCommerceCountryId(), true);

		return ListUtil.sort(
			commercePaymentMethods,
			new CommercePaymentMethodNameComparator(themeDisplay.getLocale()));
	}

	private final CommerceOrder _commerceOrder;
	private final CommercePaymentMethodService _commercePaymentMethodService;
	private final HttpServletRequest _httpServletRequest;

}