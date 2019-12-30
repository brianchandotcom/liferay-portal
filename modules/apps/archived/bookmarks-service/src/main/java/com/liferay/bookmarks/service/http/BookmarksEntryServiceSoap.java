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

package com.liferay.bookmarks.service.http;

import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>BookmarksEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.bookmarks.model.BookmarksEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.bookmarks.model.BookmarksEntry</code>, that is translated to a
 * <code>com.liferay.bookmarks.model.BookmarksEntrySoap</code>. Methods that SOAP
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
 * @see BookmarksEntryServiceHttp
 * @generated
 */
public class BookmarksEntryServiceSoap {

	public static com.liferay.bookmarks.model.BookmarksEntrySoap addEntry(
			long groupId, long folderId, String name, String url,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.addEntry(
					groupId, folderId, name, url, description, serviceContext);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			BookmarksEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap[] getEntries(
			long groupId, long folderId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.bookmarks.model.BookmarksEntry>
				returnValue = BookmarksEntryServiceUtil.getEntries(
					groupId, folderId, start, end);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap[] getEntries(
			long groupId, long folderId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.bookmarks.model.BookmarksEntry>
				returnValue = BookmarksEntryServiceUtil.getEntries(
					groupId, folderId, start, end, orderByComparator);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(long groupId, long folderId)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getEntriesCount(
				groupId, folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(long groupId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getEntriesCount(
				groupId, folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap getEntry(
			long entryId)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.getEntry(entryId);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersEntriesCount(long groupId, Long[] folderIds)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getFoldersEntriesCount(
				groupId, ListUtil.toList(folderIds));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap[]
			getGroupEntries(long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.bookmarks.model.BookmarksEntry>
				returnValue = BookmarksEntryServiceUtil.getGroupEntries(
					groupId, start, end);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap[]
			getGroupEntries(long groupId, long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.bookmarks.model.BookmarksEntry>
				returnValue = BookmarksEntryServiceUtil.getGroupEntries(
					groupId, userId, start, end);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap[]
			getGroupEntries(
				long groupId, long userId, long rootFolderId, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.bookmarks.model.BookmarksEntry>
				returnValue = BookmarksEntryServiceUtil.getGroupEntries(
					groupId, userId, rootFolderId, start, end);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupEntriesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getGroupEntriesCount(
				groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupEntriesCount(long groupId, long userId)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getGroupEntriesCount(
				groupId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws RemoteException {

		try {
			int returnValue = BookmarksEntryServiceUtil.getGroupEntriesCount(
				groupId, userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap moveEntry(
			long entryId, long parentFolderId)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.moveEntry(entryId, parentFolderId);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap
			moveEntryFromTrash(long entryId, long parentFolderId)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.moveEntryFromTrash(
					entryId, parentFolderId);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap
			moveEntryToTrash(long entryId)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.moveEntryToTrash(entryId);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap openEntry(
			com.liferay.bookmarks.model.BookmarksEntrySoap entry)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.openEntry(
					com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl.
						toModel(entry));

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap openEntry(
			long entryId)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.openEntry(entryId);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreEntryFromTrash(long entryId)
		throws RemoteException {

		try {
			BookmarksEntryServiceUtil.restoreEntryFromTrash(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeEntry(long entryId) throws RemoteException {
		try {
			BookmarksEntryServiceUtil.subscribeEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeEntry(long entryId) throws RemoteException {
		try {
			BookmarksEntryServiceUtil.unsubscribeEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.bookmarks.model.BookmarksEntrySoap updateEntry(
			long entryId, long groupId, long folderId, String name, String url,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.bookmarks.model.BookmarksEntry returnValue =
				BookmarksEntryServiceUtil.updateEntry(
					entryId, groupId, folderId, name, url, description,
					serviceContext);

			return com.liferay.bookmarks.model.BookmarksEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BookmarksEntryServiceSoap.class);

}