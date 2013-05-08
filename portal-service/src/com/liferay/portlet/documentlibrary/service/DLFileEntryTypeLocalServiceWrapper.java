/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link DLFileEntryTypeLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryTypeLocalService
 * @generated
 */
public class DLFileEntryTypeLocalServiceWrapper
	implements DLFileEntryTypeLocalService,
		ServiceWrapper<DLFileEntryTypeLocalService> {
	public DLFileEntryTypeLocalServiceWrapper(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	/**
	* Adds the document library file entry type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.addDLFileEntryType(dlFileEntryType);
	}

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType createDLFileEntryType(
		long fileEntryTypeId) {
		return _dlFileEntryTypeLocalService.createDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Deletes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws PortalException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.deleteDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Deletes the document library file entry type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.deleteDLFileEntryType(dlFileEntryType);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileEntryTypeLocalService.dynamicQuery();
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _dlFileEntryTypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.fetchDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type with the primary key.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws PortalException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFileEntryType(fileEntryTypeId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type
	* @throws PortalException if a matching document library file entry type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypes(start, end);
	}

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileEntryTypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFileEntryTypesCount();
	}

	/**
	* Updates the document library file entry type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType updateDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolderDLFileEntryType(long folderId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryType(folderId,
			dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDLFolderDLFileEntryTypes(folderId,
			DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearDLFolderDLFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.clearDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryType(folderId,
			dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDLFolderDLFileEntryTypes(folderId,
			DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(folderId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFolderDLFileEntryTypesCount(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypesCount(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.hasDLFolderDLFileEntryType(folderId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasDLFolderDLFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.hasDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.setDLFolderDLFileEntryTypes(folderId,
			fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDDMStructureDLFileEntryType(structureId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructureDLFileEntryType(long structureId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDDMStructureDLFileEntryType(structureId,
			dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDDMStructureDLFileEntryTypes(structureId,
			fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructureDLFileEntryTypes(long structureId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.addDDMStructureDLFileEntryTypes(structureId,
			DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearDDMStructureDLFileEntryTypes(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.clearDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDDMStructureDLFileEntryType(structureId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureDLFileEntryType(long structureId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDDMStructureDLFileEntryType(structureId,
			dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDDMStructureDLFileEntryTypes(structureId,
			fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureDLFileEntryTypes(long structureId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteDDMStructureDLFileEntryTypes(structureId,
			DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDDMStructureDLFileEntryTypes(structureId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDDMStructureDLFileEntryTypes(structureId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMStructureDLFileEntryTypesCount(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDDMStructureDLFileEntryTypesCount(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.hasDDMStructureDLFileEntryType(structureId,
			fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasDDMStructureDLFileEntryTypes(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.hasDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.setDDMStructureDLFileEntryTypes(structureId,
			fileEntryTypeIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlFileEntryTypeLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFileEntryTypeLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.addFileEntryType(userId, groupId,
			name, description, ddmStructureIds, serviceContext);
	}

	public void cascadeFileEntryTypes(long userId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.cascadeFileEntryTypes(userId, dlFolder);
	}

	public void deleteFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
	}

	public void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	public void deleteFileEntryTypes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.deleteFileEntryTypes(groupId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.fetchFileEntryType(fileEntryTypeId);
	}

	public long getDefaultFileEntryTypeId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getFileEntryType(groupId, name);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getFileEntryTypes(groupIds);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFolderFileEntryTypes(
		long[] groupIds, long folderId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.getFolderFileEntryTypes(groupIds,
			folderId, inherited);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> search(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.search(companyId, groupIds,
			keywords, includeBasicFileEntryType, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.searchCount(companyId, groupIds,
			keywords, includeBasicFileEntryType);
	}

	public void unsetFolderFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.unsetFolderFileEntryTypes(folderId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntryFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeLocalService.updateFileEntryFileEntryType(dlFileEntry,
			serviceContext);
	}

	public void updateFileEntryType(long userId, long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.updateFileEntryType(userId,
			fileEntryTypeId, name, description, ddmStructureIds, serviceContext);
	}

	public void updateFolderFileEntryTypes(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		long defaultFileEntryTypeId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeLocalService.updateFolderFileEntryTypes(dlFolder,
			fileEntryTypeIds, defaultFileEntryTypeId, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLFileEntryTypeLocalService getWrappedDLFileEntryTypeLocalService() {
		return _dlFileEntryTypeLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	public DLFileEntryTypeLocalService getWrappedService() {
		return _dlFileEntryTypeLocalService;
	}

	public void setWrappedService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
}