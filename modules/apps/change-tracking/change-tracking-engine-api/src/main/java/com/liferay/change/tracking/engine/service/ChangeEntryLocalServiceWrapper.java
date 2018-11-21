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

package com.liferay.change.tracking.engine.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangeEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeEntryLocalService
 * @generated
 */
@ProviderType
public class ChangeEntryLocalServiceWrapper implements ChangeEntryLocalService,
	ServiceWrapper<ChangeEntryLocalService> {
	public ChangeEntryLocalServiceWrapper(
		ChangeEntryLocalService changeEntryLocalService) {
		_changeEntryLocalService = changeEntryLocalService;
	}

	@Override
	public void addChangeCollectionChangeEntries(long changeCollectionId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		_changeEntryLocalService.addChangeCollectionChangeEntries(changeCollectionId,
			changeEntries);
	}

	@Override
	public void addChangeCollectionChangeEntries(long changeCollectionId,
		long[] changeEntryIds) {
		_changeEntryLocalService.addChangeCollectionChangeEntries(changeCollectionId,
			changeEntryIds);
	}

	@Override
	public void addChangeCollectionChangeEntry(long changeCollectionId,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		_changeEntryLocalService.addChangeCollectionChangeEntry(changeCollectionId,
			changeEntry);
	}

	@Override
	public void addChangeCollectionChangeEntry(long changeCollectionId,
		long changeEntryId) {
		_changeEntryLocalService.addChangeCollectionChangeEntry(changeCollectionId,
			changeEntryId);
	}

	/**
	* Adds the change entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was added
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry addChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return _changeEntryLocalService.addChangeEntry(changeEntry);
	}

	@Override
	public void clearChangeCollectionChangeEntries(long changeCollectionId) {
		_changeEntryLocalService.clearChangeCollectionChangeEntries(changeCollectionId);
	}

	/**
	* Creates a new change entry with the primary key. Does not add the change entry to the database.
	*
	* @param changeEntryId the primary key for the new change entry
	* @return the new change entry
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry createChangeEntry(
		long changeEntryId) {
		return _changeEntryLocalService.createChangeEntry(changeEntryId);
	}

	@Override
	public void deleteChangeCollectionChangeEntries(long changeCollectionId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		_changeEntryLocalService.deleteChangeCollectionChangeEntries(changeCollectionId,
			changeEntries);
	}

	@Override
	public void deleteChangeCollectionChangeEntries(long changeCollectionId,
		long[] changeEntryIds) {
		_changeEntryLocalService.deleteChangeCollectionChangeEntries(changeCollectionId,
			changeEntryIds);
	}

	@Override
	public void deleteChangeCollectionChangeEntry(long changeCollectionId,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		_changeEntryLocalService.deleteChangeCollectionChangeEntry(changeCollectionId,
			changeEntry);
	}

	@Override
	public void deleteChangeCollectionChangeEntry(long changeCollectionId,
		long changeEntryId) {
		_changeEntryLocalService.deleteChangeCollectionChangeEntry(changeCollectionId,
			changeEntryId);
	}

	/**
	* Deletes the change entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was removed
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry deleteChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return _changeEntryLocalService.deleteChangeEntry(changeEntry);
	}

	/**
	* Deletes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry that was removed
	* @throws PortalException if a change entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry deleteChangeEntry(
		long changeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeEntryLocalService.deleteChangeEntry(changeEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changeEntryLocalService.dynamicQuery();
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
		return _changeEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _changeEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changeEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry fetchChangeEntry(
		long changeEntryId) {
		return _changeEntryLocalService.fetchChangeEntry(changeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changeEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId) {
		return _changeEntryLocalService.getChangeCollectionChangeEntries(changeCollectionId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId, int start, int end) {
		return _changeEntryLocalService.getChangeCollectionChangeEntries(changeCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeEntry> orderByComparator) {
		return _changeEntryLocalService.getChangeCollectionChangeEntries(changeCollectionId,
			start, end, orderByComparator);
	}

	@Override
	public int getChangeCollectionChangeEntriesCount(long changeCollectionId) {
		return _changeEntryLocalService.getChangeCollectionChangeEntriesCount(changeCollectionId);
	}

	/**
	* Returns the changeCollectionIds of the change collections associated with the change entry.
	*
	* @param changeEntryId the changeEntryId of the change entry
	* @return long[] the changeCollectionIds of change collections associated with the change entry
	*/
	@Override
	public long[] getChangeCollectionPrimaryKeys(long changeEntryId) {
		return _changeEntryLocalService.getChangeCollectionPrimaryKeys(changeEntryId);
	}

	/**
	* Returns a range of all the change entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @return the range of change entries
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		int start, int end) {
		return _changeEntryLocalService.getChangeEntries(start, end);
	}

	/**
	* Returns the number of change entries.
	*
	* @return the number of change entries
	*/
	@Override
	public int getChangeEntriesCount() {
		return _changeEntryLocalService.getChangeEntriesCount();
	}

	/**
	* Returns the change entry with the primary key.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry
	* @throws PortalException if a change entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry getChangeEntry(
		long changeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeEntryLocalService.getChangeEntry(changeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changeEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changeEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeEntryLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasChangeCollectionChangeEntries(long changeCollectionId) {
		return _changeEntryLocalService.hasChangeCollectionChangeEntries(changeCollectionId);
	}

	@Override
	public boolean hasChangeCollectionChangeEntry(long changeCollectionId,
		long changeEntryId) {
		return _changeEntryLocalService.hasChangeCollectionChangeEntry(changeCollectionId,
			changeEntryId);
	}

	@Override
	public void setChangeCollectionChangeEntries(long changeCollectionId,
		long[] changeEntryIds) {
		_changeEntryLocalService.setChangeCollectionChangeEntries(changeCollectionId,
			changeEntryIds);
	}

	/**
	* Updates the change entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was updated
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeEntry updateChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return _changeEntryLocalService.updateChangeEntry(changeEntry);
	}

	@Override
	public ChangeEntryLocalService getWrappedService() {
		return _changeEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ChangeEntryLocalService changeEntryLocalService) {
		_changeEntryLocalService = changeEntryLocalService;
	}

	private ChangeEntryLocalService _changeEntryLocalService;
}