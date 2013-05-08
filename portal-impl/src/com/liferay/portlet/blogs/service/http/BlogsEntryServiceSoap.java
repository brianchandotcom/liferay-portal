/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.blogs.service.BlogsEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.blogs.model.BlogsEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.blogs.model.BlogsEntry}, that is translated to a
 * {@link com.liferay.portlet.blogs.model.BlogsEntrySoap}. Methods that SOAP cannot
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
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryServiceHttp
 * @see       com.liferay.portlet.blogs.model.BlogsEntrySoap
 * @see       com.liferay.portlet.blogs.service.BlogsEntryServiceUtil
 * @generated
 */
public class BlogsEntryServiceSoap {
	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getCompanyEntries(companyId, displayDate,
					status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(entryId);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap getEntry(
		long groupId, java.lang.String urlTitle) throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(groupId,
					urlTitle);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, displayDate,
					status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, displayDate,
					status, start, end);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupEntries(
		long groupId, int status, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupEntries(
		long groupId, int status, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, start,
					end);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
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

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupsEntries(companyId, groupId,
					displayDate, status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int max)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getOrganizationEntries(organizationId,
					displayDate, status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void moveEntryToTrash(long entryId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.moveEntryToTrash(entryId);
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

	private static Log _log = LogFactoryUtil.getLog(BlogsEntryServiceSoap.class);
}