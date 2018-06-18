/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	public void deleteCPAttachmentFileEntry(long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpAttachmentFileEntryService.deleteCPAttachmentFileEntry(cpAttachmentFileEntryId);
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