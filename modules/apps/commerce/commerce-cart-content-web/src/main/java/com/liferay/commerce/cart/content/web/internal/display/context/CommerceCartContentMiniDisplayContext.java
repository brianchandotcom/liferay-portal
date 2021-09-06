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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceCartContentMiniDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentMiniDisplayContext(
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceOrderHttpHelper commerceOrderHttpHelper,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderPriceCalculation commerceOrderPriceCalculation,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommerceProductPriceCalculation commerceProductPriceCalculation,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper,
			ModelResourcePermission<CommerceOrder>
				commerceOrderModelResourcePermission,
			PortletResourcePermission commerceProductPortletResourcePermission,
			PercentageFormatter percentageFormatter,
			HttpServletRequest httpServletRequest, Portal portal)
		throws PortalException {

		super(
			commerceChannelLocalService, commerceOrderItemService,
			commerceOrderModelResourcePermission, commerceOrderPriceCalculation,
			commerceOrderValidatorRegistry,
			commerceProductPortletResourcePermission,
			commerceProductPriceCalculation, cpDefinitionHelper,
			cpInstanceHelper, httpServletRequest, portal);

		PortletDisplay portletDisplay =
			commerceCartContentRequestHelper.getPortletDisplay();

		_commerceCartContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentMiniPortletInstanceConfiguration.class);

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_percentageFormatter = percentageFormatter;
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

	@Override
	public String getDisplayStyle() {
		return _commerceCartContentMiniPortletInstanceConfiguration.
			displayStyle();
	}

	@Override
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

	public String getLocalizedPercentage(BigDecimal percentage, Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		return _percentageFormatter.getLocalizedPercentage(
			locale, commerceCurrency.getMaxFractionDigits(),
			commerceCurrency.getMinFractionDigits(), percentage);
	}

	private final CommerceCartContentMiniPortletInstanceConfiguration
		_commerceCartContentMiniPortletInstanceConfiguration;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private long _displayStyleGroupId;
	private final PercentageFormatter _percentageFormatter;

}