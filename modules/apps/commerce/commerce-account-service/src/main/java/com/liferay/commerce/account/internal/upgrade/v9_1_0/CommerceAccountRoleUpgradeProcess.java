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

package com.liferay.commerce.account.internal.upgrade.v9_1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Riccardo Alberti
 */
public class CommerceAccountRoleUpgradeProcess extends UpgradeProcess {

	public CommerceAccountRoleUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_classNameLocalService = classNameLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		// Get all site roles that are used both as site roles and account roles
		String query = StringBundler.concat(
			"with x as (select usergrouprole.roleid from usergrouprole inner ",
			"join group_ on group_.groupid = usergrouprole.groupid inner join ",
			"classname_ on group_.classnameid= classname_.classnameid where ",
			"classname_.classnameid = ",
			_classNameLocalService.getClassNameId(AccountEntry.class),
			") select distinct usergrouprole.roleid from usergrouprole inner ",
			"join x on usergrouprole.roleid = x.roleid inner join group_ on ",
			"group_.groupid = usergrouprole.groupid inner join classname_ on ",
			"group_.classnameid = classname_.classnameid where ",
			"classname_.classnameid != ",
			_classNameLocalService.getClassNameId(AccountEntry.class));

		Map<Long, Long> roleIdMap = new HashMap<>();

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(query);

			while (resultSet.next()) {
				// Duplicate roles that are site and account roles

				long roleId = resultSet.getLong(1);

				Role siteRole = _roleLocalService.getRole(roleId);

				Role accountRole = _roleLocalService.addRole(
					siteRole.getUserId(), siteRole.getClassName(),
					siteRole.getClassPK(), siteRole.getName() + " Account",
					siteRole.getTitleMap(), siteRole.getDescriptionMap(),
					RoleConstants.TYPE_ACCOUNT, siteRole.getSubtype(),
					new ServiceContext());

				List<ResourcePermission> resourcePermissions =
					_resourcePermissionLocalService.getRoleResourcePermissions(
						siteRole.getRoleId());

				for (ResourcePermission resourcePermission : resourcePermissions) {
					List<ResourceAction> resourceActions =
						_resourceActionLocalService.getResourceActions(
							resourcePermission.getName());

					for (ResourceAction resourceAction : resourceActions) {
						_resourcePermissionLocalService.addResourcePermission(
							resourcePermission.getCompanyId(),
							resourcePermission.getName(),
							resourcePermission.getScope(),
							resourcePermission.getPrimKey(),
							accountRole.getRoleId(),
							resourceAction.getActionId());
					}
				}

				roleIdMap.put(siteRole.getRoleId(), accountRole.getRoleId());
			}
		}

		if (roleIdMap.isEmpty()) {
			return;
		}

		Set<Long> siteRoleIds = roleIdMap.keySet();

		Stream<Long> stream = siteRoleIds.stream();

		// Update site roles to make them account roles if they're just account roles

		runSQL(
			StringBundler.concat(
				"update role_ set type_ = ", RoleConstants.TYPE_ACCOUNT,
				"where roleId in (select distinct ",
				"usergrouprole.roleid from usergrouprole inner join group_ on ",
				"group_.groupid = usergrouprole.groupid inner join classname_ ",
				"on group_.classnameid = classname_.classnameid where ",
				"classname_.classnameid = ",
				_classNameLocalService.getClassNameId(AccountEntry.class),
				" and usergrouprole.roleid not in (",
				stream.map(
					roleId -> String.valueOf(roleIdMap.get(roleId))
				).collect(
					Collectors.joining(", ")
				),
				"))"));

		// Update usergrouproles and set the new role (account role) instead of the old one (site role)

		for (Map.Entry<Long, Long> entry : roleIdMap.entrySet()) {
			runSQL(
				StringBundler.concat(
					"update usergrouprole set roleid = ", entry.getValue(),
					" where roleId = ", entry.getKey()));
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService _resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}