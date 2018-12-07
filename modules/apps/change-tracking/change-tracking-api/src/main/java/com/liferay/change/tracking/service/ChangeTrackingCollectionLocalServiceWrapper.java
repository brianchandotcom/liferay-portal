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
 * Provides a wrapper for {@link ChangeTrackingCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollectionLocalService
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionLocalServiceWrapper
	implements ChangeTrackingCollectionLocalService,
		ServiceWrapper<ChangeTrackingCollectionLocalService> {
	public ChangeTrackingCollectionLocalServiceWrapper(
		ChangeTrackingCollectionLocalService changeTrackingCollectionLocalService) {
		_changeTrackingCollectionLocalService = changeTrackingCollectionLocalService;
	}

	/**
	* Adds the change tracking collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was added
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection addChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return _changeTrackingCollectionLocalService.addChangeTrackingCollection(changeTrackingCollection);
	}

	@Override
	public void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		_changeTrackingCollectionLocalService.addChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollection);
	}

	@Override
	public void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		_changeTrackingCollectionLocalService.addChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	@Override
	public void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		_changeTrackingCollectionLocalService.addChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollections);
	}

	@Override
	public void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		_changeTrackingCollectionLocalService.addChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
	}

	@Override
	public void clearChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		_changeTrackingCollectionLocalService.clearChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	/**
	* Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	*
	* @param changeTrackingCollectionId the primary key for the new change tracking collection
	* @return the new change tracking collection
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection createChangeTrackingCollection(
		long changeTrackingCollectionId) {
		return _changeTrackingCollectionLocalService.createChangeTrackingCollection(changeTrackingCollectionId);
	}

	/**
	* Deletes the change tracking collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was removed
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection deleteChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return _changeTrackingCollectionLocalService.deleteChangeTrackingCollection(changeTrackingCollection);
	}

	/**
	* Deletes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection that was removed
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection deleteChangeTrackingCollection(
		long changeTrackingCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingCollectionLocalService.deleteChangeTrackingCollection(changeTrackingCollectionId);
	}

	@Override
	public void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		_changeTrackingCollectionLocalService.deleteChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollection);
	}

	@Override
	public void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		_changeTrackingCollectionLocalService.deleteChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	@Override
	public void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		_changeTrackingCollectionLocalService.deleteChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollections);
	}

	@Override
	public void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		_changeTrackingCollectionLocalService.deleteChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changeTrackingCollectionLocalService.dynamicQuery();
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
		return _changeTrackingCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeTrackingCollectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeTrackingCollectionLocalService.dynamicQuery(dynamicQuery,
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
		return _changeTrackingCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changeTrackingCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection fetchChangeTrackingCollection(
		long changeTrackingCollectionId) {
		return _changeTrackingCollectionLocalService.fetchChangeTrackingCollection(changeTrackingCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changeTrackingCollectionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the change tracking collection with the primary key.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection getChangeTrackingCollection(
		long changeTrackingCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingCollectionLocalService.getChangeTrackingCollection(changeTrackingCollectionId);
	}

	/**
	* Returns a range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @return the range of change tracking collections
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		int start, int end) {
		return _changeTrackingCollectionLocalService.getChangeTrackingCollections(start,
			end);
	}

	/**
	* Returns the number of change tracking collections.
	*
	* @return the number of change tracking collections
	*/
	@Override
	public int getChangeTrackingCollectionsCount() {
		return _changeTrackingCollectionLocalService.getChangeTrackingCollectionsCount();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		return _changeTrackingCollectionLocalService.getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end) {
		return _changeTrackingCollectionLocalService.getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingCollection> orderByComparator) {
		return _changeTrackingCollectionLocalService.getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getChangeTrackingEntryChangeTrackingCollectionsCount(
		long changeTrackingEntryId) {
		return _changeTrackingCollectionLocalService.getChangeTrackingEntryChangeTrackingCollectionsCount(changeTrackingEntryId);
	}

	/**
	* Returns the changeTrackingEntryIds of the change tracking entries associated with the change tracking collection.
	*
	* @param changeTrackingCollectionId the changeTrackingCollectionId of the change tracking collection
	* @return long[] the changeTrackingEntryIds of change tracking entries associated with the change tracking collection
	*/
	@Override
	public long[] getChangeTrackingEntryPrimaryKeys(
		long changeTrackingCollectionId) {
		return _changeTrackingCollectionLocalService.getChangeTrackingEntryPrimaryKeys(changeTrackingCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changeTrackingCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changeTrackingCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeTrackingCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		return _changeTrackingCollectionLocalService.hasChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	@Override
	public boolean hasChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		return _changeTrackingCollectionLocalService.hasChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	@Override
	public void setChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		_changeTrackingCollectionLocalService.setChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
	}

	/**
	* Updates the change tracking collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was updated
	*/
	@Override
	public com.liferay.change.tracking.model.ChangeTrackingCollection updateChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return _changeTrackingCollectionLocalService.updateChangeTrackingCollection(changeTrackingCollection);
	}

	@Override
	public ChangeTrackingCollectionLocalService getWrappedService() {
		return _changeTrackingCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		ChangeTrackingCollectionLocalService changeTrackingCollectionLocalService) {
		_changeTrackingCollectionLocalService = changeTrackingCollectionLocalService;
	}

	private ChangeTrackingCollectionLocalService _changeTrackingCollectionLocalService;
}