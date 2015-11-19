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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileShortcutLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutLocalService
 * @generated
 */
@ProviderType
public class DLFileShortcutLocalServiceWrapper
	implements DLFileShortcutLocalService,
		ServiceWrapper<DLFileShortcutLocalService> {
	public DLFileShortcutLocalServiceWrapper(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	/**
	* Adds the document library file shortcut to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was added
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcutLocalService.addDLFileShortcut(dlFileShortcut);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long groupId, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.addFileShortcut(userId, groupId,
			repositoryId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			modelPermissions);
	}

	@Override
	public void addFileShortcutResources(long fileShortcutId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFileShortcutResources(long fileShortcutId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			modelPermissions);
	}

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId) {
		return _dlFileShortcutLocalService.createDLFileShortcut(fileShortcutId);
	}

	/**
	* Deletes the document library file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was removed
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcutLocalService.deleteDLFileShortcut(dlFileShortcut);
	}

	/**
	* Deletes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.deleteFileShortcuts(groupId, folderId);
	}

	@Override
	public void deleteFileShortcuts(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.deleteFileShortcuts(groupId, folderId,
			includeTrashedEntries);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.deleteFileShortcuts(toFileEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void disableFileShortcuts(long toFileEntryId) {
		_dlFileShortcutLocalService.disableFileShortcuts(toFileEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileShortcutLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery, start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileShortcutLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _dlFileShortcutLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void enableFileShortcuts(long toFileEntryId) {
		_dlFileShortcutLocalService.enableFileShortcuts(toFileEntryId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcut(
		long fileShortcutId) {
		return _dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);
	}

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _dlFileShortcutLocalService.fetchDLFileShortcutByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileShortcutLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the document library file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);
	}

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut
	* @throws PortalException if a matching document library file shortcut could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.getDLFileShortcutByUuidAndGroupId(uuid,
			groupId);
	}

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
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end) {
		return _dlFileShortcutLocalService.getDLFileShortcuts(start, end);
	}

	/**
	* Returns all the document library file shortcuts matching the UUID and company.
	*
	* @param uuid the UUID of the document library file shortcuts
	* @param companyId the primary key of the company
	* @return the matching document library file shortcuts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _dlFileShortcutLocalService.getDLFileShortcutsByUuidAndCompanyId(uuid,
			companyId);
	}

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
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileShortcut> orderByComparator) {
		return _dlFileShortcutLocalService.getDLFileShortcutsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	*/
	@Override
	public int getDLFileShortcutsCount() {
		return _dlFileShortcutLocalService.getDLFileShortcutsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _dlFileShortcutLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status, int start,
		int end) {
		return _dlFileShortcutLocalService.getFileShortcuts(groupId, folderId,
			active, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long toFileEntryId) {
		return _dlFileShortcutLocalService.getFileShortcuts(toFileEntryId);
	}

	@Override
	public int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status) {
		return _dlFileShortcutLocalService.getFileShortcutsCount(groupId,
			folderId, active, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _dlFileShortcutLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlFileShortcutLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.rebuildTree(companyId);
	}

	@Override
	public void setTreePaths(long folderId, java.lang.String treePath)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.setTreePaths(folderId, treePath);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.updateAsset(userId, fileShortcut,
			assetCategoryIds, assetTagNames);
	}

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was updated
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcutLocalService.updateDLFileShortcut(dlFileShortcut);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcutLocalService.updateFileShortcut(userId,
			fileShortcutId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void updateFileShortcuts(long oldToFileEntryId, long newToFileEntryId) {
		_dlFileShortcutLocalService.updateFileShortcuts(oldToFileEntryId,
			newToFileEntryId);
	}

	@Override
	public void updateFileShortcutsActive(long toFileEntryId, boolean active) {
		_dlFileShortcutLocalService.updateFileShortcutsActive(toFileEntryId,
			active);
	}

	@Override
	public void updateStatus(long userId, long fileShortcutId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileShortcutLocalService.updateStatus(userId, fileShortcutId,
			status, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DLFileShortcutLocalService getWrappedDLFileShortcutLocalService() {
		return _dlFileShortcutLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	@Override
	public DLFileShortcutLocalService getWrappedService() {
		return _dlFileShortcutLocalService;
	}

	@Override
	public void setWrappedService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	private DLFileShortcutLocalService _dlFileShortcutLocalService;
}