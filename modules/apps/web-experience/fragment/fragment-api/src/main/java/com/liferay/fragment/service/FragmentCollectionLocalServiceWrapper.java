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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionLocalService
 * @generated
 */
@ProviderType
public class FragmentCollectionLocalServiceWrapper
	implements FragmentCollectionLocalService,
		ServiceWrapper<FragmentCollectionLocalService> {
	public FragmentCollectionLocalServiceWrapper(
		FragmentCollectionLocalService fragmentCollectionLocalService) {
		_fragmentCollectionLocalService = fragmentCollectionLocalService;
	}

	/**
	* Adds the fragment collection to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was added
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection addFragmentCollection(
		com.liferay.fragment.model.FragmentCollection fragmentCollection) {
		return _fragmentCollectionLocalService.addFragmentCollection(fragmentCollection);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection addFragmentCollection(
		long userId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.addFragmentCollection(userId,
			groupId, name, description, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection addFragmentCollection(
		long userId, long groupId, java.lang.String fragmentCollectionKey,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.addFragmentCollection(userId,
			groupId, fragmentCollectionKey, name, description, serviceContext);
	}

	/**
	* Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	*
	* @param fragmentCollectionId the primary key for the new fragment collection
	* @return the new fragment collection
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection createFragmentCollection(
		long fragmentCollectionId) {
		return _fragmentCollectionLocalService.createFragmentCollection(fragmentCollectionId);
	}

	/**
	* Deletes the fragment collection from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection deleteFragmentCollection(
		com.liferay.fragment.model.FragmentCollection fragmentCollection)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.deleteFragmentCollection(fragmentCollection);
	}

	/**
	* Deletes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection that was removed
	* @throws PortalException if a fragment collection with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection deleteFragmentCollection(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.deleteFragmentCollection(fragmentCollectionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fragmentCollectionLocalService.dynamicQuery();
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
		return _fragmentCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentCollectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentCollectionLocalService.dynamicQuery(dynamicQuery,
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
		return _fragmentCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _fragmentCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection fetchFragmentCollection(
		long fragmentCollectionId) {
		return _fragmentCollectionLocalService.fetchFragmentCollection(fragmentCollectionId);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection fetchFragmentCollection(
		long groupId, java.lang.String fragmentCollectionKey) {
		return _fragmentCollectionLocalService.fetchFragmentCollection(groupId,
			fragmentCollectionKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _fragmentCollectionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the fragment collection with the primary key.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection
	* @throws PortalException if a fragment collection with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection getFragmentCollection(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.getFragmentCollection(fragmentCollectionId);
	}

	/**
	* Returns a range of all the fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of fragment collections
	*/
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection> getFragmentCollections(
		int start, int end) {
		return _fragmentCollectionLocalService.getFragmentCollections(start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection> getFragmentCollections(
		long groupId, int start, int end) {
		return _fragmentCollectionLocalService.getFragmentCollections(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentCollection> orderByComparator) {
		return _fragmentCollectionLocalService.getFragmentCollections(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection> getFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentCollection> orderByComparator) {
		return _fragmentCollectionLocalService.getFragmentCollections(groupId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the number of fragment collections.
	*
	* @return the number of fragment collections
	*/
	@Override
	public int getFragmentCollectionsCount() {
		return _fragmentCollectionLocalService.getFragmentCollectionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _fragmentCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _fragmentCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.lang.String[] getTempFileNames(long userId, long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.getTempFileNames(userId,
			groupId, folderName);
	}

	/**
	* Updates the fragment collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was updated
	*/
	@Override
	public com.liferay.fragment.model.FragmentCollection updateFragmentCollection(
		com.liferay.fragment.model.FragmentCollection fragmentCollection) {
		return _fragmentCollectionLocalService.updateFragmentCollection(fragmentCollection);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection updateFragmentCollection(
		long fragmentCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentCollectionLocalService.updateFragmentCollection(fragmentCollectionId,
			name, description);
	}

	@Override
	public FragmentCollectionLocalService getWrappedService() {
		return _fragmentCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		FragmentCollectionLocalService fragmentCollectionLocalService) {
		_fragmentCollectionLocalService = fragmentCollectionLocalService;
	}

	private FragmentCollectionLocalService _fragmentCollectionLocalService;
}