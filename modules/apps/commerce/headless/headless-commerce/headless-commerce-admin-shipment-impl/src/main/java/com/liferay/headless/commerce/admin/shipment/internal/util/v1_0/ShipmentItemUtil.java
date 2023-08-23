/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.internal.util.v1_0;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

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
		BigDecimal defaultQuantity = BigDecimal.ZERO;
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
			BigDecimalUtil.get(shipmentItem.getQuantity(), defaultQuantity),
			null,
			GetterUtil.getBoolean(shipmentItem.getValidateInventory(), true),
			serviceContext);
	}

}