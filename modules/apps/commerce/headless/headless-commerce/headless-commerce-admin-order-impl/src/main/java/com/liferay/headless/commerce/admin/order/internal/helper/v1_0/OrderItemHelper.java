/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.helper.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = OrderItemHelper.class)
public class OrderItemHelper {

	public Page<OrderItem> getOrderItemsPage(
			Long id, Locale locale, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			id);

		if (commerceOrder == null) {
			return Page.of(Collections.emptyList());
		}

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrderItemService.getCommerceOrderItems(
				id, pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems = _commerceOrderItemService.getCommerceOrderItemsCount(
			id);

		return Page.of(
			toOrderItems(commerceOrderItems, locale), pagination, totalItems);
	}

	public List<OrderItem> toOrderItems(
			List<CommerceOrderItem> commerceOrderItems, Locale locale)
		throws Exception {

		List<OrderItem> orderItems = new ArrayList<>();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			orderItems.add(
				_orderItemDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						commerceOrderItem.getCommerceOrderItemId(), locale)));
		}

		return orderItems;
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference(target = DTOConverterConstants.ORDER_ITEM_DTO_CONVERTER)
	private DTOConverter<CommerceOrderItem, OrderItem> _orderItemDTOConverter;

}