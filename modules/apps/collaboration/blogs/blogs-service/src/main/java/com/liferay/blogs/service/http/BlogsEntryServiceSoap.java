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

package com.liferay.blogs.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blogs.service.BlogsEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link BlogsEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * @see BlogsEntryServiceHttp
 * @see BlogsEntryServiceUtil
 * @generated
 */
@ProviderType
public class BlogsEntryServiceSoap {
	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.blogs.kernel.model.BlogsEntry returnValue = BlogsEntryServiceUtil.addEntry(title,
					subtitle, description, content, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, allowPingbacks, allowTrackbacks,
					trackbacks, coverImageCaption, coverImageImageSelector,
					smallImageImageSelector, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getCompanyEntries(companyId, displayDate,
					status, max);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.blogs.kernel.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(entryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry getEntry(
		long groupId, java.lang.String urlTitle) throws RemoteException {
		try {
			com.liferay.blogs.kernel.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(groupId,
					urlTitle);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, displayDate,
					status, max);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, displayDate,
					status, start, end);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupEntries(
		long groupId, int status, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, max);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupEntries(
		long groupId, int status, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, start,
					end);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsEntry> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, start,
					end, obc);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupEntriesCount(long groupId,
		java.util.Date displayDate, int status) throws RemoteException {
		try {
			int returnValue = BlogsEntryServiceUtil.getGroupEntriesCount(groupId,
					displayDate, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupEntriesCount(long groupId, int status)
		throws RemoteException {
		try {
			int returnValue = BlogsEntryServiceUtil.getGroupEntriesCount(groupId,
					status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int max) throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupsEntries(companyId, groupId,
					displayDate, status, max);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsEntry> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupUserEntries(groupId, userId,
					status, start, end, obc);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getGroupUserEntries(
		long groupId, long userId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsEntry> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupUserEntries(groupId, userId,
					statuses, start, end, obc);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupUserEntriesCount(long groupId, long userId,
		int status) throws RemoteException {
		try {
			int returnValue = BlogsEntryServiceUtil.getGroupUserEntriesCount(groupId,
					userId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupUserEntriesCount(long groupId, long userId,
		int[] statuses) throws RemoteException {
		try {
			int returnValue = BlogsEntryServiceUtil.getGroupUserEntriesCount(groupId,
					userId, statuses);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getOrganizationEntries(organizationId,
					displayDate, status, max);

			return returnValue.toArray(new com.liferay.blogs.kernel.model.BlogsEntry[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry moveEntryToTrash(
		long entryId) throws RemoteException {
		try {
			com.liferay.blogs.kernel.model.BlogsEntry returnValue = BlogsEntryServiceUtil.moveEntryToTrash(entryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreEntryFromTrash(long entryId)
		throws RemoteException {
		try {
			BlogsEntryServiceUtil.restoreEntryFromTrash(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribe(long groupId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.subscribe(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribe(long groupId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.unsubscribe(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.blogs.kernel.model.BlogsEntry returnValue = BlogsEntryServiceUtil.updateEntry(entryId,
					title, subtitle, description, content, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, allowPingbacks, allowTrackbacks,
					trackbacks, coverImageCaption, coverImageImageSelector,
					smallImageImageSelector, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BlogsEntryServiceSoap.class);
}