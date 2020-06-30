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

import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.model.ShipmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENT_ITEMS,
	service = CommerceDataSetDataProvider.class
)
public class CommerceShipmentItemDataSetDataProvider
	implements CommerceDataSetDataProvider<ShipmentItem> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentId");

		return _commerceShipmentItemService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public List<ShipmentItem> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ShipmentItem> shipmentItems = new ArrayList<>();

		long commerceShipmentId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentId");

		List<CommerceShipmentItem> commerceShipmentItems =
			_commerceShipmentItemService.getCommerceShipmentItems(
				commerceShipmentId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		for (CommerceShipmentItem commerceShipmentItem :
				commerceShipmentItems) {

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.getCommerceOrderItem(
					commerceShipmentItem.getCommerceOrderItemId());

			String commerceInventoryWarehouseName = StringPool.BLANK;

			if (commerceShipmentItem.getCommerceInventoryWarehouseId() > 0) {
				CommerceInventoryWarehouse commerceInventoryWarehouse =
					_commerceInventoryWarehouseService.
						getCommerceInventoryWarehouse(
							commerceShipmentItem.
								getCommerceInventoryWarehouseId());

				commerceInventoryWarehouseName =
					commerceInventoryWarehouse.getName();
			}

			shipmentItems.add(
				new ShipmentItem(
					commerceOrderItem.getCommerceOrderId(),
					commerceOrderItem.getQuantity() -
						commerceOrderItem.getShippedQuantity(),
					commerceShipmentItem.getCommerceShipmentItemId(),
					commerceOrderItem.getShippedQuantity(),
					commerceOrderItem.getSku(),
					commerceShipmentItem.getQuantity(),
					commerceInventoryWarehouseName));
		}

		return shipmentItems;
	}

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

}