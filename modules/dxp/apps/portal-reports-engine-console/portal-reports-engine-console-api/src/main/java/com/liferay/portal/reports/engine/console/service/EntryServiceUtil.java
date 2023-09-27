/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Entry;

import java.util.List;

/**
 * Provides the remote service utility for Entry. This utility wraps
 * <code>com.liferay.portal.reports.engine.console.service.impl.EntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EntryService
 * @generated
 */
public class EntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.reports.engine.console.service.impl.EntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Entry addEntry(
			long groupId, long definitionId, String format,
			boolean schedulerRequest, java.util.Date startDate,
			java.util.Date endDate, boolean repeating, String recurrence,
			String emailNotifications, String emailDelivery, String portletId,
			String pageURL, String reportName, String reportParameters,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addEntry(
			groupId, definitionId, format, schedulerRequest, startDate, endDate,
			repeating, recurrence, emailNotifications, emailDelivery, portletId,
			pageURL, reportName, reportParameters, serviceContext);
	}

	public static void deleteAttachment(
			long companyId, long entryId, String fileName)
		throws PortalException {

		getService().deleteAttachment(companyId, entryId, fileName);
	}

	public static Entry deleteEntry(long entryId) throws PortalException {
		return getService().deleteEntry(entryId);
	}

	public static List<Entry> getEntries(
			long groupId, String definitionName, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			boolean andSearch, int start, int end,
			OrderByComparator<Entry> orderByComparator)
		throws PortalException {

		return getService().getEntries(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch, start, end, orderByComparator);
	}

	public static int getEntriesCount(
			long groupId, String definitionName, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			boolean andSearch)
		throws PortalException {

		return getService().getEntriesCount(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andSearch);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void sendEmails(
			long entryId, String fileName, String[] emailAddresses,
			boolean notification)
		throws PortalException {

		getService().sendEmails(
			entryId, fileName, emailAddresses, notification);
	}

	public static void unscheduleEntry(long entryId) throws PortalException {
		getService().unscheduleEntry(entryId);
	}

	public static EntryService getService() {
		return _service;
	}

	public static void setService(EntryService service) {
		_service = service;
	}

	private static volatile EntryService _service;

}