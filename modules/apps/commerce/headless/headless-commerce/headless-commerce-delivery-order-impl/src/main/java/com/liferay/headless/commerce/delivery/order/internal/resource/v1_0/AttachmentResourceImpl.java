/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.AttachmentResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;
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
	public void deletePlacedOrderAttachment(
			Long attachmentId, Long placedOrderId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		_commerceOrderService.deleteAttachmentFileEntry(
			attachmentId, commerceOrder.getCommerceOrderId());
	}

	@Override
	public void
			deletePlacedOrderByExternalReferenceCodeAttachmentByExternalReferenceCodeAttachmentExternalReferenceCode(
				String attachmentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		LocalRepository localRepository = commerceOrder.getLocalRepository();

		FileEntry fileEntry =
			localRepository.getFileEntryByExternalReferenceCode(
				attachmentExternalReferenceCode);

		deletePlacedOrderAttachment(
			fileEntry.getFileEntryId(), commerceOrder.getCommerceOrderId());
	}

	@Override
	public Page<Attachment> getPlacedOrderAttachmentsPage(
			Long placedOrderId, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		return Page.of(
			transform(
				commerceOrder.getAttachmentFileEntries(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toAttachment),
			pagination, commerceOrder.getAttachmentFileEntriesCount());
	}

	@Override
	public Page<Attachment>
			getPlacedOrderByExternalReferenceCodeAttachmentsPage(
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

		return getPlacedOrderAttachmentsPage(
			commerceOrder.getCommerceOrderId(), pagination);
	}

	@Override
	public Attachment postPlacedOrderAttachmentByBase64(
			Long placedOrderId, AttachmentBase64 attachmentBase64)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		return _toAttachment(
			_commerceOrderService.addAttachmentFileEntry(
				attachmentBase64.getExternalReferenceCode(),
				contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
				attachmentBase64.getTitle(),
				new ByteArrayInputStream(
					Base64.decode(attachmentBase64.getAttachment()))));
	}

	@Override
	public Attachment postPlacedOrderByExternalReferenceCodeAttachmentByBase64(
			String externalReferenceCode, AttachmentBase64 attachmentBase64)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return postPlacedOrderAttachmentByBase64(
			commerceOrder.getCommerceOrderId(), attachmentBase64);
	}

	private Attachment _toAttachment(FileEntry fileEntry) {
		return new Attachment() {
			{
				setExternalReferenceCode(fileEntry::getExternalReferenceCode);
				setId(fileEntry::getFileEntryId);
				setTitle(fileEntry::getTitle);
				setUrl(
					() -> DLURLHelperUtil.getDownloadURL(
						fileEntry, fileEntry.getLatestFileVersion(), null,
						StringPool.BLANK, true, true));
			}
		};
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

}