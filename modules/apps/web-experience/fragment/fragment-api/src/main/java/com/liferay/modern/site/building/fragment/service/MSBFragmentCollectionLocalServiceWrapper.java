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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MSBFragmentCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionLocalService
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionLocalServiceWrapper
	implements MSBFragmentCollectionLocalService,
		ServiceWrapper<MSBFragmentCollectionLocalService> {
	public MSBFragmentCollectionLocalServiceWrapper(
		MSBFragmentCollectionLocalService msbFragmentCollectionLocalService) {
		_msbFragmentCollectionLocalService = msbFragmentCollectionLocalService;
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		long groupId, long userId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.addMSBFragmentCollection(groupId,
			userId, name, description, serviceContext);
	}

	/**
	* Adds the msb fragment collection to the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was added
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection) {
		return _msbFragmentCollectionLocalService.addMSBFragmentCollection(msbFragmentCollection);
	}

	/**
	* Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	*
	* @param msbFragmentCollectionId the primary key for the new msb fragment collection
	* @return the new msb fragment collection
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection createMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return _msbFragmentCollectionLocalService.createMSBFragmentCollection(msbFragmentCollectionId);
	}

	/**
	* Deletes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws PortalException if a msb fragment collection with the primary key could not be found
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.deleteMSBFragmentCollection(msbFragmentCollectionId);
	}

	/**
	* Deletes the msb fragment collection from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.deleteMSBFragmentCollection(msbFragmentCollection);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _msbFragmentCollectionLocalService.dynamicQuery();
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
		return _msbFragmentCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _msbFragmentCollectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _msbFragmentCollectionLocalService.dynamicQuery(dynamicQuery,
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
		return _msbFragmentCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _msbFragmentCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return _msbFragmentCollectionLocalService.fetchMSBFragmentCollection(msbFragmentCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _msbFragmentCollectionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _msbFragmentCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the msb fragment collection with the primary key.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection
	* @throws PortalException if a msb fragment collection with the primary key could not be found
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection getMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollection(msbFragmentCollectionId);
	}

	/**
	* Returns a range of all the msb fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment collections
	* @param end the upper bound of the range of msb fragment collections (not inclusive)
	* @return the range of msb fragment collections
	*/
	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		int start, int end) {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollections(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollections(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollections(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator) {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollections(groupId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the number of msb fragment collections.
	*
	* @return the number of msb fragment collections
	*/
	@Override
	public int getMSBFragmentCollectionsCount() {
		return _msbFragmentCollectionLocalService.getMSBFragmentCollectionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _msbFragmentCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionLocalService.updateMSBFragmentCollection(msbFragmentCollectionId,
			name, description);
	}

	/**
	* Updates the msb fragment collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was updated
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection) {
		return _msbFragmentCollectionLocalService.updateMSBFragmentCollection(msbFragmentCollection);
	}

	@Override
	public MSBFragmentCollectionLocalService getWrappedService() {
		return _msbFragmentCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		MSBFragmentCollectionLocalService msbFragmentCollectionLocalService) {
		_msbFragmentCollectionLocalService = msbFragmentCollectionLocalService;
	}

	private MSBFragmentCollectionLocalService _msbFragmentCollectionLocalService;
}