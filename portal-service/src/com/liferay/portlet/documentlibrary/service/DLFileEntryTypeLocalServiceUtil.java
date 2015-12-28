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
 * Provides the local service utility for DLFileEntryType. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLFileEntryTypeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addDDMStructureLinks(long fileEntryTypeId,
		java.util.Set<java.lang.Long> ddmStructureIds) {
		getService().addDDMStructureLinks(fileEntryTypeId, ddmStructureIds);
	}

	/**
	* Adds the document library file entry type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was added
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return getService().addDLFileEntryType(dlFileEntryType);
	}

	public static void addDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		getService().addDLFolderDLFileEntryType(folderId, dlFileEntryType);
	}

	public static void addDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId) {
		getService().addDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	public static void addDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes) {
		getService().addDLFolderDLFileEntryTypes(folderId, DLFileEntryTypes);
	}

	public static void addDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		getService().addDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String fileEntryTypeKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFileEntryType(userId, groupId, fileEntryTypeKey,
			nameMap, descriptionMap, ddmStructureIds, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFileEntryType(userId, groupId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void cascadeFileEntryTypes(long userId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().cascadeFileEntryTypes(userId, dlFolder);
	}

	public static void clearDLFolderDLFileEntryTypes(long folderId) {
		getService().clearDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType createDLFileEntryType(
		long fileEntryTypeId) {
		return getService().createDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Deletes the document library file entry type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was removed
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return getService().deleteDLFileEntryType(dlFileEntryType);
	}

	/**
	* Deletes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDLFileEntryType(fileEntryTypeId);
	}

	public static void deleteDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		getService().deleteDLFolderDLFileEntryType(folderId, dlFileEntryType);
	}

	public static void deleteDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId) {
		getService().deleteDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	public static void deleteDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes) {
		getService().deleteDLFolderDLFileEntryTypes(folderId, DLFileEntryTypes);
	}

	public static void deleteDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		getService().deleteDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	public static void deleteFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntryType(dlFileEntryType);
	}

	public static void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntryType(fileEntryTypeId);
	}

	public static void deleteFileEntryTypes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFileEntryTypes(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryType(
		long fileEntryTypeId) {
		return getService().fetchDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long fileEntryTypeId) {
		return getService().fetchFileEntryType(fileEntryTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long groupId, java.lang.String fileEntryTypeKey) {
		return getService().fetchFileEntryType(groupId, fileEntryTypeKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the document library file entry type with the primary key.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws PortalException if a document library file entry type with the primary key could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type matching the UUID and group.
	*
	* @param uuid the document library file entry type's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry type
	* @throws PortalException if a matching document library file entry type could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		int start, int end) {
		return getService().getDLFileEntryTypes(start, end);
	}

	/**
	* Returns all the document library file entry types matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entry types
	* @param companyId the primary key of the company
	* @return the matching document library file entry types, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getDLFileEntryTypesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getService()
				   .getDLFileEntryTypesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	*/
	public static int getDLFileEntryTypesCount() {
		return getService().getDLFileEntryTypesCount();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId) {
		return getService().getDLFolderDLFileEntryTypes(folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end) {
		return getService().getDLFolderDLFileEntryTypes(folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getService()
				   .getDLFolderDLFileEntryTypes(folderId, start, end,
			orderByComparator);
	}

	public static int getDLFolderDLFileEntryTypesCount(long folderId) {
		return getService().getDLFolderDLFileEntryTypesCount(folderId);
	}

	/**
	* Returns the folderIds of the document library folders associated with the document library file entry type.
	*
	* @param fileEntryTypeId the fileEntryTypeId of the document library file entry type
	* @return long[] the folderIds of document library folders associated with the document library file entry type
	*/
	public static long[] getDLFolderPrimaryKeys(long fileEntryTypeId) {
		return getService().getDLFolderPrimaryKeys(fileEntryTypeId);
	}

	public static long getDefaultFileEntryTypeId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefaultFileEntryTypeId(folderId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntryType(fileEntryTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long groupId, java.lang.String fileEntryTypeKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntryType(groupId, fileEntryTypeKey);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileEntryTypes(ddmStructureId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long[] groupIds) {
		return getService().getFileEntryTypes(groupIds);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFolderFileEntryTypes(
		long[] groupIds, long folderId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFolderFileEntryTypes(groupIds, folderId, inherited);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId) {
		return getService().hasDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	public static boolean hasDLFolderDLFileEntryTypes(long folderId) {
		return getService().hasDLFolderDLFileEntryTypes(folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> search(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, keywords,
			includeBasicFileEntryType, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType) {
		return getService()
				   .searchCount(companyId, groupIds, keywords,
			includeBasicFileEntryType);
	}

	public static void setDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds) {
		getService().setDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	public static void unsetFolderFileEntryTypes(long folderId) {
		getService().unsetFolderFileEntryTypes(folderId);
	}

	public static void updateDDMStructureLinks(long fileEntryTypeId,
		java.util.Set<java.lang.Long> ddmStructureIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateDDMStructureLinks(fileEntryTypeId, ddmStructureIds);
	}

	/**
	* Updates the document library file entry type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was updated
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType updateDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return getService().updateDLFileEntryType(dlFileEntryType);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntryFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFileEntryFileEntryType(dlFileEntry, serviceContext);
	}

	public static void updateFileEntryType(long userId, long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateFileEntryType(userId, fileEntryTypeId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void updateFileEntryType(long userId, long fileEntryTypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateFileEntryType(userId, fileEntryTypeId, nameMap,
			descriptionMap, ddmStructureIds, serviceContext);
	}

	public static void updateFolderFileEntryTypes(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		long defaultFileEntryTypeId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		getService()
			.updateFolderFileEntryTypes(dlFolder, fileEntryTypeIds,
			defaultFileEntryTypeId, serviceContext);
	}

	public static DLFileEntryTypeLocalService getService() {
		if (_service == null) {
			_service = (DLFileEntryTypeLocalService)PortalBeanLocatorUtil.locate(DLFileEntryTypeLocalService.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryTypeLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static DLFileEntryTypeLocalService _service;
}