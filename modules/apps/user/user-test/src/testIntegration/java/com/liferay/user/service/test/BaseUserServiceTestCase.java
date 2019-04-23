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

package com.liferay.user.service.test;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.test.rule.Inject;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
public abstract class BaseUserServiceTestCase {

	protected void unsetGroupUsers(
			long groupId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		_userService.unsetGroupUsers(
			groupId, new long[] {objectUser.getUserId()}, serviceContext);
	}

	protected void unsetOrganizationUsers(
			long organizationId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		_userService.unsetOrganizationUsers(
			organizationId, new long[] {objectUser.getUserId()});
	}

	@Inject
	protected UserLocalService userLocalService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private UserService _userService;

}