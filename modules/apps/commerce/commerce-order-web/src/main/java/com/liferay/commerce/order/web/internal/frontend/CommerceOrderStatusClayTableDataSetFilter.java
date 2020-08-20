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

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.frontend.taglib.clay.data.set.filter.BaseRadioClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS,
	service = ClayDataSetFilter.class
)
public class CommerceOrderStatusClayTableDataSetFilter
	extends BaseRadioClayDataSetFilter {

	@Override
	public String getId() {
		return "orderStatus";
	}

	@Override
	public List<KeyValuePair> getKeyValuePairs(Locale locale) {
		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		for (CommerceOrderStatus commerceOrderStatus :
				_commerceOrderStatusRegistry.getCommerceOrderStatuses()) {

			keyValuePairs.add(
				new KeyValuePair(
					String.valueOf(commerceOrderStatus.getKey()),
					commerceOrderStatus.getLabel(locale)));
		}

		return keyValuePairs;
	}

	@Override
	public String getLabel() {
		return "order-status";
	}

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

}