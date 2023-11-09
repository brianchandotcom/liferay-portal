/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.payment.constants.CommercePaymentIntegrationConstants;
import com.liferay.commerce.payment.constants.CommercePaymentWebKeys;
import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.integration.CommercePaymentIntegrationRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.payment.web.internal.constants.FunctionCommercePaymentIntegrationScreenNavigationConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class
	FunctionCommercePaymentIntegrationConfigurationScreenNavigationEntry
		implements ScreenNavigationEntry<CommercePaymentMethodGroupRel> {

	@Override
	public String getCategoryKey() {
		return FunctionCommercePaymentIntegrationScreenNavigationConstants.
			CATEGORY_KEY_FUNCTION_COMMERCE_PAYMENT_INTEGRATION_CONFIGURATION;
	}

	@Override
	public String getEntryKey() {
		return "function.commerce.payment.integration.configuration";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			FunctionCommercePaymentIntegrationScreenNavigationConstants.
				CATEGORY_KEY_FUNCTION_COMMERCE_PAYMENT_INTEGRATION_CONFIGURATION);
	}

	@Override
	public String getScreenNavigationKey() {
		return FunctionCommercePaymentIntegrationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_FUNCTION_COMMERCE_PAYMENT_INTEGRATION;
	}

	@Override
	public boolean isVisible(
		User user,
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		if (commercePaymentMethodGroupRel == null) {
			return false;
		}

		_paymentIntegrationKey =
			commercePaymentMethodGroupRel.getPaymentIntegrationKey();

		CommercePaymentIntegration commercePaymentIntegration =
			_commercePaymentIntegrationRegistry.getCommercePaymentIntegration(
				_paymentIntegrationKey);

		if (commercePaymentIntegration == null) {
			return false;
		}

		return ListUtil.fromArray(
			CommercePaymentIntegrationConstants.TYPES_FUNCTION
		).contains(
			commercePaymentIntegration.getPaymentIntegrationType()
		);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					fetchCommercePaymentMethodGroupRel(
						commerceChannel.getGroupId(), _paymentIntegrationKey);

			if (commercePaymentMethodGroupRel != null) {
				httpServletRequest.setAttribute(
					"commercePaymentIntegrationKey",
					commercePaymentMethodGroupRel.getPaymentIntegrationKey());

				UnicodeProperties typeSettingsUnicodeProperties =
					commercePaymentMethodGroupRel.
						getTypeSettingsUnicodeProperties();

				if (typeSettingsUnicodeProperties.isEmpty()) {
					CommercePaymentIntegration commercePaymentIntegration =
						_commercePaymentIntegrationRegistry.
							getCommercePaymentIntegration(
								_paymentIntegrationKey);

					httpServletRequest.setAttribute(
						CommercePaymentWebKeys.
							IS_DEFAULT_PAYMENT_INTEGRATION_TYPE_SETTINGS,
						Boolean.TRUE);
					httpServletRequest.setAttribute(
						CommercePaymentWebKeys.
							PAYMENT_INTEGRATION_TYPE_SETTINGS,
						commercePaymentIntegration.
							getPaymentIntegrationTypeSettings());
				}
				else {
					httpServletRequest.setAttribute(
						CommercePaymentWebKeys.
							IS_DEFAULT_PAYMENT_INTEGRATION_TYPE_SETTINGS,
						Boolean.FALSE);
					httpServletRequest.setAttribute(
						CommercePaymentWebKeys.
							PAYMENT_INTEGRATION_TYPE_SETTINGS,
						typeSettingsUnicodeProperties);
				}
			}
			else {
				CommercePaymentIntegration commercePaymentIntegration =
					_commercePaymentIntegrationRegistry.
						getCommercePaymentIntegration(_paymentIntegrationKey);

				httpServletRequest.setAttribute(
					"commercePaymentIntegrationKey",
					commercePaymentIntegration.getKey());

				httpServletRequest.setAttribute("isDefaultValue", Boolean.TRUE);
				httpServletRequest.setAttribute(
					"paymentIntegrationTypeSettings",
					commercePaymentIntegration.
						getPaymentIntegrationTypeSettings());
			}
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/commerce_payment_integration/configuration.jsp");
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePaymentIntegrationRegistry
		_commercePaymentIntegrationRegistry;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	private String _paymentIntegrationKey;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.web)"
	)
	private ServletContext _servletContext;

}