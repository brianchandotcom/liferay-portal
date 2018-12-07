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

package com.liferay.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangeTrackingEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntryLocalService
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryLocalServiceWrapper
	implements ChangeTrackingEntryLocalService,
		ServiceWrapper<ChangeTrackingEntryLocalService> {
	public ChangeTrackingEntryLocalServiceWrapper(
		ChangeTrackingEntryLocalService changeTrackingEntryLocalService) {
		_changeTrackingEntryLocalService = changeTrackingEntryLocalService;
	}

	@Override
	public void addChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		_changeTrackingEntryLocalService.addChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntries);
	}

	@Override
	public void addChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		_changeTrackingEntryLocalService.addChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	@Override
	public void addChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		_changeTrackingEntryLocalService.addChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntry);
	}

	@Override
	public void addChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		_changeTrackingEntryLocalService.addChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	/**
	* Adds the change tracking entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was added
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry addChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return _changeTrackingEntryLocalService.addChangeTrackingEntry(changeTrackingEntry);
	}

	@Override
	public void clearChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		_changeTrackingEntryLocalService.clearChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	/**
	* Creates a new change tracking entry with the primary key. Does not add the change tracking entry to the database.
	*
	* @param changeTrackingEntryId the primary key for the new change tracking entry
	* @return the new change tracking entry
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry createChangeTrackingEntry(
		long changeTrackingEntryId) {
		return _changeTrackingEntryLocalService.createChangeTrackingEntry(changeTrackingEntryId);
	}

	@Override
	public void deleteChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		_changeTrackingEntryLocalService.deleteChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntries);
	}

	@Override
	public void deleteChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		_changeTrackingEntryLocalService.deleteChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	@Override
	public void deleteChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		_changeTrackingEntryLocalService.deleteChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntry);
	}

	@Override
	public void deleteChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		_changeTrackingEntryLocalService.deleteChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	/**
	* Deletes the change tracking entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was removed
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry deleteChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return _changeTrackingEntryLocalService.deleteChangeTrackingEntry(changeTrackingEntry);
	}

	/**
	* Deletes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry that was removed
	* @throws PortalException if a change tracking entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry deleteChangeTrackingEntry(
		long changeTrackingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingEntryLocalService.deleteChangeTrackingEntry(changeTrackingEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changeTrackingEntryLocalService.dynamicQuery();
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
		return _changeTrackingEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeTrackingEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeTrackingEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _changeTrackingEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changeTrackingEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry fetchChangeTrackingEntry(
		long changeTrackingEntryId) {
		return _changeTrackingEntryLocalService.fetchChangeTrackingEntry(changeTrackingEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changeTrackingEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		return _changeTrackingEntryLocalService.getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, int start, int end) {
		return _changeTrackingEntryLocalService.getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingEntry> orderByComparator) {
		return _changeTrackingEntryLocalService.getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			start, end, orderByComparator);
	}

	@Override
	public int getChangeTrackingCollectionChangeTrackingEntriesCount(
		long changeTrackingCollectionId) {
		return _changeTrackingEntryLocalService.getChangeTrackingCollectionChangeTrackingEntriesCount(changeTrackingCollectionId);
	}

	/**
	* Returns the changeTrackingCollectionIds of the change tracking collections associated with the change tracking entry.
	*
	* @param changeTrackingEntryId the changeTrackingEntryId of the change tracking entry
	* @return long[] the changeTrackingCollectionIds of change tracking collections associated with the change tracking entry
	*/
	@Override
	public long[] getChangeTrackingCollectionPrimaryKeys(
		long changeTrackingEntryId) {
		return _changeTrackingEntryLocalService.getChangeTrackingCollectionPrimaryKeys(changeTrackingEntryId);
	}

	/**
	* Returns a range of all the change tracking entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @return the range of change tracking entries
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		int start, int end) {
		return _changeTrackingEntryLocalService.getChangeTrackingEntries(start,
			end);
	}

	/**
	* Returns the number of change tracking entries.
	*
	* @return the number of change tracking entries
	*/
	@Override
	public int getChangeTrackingEntriesCount() {
		return _changeTrackingEntryLocalService.getChangeTrackingEntriesCount();
	}

	/**
	* Returns the change tracking entry with the primary key.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry
	* @throws PortalException if a change tracking entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry getChangeTrackingEntry(
		long changeTrackingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingEntryLocalService.getChangeTrackingEntry(changeTrackingEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changeTrackingEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changeTrackingEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingEntryLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		return _changeTrackingEntryLocalService.hasChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	@Override
	public boolean hasChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		return _changeTrackingEntryLocalService.hasChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	@Override
	public void setChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		_changeTrackingEntryLocalService.setChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	/**
	* Updates the change tracking entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was updated
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingEntry updateChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return _changeTrackingEntryLocalService.updateChangeTrackingEntry(changeTrackingEntry);
	}

	@Override
	public ChangeTrackingEntryLocalService getWrappedService() {
		return _changeTrackingEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ChangeTrackingEntryLocalService changeTrackingEntryLocalService) {
		_changeTrackingEntryLocalService = changeTrackingEntryLocalService;
	}

	private ChangeTrackingEntryLocalService _changeTrackingEntryLocalService;
}