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

import com.liferay.commerce.cart.CommerceOrderValidatorRegistry;
import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentTotalPortletInstanceConfiguration;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.util.CommerceOrderHelper;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentTotalDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentTotalDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			CommerceOrderHelper commerceOrderHelper,
			CommerceCartItemService commerceCartItemService,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommercePriceCalculator commercePriceCalculator,
			CommercePriceFormatter commercePriceFormatter,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper, Portal portal)
		throws ConfigurationException {

		super(
			httpServletRequest, httpServletResponse, commerceOrderHelper,
			commerceCartItemService, commerceOrderValidatorRegistry,
			commercePriceCalculator, commercePriceFormatter, cpDefinitionHelper,
			cpInstanceHelper);

		_portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_commerceCartContentTotalPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentTotalPortletInstanceConfiguration.class);
	}

	public PortletURL getCheckoutPortletURL() throws PortalException {
		long plid = _portal.getPlidFromPortletId(
			commerceCartContentRequestHelper.getScopeGroupId(),
			CommercePortletKeys.COMMERCE_CHECKOUT);

		return PortletURLFactoryUtil.create(
			commerceCartContentRequestHelper.getRequest(),
			CommercePortletKeys.COMMERCE_CHECKOUT, plid,
			PortletRequest.RENDER_PHASE);
	}

	public String getDisplayStyle() {
		return _commerceCartContentTotalPortletInstanceConfiguration.
			displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceCartContentTotalPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId =
				commerceCartContentRequestHelper.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	private final CommerceCartContentTotalPortletInstanceConfiguration
		_commerceCartContentTotalPortletInstanceConfiguration;
	private long _displayStyleGroupId;
	private final Portal _portal;

}