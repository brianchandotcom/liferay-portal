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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Order;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.OrderItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrder",
	service = {DTOConverter.class, OrderDTOConverter.class}
)
public class OrderDTOConverter implements DTOConverter<CommerceOrder, Order> {

	@Override
	public String getContentType() {
		return Order.class.getSimpleName();
	}

	@Override
	public CommerceOrder getObject(String externalReferenceCode)
		throws Exception {

		return _commerceOrderLocalService.fetchCommerceOrder(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public Order toDTO(
			DTOConverterContext dtoConverterContext,
			CommerceOrder commerceOrder)
		throws Exception {

		if (commerceOrder == null) {
			return null;
		}

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		ExpandoBridge expandoBridge = commerceOrder.getExpandoBridge();

		return new Order() {
			{
				accountId = commerceOrder.getCommerceAccountId();
				channelId = commerceOrder.getGroupId();
				createDate = commerceOrder.getCreateDate();
				currencyCode = commerceCurrency.getCode();
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commerceOrder.getExternalReferenceCode();
				id = commerceOrder.getCommerceOrderId();
				modifiedDate = commerceOrder.getModifiedDate();
				orderDate = commerceOrder.getOrderDate();
				orderItems = TransformUtil.transformToArray(
					commerceOrder.getCommerceOrderItems(),
					commerceOrderItem -> _orderItemDTOConverter.toDTO(
						commerceOrderItem),
					OrderItem.class);
				orderStatus = commerceOrder.getOrderStatus();
				orderTypeId = commerceOrder.getCommerceOrderTypeId();
				paymentMethod = commerceOrder.getCommercePaymentMethodKey();
				paymentStatus = commerceOrder.getPaymentStatus();
				status = commerceOrder.getStatus();
				total = commerceOrder.getTotal();
				userId = commerceOrder.getUserId();
			}
		};
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private OrderItemDTOConverter _orderItemDTOConverter;

}