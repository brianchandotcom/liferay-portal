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
@ProviderType
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
				long userId, String name, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCTCollection(
			userId, name, description, serviceContext);
	}

	public static void addCTEntryAggregateCTCollection(
		long ctEntryAggregateId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {

		getService().addCTEntryAggregateCTCollection(
			ctEntryAggregateId, ctCollection);
	}

	public static void addCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId) {

		getService().addCTEntryAggregateCTCollection(
			ctEntryAggregateId, ctCollectionId);
	}

	public static void addCTEntryAggregateCTCollections(
		long ctEntryAggregateId,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections) {

		getService().addCTEntryAggregateCTCollections(
			ctEntryAggregateId, ctCollections);
	}

	public static void addCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds) {

		getService().addCTEntryAggregateCTCollections(
			ctEntryAggregateId, ctCollectionIds);
	}

	public static void addCTEntryCTCollection(
		long ctEntryId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {

		getService().addCTEntryCTCollection(ctEntryId, ctCollection);
	}

	public static void addCTEntryCTCollection(
		long ctEntryId, long ctCollectionId) {

		getService().addCTEntryCTCollection(ctEntryId, ctCollectionId);
	}

	public static void addCTEntryCTCollections(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections) {

		getService().addCTEntryCTCollections(ctEntryId, ctCollections);
	}

	public static void addCTEntryCTCollections(
		long ctEntryId, long[] ctCollectionIds) {

		getService().addCTEntryCTCollections(ctEntryId, ctCollectionIds);
	}

	public static void clearCTEntryAggregateCTCollections(
		long ctEntryAggregateId) {

		getService().clearCTEntryAggregateCTCollections(ctEntryAggregateId);
	}

	public static void clearCTEntryCTCollections(long ctEntryId) {
		getService().clearCTEntryCTCollections(ctEntryId);
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

	public static void deleteCompanyCTCollections(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCompanyCTCollections(companyId);
	}

	/**
	 * Deletes the ct collection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollection the ct collection
	 * @return the ct collection that was removed
	 * @throws PortalException
	 */
	public static com.liferay.change.tracking.model.CTCollection
			deleteCTCollection(
				com.liferay.change.tracking.model.CTCollection ctCollection)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static void deleteCTEntryAggregateCTCollection(
		long ctEntryAggregateId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {

		getService().deleteCTEntryAggregateCTCollection(
			ctEntryAggregateId, ctCollection);
	}

	public static void deleteCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId) {

		getService().deleteCTEntryAggregateCTCollection(
			ctEntryAggregateId, ctCollectionId);
	}

	public static void deleteCTEntryAggregateCTCollections(
		long ctEntryAggregateId,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections) {

		getService().deleteCTEntryAggregateCTCollections(
			ctEntryAggregateId, ctCollections);
	}

	public static void deleteCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds) {

		getService().deleteCTEntryAggregateCTCollections(
			ctEntryAggregateId, ctCollectionIds);
	}

	public static void deleteCTEntryCTCollection(
		long ctEntryId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {

		getService().deleteCTEntryCTCollection(ctEntryId, ctCollection);
	}

	public static void deleteCTEntryCTCollection(
		long ctEntryId, long ctCollectionId) {

		getService().deleteCTEntryCTCollection(ctEntryId, ctCollectionId);
	}

	public static void deleteCTEntryCTCollections(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections) {

		getService().deleteCTEntryCTCollections(ctEntryId, ctCollections);
	}

	public static void deleteCTEntryCTCollections(
		long ctEntryId, long[] ctCollectionIds) {

		getService().deleteCTEntryCTCollections(ctEntryId, ctCollectionIds);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.CTCollection
		fetchCTCollection(long companyId, String name) {

		return getService().fetchCTCollection(companyId, name);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
			long companyId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.change.tracking.model.CTCollection>
					queryDefinition) {

		return getService().getCTCollections(companyId, queryDefinition);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long companyId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.change.tracking.model.CTCollection>
					queryDefinition,
			boolean includeProduction) {

		return getService().getCTCollections(
			companyId, queryDefinition, includeProduction);
	}

	/**
	 * Returns the number of ct collections.
	 *
	 * @return the number of ct collections
	 */
	public static int getCTCollectionsCount() {
		return getService().getCTCollectionsCount();
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryAggregateCTCollections(long ctEntryAggregateId) {

		return getService().getCTEntryAggregateCTCollections(
			ctEntryAggregateId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryAggregateCTCollections(
			long ctEntryAggregateId, int start, int end) {

		return getService().getCTEntryAggregateCTCollections(
			ctEntryAggregateId, start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryAggregateCTCollections(
			long ctEntryAggregateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator) {

		return getService().getCTEntryAggregateCTCollections(
			ctEntryAggregateId, start, end, orderByComparator);
	}

	public static int getCTEntryAggregateCTCollectionsCount(
		long ctEntryAggregateId) {

		return getService().getCTEntryAggregateCTCollectionsCount(
			ctEntryAggregateId);
	}

	/**
	 * Returns the ctEntryAggregateIds of the ct entry aggregates associated with the ct collection.
	 *
	 * @param ctCollectionId the ctCollectionId of the ct collection
	 * @return long[] the ctEntryAggregateIds of ct entry aggregates associated with the ct collection
	 */
	public static long[] getCTEntryAggregatePrimaryKeys(long ctCollectionId) {
		return getService().getCTEntryAggregatePrimaryKeys(ctCollectionId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryCTCollections(long ctEntryId) {

		return getService().getCTEntryCTCollections(ctEntryId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryCTCollections(long ctEntryId, int start, int end) {

		return getService().getCTEntryCTCollections(ctEntryId, start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTEntryCTCollections(
			long ctEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator) {

		return getService().getCTEntryCTCollections(
			ctEntryId, start, end, orderByComparator);
	}

	public static int getCTEntryCTCollectionsCount(long ctEntryId) {
		return getService().getCTEntryCTCollectionsCount(ctEntryId);
	}

	/**
	 * Returns the ctEntryIds of the ct entries associated with the ct collection.
	 *
	 * @param ctCollectionId the ctCollectionId of the ct collection
	 * @return long[] the ctEntryIds of ct entries associated with the ct collection
	 */
	public static long[] getCTEntryPrimaryKeys(long ctCollectionId) {
		return getService().getCTEntryPrimaryKeys(ctCollectionId);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasCTEntryAggregateCTCollection(
		long ctEntryAggregateId, long ctCollectionId) {

		return getService().hasCTEntryAggregateCTCollection(
			ctEntryAggregateId, ctCollectionId);
	}

	public static boolean hasCTEntryAggregateCTCollections(
		long ctEntryAggregateId) {

		return getService().hasCTEntryAggregateCTCollections(
			ctEntryAggregateId);
	}

	public static boolean hasCTEntryCTCollection(
		long ctEntryId, long ctCollectionId) {

		return getService().hasCTEntryCTCollection(ctEntryId, ctCollectionId);
	}

	public static boolean hasCTEntryCTCollections(long ctEntryId) {
		return getService().hasCTEntryCTCollections(ctEntryId);
	}

	public static void setCTEntryAggregateCTCollections(
		long ctEntryAggregateId, long[] ctCollectionIds) {

		getService().setCTEntryAggregateCTCollections(
			ctEntryAggregateId, ctCollectionIds);
	}

	public static void setCTEntryCTCollections(
		long ctEntryId, long[] ctCollectionIds) {

		getService().setCTEntryCTCollections(ctEntryId, ctCollectionIds);
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

	public static com.liferay.change.tracking.model.CTCollection updateStatus(
			long userId,
			com.liferay.change.tracking.model.CTCollection ctCollection,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, ctCollection, status, serviceContext);
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