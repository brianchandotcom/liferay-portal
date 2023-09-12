/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentMiniDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentMiniDisplayContext(
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
			PercentageFormatter percentageFormatter,
			HttpServletRequest httpServletRequest, Portal portal)
		throws PortalException {

		super(
			commerceChannelLocalService, commerceOrderItemService,
			commerceOrderModelResourcePermission, commerceOrderPriceCalculation,
			commerceOrderValidatorRegistry,
			commerceProductPortletResourcePermission, configurationProvider,
			cpDefinitionHelper, cpInstanceHelper, httpServletRequest, portal);

		_commerceCartContentMiniPortletInstanceConfiguration =
			configurationProvider.getPortletInstanceConfiguration(
				CommerceCartContentMiniPortletInstanceConfiguration.class,
				commerceCartContentRequestHelper.getThemeDisplay());

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
		BigDecimal quantity =
			_commerceOrderHttpHelper.getCommerceOrderItemsQuantity(
				commerceCartContentRequestHelper.getRequest());

		return quantity.intValue();
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