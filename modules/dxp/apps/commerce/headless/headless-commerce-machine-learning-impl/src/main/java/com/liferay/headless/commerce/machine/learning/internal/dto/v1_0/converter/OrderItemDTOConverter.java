/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.OrderItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = DTOConverter.class
)
public class OrderItemDTOConverter
	implements DTOConverter<CommerceOrderItem, OrderItem> {

	@Override
	public String getContentType() {
		return OrderItem.class.getSimpleName();
	}

	@Override
	public CommerceOrderItem getObject(String externalReferenceCode)
		throws Exception {

		return _commerceOrderItemLocalService.fetchCommerceOrderItem(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public OrderItem toDTO(
			DTOConverterContext dtoConverterContext,
			CommerceOrderItem commerceOrderItem)
		throws Exception {

		if (commerceOrderItem == null) {
			return null;
		}

		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		return new OrderItem() {
			{
				cpDefinitionId = commerceOrderItem.getCPDefinitionId();
				createDate = commerceOrderItem.getCreateDate();
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commerceOrderItem.getExternalReferenceCode();
				finalPrice = commerceOrderItem.getFinalPrice();
				id = commerceOrderItem.getCommerceOrderItemId();
				modifiedDate = commerceOrderItem.getModifiedDate();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderItem.getNameMap());
				options = commerceOrderItem.getJson();
				parentOrderItemId =
					commerceOrderItem.getParentCommerceOrderItemId();

				sku = commerceOrderItem.getSku();
				subscription = commerceOrderItem.isSubscription();
				unitPrice = commerceOrderItem.getUnitPrice();
				userId = commerceOrderItem.getUserId();

				setQuantity(
					() -> {
						BigDecimal quantity = commerceOrderItem.getQuantity();

						return quantity.intValue();
					});
				setUnitOfMeasure(
					() -> {
						if (commerceOrderItem.getCPMeasurementUnitId() <= 0) {
							return StringPool.BLANK;
						}

						CPMeasurementUnit cpMeasurementUnit =
							_cpMeasurementUnitLocalService.getCPMeasurementUnit(
								commerceOrderItem.getCPMeasurementUnitId());

						return cpMeasurementUnit.getKey();
					});
			}
		};
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}