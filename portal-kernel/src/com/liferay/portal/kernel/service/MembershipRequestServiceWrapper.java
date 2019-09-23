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

/**
 * Provides a wrapper for {@link MembershipRequestService}.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestService
 * @generated
 */
public class MembershipRequestServiceWrapper
	implements MembershipRequestService,
			   ServiceWrapper<MembershipRequestService> {

	public MembershipRequestServiceWrapper(
		MembershipRequestService membershipRequestService) {

		_membershipRequestService = membershipRequestService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MembershipRequestServiceUtil} to access the membership request remote service. Add custom service methods to <code>com.liferay.portal.service.impl.MembershipRequestServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
			addMembershipRequest(
				long groupId, java.lang.String comments,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestService.addMembershipRequest(
			groupId, comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId, long statusId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_membershipRequestService.deleteMembershipRequests(groupId, statusId);
	}

	@Override
	public com.liferay.portal.kernel.model.MembershipRequest
			getMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _membershipRequestService.getMembershipRequest(
			membershipRequestId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _membershipRequestService.getOSGiServiceIdentifier();
	}

	@Override
	public void updateStatus(
			long membershipRequestId, java.lang.String reviewComments,
			long statusId, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_membershipRequestService.updateStatus(
			membershipRequestId, reviewComments, statusId, serviceContext);
	}

	@Override
	public MembershipRequestService getWrappedService() {
		return _membershipRequestService;
	}

	@Override
	public void setWrappedService(
		MembershipRequestService membershipRequestService) {

		_membershipRequestService = membershipRequestService;
	}

	private MembershipRequestService _membershipRequestService;

}