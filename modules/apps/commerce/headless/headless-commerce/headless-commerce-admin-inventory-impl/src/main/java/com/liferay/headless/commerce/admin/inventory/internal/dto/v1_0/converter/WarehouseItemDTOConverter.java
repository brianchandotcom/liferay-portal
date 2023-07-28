/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

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

		return new WarehouseItem() {
			{
				externalReferenceCode =
					commerceInventoryWarehouseItem.getExternalReferenceCode();
				id =
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId();
				sku = commerceInventoryWarehouseItem.getSku();
				warehouseExternalReferenceCode =
					commerceInventoryWarehouse.getExternalReferenceCode();
				warehouseId =
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseId();

				setQuantity(
					() -> {
						BigDecimal commerceInventoryWarehouseItemQuantity =
							commerceInventoryWarehouseItem.getQuantity();

						if (commerceInventoryWarehouseItemQuantity == null) {
							return null;
						}

						return commerceInventoryWarehouseItemQuantity.setScale(
							2);
					});
				setReservedQuantity(
					() -> {
						BigDecimal
							commerceInventoryWarehouseItemReservedQuantity =
								commerceInventoryWarehouseItem.
									getReservedQuantity();

						if (commerceInventoryWarehouseItemReservedQuantity ==
								null) {

							return null;
						}

						return commerceInventoryWarehouseItemReservedQuantity.
							setScale(2);
					});

				setUnitOfMeasureKey(
					() -> {
						if (Validator.isNull(
								commerceInventoryWarehouseItem.
									getUnitOfMeasureKey())) {

							return null;
						}

						return commerceInventoryWarehouseItem.
							getUnitOfMeasureKey();
					});
			}
		};
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

}