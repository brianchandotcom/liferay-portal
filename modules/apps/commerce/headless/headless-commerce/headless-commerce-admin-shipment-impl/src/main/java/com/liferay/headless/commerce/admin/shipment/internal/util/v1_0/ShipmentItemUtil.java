/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.internal.util.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
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
			CommerceInventoryWarehouseService commerceInventoryWarehouseService,
			CommerceOrderItemService commerceOrderItemService,
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
			getCommerceOrderItemId(
				serviceContext.getCompanyId(), defaultOrderItemId, shipmentItem,
				commerceOrderItemService),
			getCommerceInventoryWarehouseId(
				serviceContext.getCompanyId(), defaultWarehouseId, shipmentItem,
				commerceInventoryWarehouseService),
			BigDecimalUtil.get(shipmentItem.getQuantity(), defaultQuantity),
			null,
			GetterUtil.getBoolean(shipmentItem.getValidateInventory(), true),
			serviceContext);
	}

	public static long getCommerceInventoryWarehouseId(
			long companyId, long defaultCommerceInventoryWarehouseId,
			ShipmentItem shipmentItem,
			CommerceInventoryWarehouseService commerceInventoryWarehouseService)
		throws Exception {

		long commerceInventoryWarehouseId = GetterUtil.getLong(
			shipmentItem.getWarehouseId());

		if (commerceInventoryWarehouseId > 0) {
			return commerceInventoryWarehouseId;
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseService.fetchByExternalReferenceCode(
				shipmentItem.getWarehouseExternalReferenceCode(), companyId);

		if (commerceInventoryWarehouse != null) {
			return commerceInventoryWarehouse.getCommerceInventoryWarehouseId();
		}

		return defaultCommerceInventoryWarehouseId;
	}

	public static long getCommerceOrderItemId(
			long companyId, long defaultCommerceOrderItemId,
			ShipmentItem shipmentItem,
			CommerceOrderItemService commerceOrderItemService)
		throws Exception {

		long commerceOrderItemId = GetterUtil.getLong(
			shipmentItem.getOrderItemId());

		if (commerceOrderItemId > 0) {
			return commerceOrderItemId;
		}

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemService.fetchByExternalReferenceCode(
				shipmentItem.getOrderItemExternalReferenceCode(), companyId);

		if (commerceOrderItem != null) {
			return commerceOrderItem.getCommerceOrderItemId();
		}

		return defaultCommerceOrderItemId;
	}

}