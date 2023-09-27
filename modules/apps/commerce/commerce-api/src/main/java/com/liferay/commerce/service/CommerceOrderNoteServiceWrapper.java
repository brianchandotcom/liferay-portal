/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderNoteService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteService
 * @generated
 */
public class CommerceOrderNoteServiceWrapper
	implements CommerceOrderNoteService,
			   ServiceWrapper<CommerceOrderNoteService> {

	public CommerceOrderNoteServiceWrapper() {
		this(null);
	}

	public CommerceOrderNoteServiceWrapper(
		CommerceOrderNoteService commerceOrderNoteService) {

		_commerceOrderNoteService = commerceOrderNoteService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote addCommerceOrderNote(
			long commerceOrderId, String content, boolean restricted,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.addCommerceOrderNote(
			commerceOrderId, content, restricted, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote
			addOrUpdateCommerceOrderNote(
				String externalReferenceCode, long commerceOrderNoteId,
				long commerceOrderId, String content, boolean restricted,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.addOrUpdateCommerceOrderNote(
			externalReferenceCode, commerceOrderNoteId, commerceOrderId,
			content, restricted, serviceContext);
	}

	@Override
	public void deleteCommerceOrderNote(long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderNoteService.deleteCommerceOrderNote(commerceOrderNoteId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote fetchCommerceOrderNote(
			long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.fetchCommerceOrderNote(
			commerceOrderNoteId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote getCommerceOrderNote(
			long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNote(
			commerceOrderNoteId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderNote>
			getCommerceOrderNotes(long commerceOrderId, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, restricted);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderNote>
			getCommerceOrderNotes(
				long commerceOrderId, boolean restricted, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, restricted, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderNote>
			getCommerceOrderNotes(long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderNotesCount(
			long commerceOrderId, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrderId, restricted);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderNoteService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote updateCommerceOrderNote(
			long commerceOrderNoteId, String content, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderNoteService.updateCommerceOrderNote(
			commerceOrderNoteId, content, restricted);
	}

	@Override
	public CommerceOrderNoteService getWrappedService() {
		return _commerceOrderNoteService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderNoteService commerceOrderNoteService) {

		_commerceOrderNoteService = commerceOrderNoteService;
	}

	private CommerceOrderNoteService _commerceOrderNoteService;

}