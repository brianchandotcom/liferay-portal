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

package com.liferay.commerce.internal.test.util;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.CommerceWarehouseItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceWarehouseLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceTestUtil {

	public static CommerceCountry addCommerceCountry(long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceCountryLocalServiceUtil.addCommerceCountry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(2), RandomTestUtil.randomString(3),
			RandomTestUtil.randomInt(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			serviceContext);
	}

	public static CommerceOrder addCommerceOrder(long groupId)
		throws Exception {

		return addCommerceOrder(
			groupId, CommerceOrderConstants.STATUS_COMPLETED);
	}

	public static CommerceOrder addCommerceOrder(long groupId, int status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceOrderLocalServiceUtil.addCommerceOrder(
			serviceContext.getUserId(), 0, 0, null,
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomDouble(), 0, 0, status, serviceContext);
	}

	public static CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpInstanceId)
		throws Exception {

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.getCommerceOrder(commerceOrderId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commerceOrder.getGroupId());

		return CommerceOrderItemLocalServiceUtil.addCommerceOrderItem(
			commerceOrderId, cpInstanceId, RandomTestUtil.randomInt(),
			RandomTestUtil.randomInt(), null, RandomTestUtil.randomDouble(),
			serviceContext);
	}

	public static CommerceWarehouse addCommerceWarehouse(
			long groupId, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceWarehouseLocalServiceUtil.addCommerceWarehouse(
			name, RandomTestUtil.randomString(), true,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), 0, 0, RandomTestUtil.randomDouble(),
			RandomTestUtil.randomDouble(), serviceContext);
	}

	public static CommerceWarehouseItem addCommerceWarehouseItem(
			CommerceWarehouse commerceWarehouse, long cpInstanceId,
			int quantity)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commerceWarehouse.getGroupId());

		return CommerceWarehouseItemLocalServiceUtil.addCommerceWarehouseItem(
			commerceWarehouse.getCommerceWarehouseId(), cpInstanceId, quantity,
			serviceContext);
	}

}