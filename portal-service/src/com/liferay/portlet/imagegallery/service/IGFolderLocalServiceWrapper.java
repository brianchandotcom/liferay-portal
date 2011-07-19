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

/**
 * <p>
 * This class is a wrapper for {@link IGFolderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderLocalService
 * @generated
 */
public class IGFolderLocalServiceWrapper implements IGFolderLocalService {
	public IGFolderLocalServiceWrapper(
		IGFolderLocalService igFolderLocalService) {
		_igFolderLocalService = igFolderLocalService;
	}

	/**
	* Adds the i g folder to the database. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @return the i g folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGFolder addIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.addIGFolder(igFolder);
	}

	/**
	* Creates a new i g folder with the primary key. Does not add the i g folder to the database.
	*
	* @param folderId the primary key for the new i g folder
	* @return the new i g folder
	*/
	public com.liferay.portlet.imagegallery.model.IGFolder createIGFolder(
		long folderId) {
		return _igFolderLocalService.createIGFolder(folderId);
	}

	/**
	* Deletes the i g folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the i g folder
	* @throws PortalException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteIGFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteIGFolder(folderId);
	}

	/**
	* Deletes the i g folder from the database. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @throws SystemException if a system exception occurred
	*/
	public void deleteIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteIGFolder(igFolder);
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
		return _igFolderLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _igFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the i g folder with the primary key.
	*
	* @param folderId the primary key of the i g folder
	* @return the i g folder
	* @throws PortalException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGFolder getIGFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolder(folderId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getPersistedModel(primaryKeyObj);
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
	public com.liferay.portlet.imagegallery.model.IGFolder getIGFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolderByUuidAndGroupId(uuid, groupId);
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
	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getIGFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFolders(start, end);
	}

	/**
	* Returns the number of i g folders.
	*
	* @return the number of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public int getIGFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getIGFoldersCount();
	}

	/**
	* Updates the i g folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @return the i g folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder);
	}

	/**
	* Updates the i g folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param igFolder the i g folder
	* @param merge whether to merge the i g folder with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the i g folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.imagegallery.model.IGFolder updateIGFolder(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateIGFolder(igFolder, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _igFolderLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_igFolderLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder addFolder(
		long userId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.addFolder(userId, parentFolderId, name,
			description, serviceContext);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folder, addGroupPermissions,
			addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.imagegallery.model.IGFolder folder,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folder, groupPermissions,
			guestPermissions);
	}

	public void addFolderResources(long folderId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folderId, addGroupPermissions,
			addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.addFolderResources(folderId, groupPermissions,
			guestPermissions);
	}

	public void deleteFolder(
		com.liferay.portlet.imagegallery.model.IGFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.deleteFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getCompanyFolders(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getCompanyFoldersCount(companyId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolder(folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	public IGFolderLocalService getWrappedIGFolderLocalService() {
		return _igFolderLocalService;
	}

	public void setWrappedIGFolderLocalService(
		IGFolderLocalService igFolderLocalService) {
		_igFolderLocalService = igFolderLocalService;
	}

	private IGFolderLocalService _igFolderLocalService;
}