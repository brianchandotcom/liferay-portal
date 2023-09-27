/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.service.test;

import com.liferay.portal.kernel.model.User;
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

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(subjectUser));

		_userService.unsetGroupUsers(
			groupId, new long[] {objectUser.getUserId()}, new ServiceContext());
	}

	protected void unsetOrganizationUsers(
			long organizationId, User subjectUser, User objectUser)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(subjectUser));

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