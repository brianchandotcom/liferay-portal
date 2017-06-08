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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.users.admin.demo.data.creator.SiteMemberUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = SiteMemberUserDemoDataCreator.class)
public class SiteMemberUserDemoDataCreatorImpl
	extends BaseUserDemoDataCreator implements SiteMemberUserDemoDataCreator {

	@Override
	public User create(long groupId) throws PortalException {
		return create(groupId, null);
	}

	@Override
	public User create(long groupId, String emailAddress)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = createUser(group.getCompanyId(), emailAddress);

		userLocalService.addGroupUser(groupId, user.getUserId());

		return user;
	}

	@Override
	public User create(long groupId, String emailAddress, long[] roleIds)
		throws PortalException {

		User user = create(groupId, emailAddress);

		_userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), groupId, roleIds);

		return user;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}