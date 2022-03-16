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
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStepServicesTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
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
			liferayPortletRequest);
		_httpServletResponse = portal.getHttpServletResponse(
			liferayPortletResponse);

		_commerceOrder = (CommerceOrder)_httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);

		String checkoutStepName = ParamUtil.getString(
			liferayPortletRequest, "checkoutStepName");

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getCommerceCheckoutStep(
				checkoutStepName);

		if ((commerceCheckoutStep == null) && (_commerceOrder != null)) {
			List<CommerceCheckoutStep> commerceCheckoutSteps =
				_commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
					true, _httpServletRequest, _httpServletResponse);

			commerceCheckoutStep = commerceCheckoutSteps.get(0);
		}

		_commerceCheckoutStep = commerceCheckoutStep;
	}

	public List<CommerceCheckoutStep> getCommerceCheckoutSteps()
		throws Exception {

		return _commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
			true, _httpServletRequest, _httpServletResponse);
	}

	public String getCommerceOrderUuid() {
		return _commerceOrder.getUuid();
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

	public boolean hasCommerceChannel() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public boolean isEmptyCommerceOrder() {
		if (_commerceOrder == null) {
			return true;
		}

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

	public void renderCurrentCheckoutStep(PageContext pageContext)
		throws Exception {

		_commerceCheckoutStep.render(
			_httpServletRequest,
			PipingServletResponseFactory.createPipingServletResponse(
				pageContext));
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