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

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.ReplenishmentItem;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.headless.commerce.admin.inventory.dto.v1_0.ReplenishmentItem",
	service = {DTOConverter.class, ReplenishmentItemDTOConverter.class}
)
public class ReplenishmentItemDTOConverter
	implements DTOConverter
		<CommerceInventoryReplenishmentItem, ReplenishmentItem> {

	@Override
	public String getContentType() {
		return ReplenishmentItem.class.getSimpleName();
	}

	@Override
	public ReplenishmentItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItem(
					(Long)dtoConverterContext.getId());

		return new ReplenishmentItem() {
			{
				availabilityDate =
					commerceInventoryReplenishmentItem.getAvailabilityDate();
				externalReferenceCode =
					commerceInventoryReplenishmentItem.
						getExternalReferenceCode();
				id =
					commerceInventoryReplenishmentItem.
						getCommerceInventoryReplenishmentItemId();
				quantity = commerceInventoryReplenishmentItem.getQuantity();
				sku = commerceInventoryReplenishmentItem.getSku();
				warehouseId =
					commerceInventoryReplenishmentItem.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Reference
	private CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;

}