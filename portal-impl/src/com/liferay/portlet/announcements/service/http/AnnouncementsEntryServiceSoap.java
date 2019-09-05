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

package com.liferay.portlet.announcements.service.http;

import com.liferay.announcements.kernel.service.AnnouncementsEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AnnouncementsEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.announcements.kernel.model.AnnouncementsEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.announcements.kernel.model.AnnouncementsEntry</code>, that is translated to a
 * <code>com.liferay.announcements.kernel.model.AnnouncementsEntrySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @see AnnouncementsEntryServiceHttp
 * @generated
 */
public class AnnouncementsEntryServiceSoap {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addEntry(long,
	 long, String, String, String, String, Date, Date, int,
	 boolean)}
	 */
	@Deprecated
	public static com.liferay.announcements.kernel.model.AnnouncementsEntrySoap
			addEntry(
				long plid, long classNameId, long classPK, String title,
				String content, String url, String type, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, boolean displayImmediately,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, int priority, boolean alert)
		throws RemoteException {

		try {
			com.liferay.announcements.kernel.model.AnnouncementsEntry
				returnValue = AnnouncementsEntryServiceUtil.addEntry(
					plid, classNameId, classPK, title, content, url, type,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, displayImmediately,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, priority, alert);

			return com.liferay.announcements.kernel.model.
				AnnouncementsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntrySoap
			addEntry(
				long classNameId, long classPK, String title, String content,
				String url, String type, java.util.Date displayDate,
				java.util.Date expirationDate, int priority, boolean alert)
		throws RemoteException {

		try {
			com.liferay.announcements.kernel.model.AnnouncementsEntry
				returnValue = AnnouncementsEntryServiceUtil.addEntry(
					classNameId, classPK, title, content, url, type,
					displayDate, expirationDate, priority, alert);

			return com.liferay.announcements.kernel.model.
				AnnouncementsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			AnnouncementsEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntrySoap
			getEntry(long entryId)
		throws RemoteException {

		try {
			com.liferay.announcements.kernel.model.AnnouncementsEntry
				returnValue = AnnouncementsEntryServiceUtil.getEntry(entryId);

			return com.liferay.announcements.kernel.model.
				AnnouncementsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntrySoap
			updateEntry(
				long entryId, String title, String content, String url,
				String type, java.util.Date displayDate,
				java.util.Date expirationDate, int priority)
		throws RemoteException {

		try {
			com.liferay.announcements.kernel.model.AnnouncementsEntry
				returnValue = AnnouncementsEntryServiceUtil.updateEntry(
					entryId, title, content, url, type, displayDate,
					expirationDate, priority);

			return com.liferay.announcements.kernel.model.
				AnnouncementsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateEntry(long,
	 String, String, String, String, Date, Date, int)}
	 */
	@Deprecated
	public static com.liferay.announcements.kernel.model.AnnouncementsEntrySoap
			updateEntry(
				long entryId, String title, String content, String url,
				String type, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				boolean displayImmediately, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute, int priority)
		throws RemoteException {

		try {
			com.liferay.announcements.kernel.model.AnnouncementsEntry
				returnValue = AnnouncementsEntryServiceUtil.updateEntry(
					entryId, title, content, url, type, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, displayImmediately, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, priority);

			return com.liferay.announcements.kernel.model.
				AnnouncementsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryServiceSoap.class);

}