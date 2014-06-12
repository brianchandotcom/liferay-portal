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

/**
 * Provides a wrapper for {@link MembershipRequestLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestLocalService
 * @generated
 */
@ProviderType
public class MembershipRequestLocalServiceWrapper
	implements MembershipRequestLocalService,
		ServiceWrapper<MembershipRequestLocalService> {
	public MembershipRequestLocalServiceWrapper(
		MembershipRequestLocalService membershipRequestLocalService) {
		_membershipRequestLocalService = membershipRequestLocalService;
	}

	/**
	* Adds the membership request to the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was added
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequestLocalService.addMembershipRequest(membershipRequest);
	}

	/**
	* Creates a new membership request with the primary key. Does not add the membership request to the database.
	*
	* @param membershipRequestId the primary key for the new membership request
	* @return the new membership request
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest createMembershipRequest(
		long membershipRequestId) {
		return _membershipRequestLocalService.createMembershipRequest(membershipRequestId);
	}

	/**
	* Deletes the membership request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request that was removed
	* @throws PortalException if a membership request with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest deleteMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestLocalService.deleteMembershipRequest(membershipRequestId);
	}

	/**
	* Deletes the membership request from the database. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was removed
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest deleteMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequestLocalService.deleteMembershipRequest(membershipRequest);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _membershipRequestLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _membershipRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _membershipRequestLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.MembershipRequest fetchMembershipRequest(
		long membershipRequestId) {
		return _membershipRequestLocalService.fetchMembershipRequest(membershipRequestId);
	}

	/**
	* Returns the membership request with the primary key.
	*
	* @param membershipRequestId the primary key of the membership request
	* @return the membership request
	* @throws PortalException if a membership request with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestLocalService.getMembershipRequest(membershipRequestId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _membershipRequestLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		int start, int end) {
		return _membershipRequestLocalService.getMembershipRequests(start, end);
	}

	/**
	* Returns the number of membership requests.
	*
	* @return the number of membership requests
	*/
	@Override
	public int getMembershipRequestsCount() {
		return _membershipRequestLocalService.getMembershipRequestsCount();
	}

	/**
	* Updates the membership request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param membershipRequest the membership request
	* @return the membership request that was updated
	*/
	@Override
	public com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequestLocalService.updateMembershipRequest(membershipRequest);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _membershipRequestLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_membershipRequestLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long userId, long groupId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestLocalService.addMembershipRequest(userId,
			groupId, comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId) {
		_membershipRequestLocalService.deleteMembershipRequests(groupId);
	}

	@Override
	public void deleteMembershipRequests(long groupId, int statusId) {
		_membershipRequestLocalService.deleteMembershipRequests(groupId,
			statusId);
	}

	@Override
	public void deleteMembershipRequestsByUserId(long userId) {
		_membershipRequestLocalService.deleteMembershipRequestsByUserId(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		long userId, long groupId, int statusId) {
		return _membershipRequestLocalService.getMembershipRequests(userId,
			groupId, statusId);
	}

	@Override
	public boolean hasMembershipRequest(long userId, long groupId, int statusId) {
		return _membershipRequestLocalService.hasMembershipRequest(userId,
			groupId, statusId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.MembershipRequest> search(
		long groupId, int status, int start, int end) {
		return _membershipRequestLocalService.search(groupId, status, start, end);
	}

	@Override
	public int searchCount(long groupId, int status) {
		return _membershipRequestLocalService.searchCount(groupId, status);
	}

	@Override
	public void updateStatus(long replierUserId, long membershipRequestId,
		java.lang.String replyComments, int statusId, boolean addUserToGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_membershipRequestLocalService.updateStatus(replierUserId,
			membershipRequestId, replyComments, statusId, addUserToGroup,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public MembershipRequestLocalService getWrappedMembershipRequestLocalService() {
		return _membershipRequestLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedMembershipRequestLocalService(
		MembershipRequestLocalService membershipRequestLocalService) {
		_membershipRequestLocalService = membershipRequestLocalService;
	}

	@Override
	public MembershipRequestLocalService getWrappedService() {
		return _membershipRequestLocalService;
	}

	@Override
	public void setWrappedService(
		MembershipRequestLocalService membershipRequestLocalService) {
		_membershipRequestLocalService = membershipRequestLocalService;
	}

	private MembershipRequestLocalService _membershipRequestLocalService;
}