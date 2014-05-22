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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFolderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderLocalService
 * @generated
 */
@ProviderType
public class DLFolderLocalServiceWrapper implements DLFolderLocalService,
	ServiceWrapper<DLFolderLocalService> {
	public DLFolderLocalServiceWrapper(
		DLFolderLocalService dlFolderLocalService) {
		_dlFolderLocalService = dlFolderLocalService;
	}

	/**
	* Adds the document library folder to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder addDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.addDLFolder(dlFolder);
	}

	/**
	* Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	*
	* @param folderId the primary key for the new document library folder
	* @return the new document library folder
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder createDLFolder(
		long folderId) {
		return _dlFolderLocalService.createDLFolder(folderId);
	}

	/**
	* Deletes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder that was removed
	* @throws PortalException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteDLFolder(folderId);
	}

	/**
	* Deletes the document library folder from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteDLFolder(dlFolder);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFolderLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.dynamicQuery(dynamicQuery);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.fetchDLFolder(folderId);
	}

	/**
	* Returns the document library folder with the matching UUID and company.
	*
	* @param uuid the document library folder's UUID
	* @param companyId the primary key of the company
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolderByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.fetchDLFolderByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the document library folder matching the UUID and group.
	*
	* @param uuid the document library folder's UUID
	* @param groupId the primary key of the group
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.fetchDLFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the document library folder with the primary key.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder
	* @throws PortalException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolder(folderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the document library folder with the matching UUID and company.
	*
	* @param uuid the document library folder's UUID
	* @param companyId the primary key of the company
	* @return the matching document library folder
	* @throws PortalException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolderByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolderByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the document library folder matching the UUID and group.
	*
	* @param uuid the document library folder's UUID
	* @param groupId the primary key of the group
	* @return the matching document library folder
	* @throws PortalException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolderByUuidAndGroupId(uuid, groupId);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFolders(start, end);
	}

	/**
	* Returns the number of document library folders.
	*
	* @return the number of document library folders
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getDLFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFoldersCount();
	}

	/**
	* Updates the document library folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateDLFolder(dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addDLFileEntryTypeDLFolder(fileEntryTypeId,
			folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addDLFileEntryTypeDLFolder(fileEntryTypeId,
			dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addDLFileEntryTypeDLFolders(fileEntryTypeId,
			folderIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> DLFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.addDLFileEntryTypeDLFolders(fileEntryTypeId,
			DLFolders);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearDLFileEntryTypeDLFolders(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.clearDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFileEntryTypeDLFolder(fileEntryTypeId,
			folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFileEntryTypeDLFolder(fileEntryTypeId,
			dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFileEntryTypeDLFolders(fileEntryTypeId,
			folderIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> DLFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteDLFileEntryTypeDLFolders(fileEntryTypeId,
			DLFolders);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFileEntryTypeDLFolders(fileEntryTypeId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFileEntryTypeDLFolders(fileEntryTypeId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getDLFileEntryTypeDLFoldersCount(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getDLFileEntryTypeDLFoldersCount(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.hasDLFileEntryTypeDLFolder(fileEntryTypeId,
			folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasDLFileEntryTypeDLFolders(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.hasDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.setDLFileEntryTypeDLFolders(fileEntryTypeId,
			folderIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _dlFolderLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFolderLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description, boolean hidden,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.addFolder(userId, groupId, repositoryId,
			mountPoint, parentFolderId, name, description, hidden,
			serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by more general {@link #addFolder(long,
	long, long, boolean, long, String, String, boolean,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.addFolder(userId, groupId, repositoryId,
			mountPoint, parentFolderId, name, description, serviceContext);
	}

	@Override
	public void deleteAll(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.deleteAll(groupId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteFolder(dlFolder);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteFolder(dlFolder,
			includeTrashedEntries);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteFolder(folderId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteFolder(folderId,
			includeTrashedEntries);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long userId, long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.deleteFolder(userId, folderId,
			includeTrashedEntries);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.fetchFolder(folderId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.fetchFolder(groupId, parentFolderId, name);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getCompanyFolders(companyId, start, end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getCompanyFoldersCount(companyId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileEntriesAndFileShortcuts(long, long, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcuts(groupId,
			folderId, status, start, end);
	}

	@Override
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcuts(groupId,
			folderId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileEntriesAndFileShortcutsCount(long, long,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFileEntriesAndFileShortcutsCount(groupId,
			folderId, queryDefinition);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolder(folderId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	@Override
	public long getFolderId(long companyId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolderId(companyId, folderId);
	}

	@Override
	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolderIds(groupId, parentFolderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId,
			includeMountfolders);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, boolean includeMountfolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId,
			includeMountfolders, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFolders(groupId, parentFolderId, start,
			end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcuts(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, includeMountFolders, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, status, mimeTypes, includeMountFolders, start, end, obc);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, includeMountFolders);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId,
		boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersCount(groupId, parentFolderId,
			includeMountfolders);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status,
		boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getFoldersCount(groupId, parentFolderId,
			status, includeMountfolders);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getMountFolder(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getMountFolder(repositoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getMountFolders(groupId, parentFolderId,
			start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getMountFoldersCount(groupId,
			parentFolderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getNoAssetFolders()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.getNoAssetFolders();
	}

	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	@Override
	public boolean hasFolderLock(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.hasFolderLock(userId, folderId);
	}

	@Override
	public com.liferay.portal.model.Lock lockFolder(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.lockFolder(userId, folderId);
	}

	@Override
	public com.liferay.portal.model.Lock lockFolder(long userId, long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.lockFolder(userId, folderId, owner,
			inheritable, expirationTime);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long userId, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.moveFolder(userId, folderId,
			parentFolderId, serviceContext);
	}

	@Override
	public void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.rebuildTree(companyId);
	}

	@Override
	public void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.unlockFolder(groupId, parentFolderId, name,
			lockUuid);
	}

	@Override
	public void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.unlockFolder(folderId, lockUuid);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description, defaultFileEntryTypeId, fileEntryTypeIds,
			overrideFileEntryTypes, serviceContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolderAndFileEntryTypes(
		long userId, long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateFolderAndFileEntryTypes(userId,
			folderId, parentFolderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	@Override
	public void updateLastPostDate(long folderId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFolderLocalService.updateLastPostDate(folderId, lastPostDate);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder updateStatus(
		long userId, long folderId, int status,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFolderLocalService.updateStatus(userId, folderId, status,
			workflowContext, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DLFolderLocalService getWrappedDLFolderLocalService() {
		return _dlFolderLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		_dlFolderLocalService = dlFolderLocalService;
	}

	@Override
	public DLFolderLocalService getWrappedService() {
		return _dlFolderLocalService;
	}

	@Override
	public void setWrappedService(DLFolderLocalService dlFolderLocalService) {
		_dlFolderLocalService = dlFolderLocalService;
	}

	private DLFolderLocalService _dlFolderLocalService;
}