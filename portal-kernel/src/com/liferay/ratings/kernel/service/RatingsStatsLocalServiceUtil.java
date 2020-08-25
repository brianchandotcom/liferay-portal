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

package com.liferay.ratings.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for RatingsStats. This utility wraps
 * <code>com.liferay.portlet.ratings.service.impl.RatingsStatsLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsLocalService
 * @generated
 */
public class RatingsStatsLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.ratings.service.impl.RatingsStatsLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ratings stats to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsStatsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsStats the ratings stats
	 * @return the ratings stats that was added
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats addRatingsStats(
		com.liferay.ratings.kernel.model.RatingsStats ratingsStats) {

		return getService().addRatingsStats(ratingsStats);
	}

	public static com.liferay.ratings.kernel.model.RatingsStats addStats(
		long classNameId, long classPK) {

		return getService().addStats(classNameId, classPK);
	}

	/**
	 * Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	 *
	 * @param statsId the primary key for the new ratings stats
	 * @return the new ratings stats
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats
		createRatingsStats(long statsId) {

		return getService().createRatingsStats(statsId);
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

	/**
	 * Deletes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsStatsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats that was removed
	 * @throws PortalException if a ratings stats with the primary key could not be found
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats
			deleteRatingsStats(long statsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRatingsStats(statsId);
	}

	/**
	 * Deletes the ratings stats from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsStatsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsStats the ratings stats
	 * @return the ratings stats that was removed
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats
		deleteRatingsStats(
			com.liferay.ratings.kernel.model.RatingsStats ratingsStats) {

		return getService().deleteRatingsStats(ratingsStats);
	}

	public static void deleteStats(String className, long classPK) {
		getService().deleteStats(className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl</code>.
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

	public static com.liferay.ratings.kernel.model.RatingsStats
		fetchRatingsStats(long statsId) {

		return getService().fetchRatingsStats(statsId);
	}

	public static com.liferay.ratings.kernel.model.RatingsStats fetchStats(
		String className, long classPK) {

		return getService().fetchStats(className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	/**
	 * Returns the ratings stats with the primary key.
	 *
	 * @param statsId the primary key of the ratings stats
	 * @return the ratings stats
	 * @throws PortalException if a ratings stats with the primary key could not be found
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats getRatingsStats(
			long statsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRatingsStats(statsId);
	}

	/**
	 * Returns a range of all the ratings statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings statses
	 * @param end the upper bound of the range of ratings statses (not inclusive)
	 * @return the range of ratings statses
	 */
	public static java.util.List<com.liferay.ratings.kernel.model.RatingsStats>
		getRatingsStatses(int start, int end) {

		return getService().getRatingsStatses(start, end);
	}

	/**
	 * Returns the number of ratings statses.
	 *
	 * @return the number of ratings statses
	 */
	public static int getRatingsStatsesCount() {
		return getService().getRatingsStatsesCount();
	}

	public static com.liferay.ratings.kernel.model.RatingsStats getStats(
			long statsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStats(statsId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static java.util.List<com.liferay.ratings.kernel.model.RatingsStats>
		getStats(String className, java.util.List<Long> classPKs) {

		return getService().getStats(className, classPKs);
	}

	public static com.liferay.ratings.kernel.model.RatingsStats getStats(
			String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStats(className, classPK);
	}

	public static java.util.Map
		<Long, com.liferay.ratings.kernel.model.RatingsStats> getStats(
			String className, long[] classPKs) {

		return getService().getStats(className, classPKs);
	}

	/**
	 * Updates the ratings stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsStatsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsStats the ratings stats
	 * @return the ratings stats that was updated
	 */
	public static com.liferay.ratings.kernel.model.RatingsStats
		updateRatingsStats(
			com.liferay.ratings.kernel.model.RatingsStats ratingsStats) {

		return getService().updateRatingsStats(ratingsStats);
	}

	public static RatingsStatsLocalService getService() {
		if (_service == null) {
			_service = (RatingsStatsLocalService)PortalBeanLocatorUtil.locate(
				RatingsStatsLocalService.class.getName());
		}

		return _service;
	}

	private static RatingsStatsLocalService _service;

}