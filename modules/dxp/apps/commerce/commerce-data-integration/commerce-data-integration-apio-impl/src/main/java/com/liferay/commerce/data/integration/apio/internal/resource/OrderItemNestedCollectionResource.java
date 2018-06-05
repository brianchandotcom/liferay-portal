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

package com.liferay.commerce.data.integration.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.OrderIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.OrderItemIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.ProductInstanceIdentifier;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class OrderItemNestedCollectionResource
	implements
		NestedCollectionResource<CommerceOrderItem, Long, OrderItemIdentifier,
			Long, OrderIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceOrderItem, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommerceOrderItem, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "order-items";
	}

	@Override
	public ItemRoutes<CommerceOrderItem, Long> itemRoutes(
		ItemRoutes.Builder<CommerceOrderItem, Long> builder) {

		return builder.addGetter(
			this::_getCommerceOrderItem
		).build();
	}

	@Override
	public Representor<CommerceOrderItem> representor(
		Representor.Builder<CommerceOrderItem, Long> builder) {

		return builder.types(
			"OrderItem"
		).identifier(
			CommerceOrderItem::getCommerceOrderItemId
		).addBidirectionalModel(
			"order", "items", OrderIdentifier.class,
			CommerceOrderItem::getCommerceOrderId
		).addLinkedModel(
			"productSKU", ProductInstanceIdentifier.class,
			CommerceOrderItem::getCPInstanceId
		).addString(
			"sku", CommerceOrderItem::getSku
		).addLocalizedStringByLocale(
			"name", CommerceOrderItem::getName
		).addNumber(
			"quantity", CommerceOrderItem::getQuantity
		).addNumber(
			"shippedQuantity", CommerceOrderItem::getShippedQuantity
		).addNumber(
			"price", CommerceOrderItem::getPrice
		).addDate(
			"dateCreated", CommerceOrderItem::getCreateDate
		).addDate(
			"dateModified", CommerceOrderItem::getModifiedDate
		).addLinkedModel(
			"author", PersonIdentifier.class, CommerceOrderItem::getUserId
		).build();
	}

	private CommerceOrderItem _getCommerceOrderItem(Long commerceOrderItemId) {
		try {
			return _commerceOrderItemService.getCommerceOrderItem(
				commerceOrderItemId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<CommerceOrderItem> _getPageItems(
		Pagination pagination, Long commerceOrderId) {

		try {
			List<CommerceOrderItem> items =
				_commerceOrderItemService.getCommerceOrderItems(
					commerceOrderId, pagination.getStartPosition(),
					pagination.getEndPosition());

			int total = _commerceOrderItemService.getCommerceOrderItemsCount(
				commerceOrderId);

			return new PageItems<>(items, total);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

}