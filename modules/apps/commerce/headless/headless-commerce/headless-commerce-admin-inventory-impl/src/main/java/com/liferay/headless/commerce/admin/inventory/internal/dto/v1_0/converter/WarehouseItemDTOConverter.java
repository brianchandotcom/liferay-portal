/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem",
	service = DTOConverter.class
)
public class WarehouseItemDTOConverter
	implements DTOConverter<CommerceInventoryWarehouseItem, WarehouseItem> {

	@Override
	public String getContentType() {
		return WarehouseItem.class.getSimpleName();
	}

	@Override
	public WarehouseItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItem(
					(Long)dtoConverterContext.getId());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasure(
				commerceInventoryWarehouseItem.getCompanyId(),
				commerceInventoryWarehouseItem.getUnitOfMeasureKey(),
				commerceInventoryWarehouseItem.getSku());

		return new WarehouseItem() {
			{
				externalReferenceCode =
					commerceInventoryWarehouseItem.getExternalReferenceCode();
				id =
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId();
				quantity = _commerceQuantityFormatter.format(
					cpInstanceUnitOfMeasure,
					commerceInventoryWarehouseItem.getQuantity());
				reservedQuantity = _commerceQuantityFormatter.format(
					cpInstanceUnitOfMeasure,
					commerceInventoryWarehouseItem.getReservedQuantity());
				sku = commerceInventoryWarehouseItem.getSku();
				unitOfMeasureKey =
					commerceInventoryWarehouseItem.getUnitOfMeasureKey();
				warehouseExternalReferenceCode =
					commerceInventoryWarehouse.getExternalReferenceCode();
				warehouseId =
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}