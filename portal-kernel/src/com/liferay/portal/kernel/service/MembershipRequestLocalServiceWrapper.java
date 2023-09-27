/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MembershipRequestLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestLocalService
 * @generated
 */
public class MembershipRequestLocalServiceWrapper
	implements MembershipRequestLocalService,
			   ServiceWrapper<MembershipRequestLocalService> {

	public MembershipRequestLocalServiceWrapper() {
		this(null);
	}

	public MembershipRequestLocalServiceWrapper(
		MembershipRequestLocalService membershipRequestLocalService) {

		_membershipRequestLocalService = membershipRequestLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
			addMembershipRequest(
				long userId, long groupId, String comments,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.addMembershipRequest(
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
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
		addMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return _membershipRequestLocalService.addMembershipRequest(
			membershipRequest);
	}

	/**
	 * Creates a new membership request with the primary key. Does not add the membership request to the database.
	 *
	 * @param membershipRequestId the primary key for the new membership request
	 * @return the new membership request
	 */
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
		createMembershipRequest(long membershipRequestId) {

		return _membershipRequestLocalService.createMembershipRequest(
			membershipRequestId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
			deleteMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.deleteMembershipRequest(
			membershipRequestId);
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
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
		deleteMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return _membershipRequestLocalService.deleteMembershipRequest(
			membershipRequest);
	}

	@Override
	public void deleteMembershipRequests(long groupId) {
		_membershipRequestLocalService.deleteMembershipRequests(groupId);
	}

	@Override
	public void deleteMembershipRequests(long groupId, long statusId) {
		_membershipRequestLocalService.deleteMembershipRequests(
			groupId, statusId);
	}

	@Override
	public void deleteMembershipRequestsByUserId(long userId) {
		_membershipRequestLocalService.deleteMembershipRequestsByUserId(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _membershipRequestLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _membershipRequestLocalService.dslQueryCount(dslQuery);
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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _membershipRequestLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _membershipRequestLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _membershipRequestLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _membershipRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _membershipRequestLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
		fetchMembershipRequest(long membershipRequestId) {

		return _membershipRequestLocalService.fetchMembershipRequest(
			membershipRequestId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _membershipRequestLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _membershipRequestLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the membership request with the primary key.
	 *
	 * @param membershipRequestId the primary key of the membership request
	 * @return the membership request
	 * @throws PortalException if a membership request with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
			getMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.getMembershipRequest(
			membershipRequestId);
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
	@Override
	public java.util.List<com.liferay.portal.kernel.model.MembershipRequest>
		getMembershipRequests(int start, int end) {

		return _membershipRequestLocalService.getMembershipRequests(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.MembershipRequest>
		getMembershipRequests(long userId, long groupId, long statusId) {

		return _membershipRequestLocalService.getMembershipRequests(
			userId, groupId, statusId);
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
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _membershipRequestLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasMembershipRequest(
		long userId, long groupId, long statusId) {

		return _membershipRequestLocalService.hasMembershipRequest(
			userId, groupId, statusId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.MembershipRequest>
		search(long groupId, int status, int start, int end) {

		return _membershipRequestLocalService.search(
			groupId, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.MembershipRequest>
		search(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.MembershipRequest>
					orderByComparator) {

		return _membershipRequestLocalService.search(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long groupId, int status) {
		return _membershipRequestLocalService.searchCount(groupId, status);
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
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
		updateMembershipRequest(
			com.liferay.portal.kernel.model.MembershipRequest
				membershipRequest) {

		return _membershipRequestLocalService.updateMembershipRequest(
			membershipRequest);
	}

	@Override
	public void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			long statusId, boolean addUserToGroup,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_membershipRequestLocalService.updateStatus(
			replierUserId, membershipRequestId, replyComments, statusId,
			addUserToGroup, serviceContext);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _membershipRequestLocalService.getBasePersistence();
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