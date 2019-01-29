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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see EntryService
 * @generated
 */
@ProviderType
public class EntryServiceWrapper implements EntryService,
	ServiceWrapper<EntryService> {
	public EntryServiceWrapper(EntryService entryService) {
		_entryService = entryService;
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Entry addEntry(
		long groupId, long definitionId, String format,
		boolean schedulerRequest, java.util.Date startDate,
		java.util.Date endDate, boolean repeating, String recurrence,
		String emailNotifications, String emailDelivery, String portletId,
		String pageURL, String reportName, String reportParameters,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.addEntry(groupId, definitionId, format,
			schedulerRequest, startDate, endDate, repeating, recurrence,
			emailNotifications, emailDelivery, portletId, pageURL, reportName,
			reportParameters, serviceContext);
	}

	@Override
	public void deleteAttachment(long companyId, long entryId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_entryService.deleteAttachment(companyId, entryId, fileName);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Entry deleteEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.deleteEntry(entryId);
	}

	@Override
	public java.util.List<com.liferay.portal.reports.engine.console.model.Entry> getEntries(
		long groupId, String definitionName, String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.getEntries(groupId, definitionName, userName,
			createDateGT, createDateLT, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getEntriesCount(long groupId, String definitionName,
		String userName, java.util.Date createDateGT,
		java.util.Date createDateLT, boolean andSearch)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.getEntriesCount(groupId, definitionName, userName,
			createDateGT, createDateLT, andSearch);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _entryService.getOSGiServiceIdentifier();
	}

	@Override
	public void sendEmails(long entryId, String fileName,
		String[] emailAddresses, boolean notification)
		throws com.liferay.portal.kernel.exception.PortalException {
		_entryService.sendEmails(entryId, fileName, emailAddresses, notification);
	}

	@Override
	public void unscheduleEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_entryService.unscheduleEntry(entryId);
	}

	@Override
	public EntryService getWrappedService() {
		return _entryService;
	}

	@Override
	public void setWrappedService(EntryService entryService) {
		_entryService = entryService;
	}

	private EntryService _entryService;
}