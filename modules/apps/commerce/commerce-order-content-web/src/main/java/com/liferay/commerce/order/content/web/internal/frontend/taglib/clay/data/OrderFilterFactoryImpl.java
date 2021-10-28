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

package com.liferay.commerce.order.content.web.internal.frontend.taglib.clay.data;

import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.FilterFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDER_ITEMS,
		"clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS
	},
	service = FilterFactory.class
)
public class OrderFilterFactoryImpl implements FilterFactory {

	@Override
	public Filter create(HttpServletRequest httpServletRequest) {
		OrderFilterImpl orderFilterImpl = new OrderFilterImpl();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "accountId");

		orderFilterImpl.setAccountId(commerceAccountId);

		orderFilterImpl.setCommerceOrderId(commerceOrderId);

		orderFilterImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "search"));

		return orderFilterImpl;
	}

}