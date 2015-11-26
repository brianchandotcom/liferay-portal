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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link DLFolderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.documentlibrary.model.DLFolderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.documentlibrary.model.DLFolder}, that is translated to a
 * {@link com.liferay.portlet.documentlibrary.model.DLFolderSoap}. Methods that SOAP cannot
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
 * @see DLFolderServiceHttp
 * @see com.liferay.portlet.documentlibrary.model.DLFolderSoap
 * @see DLFolderServiceUtil
 * @generated
 */
@ProviderType
public class DLFolderServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap addFolder(
		long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.addFolder(groupId,
					repositoryId, mountPoint, parentFolderId, name,
					description, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
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

	public static void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws RemoteException {
		try {
			DLFolderServiceUtil.deleteFolder(folderId, includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			DLFolderServiceUtil.deleteFolder(groupId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status) throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getFileEntriesAndFileShortcutsCount(groupId,
					folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes)
		throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getFileEntriesAndFileShortcutsCount(groupId,
					folderId, status, mimeTypes);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.getFolder(folderId);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.getFolder(groupId,
					parentFolderId, name);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getFolderIds(long groupId, long folderId)
		throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLFolderServiceUtil.getFolderIds(groupId,
					folderId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap[] getFolders(
		long groupId, long parentFolderId, int status,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> returnValue =
				DLFolderServiceUtil.getFolders(groupId, parentFolderId, status,
					includeMountfolders, start, end, obc);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap[] getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> returnValue =
				DLFolderServiceUtil.getFolders(groupId, parentFolderId, start,
					end, obc);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, boolean includeMountFolders)
		throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
					folderId, status, includeMountFolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders) throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
					folderId, status, mimeTypes, includeMountFolders);

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
			int returnValue = DLFolderServiceUtil.getFoldersCount(groupId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		int status, boolean includeMountfolders) throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getFoldersCount(groupId,
					parentFolderId, status, includeMountfolders);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap[] getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> returnValue =
				DLFolderServiceUtil.getMountFolders(groupId, parentFolderId,
					start, end, obc);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getMountFoldersCount(long groupId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = DLFolderServiceUtil.getMountFoldersCount(groupId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	public static void getSubfolderIds(Long[] folderIds, long groupId,
		long folderId) throws RemoteException {
		try {
			DLFolderServiceUtil.getSubfolderIds(ListUtil.toList(folderIds),
				groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(Long[] folderIds, long groupId,
		long folderId, boolean recurse) throws RemoteException {
		try {
			DLFolderServiceUtil.getSubfolderIds(ListUtil.toList(folderIds),
				groupId, folderId, recurse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long groupId, long folderId,
		boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = DLFolderServiceUtil.getSubfolderIds(groupId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFolderLock(long folderId)
		throws RemoteException {
		try {
			boolean returnValue = DLFolderServiceUtil.hasFolderLock(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isFolderLocked(long folderId)
		throws RemoteException {
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
			com.liferay.portal.kernel.lock.Lock returnValue = DLFolderServiceUtil.lockFolder(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
		long folderId, java.lang.String owner, boolean inheritable,
		long expirationTime) throws RemoteException {
		try {
			com.liferay.portal.kernel.lock.Lock returnValue = DLFolderServiceUtil.lockFolder(folderId,
					owner, inheritable, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.moveFolder(folderId,
					parentFolderId, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.lock.Lock returnValue = DLFolderServiceUtil.refreshFolderLock(lockUuid,
					companyId, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLFolderServiceUtil.unlockFolder(groupId, parentFolderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long folderId, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLFolderServiceUtil.unlockFolder(folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		Long[] fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, defaultFileEntryTypeId,
					ListUtil.toList(fileEntryTypeIds), restrictionType,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 7.0.0, replaced by more general {@link
	#updateFolder(long, String, String, long, List, int,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId, Long[] fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.updateFolder(folderId,
					name, description, defaultFileEntryTypeId,
					ListUtil.toList(fileEntryTypeIds), overrideFileEntryTypes,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId, Long[] fileEntryTypeIds,
		int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.updateFolder(folderId,
					name, description, defaultFileEntryTypeId,
					ListUtil.toList(fileEntryTypeIds), restrictionType,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceSoap.class);
}