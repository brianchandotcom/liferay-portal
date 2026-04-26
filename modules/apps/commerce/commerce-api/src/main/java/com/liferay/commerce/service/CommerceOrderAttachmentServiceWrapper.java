/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderAttachmentService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachmentService
 * @generated
 */
public class CommerceOrderAttachmentServiceWrapper
	implements CommerceOrderAttachmentService,
			   ServiceWrapper<CommerceOrderAttachmentService> {

	public CommerceOrderAttachmentServiceWrapper() {
		this(null);
	}

	public CommerceOrderAttachmentServiceWrapper(
		CommerceOrderAttachmentService commerceOrderAttachmentService) {

		_commerceOrderAttachmentService = commerceOrderAttachmentService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderAttachment
			addCommerceOrderAttachment(
				long commerceOrderId, double priority, boolean restricted,
				String title, String type, String fileName,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.addCommerceOrderAttachment(
			commerceOrderId, priority, restricted, title, type, fileName,
			inputStream);
	}

	@Override
	public void deleteCommerceOrderAttachment(long commerceOrderAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderAttachmentService.deleteCommerceOrderAttachment(
			commerceOrderAttachmentId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderAttachment
			fetchCommerceOrderAttachment(long commerceOrderAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.fetchCommerceOrderAttachment(
			commerceOrderAttachmentId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderAttachment
			fetchCommerceOrderAttachmentByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.
			fetchCommerceOrderAttachmentByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderAttachment
			getCommerceOrderAttachment(long commerceOrderAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.getCommerceOrderAttachment(
			commerceOrderAttachmentId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderAttachment>
			getCommerceOrderAttachments(
				long commerceOrderId, boolean restricted, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrderAttachment>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.getCommerceOrderAttachments(
			commerceOrderId, restricted, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderAttachment>
			getCommerceOrderAttachments(
				long commerceOrderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrderAttachment>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.getCommerceOrderAttachments(
			commerceOrderId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderAttachmentsCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.getCommerceOrderAttachmentsCount(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderAttachmentsCount(
			long commerceOrderId, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.getCommerceOrderAttachmentsCount(
			commerceOrderId, restricted);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderAttachmentService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderAttachment
			updateCommerceOrderAttachment(
				long commerceOrderAttachmentId, double priority,
				boolean restricted, String title, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderAttachmentService.updateCommerceOrderAttachment(
			commerceOrderAttachmentId, priority, restricted, title, type);
	}

	@Override
	public CommerceOrderAttachmentService getWrappedService() {
		return _commerceOrderAttachmentService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderAttachmentService commerceOrderAttachmentService) {

		_commerceOrderAttachmentService = commerceOrderAttachmentService;
	}

	private CommerceOrderAttachmentService _commerceOrderAttachmentService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1471457876