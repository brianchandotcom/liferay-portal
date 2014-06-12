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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _membershipRequestService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_membershipRequestService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long groupId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestService.addMembershipRequest(groupId,
			comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_membershipRequestService.deleteMembershipRequests(groupId, statusId);
	}

	@Override
	public com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _membershipRequestService.getMembershipRequest(membershipRequestId);
	}

	@Override
	public void updateStatus(long membershipRequestId,
		java.lang.String reviewComments, int statusId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_membershipRequestService.updateStatus(membershipRequestId,
			reviewComments, statusId, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public MembershipRequestService getWrappedMembershipRequestService() {
		return _membershipRequestService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedMembershipRequestService(
		MembershipRequestService membershipRequestService) {
		_membershipRequestService = membershipRequestService;
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