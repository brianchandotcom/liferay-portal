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

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.base.EntryServiceBaseImpl;
import com.liferay.portal.reports.engine.console.service.permission.DefinitionPermissionChecker;
import com.liferay.portal.reports.engine.console.service.permission.EntryPermissionChecker;
import com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys;

import java.util.Date;
import java.util.List;

/**
 * @author Gavin Wan
 */
public class EntryServiceImpl extends EntryServiceBaseImpl {

	@Override
	public Entry addEntry(
			long groupId, long definitionId, String format,
			boolean schedulerRequest, Date startDate, Date endDate,
			boolean repeating, String recurrence, String emailNotifications,
			String emailDelivery, String portletId, String pageURL,
			String reportName, String reportParameters,
			ServiceContext serviceContext)
		throws PortalException {

		DefinitionPermissionChecker.check(
			getPermissionChecker(), definitionId, ReportsActionKeys.ADD_REPORT);

		return entryLocalService.addEntry(
			getUserId(), groupId, definitionId, format, schedulerRequest,
			startDate, endDate, repeating, recurrence, emailNotifications,
			emailDelivery, portletId, pageURL, reportName, reportParameters,
			serviceContext);
	}

	@Override
	public void deleteAttachment(long companyId, long entryId, String fileName)
		throws PortalException {

		EntryPermissionChecker.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		entryLocalService.deleteAttachment(companyId, fileName);
	}

	@Override
	public Entry deleteEntry(long entryId) throws PortalException {
		EntryPermissionChecker.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		return entryLocalService.deleteEntry(entryId);
	}

	@Override
	public List<Entry> getEntries(
			long groupId, String definitionName, String userName,
			Date createDateGT, Date createDateLT, boolean andSearch, int start,
			int end, OrderByComparator orderByComparator)
		throws PortalException {

		return entryFinder.filterFindByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch, start, end, orderByComparator);
	}

	@Override
	public int getEntriesCount(
			long groupId, String definitionName, String userName,
			Date createDateGT, Date createDateLT, boolean andSearch)
		throws PortalException {

		return entryFinder.filterCountByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch);
	}

	@Override
	public void sendEmails(
			long entryId, String fileName, String[] emailAddresses,
			boolean notification)
		throws PortalException {

		EntryPermissionChecker.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		entryLocalService.sendEmails(
			entryId, fileName, emailAddresses, notification);
	}

	@Override
	public void unscheduleEntry(long entryId) throws PortalException {
		EntryPermissionChecker.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		entryLocalService.unscheduleEntry(entryId);
	}

}