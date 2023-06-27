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

package com.liferay.commerce.shipment.web.internal.frontend.data.set.provider;

import com.liferay.commerce.constants.CommerceShipmentFDSNames;
import com.liferay.commerce.frontend.model.ShipmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommerceShipmentFDSNames.SHIPMENT_ITEMS,
	service = FDSDataProvider.class
)
public class CommerceShipmentItemFDSDataProvider
	implements FDSDataProvider<ShipmentItem> {

	@Override
	public List<ShipmentItem> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<ShipmentItem> shipmentItems = new ArrayList<>();

		long commerceShipmentId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentId");

		List<CommerceShipmentItem> commerceShipmentItems =
			_commerceShipmentItemService.getCommerceShipmentItems(
				commerceShipmentId, fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), null);

		for (CommerceShipmentItem commerceShipmentItem :
				commerceShipmentItems) {

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.getCommerceOrderItem(
					commerceShipmentItem.getCommerceOrderItemId());

			String commerceInventoryWarehouseName = StringPool.BLANK;

			if (commerceShipmentItem.getCommerceInventoryWarehouseId() > 0) {
				try {
					CommerceInventoryWarehouse commerceInventoryWarehouse =
						_commerceInventoryWarehouseService.
							fetchByCommerceInventoryWarehouse(
								commerceShipmentItem.
									getCommerceInventoryWarehouseId());

					if (commerceInventoryWarehouse != null) {
						commerceInventoryWarehouseName =
							commerceInventoryWarehouse.getName(
								_portal.getLocale(httpServletRequest));
					}
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}

			shipmentItems.add(
				new ShipmentItem(
					commerceShipmentItem.getExternalReferenceCode(),
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

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentId");

		return _commerceShipmentItemService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentItemFDSDataProvider.class);

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

}