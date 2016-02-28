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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Raymond Augé
 */
public class UpgradePermission extends UpgradeProcess {

	protected void addRole(
			long roleId, long companyId, long classNameId, long classPK,
			String name, int type)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into Role_ (roleId, companyId, classNameId, classPK, " +
					"name, type_) values (?, ?, ?, ?, ?, ?)")) {

			ps.setLong(1, roleId);
			ps.setLong(2, companyId);
			ps.setLong(3, classNameId);
			ps.setLong(4, classPK);
			ps.setString(5, name);
			ps.setInt(6, type);

			ps.executeUpdate();
		}
	}

	protected void addSingleApproverWorkflowRoles() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				addSingleApproverWorkflowRoles(companyId);
			}
		}
	}

	protected void addSingleApproverWorkflowRoles(long companyId)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.model.Role");
		long roleId = increment();

		addRole(
			roleId, companyId, classNameId, roleId,
			_ROLE_COMMUNITY_CONTENT_REVIEWER, RoleConstants.TYPE_SITE);

		classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.model.Organization");
		roleId = increment();

		addRole(
			roleId, companyId, classNameId, roleId,
			_ROLE_ORGANIZATION_CONTENT_REVIEWER,
			RoleConstants.TYPE_ORGANIZATION);

		classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.model.Company");
		roleId = increment();

		addRole(
			roleId, companyId, classNameId, roleId,
			_ROLE_PORTAL_CONTENT_REVIEWER, RoleConstants.TYPE_REGULAR);
	}

	protected void addUserGroupRole(long userId, long groupId, long roleId)
		throws Exception {

		if (hasUserGroupRole(userId, groupId, roleId)) {
			return;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into UserGroupRole (userId, groupId, roleId) values " +
					"(?, ?, ?)")) {

			ps.setLong(1, userId);
			ps.setLong(2, groupId);
			ps.setLong(3, roleId);

			ps.executeUpdate();
		}
	}

	protected void addUserRole(long userId, long roleId) throws Exception {
		if (hasUserRole(userId, roleId)) {
			return;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into Users_Roles (userId, roleId) values (?, ?)")) {

			ps.setLong(1, userId);
			ps.setLong(2, roleId);

			ps.executeUpdate();
		}
	}

	protected void assignSingleApproverWorkflowRoles(
			long companyId, long roleId, long groupId)
		throws Exception {

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select classNameId from Group_ where groupId = ?")) {

			ps1.setLong(1, groupId);

			try (ResultSet rs1 = ps1.executeQuery()) {
				long classNameId = 0;

				if (rs1.next()) {
					classNameId = rs1.getLong("classNameId");
				}

				String className = PortalUtil.getClassName(classNameId);

				long communityContentReviewerRoleId = getRoleId(
					companyId, _ROLE_COMMUNITY_CONTENT_REVIEWER);
				long organizationContentReviewerRoleId = getRoleId(
					companyId, _ROLE_ORGANIZATION_CONTENT_REVIEWER);
				long portalContentReviewerRoleId = getRoleId(
					companyId, _ROLE_PORTAL_CONTENT_REVIEWER);

				StringBundler sb = new StringBundler(5);

				sb.append("(select User_.* from User_, Users_Roles where ");
				sb.append("User_.userId = Users_Roles.userId and ");
				sb.append("Users_Roles.roleId = ?) union all (select User_.* ");
				sb.append("from User_, UserGroupRole where User_.userId = ");
				sb.append("UserGroupRole.userId and UserGroupRole.roleId = ?)");

				try (PreparedStatement ps2 = connection.prepareStatement(
						sb.toString())) {

					ps2.setLong(1, roleId);
					ps2.setLong(2, roleId);

					try (ResultSet rs2 = ps2.executeQuery()) {
						while (rs2.next()) {
							long userId = rs2.getLong("userId");

							if (className.equals(
									"com.liferay.portal.model.Company")) {

								addUserRole(
									userId, portalContentReviewerRoleId);
							}
							else if (className.equals(
										"com.liferay.portal.model.Group")) {

								addUserGroupRole(
									userId, groupId,
									communityContentReviewerRoleId);
							}
							else if (className.equals(
										"com.liferay.portal.model." +
											"Organization")) {

								addUserGroupRole(
									userId, groupId,
									organizationContentReviewerRoleId);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		addSingleApproverWorkflowRoles();

		updatePermissions();
	}

	protected long getRoleId(long companyId, String name) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select roleId from Role_ where companyId = ? and name = ?")) {

			ps.setLong(1, companyId);
			ps.setString(2, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("roleId");
				}

				return 0;
			}
		}
	}

	protected boolean hasUserGroupRole(long userId, long groupId, long roleId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from UserGroupRole where userId = ? and " +
					"groupId = ? and roleId = ?")) {

			ps.setLong(1, userId);
			ps.setLong(2, groupId);
			ps.setLong(3, roleId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected boolean hasUserRole(long userId, long roleId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from Users_Roles where userId = ? and " +
					"roleId = ?")) {

			ps.setLong(1, userId);
			ps.setLong(2, roleId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected void updatePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(11);

			sb.append("select ResourcePermission.companyId, ");
			sb.append("ResourcePermission.roleId, ResourcePermission.primKey ");
			sb.append("from ResourcePermission, ResourceAction where ");
			sb.append("ResourceAction.name = 'com.liferay.portlet.journal' ");
			sb.append("and ResourceAction.name = ResourcePermission.name and ");
			sb.append("ResourceAction.actionId = 'APPROVE_ARTICLE' and ");
			sb.append("ResourcePermission.scope = 4 and ");
			sb.append("ResourcePermission.actionIds >= ");
			sb.append("ResourceAction.bitwiseValue and ");
			sb.append("mod((ResourcePermission.actionIds / ");
			sb.append("ResourceAction.bitwiseValue), 2) = 1");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long roleId = rs.getLong("roleId");
					long groupId = GetterUtil.getLong(rs.getString("primKey"));

					assignSingleApproverWorkflowRoles(
						companyId, roleId, groupId);
				}
			}
		}
	}

	private static final String _ROLE_COMMUNITY_CONTENT_REVIEWER =
		"Community Content Reviewer";

	private static final String _ROLE_ORGANIZATION_CONTENT_REVIEWER =
		"Organization Content Reviewer";

	private static final String _ROLE_PORTAL_CONTENT_REVIEWER =
		"Portal Content Reviewer";

}