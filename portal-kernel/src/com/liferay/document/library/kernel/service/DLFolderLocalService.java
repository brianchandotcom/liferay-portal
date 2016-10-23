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

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.model.DLFolder;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for DLFolder. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFolderLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFolderLocalServiceUtil} to access the document library folder local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDLFileEntryTypeDLFolders(long fileEntryTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFolderLock(long userId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasInheritableLock(long folderId) throws PortalException;

	public boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid) throws PortalException;

	/**
	* Adds the document library folder to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFolder addDLFolder(DLFolder dlFolder);

	public DLFolder addFolder(long userId, long groupId, long repositoryId,
		boolean mountPoint, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean hidden,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	*
	* @param folderId the primary key for the new document library folder
	* @return the new document library folder
	*/
	public DLFolder createDLFolder(long folderId);

	/**
	* Deletes the document library folder from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFolder deleteDLFolder(DLFolder dlFolder);

	/**
	* Deletes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder that was removed
	* @throws PortalException if a document library folder with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFolder deleteDLFolder(long folderId) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public DLFolder deleteFolder(DLFolder dlFolder) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public DLFolder deleteFolder(DLFolder dlFolder,
		boolean includeTrashedEntries) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public DLFolder deleteFolder(long folderId) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public DLFolder deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public DLFolder deleteFolder(long userId, long folderId,
		boolean includeTrashedEntries) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder fetchDLFolder(long folderId);

	/**
	* Returns the document library folder matching the UUID and group.
	*
	* @param uuid the document library folder's UUID
	* @param groupId the primary key of the group
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder fetchDLFolderByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder fetchFolder(java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder fetchFolder(long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder fetchFolder(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns the document library folder with the primary key.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder
	* @throws PortalException if a document library folder with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder getDLFolder(long folderId) throws PortalException;

	/**
	* Returns the document library folder matching the UUID and group.
	*
	* @param uuid the document library folder's UUID
	* @param groupId the primary key of the group
	* @return the matching document library folder
	* @throws PortalException if a matching document library folder could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder getDLFolderByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder getFolder(long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder getFolder(long groupId, long parentFolderId,
		java.lang.String name) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFolder getMountFolder(long repositoryId) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DLFolder moveFolder(long userId, long folderId, long parentFolderId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the document library folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFolder updateDLFolder(DLFolder dlFolder);

	/**
	* @deprecated As of 7.0.0, replaced {@link #updateFolder(long, long,
	String, String, long, List, int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DLFolder updateFolder(long folderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		List<java.lang.Long> fileEntryTypeIds, boolean overrideFileEntryTypes,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DLFolder updateFolder(long folderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateFolder(long, long,
	String, String, long, List, int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DLFolder updateFolder(long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId, List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DLFolder updateFolder(long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId, List<java.lang.Long> fileEntryTypeIds,
		int restrictionType, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #
	updateFolderAndFileEntryTypes(long, long, long, String,
	String, long, List, int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DLFolder updateFolderAndFileEntryTypes(long userId, long folderId,
		long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		List<java.lang.Long> fileEntryTypeIds, boolean overrideFileEntryTypes,
		ServiceContext serviceContext) throws PortalException;

	public DLFolder updateFolderAndFileEntryTypes(long userId, long folderId,
		long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		ServiceContext serviceContext) throws PortalException;

	public DLFolder updateStatus(long userId, long folderId, int status,
		Map<java.lang.String, Serializable> workflowContext,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	public Lock lockFolder(long userId, long folderId)
		throws PortalException;

	public Lock lockFolder(long userId, long folderId, java.lang.String owner,
		boolean inheritable, long expirationTime) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyFoldersCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileEntryTypeDLFoldersCount(long fileEntryTypeId);

	/**
	* Returns the number of document library folders.
	*
	* @return the number of document library folders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFoldersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		QueryDefinition<?> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId,
		boolean includeMountfolders);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId, int status,
		boolean includeMountfolders);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMountFoldersCount(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRepositoryFoldersCount(long repositoryId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getCompanyFolders(long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId,
		int start, int end, OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns a range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of document library folders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFolders(int start, int end);

	/**
	* Returns all the document library folders matching the UUID and company.
	*
	* @param uuid the UUID of the document library folders
	* @param companyId the primary key of the company
	* @return the matching document library folders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFoldersByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of document library folders matching the UUID and company.
	*
	* @param uuid the UUID of the document library folders
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching document library folders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getDLFoldersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getFileEntriesAndFileShortcuts(long groupId,
		long folderId, QueryDefinition<?> queryDefinition);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getGroupFolderIds(long,
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getFolderIds(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getFolders(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getFolders(long groupId, long parentFolderId,
		boolean includeMountfolders);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getFolders(long groupId, long parentFolderId,
		boolean includeMountfolders, int start, int end,
		OrderByComparator<DLFolder> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getFolders(long groupId, long parentFolderId,
		int start, int end, OrderByComparator<DLFolder> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getFolders(long groupId, long parentFolderId,
		int status, boolean includeMountfolders, int start, int end,
		OrderByComparator<DLFolder> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getGroupFolderIds(long groupId,
		long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getMountFolders(long groupId, long parentFolderId,
		int start, int end, OrderByComparator<DLFolder> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getNoAssetFolders();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getRepositoryFolderIds(long repositoryId,
		long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFolder> getRepositoryFolders(long repositoryId, int start,
		int end);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getFolderId(long companyId, long folderId);

	/**
	* Returns the fileEntryTypeIds of the document library file entry types associated with the document library folder.
	*
	* @param folderId the folderId of the document library folder
	* @return long[] the fileEntryTypeIds of document library file entry types associated with the document library folder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getDLFileEntryTypePrimaryKeys(long folderId);

	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId,
		DLFolder dlFolder);

	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId, long folderId);

	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		List<DLFolder> dlFolders);

	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds);

	public void clearDLFileEntryTypeDLFolders(long fileEntryTypeId);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #deleteAllByGroup(long)}
	*/
	@java.lang.Deprecated
	public void deleteAll(long groupId) throws PortalException;

	public void deleteAllByGroup(long groupId) throws PortalException;

	public void deleteAllByRepository(long repositoryId)
		throws PortalException;

	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		DLFolder dlFolder);

	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId);

	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		List<DLFolder> dlFolders);

	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getGroupSubfolderIds(List<java.lang.Long> folderIds,
		long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getRepositorySubfolderIds(List<java.lang.Long> folderIds,
		long repositoryId, long folderId);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getGroupSubfolderIds(List,
	long, long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(List<java.lang.Long> folderIds, long groupId,
		long folderId);

	public void rebuildTree(long companyId) throws PortalException;

	public void rebuildTree(long companyId, long parentFolderId,
		java.lang.String parentTreePath, boolean reindex)
		throws PortalException;

	public void setDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds);

	public void unlockFolder(long folderId, java.lang.String lockUuid)
		throws PortalException;

	public void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws PortalException;

	@BufferedIncrement(configuration = "DLFolderEntry", incrementClass = com.liferay.portal.kernel.increment.DateOverrideIncrement.class)
	public void updateLastPostDate(long folderId, Date lastPostDate)
		throws PortalException;
}