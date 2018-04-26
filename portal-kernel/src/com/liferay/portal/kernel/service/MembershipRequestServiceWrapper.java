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

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link MembershipRequestService}.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestService
 * @generated
 */
@ProviderType
public class MembershipRequestServiceWrapper implements MembershipRequestService,
	ServiceWrapper<MembershipRequestService> {
	public MembershipRequestServiceWrapper(
		MembershipRequestService membershipRequestService) {
		_membershipRequestService = membershipRequestService;
	}

	@Override
	public com.liferay.portal.kernel.model.MembershipRequest addMembershipRequest(
		long groupId, String comments, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestService.addMembershipRequest(groupId,
			comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId, long statusId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_membershipRequestService.deleteMembershipRequests(groupId, statusId);
	}

	@Override
	public com.liferay.portal.kernel.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestService.getMembershipRequest(membershipRequestId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _membershipRequestService.getOSGiServiceIdentifier();
	}

	@Override
	public void updateStatus(long membershipRequestId, String reviewComments,
		long statusId, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_membershipRequestService.updateStatus(membershipRequestId,
			reviewComments, statusId, serviceContext);
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