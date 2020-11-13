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

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.NoteDTOConverter;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/cart-comment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CartCommentResource.class, NestedFieldSupport.class}
)
public class CartCommentResourceImpl
	extends BaseCartCommentResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteCartComment(Long commentId) throws Exception {
		_commerceOrderNoteService.deleteCommerceOrderNote(commentId);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public CartComment getCartComment(Long commentId) throws Exception {
		return _toOrderNote(GetterUtil.getLong(commentId));
	}

	@NestedField(parentClass = Cart.class, value = "notes")
	@Override
	public Page<CartComment> getCartCommentsPage(
			@NestedFieldId("id") @NotNull Long cartId, Pagination pagination)
		throws Exception {

		List<CommerceOrderNote> commerceOrderNotes =
			_commerceOrderNoteService.getCommerceOrderNotes(cartId, false);

		int totalItems = _commerceOrderNoteService.getCommerceOrderNotesCount(
			cartId, false);

		return Page.of(
			_toOrderNotes(commerceOrderNotes), pagination, totalItems);
	}

	@Override
	public CartComment postCartComment(Long cartId, CartComment cartComment)
		throws Exception {

		return _upsertOrderNote(
			_commerceOrderService.getCommerceOrder(cartId), cartComment);
	}

	@Override
	public CartComment putCartComment(Long commentId, CartComment cartComment)
		throws Exception {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteService.getCommerceOrderNote(commentId);

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderNote.getCommerceOrderId());

		cartComment.setId(commentId);

		return _upsertOrderNote(commerceOrder, cartComment);
	}

	private CartComment _toOrderNote(Long commerceOrderNoteId)
		throws Exception {

		return _noteDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceOrderNoteId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<CartComment> _toOrderNotes(
			List<CommerceOrderNote> commerceOrderNotes)
		throws Exception {

		List<CartComment> orders = new ArrayList<>();

		for (CommerceOrderNote commerceOrderNote : commerceOrderNotes) {
			orders.add(
				_toOrderNote(commerceOrderNote.getCommerceOrderNoteId()));
		}

		return orders;
	}

	private CartComment _upsertOrderNote(
			CommerceOrder commerceOrder, CartComment cartComment)
		throws Exception {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteService.upsertCommerceOrderNote(
				GetterUtil.get(cartComment.getId(), 0L),
				commerceOrder.getCommerceOrderId(), cartComment.getContent(),
				GetterUtil.get(cartComment.getRestricted(), false), null,
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId()));

		return _toOrderNote(commerceOrderNote.getCommerceOrderNoteId());
	}

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private NoteDTOConverter _noteDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}