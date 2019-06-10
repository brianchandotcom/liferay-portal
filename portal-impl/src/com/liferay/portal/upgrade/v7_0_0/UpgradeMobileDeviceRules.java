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

		try (PreparedStatement ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ?")) {

			ps.setString(1, className);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long bitwiseValue = rs.getLong(1);

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

		try (PreparedStatement ps = connection.prepareStatement(
				"select companyId, roleId from Role_ where name = ? and " +
					"type_ = ?")) {

			ps.setString(1, roleName);
			ps.setInt(2, roleType);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong(1);
					long roleId = rs.getLong(2);

					ownerRoleIds.put(companyId, roleId);
				}
			}
		}

		return ownerRoleIds;
	}

	public void populateCompanyIds() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select MDRRuleGroup.companyId, ");
		sb.append("MDRRuleGroupInstance.ruleGroupInstanceId from ");
		sb.append("MDRRuleGroup left join MDRRuleGroupInstance on ");
		sb.append("MDRRuleGroup.ruleGroupId = ");
		sb.append("MDRRuleGroupInstance.ruleGroupId where ");
		sb.append("MDRRuleGroupInstance.companyId = 0");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MDRRuleGroupInstance set companyId = ? where " +
						"ruleGroupInstanceId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong(1);
				long ruleGroupInstanceId = rs.getLong(2);

				ps2.setLong(1, companyId);
				ps2.setLong(2, ruleGroupInstanceId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	public void populateResourcePermissions() throws Exception {
		StringBundler sb1 = new StringBundler(12);

		sb1.append("select MDRRuleGroupInstance.companyId, ");
		sb1.append("MDRRuleGroupInstance.ruleGroupInstanceId, ");
		sb1.append("MDRRuleGroupInstance.userId from MDRRuleGroupInstance ");
		sb1.append("where not exists (select 1 from ResourcePermission where ");
		sb1.append("(MDRRuleGroupInstance.companyId = ResourcePermission.");
		sb1.append("companyId) and (MDRRuleGroupInstance.ruleGroupInstanceId ");
		sb1.append("= ResourcePermission.primKeyId) and ");
		sb1.append("(MDRRuleGroupInstance.userId = ");
		sb1.append("ResourcePermission.ownerId) and (ResourcePermission.name ");
		sb1.append("= '");
		sb1.append(_CLASS_NAME);
		sb1.append("'))");

		StringBundler sb2 = new StringBundler(4);

		sb2.append("insert into ResourcePermission (resourcePermissionId, ");
		sb2.append("companyId, name, scope, primKey, primKeyId, roleId, ");
		sb2.append("ownerId, actionIds, viewActionId) values (?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?)");

		Map<Long, Long> ownerRoleIds = getOwnerRoleIds();

		long actionIds = getActionIds(_CLASS_NAME);

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong(1);
				long ruleGroupInstanceId = rs.getLong(2);
				long userId = rs.getLong(3);

				ps2.setLong(1, increment(ResourcePermission.class.getName()));
				ps2.setLong(2, companyId);
				ps2.setString(3, _CLASS_NAME);
				ps2.setInt(4, 4);
				ps2.setString(5, String.valueOf(ruleGroupInstanceId));
				ps2.setLong(6, ruleGroupInstanceId);
				ps2.setLong(7, ownerRoleIds.get(companyId));
				ps2.setLong(8, userId);
				ps2.setLong(9, actionIds);
				ps2.setBoolean(10, true);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.mobile.device.rules.model.MDRRuleGroupInstance";

}