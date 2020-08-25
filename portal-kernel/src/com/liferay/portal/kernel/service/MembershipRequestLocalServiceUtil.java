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
 * Provides the local service utility for MembershipRequest. This utility wraps
 * <code>com.liferay.portal.service.impl.MembershipRequestLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestLocalService
 * @generated
 */
public class MembershipRequestLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.MembershipRequestLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
			addMembershipRequest(
				long userId, long groupId, String comments,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMembershipRequest(
			userId, groupId, comments, serviceContext);
	}

	/**
	 * Adds the membership request to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MembershipRequestLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param membershipRequest the membership request
	 * @return the membership request that was added
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
		addMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return getService().addMembershipRequest(membershipRequest);
	}

	/**
	 * Creates a new membership request with the primary key. Does not add the membership request to the database.
	 *
	 * @param membershipRequestId the primary key for the new membership request
	 * @return the new membership request
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
		createMembershipRequest(long membershipRequestId) {

		return getService().createMembershipRequest(membershipRequestId);
	}

	/**
	 * Deletes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MembershipRequestLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param membershipRequestId the primary key of the membership request
	 * @return the membership request that was removed
	 * @throws PortalException if a membership request with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
			deleteMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMembershipRequest(membershipRequestId);
	}

	/**
	 * Deletes the membership request from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MembershipRequestLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param membershipRequest the membership request
	 * @return the membership request that was removed
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
		deleteMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return getService().deleteMembershipRequest(membershipRequest);
	}

	public static void deleteMembershipRequests(long groupId) {
		getService().deleteMembershipRequests(groupId);
	}

	public static void deleteMembershipRequests(long groupId, long statusId) {
		getService().deleteMembershipRequests(groupId, statusId);
	}

	public static void deleteMembershipRequestsByUserId(long userId) {
		getService().deleteMembershipRequestsByUserId(userId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.MembershipRequestModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.MembershipRequestModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.MembershipRequest
		fetchMembershipRequest(long membershipRequestId) {

		return getService().fetchMembershipRequest(membershipRequestId);
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
	 * Returns the membership request with the primary key.
	 *
	 * @param membershipRequestId the primary key of the membership request
	 * @return the membership request
	 * @throws PortalException if a membership request with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
			getMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMembershipRequest(membershipRequestId);
	}

	/**
	 * Returns a range of all the membership requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.MembershipRequestModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of membership requests
	 * @param end the upper bound of the range of membership requests (not inclusive)
	 * @return the range of membership requests
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.MembershipRequest>
			getMembershipRequests(int start, int end) {

		return getService().getMembershipRequests(start, end);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.MembershipRequest>
			getMembershipRequests(long userId, long groupId, long statusId) {

		return getService().getMembershipRequests(userId, groupId, statusId);
	}

	/**
	 * Returns the number of membership requests.
	 *
	 * @return the number of membership requests
	 */
	public static int getMembershipRequestsCount() {
		return getService().getMembershipRequestsCount();
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

	public static boolean hasMembershipRequest(
		long userId, long groupId, long statusId) {

		return getService().hasMembershipRequest(userId, groupId, statusId);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.MembershipRequest> search(
			long groupId, int status, int start, int end) {

		return getService().search(groupId, status, start, end);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.MembershipRequest> search(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.MembershipRequest> obc) {

		return getService().search(groupId, status, start, end, obc);
	}

	public static int searchCount(long groupId, int status) {
		return getService().searchCount(groupId, status);
	}

	/**
	 * Updates the membership request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MembershipRequestLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param membershipRequest the membership request
	 * @return the membership request that was updated
	 */
	public static com.liferay.portal.kernel.model.MembershipRequest
		updateMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return getService().updateMembershipRequest(membershipRequest);
	}

	public static void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			long statusId, boolean addUserToGroup,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateStatus(
			replierUserId, membershipRequestId, replyComments, statusId,
			addUserToGroup, serviceContext);
	}

	public static MembershipRequestLocalService getService() {
		if (_service == null) {
			_service =
				(MembershipRequestLocalService)PortalBeanLocatorUtil.locate(
					MembershipRequestLocalService.class.getName());
		}

		return _service;
	}

	private static MembershipRequestLocalService _service;

}