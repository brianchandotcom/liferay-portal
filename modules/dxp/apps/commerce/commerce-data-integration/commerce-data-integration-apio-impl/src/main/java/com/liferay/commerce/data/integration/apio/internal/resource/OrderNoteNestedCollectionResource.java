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
import com.liferay.commerce.data.integration.apio.identifiers.OrderNoteIdentifier;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.ServerErrorException;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class OrderNoteNestedCollectionResource
	implements
		NestedCollectionResource<CommerceOrderNote, Long, OrderNoteIdentifier,
			Long, OrderIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceOrderNote, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommerceOrderNote, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "order-notes";
	}

	@Override
	public ItemRoutes<CommerceOrderNote, Long> itemRoutes(
		ItemRoutes.Builder<CommerceOrderNote, Long> builder) {

		return builder.addGetter(
			this::_getCommerceOrderNote
		).build();
	}

	@Override
	public Representor<CommerceOrderNote> representor(
		Representor.Builder<CommerceOrderNote, Long> builder) {

		return builder.types(
			"OrderNote"
		).identifier(
			CommerceOrderNote::getCommerceOrderNoteId
		).addBidirectionalModel(
			"order", "notes", OrderIdentifier.class,
			CommerceOrderNote::getCommerceOrderId
		).addString(
			"content", CommerceOrderNote::getContent
		).addBoolean(
			"restricted", CommerceOrderNote::getRestricted
		).addDate(
			"dateCreated", CommerceOrderNote::getCreateDate
		).addDate(
			"dateModified", CommerceOrderNote::getModifiedDate
		).addLinkedModel(
			"author", PersonIdentifier.class, CommerceOrderNote::getUserId
		).build();
	}

	private CommerceOrderNote _getCommerceOrderNote(Long commerceOrderNoteId) {
		try {
			return _commerceOrderNoteService.getCommerceOrderNote(
				commerceOrderNoteId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<CommerceOrderNote> _getPageItems(
		Pagination pagination, Long commerceOrderId) {

		try {
			List<CommerceOrderNote> notes =
				_commerceOrderNoteService.getCommerceOrderNotes(
					commerceOrderId, pagination.getStartPosition(),
					pagination.getEndPosition());

			int total = _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrderId);

			return new PageItems<>(notes, total);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

}