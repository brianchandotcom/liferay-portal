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
@Component(immediate = true, service = ServiceWrapper.class)
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