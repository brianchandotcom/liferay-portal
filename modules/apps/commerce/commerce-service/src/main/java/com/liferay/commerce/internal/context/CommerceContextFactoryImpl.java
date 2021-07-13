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

package com.liferay.commerce.internal.context;

import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.context.BaseCommerceContext;
import com.liferay.commerce.context.BaseCommerceContextHttp;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, service = CommerceContextFactory.class)
public class CommerceContextFactoryImpl implements CommerceContextFactory {

	@Override
	public CommerceContext create(HttpServletRequest httpServletRequest) {
		return new BaseCommerceContextHttp(
			httpServletRequest, _commerceAccountHelper,
			_commerceChannelLocalService, _commerceCurrencyLocalService,
			_commerceOrderHttpHelper, _configurationProvider, _portal);
	}

	@Override
	public CommerceContext create(
		long companyId, long commerceChannelGroupId, long userId, long orderId,
		long commerceAccountId) {

		return new BaseCommerceContext(
			companyId, commerceChannelGroupId, orderId, commerceAccountId,
			_commerceAccountHelper, _commerceAccountLocalService,
			_commerceAccountService, _commerceChannelLocalService,
			_commerceCurrencyLocalService, _commerceOrderService,
			_configurationProvider);
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}