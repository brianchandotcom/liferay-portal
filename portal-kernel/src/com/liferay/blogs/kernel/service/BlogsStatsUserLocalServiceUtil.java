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

package com.liferay.blogs.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for BlogsStatsUser. This utility wraps
 * {@link com.liferay.portlet.blogs.service.impl.BlogsStatsUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @see com.liferay.portlet.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl
 * @see com.liferay.portlet.blogs.service.impl.BlogsStatsUserLocalServiceImpl
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class BlogsStatsUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.blogs.service.impl.BlogsStatsUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the blogs stats user to the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user
	* @return the blogs stats user that was added
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser addBlogsStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser blogsStatsUser) {
		return getService().addBlogsStatsUser(blogsStatsUser);
	}

	/**
	* Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	*
	* @param statsUserId the primary key for the new blogs stats user
	* @return the new blogs stats user
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser createBlogsStatsUser(
		long statsUserId) {
		return getService().createBlogsStatsUser(statsUserId);
	}

	/**
	* Deletes the blogs stats user from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user
	* @return the blogs stats user that was removed
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser deleteBlogsStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser blogsStatsUser) {
		return getService().deleteBlogsStatsUser(blogsStatsUser);
	}

	/**
	* Deletes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the blogs stats user
	* @return the blogs stats user that was removed
	* @throws PortalException if a blogs stats user with the primary key could not be found
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser deleteBlogsStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteBlogsStatsUser(statsUserId);
	}

	public static com.liferay.blogs.kernel.model.BlogsStatsUser fetchBlogsStatsUser(
		long statsUserId) {
		return getService().fetchBlogsStatsUser(statsUserId);
	}

	/**
	* Returns the blogs stats user with the primary key.
	*
	* @param statsUserId the primary key of the blogs stats user
	* @return the blogs stats user
	* @throws PortalException if a blogs stats user with the primary key could not be found
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser getBlogsStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBlogsStatsUser(statsUserId);
	}

	public static com.liferay.blogs.kernel.model.BlogsStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStatsUser(groupId, userId);
	}

	/**
	* Updates the blogs stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user
	* @return the blogs stats user that was updated
	*/
	public static com.liferay.blogs.kernel.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser blogsStatsUser) {
		return getService().updateBlogsStatsUser(blogsStatsUser);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of blogs stats users.
	*
	* @return the number of blogs stats users
	*/
	public static int getBlogsStatsUsersCount() {
		return getService().getBlogsStatsUsersCount();
	}

	public static int getCompanyStatsUsersCount(long companyId) {
		return getService().getCompanyStatsUsersCount(companyId);
	}

	public static int getGroupStatsUsersCount(long groupId) {
		return getService().getGroupStatsUsersCount(groupId);
	}

	public static int getOrganizationStatsUsersCount(long organizationId) {
		return getService().getOrganizationStatsUsersCount(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the blogs stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of blogs stats users
	*/
	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getBlogsStatsUsers(
		int start, int end) {
		return getService().getBlogsStatsUsers(start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {
		return getService().getCompanyStatsUsers(companyId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService().getCompanyStatsUsers(companyId, start, end, obc);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {
		return getService().getGroupStatsUsers(groupId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService().getGroupStatsUsers(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {
		return getService().getGroupsStatsUsers(companyId, groupId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {
		return getService().getOrganizationStatsUsers(organizationId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService()
				   .getOrganizationStatsUsers(organizationId, start, end, obc);
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

	public static void deleteStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser statsUsers) {
		getService().deleteStatsUser(statsUsers);
	}

	public static void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStatsUser(statsUserId);
	}

	public static void deleteStatsUserByGroupId(long groupId) {
		getService().deleteStatsUserByGroupId(groupId);
	}

	public static void deleteStatsUserByUserId(long userId) {
		getService().deleteStatsUserByUserId(userId);
	}

	public static void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStatsUser(groupId, userId);
	}

	public static void updateStatsUser(long groupId, long userId,
		java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStatsUser(groupId, userId, displayDate);
	}

	public static BlogsStatsUserLocalService getService() {
		if (_service == null) {
			_service = (BlogsStatsUserLocalService)PortalBeanLocatorUtil.locate(BlogsStatsUserLocalService.class.getName());

			ReferenceRegistry.registerReference(BlogsStatsUserLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static BlogsStatsUserLocalService _service;
}