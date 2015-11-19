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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for MembershipRequest. This utility wraps
 * {@link com.liferay.portal.service.impl.MembershipRequestLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestLocalService
 * @see com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.MembershipRequestLocalServiceImpl
 * @generated
 */
@ProviderType
public class MembershipRequestLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.MembershipRequestLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the membership request to the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was added
	*/
	public static com.liferay.portal.model.MembershipRequest addMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return getService().addMembershipRequest(membershipRequest);
	}

	public static com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long userId, long groupId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMembershipRequest(userId, groupId, comments,
			serviceContext);
	}

	/**
	* Creates a new membership request with the primary key. Does not add the membership request to the database.
	*
	* @param membershipRequestId the primary key for the new membership request
	* @return the new membership request
	*/
	public static com.liferay.portal.model.MembershipRequest createMembershipRequest(
		long membershipRequestId) {
		return getService().createMembershipRequest(membershipRequestId);
	}

	/**
	* Deletes the membership request from the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was removed
	*/
	public static com.liferay.portal.model.MembershipRequest deleteMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return getService().deleteMembershipRequest(membershipRequest);
	}

	/**
	* Deletes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request that was removed
	* @throws PortalException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest deleteMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMembershipRequest(membershipRequestId);
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
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.MembershipRequest fetchMembershipRequest(
		long membershipRequestId) {
		return getService().fetchMembershipRequest(membershipRequestId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the membership request with the primary key.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request
	* @throws PortalException if a membership request with the primary key could not be found
	*/
	public static com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMembershipRequest(membershipRequestId);
	}

	/**
	* Returns a range of all the membership requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.MembershipRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of membership requests
	* @param end the upper bound of the range of membership requests (not inclusive)
	* @return the range of membership requests
	*/
	public static java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		int start, int end) {
		return getService().getMembershipRequests(start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		long userId, long groupId, long statusId) {
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
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasMembershipRequest(long userId, long groupId,
		long statusId) {
		return getService().hasMembershipRequest(userId, groupId, statusId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> search(
		long groupId, int status, int start, int end) {
		return getService().search(groupId, status, start, end);
	}

	public static int searchCount(long groupId, int status) {
		return getService().searchCount(groupId, status);
	}

	/**
	* Updates the membership request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was updated
	*/
	public static com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return getService().updateMembershipRequest(membershipRequest);
	}

	public static void updateStatus(long replierUserId,
		long membershipRequestId, java.lang.String replyComments,
		long statusId, boolean addUserToGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateStatus(replierUserId, membershipRequestId, replyComments,
			statusId, addUserToGroup, serviceContext);
	}

	public static MembershipRequestLocalService getService() {
		if (_service == null) {
			_service = (MembershipRequestLocalService)PortalBeanLocatorUtil.locate(MembershipRequestLocalService.class.getName());

			ReferenceRegistry.registerReference(MembershipRequestLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(MembershipRequestLocalService service) {
	}

	private static MembershipRequestLocalService _service;
}