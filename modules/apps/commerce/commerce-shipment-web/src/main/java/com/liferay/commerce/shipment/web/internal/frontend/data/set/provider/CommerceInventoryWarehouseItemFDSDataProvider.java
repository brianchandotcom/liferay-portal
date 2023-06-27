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

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentFDSNames;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.shipment.web.internal.model.Warehouse;
import com.liferay.commerce.shipment.web.internal.model.WarehouseItem;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
	property = "fds.data.provider.key=" + CommerceShipmentFDSNames.INVENTORY_WAREHOUSE_ITEM,
	service = FDSDataProvider.class
)
public class CommerceInventoryWarehouseItemFDSDataProvider
	implements FDSDataProvider<Warehouse> {

	@Override
	public List<Warehouse> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
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

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelByGroupClassPK(
				commerceOrderItem.getGroupId());

		_commerceChannelModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			commerceChannel.getCommerceChannelId(), ActionKeys.VIEW);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
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
						commerceInventoryWarehouse.getName(
							_portal.getLocale(httpServletRequest))));
			}
			else {
				warehouses.add(
					new Warehouse(
						commerceInventoryWarehouseId,
						new WarehouseItem(
							inputName, shipmentItemWarehouseItemQuantity, 0,
							shipmentItemWarehouseItemQuantity),
						0, StringPool.BLANK,
						commerceInventoryWarehouse.getName(
							_portal.getLocale(httpServletRequest))));
			}
		}

		return warehouses;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceShipmentItemId = ParamUtil.getLong(
			httpServletRequest, "commerceShipmentItemId");

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				commerceShipmentItemId);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelByGroupClassPK(
				commerceOrderItem.getGroupId());

		_commerceChannelModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			commerceChannel.getCommerceChannelId(), ActionKeys.VIEW);

		return _commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(
				_portal.getCompanyId(httpServletRequest),
				commerceOrderItem.getGroupId(), commerceOrderItem.getSku());
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

}