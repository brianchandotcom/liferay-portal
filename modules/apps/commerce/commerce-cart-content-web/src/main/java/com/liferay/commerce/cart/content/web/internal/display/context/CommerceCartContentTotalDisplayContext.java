/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentTotalPortletInstanceConfiguration;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentTotalDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentTotalDisplayContext(
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceOrderHttpHelper commerceOrderHttpHelper,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderPriceCalculation commerceOrderPriceCalculation,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			ConfigurationProvider configurationProvider,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper,
			ModelResourcePermission<CommerceOrder>
				commerceOrderModelResourcePermission,
			PortletResourcePermission commerceProductPortletResourcePermission,
			HttpServletRequest httpServletRequest, Portal portal)
		throws PortalException {

		super(
			commerceChannelLocalService, commerceOrderItemService,
			commerceOrderModelResourcePermission, commerceOrderPriceCalculation,
			commerceOrderValidatorRegistry,
			commerceProductPortletResourcePermission, configurationProvider,
			cpDefinitionHelper, cpInstanceHelper, httpServletRequest, portal);

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_portal = portal;

		_commerceCartContentTotalPortletInstanceConfiguration =
			configurationProvider.getPortletInstanceConfiguration(
				CommerceCartContentTotalPortletInstanceConfiguration.class,
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
	}

	public PortletURL getCheckoutPortletURL() throws PortalException {
		return _commerceOrderHttpHelper.getCommerceCheckoutPortletURL(
			commerceCartContentRequestHelper.getRequest());
	}

	@Override
	public String getDisplayStyle() {
		return _commerceCartContentTotalPortletInstanceConfiguration.
			displayStyle();
	}

	@Override
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
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private long _displayStyleGroupId;
	private final Portal _portal;

}