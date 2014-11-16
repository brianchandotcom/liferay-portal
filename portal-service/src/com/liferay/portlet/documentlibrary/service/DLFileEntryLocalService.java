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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for DLFileEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFileEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryLocalServiceUtil} to access the document library file entry local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the document library file entry to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry addDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry);

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, long groupId, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion cancelCheckOut(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long, long,
	String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void checkInFileEntry(long userId, long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void checkInFileEntry(long userId, long fileEntryId,
		java.lang.String lockUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void checkInFileEntry(long userId, long fileEntryId,
		boolean majorVersion, java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	long, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	long, String, long, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId, java.lang.String owner,
		long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntry checkOutFileEntry(
		long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void convertExtraSettings(java.lang.String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void copyFileEntryMetadata(long companyId, long fileEntryTypeId,
		long fileEntryId, long fromFileVersionId, long toFileVersionId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	*
	* @param fileEntryId the primary key for the new document library file entry
	* @return the new document library file entry
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry createDLFileEntry(
		long fileEntryId);

	/**
	* Deletes the document library file entry from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry);

	/**
	* Deletes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry that was removed
	* @throws PortalException if a document library file entry with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteFileEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteFileEntries(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteFileEntry(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry deleteFileVersion(
		long userId, long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteRepositoryFileEntries(long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteRepositoryFileEntries(long repositoryId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchDLFileEntry(
		long fileEntryId);

	/**
	* Returns the document library file entry matching the UUID and group.
	*
	* @param uuid the document library file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchDLFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntry(
		long groupId, long folderId, java.lang.String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntryByAnyImageId(
		long imageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntryByFileName(
		long groupId, long folderId, java.lang.String fileName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchFileEntryByName(
		long groupId, long folderId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDDMStructureFileEntries(
		long[] ddmStructureIds);

	/**
	* Returns a range of all the document library file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of document library file entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getDLFileEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the number of document library file entries.
	*
	* @return the number of document library file entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileEntriesCount();

	/**
	* Returns the document library file entry with the primary key.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry
	* @throws PortalException if a document library file entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the document library file entry matching the UUID and group.
	*
	* @param uuid the document library file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry
	* @throws PortalException if a matching document library file entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getDLFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getExtraSettingsFileEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExtraSettingsFileEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.File getFile(long fileEntryId, java.lang.String version,
		boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.File getFile(long fileEntryId, java.lang.String version,
		boolean incrementCounter, int increment)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getFile(long, String,
	boolean)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.File getFile(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getFile(long, String,
	boolean, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.File getFile(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter, int increment)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getFileAsStream(long fileEntryId,
		java.lang.String version, boolean incrementCounter, int increment)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	String)}
	*/
	@java.lang.Deprecated
	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	String, boolean)}
	*/
	@java.lang.Deprecated
	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	String, boolean, int)}
	*/
	@java.lang.Deprecated
	public java.io.InputStream getFileAsStream(long userId, long fileEntryId,
		java.lang.String version, boolean incrementCounter, int increment)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long folderId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long userId, java.util.List<java.lang.Long> folderIds,
		java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition)
		throws java.lang.Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition)
		throws java.lang.Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getFileEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId,
		com.liferay.portal.kernel.util.DateRange dateRange, long repositoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long userId,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition)
		throws java.lang.Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long userId,
		java.util.List<java.lang.Long> repositoryIds,
		java.util.List<java.lang.Long> folderIds, java.lang.String[] mimeTypes,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.documentlibrary.model.DLFileEntry> queryDefinition)
		throws java.lang.Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByName(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long repositoryId, long rootFolderId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getMisversionedFileEntries();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getOrphanedFileEntries();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getRepositoryFileEntries(
		long repositoryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRepositoryFileEntriesCount(long repositoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasExtraSettings();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFileEntryLock(long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.increment.BufferedIncrement(configuration = "DLFileEntry", incrementClass = com.liferay.portal.kernel.increment.NumberIncrement.class)
	public void incrementViewCounter(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		int increment);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isKeepFileVersionLabel(long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.Lock lockFileEntry(long userId,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry moveFileEntry(
		long userId, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void revertFileEntry(long userId, long fileEntryId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long userId, long creatorUserId, long folderId,
		java.lang.String[] mimeTypes, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long userId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void setTreePaths(long folderId, java.lang.String treePath,
		boolean reindex)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void unlockFileEntry(long fileEntryId);

	/**
	* Updates the document library file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateDLFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry);

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, long fileEntryTypeId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		java.io.File file, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntryType(
		long userId, long fileEntryId, long fileEntryTypeId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateStatus(
		long userId, long fileVersionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void validateFile(long groupId, long folderId, long fileEntryId,
		java.lang.String fileName, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean verifyFileEntryCheckOut(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean verifyFileEntryLock(long fileEntryId,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException;
}