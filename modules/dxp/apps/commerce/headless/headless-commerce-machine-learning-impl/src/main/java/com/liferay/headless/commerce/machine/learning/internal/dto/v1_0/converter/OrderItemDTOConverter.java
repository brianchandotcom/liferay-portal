/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = {DTOConverter.class, OrderItemDTOConverter.class}
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
				quantity = commerceOrderItem.getQuantity();
				sku = commerceOrderItem.getSku();
				subscription = commerceOrderItem.isSubscription();
				unitPrice = commerceOrderItem.getUnitPrice();
				userId = commerceOrderItem.getUserId();

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