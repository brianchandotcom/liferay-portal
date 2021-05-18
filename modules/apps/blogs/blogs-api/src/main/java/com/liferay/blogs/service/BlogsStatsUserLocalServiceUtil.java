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

package com.liferay.blogs.service;

import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for BlogsStatsUser. This utility wraps
 * <code>com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the blogs stats user to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogsStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was added
	 */
	public static BlogsStatsUser addBlogsStatsUser(
		BlogsStatsUser blogsStatsUser) {

		return getService().addBlogsStatsUser(blogsStatsUser);
	}

	/**
	 * Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	 *
	 * @param statsUserId the primary key for the new blogs stats user
	 * @return the new blogs stats user
	 */
	public static BlogsStatsUser createBlogsStatsUser(long statsUserId) {
		return getService().createBlogsStatsUser(statsUserId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the blogs stats user from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogsStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was removed
	 */
	public static BlogsStatsUser deleteBlogsStatsUser(
		BlogsStatsUser blogsStatsUser) {

		return getService().deleteBlogsStatsUser(blogsStatsUser);
	}

	/**
	 * Deletes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogsStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws PortalException if a blogs stats user with the primary key could not be found
	 */
	public static BlogsStatsUser deleteBlogsStatsUser(long statsUserId)
		throws PortalException {

		return getService().deleteBlogsStatsUser(statsUserId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteStatsUser(BlogsStatsUser statsUsers) {
		getService().deleteStatsUser(statsUsers);
	}

	public static void deleteStatsUser(long statsUserId)
		throws PortalException {

		getService().deleteStatsUser(statsUserId);
	}

	public static void deleteStatsUserByGroupId(long groupId) {
		getService().deleteStatsUserByGroupId(groupId);
	}

	public static void deleteStatsUserByUserId(long userId) {
		getService().deleteStatsUserByUserId(userId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static BlogsStatsUser fetchBlogsStatsUser(long statsUserId) {
		return getService().fetchBlogsStatsUser(statsUserId);
	}

	public static BlogsStatsUser fetchStatsUser(long groupId, long userId) {
		return getService().fetchStatsUser(groupId, userId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the blogs stats user with the primary key.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws PortalException if a blogs stats user with the primary key could not be found
	 */
	public static BlogsStatsUser getBlogsStatsUser(long statsUserId)
		throws PortalException {

		return getService().getBlogsStatsUser(statsUserId);
	}

	/**
	 * Returns a range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of blogs stats users
	 */
	public static List<BlogsStatsUser> getBlogsStatsUsers(int start, int end) {
		return getService().getBlogsStatsUsers(start, end);
	}

	/**
	 * Returns the number of blogs stats users.
	 *
	 * @return the number of blogs stats users
	 */
	public static int getBlogsStatsUsersCount() {
		return getService().getBlogsStatsUsersCount();
	}

	public static List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {

		return getService().getCompanyStatsUsers(companyId, start, end);
	}

	public static List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return getService().getCompanyStatsUsers(
			companyId, start, end, orderByComparator);
	}

	public static int getCompanyStatsUsersCount(long companyId) {
		return getService().getCompanyStatsUsersCount(companyId);
	}

	public static List<BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		return getService().getGroupsStatsUsers(companyId, groupId, start, end);
	}

	public static List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {

		return getService().getGroupStatsUsers(groupId, start, end);
	}

	public static List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return getService().getGroupStatsUsers(
			groupId, start, end, orderByComparator);
	}

	public static int getGroupStatsUsersCount(long groupId) {
		return getService().getGroupStatsUsersCount(groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {

		return getService().getOrganizationStatsUsers(
			organizationId, start, end);
	}

	public static List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return getService().getOrganizationStatsUsers(
			organizationId, start, end, orderByComparator);
	}

	public static int getOrganizationStatsUsersCount(long organizationId) {
		return getService().getOrganizationStatsUsersCount(organizationId);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException {

		return getService().getStatsUser(groupId, userId);
	}

	/**
	 * Updates the blogs stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlogsStatsUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was updated
	 */
	public static BlogsStatsUser updateBlogsStatsUser(
		BlogsStatsUser blogsStatsUser) {

		return getService().updateBlogsStatsUser(blogsStatsUser);
	}

	public static void updateStatsUser(long groupId, long userId)
		throws PortalException {

		getService().updateStatsUser(groupId, userId);
	}

	public static void updateStatsUser(
			long groupId, long userId, java.util.Date displayDate)
		throws PortalException {

		getService().updateStatsUser(groupId, userId, displayDate);
	}

	public static BlogsStatsUser updateStatsUser(
			long groupId, long userId, int ratingsTotalEntries,
			double ratingsTotalScore, double ratingsAverageScore)
		throws PortalException {

		return getService().updateStatsUser(
			groupId, userId, ratingsTotalEntries, ratingsTotalScore,
			ratingsAverageScore);
	}

	public static BlogsStatsUserLocalService getService() {
		return _service;
	}

	private static volatile BlogsStatsUserLocalService _service;

}