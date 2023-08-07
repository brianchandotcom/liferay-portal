/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.PlacedOrderItemDTOConverterContext;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemResource;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
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
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PlacedOrderItemResource.class
)
public class PlacedOrderItemResourceImpl
	extends BasePlacedOrderItemResourceImpl {

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

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.PlacedOrderItemDTOConverter)"
	)
	private DTOConverter<CommerceOrderItem, PlacedOrderItem>
		_placedOrderItemDTOConverter;

}