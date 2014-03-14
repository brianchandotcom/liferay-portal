/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Connor McKay
 * @author Igor Beslic
 */
public class UpgradePermission extends UpgradeProcess {

	public void addResourceBlock(
			long resourceBlockId, long companyId, long groupId, String name,
			String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into ResourceBlock (resourceBlockId, companyId, " +
					"groupId, name, permissionsHash, referenceCount) values " +
						"(?, ?, ?, ?, ?, ?)");

			ps.setLong(1, resourceBlockId);
			ps.setLong(2, companyId);
			ps.setLong(3, groupId);
			ps.setString(4, name);
			ps.setString(5, permissionsHash);
			ps.setInt(6, 1);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		addResourceBlockPermissions(
			resourceBlockId, resourceBlockPermissionsContainer);
	}

	protected void addResourceBlockPermission(
			long resourceBlockPermissionId, long resourceBlockId, long roleId,
			long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into ResourceBlockPermission " +
					"(resourceBlockPermissionId, resourceBlockId, roleId, " +
						"actionIds) values (?, ?, ?, ?)");

			ps.setLong(1, resourceBlockPermissionId);
			ps.setLong(2, resourceBlockId);
			ps.setLong(3, roleId);
			ps.setLong(4, actionIds);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addResourceBlockPermissions(
			long resourceBlockId,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws Exception {

		Map<Long, Long> permissions =
			resourceBlockPermissionsContainer.getPermissions();

		for (Map.Entry<Long, Long> permission : permissions.entrySet()) {
			addResourceBlockPermission(
				increment(), resourceBlockId, permission.getKey(),
				permission.getValue());
		}
	}

	protected void addResourceTypePermission(
			long resourceTypePermissionId, long companyId, String name,
			long roleId, long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into ResourceTypePermission " +
					"(resourceTypePermissionId, companyId, groupId, name, " +
						"roleId, actionIds) values (?, ?, ?, ?, ?, ?)");

			ps.setLong(1, resourceTypePermissionId);
			ps.setLong(2, companyId);
			ps.setLong(3, 0);
			ps.setString(4, name);
			ps.setLong(5, roleId);
			ps.setLong(6, actionIds);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void convertResourcePermissions(
			String tableName, String pkColumnName, long companyId, long groupId,
			String name, long primKey)
		throws Exception {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			getResourceBlockPermissionsContainer(
				companyId, groupId, name, primKey);

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		updateResourceBlockId(
			tableName, pkColumnName, primKey, companyId, groupId, name,
			permissionsHash, resourceBlockPermissionsContainer);
	}

	protected void convertResourcePermissions(
			String name, String tableName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int resourceBlocksProcessed = 0;

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + ", groupId, companyId from " +
					tableName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");

				convertResourcePermissions(
					tableName, pkColumnName, companyId, groupId, name, primKey);

				if (_log.isInfoEnabled()) {
					resourceBlocksProcessed ++;

					if ((resourceBlocksProcessed % 100) == 0) {
						_log.info("Processed 100 resource blocks for " + name);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		updateScopeResourcePermissions(name);
	}

	@Override
	protected void doUpgrade() throws Exception {

		// LPS-14202 and LPS-17841

		RoleLocalServiceUtil.checkSystemRoles();

		updatePermissions("com.liferay.portlet.bookmarks", true, true);
		updatePermissions("com.liferay.portlet.documentlibrary", false, true);
		updatePermissions("com.liferay.portlet.imagegallery", true, true);
		updatePermissions("com.liferay.portlet.messageboards", true, true);
		updatePermissions("com.liferay.portlet.shopping", true, true);

		convertResourcePermissions(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId");
		convertResourcePermissions(
			BookmarksFolder.class.getName(), "BookmarksFolder", "folderId");
	}

	protected long getResourceBlockId(
			long companyId, long groupId, String name, String permissionsHash)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourceBlockId from ResourceBlock where companyId = " +
					"? and groupId = ? and name = ? and permissionsHash = ?");

			ps.setLong(1, companyId);
			ps.setLong(2, groupId);
			ps.setString(3, name);
			ps.setString(4, permissionsHash);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("resourceBlockId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(long resourceBlockId)
		throws Exception {

		ResourceBlockPermissionsContainer resourceBlockPermissionContainer =
			new ResourceBlockPermissionsContainer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select roleId, actionIds from ResourceBlockPermissions " +
					"where resourceBlockId = " + resourceBlockId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long roleId = rs.getLong("roleId");
				long actionIds = rs.getLong("actionIds");

				resourceBlockPermissionContainer.addPermission(
					roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return resourceBlockPermissionContainer;
	}

	protected ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(
				long companyId, long groupId, String name, long primKey)
		throws Exception {

		ResourceBlockPermissionsContainer resourceBlockPermissionContainer =
			new ResourceBlockPermissionsContainer();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select roleId, actionIds from ");
			sb.append("resourcePermission where companyId = ? and name = ? ");
			sb.append("and ((primKey = ? and scope = ?) or (primKey = ? and ");
			sb.append("scope = ?) or scope = ? or scope = ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, companyId);
			ps.setString(2, name);
			ps.setString(3, String.valueOf(primKey));
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setString(5, String.valueOf(groupId));
			ps.setInt(6, ResourceConstants.SCOPE_GROUP);
			ps.setInt(7, ResourceConstants.SCOPE_COMPANY);
			ps.setInt(8, ResourceConstants.SCOPE_GROUP_TEMPLATE);

			rs = ps.executeQuery();

			while (rs.next()) {
				long roleId = rs.getLong("roleId");
				long actionIds = rs.getLong("actionIds");

				resourceBlockPermissionContainer.addPermission(
					roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return resourceBlockPermissionContainer;
	}

	protected void setCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIds)
		throws Exception {

		updateCompanyScopeResourceTypePermissions(
			companyId, name, roleId, actionIds);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourceBlockId from ResourceBlock where companyId = " +
					"? and name = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			ps.executeQuery();

			while (rs.next()) {
				long resourceBlockId = rs.getLong("resourceBlockId");

				updateResourceBlockPermissions(
					resourceBlockId, roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void setGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIds)
		throws Exception {

		updateGroupScopeResourceTypePermissions(
			companyId, groupId, name, roleId, actionIds);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourceBlockId from ResourceBlock where companyId = " +
					"? groupId = ? and name = ?");

			ps.setLong(1, companyId);
			ps.setLong(2, groupId);
			ps.setString(3, name);

			ps.executeQuery();

			while (rs.next()) {
				long resourceBlockId = rs.getLong("resourceBlockId");

				updateResourceBlockPermissions(
					resourceBlockId, roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCompanyScopeResourceTypePermissions(
			long companyId, String name, long roleId, long actionIds)
		throws Exception {

		updateGroupScopeResourceTypePermissions(
			companyId, 0, name, roleId, actionIds);
	}

	protected void updateGroupScopeResourceTypePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourceTypePermissionId from ResourceTypePermission " +
					"where companyId = ? and groupId = ? and name = ? and " +
						"roleId = ?");

			ps.setLong(1, companyId);
			ps.setLong(2, groupId);
			ps.setString(3, name);
			ps.setLong(4, roleId);

			if (rs.next()) {
				long resourceTypePermissionId = rs.getLong(
					"resourceTypePermissionId");

				updateResourceTypePermission(
					resourceTypePermissionId, actionIds);
			}
			else {
				addResourceTypePermission(
					increment(), companyId, name, roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePermissions(
			String name, boolean community, boolean guest)
		throws Exception {

		List<String> modelActions = ResourceActionsUtil.getModelResourceActions(
			name);

		ResourceActionLocalServiceUtil.checkResourceActions(name, modelActions);

		int scope = ResourceConstants.SCOPE_INDIVIDUAL;
		long actionIdsLong = 1;

		if (community) {
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.ORGANIZATION_USER, scope, actionIdsLong);
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.SITE_MEMBER, scope, actionIdsLong);
		}

		if (guest) {
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.GUEST, scope, actionIdsLong);
		}

		ResourcePermissionLocalServiceUtil.addResourcePermissions(
			name, RoleConstants.OWNER, scope, actionIdsLong);
	}

	protected void updatePermissionsHash(long resourceBlockId)
		throws Exception {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			getResourceBlockPermissionsContainer(resourceBlockId);

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update ResourceBlock set permissionsHash = ? where " +
					"resourceBlockId = ?");

			ps.setString(
				1, resourceBlockPermissionsContainer.getPermissionsHash());
			ps.setLong(2, resourceBlockId);
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateResourceBlockId(
			String tableName, String pkColumnName, long primKey, long companyId,
			long groupId, String name, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws Exception {

		long resourceBlockId = getResourceBlockId(
			companyId, groupId, name, permissionsHash);

		if (resourceBlockId == 0) {
			resourceBlockId = increment();

			addResourceBlock(
				resourceBlockId, companyId, groupId, name, permissionsHash,
				resourceBlockPermissionsContainer);
		}
		else {
			StringBundler sb = new StringBundler(4);

			sb.append("update ResourceBlock set referenceCount = ");
			sb.append("(referenceCount + 1) where referenceCount > 0 and ");
			sb.append("resourceBlockId = ");
			sb.append(resourceBlockId);

			runSQL(sb.toString());
		}

		StringBundler sb = new StringBundler(8);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set resourceBlockId = ");
		sb.append(resourceBlockId);
		sb.append(" where ");
		sb.append(pkColumnName);
		sb.append(" = ");
		sb.append(primKey);

		runSQL(sb.toString());
	}

	protected void updateResourceBlockPermission(
			long resourceBlockId, long roleId, long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourceBlockPermissionId from " +
					"ResourceBlockPermission where resourceBlockId = ? and " +
						"roleId = ?");

			ps.setLong(1, resourceBlockId);
			ps.setLong(2, roleId);

			if (rs.next()) {
				long resourceBlockPermissionId = rs.getLong(
					"resourceBlockPermissionId");

				StringBundler sb = new StringBundler(4);

				sb.append("update ResourceBlockPermission set actionIds = ");
				sb.append(actionIds);
				sb.append(" where resourceBlockPermissionId = ");
				sb.append(resourceBlockPermissionId);

				runSQL(sb.toString());
			}
			else {
				if (actionIds == 0) {
					return;
				}

				addResourceBlockPermission(
					increment(), resourceBlockId, roleId, actionIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourceBlockPermissions(
			long resourceBlockId, long roleId, long actionIds)
		throws Exception {

		updateResourceBlockPermission(resourceBlockId, roleId, actionIds);

		updatePermissionsHash(resourceBlockId);
	}

	protected void updateResourceTypePermission(
			long resourceTypePermissionId, long actionIds)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update ResourceTypePermissionId set actionIds = ");
		sb.append(actionIds);
		sb.append(" where resourcePermissionId = ");
		sb.append(resourceTypePermissionId);

		runSQL(sb.toString());
	}

	protected void updateScopeResourcePermissions(String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, scope, primKey, roleId, actionIds from " +
					"ResourcePermission where name = ? and (scope = ? or " +
						"scope = ? or scope = ?)");

			ps.setString(1, name);
			ps.setInt(2, ResourceConstants.SCOPE_COMPANY);
			ps.setInt(3, ResourceConstants.SCOPE_GROUP);
			ps.setInt(4, ResourceConstants.SCOPE_GROUP_TEMPLATE);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				int scope = rs.getInt("scope");
				long primKey = rs.getLong("primKey");
				long roleId = rs.getLong("roleId");
				long actionIds = rs.getLong("actionIds");

				if ((scope == ResourceConstants.SCOPE_COMPANY) ||
					(scope == ResourceConstants.SCOPE_GROUP_TEMPLATE)) {

					setCompanyScopePermissions(
						companyId, name, roleId, actionIds);
				}
				else if (scope == ResourceConstants.SCOPE_GROUP) {
					setGroupScopePermissions(
						companyId, primKey, name, roleId, actionIds);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradePermission.class);

}