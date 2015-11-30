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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for DLFileShortcut. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl
 * @generated
 */
@ProviderType
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
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long groupId, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	public void addFileShortcutResources(long fileShortcutId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFileShortcutResources(long fileShortcutId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId);

	/**
	* Deletes the document library file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	/**
	* Deletes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		long fileShortcutId) throws PortalException;

	@com.liferay.portal.kernel.systemevent.SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws PortalException;

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException;

	public void deleteFileShortcuts(long groupId, long folderId)
		throws PortalException;

	public void deleteFileShortcuts(long groupId, long folderId,
		boolean includeTrashedEntries) throws PortalException;

	public void deleteFileShortcuts(long toFileEntryId)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public void disableFileShortcuts(long toFileEntryId);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	public void enableFileShortcuts(long toFileEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcut(
		long fileShortcutId);

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the document library file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId) throws PortalException;

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut
	* @throws PortalException if a matching document library file shortcut could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end);

	/**
	* Returns all the document library file shortcuts matching the UUID and company.
	*
	* @param uuid the UUID of the document library file shortcuts
	* @param companyId the primary key of the company
	* @return the matching document library file shortcuts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of document library file shortcuts matching the UUID and company.
	*
	* @param uuid the UUID of the document library file shortcuts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching document library file shortcuts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileShortcut> orderByComparator);

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileShortcutsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long toFileEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	public void rebuildTree(long companyId) throws PortalException;

	public void setTreePaths(long folderId, java.lang.String treePath)
		throws PortalException;

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws PortalException;

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void updateFileShortcuts(long oldToFileEntryId, long newToFileEntryId);

	public void updateFileShortcutsActive(long toFileEntryId, boolean active);

	public void updateStatus(long userId, long fileShortcutId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}