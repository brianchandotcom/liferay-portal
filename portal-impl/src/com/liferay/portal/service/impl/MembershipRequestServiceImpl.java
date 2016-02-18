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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.base.MembershipRequestServiceBaseImpl;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestServiceImpl
	extends MembershipRequestServiceBaseImpl {

	@Override
	public MembershipRequest addMembershipRequest(
			long groupId, String comments, ServiceContext serviceContext)
		throws PortalException {

		return membershipRequestLocalService.addMembershipRequest(
			getUserId(), groupId, comments, serviceContext);
	}

	@Override
	public void deleteMembershipRequests(long groupId, long statusId)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		membershipRequestLocalService.deleteMembershipRequests(
			groupId, statusId);
	}

	@Override
	public MembershipRequest getMembershipRequest(long membershipRequestId)
		throws PortalException {

		MembershipRequest membershipRequest =
			membershipRequestLocalService.getMembershipRequest(
				membershipRequestId);

		GroupPermissionUtil.check(
			getPermissionChecker(), membershipRequest.getGroupId(),
			ActionKeys.ASSIGN_MEMBERS);

		return membershipRequest;
	}

	@Override
	public void updateStatus(
			long membershipRequestId, String reviewComments, long statusId,
			ServiceContext serviceContext)
		throws PortalException {

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