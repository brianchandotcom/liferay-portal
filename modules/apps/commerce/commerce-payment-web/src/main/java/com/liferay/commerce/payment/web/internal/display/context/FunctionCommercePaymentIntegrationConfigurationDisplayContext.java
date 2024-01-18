/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.display.context;

import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.integration.CommercePaymentIntegrationRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Crescenzo Rega
 */
public class FunctionCommercePaymentIntegrationConfigurationDisplayContext {

	public FunctionCommercePaymentIntegrationConfigurationDisplayContext(
		CommerceChannelService commerceChannelService, long commerceChannelId,
		CommercePaymentIntegrationRegistry commercePaymentIntegrationRegistry,
		CommercePaymentMethodGroupRelService
			commercePaymentMethodGroupRelService,
		String paymentIntegrationKey) {

		_commerceChannelService = commerceChannelService;
		_commerceChannelId = commerceChannelId;
		_commercePaymentIntegrationRegistry =
			commercePaymentIntegrationRegistry;
		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
		_paymentIntegrationKey = paymentIntegrationKey;
	}

	public String getCommercePaymentIntegrationKey() throws PortalException {
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_getCommercePaymentMethodGroupRel();

		if (commercePaymentMethodGroupRel != null) {
			return commercePaymentMethodGroupRel.getPaymentIntegrationKey();
		}

		CommercePaymentIntegration commercePaymentIntegration =
			_getCommercePaymentIntegration();

		return commercePaymentIntegration.getKey();
	}

	public UnicodeProperties getPaymentIntegrationUnicodeProperties()
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_getCommercePaymentMethodGroupRel();

		UnicodeProperties paymentIntegrationUnicodeProperties =
			_commercePaymentMethodGroupRel.getTypeSettingsUnicodeProperties();

		if ((commercePaymentMethodGroupRel != null) &&
			paymentIntegrationUnicodeProperties.isEmpty()) {

			CommercePaymentIntegration commercePaymentIntegration =
				_getCommercePaymentIntegration();

			return commercePaymentIntegration.
				getPaymentIntegrationTypeSettings();
		}

		return paymentIntegrationUnicodeProperties;
	}

	public boolean isDefaultPaymentIntegrationUnicodeProperties()
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_getCommercePaymentMethodGroupRel();

		if (commercePaymentMethodGroupRel != null) {
			UnicodeProperties paymentIntegrationUnicodeProperties =
				_commercePaymentMethodGroupRel.
					getTypeSettingsUnicodeProperties();

			return paymentIntegrationUnicodeProperties.isEmpty();
		}

		return true;
	}

	private CommercePaymentIntegration _getCommercePaymentIntegration() {
		if (_commercePaymentIntegration != null) {
			return _commercePaymentIntegration;
		}

		_commercePaymentIntegration =
			_commercePaymentIntegrationRegistry.getCommercePaymentIntegration(
				_paymentIntegrationKey);

		return _commercePaymentIntegration;
	}

	private CommercePaymentMethodGroupRel _getCommercePaymentMethodGroupRel()
		throws PortalException {

		if (_commercePaymentMethodGroupRel != null) {
			return _commercePaymentMethodGroupRel;
		}

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(_commerceChannelId);

		_commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelService.
				fetchCommercePaymentMethodGroupRel(
					commerceChannel.getGroupId(), _paymentIntegrationKey);

		return _commercePaymentMethodGroupRel;
	}

	private final long _commerceChannelId;
	private final CommerceChannelService _commerceChannelService;
	private CommercePaymentIntegration _commercePaymentIntegration;
	private final CommercePaymentIntegrationRegistry
		_commercePaymentIntegrationRegistry;
	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;
	private final CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;
	private final String _paymentIntegrationKey;

}