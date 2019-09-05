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

import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DLFileEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.document.library.kernel.model.DLFileEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.document.library.kernel.model.DLFileEntry</code>, that is translated to a
 * <code>com.liferay.document.library.kernel.model.DLFileEntrySoap</code>. Methods that SOAP
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
 * @see DLFileEntryServiceHttp
 * @generated
 */
public class DLFileEntryServiceSoap {

	public static com.liferay.document.library.kernel.model.DLFileVersionSoap
			cancelCheckOut(long fileEntryId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileVersion
				returnValue = DLFileEntryServiceUtil.cancelCheckOut(
					fileEntryId);

			return com.liferay.document.library.kernel.model.DLFileVersionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #checkInFileEntry(long, DLVersionNumberIncrease, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.checkInFileEntry(
				fileEntryId, major, changeLog, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkInFileEntry(
			long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkInFileEntry(
			long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.checkInFileEntry(
				fileEntryId, lockUuid, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			checkOutFileEntry(
				long fileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.checkOutFileEntry(
					fileEntryId, serviceContext);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			checkOutFileEntry(
				long fileEntryId, String owner, long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.checkOutFileEntry(
					fileEntryId, owner, expirationTime, serviceContext);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			copyFileEntry(
				long groupId, long repositoryId, long fileEntryId,
				long destFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.copyFileEntry(
					groupId, repositoryId, fileEntryId, destFolderId,
					serviceContext);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long fileEntryId)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.deleteFileEntry(fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(
			long groupId, long folderId, String title)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.deleteFileEntry(groupId, folderId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileVersion(long fileEntryId, String version)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.deleteFileVersion(fileEntryId, version);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			fetchFileEntryByImageId(long imageId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.fetchFileEntryByImageId(imageId);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getFileEntries(
				long groupId, long folderId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getFileEntries(
						groupId, folderId, status, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getFileEntries(
				long groupId, long folderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getFileEntries(
						groupId, folderId, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getFileEntries(
				long groupId, long folderId, long fileEntryTypeId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getFileEntries(
						groupId, folderId, fileEntryTypeId, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getFileEntries(
						groupId, folderId, mimeTypes, status, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getFileEntries(
						groupId, folderId, mimeTypes, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(long groupId, long folderId)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(
				groupId, folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(
			long groupId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(
				groupId, folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(
			long groupId, long folderId, long fileEntryTypeId)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(
				groupId, folderId, fileEntryTypeId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(
				groupId, folderId, mimeTypes);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes, int status)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(
				groupId, folderId, mimeTypes, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			getFileEntry(long fileEntryId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.getFileEntry(fileEntryId);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			getFileEntry(long groupId, long folderId, String title)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.getFileEntry(groupId, folderId, title);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock getFileEntryLock(
			long fileEntryId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.lock.Lock returnValue =
				DLFileEntryServiceUtil.getFileEntryLock(fileEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersFileEntriesCount(
			long groupId, Long[] folderIds, int status)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getFoldersFileEntriesCount(
				groupId, ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getGroupFileEntries(
				long groupId, long userId, long rootFolderId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getGroupFileEntries(
						groupId, userId, rootFolderId, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getGroupFileEntries(
				long groupId, long userId, long repositoryId, long rootFolderId,
				String[] mimeTypes, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getGroupFileEntries(
						groupId, userId, repositoryId, rootFolderId, mimeTypes,
						status, start, end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap[]
			getGroupFileEntries(
				long groupId, long userId, long rootFolderId,
				String[] mimeTypes, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntry>
					returnValue = DLFileEntryServiceUtil.getGroupFileEntries(
						groupId, userId, rootFolderId, mimeTypes, status, start,
						end, obc);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getGroupFileEntriesCount(
				groupId, userId, rootFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long repositoryId, long rootFolderId,
			String[] mimeTypes, int status)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getGroupFileEntriesCount(
				groupId, userId, repositoryId, rootFolderId, mimeTypes, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryServiceUtil.getGroupFileEntriesCount(
				groupId, userId, rootFolderId, mimeTypes, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFileEntryLock(long fileEntryId)
		throws RemoteException {

		try {
			boolean returnValue = DLFileEntryServiceUtil.hasFileEntryLock(
				fileEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isFileEntryCheckedOut(long fileEntryId)
		throws RemoteException {

		try {
			boolean returnValue = DLFileEntryServiceUtil.isFileEntryCheckedOut(
				fileEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static boolean isKeepFileVersionLabel(
			long fileEntryId, boolean majorVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			boolean returnValue = DLFileEntryServiceUtil.isKeepFileVersionLabel(
				fileEntryId, majorVersion, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #isKeepFileVersionLabel(long, boolean, ServiceContext)}
	 */
	@Deprecated
	public static boolean isKeepFileVersionLabel(
			long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			boolean returnValue = DLFileEntryServiceUtil.isKeepFileVersionLabel(
				fileEntryId, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntrySoap
			moveFileEntry(
				long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntry returnValue =
				DLFileEntryServiceUtil.moveFileEntry(
					fileEntryId, newFolderId, serviceContext);

			return com.liferay.document.library.kernel.model.DLFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.lock.Lock returnValue =
				DLFileEntryServiceUtil.refreshFileEntryLock(
					lockUuid, companyId, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertFileEntry(
			long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DLFileEntryServiceUtil.revertFileEntry(
				fileEntryId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryCheckOut(
			long fileEntryId, String lockUuid)
		throws RemoteException {

		try {
			boolean returnValue =
				DLFileEntryServiceUtil.verifyFileEntryCheckOut(
					fileEntryId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws RemoteException {

		try {
			boolean returnValue = DLFileEntryServiceUtil.verifyFileEntryLock(
				fileEntryId, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryServiceSoap.class);

}