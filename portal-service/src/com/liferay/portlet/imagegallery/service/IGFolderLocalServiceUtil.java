/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the i g folder local service. This utility wraps {@link com.liferay.portlet.imagegallery.service.impl.IGFolderLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IGFolderLocalService
 * @see com.liferay.portlet.imagegallery.service.base.IGFolderLocalServiceBaseImpl
 * @see com.liferay.portlet.imagegallery.service.impl.IGFolderLocalServiceImpl
 * @generated
 */
public class IGFolderLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.imagegallery.service.impl.IGFolderLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the i g folder to the database. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @return the i g folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder addIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addIGFolder(igFolder);
	}

	/**
	* Creates a new i g folder with the primary key. Does not add the i g folder to the database.
	*
	* @param folderId the primary key for the new i g folder
	* @return the new i g folder
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder createIGFolder(
		long folderId) {
		return getService().createIGFolder(folderId);
	}

	/**
	* Deletes the i g folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the i g folder
	* @throws PortalException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteIGFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteIGFolder(folderId);
	}

	/**
	* Deletes the i g folder from the database. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteIGFolder(igFolder);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* Returns the i g folder with the primary key.
	*
	* @param folderId the primary key of the i g folder
	* @return the i g folder
	* @throws PortalException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder getIGFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getIGFolder(folderId);
	}

	/**
	* Returns the i g folder with the UUID in the group.
	*
	* @param uuid the UUID of i g folder
	* @param groupId the group id of the i g folder
	* @return the i g folder
	* @throws PortalException if a i g folder with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder getIGFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getIGFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the i g folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getIGFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getIGFolders(start, end);
	}

	/**
	* Returns the number of i g folders.
	*
	* @return the number of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int getIGFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getIGFoldersCount();
	}

	/**
	* Updates the i g folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @return the i g folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateIGFolder(igFolder);
	}

	/**
	* Updates the i g folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @param merge whether to merge the i g folder with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the i g folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateIGFolder(igFolder, merge);
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

	public static com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long userId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFolder(userId, parentFolderId, name, description,
			serviceContext);
	}

	public static void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFolderResources(folder, addGroupPermissions, addGuestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFolderResources(folder, groupPermissions, guestPermissions);
	}

	public static void addFolderResources(long folderId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFolderResources(folderId, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addFolderResources(long folderId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFolderResources(folderId, groupPermissions, guestPermissions);
	}

	public static void deleteFolder(
		com.liferay.portlet.imagegallery.model.IGFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolder(folder);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolders(groupId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyFolders(companyId, start, end);
	}

	public static int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyFoldersCount(companyId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	public static IGFolderLocalService getService() {
		if (_service == null) {
			_service = (IGFolderLocalService)PortalBeanLocatorUtil.locate(IGFolderLocalService.class.getName());

			ReferenceRegistry.registerReference(IGFolderLocalServiceUtil.class,
				"_service");
			MethodCache.remove(IGFolderLocalService.class);
		}

		return _service;
	}

	public void setService(IGFolderLocalService service) {
		MethodCache.remove(IGFolderLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(IGFolderLocalServiceUtil.class,
			"_service");
		MethodCache.remove(IGFolderLocalService.class);
	}

	private static IGFolderLocalService _service;
}