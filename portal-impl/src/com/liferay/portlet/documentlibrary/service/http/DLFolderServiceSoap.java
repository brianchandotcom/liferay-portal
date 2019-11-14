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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DLFolderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.document.library.kernel.model.DLFolderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.document.library.kernel.model.DLFolder</code>, that is translated to a
 * <code>com.liferay.document.library.kernel.model.DLFolderSoap</code>. Methods that SOAP
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
 * @see DLFolderServiceHttp
 * @generated
 */
public class DLFolderServiceSoap {

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			addFolder(
				long groupId, long repositoryId, boolean mountPoint,
				long parentFolderId, String name, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.addFolder(
					groupId, repositoryId, mountPoint, parentFolderId, name,
					description, serviceContext);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			DLFolderServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws RemoteException {

		try {
			DLFolderServiceUtil.deleteFolder(folderId, includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(
			long groupId, long parentFolderId, String name)
		throws RemoteException {

		try {
			DLFolderServiceUtil.deleteFolder(groupId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.getFileEntriesAndFileShortcutsCount(
					groupId, folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.getFileEntriesAndFileShortcutsCount(
					groupId, folderId, status, mimeTypes);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			getFolder(long folderId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.getFolder(folderId);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			getFolder(long groupId, long parentFolderId, String name)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.getFolder(groupId, parentFolderId, name);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static Long[] getFolderIds(long groupId, long folderId)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue = DLFolderServiceUtil.getFolderIds(
				groupId, folderId);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap[]
			getFolders(
				long groupId, long parentFolderId, int status,
				boolean includeMountfolders, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.document.library.kernel.model.DLFolder>
				returnValue = DLFolderServiceUtil.getFolders(
					groupId, parentFolderId, status, includeMountfolders, start,
					end, obc);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap[]
			getFolders(
				long groupId, long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.document.library.kernel.model.DLFolder>
				returnValue = DLFolderServiceUtil.getFolders(
					groupId, parentFolderId, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.
					getFoldersAndFileEntriesAndFileShortcutsCount(
						groupId, folderId, status, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.
					getFoldersAndFileEntriesAndFileShortcutsCount(
						groupId, folderId, status, mimeTypes,
						includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.
					getFoldersAndFileEntriesAndFileShortcutsCount(
						groupId, folderId, mimeTypes, includeMountFolders,
						queryDefinition);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			long fileEntryTypeId, boolean includeMountFolders, int status)
		throws RemoteException {

		try {
			int returnValue =
				DLFolderServiceUtil.
					getFoldersAndFileEntriesAndFileShortcutsCount(
						groupId, folderId, mimeTypes, fileEntryTypeId,
						includeMountFolders, status);

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
			int returnValue = DLFolderServiceUtil.getFoldersCount(
				groupId, parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders)
		throws RemoteException {

		try {
			int returnValue = DLFolderServiceUtil.getFoldersCount(
				groupId, parentFolderId, status, includeMountfolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap[]
			getMountFolders(
				long groupId, long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.document.library.kernel.model.DLFolder>
				returnValue = DLFolderServiceUtil.getMountFolders(
					groupId, parentFolderId, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getMountFoldersCount(long groupId, long parentFolderId)
		throws RemoteException {

		try {
			int returnValue = DLFolderServiceUtil.getMountFoldersCount(
				groupId, parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(
			Long[] folderIds, long groupId, long folderId, boolean recurse)
		throws RemoteException {

		try {
			DLFolderServiceUtil.getSubfolderIds(
				ListUtil.toList(folderIds), groupId, folderId, recurse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static Long[] getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue =
				DLFolderServiceUtil.getSubfolderIds(groupId, folderId, recurse);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFolderLock(long folderId) throws RemoteException {
		try {
			boolean returnValue = DLFolderServiceUtil.hasFolderLock(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasInheritableLock(long folderId)
		throws RemoteException {

		try {
			boolean returnValue = DLFolderServiceUtil.hasInheritableLock(
				folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isFolderLocked(long folderId) throws RemoteException {
		try {
			boolean returnValue = DLFolderServiceUtil.isFolderLocked(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(long folderId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.lock.Lock returnValue =
				DLFolderServiceUtil.lockFolder(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.lock.Lock returnValue =
				DLFolderServiceUtil.lockFolder(
					folderId, owner, inheritable, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			moveFolder(
				long folderId, long parentFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.moveFolder(
					folderId, parentFolderId, serviceContext);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.lock.Lock returnValue =
				DLFolderServiceUtil.refreshFolderLock(
					lockUuid, companyId, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws RemoteException {

		try {
			DLFolderServiceUtil.unlockFolder(
				groupId, parentFolderId, name, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long folderId, String lockUuid)
		throws RemoteException {

		try {
			DLFolderServiceUtil.unlockFolder(folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			updateFolder(
				long folderId, long parentFolderId, String name,
				String description, long defaultFileEntryTypeId,
				Long[] fileEntryTypeIds, int restrictionType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.updateFolder(
					folderId, parentFolderId, name, description,
					defaultFileEntryTypeId, ListUtil.toList(fileEntryTypeIds),
					restrictionType, serviceContext);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolderSoap
			updateFolder(
				long folderId, String name, String description,
				long defaultFileEntryTypeId, Long[] fileEntryTypeIds,
				int restrictionType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFolder returnValue =
				DLFolderServiceUtil.updateFolder(
					folderId, name, description, defaultFileEntryTypeId,
					ListUtil.toList(fileEntryTypeIds), restrictionType,
					serviceContext);

			return com.liferay.document.library.kernel.model.DLFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyInheritableLock(long folderId, String lockUuid)
		throws RemoteException {

		try {
			boolean returnValue = DLFolderServiceUtil.verifyInheritableLock(
				folderId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceSoap.class);

}