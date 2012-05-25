/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * The interface for the document library file shortcut local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFileShortcutLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileShortcutLocalServiceUtil} to access the document library file shortcut local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the document library file shortcut to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId);

	/**
	* Deletes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the document library file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

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
		throws com.liferay.portal.kernel.exception.SystemException;

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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcut with the UUID in the group.
	*
	* @param uuid the UUID of document library file shortcut
	* @param groupId the group id of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileShortcutsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @param merge whether to merge the document library file shortcut with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the document library file shortcut that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long groupId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFileShortcutResources(long fileShortcutId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFileShortcutResources(long fileShortcutId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void disableFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void enableFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void updateFileShortcuts(long oldToFileEntryId, long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void updateStatus(long userId, long fileShortcutId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}