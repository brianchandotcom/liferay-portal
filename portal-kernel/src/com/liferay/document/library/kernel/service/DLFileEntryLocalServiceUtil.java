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
 * Provides the local service utility for DLFileEntry. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryLocalService
 * @generated
 */
public class DLFileEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the document library file entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the document library file entry
	 * @return the document library file entry that was added
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
		addDLFileEntry(
			com.liferay.document.library.kernel.model.DLFileEntry dlFileEntry) {

		return getService().addDLFileEntry(dlFileEntry);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			addFileEntry(
				long userId, long groupId, long repositoryId, long folderId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFileEntry(
			userId, groupId, repositoryId, folderId, sourceFileName, mimeType,
			title, description, changeLog, fileEntryTypeId, ddmFormValuesMap,
			file, is, size, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileVersion
			cancelCheckOut(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().cancelCheckOut(userId, fileEntryId);
	}

	public static void checkInFileEntry(
			long userId, long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkInFileEntry(
			userId, fileEntryId, dlVersionNumberIncrease, changeLog,
			serviceContext);
	}

	public static void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long userId, long fileEntryId, long fileEntryTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(
			userId, fileEntryId, fileEntryTypeId, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long userId, long fileEntryId, long fileEntryTypeId,
				String owner, long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(
			userId, fileEntryId, fileEntryTypeId, owner, expirationTime,
			serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long userId, long fileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(
			userId, fileEntryId, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long userId, long fileEntryId, String owner,
				long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkOutFileEntry(
			userId, fileEntryId, owner, expirationTime, serviceContext);
	}

	public static void convertExtraSettings(String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().convertExtraSettings(keys);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			copyFileEntry(
				long userId, long groupId, long repositoryId, long fileEntryId,
				long destFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyFileEntry(
			userId, groupId, repositoryId, fileEntryId, destFolderId,
			serviceContext);
	}

	public static void copyFileEntryMetadata(
			long companyId, long fileEntryTypeId, long fileEntryId,
			long fromFileVersionId, long toFileVersionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().copyFileEntryMetadata(
			companyId, fileEntryTypeId, fileEntryId, fromFileVersionId,
			toFileVersionId, serviceContext);
	}

	/**
	 * Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new document library file entry
	 * @return the new document library file entry
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
		createDLFileEntry(long fileEntryId) {

		return getService().createDLFileEntry(fileEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the document library file entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the document library file entry
	 * @return the document library file entry that was removed
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
		deleteDLFileEntry(
			com.liferay.document.library.kernel.model.DLFileEntry dlFileEntry) {

		return getService().deleteDLFileEntry(dlFileEntry);
	}

	/**
	 * Deletes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws PortalException if a document library file entry with the primary key could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
			deleteDLFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDLFileEntry(fileEntryId);
	}

	public static void deleteFileEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntries(groupId, folderId);
	}

	public static void deleteFileEntries(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntries(
			groupId, folderId, includeTrashedEntries);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			deleteFileEntry(
				com.liferay.document.library.kernel.model.DLFileEntry
					dlFileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFileEntry(dlFileEntry);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFileEntry(fileEntryId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			deleteFileEntry(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFileEntry(userId, fileEntryId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			deleteFileVersion(long userId, long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFileVersion(userId, fileEntryId, version);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRepositoryFileEntries(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRepositoryFileEntries(repositoryId);
	}

	public static void deleteRepositoryFileEntries(
			long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRepositoryFileEntries(repositoryId, folderId);
	}

	public static void deleteRepositoryFileEntries(
			long repositoryId, long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRepositoryFileEntries(
			repositoryId, folderId, includeTrashedEntries);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchDLFileEntry(long fileEntryId) {

		return getService().fetchDLFileEntry(fileEntryId);
	}

	/**
	 * Returns the document library file entry matching the UUID and group.
	 *
	 * @param uuid the document library file entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchDLFileEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchDLFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchFileEntry(long groupId, long folderId, String title) {

		return getService().fetchFileEntry(groupId, folderId, title);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchFileEntry(String uuid, long groupId) {

		return getService().fetchFileEntry(uuid, groupId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchFileEntryByAnyImageId(long imageId) {

		return getService().fetchFileEntryByAnyImageId(imageId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchFileEntryByFileName(long groupId, long folderId, String fileName) {

		return getService().fetchFileEntryByFileName(
			groupId, folderId, fileName);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
		fetchFileEntryByName(long groupId, long folderId, String name) {

		return getService().fetchFileEntryByName(groupId, folderId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getDDMStructureFileEntries(long groupId, long[] ddmStructureIds) {

		return getService().getDDMStructureFileEntries(
			groupId, ddmStructureIds);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getDDMStructureFileEntries(long[] ddmStructureIds) {

		return getService().getDDMStructureFileEntries(ddmStructureIds);
	}

	/**
	 * Returns a range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of document library file entries
	 */
	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getDLFileEntries(int start, int end) {

		return getService().getDLFileEntries(start, end);
	}

	/**
	 * Returns all the document library file entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the document library file entries
	 * @param companyId the primary key of the company
	 * @return the matching document library file entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getDLFileEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getDLFileEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of document library file entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the document library file entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching document library file entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getDLFileEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						orderByComparator) {

		return getService().getDLFileEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of document library file entries.
	 *
	 * @return the number of document library file entries
	 */
	public static int getDLFileEntriesCount() {
		return getService().getDLFileEntriesCount();
	}

	/**
	 * Returns the document library file entry with the primary key.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws PortalException if a document library file entry with the primary key could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
			getDLFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileEntry(fileEntryId);
	}

	/**
	 * Returns the document library file entry matching the UUID and group.
	 *
	 * @param uuid the document library file entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching document library file entry
	 * @throws PortalException if a matching document library file entry could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
			getDLFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getExtraSettingsFileEntries(int start, int end) {

		return getService().getExtraSettingsFileEntries(start, end);
	}

	public static int getExtraSettingsFileEntriesCount() {
		return getService().getExtraSettingsFileEntriesCount();
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

	public static java.io.InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter,
			int increment)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileAsStream(
			fileEntryId, version, incrementCounter, increment);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			int start, int end) {

		return getService().getFileEntries(start, end);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long groupId, long folderId) {

		return getService().getFileEntries(groupId, folderId);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long groupId, long folderId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.document.library.kernel.model.DLFileEntry> obc) {

		return getService().getFileEntries(
			groupId, folderId, status, start, end, obc);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.document.library.kernel.model.DLFileEntry> obc) {

		return getService().getFileEntries(groupId, folderId, start, end, obc);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long groupId, long userId, java.util.List<Long> repositoryIds,
			java.util.List<Long> folderIds, String[] mimeTypes,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.document.library.kernel.model.DLFileEntry>
					queryDefinition) {

		return getService().getFileEntries(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long groupId, long userId, java.util.List<Long> folderIds,
			String[] mimeTypes,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.document.library.kernel.model.DLFileEntry>
					queryDefinition) {

		return getService().getFileEntries(
			groupId, userId, folderIds, mimeTypes, queryDefinition);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry> getFileEntries(
			long folderId, String name) {

		return getService().getFileEntries(folderId, name);
	}

	public static int getFileEntriesCount() {
		return getService().getFileEntriesCount();
	}

	public static int getFileEntriesCount(long groupId, long folderId) {
		return getService().getFileEntriesCount(groupId, folderId);
	}

	public static int getFileEntriesCount(
		long groupId, long folderId, int status) {

		return getService().getFileEntriesCount(groupId, folderId, status);
	}

	public static int getFileEntriesCount(
		long groupId, long userId, java.util.List<Long> repositoryIds,
		java.util.List<Long> folderIds, String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.document.library.kernel.model.DLFileEntry>
				queryDefinition) {

		return getService().getFileEntriesCount(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	public static int getFileEntriesCount(
		long groupId, long userId, java.util.List<Long> folderIds,
		String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.document.library.kernel.model.DLFileEntry>
				queryDefinition) {

		return getService().getFileEntriesCount(
			groupId, userId, folderIds, mimeTypes, queryDefinition);
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
			getFileEntryByName(long groupId, long folderId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryByName(groupId, folderId, name);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(long groupId, int start, int end) {

		return getService().getGroupFileEntries(groupId, start, end);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						obc) {

		return getService().getGroupFileEntries(groupId, start, end, obc);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(long groupId, long userId, int start, int end) {

		return getService().getGroupFileEntries(groupId, userId, start, end);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						obc) {

		return getService().getGroupFileEntries(
			groupId, userId, start, end, obc);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, long rootFolderId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						obc) {

		return getService().getGroupFileEntries(
			groupId, userId, rootFolderId, start, end, obc);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, long repositoryId, long rootFolderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry>
						obc) {

		return getService().getGroupFileEntries(
			groupId, userId, repositoryId, rootFolderId, start, end, obc);
	}

	public static int getGroupFileEntriesCount(long groupId) {
		return getService().getGroupFileEntriesCount(groupId);
	}

	public static int getGroupFileEntriesCount(long groupId, long userId) {
		return getService().getGroupFileEntriesCount(groupId, userId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getNoAssetFileEntries() {

		return getService().getNoAssetFileEntries();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntry>
			getRepositoryFileEntries(long repositoryId, int start, int end) {

		return getService().getRepositoryFileEntries(repositoryId, start, end);
	}

	public static int getRepositoryFileEntriesCount(long repositoryId) {
		return getService().getRepositoryFileEntriesCount(repositoryId);
	}

	public static String getUniqueTitle(
			long groupId, long folderId, long fileEntryId, String title,
			String extension)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUniqueTitle(
			groupId, folderId, fileEntryId, title, extension);
	}

	public static boolean hasExtraSettings() {
		return getService().hasExtraSettings();
	}

	public static boolean hasFileEntryLock(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().hasFileEntryLock(userId, fileEntryId);
	}

	public static boolean hasFileEntryLock(
		long userId, long fileEntryId, long folderId) {

		return getService().hasFileEntryLock(userId, fileEntryId, folderId);
	}

	public static void incrementViewCounter(
		com.liferay.document.library.kernel.model.DLFileEntry dlFileEntry,
		int increment) {

		getService().incrementViewCounter(dlFileEntry, increment);
	}

	public static boolean isFileEntryCheckedOut(long fileEntryId) {
		return getService().isFileEntryCheckedOut(fileEntryId);
	}

	public static com.liferay.portal.kernel.lock.Lock lockFileEntry(
			long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().lockFileEntry(userId, fileEntryId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			moveFileEntry(
				long userId, long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	public static void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().rebuildTree(companyId);
	}

	public static void revertFileEntry(
			long userId, long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			groupId, userId, creatorUserId, status, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long userId, long creatorUserId, long folderId,
			String[] mimeTypes, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			groupId, userId, creatorUserId, folderId, mimeTypes, status, start,
			end);
	}

	public static void setTreePaths(
			long folderId, String treePath, boolean reindex)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().setTreePaths(folderId, treePath, reindex);
	}

	public static void unlockFileEntry(long fileEntryId) {
		getService().unlockFileEntry(fileEntryId);
	}

	/**
	 * Updates the document library file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileEntry the document library file entry
	 * @return the document library file entry that was updated
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntry
		updateDLFileEntry(
			com.liferay.document.library.kernel.model.DLFileEntry dlFileEntry) {

		return getService().updateDLFileEntry(dlFileEntry);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				long userId, long fileEntryId, String sourceFileName,
				String mimeType, String title, String description,
				String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, fileEntryTypeId,
			ddmFormValuesMap, file, is, size, serviceContext);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntryType(
				long userId, long fileEntryId, long fileEntryTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFileEntryType(
			userId, fileEntryId, fileEntryTypeId, serviceContext);
	}

	public static void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateSmallImage(smallImageId, largeImageId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntry
			updateStatus(
				long userId,
				com.liferay.document.library.kernel.model.DLFileEntry
					dlFileEntry,
				com.liferay.document.library.kernel.model.DLFileVersion
					dlFileVersion,
				int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, dlFileEntry, dlFileVersion, status, serviceContext,
			workflowContext);
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

	public static void validateFile(
			long groupId, long folderId, long fileEntryId, String fileName,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().validateFile(
			groupId, folderId, fileEntryId, fileName, title);
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

	public static DLFileEntryLocalService getService() {
		if (_service == null) {
			_service = (DLFileEntryLocalService)PortalBeanLocatorUtil.locate(
				DLFileEntryLocalService.class.getName());
		}

		return _service;
	}

	private static DLFileEntryLocalService _service;

}