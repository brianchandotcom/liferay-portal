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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTCollection. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTCollectionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionLocalService
 * @generated
 */
public class CTCollectionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTCollectionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ct collection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was added
	 */
	public static com.liferay.change.tracking.model.CTCollection
		addCTCollection(
			com.liferay.change.tracking.model.CTCollection ctCollection) {

		return getService().addCTCollection(ctCollection);
	}

	public static com.liferay.change.tracking.model.CTCollection
			addCTCollection(
				long companyId, long userId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCTCollection(
			companyId, userId, name, description);
	}

	public static java.util.Map
		<Long,
		 java.util.List<com.liferay.change.tracking.conflict.ConflictInfo>>
				checkConflicts(
					com.liferay.change.tracking.model.CTCollection ctCollection)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkConflicts(ctCollection);
	}

	/**
	 * Creates a new ct collection with the primary key. Does not add the ct collection to the database.
	 *
	 * @param ctCollectionId the primary key for the new ct collection
	 * @return the new ct collection
	 */
	public static com.liferay.change.tracking.model.CTCollection
		createCTCollection(long ctCollectionId) {

		return getService().createCTCollection(ctCollectionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteCompanyCTCollections(long companyId) {
		getService().deleteCompanyCTCollections(companyId);
	}

	public static void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId) {
		getService().deleteCTAutoResolutionInfo(ctAutoResolutionInfoId);
	}

	/**
	 * Deletes the ct collection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was removed
	 */
	public static com.liferay.change.tracking.model.CTCollection
		deleteCTCollection(
			com.liferay.change.tracking.model.CTCollection ctCollection) {

		return getService().deleteCTCollection(ctCollection);
	}

	/**
	 * Deletes the ct collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection that was removed
	 * @throws PortalException if a ct collection with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTCollection
			deleteCTCollection(long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTCollection(ctCollectionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.change.tracking.model.CTCollection
		fetchCTCollection(long ctCollectionId) {

		return getService().fetchCTCollection(ctCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ct collection with the primary key.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection
	 * @throws PortalException if a ct collection with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTCollection
			getCTCollection(long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTCollection(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct collections
	 */
	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(int start, int end) {

		return getService().getCTCollections(start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator) {

		return getService().getCTCollections(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct collections.
	 *
	 * @return the number of ct collections
	 */
	public static int getCTCollectionsCount() {
		return getService().getCTCollectionsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.change.tracking.model.CTCollection
			undoCTCollection(
				long ctCollectionId, long userId, String name,
				String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().undoCTCollection(
			ctCollectionId, userId, name, description);
	}

	/**
	 * Updates the ct collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was updated
	 */
	public static com.liferay.change.tracking.model.CTCollection
		updateCTCollection(
			com.liferay.change.tracking.model.CTCollection ctCollection) {

		return getService().updateCTCollection(ctCollection);
	}

	public static com.liferay.change.tracking.model.CTCollection
			updateCTCollection(
				long userId, long ctCollectionId, String name,
				String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCTCollection(
			userId, ctCollectionId, name, description);
	}

	public static CTCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTCollectionLocalService, CTCollectionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTCollectionLocalService.class);

		ServiceTracker<CTCollectionLocalService, CTCollectionLocalService>
			serviceTracker =
				new ServiceTracker
					<CTCollectionLocalService, CTCollectionLocalService>(
						bundle.getBundleContext(),
						CTCollectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}