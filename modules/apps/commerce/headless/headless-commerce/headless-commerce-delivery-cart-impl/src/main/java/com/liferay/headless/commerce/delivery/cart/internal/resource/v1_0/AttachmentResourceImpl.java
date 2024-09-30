/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.AttachmentResource;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.ByteArrayInputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/attachment.properties",
	scope = ServiceScope.PROTOTYPE, service = AttachmentResource.class
)
public class AttachmentResourceImpl extends BaseAttachmentResourceImpl {

	@Override
	public void deleteCartAttachment(Long attachmentId, Long cartId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		_commerceOrderService.deleteAttachmentFileEntry(
			attachmentId, commerceOrder.getCommerceOrderId());
	}

	@Override
	public void
			deleteCartByExternalReferenceCodeAttachmentByExternalReferenceCodeAttachmentExternalReferenceCode(
				String attachmentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find cart with external reference code " +
					externalReferenceCode);
		}

		LocalRepository localRepository = commerceOrder.getLocalRepository();

		FileEntry fileEntry =
			localRepository.getFileEntryByExternalReferenceCode(
				attachmentExternalReferenceCode);

		deleteCartAttachment(
			fileEntry.getFileEntryId(), commerceOrder.getCommerceOrderId());
	}

	@Override
	public Page<Attachment> getCartAttachmentsPage(
			Long cartId, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return Page.of(
			transform(
				commerceOrder.getAttachmentFileEntries(
					pagination.getStartPosition(), pagination.getEndPosition()),
				_attachmentDTOConverter::toDTO),
			pagination, commerceOrder.getAttachmentFileEntriesCount());
	}

	@Override
	public Page<Attachment> getCartByExternalReferenceCodeAttachmentsPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find cart with external reference code " +
					externalReferenceCode);
		}

		return getCartAttachmentsPage(
			commerceOrder.getCommerceOrderId(), pagination);
	}

	@Override
	public Attachment postCartAttachmentByBase64(
			Long cartId, AttachmentBase64 attachmentBase64)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return _attachmentDTOConverter.toDTO(
			_commerceOrderService.addAttachmentFileEntry(
				attachmentBase64.getExternalReferenceCode(),
				contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
				attachmentBase64.getTitle(),
				new ByteArrayInputStream(
					Base64.decode(attachmentBase64.getAttachment()))));
	}

	@Override
	public Attachment postCartByExternalReferenceCodeAttachmentByBase64(
			String externalReferenceCode, AttachmentBase64 attachmentBase64)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find cart with external reference code " +
					externalReferenceCode);
		}

		return postCartAttachmentByBase64(
			commerceOrder.getCommerceOrderId(), attachmentBase64);
	}

	@Reference(target = DTOConverterConstants.ATTACHMENT_DTO_CONVERTER)
	private DTOConverter<FileEntry, Attachment> _attachmentDTOConverter;

	@Reference
	private CommerceOrderService _commerceOrderService;

}