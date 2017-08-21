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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alec Shay
 */
public class UpgradeMDRRuleGroupInstance extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		populateCompanyIds();
		populateResourcePermissions();
	}

	public long getActionIds(String className) throws Exception {
		long actionIds = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ?");

			ps.setString(1, className);

			rs = ps.executeQuery();

			while (rs.next()) {
				long bitwiseValue = rs.getLong(1);

				actionIds |= bitwiseValue;
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return actionIds;
	}

	public Map<Long, Long> getOwnerRoleIds() throws Exception {
		Map<Long, Long> ownerRoleIds = new HashMap<>();

		String roleName = RoleConstants.OWNER;
		int roleType = RoleConstants.TYPE_REGULAR;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select companyId, roleId from Role_ where name = ? and " +
					"type_ = ?");

			ps.setString(1, roleName);
			ps.setInt(2, roleType);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong(1);
				long roleId = rs.getLong(2);

				ownerRoleIds.put(companyId, roleId);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return ownerRoleIds;
	}

	public void populateCompanyIds() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("select MDRRuleGroup.companyId, ");
		sb.append("MDRRuleGroupInstance.ruleGroupInstanceId from ");
		sb.append("MDRRuleGroup left join MDRRuleGroupInstance on ");
		sb.append("MDRRuleGroup.ruleGroupId = ");
		sb.append("MDRRuleGroupInstance.ruleGroupId where ");
		sb.append("MDRRuleGroupInstance.companyId = 0");

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MDRRuleGroupInstance set companyId = ? where " +
						"ruleGroupInstanceId = ?");

			PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps2.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong(1);
					long ruleGroupInstanceId = rs.getLong(2);

					ps1.setLong(1, companyId);
					ps1.setLong(2, ruleGroupInstanceId);

					ps1.addBatch();
				}

				ps1.executeBatch();
			}
		}
	}

	public void populateResourcePermissions() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("select MDRRuleGroupInstance.companyId, ");
		sb.append("MDRRuleGroupInstance.ruleGroupInstanceId, ");
		sb.append("MDRRuleGroupInstance.userId from MDRRuleGroupInstance ");
		sb.append("where not exists (select 1 from ResourcePermission where ");
		sb.append("(MDRRuleGroupInstance.companyId = ResourcePermission.");
		sb.append("companyId) and (MDRRuleGroupInstance.ruleGroupInstanceId =");
		sb.append("ResourcePermission.primKeyId) and (MDRRuleGroupInstance.");
		sb.append("userId = ResourcePermission.ownerId) and ");
		sb.append("(ResourcePermission.name = '");
		sb.append("com.liferay.mobile.device.rules.model.MDRRuleGroupInstance");
		sb.append("'))");

		StringBundler sb2 = new StringBundler();

		sb2.append("insert into ResourcePermission (resourcePermissionId, ");
		sb2.append("companyId, name, scope, primKey, primKeyId, roleId, ");
		sb2.append("ownerId, actionIds, viewActionId) values (?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?)");

		String className =
			"com.liferay.mobile.device.rules.model.MDRRuleGroupInstance";
		Map<Long, Long> ownerRoleIds = getOwnerRoleIds();

		long actionIds = getActionIds(className);

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());

			PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps2.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong(1);
					long ruleGroupInstanceId = rs.getLong(2);
					long userId = rs.getLong(3);

					ps1.setLong(
						1, increment(ResourcePermission.class.getName()));
					ps1.setLong(2, companyId);
					ps1.setString(3, className);
					ps1.setInt(4, 4);
					ps1.setString(5, String.valueOf(ruleGroupInstanceId));
					ps1.setLong(6, ruleGroupInstanceId);
					ps1.setLong(7, ownerRoleIds.get(companyId));
					ps1.setLong(8, userId);
					ps1.setLong(9, actionIds);
					ps1.setBoolean(10, true);

					ps1.addBatch();
				}

				ps1.executeBatch();
			}
		}
	}

}