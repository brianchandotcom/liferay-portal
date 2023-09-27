/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter;

import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Status;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment",
	service = DTOConverter.class
)
public class PlacedOrderItemShipmentDTOConverter
	implements DTOConverter<CommerceShipment, PlacedOrderItemShipment> {

	@Override
	public String getContentType() {
		return PlacedOrderItemShipment.class.getSimpleName();
	}

	@Override
	public PlacedOrderItemShipment toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		PlacedOrderItemShipmentDTOConverterContext
			placedOrderItemShipmentDTOConverterContext =
				(PlacedOrderItemShipmentDTOConverterContext)dtoConverterContext;

		Long commerceShipmentItemId =
			(Long)placedOrderItemShipmentDTOConverterContext.getId();

		Locale locale = dtoConverterContext.getLocale();

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				commerceShipmentItemId);

		CommerceShipment commerceShipment =
			_commerceShipmentLocalService.getCommerceShipment(
				commerceShipmentItem.getCommerceShipmentId());

		return new PlacedOrderItemShipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				author = commerceShipment.getUserName();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				estimatedDeliveryDate = commerceShipment.getExpectedDate();
				estimatedShippingDate = commerceShipment.getShippingDate();
				id = commerceShipment.getCommerceShipmentId();
				modifiedDate = commerceShipment.getModifiedDate();
				shippingAddressId = commerceShipment.getCommerceAddressId();
				shippingMethodId =
					commerceShipment.getCommerceShippingMethodId();
				shippingOptionName = commerceShipment.getShippingOptionName();
				status = new Status() {
					{
						code = commerceShipment.getStatus();
						label =
							CommerceShipmentConstants.getShipmentStatusLabel(
								commerceShipment.getStatus());
						label_i18n = _language.get(
							LanguageResources.getResourceBundle(locale),
							CommerceShipmentConstants.getShipmentStatusLabel(
								commerceShipment.getStatus()));
					}
				};
				supplierShipment =
					placedOrderItemShipmentDTOConverterContext.
						isSupplierShipment();
				trackingNumber = commerceShipment.getTrackingNumber();
				trackingURL = commerceShipment.getTrackingURL();
				unitOfMeasureKey = commerceShipmentItem.getUnitOfMeasureKey();

				setQuantity(
					() -> {
						CommerceOrderItem commerceOrderItem =
							_commerceOrderItemLocalService.getCommerceOrderItem(
								commerceShipmentItem.getCommerceOrderItemId());

						return _commerceQuantityFormatter.format(
							commerceOrderItem.getCPInstanceId(),
							commerceShipmentItem.getQuantity(),
							commerceShipmentItem.getUnitOfMeasureKey());
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

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@Reference
	private Language _language;

}