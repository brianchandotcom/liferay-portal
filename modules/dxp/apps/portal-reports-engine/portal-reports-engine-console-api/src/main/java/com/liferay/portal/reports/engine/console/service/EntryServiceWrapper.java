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
		long groupId, long definitionId, java.lang.String format,
		boolean schedulerRequest, java.util.Date startDate,
		java.util.Date endDate, boolean repeating, java.lang.String recurrence,
		java.lang.String emailNotifications, java.lang.String emailDelivery,
		java.lang.String portletId, java.lang.String pageURL,
		java.lang.String reportName, java.lang.String reportParameters,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.addEntry(groupId, definitionId, format,
			schedulerRequest, startDate, endDate, repeating, recurrence,
			emailNotifications, emailDelivery, portletId, pageURL, reportName,
			reportParameters, serviceContext);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Entry deleteEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.deleteEntry(entryId);
	}

	@Override
	public int getEntriesCount(long groupId, java.lang.String definitionName,
		java.lang.String userName, java.util.Date createDateGT,
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _entryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.reports.engine.console.model.Entry> getEntries(
		long groupId, java.lang.String definitionName,
		java.lang.String userName, java.util.Date createDateGT,
		java.util.Date createDateLT, boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entryService.getEntries(groupId, definitionName, userName,
			createDateGT, createDateLT, andSearch, start, end, orderByComparator);
	}

	@Override
	public void deleteAttachment(long companyId, long entryId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_entryService.deleteAttachment(companyId, entryId, fileName);
	}

	@Override
	public void sendEmails(long entryId, java.lang.String fileName,
		java.lang.String[] emailAddresses, boolean notification)
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