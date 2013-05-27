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
public class DLFileEntryTypeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the document library file entry type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFileEntryType(dlFileEntryType);
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
	* Deletes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws PortalException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Deletes the document library file entry type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType deleteDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileEntryType(dlFileEntryType);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFileEntryType(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type with the primary key.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws PortalException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryType(fileEntryTypeId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryTypes(start, end);
	}

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFileEntryTypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryTypesCount();
	}

	/**
	* Updates the document library file entry type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryType the document library file entry type
	* @return the document library file entry type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType updateDLFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileEntryType(dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFolderDLFileEntryType(folderId, dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFolderDLFileEntryTypes(folderId, DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearDLFolderDLFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFolderDLFileEntryType(long folderId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFolderDLFileEntryType(folderId, dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFolderDLFileEntryTypes(long folderId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFolderDLFileEntryTypes(folderId, DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolderDLFileEntryTypes(folderId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFolderDLFileEntryTypes(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDLFolderDLFileEntryTypes(folderId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFolderDLFileEntryTypesCount(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolderDLFileEntryTypesCount(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDLFolderDLFileEntryType(long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDLFolderDLFileEntryType(folderId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDLFolderDLFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDLFolderDLFileEntryTypes(folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setDLFolderDLFileEntryTypes(long folderId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setDLFolderDLFileEntryTypes(folderId, fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDDMStructureDLFileEntryType(structureId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructureDLFileEntryType(long structureId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDDMStructureDLFileEntryType(structureId, dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addDDMStructureDLFileEntryTypes(structureId, fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructureDLFileEntryTypes(long structureId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addDDMStructureDLFileEntryTypes(structureId, DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearDDMStructureDLFileEntryTypes(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteDDMStructureDLFileEntryType(structureId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureDLFileEntryType(long structureId,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteDDMStructureDLFileEntryType(structureId, dlFileEntryType);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteDDMStructureDLFileEntryTypes(structureId, fileEntryTypeIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureDLFileEntryTypes(long structureId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> DLFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteDDMStructureDLFileEntryTypes(structureId, DLFileEntryTypes);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDDMStructureDLFileEntryTypes(structureId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDDMStructureDLFileEntryTypes(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDDMStructureDLFileEntryTypes(structureId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMStructureDLFileEntryTypesCount(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureDLFileEntryTypesCount(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDDMStructureDLFileEntryType(long structureId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasDDMStructureDLFileEntryType(structureId, fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDDMStructureDLFileEntryTypes(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDDMStructureDLFileEntryTypes(structureId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setDDMStructureDLFileEntryTypes(long structureId,
		long[] fileEntryTypeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setDDMStructureDLFileEntryTypes(structureId, fileEntryTypeIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String fileEntryTypeKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntryType(userId, groupId, fileEntryTypeKey,
			nameMap, descriptionMap, ddmStructureIds, serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileEntryType(userId, groupId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void cascadeFileEntryTypes(long userId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().cascadeFileEntryTypes(userId, dlFolder);
	}

	public static void deleteFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntryType(dlFileEntryType);
	}

	public static void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntryType(fileEntryTypeId);
	}

	public static void deleteFileEntryTypes(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntryTypes(groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFileEntryType(fileEntryTypeId);
	}

	public static long getDefaultFileEntryTypeId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultFileEntryTypeId(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryType(fileEntryTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long groupId, java.lang.String fileEntryTypeKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryType(groupId, fileEntryTypeKey);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryTypes(groupIds);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFolderFileEntryTypes(
		long[] groupIds, long folderId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFolderFileEntryTypes(groupIds, folderId, inherited);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> search(
		long companyId, long[] groupIds, java.lang.String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, keywords,
			includeBasicFileEntryType, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, boolean includeBasicFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, keywords,
			includeBasicFileEntryType);
	}

	public static void unsetFolderFileEntryTypes(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetFolderFileEntryTypes(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntryFileEntryType(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntryFileEntryType(dlFileEntry, serviceContext);
	}

	public static void updateFileEntryType(long userId, long fileEntryTypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntryType(userId, fileEntryTypeId, nameMap,
			descriptionMap, ddmStructureIds, serviceContext);
	}

	public static void updateFileEntryType(long userId, long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntryType(userId, fileEntryTypeId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void updateFolderFileEntryTypes(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		long defaultFileEntryTypeId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLFileEntryTypeLocalService service) {
	}

	private static DLFileEntryTypeLocalService _service;
}