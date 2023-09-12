/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CommerceShipmentItem",
	service = DTOConverter.class
)
public class ShipmentItemDTOConverter
	implements DTOConverter<CommerceShipmentItem, ShipmentItem> {

	@Override
	public String getContentType() {
		return ShipmentItem.class.getSimpleName();
	}

	@Override
	public ShipmentItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				(Long)dtoConverterContext.getId());

		return new ShipmentItem() {
			{
				actions = dtoConverterContext.getActions();
				createDate = commerceShipmentItem.getCreateDate();
				externalReferenceCode =
					commerceShipmentItem.getExternalReferenceCode();
				id = commerceShipmentItem.getCommerceShipmentItemId();
				modifiedDate = commerceShipmentItem.getModifiedDate();
				orderItemId = commerceShipmentItem.getCommerceOrderItemId();
				shipmentId = commerceShipmentItem.getCommerceShipmentId();
				unitOfMeasureKey = commerceShipmentItem.getUnitOfMeasureKey();
				userName = commerceShipmentItem.getUserName();
				warehouseId =
					commerceShipmentItem.getCommerceInventoryWarehouseId();

				setQuantity(
					() -> {
						CommerceOrderItem commerceOrderItem =
							_commerceOrderItemLocalService.
								fetchCommerceOrderItem(
									commerceShipmentItem.
										getCommerceOrderItemId());

						if (commerceOrderItem == null) {
							return null;
						}

						return _commerceQuantityFormatter.format(
							commerceOrderItem.getCPInstanceId(),
							commerceShipmentItem.getQuantity(),
							commerceOrderItem.getUnitOfMeasureKey());
					});
				setShipmentExternalReferenceCode(
					() -> {
						CommerceShipment commerceShipment =
							commerceShipmentItem.getCommerceShipment();

						return commerceShipment.getExternalReferenceCode();
					});
			}
		};
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

}