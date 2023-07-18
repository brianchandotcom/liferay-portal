/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.frontend.data.set.provider;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.web.internal.constants.CommerceInventoryFDSNames;
import com.liferay.commerce.inventory.web.internal.model.Warehouse;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommerceInventoryFDSNames.INVENTORY_WAREHOUSES,
	service = FDSDataProvider.class
)
public class CommerceInventoryWarehouseFDSDataProvider
	implements FDSDataProvider<Warehouse> {

	@Override
	public List<Warehouse> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<Warehouse> warehouses = new ArrayList<>();

		String sku = ParamUtil.getString(httpServletRequest, "sku");

		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
					_portal.getCompanyId(httpServletRequest), sku,
					fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition());

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				commerceInventoryWarehouseItems) {

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();

			int stockQuantity = 0;

			BigDecimal commerceInventoryWarehouseItemQuantity =
				commerceInventoryWarehouseItem.getQuantity();

			if (commerceInventoryWarehouseItemQuantity != null) {
				stockQuantity =
					commerceInventoryWarehouseItemQuantity.intValue();
			}

			int reservedQuantity = 0;

			BigDecimal commerceInventoryWarehouseItemReservedQuantity =
				commerceInventoryWarehouseItem.getReservedQuantity();

			if (commerceInventoryWarehouseItemReservedQuantity != null) {
				reservedQuantity =
					commerceInventoryWarehouseItemReservedQuantity.intValue();
			}

			int replenishmentQuantity = 0;

			BigDecimal commerceInventoryReplenishmentItemsCount =
				_commerceInventoryReplenishmentItemService.
					getCommerceInventoryReplenishmentItemsCount(
						commerceInventoryWarehouse.
							getCommerceInventoryWarehouseId(),
						sku);

			if (commerceInventoryReplenishmentItemsCount != null) {
				replenishmentQuantity =
					commerceInventoryReplenishmentItemsCount.intValue();
			}

			warehouses.add(
				new Warehouse(
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					commerceInventoryWarehouse.getName(
						_portal.getLocale(httpServletRequest)),
					stockQuantity, reservedQuantity, replenishmentQuantity));
		}

		return warehouses;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		String sku = ParamUtil.getString(httpServletRequest, "sku");

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(
				_portal.getCompanyId(httpServletRequest), sku);
	}

	@Reference
	private CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private Portal _portal;

}