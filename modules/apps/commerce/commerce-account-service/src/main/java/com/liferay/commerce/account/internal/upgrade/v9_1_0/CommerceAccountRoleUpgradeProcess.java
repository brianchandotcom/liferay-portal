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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommerceAccountRoleUpgradeProcess extends UpgradeProcess {

	public CommerceAccountRoleUpgradeProcess(
		AccountRoleLocalService accountRoleLocalService,
		ClassNameLocalService classNameLocalService,
		CounterLocalService counterLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_accountRoleLocalService = accountRoleLocalService;
		_classNameLocalService = classNameLocalService;
		_counterLocalService = counterLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select distinct UserGroupRole.roleId from UserGroupRole ",
					"inner join Role_ on UserGroupRole.roleId = Role_.roleId ",
					"where Role_.type_ =", RoleConstants.TYPE_SITE));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update Role_ set type_ = " +
							RoleConstants.TYPE_ACCOUNT + " where roleId = ?"));
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update UserGroupRole set roleId =  ?" +
							" where roleId = ?"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long roleId = resultSet.getLong(1);

					if (!_hasAccountEntryGroup(roleId)) {
						continue;
					}

					if (_hasNonaccountEntryGroup(roleId)) {
						AccountRole accountRole = _copyToAccountRole(roleId);

						preparedStatement3.setLong(1, accountRole.getRoleId());

						preparedStatement3.setLong(2, roleId);

						preparedStatement3.addBatch();
					}
					else {
						preparedStatement2.setLong(1, roleId);

						preparedStatement2.addBatch();
					}
				}

				preparedStatement2.executeBatch();
				preparedStatement3.executeBatch();
			}
		}
	}

	private AccountRole _copyToAccountRole(long roleId) throws Exception {
		Role role = _roleLocalService.getRole(roleId);

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			role.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			role.getName() + "_Account", role.getTitleMap(),
			role.getDescriptionMap());

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getRoleResourcePermissions(
				role.getRoleId());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermission newResourcePermission =
				_resourcePermissionLocalService.createResourcePermission(
					_counterLocalService.increment(
						ResourcePermission.class.getName()));

			newResourcePermission.setCompanyId(
				resourcePermission.getCompanyId());
			newResourcePermission.setName(resourcePermission.getName());
			newResourcePermission.setScope(resourcePermission.getScope());
			newResourcePermission.setPrimKey(resourcePermission.getPrimKey());
			newResourcePermission.setPrimKeyId(
				resourcePermission.getPrimKeyId());
			newResourcePermission.setRoleId(accountRole.getRoleId());
			newResourcePermission.setOwnerId(resourcePermission.getOwnerId());
			newResourcePermission.setActionIds(
				resourcePermission.getActionIds());
			newResourcePermission.setViewActionId(
				resourcePermission.getViewActionId());

			_resourcePermissionLocalService.addResourcePermission(
				newResourcePermission);
		}

		return accountRole;
	}

	private boolean _hasAccountEntryGroup(long roleId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(distinct UserGroupRole.groupId)",
					"from UserGroupRole inner join Group_ on ",
					"UserGroupRole.groupId = Group_.groupId and ",
					"Group.classNameId =",
					_classNameLocalService.getClassNameId(AccountEntry.class),
					" where UserGroupRole.roleId = ", roleId))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private boolean _hasNonaccountEntryGroup(long roleId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(distinct UserGroupRole.groupId)",
					"from UserGroupRole inner join Group_ on ",
					"UserGroupRole.groupId = Group_.groupId and ",
					"Group.classNameId !=",
					_classNameLocalService.getClassNameId(AccountEntry.class),
					" where UserGroupRole.roleId = ", roleId))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private final AccountRoleLocalService _accountRoleLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final CounterLocalService _counterLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}