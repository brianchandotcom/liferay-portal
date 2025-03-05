/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test.util.context;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.context.BaseCommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.discovery.CPConfigurationListDiscovery;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alec Sloan
 */
public class TestCustomCommerceContext extends BaseCommerceContext {

	public TestCustomCommerceContext(
		AccountEntryLocalService accountEntryLocalService,
		AccountGroupLocalService accountGroupLocalService,
		long commerceAccountId,
		CommerceCatalogLocalService commerceCatalogLocalService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		long commerceChannelGroupId,
		CommerceChannelLocalService commerceChannelLocalService,
		String commerceCurrencyCode,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		long commerceOrderId, CommerceOrderService commerceOrderService,
		long companyId, ConfigurationProvider configurationProvider,
		CPConfigurationListDiscovery cpConfigurationListDiscovery) {

		super(
			accountEntryLocalService, accountGroupLocalService,
			commerceAccountId, commerceCatalogLocalService,
			commerceChannelAccountEntryRelLocalService, commerceChannelGroupId,
			commerceChannelLocalService, commerceCurrencyCode,
			commerceCurrencyLocalService, commerceOrderId, commerceOrderService,
			companyId, configurationProvider, cpConfigurationListDiscovery);

		_commerceCurrencyCode = commerceCurrencyCode;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_companyId = companyId;
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder != null) {
			return commerceOrder.getCommerceCurrency();
		}

		if (!Validator.isBlank(_commerceCurrencyCode)) {
			_commerceCurrency =
				_commerceCurrencyLocalService.fetchCommerceCurrency(
					_companyId, _commerceCurrencyCode);
		}

		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		_commerceCurrency = _commerceCurrencyLocalService.getCommerceCurrency(
			_companyId, "USD");

		if (_commerceCurrency == null) {
			_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
				_companyId, "USD");
		}

		return _commerceCurrency;
	}

	private CommerceCurrency _commerceCurrency;
	private final String _commerceCurrencyCode;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final long _companyId;

}