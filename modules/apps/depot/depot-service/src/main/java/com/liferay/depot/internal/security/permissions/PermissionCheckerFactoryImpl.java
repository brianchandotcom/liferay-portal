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

package com.liferay.depot.internal.security.permissions;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina Gonzálaez
 */
@Component(
	property = "service.ranking:Integer=100",
	service = PermissionCheckerFactory.class
)
public class PermissionCheckerFactoryImpl implements PermissionCheckerFactory {

	@Override
	public PermissionChecker create(User user) {
		return new DepotPermissionChecker(
			_permissionCheckerFactory.create(user), _groupLocalService,
			_roleLocalService, _userGroupRoleLocalService);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(&(original.bean=true)(bean.id=com.liferay.portal.kernel.security.permission.PermissionCheckerFactory))"
	)
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}