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

import com.liferay.document.library.kernel.model.DLFileShortcut;

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
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

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
	@Indexable(type = IndexableType.REINDEX)
	public DLFileShortcut addDLFileShortcut(DLFileShortcut dlFileShortcut);

	public DLFileShortcut addFileShortcut(long userId, long groupId,
		long repositoryId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public void addFileShortcutResources(DLFileShortcut fileShortcut,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFileShortcutResources(DLFileShortcut fileShortcut,
		ModelPermissions modelPermissions) throws PortalException;

	public void addFileShortcutResources(long fileShortcutId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addFileShortcutResources(long fileShortcutId,
		ModelPermissions modelPermissions) throws PortalException;

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public DLFileShortcut createDLFileShortcut(long fileShortcutId);

	/**
	* Deletes the document library file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileShortcut deleteDLFileShortcut(DLFileShortcut dlFileShortcut);

	/**
	* Deletes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileShortcut deleteDLFileShortcut(long fileShortcutId)
		throws PortalException;

	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileShortcut(DLFileShortcut fileShortcut)
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
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void disableFileShortcuts(long toFileEntryId);

	public DynamicQuery dynamicQuery();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public void enableFileShortcuts(long toFileEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileShortcut fetchDLFileShortcut(long fileShortcutId);

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileShortcut fetchDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the document library file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileShortcut getDLFileShortcut(long fileShortcutId)
		throws PortalException;

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut
	* @throws PortalException if a matching document library file shortcut could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileShortcut getDLFileShortcutByUuidAndGroupId(
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
	public List<DLFileShortcut> getDLFileShortcuts(int start, int end);

	/**
	* Returns all the document library file shortcuts matching the UUID and company.
	*
	* @param uuid the UUID of the document library file shortcuts
	* @param companyId the primary key of the company
	* @return the matching document library file shortcuts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
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
	public List<DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileShortcutsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileShortcut> getFileShortcuts(long groupId, long folderId,
		boolean active, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileShortcut> getFileShortcuts(long toFileEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public void rebuildTree(long companyId) throws PortalException;

	public void setTreePaths(long folderId, java.lang.String treePath)
		throws PortalException;

	public void updateAsset(long userId, DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws PortalException;

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFileShortcut updateDLFileShortcut(DLFileShortcut dlFileShortcut);

	public DLFileShortcut updateFileShortcut(long userId, long fileShortcutId,
		long repositoryId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public void updateFileShortcuts(long oldToFileEntryId, long newToFileEntryId);

	public void updateFileShortcutsActive(long toFileEntryId, boolean active);

	public void updateStatus(long userId, long fileShortcutId, int status,
		ServiceContext serviceContext) throws PortalException;
}