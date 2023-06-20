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

package com.liferay.commerce.inventory.web.internal.frontend.data.set.provider;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.web.internal.constants.CommerceInventoryFDSNames;
import com.liferay.commerce.inventory.web.internal.model.InventoryItem;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommerceInventoryFDSNames.INVENTORY_ITEMS,
	service = FDSDataProvider.class
)
public class CommerceInventoryItemFDSDataProvider
	implements FDSDataProvider<InventoryItem> {

	@Override
	public List<InventoryItem> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		if (portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceInventoryActionKeys.MANAGE_INVENTORY)) {

			return TransformUtil.transform(
				_commerceInventoryWarehouseItemLocalService.getItemsByCompanyId(
					_portal.getCompanyId(httpServletRequest),
					fdsKeywords.getKeywords(), fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition()),
				ciWarehouseItem -> new InventoryItem(
					ciWarehouseItem.getSkuCode(),
					ciWarehouseItem.getStockQuantity(),
					ciWarehouseItem.getBookedQuantity(),
					ciWarehouseItem.getReplenishmentQuantity()));
		}

		return Collections.emptyList();
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return _commerceInventoryWarehouseItemLocalService.
			countItemsByCompanyId(
				_portal.getCompanyId(httpServletRequest),
				fdsKeywords.getKeywords());
	}

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse)"
	)
	private ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission;

	@Reference
	private Portal _portal;

}