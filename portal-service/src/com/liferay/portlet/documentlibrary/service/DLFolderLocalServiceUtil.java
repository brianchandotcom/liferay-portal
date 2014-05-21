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
 * Provides the local service utility for DLFolder. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLFolderLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the document library folder to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder addDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFolder(dlFolder);
	}

	/**
	* Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	*
	* @param folderId the primary key for the new document library folder
	* @return the new document library folder
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder createDLFolder(
		long folderId) {
		return getService().createDLFolder(folderId);
	}

	/**
	* Deletes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder that was removed
	* @throws PortalException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFolder(folderId);
	}

	/**
	* Deletes the document library folder from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFolder(dlFolder);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFolder(folderId);
	}

	/**
	* Returns the document library folder with the matching UUID and company.
	*
	* @param uuid the document library folder's UUID
	* @param companyId the primary key of the company
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolderByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFolderByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the document library folder matching the UUID and group.
	*
	* @param uuid the document library folder's UUID
	* @param groupId the primary key of the group
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder fetchDLFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the document library folder with the primary key.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder
	* @throws PortalException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolder(folderId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.portlet.documentlibrary.model.DLFolder getDLFolderByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolderByUuidAndCompanyId(uuid, companyId);
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
	public static com.liferay.portlet.documentlibrary.model.DLFolder getDLFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolderByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFolders(start, end);
	}

	/**
	* Returns the number of document library folders.
	*
	* @return the number of document library folders
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFoldersCount();
	}

	/**
	* Updates the document library folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFolder the document library folder
	* @return the document library folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFolder(dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFileEntryTypeDLFolder(fileEntryTypeId, folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFileEntryTypeDLFolder(long fileEntryTypeId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFileEntryTypeDLFolder(fileEntryTypeId, dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFileEntryTypeDLFolders(fileEntryTypeId, folderIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> DLFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addDLFileEntryTypeDLFolders(fileEntryTypeId, DLFolders);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearDLFileEntryTypeDLFolders(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileEntryTypeDLFolder(fileEntryTypeId, folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileEntryTypeDLFolder(fileEntryTypeId, dlFolder);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileEntryTypeDLFolders(fileEntryTypeId, folderIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> DLFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileEntryTypeDLFolders(fileEntryTypeId, DLFolders);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDLFileEntryTypeDLFolders(fileEntryTypeId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFileEntryTypeDLFolders(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDLFileEntryTypeDLFolders(fileEntryTypeId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFileEntryTypeDLFoldersCount(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryTypeDLFoldersCount(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDLFileEntryTypeDLFolder(fileEntryTypeId, folderId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasDLFileEntryTypeDLFolders(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDLFileEntryTypeDLFolders(fileEntryTypeId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setDLFileEntryTypeDLFolders(fileEntryTypeId, folderIds);
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

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description, boolean hidden,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFolder(userId, groupId, repositoryId, mountPoint,
			parentFolderId, name, description, hidden, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by more general {@link #addFolder(long,
	long, long, boolean, long, String, String, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFolder(userId, groupId, repositoryId, mountPoint,
			parentFolderId, name, description, serviceContext);
	}

	public static void deleteAll(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAll(groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFolder(dlFolder);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFolder(dlFolder, includeTrashedEntries);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder deleteFolder(
		long userId, long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFolder(userId, folderId, includeTrashedEntries);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder fetchFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder fetchFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyFolders(companyId, start, end);
	}

	public static int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyFoldersCount(companyId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileEntriesAndFileShortcuts(long, long, QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderId, status,
			start, end);
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcuts(groupId, folderId,
			queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileEntriesAndFileShortcutsCount(long, long,
	QueryDefinition)}
	*/
	@Deprecated
	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			status);
	}

	public static int getFileEntriesAndFileShortcutsCount(long groupId,
		long folderId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileEntriesAndFileShortcutsCount(groupId, folderId,
			queryDefinition);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static long getFolderId(long companyId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolderId(companyId, folderId);
	}

	public static java.util.List<java.lang.Long> getFolderIds(long groupId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolderIds(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFolders(groupId, parentFolderId, includeMountfolders);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, boolean includeMountfolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFolders(groupId, parentFolderId, includeMountfolders,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcuts(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, includeMountFolders, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			status, mimeTypes, includeMountFolders, start, end, obc);
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcuts(groupId, folderId,
			mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, includeMountFolders);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	String[], boolean, QueryDefinition)}
	*/
	@Deprecated
	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, status, mimeTypes, includeMountFolders);
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersAndFileEntriesAndFileShortcutsCount(groupId,
			folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersCount(groupId, parentFolderId, includeMountfolders);
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		int status, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFoldersCount(groupId, parentFolderId, status,
			includeMountfolders);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getMountFolder(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMountFolder(repositoryId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getMountFolders(groupId, parentFolderId, start, end, obc);
	}

	public static int getMountFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMountFoldersCount(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getNoAssetFolders()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoAssetFolders();
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static boolean hasFolderLock(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasFolderLock(userId, folderId);
	}

	public static com.liferay.portal.model.Lock lockFolder(long userId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().lockFolder(userId, folderId);
	}

	public static com.liferay.portal.model.Lock lockFolder(long userId,
		long folderId, java.lang.String owner, boolean inheritable,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .lockFolder(userId, folderId, owner, inheritable,
			expirationTime);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long userId, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveFolder(userId, folderId, parentFolderId, serviceContext);
	}

	public static void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().rebuildTree(companyId);
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFolder(groupId, parentFolderId, name, lockUuid);
	}

	public static void unlockFolder(long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unlockFolder(folderId, lockUuid);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFolder(folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, overrideFileEntryTypes,
			serviceContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolderAndFileEntryTypes(
		long userId, long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFolderAndFileEntryTypes(userId, folderId,
			parentFolderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
	}

	public static void updateLastPostDate(long folderId,
		java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateLastPostDate(folderId, lastPostDate);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateStatus(
		long userId, long folderId, int status,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, folderId, status, workflowContext,
			serviceContext);
	}

	public static DLFolderLocalService getService() {
		if (_service == null) {
			_service = (DLFolderLocalService)PortalBeanLocatorUtil.locate(DLFolderLocalService.class.getName());

			ReferenceRegistry.registerReference(DLFolderLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DLFolderLocalService service) {
	}

	private static DLFolderLocalService _service;
}