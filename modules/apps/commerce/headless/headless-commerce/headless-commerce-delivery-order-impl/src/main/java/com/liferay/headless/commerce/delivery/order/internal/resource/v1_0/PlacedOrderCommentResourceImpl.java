/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderComment;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderCommentResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/placed-order-comment.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PlacedOrderCommentResource.class
)
public class PlacedOrderCommentResourceImpl
	extends BasePlacedOrderCommentResourceImpl {

	@Override
	public Page<PlacedOrderComment>
			getPlacedOrderByExternalReferenceCodePlacedOrderCommentsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return getPlacedOrderPlacedOrderCommentsPage(
			commerceOrder.getCommerceOrderId(), pagination);
	}

	@Override
	public PlacedOrderComment getPlacedOrderComment(Long placedOrderCommentId)
		throws Exception {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteService.getCommerceOrderNote(
				placedOrderCommentId);

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderNote.getCommerceOrderId());

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return _toPlacedOrderComment(placedOrderCommentId);
	}

	@Override
	public PlacedOrderComment getPlacedOrderCommentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteService.
				fetchCommerceOrderNoteByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderNote == null) {
			throw new NoSuchOrderNoteException(
				"Unable to find order note with external reference code " +
					externalReferenceCode);
		}

		return getPlacedOrderComment(
			commerceOrderNote.getCommerceOrderNoteId());
	}

	@NestedField(parentClass = PlacedOrder.class, value = "placedOrderComments")
	@Override
	public Page<PlacedOrderComment> getPlacedOrderPlacedOrderCommentsPage(
			@NestedFieldId("id") Long orderId, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			orderId);

		if (commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}

		return Page.of(
			transform(
				_commerceOrderNoteService.getCommerceOrderNotes(
					orderId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				commerceOrderNote -> _toPlacedOrderComment(
					commerceOrderNote.getCommerceOrderNoteId())),
			pagination,
			_commerceOrderNoteService.getCommerceOrderNotesCount(orderId));
	}

	private PlacedOrderComment _toPlacedOrderComment(Long commerceOrderNoteId)
		throws Exception {

		return _placedOrderCommentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, commerceOrderNoteId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.PlacedOrderCommentDTOConverter)"
	)
	private DTOConverter<CommerceOrderNote, PlacedOrderComment>
		_placedOrderCommentDTOConverter;

}