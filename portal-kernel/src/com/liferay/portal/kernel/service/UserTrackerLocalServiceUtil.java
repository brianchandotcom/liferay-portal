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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for UserTracker. This utility wraps
 * <code>com.liferay.portal.service.impl.UserTrackerLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerLocalService
 * @generated
 */
public class UserTrackerLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.UserTrackerLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.UserTracker addUserTracker(
		long companyId, long userId, java.util.Date modifiedDate,
		String sessionId, String remoteAddr, String remoteHost,
		String userAgent,
		java.util.List<com.liferay.portal.kernel.model.UserTrackerPath>
			userTrackerPaths) {

		return getService().addUserTracker(
			companyId, userId, modifiedDate, sessionId, remoteAddr, remoteHost,
			userAgent, userTrackerPaths);
	}

	/**
	 * Adds the user tracker to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was added
	 */
	public static com.liferay.portal.kernel.model.UserTracker addUserTracker(
		com.liferay.portal.kernel.model.UserTracker userTracker) {

		return getService().addUserTracker(userTracker);
	}

	/**
	 * Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	 *
	 * @param userTrackerId the primary key for the new user tracker
	 * @return the new user tracker
	 */
	public static com.liferay.portal.kernel.model.UserTracker createUserTracker(
		long userTrackerId) {

		return getService().createUserTracker(userTrackerId);
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
	 * Deletes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws PortalException if a user tracker with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.UserTracker deleteUserTracker(
			long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteUserTracker(userTrackerId);
	}

	/**
	 * Deletes the user tracker from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was removed
	 */
	public static com.liferay.portal.kernel.model.UserTracker deleteUserTracker(
		com.liferay.portal.kernel.model.UserTracker userTracker) {

		return getService().deleteUserTracker(userTracker);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.UserTracker fetchUserTracker(
		long userTrackerId) {

		return getService().fetchUserTracker(userTrackerId);
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
	 * Returns the user tracker with the primary key.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker
	 * @throws PortalException if a user tracker with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.UserTracker getUserTracker(
			long userTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserTracker(userTrackerId);
	}

	/**
	 * Returns a range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of user trackers
	 */
	public static java.util.List<com.liferay.portal.kernel.model.UserTracker>
		getUserTrackers(int start, int end) {

		return getService().getUserTrackers(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserTracker>
		getUserTrackers(long companyId, int start, int end) {

		return getService().getUserTrackers(companyId, start, end);
	}

	/**
	 * Returns the number of user trackers.
	 *
	 * @return the number of user trackers
	 */
	public static int getUserTrackersCount() {
		return getService().getUserTrackersCount();
	}

	/**
	 * Updates the user tracker in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was updated
	 */
	public static com.liferay.portal.kernel.model.UserTracker updateUserTracker(
		com.liferay.portal.kernel.model.UserTracker userTracker) {

		return getService().updateUserTracker(userTracker);
	}

	public static UserTrackerLocalService getService() {
		if (_service == null) {
			_service = (UserTrackerLocalService)PortalBeanLocatorUtil.locate(
				UserTrackerLocalService.class.getName());
		}

		return _service;
	}

	private static UserTrackerLocalService _service;

}