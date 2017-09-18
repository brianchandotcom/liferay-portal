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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MSBFragmentCollection. This utility wraps
 * {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionLocalService
 * @see com.liferay.modern.site.building.fragment.service.base.MSBFragmentCollectionLocalServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		long groupId, long userId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMSBFragmentCollection(groupId, userId, name,
			description, serviceContext);
	}

	/**
	* Adds the msb fragment collection to the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was added
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection) {
		return getService().addMSBFragmentCollection(msbFragmentCollection);
	}

	/**
	* Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	*
	* @param msbFragmentCollectionId the primary key for the new msb fragment collection
	* @return the new msb fragment collection
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection createMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return getService().createMSBFragmentCollection(msbFragmentCollectionId);
	}

	/**
	* Deletes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws PortalException if a msb fragment collection with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentCollection(msbFragmentCollectionId);
	}

	/**
	* Deletes the msb fragment collection from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was removed
	* @throws PortalException
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentCollection(msbFragmentCollection);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return getService().fetchMSBFragmentCollection(msbFragmentCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the msb fragment collection with the primary key.
	*
	* @param msbFragmentCollectionId the primary key of the msb fragment collection
	* @return the msb fragment collection
	* @throws PortalException if a msb fragment collection with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection getMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMSBFragmentCollection(msbFragmentCollectionId);
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
	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		int start, int end) {
		return getService().getMSBFragmentCollections(start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMSBFragmentCollections(groupId, start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentCollections(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator) {
		return getService()
				   .getMSBFragmentCollections(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of msb fragment collections.
	*
	* @return the number of msb fragment collections
	*/
	public static int getMSBFragmentCollectionsCount() {
		return getService().getMSBFragmentCollectionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMSBFragmentCollection(msbFragmentCollectionId, name,
			description);
	}

	/**
	* Updates the msb fragment collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentCollection the msb fragment collection
	* @return the msb fragment collection that was updated
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		com.liferay.modern.site.building.fragment.model.MSBFragmentCollection msbFragmentCollection) {
		return getService().updateMSBFragmentCollection(msbFragmentCollection);
	}

	public static MSBFragmentCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentCollectionLocalService, MSBFragmentCollectionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentCollectionLocalService.class);
}