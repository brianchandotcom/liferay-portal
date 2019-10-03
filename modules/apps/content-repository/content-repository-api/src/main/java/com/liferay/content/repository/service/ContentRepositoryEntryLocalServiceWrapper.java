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

package com.liferay.content.repository.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ContentRepositoryEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntryLocalService
 * @generated
 */
public class ContentRepositoryEntryLocalServiceWrapper
	implements ContentRepositoryEntryLocalService,
			   ServiceWrapper<ContentRepositoryEntryLocalService> {

	public ContentRepositoryEntryLocalServiceWrapper(
		ContentRepositoryEntryLocalService contentRepositoryEntryLocalService) {

		_contentRepositoryEntryLocalService =
			contentRepositoryEntryLocalService;
	}

	/**
	 * Adds the content repository entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was added
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		addContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return _contentRepositoryEntryLocalService.addContentRepositoryEntry(
			contentRepositoryEntry);
	}

	/**
	 * Creates a new content repository entry with the primary key. Does not add the content repository entry to the database.
	 *
	 * @param contentRepositoryEntryId the primary key for the new content repository entry
	 * @return the new content repository entry
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		createContentRepositoryEntry(long contentRepositoryEntryId) {

		return _contentRepositoryEntryLocalService.createContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * Deletes the content repository entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was removed
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		deleteContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return _contentRepositoryEntryLocalService.deleteContentRepositoryEntry(
			contentRepositoryEntry);
	}

	/**
	 * Deletes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws PortalException if a content repository entry with the primary key could not be found
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
			deleteContentRepositoryEntry(long contentRepositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contentRepositoryEntryLocalService.deleteContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contentRepositoryEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _contentRepositoryEntryLocalService.dynamicQuery();
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

		return _contentRepositoryEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _contentRepositoryEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _contentRepositoryEntryLocalService.dynamicQuery(
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

		return _contentRepositoryEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _contentRepositoryEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		fetchContentRepositoryEntry(long contentRepositoryEntryId) {

		return _contentRepositoryEntryLocalService.fetchContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry matching the UUID and group.
	 *
	 * @param uuid the content repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		fetchContentRepositoryEntryByUuidAndGroupId(String uuid, long groupId) {

		return _contentRepositoryEntryLocalService.
			fetchContentRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _contentRepositoryEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of content repository entries
	 */
	@Override
	public java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntries(int start, int end) {

		return _contentRepositoryEntryLocalService.getContentRepositoryEntries(
			start, end);
	}

	/**
	 * Returns all the content repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the content repository entries
	 * @param companyId the primary key of the company
	 * @return the matching content repository entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return _contentRepositoryEntryLocalService.
			getContentRepositoryEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of content repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the content repository entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching content repository entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.content.repository.model.
						ContentRepositoryEntry> orderByComparator) {

		return _contentRepositoryEntryLocalService.
			getContentRepositoryEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of content repository entries.
	 *
	 * @return the number of content repository entries
	 */
	@Override
	public int getContentRepositoryEntriesCount() {
		return _contentRepositoryEntryLocalService.
			getContentRepositoryEntriesCount();
	}

	/**
	 * Returns the content repository entry with the primary key.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws PortalException if a content repository entry with the primary key could not be found
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
			getContentRepositoryEntry(long contentRepositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contentRepositoryEntryLocalService.getContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry matching the UUID and group.
	 *
	 * @param uuid the content repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching content repository entry
	 * @throws PortalException if a matching content repository entry could not be found
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
			getContentRepositoryEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contentRepositoryEntryLocalService.
			getContentRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _contentRepositoryEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _contentRepositoryEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _contentRepositoryEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contentRepositoryEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the content repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was updated
	 */
	@Override
	public com.liferay.content.repository.model.ContentRepositoryEntry
		updateContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return _contentRepositoryEntryLocalService.updateContentRepositoryEntry(
			contentRepositoryEntry);
	}

	@Override
	public ContentRepositoryEntryLocalService getWrappedService() {
		return _contentRepositoryEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ContentRepositoryEntryLocalService contentRepositoryEntryLocalService) {

		_contentRepositoryEntryLocalService =
			contentRepositoryEntryLocalService;
	}

	private ContentRepositoryEntryLocalService
		_contentRepositoryEntryLocalService;

}