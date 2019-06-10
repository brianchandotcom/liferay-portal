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

package com.liferay.portal.upgrade.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.lang.reflect.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Preston Crary
 */
public abstract class BaseUpgradeResourceBlock extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String className = getClassName();

		_upgradeCompanyScopePermissions(className);

		_upgradeGroupScopePermissions(className);

		_upgradeGroupTemplateScopePermissions(className);

		_upgradeIndividualScopePermissions(className);

		_removeResourceBlocks(className);

		alter(getTableClass(), new AlterTableDropColumn("resourceBlockId"));
	}

	protected abstract String getClassName();

	protected abstract String getPrimaryKeyName();

	protected abstract Class<?> getTableClass();

	protected abstract boolean hasUserId();

	private void _addResourcePermissionBatch(
			PreparedStatement ps, long companyId, String name, int scope,
			long primKeyId, long roleId, long ownerId, long actionIds)
		throws SQLException {

		ps.setLong(1, 0L);
		ps.setLong(2, increment(ResourcePermission.class.getName()));
		ps.setLong(3, companyId);
		ps.setString(4, name);
		ps.setInt(5, scope);
		ps.setString(6, String.valueOf(primKeyId));
		ps.setLong(7, primKeyId);
		ps.setLong(8, roleId);
		ps.setLong(9, ownerId);
		ps.setLong(10, actionIds);
		ps.setBoolean(11, (actionIds % 2) == 1);

		ps.addBatch();
	}

	private void _removeResourceBlocks(String className) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"delete from ResourceTypePermission where name = ?")) {

			ps.setString(1, className);

			ps.executeUpdate();
		}

		try (PreparedStatement selectPS = connection.prepareStatement(
				"select resourceBlockId from ResourceBlock where name = ?")) {

			selectPS.setString(1, className);

			try (ResultSet rs = selectPS.executeQuery();
				PreparedStatement deletePS =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from ResourceBlockPermission where " +
							"resourceBlockId = ?")) {

				while (rs.next()) {
					long resourceBlockId = rs.getLong(1);

					deletePS.setLong(1, resourceBlockId);

					deletePS.addBatch();
				}

				deletePS.executeBatch();
			}
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from ResourceBlock where name = ?")) {

			ps.setString(1, className);

			ps.executeUpdate();
		}
	}

	private void _upgradeCompanyScopePermissions(String className)
		throws SQLException {

		StringBundler sb = new StringBundler(8);

		sb.append("select ResourceTypePermission.companyId, ");
		sb.append("ResourceTypePermission.roleId, ");
		sb.append("ResourceTypePermission.actionIds from ");
		sb.append("ResourceTypePermission inner join Role_ on Role_.roleId =");
		sb.append("ResourceTypePermission.roleId where ");
		sb.append("ResourceTypePermission.groupId = 0 and Role_.type_ = ");
		sb.append(RoleConstants.TYPE_REGULAR);
		sb.append(" and ResourceTypePermission.name = ?");

		try (PreparedStatement selectPS = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			selectPS.setString(1, className);

			try (ResultSet rs = selectPS.executeQuery();
				PreparedStatement insertPS =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long roleId = rs.getLong("roleId");
					long actionIds = rs.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPS, companyId, className,
						ResourceConstants.SCOPE_COMPANY, companyId, roleId, 0,
						actionIds);
				}

				insertPS.executeBatch();
			}
		}
	}

	private void _upgradeGroupScopePermissions(String className)
		throws SQLException {

		try (PreparedStatement selectPS = connection.prepareStatement(
				SQLTransformer.transform(
					"select companyId, groupId, roleId, actionIds from " +
						"ResourceTypePermission where groupId != 0 and name " +
							"= ?"))) {

			selectPS.setString(1, className);

			try (ResultSet rs = selectPS.executeQuery();
				PreparedStatement insertPS =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long groupId = rs.getLong("groupId");
					long roleId = rs.getLong("roleId");
					long actionIds = rs.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPS, companyId, className,
						ResourceConstants.SCOPE_GROUP, groupId, roleId, 0,
						actionIds);
				}

				insertPS.executeBatch();
			}
		}
	}

	private void _upgradeGroupTemplateScopePermissions(String className)
		throws SQLException {

		StringBundler sb = new StringBundler(8);

		sb.append("select ResourceTypePermission.companyId, ");
		sb.append("ResourceTypePermission.roleId, ");
		sb.append("ResourceTypePermission.actionIds from ");
		sb.append("ResourceTypePermission inner join Role_ on Role_.roleId =");
		sb.append("ResourceTypePermission.roleId where ");
		sb.append("ResourceTypePermission.groupId = 0 and Role_.type_ != ");
		sb.append(RoleConstants.TYPE_REGULAR);
		sb.append(" and ResourceTypePermission.name = ?");

		try (PreparedStatement selectPS = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			selectPS.setString(1, className);

			try (ResultSet rs = selectPS.executeQuery();
				PreparedStatement insertPS =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long roleId = rs.getLong("roleId");
					long actionIds = rs.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPS, companyId, className,
						ResourceConstants.SCOPE_GROUP_TEMPLATE, 0, roleId, 0,
						actionIds);
				}

				insertPS.executeBatch();
			}
		}
	}

	private void _upgradeIndividualScopePermissions(String className)
		throws Exception {

		StringBundler sb = new StringBundler(16);

		Class<?> tableClass = getTableClass();

		Field tableNameField = tableClass.getField("TABLE_NAME");

		String tableName = (String)tableNameField.get(null);

		String primaryKeyName = getPrimaryKeyName();

		sb.append("select ResourceBlock.companyId, ");
		sb.append(tableName);
		sb.append(".");
		sb.append(primaryKeyName);
		sb.append(", ResourceBlockPermission.roleId, ");

		if (hasUserId()) {
			sb.append(tableName);
			sb.append(".userId, ");
		}

		sb.append("ResourceBlockPermission.resourceBlockPermissionId, ");
		sb.append("ResourceBlockPermission.actionIds from ");
		sb.append(tableName);
		sb.append(" inner join ResourceBlock on ");
		sb.append("(ResourceBlock.resourceBlockId = ");
		sb.append(tableName);
		sb.append(".resourceBlockId) inner join ResourceBlockPermission on ");
		sb.append("(ResourceBlockPermission.resourceBlockId = ResourceBlock.");
		sb.append("resourceBlockId) where ResourceBlock.name = ?");

		try (PreparedStatement selectPS = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			selectPS.setString(1, className);

			try (ResultSet rs = selectPS.executeQuery();
				PreparedStatement insertPS =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long primKeyId = rs.getLong(primaryKeyName);
					long roleId = rs.getLong("roleId");

					long userId = 0;

					if (hasUserId()) {
						userId = rs.getLong("userId");
					}

					long actionIds = rs.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPS, companyId, className,
						ResourceConstants.SCOPE_INDIVIDUAL, primKeyId, roleId,
						userId, actionIds);
				}

				insertPS.executeBatch();
			}
		}
	}

	private static final String _INSERT_SQL;

	static {
		StringBundler sb = new StringBundler(4);

		sb.append("insert into ResourcePermission(mvccVersion, ");
		sb.append("resourcePermissionId, companyId, name, scope, primKey, ");
		sb.append("primKeyId, roleId, ownerId, actionIds, viewActionId) ");
		sb.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		_INSERT_SQL = sb.toString();
	}

}