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
 * Provides a wrapper for {@link ChangeCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollectionLocalService
 * @generated
 */
@ProviderType
public class ChangeCollectionLocalServiceWrapper
	implements ChangeCollectionLocalService,
		ServiceWrapper<ChangeCollectionLocalService> {
	public ChangeCollectionLocalServiceWrapper(
		ChangeCollectionLocalService changeCollectionLocalService) {
		_changeCollectionLocalService = changeCollectionLocalService;
	}

	/**
	* Adds the change collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was added
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection addChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return _changeCollectionLocalService.addChangeCollection(changeCollection);
	}

	@Override
	public void addChangeEntryChangeCollection(long changeEntryId,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		_changeCollectionLocalService.addChangeEntryChangeCollection(changeEntryId,
			changeCollection);
	}

	@Override
	public void addChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		_changeCollectionLocalService.addChangeEntryChangeCollection(changeEntryId,
			changeCollectionId);
	}

	@Override
	public void addChangeEntryChangeCollections(long changeEntryId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		_changeCollectionLocalService.addChangeEntryChangeCollections(changeEntryId,
			changeCollections);
	}

	@Override
	public void addChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		_changeCollectionLocalService.addChangeEntryChangeCollections(changeEntryId,
			changeCollectionIds);
	}

	@Override
	public void clearChangeEntryChangeCollections(long changeEntryId) {
		_changeCollectionLocalService.clearChangeEntryChangeCollections(changeEntryId);
	}

	/**
	* Creates a new change collection with the primary key. Does not add the change collection to the database.
	*
	* @param changeCollectionId the primary key for the new change collection
	* @return the new change collection
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection createChangeCollection(
		long changeCollectionId) {
		return _changeCollectionLocalService.createChangeCollection(changeCollectionId);
	}

	/**
	* Deletes the change collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was removed
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection deleteChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return _changeCollectionLocalService.deleteChangeCollection(changeCollection);
	}

	/**
	* Deletes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection that was removed
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection deleteChangeCollection(
		long changeCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeCollectionLocalService.deleteChangeCollection(changeCollectionId);
	}

	@Override
	public void deleteChangeEntryChangeCollection(long changeEntryId,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		_changeCollectionLocalService.deleteChangeEntryChangeCollection(changeEntryId,
			changeCollection);
	}

	@Override
	public void deleteChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		_changeCollectionLocalService.deleteChangeEntryChangeCollection(changeEntryId,
			changeCollectionId);
	}

	@Override
	public void deleteChangeEntryChangeCollections(long changeEntryId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		_changeCollectionLocalService.deleteChangeEntryChangeCollections(changeEntryId,
			changeCollections);
	}

	@Override
	public void deleteChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		_changeCollectionLocalService.deleteChangeEntryChangeCollections(changeEntryId,
			changeCollectionIds);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changeCollectionLocalService.dynamicQuery();
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
		return _changeCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeCollectionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changeCollectionLocalService.dynamicQuery(dynamicQuery, start,
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
		return _changeCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changeCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection fetchChangeCollection(
		long changeCollectionId) {
		return _changeCollectionLocalService.fetchChangeCollection(changeCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changeCollectionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the change collection with the primary key.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection getChangeCollection(
		long changeCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeCollectionLocalService.getChangeCollection(changeCollectionId);
	}

	/**
	* Returns a range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @return the range of change collections
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		int start, int end) {
		return _changeCollectionLocalService.getChangeCollections(start, end);
	}

	/**
	* Returns the number of change collections.
	*
	* @return the number of change collections
	*/
	@Override
	public int getChangeCollectionsCount() {
		return _changeCollectionLocalService.getChangeCollectionsCount();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId) {
		return _changeCollectionLocalService.getChangeEntryChangeCollections(changeEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end) {
		return _changeCollectionLocalService.getChangeEntryChangeCollections(changeEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeCollection> orderByComparator) {
		return _changeCollectionLocalService.getChangeEntryChangeCollections(changeEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getChangeEntryChangeCollectionsCount(long changeEntryId) {
		return _changeCollectionLocalService.getChangeEntryChangeCollectionsCount(changeEntryId);
	}

	/**
	* Returns the changeEntryIds of the change entries associated with the change collection.
	*
	* @param changeCollectionId the changeCollectionId of the change collection
	* @return long[] the changeEntryIds of change entries associated with the change collection
	*/
	@Override
	public long[] getChangeEntryPrimaryKeys(long changeCollectionId) {
		return _changeCollectionLocalService.getChangeEntryPrimaryKeys(changeCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changeCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changeCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changeCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		return _changeCollectionLocalService.hasChangeEntryChangeCollection(changeEntryId,
			changeCollectionId);
	}

	@Override
	public boolean hasChangeEntryChangeCollections(long changeEntryId) {
		return _changeCollectionLocalService.hasChangeEntryChangeCollections(changeEntryId);
	}

	@Override
	public void setChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		_changeCollectionLocalService.setChangeEntryChangeCollections(changeEntryId,
			changeCollectionIds);
	}

	/**
	* Updates the change collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was updated
	*/
	@Override
	public com.liferay.change.tracking.engine.model.ChangeCollection updateChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return _changeCollectionLocalService.updateChangeCollection(changeCollection);
	}

	@Override
	public ChangeCollectionLocalService getWrappedService() {
		return _changeCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		ChangeCollectionLocalService changeCollectionLocalService) {
		_changeCollectionLocalService = changeCollectionLocalService;
	}

	private ChangeCollectionLocalService _changeCollectionLocalService;
}