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

package com.liferay.commerce.shipment.web.internal.frontend;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.shipment.web.internal.model.Warehouse;
import com.liferay.commerce.shipment.web.internal.model.WarehouseItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_WAREHOUSE_ITEM,
	service = CommerceDataSetDataProvider.class
)
public class CommerceInventoryWarehouseItemDataSetDataProvider
	implements CommerceDataSetDataProvider<Warehouse> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceShipmentItemId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentItemId");

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				commerceShipmentItemId);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(
				_portal.getCompanyId(httpServletRequest),
				commerceOrderItem.getSku());
	}

	@Override
	public List<Warehouse> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Warehouse> warehouses = new ArrayList<>();

		long companyId = _portal.getCompanyId(httpServletRequest);

		long commerceShipmentItemId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentItemId");

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				commerceShipmentItemId);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				companyId, commerceOrderItem.getGroupId(), true);

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			long commerceInventoryWarehouseId =
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId();

			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemService.
					fetchCommerceInventoryWarehouseItem(
						commerceInventoryWarehouseId,
						commerceOrderItem.getSku());

			String portletNamespace = _portal.getPortletNamespace(
				CommercePortletKeys.COMMERCE_SHIPMENT);

			String inputName =
				portletNamespace + commerceInventoryWarehouseId + "_quantity";

			int maxShippableQuantity =
				commerceOrderItem.getQuantity() -
					commerceOrderItem.getShippedQuantity();

			int shipmentItemWarehouseItemQuantity = 0;

			long commerceShipmentId = ParamUtil.getLong(
				httpServletRequest, "commerceShipmentId");

			commerceShipmentItem =
				_commerceShipmentItemLocalService.fetchCommerceShipmentItem(
					commerceShipmentId,
					commerceOrderItem.getCommerceOrderItemId(),
					commerceInventoryWarehouseId);

			if (commerceShipmentItem != null) {
				shipmentItemWarehouseItemQuantity =
					commerceShipmentItem.getQuantity();

				maxShippableQuantity =
					maxShippableQuantity + commerceShipmentItem.getQuantity();
			}

			if (commerceInventoryWarehouseItem != null) {
				if (maxShippableQuantity >
						commerceInventoryWarehouseItem.getQuantity()) {

					maxShippableQuantity =
						commerceInventoryWarehouseItem.getQuantity();
				}

				warehouses.add(
					new Warehouse(
						commerceInventoryWarehouseId,
						new WarehouseItem(
							inputName, maxShippableQuantity, 0,
							shipmentItemWarehouseItemQuantity),
						commerceInventoryWarehouseItem.getQuantity(),
						StringPool.BLANK,
						commerceInventoryWarehouse.getName()));
			}
			else {
				warehouses.add(
					new Warehouse(
						commerceInventoryWarehouseId,
						new WarehouseItem(
							inputName, shipmentItemWarehouseItemQuantity, 0,
							shipmentItemWarehouseItemQuantity),
						0, StringPool.BLANK,
						commerceInventoryWarehouse.getName()));
			}
		}

		return warehouses;
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

}