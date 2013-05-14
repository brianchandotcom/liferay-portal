/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.MembershipRequestServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestServiceImpl
	extends MembershipRequestServiceBaseImpl {

	@Override
	public MembershipRequest addMembershipRequest(
			long groupId, String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return membershipRequestLocalService.addMembershipRequest(
			getUserId(), groupId, comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId, int statusId)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		membershipRequestLocalService.deleteMembershipRequests(
			groupId, statusId);
	}

	@Override
	public MembershipRequest getMembershipRequest(long membershipRequestId)
		throws PortalException, SystemException {

		return membershipRequestLocalService.getMembershipRequest(
			membershipRequestId);
	}

	@Override
	public void updateStatus(
			long membershipRequestId, String reviewComments, int statusId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MembershipRequest membershipRequest =
			membershipRequestPersistence.findByPrimaryKey(membershipRequestId);

		GroupPermissionUtil.check(
			getPermissionChecker(), membershipRequest.getGroupId(),
			ActionKeys.ASSIGN_MEMBERS);

		membershipRequestLocalService.updateStatus(
			getUserId(), membershipRequestId, reviewComments, statusId, true,
			serviceContext);
	}

}