/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link MembershipRequestService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestService
 * @generated
 */
public class MembershipRequestServiceWrapper implements MembershipRequestService,
	ServiceWrapper<MembershipRequestService> {
	public MembershipRequestServiceWrapper(
		MembershipRequestService membershipRequestService) {
		_membershipRequestService = membershipRequestService;
	}

	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long groupId, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestService.addMembershipRequest(groupId, comments);
	}

	public void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestService.deleteMembershipRequests(groupId, statusId);
	}

	public com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestService.getMembershipRequest(membershipRequestId);
	}

	public void updateStatus(long membershipRequestId,
		java.lang.String reviewComments, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestService.updateStatus(membershipRequestId,
			reviewComments, statusId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public MembershipRequestService getWrappedMembershipRequestService() {
		return _membershipRequestService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedMembershipRequestService(
		MembershipRequestService membershipRequestService) {
		_membershipRequestService = membershipRequestService;
	}

	public MembershipRequestService getWrappedService() {
		return _membershipRequestService;
	}

	public void setWrappedService(
		MembershipRequestService membershipRequestService) {
		_membershipRequestService = membershipRequestService;
	}

	private MembershipRequestService _membershipRequestService;
}