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

package com.liferay.knowledge.base.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBFolderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderLocalService
 * @generated
 */
public class KBFolderLocalServiceWrapper
	implements KBFolderLocalService, ServiceWrapper<KBFolderLocalService> {

	public KBFolderLocalServiceWrapper(
		KBFolderLocalService kbFolderLocalService) {

		_kbFolderLocalService = kbFolderLocalService;
	}

	/**
	 * Adds the kb folder to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was added
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder addKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return _kbFolderLocalService.addKBFolder(kbFolder);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder addKBFolder(
			long userId, long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.addKBFolder(
			userId, groupId, parentResourceClassNameId, parentResourcePrimKey,
			name, description, serviceContext);
	}

	/**
	 * Creates a new kb folder with the primary key. Does not add the kb folder to the database.
	 *
	 * @param kbFolderId the primary key for the new kb folder
	 * @return the new kb folder
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder createKBFolder(
		long kbFolderId) {

		return _kbFolderLocalService.createKBFolder(kbFolderId);
	}

	/**
	 * Deletes the kb folder from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was removed
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return _kbFolderLocalService.deleteKBFolder(kbFolder);
	}

	/**
	 * Deletes the kb folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder that was removed
	 * @throws PortalException if a kb folder with the primary key could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
			long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.deleteKBFolder(kbFolderId);
	}

	@Override
	public void deleteKBFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbFolderLocalService.deleteKBFolders(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kbFolderLocalService.dynamicQuery();
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

		return _kbFolderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
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

		return _kbFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
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

		return _kbFolderLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _kbFolderLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kbFolderLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.fetchFirstChildKBFolder(
			groupId, kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.fetchFirstChildKBFolder(
			groupId, kbFolderId, obc);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
		long kbFolderId) {

		return _kbFolderLocalService.fetchKBFolder(kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
		String uuid, long groupId) {

		return _kbFolderLocalService.fetchKBFolder(uuid, groupId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder fetchKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.fetchKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	/**
	 * Returns the kb folder matching the UUID and group.
	 *
	 * @param uuid the kb folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder
		fetchKBFolderByUuidAndGroupId(String uuid, long groupId) {

		return _kbFolderLocalService.fetchKBFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kbFolderLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _kbFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kbFolderLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kb folder with the primary key.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder
	 * @throws PortalException if a kb folder with the primary key could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder getKBFolder(
			long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getKBFolder(kbFolderId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder getKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	/**
	 * Returns the kb folder matching the UUID and group.
	 *
	 * @param uuid the kb folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb folder
	 * @throws PortalException if a matching kb folder could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder
			getKBFolderByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getKBFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the kb folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of kb folders
	 */
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFolders(int start, int end) {

		return _kbFolderLocalService.getKBFolders(start, end);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBFolder>
			getKBFolders(
				long groupId, long parentKBFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getKBFolders(
			groupId, parentKBFolderId, start, end);
	}

	@Override
	public java.util.List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {

		return _kbFolderLocalService.getKBFoldersAndKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	@Override
	public int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return _kbFolderLocalService.getKBFoldersAndKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	/**
	 * Returns all the kb folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb folders
	 * @param companyId the primary key of the company
	 * @return the matching kb folders, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFoldersByUuidAndCompanyId(String uuid, long companyId) {

		return _kbFolderLocalService.getKBFoldersByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of kb folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb folders
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching kb folders, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFoldersByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBFolder> orderByComparator) {

		return _kbFolderLocalService.getKBFoldersByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kb folders.
	 *
	 * @return the number of kb folders
	 */
	@Override
	public int getKBFoldersCount() {
		return _kbFolderLocalService.getKBFoldersCount();
	}

	@Override
	public int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getKBFoldersCount(
			groupId, parentKBFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kbFolderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbFolderLocalService.moveKBFolder(kbFolderId, parentKBFolderId);
	}

	/**
	 * Updates the kb folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was updated
	 */
	@Override
	public com.liferay.knowledge.base.model.KBFolder updateKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return _kbFolderLocalService.updateKBFolder(kbFolder);
	}

	@Override
	public com.liferay.knowledge.base.model.KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbFolderLocalService.updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, serviceContext);
	}

	@Override
	public KBFolderLocalService getWrappedService() {
		return _kbFolderLocalService;
	}

	@Override
	public void setWrappedService(KBFolderLocalService kbFolderLocalService) {
		_kbFolderLocalService = kbFolderLocalService;
	}

	private KBFolderLocalService _kbFolderLocalService;

}