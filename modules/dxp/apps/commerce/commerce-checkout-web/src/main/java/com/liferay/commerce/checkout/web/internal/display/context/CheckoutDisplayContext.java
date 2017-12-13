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

import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStepServicesTracker;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
public class CheckoutDisplayContext {

	public CheckoutDisplayContext(
			CommerceCartHelper commerceCartHelper,
			CommerceCheckoutStepServicesTracker
				commerceCheckoutStepServicesTracker,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_commerceCartHelper = commerceCartHelper;
		_commerceCheckoutStepServicesTracker =
			commerceCheckoutStepServicesTracker;

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		_commerceCart = _commerceCartHelper.getCurrentCommerceCart(
			httpServletRequest, httpServletResponse,
			CommerceCartConstants.TYPE_CART);

		String checkoutStepName = ParamUtil.getString(
			httpServletRequest, "checkoutStepName");

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getCommerceCheckoutStep(
				checkoutStepName);

		if (commerceCheckoutStep == null) {
			List<CommerceCheckoutStep> commerceCheckoutSteps =
				_commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
					_httpServletRequest, _httpServletResponse);

			commerceCheckoutStep = commerceCheckoutSteps.get(0);
		}

		_currentCheckoutStep = commerceCheckoutStep;
	}

	public long getCommerceCartId() {
		if (_commerceCart == null) {
			return 0;
		}

		return _commerceCart.getCommerceCartId();
	}

	public List<CommerceCheckoutStep> getCommerceCheckoutSteps()
		throws Exception {

		return _commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
			_httpServletRequest, _httpServletResponse);
	}

	public String getCurrentCheckoutStepName() {
		return _currentCheckoutStep.getName();
	}

	public String getNextCheckoutStepName() throws Exception {
		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getNextCommerceCheckoutStep(
				_currentCheckoutStep.getName(), _httpServletRequest,
				_httpServletResponse);

		if (commerceCheckoutStep == null) {
			return null;
		}

		return commerceCheckoutStep.getName();
	}

	public String getPreviousCheckoutStepName() throws Exception {
		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.
				getPreviousCommerceCheckoutStep(
					_currentCheckoutStep.getName(), _httpServletRequest,
					_httpServletResponse);

		if (commerceCheckoutStep == null) {
			return null;
		}

		return commerceCheckoutStep.getName();
	}

	public boolean isCurrentCommerceCheckoutStep(
		CommerceCheckoutStep commerceCheckoutStep) {

		if (getCurrentCheckoutStepName().equals(
				commerceCheckoutStep.getName())) {

			return true;
		}

		return false;
	}

	public boolean isSennaDisabled() {
		return _currentCheckoutStep.isSennaDisabled();
	}

	public void renderCurrentCheckoutStep() throws Exception {
		_currentCheckoutStep.render(_httpServletRequest, _httpServletResponse);
	}

	public boolean showControls() {
		return _currentCheckoutStep.showControls(
			_httpServletRequest, _httpServletResponse);
	}

	private final CommerceCart _commerceCart;
	private final CommerceCartHelper _commerceCartHelper;
	private final CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;
	private final CommerceCheckoutStep _currentCheckoutStep;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;

}