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

package com.liferay.headless.commerce.admin.shipment.internal.util.v1_0;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class ShipmentItemUtil {

	public static CommerceShipmentItem addOrUpdateShipmentItem(
			String externalReferenceCode, CommerceShipment commerceShipment,
			CommerceShipmentItemService commerceShipmentItemService,
			ShipmentItem shipmentItem,
			ServiceContextHelper serviceContextHelper)
		throws Exception {

		long defaultOrderItemId = 0;
		int defaultQuantity = 0;
		long defaultWarehouseId = 0;

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommerceShipmentItem commerceShipmentItem = null;

		if (Validator.isNotNull(externalReferenceCode)) {
			commerceShipmentItem =
				commerceShipmentItemService.
					fetchCommerceShipmentItemByExternalReferenceCode(
						serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (commerceShipmentItem != null) {
			defaultOrderItemId = commerceShipmentItem.getCommerceOrderItemId();
			defaultQuantity = commerceShipmentItem.getQuantity();
			defaultWarehouseId =
				commerceShipmentItem.getCommerceInventoryWarehouseId();
		}

		return commerceShipmentItemService.addOrUpdateCommerceShipmentItem(
			externalReferenceCode, commerceShipment.getCommerceShipmentId(),
			GetterUtil.getLong(
				shipmentItem.getOrderItemId(), defaultOrderItemId),
			GetterUtil.getLong(
				shipmentItem.getWarehouseId(), defaultWarehouseId),
			GetterUtil.getInteger(shipmentItem.getQuantity(), defaultQuantity),
			GetterUtil.getBoolean(shipmentItem.getValidateInventory(), true),
			serviceContext);
	}

}