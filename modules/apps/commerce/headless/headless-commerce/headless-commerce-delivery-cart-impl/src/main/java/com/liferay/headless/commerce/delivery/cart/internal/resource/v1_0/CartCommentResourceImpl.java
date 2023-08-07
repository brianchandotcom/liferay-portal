/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cart-comment.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = CartCommentResource.class
)
public class CartCommentResourceImpl extends BaseCartCommentResourceImpl {

	@Override
	public void deleteCartComment(Long commentId) throws Exception {
		_commerceOrderNoteService.deleteCommerceOrderNote(commentId);
	}

	@Override
	public CartComment getCartComment(Long commentId) throws Exception {
		return _toOrderNote(GetterUtil.getLong(commentId));
	}

	@NestedField(parentClass = Cart.class, value = "notes")
	@Override
	public Page<CartComment> getCartCommentsPage(
			@NestedFieldId("id") Long cartId, Pagination pagination)
		throws Exception {

		int totalItems = _commerceOrderNoteService.getCommerceOrderNotesCount(
			cartId, false);

		return Page.of(
			_toOrderNotes(
				_commerceOrderNoteService.getCommerceOrderNotes(
					cartId, false, pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination, totalItems);
	}

	@Override
	public CartComment postCartComment(Long cartId, CartComment cartComment)
		throws Exception {

		return _addOrUpdateOrderNote(
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

		return _addOrUpdateOrderNote(commerceOrder, cartComment);
	}

	private CartComment _addOrUpdateOrderNote(
			CommerceOrder commerceOrder, CartComment cartComment)
		throws Exception {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteService.addOrUpdateCommerceOrderNote(
				null, GetterUtil.get(cartComment.getId(), 0L),
				commerceOrder.getCommerceOrderId(), cartComment.getContent(),
				GetterUtil.get(cartComment.getRestricted(), false),
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId()));

		return _toOrderNote(commerceOrderNote.getCommerceOrderNoteId());
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

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.NoteDTOConverter)"
	)
	private DTOConverter<CommerceOrderNote, CartComment> _noteDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}