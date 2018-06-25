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

package com.liferay.commerce.shipment.test.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Luca Pellizzon
 */
public class CommerceShipmentTestUtil {

	public static CommerceShipment createOrderShipment(
			long groupId, long orderId, long warehouseId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceShipment commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				orderId, serviceContext);

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.getCommerceOrder(orderId);

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
				commerceShipment.getCommerceShipmentId(),
				commerceOrderItem.getCommerceOrderItemId(), warehouseId,
				commerceOrderItem.getQuantity(), serviceContext);
		}

		return commerceShipment;
	}

}