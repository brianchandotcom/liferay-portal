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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceCartContentMiniDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentMiniDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrderHttpHelper commerceOrderHttpHelper,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommercePriceCalculation commercePriceCalculation,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper)
		throws PortalException {

		super(
			httpServletRequest, commerceOrderItemService,
			commerceOrderValidatorRegistry, commercePriceCalculation,
			cpDefinitionHelper, cpInstanceHelper);

		PortletDisplay portletDisplay =
			commerceCartContentRequestHelper.getPortletDisplay();

		_commerceCartContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentMiniPortletInstanceConfiguration.class);

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
	}

	public String getCommerceCartPortletURL() throws PortalException {
		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				commerceCartContentRequestHelper.getRequest());

		return portletURL.toString();
	}

	public int getCommerceOrderItemsQuantity() throws PortalException {
		return _commerceOrderHttpHelper.getCommerceOrderItemsQuantity(
			commerceCartContentRequestHelper.getRequest());
	}

	public String getDisplayStyle() {
		return
			_commerceCartContentMiniPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceCartContentMiniPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId =
				commerceCartContentRequestHelper.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	private final CommerceCartContentMiniPortletInstanceConfiguration
		_commerceCartContentMiniPortletInstanceConfiguration;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private long _displayStyleGroupId;

}