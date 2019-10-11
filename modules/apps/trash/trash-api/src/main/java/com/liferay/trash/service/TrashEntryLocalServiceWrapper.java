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

package com.liferay.trash.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TrashEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryLocalService
 * @generated
 */
public class TrashEntryLocalServiceWrapper
	implements ServiceWrapper<TrashEntryLocalService>, TrashEntryLocalService {

	public TrashEntryLocalServiceWrapper(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Moves an entry to trash.
	 *
	 * @param userId the primary key of the user removing the entity
	 * @param groupId the primary key of the entry's group
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @param classUuid the UUID of the entity's class
	 * @param referrerClassName the referrer class name used to add a deletion
	 {@link SystemEvent}
	 * @param status the status of the entity prior to being moved to trash
	 * @param statusOVPs the primary keys and statuses of any of the entry's
	 versions (e.g., {@link
	 com.liferay.portlet.documentlibrary.model.DLFileVersion})
	 * @param typeSettingsProperties the type settings properties
	 * @return the trashEntry
	 */
	@Override
	public com.liferay.trash.model.TrashEntry addTrashEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int status,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair<Long, Integer>>
					statusOVPs,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.addTrashEntry(
			userId, groupId, className, classPK, classUuid, referrerClassName,
			status, statusOVPs, typeSettingsProperties);
	}

	/**
	 * Adds the trash entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was added
	 */
	@Override
	public com.liferay.trash.model.TrashEntry addTrashEntry(
		com.liferay.trash.model.TrashEntry trashEntry) {

		return _trashEntryLocalService.addTrashEntry(trashEntry);
	}

	@Override
	public void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		_trashEntryLocalService.checkEntries();
	}

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param entryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	@Override
	public com.liferay.trash.model.TrashEntry createTrashEntry(long entryId) {
		return _trashEntryLocalService.createTrashEntry(entryId);
	}

	@Override
	public void deleteEntries(long groupId) {
		_trashEntryLocalService.deleteEntries(groupId);
	}

	@Override
	public void deleteEntries(long groupId, boolean deleteTrashedModels) {
		_trashEntryLocalService.deleteEntries(groupId, deleteTrashedModels);
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry deleteEntry(long entryId) {
		return _trashEntryLocalService.deleteEntry(entryId);
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * @param className the class name of entity
	 * @param classPK the primary key of the entry
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry deleteEntry(
		String className, long classPK) {

		return _trashEntryLocalService.deleteEntry(className, classPK);
	}

	@Override
	public com.liferay.trash.model.TrashEntry deleteEntry(
		com.liferay.trash.model.TrashEntry trashEntry) {

		return _trashEntryLocalService.deleteEntry(trashEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws PortalException if a trash entry with the primary key could not be found
	 */
	@Override
	public com.liferay.trash.model.TrashEntry deleteTrashEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.deleteTrashEntry(entryId);
	}

	/**
	 * Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was removed
	 */
	@Override
	public com.liferay.trash.model.TrashEntry deleteTrashEntry(
		com.liferay.trash.model.TrashEntry trashEntry) {

		return _trashEntryLocalService.deleteTrashEntry(trashEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _trashEntryLocalService.dynamicQuery();
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

		return _trashEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashEntryModelImpl</code>.
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

		return _trashEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashEntryModelImpl</code>.
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

		return _trashEntryLocalService.dynamicQuery(
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

		return _trashEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _trashEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param entryId the primary key of the entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry fetchEntry(long entryId) {
		return _trashEntryLocalService.fetchEntry(entryId);
	}

	/**
	 * Returns the trash entry with the entity class name and primary key.
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry fetchEntry(
		String className, long classPK) {

		return _trashEntryLocalService.fetchEntry(className, classPK);
	}

	@Override
	public com.liferay.trash.model.TrashEntry fetchTrashEntry(long entryId) {
		return _trashEntryLocalService.fetchTrashEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _trashEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param groupId the primary key of the group
	 * @return the trash entries with the group ID
	 */
	@Override
	public java.util.List<com.liferay.trash.model.TrashEntry> getEntries(
		long groupId) {

		return _trashEntryLocalService.getEntries(groupId);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @return the range of matching trash entries
	 */
	@Override
	public java.util.List<com.liferay.trash.model.TrashEntry> getEntries(
		long groupId, int start, int end) {

		return _trashEntryLocalService.getEntries(groupId, start, end);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param obc the comparator to order the trash entries (optionally
	 <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>obc</code>
	 */
	@Override
	public java.util.List<com.liferay.trash.model.TrashEntry> getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.trash.model.TrashEntry> obc) {

		return _trashEntryLocalService.getEntries(groupId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.trash.model.TrashEntry> getEntries(
		long groupId, String className) {

		return _trashEntryLocalService.getEntries(groupId, className);
	}

	/**
	 * Returns the number of trash entries with the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching trash entries
	 */
	@Override
	public int getEntriesCount(long groupId) {
		return _trashEntryLocalService.getEntriesCount(groupId);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getEntry(entryId);
	}

	/**
	 * Returns the entry with the entity class name and primary key.
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public com.liferay.trash.model.TrashEntry getEntry(
			String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getEntry(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _trashEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _trashEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 */
	@Override
	public java.util.List<com.liferay.trash.model.TrashEntry> getTrashEntries(
		int start, int end) {

		return _trashEntryLocalService.getTrashEntries(start, end);
	}

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 */
	@Override
	public int getTrashEntriesCount() {
		return _trashEntryLocalService.getTrashEntriesCount();
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws PortalException if a trash entry with the primary key could not be found
	 */
	@Override
	public com.liferay.trash.model.TrashEntry getTrashEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getTrashEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort) {

		return _trashEntryLocalService.search(
			companyId, groupId, userId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.trash.model.TrashEntry> searchTrashEntries(
			long companyId, long groupId, long userId, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort) {

		return _trashEntryLocalService.searchTrashEntries(
			companyId, groupId, userId, keywords, start, end, sort);
	}

	/**
	 * Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was updated
	 */
	@Override
	public com.liferay.trash.model.TrashEntry updateTrashEntry(
		com.liferay.trash.model.TrashEntry trashEntry) {

		return _trashEntryLocalService.updateTrashEntry(trashEntry);
	}

	@Override
	public TrashEntryLocalService getWrappedService() {
		return _trashEntryLocalService;
	}

	@Override
	public void setWrappedService(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	private TrashEntryLocalService _trashEntryLocalService;

}