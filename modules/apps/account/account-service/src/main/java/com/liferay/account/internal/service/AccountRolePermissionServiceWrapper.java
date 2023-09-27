/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ServiceWrapper.class)
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