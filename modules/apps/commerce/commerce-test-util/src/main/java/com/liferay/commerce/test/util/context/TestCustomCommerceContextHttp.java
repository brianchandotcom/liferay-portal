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

import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.context.BaseCommerceContextHttp;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alec Sloan
 */
public class TestCustomCommerceContextHttp extends BaseCommerceContextHttp {

	public TestCustomCommerceContextHttp(
		HttpServletRequest httpServletRequest,
		CommerceAccountHelper commerceAccountHelper,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		ConfigurationProvider configurationProvider, Portal portal) {

		super(
			httpServletRequest, commerceAccountHelper,
			commerceChannelLocalService, commerceCurrencyLocalService,
			commerceOrderHttpHelper, configurationProvider, portal);

		_httpServletRequest = httpServletRequest;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_portal = portal;
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		long companyId = _portal.getCompanyId(_httpServletRequest);

		_commerceCurrency = _commerceCurrencyLocalService.getCommerceCurrency(
			companyId, "USD");

		if (_commerceCurrency == null) {
			_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
				companyId, "USD");
		}

		return _commerceCurrency;
	}

	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}