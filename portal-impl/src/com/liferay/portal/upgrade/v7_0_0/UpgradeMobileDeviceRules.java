/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alec Shay
 */
public class UpgradeMobileDeviceRules extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		populateCompanyIds();
		populateResourcePermissions();
	}

	public long getActionIds(String className) throws Exception {
		long actionIds = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ?")) {

			preparedStatement.setString(1, className);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long bitwiseValue = resultSet.getLong(1);

					actionIds |= bitwiseValue;
				}
			}
		}

		return actionIds;
	}

	public Map<Long, Long> getOwnerRoleIds() throws Exception {
		Map<Long, Long> ownerRoleIds = new HashMap<>();

		String roleName = RoleConstants.OWNER;
		int roleType = RoleConstants.TYPE_REGULAR;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, roleId from Role_ where name = ? and " +
					"type_ = ?")) {

			preparedStatement.setString(1, roleName);
			preparedStatement.setInt(2, roleType);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);
					long roleId = resultSet.getLong(2);

					ownerRoleIds.put(companyId, roleId);
				}
			}
		}

		return ownerRoleIds;
	}

	public void populateCompanyIds() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select MDRRuleGroup.companyId, ",
					"MDRRuleGroupInstance.ruleGroupInstanceId from ",
					"MDRRuleGroup left join MDRRuleGroupInstance on ",
					"MDRRuleGroup.ruleGroupId = ",
					"MDRRuleGroupInstance.ruleGroupId where ",
					"MDRRuleGroupInstance.companyId = 0"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MDRRuleGroupInstance set companyId = ? where " +
						"ruleGroupInstanceId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				long ruleGroupInstanceId = resultSet.getLong(2);

				preparedStatement2.setLong(1, companyId);
				preparedStatement2.setLong(2, ruleGroupInstanceId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	public void populateResourcePermissions() throws Exception {
		Map<Long, Long> ownerRoleIds = getOwnerRoleIds();

		long actionIds = getActionIds(_CLASS_NAME);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select MDRRuleGroupInstance.companyId, ",
					"MDRRuleGroupInstance.ruleGroupInstanceId, ",
					"MDRRuleGroupInstance.userId from MDRRuleGroupInstance ",
					"where not exists (select 1 from ResourcePermission where ",
					"(MDRRuleGroupInstance.companyId = ResourcePermission.",
					"companyId) and (MDRRuleGroupInstance.ruleGroupInstanceId ",
					"= ResourcePermission.primKeyId) and ",
					"(MDRRuleGroupInstance.userId = ",
					"ResourcePermission.ownerId) and (ResourcePermission.name ",
					"= '", _CLASS_NAME, "'))"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ResourcePermission ",
						"(resourcePermissionId, companyId, name, scope, ",
						"primKey, primKeyId, roleId, ownerId, actionIds, ",
						"viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				long ruleGroupInstanceId = resultSet.getLong(2);
				long userId = resultSet.getLong(3);

				preparedStatement2.setLong(
					1, increment(ResourcePermission.class.getName()));
				preparedStatement2.setLong(2, companyId);
				preparedStatement2.setString(3, _CLASS_NAME);
				preparedStatement2.setInt(4, 4);
				preparedStatement2.setString(
					5, String.valueOf(ruleGroupInstanceId));
				preparedStatement2.setLong(6, ruleGroupInstanceId);
				preparedStatement2.setLong(7, ownerRoleIds.get(companyId));
				preparedStatement2.setLong(8, userId);
				preparedStatement2.setLong(9, actionIds);
				preparedStatement2.setBoolean(10, true);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.mobile.device.rules.model.MDRRuleGroupInstance";

}