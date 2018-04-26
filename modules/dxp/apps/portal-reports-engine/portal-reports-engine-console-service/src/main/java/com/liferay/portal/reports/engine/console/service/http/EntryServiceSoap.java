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

package com.liferay.portal.reports.engine.console.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.reports.engine.console.service.EntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link EntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.reports.engine.console.model.EntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.reports.engine.console.model.Entry}, that is translated to a
 * {@link com.liferay.portal.reports.engine.console.model.EntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryServiceHttp
 * @see com.liferay.portal.reports.engine.console.model.EntrySoap
 * @see EntryServiceUtil
 * @generated
 */
@ProviderType
public class EntryServiceSoap {
	public static com.liferay.portal.reports.engine.console.model.EntrySoap addEntry(
		long groupId, long definitionId, String format,
		boolean schedulerRequest, java.util.Date startDate,
		java.util.Date endDate, boolean repeating, String recurrence,
		String emailNotifications, String emailDelivery, String portletId,
		String pageURL, String reportName, String reportParameters,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.reports.engine.console.model.Entry returnValue = EntryServiceUtil.addEntry(groupId,
					definitionId, format, schedulerRequest, startDate, endDate,
					repeating, recurrence, emailNotifications, emailDelivery,
					portletId, pageURL, reportName, reportParameters,
					serviceContext);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAttachment(long companyId, long entryId,
		String fileName) throws RemoteException {
		try {
			EntryServiceUtil.deleteAttachment(companyId, entryId, fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.EntrySoap deleteEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portal.reports.engine.console.model.Entry returnValue = EntryServiceUtil.deleteEntry(entryId);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.EntrySoap[] getEntries(
		long groupId, String definitionName, String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.reports.engine.console.model.Entry> returnValue =
				EntryServiceUtil.getEntries(groupId, definitionName, userName,
					createDateGT, createDateLT, andSearch, start, end,
					orderByComparator);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(long groupId, String definitionName,
		String userName, java.util.Date createDateGT,
		java.util.Date createDateLT, boolean andSearch)
		throws RemoteException {
		try {
			int returnValue = EntryServiceUtil.getEntriesCount(groupId,
					definitionName, userName, createDateGT, createDateLT,
					andSearch);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void sendEmails(long entryId, String fileName,
		String[] emailAddresses, boolean notification)
		throws RemoteException {
		try {
			EntryServiceUtil.sendEmails(entryId, fileName, emailAddresses,
				notification);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unscheduleEntry(long entryId) throws RemoteException {
		try {
			EntryServiceUtil.unscheduleEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntryServiceSoap.class);
}