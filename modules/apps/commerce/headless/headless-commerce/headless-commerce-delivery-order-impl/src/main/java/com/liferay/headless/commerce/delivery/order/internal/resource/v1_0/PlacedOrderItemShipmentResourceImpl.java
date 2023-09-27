/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.PlacedOrderItemShipmentDTOConverterContext;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemShipmentResource;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/placed-order-item-shipment.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PlacedOrderItemShipmentResource.class
)
public class PlacedOrderItemShipmentResourceImpl
	extends BasePlacedOrderItemShipmentResourceImpl {

	@NestedField(
		parentClass = PlacedOrderItem.class, value = "placedOrderItemShipments"
	)
	@Override
	public Page<PlacedOrderItemShipment>
			getPlacedOrderItemPlacedOrderItemShipmentsPage(
				@NestedFieldId("id") Long placedOrderItemId)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(placedOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return Page.of(_toPlaceOrderItemShipment(placedOrderItemId));
	}

	private List<PlacedOrderItemShipment> _toPlaceOrderItemShipment(
			long placedOrderItemId)
		throws Exception {

		List<CommerceShipmentItem> commerceShipmentItems =
			_commerceShipmentItemService.
				getCommerceShipmentItemsByCommerceOrderItemId(
					placedOrderItemId);

		if (ListUtil.isNotEmpty(commerceShipmentItems)) {
			return transform(
				commerceShipmentItems,
				commerceShipmentItem ->
					_placedOrderItemShipmentDTOConverter.toDTO(
						new PlacedOrderItemShipmentDTOConverterContext(
							commerceShipmentItem.getCommerceShipmentItemId(),
							contextAcceptLanguage.getPreferredLocale(),
							false)));
		}

		List<CommerceOrderItem> supplierCommerceOrderItems =
			_commerceOrderItemService.getSupplierCommerceOrderItems(
				placedOrderItemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<CommerceShipmentItem> supplierCommerceShipmentItems =
			new ArrayList<>();

		for (CommerceOrderItem supplierCommerceOrderItem :
				supplierCommerceOrderItems) {

			CommerceOrder supplierCommerceOrder =
				supplierCommerceOrderItem.getCommerceOrder();

			if (supplierCommerceOrder.isOpen()) {
				continue;
			}

			supplierCommerceShipmentItems.addAll(
				_commerceShipmentItemService.
					getCommerceShipmentItemsByCommerceOrderItemId(
						supplierCommerceOrderItem.getCommerceOrderItemId()));
		}

		return transform(
			supplierCommerceShipmentItems,
			supplierCommerceShipmentItem ->
				_placedOrderItemShipmentDTOConverter.toDTO(
					new PlacedOrderItemShipmentDTOConverterContext(
						supplierCommerceShipmentItem.
							getCommerceShipmentItemId(),
						contextAcceptLanguage.getPreferredLocale(), true)));
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.PlacedOrderItemShipmentDTOConverter)"
	)
	private DTOConverter<CommerceShipment, PlacedOrderItemShipment>
		_placedOrderItemShipmentDTOConverter;

}