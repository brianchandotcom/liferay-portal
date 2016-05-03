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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service interface for DLFileEntryType. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFileEntryTypeLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryTypeLocalServiceUtil} to access the document library file entry type local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDLFolderDLFileEntryTypes(long folderId);

	public DLFileEntry updateFileEntryFileEntryType(DLFileEntry dlFileEntry,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the document library file entry type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntryType addDLFileEntryType(DLFileEntryType dlFileEntryType);

	public DLFileEntryType addFileEntryType(long userId, long groupId,
		java.lang.String fileEntryTypeKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, long[] ddmStructureIds,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntryType addFileEntryType(long userId, long groupId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	public DLFileEntryType createDLFileEntryType(long fileEntryTypeId);

	/**
	* Deletes the document library file entry type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntryType deleteDLFileEntryType(
		DLFileEntryType dlFileEntryType);

	/**
	* Deletes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntryType deleteDLFileEntryType(long fileEntryTypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType fetchDLFileEntryType(long fileEntryTypeId);

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType fetchDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType fetchFileEntryType(long fileEntryTypeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType fetchFileEntryType(long groupId,
		java.lang.String fileEntryTypeKey);

	/**
	* Returns the document library file entry type with the primary key.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType getDLFileEntryType(long fileEntryTypeId)
		throws PortalException;

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type
	* @throws PortalException if a matching document library file entry type could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType getDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntryType getFileEntryType(long groupId,
		java.lang.String fileEntryTypeKey) throws PortalException;

	/**
	* Updates the document library file entry type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntryType updateDLFileEntryType(
		DLFileEntryType dlFileEntryType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

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

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileEntryTypesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFolderDLFileEntryTypesCount(long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFileEntryTypes(int start, int end);

	/**
	* Returns all the document library file entry types matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entry types
	* @param companyId the primary key of the company
	* @return the matching document library file entry types, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFolderDLFileEntryTypes(long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFolderDLFileEntryTypes(long folderId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getDLFolderDLFileEntryTypes(long folderId,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFileEntryTypes(long ddmStructureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> getFolderFileEntryTypes(long[] groupIds,
		long folderId, boolean inherited) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntryType> search(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator);

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
	public long getDefaultFileEntryTypeId(long folderId)
		throws PortalException;

	/**
	* Returns the folderIds of the document library folders associated with the document library file entry type.
	*
	* @param fileEntryTypeId the fileEntryTypeId of the document library file entry type
	* @return long[] the folderIds of document library folders associated with the document library file entry type
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getDLFolderPrimaryKeys(long fileEntryTypeId);

	public void addDDMStructureLinks(long fileEntryTypeId,
		Set<java.lang.Long> ddmStructureIds);

	public void addDLFolderDLFileEntryType(long folderId,
		DLFileEntryType dlFileEntryType);

	public void addDLFolderDLFileEntryType(long folderId, long fileEntryTypeId);

	public void addDLFolderDLFileEntryTypes(long folderId,
		List<DLFileEntryType> dlFileEntryTypes);

	public void addDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds);

	public void cascadeFileEntryTypes(long userId, DLFolder dlFolder)
		throws PortalException;

	public void clearDLFolderDLFileEntryTypes(long folderId);

	public void deleteDLFolderDLFileEntryType(long folderId,
		DLFileEntryType dlFileEntryType);

	public void deleteDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId);

	public void deleteDLFolderDLFileEntryTypes(long folderId,
		List<DLFileEntryType> dlFileEntryTypes);

	public void deleteDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds);

	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException;

	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException;

	public void deleteFileEntryTypes(long groupId) throws PortalException;

	public void setDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds);

	public void unsetFolderFileEntryTypes(long folderId);

	public void updateDDMStructureLinks(long fileEntryTypeId,
		Set<java.lang.Long> ddmStructureIds) throws PortalException;

	public void updateFileEntryType(long userId, long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException;

	public void updateFileEntryType(long userId, long fileEntryTypeId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, long[] ddmStructureIds,
		ServiceContext serviceContext) throws PortalException;

	public void updateFolderFileEntryTypes(DLFolder dlFolder,
		List<java.lang.Long> fileEntryTypeIds, long defaultFileEntryTypeId,
		ServiceContext serviceContext);
}