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
 * Provides the local service utility for RecentLayoutSetBranch. This utility wraps
 * <code>com.liferay.portal.service.impl.RecentLayoutSetBranchLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutSetBranchLocalService
 * @generated
 */
public class RecentLayoutSetBranchLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RecentLayoutSetBranchLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
			addRecentLayoutSetBranch(
				long userId, long layoutSetBranchId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRecentLayoutSetBranch(
			userId, layoutSetBranchId, layoutSetId);
	}

	/**
	 * Adds the recent layout set branch to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RecentLayoutSetBranchLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param recentLayoutSetBranch the recent layout set branch
	 * @return the recent layout set branch that was added
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		addRecentLayoutSetBranch(
			com.liferay.portal.kernel.model.RecentLayoutSetBranch
				recentLayoutSetBranch) {

		return getService().addRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	/**
	 * Creates a new recent layout set branch with the primary key. Does not add the recent layout set branch to the database.
	 *
	 * @param recentLayoutSetBranchId the primary key for the new recent layout set branch
	 * @return the new recent layout set branch
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		createRecentLayoutSetBranch(long recentLayoutSetBranchId) {

		return getService().createRecentLayoutSetBranch(
			recentLayoutSetBranchId);
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
	 * Deletes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RecentLayoutSetBranchLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch that was removed
	 * @throws PortalException if a recent layout set branch with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
			deleteRecentLayoutSetBranch(long recentLayoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRecentLayoutSetBranch(
			recentLayoutSetBranchId);
	}

	/**
	 * Deletes the recent layout set branch from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RecentLayoutSetBranchLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param recentLayoutSetBranch the recent layout set branch
	 * @return the recent layout set branch that was removed
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		deleteRecentLayoutSetBranch(
			com.liferay.portal.kernel.model.RecentLayoutSetBranch
				recentLayoutSetBranch) {

		return getService().deleteRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	public static void deleteRecentLayoutSetBranches(long layoutSetBranchId) {
		getService().deleteRecentLayoutSetBranches(layoutSetBranchId);
	}

	public static void deleteUserRecentLayoutSetBranches(long userId) {
		getService().deleteUserRecentLayoutSetBranches(userId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		fetchRecentLayoutSetBranch(long recentLayoutSetBranchId) {

		return getService().fetchRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		fetchRecentLayoutSetBranch(long userId, long layoutSetId) {

		return getService().fetchRecentLayoutSetBranch(userId, layoutSetId);
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
	 * Returns the recent layout set branch with the primary key.
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch
	 * @throws PortalException if a recent layout set branch with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
			getRecentLayoutSetBranch(long recentLayoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentLayoutSetBranch(recentLayoutSetBranchId);
	}

	/**
	 * Returns a range of all the recent layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of recent layout set branchs
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.RecentLayoutSetBranch>
			getRecentLayoutSetBranchs(int start, int end) {

		return getService().getRecentLayoutSetBranchs(start, end);
	}

	/**
	 * Returns the number of recent layout set branchs.
	 *
	 * @return the number of recent layout set branchs
	 */
	public static int getRecentLayoutSetBranchsCount() {
		return getService().getRecentLayoutSetBranchsCount();
	}

	/**
	 * Updates the recent layout set branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RecentLayoutSetBranchLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param recentLayoutSetBranch the recent layout set branch
	 * @return the recent layout set branch that was updated
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutSetBranch
		updateRecentLayoutSetBranch(
			com.liferay.portal.kernel.model.RecentLayoutSetBranch
				recentLayoutSetBranch) {

		return getService().updateRecentLayoutSetBranch(recentLayoutSetBranch);
	}

	public static RecentLayoutSetBranchLocalService getService() {
		if (_service == null) {
			_service =
				(RecentLayoutSetBranchLocalService)PortalBeanLocatorUtil.locate(
					RecentLayoutSetBranchLocalService.class.getName());
		}

		return _service;
	}

	private static RecentLayoutSetBranchLocalService _service;

}