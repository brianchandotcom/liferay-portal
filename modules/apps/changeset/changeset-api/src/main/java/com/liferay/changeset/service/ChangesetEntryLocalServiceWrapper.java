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

package com.liferay.changeset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangesetEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntryLocalService
 * @generated
 */
@ProviderType
public class ChangesetEntryLocalServiceWrapper
	implements ChangesetEntryLocalService,
		ServiceWrapper<ChangesetEntryLocalService> {
	public ChangesetEntryLocalServiceWrapper(
		ChangesetEntryLocalService changesetEntryLocalService) {
		_changesetEntryLocalService = changesetEntryLocalService;
	}

	/**
	* Adds the changeset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was added
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry addChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return _changesetEntryLocalService.addChangesetEntry(changesetEntry);
	}

	@Override
	public com.liferay.changeset.model.ChangesetEntry addChangesetEntry(
		long userId, long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.addChangesetEntry(userId,
			changesetCollectionId, classNameId, classPK);
	}

	/**
	* Creates a new changeset entry with the primary key. Does not add the changeset entry to the database.
	*
	* @param changesetEntryId the primary key for the new changeset entry
	* @return the new changeset entry
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry createChangesetEntry(
		long changesetEntryId) {
		return _changesetEntryLocalService.createChangesetEntry(changesetEntryId);
	}

	@Override
	public void deleteChangesetEntries(long changesetCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_changesetEntryLocalService.deleteChangesetEntries(changesetCollectionId);
	}

	/**
	* Deletes the changeset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was removed
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry deleteChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return _changesetEntryLocalService.deleteChangesetEntry(changesetEntry);
	}

	/**
	* Deletes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry that was removed
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry deleteChangesetEntry(
		long changesetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.deleteChangesetEntry(changesetEntryId);
	}

	@Override
	public void deleteEntry(long changesetId, long classNameId, long classPK) {
		_changesetEntryLocalService.deleteEntry(changesetId, classNameId,
			classPK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changesetEntryLocalService.dynamicQuery();
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
		return _changesetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetEntryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _changesetEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changesetEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.changeset.model.ChangesetEntry fetchChangesetEntry(
		long changesetEntryId) {
		return _changesetEntryLocalService.fetchChangesetEntry(changesetEntryId);
	}

	@Override
	public com.liferay.changeset.model.ChangesetEntry fetchChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK) {
		return _changesetEntryLocalService.fetchChangesetEntry(changesetCollectionId,
			classNameId, classPK);
	}

	@Override
	public com.liferay.changeset.model.ChangesetEntry fetchOrAddChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.fetchOrAddChangesetEntry(changesetCollectionId,
			classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changesetEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of changeset entries
	*/
	@Override
	public java.util.List<com.liferay.changeset.model.ChangesetEntry> getChangesetEntries(
		int start, int end) {
		return _changesetEntryLocalService.getChangesetEntries(start, end);
	}

	/**
	* Returns the number of changeset entries.
	*
	* @return the number of changeset entries
	*/
	@Override
	public int getChangesetEntriesCount() {
		return _changesetEntryLocalService.getChangesetEntriesCount();
	}

	@Override
	public long getChangesetEntriesCount(long changesetCollectionId) {
		return _changesetEntryLocalService.getChangesetEntriesCount(changesetCollectionId);
	}

	@Override
	public long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId) {
		return _changesetEntryLocalService.getChangesetEntriesCount(changesetCollectionId,
			classNameId);
	}

	@Override
	public long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId, java.util.Set<Long> classPKs) {
		return _changesetEntryLocalService.getChangesetEntriesCount(changesetCollectionId,
			classNameId, classPKs);
	}

	/**
	* Returns the changeset entry with the primary key.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry getChangesetEntry(
		long changesetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.getChangesetEntry(changesetEntryId);
	}

	@Override
	public com.liferay.changeset.model.ChangesetEntry getChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.changeset.exception.NoSuchEntryException {
		return _changesetEntryLocalService.getChangesetEntry(changesetCollectionId,
			classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changesetEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changesetEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was updated
	*/
	@Override
	public com.liferay.changeset.model.ChangesetEntry updateChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return _changesetEntryLocalService.updateChangesetEntry(changesetEntry);
	}

	@Override
	public ChangesetEntryLocalService getWrappedService() {
		return _changesetEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ChangesetEntryLocalService changesetEntryLocalService) {
		_changesetEntryLocalService = changesetEntryLocalService;
	}

	private ChangesetEntryLocalService _changesetEntryLocalService;
}