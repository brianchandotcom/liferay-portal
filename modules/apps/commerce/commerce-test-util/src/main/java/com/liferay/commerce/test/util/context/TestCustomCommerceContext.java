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
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alec Sloan
 */
public class TestCustomCommerceContext extends BaseCommerceContext {

	public TestCustomCommerceContext(
		long companyId, long commerceChannelGroupId, long orderId,
		long commerceAccountId,
		AccountEntryLocalService accountEntryLocalService,
		AccountGroupLocalService accountGroupLocalService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider) {

		super(
			companyId, commerceChannelGroupId, orderId, commerceAccountId,
			accountEntryLocalService, accountGroupLocalService,
			commerceChannelAccountEntryRelLocalService,
			commerceChannelLocalService, commerceCurrencyLocalService,
			commerceOrderService, configurationProvider);

		_companyId = companyId;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
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
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final long _companyId;

}