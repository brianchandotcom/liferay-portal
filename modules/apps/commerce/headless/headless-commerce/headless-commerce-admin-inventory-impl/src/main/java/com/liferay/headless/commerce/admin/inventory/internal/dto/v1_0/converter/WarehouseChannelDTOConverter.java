/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseChannel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.inventory.model.CommerceChannelRel",
	service = DTOConverter.class
)
public class WarehouseChannelDTOConverter
	implements DTOConverter<CommerceChannelRel, WarehouseChannel> {

	@Override
	public String getContentType() {
		return WarehouseChannel.class.getSimpleName();
	}

	@Override
	public WarehouseChannel toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceChannelRel commerceWarehouseChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(
				(Long)dtoConverterContext.getId());

		CommerceChannel commerceChannel =
			commerceWarehouseChannelRel.getCommerceChannel();

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				commerceWarehouseChannelRel.getClassPK());

		return new WarehouseChannel() {
			{
				actions = dtoConverterContext.getActions();
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				warehouseChannelId =
					commerceWarehouseChannelRel.getCommerceChannelRelId();
				warehouseExternalReferenceCode =
					commerceInventoryWarehouse.getExternalReferenceCode();
				warehouseId =
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

}