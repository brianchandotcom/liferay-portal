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
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStepServicesTracker;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
public class CheckoutDisplayContext {

	public CheckoutDisplayContext(
			CommerceCheckoutStepServicesTracker
				commerceCheckoutStepServicesTracker,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Portal portal)
		throws Exception {

		_commerceCheckoutStepServicesTracker =
			commerceCheckoutStepServicesTracker;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = portal.getHttpServletRequest(
			_liferayPortletRequest);
		_httpServletResponse = portal.getHttpServletResponse(
			_liferayPortletResponse);

		_commerceOrder = (CommerceOrder)_httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);

		String checkoutStepName = ParamUtil.getString(
			liferayPortletRequest, "checkoutStepName");

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getCommerceCheckoutStep(
				checkoutStepName);

		if (commerceCheckoutStep == null) {
			List<CommerceCheckoutStep> commerceCheckoutSteps =
				_commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
					_httpServletRequest, _httpServletResponse);

			commerceCheckoutStep = commerceCheckoutSteps.get(0);
		}

		_commerceCheckoutStep = commerceCheckoutStep;
	}

	public List<CommerceCheckoutStep> getCommerceCheckoutSteps()
		throws Exception {

		return _commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
			_httpServletRequest, _httpServletResponse);
	}

	public long getCommerceOrderId() {
		return _commerceOrder.getCommerceOrderId();
	}

	public String getCurrentCheckoutStepName() {
		return _commerceCheckoutStep.getName();
	}

	public String getPreviousCheckoutStepName() throws Exception {
		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.
				getPreviousCommerceCheckoutStep(
					_commerceCheckoutStep.getName(), _httpServletRequest,
					_httpServletResponse);

		if ((commerceCheckoutStep == null) ||
			(_commerceCheckoutStep.isOrder() &&
			 !commerceCheckoutStep.isOrder())) {

			return null;
		}

		return commerceCheckoutStep.getName();
	}

	public String getRedirect() throws Exception {
		PortletURL portletURL = null;

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getNextCommerceCheckoutStep(
				_commerceCheckoutStep.getName(), _httpServletRequest,
				_httpServletResponse);

		if (commerceCheckoutStep == null) {
			portletURL = PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse);
		}
		else {
			portletURL = _liferayPortletResponse.createRenderURL();

			portletURL.setParameter(
				"commerceOrderId", String.valueOf(getCommerceOrderId()));
			portletURL.setParameter(
				"checkoutStepName", commerceCheckoutStep.getName());
		}

		return portletURL.toString();
	}

	public boolean isEmptyCommerceOrder() {
		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean isSennaDisabled() {
		return _commerceCheckoutStep.isSennaDisabled();
	}

	public void renderCurrentCheckoutStep() throws Exception {
		_commerceCheckoutStep.render(_httpServletRequest, _httpServletResponse);
	}

	public boolean showControls() {
		return _commerceCheckoutStep.showControls(
			_httpServletRequest, _httpServletResponse);
	}

	private final CommerceCheckoutStep _commerceCheckoutStep;
	private final CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;
	private final CommerceOrder _commerceOrder;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}