/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLAppServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLAppServiceHttp
 * @see       com.liferay.portlet.documentlibrary.service.DLAppServiceUtil
 * @generated
 */
public class DLAppServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap addFileShortcut(
		long repositoryId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.addFileShortcut(repositoryId, folderId,
					toFileEntryId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void cancelCheckOut(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.cancelCheckOut(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkInFileEntry(long fileEntryId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLAppServiceUtil.checkInFileEntry(fileEntryId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkInFileEntry(long fileEntryId, boolean major,
		java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkInFileEntry(fileEntryId, major, changeLog,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkOutFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long fileEntryId)
		throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntryByTitle(long repositoryId, long folderId,
		java.lang.String title) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileEntryByTitle(repositoryId, folderId,
				title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws RemoteException {
		try {
			DLAppServiceUtil.deleteFileShortcut(fileShortcutId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long repositoryId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			DLAppServiceUtil.deleteFolder(repositoryId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(long repositoryId,
		long folderId, int status) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(long repositoryId, long folderId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesCount(repositoryId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(long repositoryId, long folderId,
		long fileEntryTypeId) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFileEntriesCount(repositoryId,
					folderId, fileEntryTypeId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap getFileShortcut(
		long fileShortcutId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.getFileShortcut(fileShortcutId);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long repositoryId, long folderId, int status,
		boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId,
					folderId, status, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long repositoryId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersCount(repositoryId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long repositoryId, long parentFolderId,
		boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersCount(repositoryId,
					parentFolderId, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersFileEntriesCount(long repositoryId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getFoldersFileEntriesCount(repositoryId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(long repositoryId, long userId)
		throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(long repositoryId, long userId,
		long rootFolderId) throws RemoteException {
		try {
			int returnValue = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId,
					userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long repositoryId,
		long folderId) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLAppServiceUtil.getSubfolderIds(repositoryId,
					folderId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long repositoryId,
		long folderId, boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLAppServiceUtil.getSubfolderIds(repositoryId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertFileEntry(long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DLAppServiceUtil.revertFileEntry(fileEntryId, version,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long repositoryId, long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLAppServiceUtil.unlockFolder(repositoryId, folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLAppServiceUtil.unlockFolder(repositoryId, parentFolderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap updateFileShortcut(
		long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileShortcut returnValue =
				DLAppServiceUtil.updateFileShortcut(fileShortcutId, folderId,
					toFileEntryId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryCheckOut(long repositoryId,
		long fileEntryId, java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLAppServiceUtil.verifyFileEntryCheckOut(repositoryId,
					fileEntryId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyInheritableLock(long repositoryId,
		long folderId, java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLAppServiceUtil.verifyInheritableLock(repositoryId,
					folderId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceSoap.class);
}