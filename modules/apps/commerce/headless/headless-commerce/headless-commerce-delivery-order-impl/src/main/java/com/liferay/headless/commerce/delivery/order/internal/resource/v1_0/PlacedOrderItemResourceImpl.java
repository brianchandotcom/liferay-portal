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

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.PlacedOrderItemDTOConverter;
import com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.PlacedOrderItemDTOConverterContext;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemResource;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/placed-order-item.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PlacedOrderItemResource.class}
)
public class PlacedOrderItemResourceImpl
	extends BasePlacedOrderItemResourceImpl implements NestedFieldSupport {

	@Override
	public PlacedOrderItem getPlacedOrderItem(Long placedOrderItemId)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(placedOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return _toPlacedOrderItem(
			commerceOrder.getCommerceAccountId(), commerceOrderItem);
	}

	@NestedField(parentClass = PlacedOrder.class, value = "placedOrderItems")
	@Override
	public Page<PlacedOrderItem> getPlacedOrderPlacedOrderItemsPage(
			@NestedFieldId("id") Long placedOrderId, Long skuId,
			Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return Page.of(
			_filterPlacedOrderItems(
				transform(
					_commerceOrderItemService.getCommerceOrderItems(
						placedOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					commerceOrderItem -> {
						if ((skuId != null) &&
							!Objects.equals(
								commerceOrderItem.getCPInstanceId(), skuId)) {

							return null;
						}

						return _toPlacedOrderItem(
							commerceOrder.getCommerceAccountId(),
							commerceOrderItem);
					})));
	}

	private List<PlacedOrderItem> _filterPlacedOrderItems(
		List<PlacedOrderItem> placedOrderItems) {

		Map<Long, PlacedOrderItem> placedOrderItemMap = new HashMap<>();

		for (PlacedOrderItem placedOrderItem : placedOrderItems) {
			placedOrderItemMap.put(placedOrderItem.getId(), placedOrderItem);
		}

		for (PlacedOrderItem placedOrderItem : placedOrderItems) {
			Long parentOrderItemId = placedOrderItem.getParentOrderItemId();

			if (parentOrderItemId == null) {
				continue;
			}

			PlacedOrderItem parentOrderItem = placedOrderItemMap.get(
				parentOrderItemId);

			if (parentOrderItem == null) {
				continue;
			}

			if (parentOrderItem.getPlacedOrderItems() == null) {
				parentOrderItem.setPlacedOrderItems(new PlacedOrderItem[0]);
			}

			parentOrderItem.setPlacedOrderItems(
				ArrayUtil.append(
					parentOrderItem.getPlacedOrderItems(), placedOrderItem));

			placedOrderItemMap.remove(placedOrderItem.getId());
		}

		return new ArrayList(placedOrderItemMap.values());
	}

	private PlacedOrderItem _toPlacedOrderItem(
			long commerceAccountId, CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _placedOrderItemDTOConverter.toDTO(
			new PlacedOrderItemDTOConverterContext(
				commerceAccountId, commerceOrderItem.getCommerceOrderItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private PlacedOrderItemDTOConverter _placedOrderItemDTOConverter;

}