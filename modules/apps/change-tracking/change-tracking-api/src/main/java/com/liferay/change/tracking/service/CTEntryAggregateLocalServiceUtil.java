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
 * Provides the local service utility for CTEntryAggregate. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTEntryAggregateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregateLocalService
 * @generated
 */
@ProviderType
public class CTEntryAggregateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTEntryAggregateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCTCollectionCTEntryAggregate(
		long ctCollectionId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getService().addCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregate);
	}

	public static void addCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		getService().addCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	public static void addCTCollectionCTEntryAggregates(
		long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getService().addCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregates);
	}

	public static void addCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		getService().addCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	public static void addCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		getService().addCTEntry(ctEntryAggregate, ctEntry);
	}

	/**
	 * Adds the ct entry aggregate to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was added
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
		addCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return getService().addCTEntryAggregate(ctEntryAggregate);
	}

	public static com.liferay.change.tracking.model.CTEntryAggregate
			addCTEntryAggregate(
				long userId, long ctCollectionId, long ownerCTEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCTEntryAggregate(
			userId, ctCollectionId, ownerCTEntryId, serviceContext);
	}

	public static void addCTEntryCTEntryAggregate(
		long ctEntryId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getService().addCTEntryCTEntryAggregate(ctEntryId, ctEntryAggregate);
	}

	public static void addCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		getService().addCTEntryCTEntryAggregate(ctEntryId, ctEntryAggregateId);
	}

	public static void addCTEntryCTEntryAggregates(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getService().addCTEntryCTEntryAggregates(ctEntryId, ctEntryAggregates);
	}

	public static void addCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		getService().addCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
	}

	public static void clearCTCollectionCTEntryAggregates(long ctCollectionId) {
		getService().clearCTCollectionCTEntryAggregates(ctCollectionId);
	}

	public static void clearCTEntryCTEntryAggregates(long ctEntryId) {
		getService().clearCTEntryCTEntryAggregates(ctEntryId);
	}

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
		createCTEntryAggregate(long ctEntryAggregateId) {

		return getService().createCTEntryAggregate(ctEntryAggregateId);
	}

	public static void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getService().deleteCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregate);
	}

	public static void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		getService().deleteCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	public static void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getService().deleteCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregates);
	}

	public static void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		getService().deleteCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	/**
	 * Deletes the ct entry aggregate from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
		deleteCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return getService().deleteCTEntryAggregate(ctEntryAggregate);
	}

	/**
	 * Deletes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
			deleteCTEntryAggregate(long ctEntryAggregateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTEntryAggregate(ctEntryAggregateId);
	}

	public static void deleteCTEntryCTEntryAggregate(
		long ctEntryId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getService().deleteCTEntryCTEntryAggregate(ctEntryId, ctEntryAggregate);
	}

	public static void deleteCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		getService().deleteCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregateId);
	}

	public static void deleteCTEntryCTEntryAggregates(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getService().deleteCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregates);
	}

	public static void deleteCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		getService().deleteCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.CTEntryAggregate
		fetchCTEntryAggregate(long ctEntryAggregateId) {

		return getService().fetchCTEntryAggregate(ctEntryAggregateId);
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			fetchCTEntryAggregates(long ctCollectionId, long ownerCTEntryId) {

		return getService().fetchCTEntryAggregates(
			ctCollectionId, ownerCTEntryId);
	}

	public static com.liferay.change.tracking.model.CTEntryAggregate
		fetchLatestCTEntryAggregate(long ctCollectionId, long ownerCTEntryId) {

		return getService().fetchLatestCTEntryAggregate(
			ctCollectionId, ownerCTEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTCollectionCTEntryAggregates(long ctCollectionId) {

		return getService().getCTCollectionCTEntryAggregates(ctCollectionId);
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTCollectionCTEntryAggregates(
				long ctCollectionId, int start, int end) {

		return getService().getCTCollectionCTEntryAggregates(
			ctCollectionId, start, end);
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTCollectionCTEntryAggregates(
				long ctCollectionId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTEntryAggregate>
						orderByComparator) {

		return getService().getCTCollectionCTEntryAggregates(
			ctCollectionId, start, end, orderByComparator);
	}

	public static int getCTCollectionCTEntryAggregatesCount(
		long ctCollectionId) {

		return getService().getCTCollectionCTEntryAggregatesCount(
			ctCollectionId);
	}

	/**
	 * Returns the ctCollectionIds of the ct collections associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctCollectionIds of ct collections associated with the ct entry aggregate
	 */
	public static long[] getCTCollectionPrimaryKeys(long ctEntryAggregateId) {
		return getService().getCTCollectionPrimaryKeys(ctEntryAggregateId);
	}

	/**
	 * Returns the ct entry aggregate with the primary key.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
			getCTEntryAggregate(long ctEntryAggregateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTEntryAggregate(ctEntryAggregateId);
	}

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTEntryAggregates(int start, int end) {

		return getService().getCTEntryAggregates(start, end);
	}

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	public static int getCTEntryAggregatesCount() {
		return getService().getCTEntryAggregatesCount();
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTEntryCTEntryAggregates(long ctEntryId) {

		return getService().getCTEntryCTEntryAggregates(ctEntryId);
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTEntryCTEntryAggregates(long ctEntryId, int start, int end) {

		return getService().getCTEntryCTEntryAggregates(ctEntryId, start, end);
	}

	public static java.util.List
		<com.liferay.change.tracking.model.CTEntryAggregate>
			getCTEntryCTEntryAggregates(
				long ctEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTEntryAggregate>
						orderByComparator) {

		return getService().getCTEntryCTEntryAggregates(
			ctEntryId, start, end, orderByComparator);
	}

	public static int getCTEntryCTEntryAggregatesCount(long ctEntryId) {
		return getService().getCTEntryCTEntryAggregatesCount(ctEntryId);
	}

	/**
	 * Returns the ctEntryIds of the ct entries associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctEntryIds of ct entries associated with the ct entry aggregate
	 */
	public static long[] getCTEntryPrimaryKeys(long ctEntryAggregateId) {
		return getService().getCTEntryPrimaryKeys(ctEntryAggregateId);
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

	public static boolean hasCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		return getService().hasCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	public static boolean hasCTCollectionCTEntryAggregates(
		long ctCollectionId) {

		return getService().hasCTCollectionCTEntryAggregates(ctCollectionId);
	}

	public static boolean hasCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		return getService().hasCTEntry(ctEntryAggregate, ctEntry);
	}

	public static boolean hasCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		return getService().hasCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregateId);
	}

	public static boolean hasCTEntryCTEntryAggregates(long ctEntryId) {
		return getService().hasCTEntryCTEntryAggregates(ctEntryId);
	}

	public static void removeCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		getService().removeCTEntry(ctEntryAggregate, ctEntry);
	}

	public static void setCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		getService().setCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	public static void setCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		getService().setCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
	}

	/**
	 * Updates the ct entry aggregate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was updated
	 */
	public static com.liferay.change.tracking.model.CTEntryAggregate
		updateCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return getService().updateCTEntryAggregate(ctEntryAggregate);
	}

	public static CTEntryAggregateLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTEntryAggregateLocalService, CTEntryAggregateLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTEntryAggregateLocalService.class);

		ServiceTracker
			<CTEntryAggregateLocalService, CTEntryAggregateLocalService>
				serviceTracker =
					new ServiceTracker
						<CTEntryAggregateLocalService,
						 CTEntryAggregateLocalService>(
							 bundle.getBundleContext(),
							 CTEntryAggregateLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}