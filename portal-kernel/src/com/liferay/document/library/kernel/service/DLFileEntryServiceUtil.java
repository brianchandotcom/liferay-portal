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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for DLFileEntry. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryService
 * @generated
 */
public class DLFileEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
			addFileEntry(
				long groupId, long repositoryId, long folderId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream inputStream, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFileEntry(
			groupId, repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, fileEntryTypeId, ddmFormValuesMap, file,
			inputStream, size, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileVersion
			cancelCheckOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().cancelCheckOut(fileEntryId);
	}

	public static void checkInFileEntry(
			long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);
	}

	public static void checkInFileEntry(
			long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkInFileEntry(fileEntryId, lockUuid, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long fileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(fileEntryId, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long fileEntryId, String owner, long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			copyFileEntry(
				long groupId, long repositoryId, long fileEntryId,
				long destFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyFileEntry(
			groupId, repositoryId, fileEntryId, destFolderId, serviceContext);
	}

	public static void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntry(fileEntryId);
	}

	public static void deleteFileEntry(
			long groupId, long folderId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntry(groupId, folderId, title);
	}

	public static void deleteFileVersion(long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileVersion(fileEntryId, version);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			fetchFileEntryByImageId(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchFileEntryByImageId(imageId);
	}

	public static java.io.InputStream getFileAsStream(
			long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileAsStream(fileEntryId, version);
	}

	public static java.io.InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileAsStream(
			fileEntryId, version, incrementCounter);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				long groupId, long folderId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntries(
			groupId, folderId, status, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				long groupId, long folderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntries(
			groupId, folderId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				long groupId, long folderId, long fileEntryTypeId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntries(
			groupId, folderId, fileEntryTypeId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntries(
			groupId, folderId, mimeTypes, status, start, end,
			orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntries(
			groupId, folderId, mimeTypes, start, end, orderByComparator);
	}

	public static int getFileEntriesCount(long groupId, long folderId) {
		return getService().getFileEntriesCount(groupId, folderId);
	}

	public static int getFileEntriesCount(
		long groupId, long folderId, int status) {

		return getService().getFileEntriesCount(groupId, folderId, status);
	}

	public static int getFileEntriesCount(
		long groupId, long folderId, long fileEntryTypeId) {

		return getService().getFileEntriesCount(
			groupId, folderId, fileEntryTypeId);
	}

	public static int getFileEntriesCount(
		long groupId, long folderId, String[] mimeTypes) {

		return getService().getFileEntriesCount(groupId, folderId, mimeTypes);
	}

	public static int getFileEntriesCount(
		long groupId, long folderId, String[] mimeTypes, int status) {

		return getService().getFileEntriesCount(
			groupId, folderId, mimeTypes, status);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntry(fileEntryId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntry(long groupId, long folderId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntry(groupId, folderId, title);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntryByFileName(long groupId, long folderId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryByFileName(groupId, folderId, fileName);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.lock.Lock getFileEntryLock(
		long fileEntryId) {

		return getService().getFileEntryLock(fileEntryId);
	}

	public static int getFoldersFileEntriesCount(
		long groupId, java.util.List<Long> folderIds, int status) {

		return getService().getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					long groupId, long userId, long rootFolderId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntries(
			groupId, userId, rootFolderId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					long groupId, long userId, long repositoryId,
					long rootFolderId, String[] mimeTypes, int status,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntries(
			groupId, userId, repositoryId, rootFolderId, mimeTypes, status,
			start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
				getGroupFileEntries(
					long groupId, long userId, long rootFolderId,
					String[] mimeTypes, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.document.library.kernel.model.DLFileEntry>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntries(
			groupId, userId, rootFolderId, mimeTypes, status, start, end,
			orderByComparator);
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntriesCount(
			groupId, userId, rootFolderId);
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long repositoryId, long rootFolderId,
			String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntriesCount(
			groupId, userId, repositoryId, rootFolderId, mimeTypes, status);
	}

	public static int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupFileEntriesCount(
			groupId, userId, rootFolderId, mimeTypes, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean hasFileEntryLock(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasFileEntryLock(fileEntryId);
	}

	public static boolean isFileEntryCheckedOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().isFileEntryCheckedOut(fileEntryId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			moveFileEntry(
				long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	public static void revertFileEntry(
			long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().revertFileEntry(fileEntryId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, long folderId, String[] mimeTypes,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			groupId, creatorUserId, folderId, mimeTypes, status, start, end);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				long fileEntryId, String sourceFileName, String mimeType,
				String title, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream inputStream, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, fileEntryTypeId,
			ddmFormValuesMap, file, inputStream, size, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateStatus(
				long userId, long fileVersionId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, fileVersionId, status, serviceContext, workflowContext);
	}

	public static boolean verifyFileEntryCheckOut(
			long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	public static boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().verifyFileEntryLock(fileEntryId, lockUuid);
	}

	public static DLFileEntryService getService() {
		if (_service == null) {
			_service = (DLFileEntryService)PortalBeanLocatorUtil.locate(
				DLFileEntryService.class.getName());
		}

		return _service;
	}

	private static DLFileEntryService _service;

}