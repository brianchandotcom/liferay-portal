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

package com.liferay.commerce.order.content.web.internal.frontend.data.set.provider.search;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"fds.data.provider.key=" + CommerceOrderFDSNames.PENDING_ORDER_ITEMS,
		"fds.data.provider.key=" + CommerceOrderFDSNames.PENDING_ORDERS,
		"fds.data.provider.key=" + CommerceOrderFDSNames.PLACED_ORDER_ITEMS,
		"fds.data.provider.key=" + CommerceOrderFDSNames.PLACED_ORDERS
	},
	service = FDSKeywordsFactory.class
)
public class OrderFDSKeywordsFactoryImpl implements FDSKeywordsFactory {

	@Override
	public FDSKeywords create(HttpServletRequest httpServletRequest) {
		OrderFDSKeywordsImpl orderFDSKeywordsImpl = new OrderFDSKeywordsImpl();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "accountId");

		orderFDSKeywordsImpl.setAccountId(commerceAccountId);

		orderFDSKeywordsImpl.setCommerceOrderId(commerceOrderId);
		orderFDSKeywordsImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "search"));

		return orderFDSKeywordsImpl;
	}

}