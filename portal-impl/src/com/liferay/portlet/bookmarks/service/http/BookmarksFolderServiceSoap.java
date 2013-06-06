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

package com.liferay.portlet.bookmarks.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.bookmarks.model.BookmarksFolderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.bookmarks.model.BookmarksFolder}, that is translated to a
 * {@link com.liferay.portlet.bookmarks.model.BookmarksFolderSoap}. Methods that SOAP cannot
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
 * @see BookmarksFolderServiceHttp
 * @see com.liferay.portlet.bookmarks.model.BookmarksFolderSoap
 * @see com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil
 * @generated
 */
public class BookmarksFolderServiceSoap {
	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.addFolder(parentFolderId,
					name, description, serviceContext);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			BookmarksFolderServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.deleteFolder(folderId,
				includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.getFolder(folderId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getFolderIds(long groupId, long folderId)
		throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = BookmarksFolderServiceUtil.getFolderIds(groupId,
					folderId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap[] getFolders(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> returnValue =
				BookmarksFolderServiceUtil.getFolders(groupId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap[] getFolders(
		long groupId, long parentFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> returnValue =
				BookmarksFolderServiceUtil.getFolders(groupId, parentFolderId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap[] getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> returnValue =
				BookmarksFolderServiceUtil.getFolders(groupId, parentFolderId,
					start, end);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap[] getFolders(
		long groupId, long parentFolderId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> returnValue =
				BookmarksFolderServiceUtil.getFolders(groupId, parentFolderId,
					status, start, end);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndEntriesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = BookmarksFolderServiceUtil.getFoldersAndEntriesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndEntriesCount(long groupId, long folderId,
		int status) throws RemoteException {
		try {
			int returnValue = BookmarksFolderServiceUtil.getFoldersAndEntriesCount(groupId,
					folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = BookmarksFolderServiceUtil.getFoldersCount(groupId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		int status) throws RemoteException {
		try {
			int returnValue = BookmarksFolderServiceUtil.getFoldersCount(groupId,
					parentFolderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(Long[] folderIds, long groupId,
		long folderId) throws RemoteException {
		try {
			BookmarksFolderServiceUtil.getSubfolderIds(ListUtil.toList(
					folderIds), groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long groupId, long folderId,
		boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = BookmarksFolderServiceUtil.getSubfolderIds(groupId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap moveFolder(
		long folderId, long parentFolderId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.moveFolder(folderId,
					parentFolderId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap moveFolderFromTrash(
		long folderId, long parentFolderId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.moveFolderFromTrash(folderId,
					parentFolderId);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void moveFolderToTrash(long folderId)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.moveFolderToTrash(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreFolderFromTrash(long folderId)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.restoreFolderFromTrash(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeFolder(long groupId, long folderId)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.subscribeFolder(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeFolder(long groupId, long folderId)
		throws RemoteException {
		try {
			BookmarksFolderServiceUtil.unsubscribeFolder(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolderSoap updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksFolder returnValue = BookmarksFolderServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, mergeWithParentFolder,
					serviceContext);

			return com.liferay.portlet.bookmarks.model.BookmarksFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksFolderServiceSoap.class);
}