/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.service;

import com.liferay.account.service.AccountRoleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ServiceWrapper.class)
public class AccountRoleRoleServiceWrapper extends RoleServiceWrapper {

	@Override
	public Role fetchRole(long roleId) throws PortalException {
		Role role = _roleLocalService.fetchRole(roleId);

		if ((role != null) &&
			Objects.equals(role.getType(), RoleConstants.TYPE_ACCOUNT)) {

			_accountRoleService.getAccountRoleByRoleId(roleId);

			return role;
		}

		return super.fetchRole(roleId);
	}

	@Reference
	private AccountRoleService _accountRoleService;

	@Reference
	private RoleLocalService _roleLocalService;

}