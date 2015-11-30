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
 * Provides a wrapper for {@link DLFileEntryTypeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeLocalService
 * @generated
 */
@ProviderType
public class DLFileEntryTypeLocalServiceWrapper
	implements DLFileEntryTypeLocalService,
		ServiceWrapper<DLFileEntryTypeLocalService> {
	public DLFileEntryTypeLocalServiceWrapper(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	public void addDDMStructureLinks(long fileEntryTypeId,
		java.util.Set<java.lang.Long> ddmStructureIds) {
		_dlFileEntryTypeLocalService.addDDMStructureLinks(fileEntryTypeId,
			ddmStructureIds);
	}

	/**
	* Adds the document library file entry type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was added
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return _dlFileEntryTypeLocalService.addDLFileEntryType(dlFileEntryType);
	}

	@Override
	public void addDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryType(folderId,
			dlFileEntryType);
	}

	@Override
	public void addDLFolderDLFileEntryType(long folderId, long fileEntryTypeId) {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	@Override
	public void addDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes) {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryTypes(folderId,
			DLFileEntryTypes);
	}

	@Override
	public void addDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String fileEntryTypeKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.addFileEntryType(userId, groupId,
			fileEntryTypeKey, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.addFileEntryType(userId, groupId,
			name, description, ddmStructureIds, serviceContext);
	}

	@Override
	public void cascadeFileEntryTypes(long userId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.cascadeFileEntryTypes(userId, dlFolder);
	}

	@Override
	public void clearDLFolderDLFileEntryTypes(long folderId) {
		_dlFileEntryTypeLocalService.clearDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType createDLFileEntryType(
		long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.createDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Deletes the document library file entry type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was removed
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return _dlFileEntryTypeLocalService.deleteDLFileEntryType(dlFileEntryType);
	}

	/**
	* Deletes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.deleteDLFileEntryType(fileEntryTypeId);
	}

	@Override
	public void deleteDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryType(folderId,
			dlFileEntryType);
	}

	@Override
	public void deleteDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId) {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	@Override
	public void deleteDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes) {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryTypes(folderId,
			DLFileEntryTypes);
	}

	@Override
	public void deleteDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	@Override
	public void deleteFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	public void deleteFileEntryTypes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.deleteFileEntryTypes(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileEntryTypeLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileEntryTypeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _dlFileEntryTypeLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _dlFileEntryTypeLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileEntryTypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _dlFileEntryTypeLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryType(
		long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.fetchDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _dlFileEntryTypeLocalService.fetchDLFileEntryTypeByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.fetchFileEntryType(fileEntryTypeId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long groupId, java.lang.String fileEntryTypeKey) {
		return _dlFileEntryTypeLocalService.fetchFileEntryType(groupId,
			fileEntryTypeKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileEntryTypeLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the document library file entry type with the primary key.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type
	* @throws PortalException if a matching document library file entry type could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypeByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of document library file entry types
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		int start, int end) {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypes(start, end);
	}

	/**
	* Returns all the document library file entry types matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entry types
	* @param companyId the primary key of the company
	* @return the matching document library file entry types, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of document library file entry types matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entry types
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching document library file entry types, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	*/
	@Override
	public int getDLFileEntryTypesCount() {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypesCount();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId) {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end) {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId,
			start, end, orderByComparator);
	}

	@Override
	public int getDLFolderDLFileEntryTypesCount(long folderId) {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypesCount(folderId);
	}

	/**
	* Returns the folderIds of the document library folders associated with the document library file entry type.
	*
	* @param fileEntryTypeId the fileEntryTypeId of the document library file entry type
	* @return long[] the folderIds of document library folders associated with the document library file entry type
	*/
	@Override
	public long[] getDLFolderPrimaryKeys(long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.getDLFolderPrimaryKeys(fileEntryTypeId);
	}

	@Override
	public long getDefaultFileEntryTypeId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(folderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _dlFileEntryTypeLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long groupId, java.lang.String fileEntryTypeKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getFileEntryType(groupId,
			fileEntryTypeKey);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getFileEntryTypes(ddmStructureId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long[] groupIds) {
		return _dlFileEntryTypeLocalService.getFileEntryTypes(groupIds);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFolderFileEntryTypes(
		long[] groupIds, long folderId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getFolderFileEntryTypes(groupIds,
			folderId, inherited);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _dlFileEntryTypeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlFileEntryTypeLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.hasDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	@Override
	public boolean hasDLFolderDLFileEntryTypes(long folderId) {
		return _dlFileEntryTypeLocalService.hasDLFolderDLFileEntryTypes(folderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> search(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return _dlFileEntryTypeLocalService.search(companyId, groupIds,
			keywords, includeBasicFileEntryType, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType) {
		return _dlFileEntryTypeLocalService.searchCount(companyId, groupIds,
			keywords, includeBasicFileEntryType);
	}

	@Override
	public void setDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		_dlFileEntryTypeLocalService.setDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	@Override
	public void unsetFolderFileEntryTypes(long folderId) {
		_dlFileEntryTypeLocalService.unsetFolderFileEntryTypes(folderId);
	}

	@Override
	public void updateDDMStructureLinks(long fileEntryTypeId,
		java.util.Set<java.lang.Long> ddmStructureIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.updateDDMStructureLinks(fileEntryTypeId,
			ddmStructureIds);
	}

	/**
	* Updates the document library file entry type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was updated
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType updateDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return _dlFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntryFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryTypeLocalService.updateFileEntryFileEntryType(dlFileEntry,
			serviceContext);
	}

	@Override
	public void updateFileEntryType(long userId, long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.updateFileEntryType(userId,
			fileEntryTypeId, name, description, ddmStructureIds, serviceContext);
	}

	@Override
	public void updateFileEntryType(long userId, long fileEntryTypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryTypeLocalService.updateFileEntryType(userId,
			fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void updateFolderFileEntryTypes(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		long defaultFileEntryTypeId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntryTypeLocalService.updateFolderFileEntryTypes(dlFolder,
			fileEntryTypeIds, defaultFileEntryTypeId, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DLFileEntryTypeLocalService getWrappedDLFileEntryTypeLocalService() {
		return _dlFileEntryTypeLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	public DLFileEntryTypeLocalService getWrappedService() {
		return _dlFileEntryTypeLocalService;
	}

	@Override
	public void setWrappedService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
}