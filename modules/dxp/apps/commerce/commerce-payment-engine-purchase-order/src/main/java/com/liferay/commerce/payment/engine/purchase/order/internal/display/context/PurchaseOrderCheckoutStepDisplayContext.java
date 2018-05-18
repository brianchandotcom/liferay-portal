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

package com.liferay.commerce.payment.engine.purchase.order.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.purchase.order.internal.configuration.PurchaseOrderCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.purchase.order.internal.constants.PurchaseOrderCommercePaymentEngineConstants;
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
public class PurchaseOrderCheckoutStepDisplayContext {

	public PurchaseOrderCheckoutStepDisplayContext(
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest) {

		_commerceOrderService = commerceOrderService;
		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		long commerceOrderId = ParamUtil.getLong(
			_httpServletRequest, "commerceOrderId");

		_commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		return _commerceOrder;
	}

	public String getMessage() throws PortalException {
		if (_message != null) {
			return _message;
		}

		CommerceOrder commerceOrder = getCommerceOrder();

		PurchaseOrderCommercePaymentEngineGroupServiceConfiguration
			purchaseOrderCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					PurchaseOrderCommercePaymentEngineGroupServiceConfiguration.
						class,
					new GroupServiceSettingsLocator(
						commerceOrder.getGroupId(),
						PurchaseOrderCommercePaymentEngineConstants.
							SERVICE_NAME));

		LocalizedValuesMap localizedValuesMap =
			purchaseOrderCommercePaymentEngineGroupServiceConfiguration.
				message();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_message = localizedValuesMap.get(themeDisplay.getLocale());

		return _message;
	}

	private CommerceOrder _commerceOrder;
	private final CommerceOrderService _commerceOrderService;
	private final ConfigurationProvider _configurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private String _message;

}