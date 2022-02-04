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

package com.liferay.account.internal.service;

import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.PermissionServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class AccountRolePermissionServiceWrapper
	extends PermissionServiceWrapper {

	@Override
	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException {

		if (name.equals(Role.class.getName())) {
			AccountRole accountRole =
				_accountRoleLocalService.fetchAccountRoleByRoleId(primKey);

			if ((accountRole != null) &&
				_accountRoleModelResourcePermission.contains(
					GuestOrUserUtil.getPermissionChecker(), accountRole,
					ActionKeys.DEFINE_PERMISSIONS)) {

				return;
			}
		}

		super.checkPermission(groupId, name, primKey);
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountRole)"
	)
	private ModelResourcePermission<AccountRole>
		_accountRoleModelResourcePermission;

}