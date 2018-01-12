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

package com.liferay.commerce.payment.engine.money.order.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.money.order.internal.configuration.MoneyOrderCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.money.order.internal.constants.MoneyOrderCommercePaymentEngineConstants;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class MoneyOrderCheckoutStepDisplayContext {

	public MoneyOrderCheckoutStepDisplayContext(
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest) {

		_commerceOrderService = commerceOrderService;
		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
	}

	public String getMessage() throws PortalException {
		if (_message != null) {
			return _message;
		}

		CommerceOrder commerceOrder = getCommerceOrder();

		MoneyOrderCommercePaymentEngineGroupServiceConfiguration
			moneyOrderCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					MoneyOrderCommercePaymentEngineGroupServiceConfiguration.
						class,
					new GroupServiceSettingsLocator(
						commerceOrder.getGroupId(),
						MoneyOrderCommercePaymentEngineConstants.SERVICE_NAME));

		LocalizedValuesMap localizedValuesMap =
			moneyOrderCommercePaymentEngineGroupServiceConfiguration.message();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_message = localizedValuesMap.get(themeDisplay.getLocale());

		return _message;
	}

	protected CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		long commerceOrderId = ParamUtil.getLong(
			_httpServletRequest, "commerceOrderId");

		_commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		return _commerceOrder;
	}

	private CommerceOrder _commerceOrder;
	private final CommerceOrderService _commerceOrderService;
	private final ConfigurationProvider _configurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private String _message;

}