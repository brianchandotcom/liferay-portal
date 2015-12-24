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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for DLFileEntry. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl
 * @generated
 */
@ProviderType
public class DLFileEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long groupId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> ddmFormValuesMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFileEntry(groupId, repositoryId, folderId,
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, ddmFormValuesMap, file, is, size, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion cancelCheckOut(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().cancelCheckOut(fileEntryId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	String, ServiceContext)}
	*/
	@Deprecated
	public static void checkInFileEntry(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkInFileEntry(fileEntryId, lockUuid);
	}

	public static void checkInFileEntry(long fileEntryId,
		java.lang.String lockUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkInFileEntry(fileEntryId, lockUuid, serviceContext);
	}

	public static void checkInFileEntry(long fileEntryId, boolean major,
		java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.checkInFileEntry(fileEntryId, major, changeLog, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().checkOutFileEntry(fileEntryId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	String, long, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long fileEntryId, java.lang.String owner, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().checkOutFileEntry(fileEntryId, owner, expirationTime);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long fileEntryId, java.lang.String owner, long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .checkOutFileEntry(fileEntryId, owner, expirationTime,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().checkOutFileEntry(fileEntryId, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry copyFileEntry(
		long groupId, long repositoryId, long fileEntryId, long destFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .copyFileEntry(groupId, repositoryId, fileEntryId,
			destFolderId, serviceContext);
	}

	public static void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntry(fileEntryId);
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntry(groupId, folderId, title);
	}

	public static void deleteFileVersion(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileVersion(fileEntryId, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntryByImageId(
		long imageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchFileEntryByImageId(imageId);
	}

	public static java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileAsStream(fileEntryId, version);
	}

	public static java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileAsStream(fileEntryId, version, incrementCounter);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntries(groupId, folderId, fileEntryTypeId, start,
			end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, java.lang.String[] mimeTypes, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntries(groupId, folderId, mimeTypes, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntries(groupId, folderId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFileEntries(groupId, folderId, status, start, end, obc);
	}

	public static int getFileEntriesCount(long groupId, long folderId) {
		return getService().getFileEntriesCount(groupId, folderId);
	}

	public static int getFileEntriesCount(long groupId, long folderId,
		long fileEntryTypeId) {
		return getService()
				   .getFileEntriesCount(groupId, folderId, fileEntryTypeId);
	}

	public static int getFileEntriesCount(long groupId, long folderId,
		java.lang.String[] mimeTypes) {
		return getService().getFileEntriesCount(groupId, folderId, mimeTypes);
	}

	public static int getFileEntriesCount(long groupId, long folderId,
		int status) {
		return getService().getFileEntriesCount(groupId, folderId, status);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntry(fileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntry(groupId, folderId, title);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.lock.Lock getFileEntryLock(
		long fileEntryId) {
		return getService().getFileEntryLock(fileEntryId);
	}

	public static int getFoldersFileEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status) {
		return getService()
				   .getFoldersFileEntriesCount(groupId, folderIds, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long repositoryId, long rootFolderId,
		java.lang.String[] mimeTypes, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntries(groupId, userId, repositoryId,
			rootFolderId, mimeTypes, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId,
		java.lang.String[] mimeTypes, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntries(groupId, userId, rootFolderId,
			mimeTypes, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntries(groupId, userId, rootFolderId, start,
			end, obc);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long repositoryId, long rootFolderId, java.lang.String[] mimeTypes,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntriesCount(groupId, userId, repositoryId,
			rootFolderId, mimeTypes, status);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntriesCount(groupId, userId, rootFolderId);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId,
		long rootFolderId, java.lang.String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupFileEntriesCount(groupId, userId, rootFolderId,
			mimeTypes, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
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

	public static boolean isKeepFileVersionLabel(long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().isKeepFileVersionLabel(fileEntryId, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileEntry(fileEntryId, newFolderId, serviceContext);
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .refreshFileEntryLock(lockUuid, companyId, expirationTime);
	}

	public static void revertFileEntry(long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().revertFileEntry(fileEntryId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, long folderId, java.lang.String[] mimeTypes,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, creatorUserId, folderId, mimeTypes, status,
			start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> ddmFormValuesMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFileEntry(fileEntryId, sourceFileName, mimeType,
			title, description, changeLog, majorVersion, fileEntryTypeId,
			ddmFormValuesMap, file, is, size, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateStatus(
		long userId, long fileVersionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, fileVersionId, status, serviceContext,
			workflowContext);
	}

	public static boolean verifyFileEntryCheckOut(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().verifyFileEntryCheckOut(fileEntryId, lockUuid);
	}

	public static boolean verifyFileEntryLock(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().verifyFileEntryLock(fileEntryId, lockUuid);
	}

	public static DLFileEntryService getService() {
		if (_service == null) {
			_service = (DLFileEntryService)PortalBeanLocatorUtil.locate(DLFileEntryService.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLFileEntryService _service;
}