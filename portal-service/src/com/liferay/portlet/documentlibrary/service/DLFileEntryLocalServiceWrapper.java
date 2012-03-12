/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryLocalService
 * @generated
 */
public class DLFileEntryLocalServiceWrapper implements DLFileEntryLocalService,
	ServiceWrapper<DLFileEntryLocalService> {
	public DLFileEntryLocalServiceWrapper(
		DLFileEntryLocalService dlFileEntryLocalService) {
		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	* Adds the document library file entry to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry addDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addDLFileEntry(dlFileEntry);
	}

	/**
	* Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	*
	* @param fileEntryId the primary key for the new document library file entry
	* @return the new document library file entry
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry createDLFileEntry(
		long fileEntryId) {
		return _dlFileEntryLocalService.createDLFileEntry(fileEntryId);
	}

	/**
	* Deletes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @throws PortalException if a document library file entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteDLFileEntry(fileEntryId);
	}

	/**
	* Deletes the document library file entry from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteDLFileEntry(dlFileEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.fetchDLFileEntry(fileEntryId);
	}

	/**
	* Returns the document library file entry with the primary key.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry
	* @throws PortalException if a document library file entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntry(fileEntryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the document library file entry with the UUID in the group.
	*
	* @param uuid the UUID of document library file entry
	* @param groupId the group id of the document library file entry
	* @return the document library file entry
	* @throws PortalException if a document library file entry with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the document library file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of document library file entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntries(start, end);
	}

	/**
	* Returns the number of document library file entries.
	*
	* @return the number of document library file entries
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getDLFileEntriesCount();
	}

	/**
	* Updates the document library file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
	}

	/**
	* Updates the document library file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @param merge whether to merge the document library file entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the document library file entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlFileEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFileEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.addFileEntry(userId, groupId,
			repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, fileEntryTypeId, fieldsMap, file, is, size,
			serviceContext);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(dlFileEntry,
			addGroupPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.addFileEntryResources(dlFileEntry,
			groupPermissions, guestPermissions);
	}

	public void cancelCheckOut(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.cancelCheckOut(userId, fileEntryId);
	}

	public void checkInFileEntry(long userId, long fileEntryId,
		boolean majorVersion, java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.checkInFileEntry(userId, fileEntryId,
			majorVersion, changeLog, serviceContext);
	}

	public void checkInFileEntry(long userId, long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.checkInFileEntry(userId, fileEntryId, lockUuid);
	}

	/**
	* @deprecated {@link #checkOutFileEntry(long, long, ServiceContext)}
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.checkOutFileEntry(userId, fileEntryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.checkOutFileEntry(userId, fileEntryId,
			serviceContext);
	}

	/**
	* @deprecated {@link #checkOutFileEntry(long, long, String, long,
	ServiceContext)}
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.checkOutFileEntry(userId, fileEntryId,
			owner, expirationTime);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId, java.lang.String owner,
		long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.checkOutFileEntry(userId, fileEntryId,
			owner, expirationTime, serviceContext);
	}

	public void convertExtraSettings(java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.convertExtraSettings(keys);
	}

	public void copyFileEntryMetadata(long companyId, long fileEntryTypeId,
		long fileEntryId, long fromFileVersionId, long toFileVersionId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.copyFileEntryMetadata(companyId,
			fileEntryTypeId, fileEntryId, fromFileVersionId, toFileVersionId,
			serviceContext);
	}

	public void deleteFileEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntries(groupId, folderId);
	}

	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntry(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.deleteFileEntry(userId, fileEntryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntryByAnyImageId(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.fetchFileEntryByAnyImageId(imageId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getExtraSettingsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getExtraSettingsFileEntries(start, end);
	}

	public java.io.File getFile(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFile(userId, fileEntryId, version,
			incrementCounter);
	}

	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileAsStream(userId, fileEntryId,
			version);
	}

	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileAsStream(userId, fileEntryId,
			version, incrementCounter);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntries(start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntries(groupId, folderId,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntriesByMimeType(
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntriesByMimeType(mimeType);
	}

	public int getFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntriesCount();
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntriesCount(groupId, folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntry(groupId, folderId, title);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByName(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntryByName(groupId, folderId,
			name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntries(groupId, userId,
			start, end, obc);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getGroupFileEntriesCount(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getMisversionedFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getMisversionedFileEntries();
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getNoAssetFileEntries();
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getOrphanedFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.getOrphanedFileEntries();
	}

	public boolean hasExtraSettings()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.hasExtraSettings();
	}

	public boolean hasFileEntryLock(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.hasFileEntryLock(userId, fileEntryId);
	}

	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long userId,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.lockFileEntry(userId, fileEntryId);
	}

	public com.liferay.portal.model.Lock lockFileEntry(long userId,
		long fileEntryId, java.lang.String owner, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.lockFileEntry(userId, fileEntryId,
			owner, expirationTime);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.moveFileEntry(userId, fileEntryId,
			newFolderId, serviceContext);
	}

	public void revertFileEntry(long userId, long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.revertFileEntry(userId, fileEntryId, version,
			serviceContext);
	}

	public void unlockFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.unlockFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.unlockFileEntry(fileEntryId, lockUuid);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateFileEntry(userId, fileEntryId,
			sourceFileName, mimeType, title, description, changeLog,
			majorVersion, fileEntryTypeId, fieldsMap, file, is, size,
			serviceContext);
	}

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryLocalService.updateSmallImage(smallImageId, largeImageId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateStatus(
		long userId, long fileVersionId, int status,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.updateStatus(userId, fileVersionId,
			status, workflowContext, serviceContext);
	}

	public boolean verifyFileEntryCheckOut(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.verifyFileEntryCheckOut(fileEntryId,
			lockUuid);
	}

	public boolean verifyFileEntryLock(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryLocalService.verifyFileEntryLock(fileEntryId,
			lockUuid);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DLFileEntryLocalService getWrappedDLFileEntryLocalService() {
		return _dlFileEntryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	public DLFileEntryLocalService getWrappedService() {
		return _dlFileEntryLocalService;
	}

	public void setWrappedService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	private DLFileEntryLocalService _dlFileEntryLocalService;
}