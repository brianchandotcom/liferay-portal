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

package com.liferay.commerce.test.util.context;

import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.context.BaseCommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

/**
 * @author Alec Sloan
 */
public class TestCustomCommerceContext extends BaseCommerceContext {

	public TestCustomCommerceContext(
		long companyId, long commerceChannelGroupId, long orderId,
		long commerceAccountId, CommerceAccountHelper commerceAccountHelper,
		CommerceAccountLocalService commerceAccountLocalService,
		CommerceAccountService commerceAccountService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider) {

		super(
			companyId, commerceChannelGroupId, orderId, commerceAccountId,
			commerceAccountHelper, commerceAccountLocalService,
			commerceAccountService, commerceChannelLocalService,
			commerceCurrencyLocalService, commerceOrderService,
			configurationProvider);

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