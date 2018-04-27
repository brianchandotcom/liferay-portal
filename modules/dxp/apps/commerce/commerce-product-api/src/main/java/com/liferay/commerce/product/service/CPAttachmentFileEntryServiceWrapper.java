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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPAttachmentFileEntryService}.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryService
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryServiceWrapper
	implements CPAttachmentFileEntryService,
		ServiceWrapper<CPAttachmentFileEntryService> {
	public CPAttachmentFileEntryServiceWrapper(
		CPAttachmentFileEntryService cpAttachmentFileEntryService) {
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		long classNameId, long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.addCPAttachmentFileEntry(classNameId,
			classPK, fileEntryId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, titleMap,
			json, priority, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntry cpAttachmentFileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.deleteCPAttachmentFileEntry(cpAttachmentFileEntry);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.deleteCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.fetchCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.getCPAttachmentFileEntries(classNameId,
			classPK, type, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.getCPAttachmentFileEntries(classNameId,
			classPK, type, status, start, end, orderByComparator);
	}

	@Override
	public int getCPAttachmentFileEntriesCount(long classNameId, long classPK,
		int type, int status) {
		return _cpAttachmentFileEntryService.getCPAttachmentFileEntriesCount(classNameId,
			classPK, type, status);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry getCPAttachmentFileEntry(
		long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.getCPAttachmentFileEntry(cpAttachmentFileEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpAttachmentFileEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, String> titleMap, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.updateCPAttachmentFileEntry(cpAttachmentFileEntryId,
			fileEntryId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, titleMap, json, priority, type,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry updateStatus(
		long userId, long cpAttachmentFileEntryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryService.updateStatus(userId,
			cpAttachmentFileEntryId, status, serviceContext, workflowContext);
	}

	@Override
	public CPAttachmentFileEntryService getWrappedService() {
		return _cpAttachmentFileEntryService;
	}

	@Override
	public void setWrappedService(
		CPAttachmentFileEntryService cpAttachmentFileEntryService) {
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
	}

	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;
}