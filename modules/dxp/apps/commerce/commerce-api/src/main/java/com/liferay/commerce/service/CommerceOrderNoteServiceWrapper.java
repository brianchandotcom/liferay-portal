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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderNoteService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteService
 * @generated
 */
@ProviderType
public class CommerceOrderNoteServiceWrapper implements CommerceOrderNoteService,
	ServiceWrapper<CommerceOrderNoteService> {
	public CommerceOrderNoteServiceWrapper(
		CommerceOrderNoteService commerceOrderNoteService) {
		_commerceOrderNoteService = commerceOrderNoteService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote addCommerceOrderNote(
		long commerceOrderId, String content, boolean restricted,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.addCommerceOrderNote(commerceOrderId,
			content, restricted, serviceContext);
	}

	@Override
	public void deleteCommerceOrderNote(long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderNoteService.deleteCommerceOrderNote(commerceOrderNoteId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderNote getCommerceOrderNote(
		long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.getCommerceOrderNote(commerceOrderNoteId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.getCommerceOrderNotes(commerceOrderId,
			restricted);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.getCommerceOrderNotes(commerceOrderId,
			start, end);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.getCommerceOrderNotesCount(commerceOrderId);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId,
		boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderNoteService.getCommerceOrderNotesCount(commerceOrderId,
			restricted);
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
		return _commerceOrderNoteService.updateCommerceOrderNote(commerceOrderNoteId,
			content, restricted);
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