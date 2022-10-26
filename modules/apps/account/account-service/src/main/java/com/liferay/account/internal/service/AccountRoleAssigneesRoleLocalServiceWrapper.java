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

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Queiroz
 * @author Erick Monteiro
 */
@Component(service = ServiceWrapper.class)
public class AccountRoleAssigneesRoleLocalServiceWrapper
	extends RoleLocalServiceWrapper {

	@Override
	public int getAssigneesTotal(long roleId) throws PortalException {
		Role role = getRole(roleId);

		if (role.getType() == RoleConstants.TYPE_ACCOUNT) {
			return _userGroupRoleLocalService.dslQueryCount(
				DSLQueryFactoryUtil.countDistinct(
					UserGroupRoleTable.INSTANCE.userId
				).from(
					UserGroupRoleTable.INSTANCE
				).innerJoinON(
					UserTable.INSTANCE,
					UserTable.INSTANCE.userId.eq(
						UserGroupRoleTable.INSTANCE.userId)
				).where(
					UserGroupRoleTable.INSTANCE.roleId.eq(
						role.getRoleId()
					).and(
						UserTable.INSTANCE.status.eq(
							WorkflowConstants.STATUS_APPROVED)
					)
				));
		}

		return super.getAssigneesTotal(roleId);
	}

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}