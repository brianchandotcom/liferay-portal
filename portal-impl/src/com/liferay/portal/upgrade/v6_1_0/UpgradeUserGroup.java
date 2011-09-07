/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.upgrade.v6_1_0.util.UserGroupTemplateInfo;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public class UpgradeUserGroup extends UpgradeProcess {

	protected long addGroup(
			long companyId, long creatorUserId, String groupName,
			long layoutSetPrototypeId)
		throws Exception {

		long layoutSetPrototypeClassNameId = getClassNameId(
			LayoutSetPrototype.class.getName());

		long groupId = getGroupId(
			companyId, layoutSetPrototypeClassNameId, layoutSetPrototypeId);

		if (groupId > 0) {
			return groupId;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into Group_ (groupId, companyId, ");
			sb.append("creatorUserId, classNameId, classPK, parentGroupId, ");
			sb.append("liveGroupId, name, description, type_, typeSettings, ");
			sb.append("friendlyURL, site, active_) values (?, ?, ?, ?, ?, 0, ");
			sb.append("0, ?, ?, 0, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			groupId = increment();

			ps.setLong(1, groupId);
			ps.setLong(2, companyId);
			ps.setLong(3, creatorUserId);
			ps.setLong(4, layoutSetPrototypeClassNameId);
			ps.setLong(5, layoutSetPrototypeId);
			ps.setString(6, groupId + StringPool.MINUS + groupName);
			ps.setString(7, StringPool.BLANK);
			ps.setString(8, StringPool.BLANK);
			ps.setString(9, "/template-" + layoutSetPrototypeId);
			ps.setBoolean(10, false);
			ps.setBoolean(11, true);

			ps.execute();

			return groupId;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long addLayoutSet(
			long layoutSetId, long companyId, long groupId,
			long layoutSetPrototypeId,
			UserGroupTemplateInfo userGroupTemplateInfo)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into LayoutSet (layoutSetId, groupId, ");
			sb.append("companyId, privateLayout, logo, logoId, themeId, ");
			sb.append("colorSchemeId, wapThemeId, wapColorSchemeId, css, ");
			sb.append("pageCount, settings_, layoutSetPrototypeId) values ");
			sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, layoutSetId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setBoolean(4, userGroupTemplateInfo.isPrivateLayout());
			ps.setShort(5, userGroupTemplateInfo.getLogo());
			ps.setLong(6, userGroupTemplateInfo.getLogoId());
			ps.setString(7, userGroupTemplateInfo.getThemeId());
			ps.setString(8, userGroupTemplateInfo.getColorSchemeId());
			ps.setString(9, userGroupTemplateInfo.getWapThemeId());
			ps.setString(10, userGroupTemplateInfo.getWapColorSchemeId());
			ps.setString(11, userGroupTemplateInfo.getCss());
			ps.setLong(12, userGroupTemplateInfo.getPageCount());
			ps.setString(13, userGroupTemplateInfo.getSettings());
			ps.setLong(14, layoutSetPrototypeId);

			ps.execute();

			return layoutSetId;

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addLayoutSetPrototype(
			long layoutSetPrototypeId, long companyId, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into LayoutSetPrototype (layoutSetPrototypeId, " +
					"companyId, name, description, active_) values (?, ?, ?, " +
						"?, ?)");

			ps.setLong(1, layoutSetPrototypeId);
			ps.setLong(2, companyId);
			ps.setString(3, getNameXML(name));
			ps.setString(4, name);
			ps.setBoolean(5, true);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addPermission(
			long permissionId, long companyId, String actionId, long resourceId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Permission_ (permissionId, companyId, actionId, " +
					"resourceId) values (?, ?, ?, ?)");

			ps.setLong(1, permissionId);
			ps.setLong(2, companyId);
			ps.setString(3, actionId);
			ps.setLong(4, resourceId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addPermissions(
			long companyId, long userId, long layoutSetPrototypeId)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addResourcePermission(
				increment(), companyId, LayoutSetPrototype.class.getName(),
				layoutSetPrototypeId, userId);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			addResource(increment(), increment(), companyId);

			long resourceId = increment();

			addResource(resourceId, increment(), layoutSetPrototypeId);

			String[] actionIds = {
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.VIEW
			};

			for (String actionId : actionIds) {
				addPermission(increment(), companyId, actionId, resourceId);
			}
		}
	}

	protected long addResource(long resourceId, long codeId, long primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Resource_ (resourceId, codeId, primKey) values " +
					"(?, ?, ?)");

			ps.setLong(1, resourceId);
			ps.setLong(2, codeId);
			ps.setString(3, String.valueOf(primKey));

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return resourceId;
	}

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name,
			long primKey, long ownerId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into ResourcePermission (resourcePermissionId, " +
					"companyId, name, scope, primKey, roleId, ownerId, " +
						"actionIds) values (?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, name);
			ps.setLong(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setString(5, String.valueOf(primKey));
			ps.setLong(6, getRoleId(companyId, RoleConstants.OWNER));
			ps.setLong(7, ownerId);
			ps.setLong(8, getActionIds(name));

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected UserGroupTemplateInfo buildUserGroupsTemplatesInfo(ResultSet rs)
		throws Exception {

		UserGroupTemplateInfo userGroupTemplateInfo =
			new UserGroupTemplateInfo();

		userGroupTemplateInfo.setColorSchemeId(rs.getString("colorSchemeId"));
		userGroupTemplateInfo.setCompanyId(rs.getLong("companyId"));
		userGroupTemplateInfo.setCss(rs.getString("css"));
		userGroupTemplateInfo.setGroupId(rs.getLong("groupId"));
		userGroupTemplateInfo.setLayoutSetId(rs.getLong("layoutSetId"));
		userGroupTemplateInfo.setLayoutSetPrototypeId(
			rs.getLong("layoutSetPrototypeId"));
		userGroupTemplateInfo.setLogo(rs.getShort("logo"));
		userGroupTemplateInfo.setLogoId(rs.getLong("logoId"));
		userGroupTemplateInfo.setPageCount(rs.getLong("pageCount"));
		userGroupTemplateInfo.setPrivateLayout(rs.getBoolean("privateLayout"));
		userGroupTemplateInfo.setSettings(rs.getString("settings"));
		userGroupTemplateInfo.setThemeId(rs.getString("themeId"));
		userGroupTemplateInfo.setUserGroupId(rs.getLong("userGroupId"));
		userGroupTemplateInfo.setWapColorSchemeId(
			rs.getString("wapColorSchemeId"));
		userGroupTemplateInfo.setWapThemeId(rs.getString("wapThemeId"));

		return userGroupTemplateInfo;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(17);

			sb.append("select UserGroup.userGroupId as userGroupId, ");
			sb.append("UserGroup.companyId as companyId, UserGroup.name as ");
			sb.append("userGroupName, Group_.groupId as groupId, ");
			sb.append("Group_.creatorUserId as creatorUserId, ");
			sb.append("LayoutSet.layoutSetId as layoutSetId, ");
			sb.append("LayoutSet.privateLayout as privateLayout, ");
			sb.append("LayoutSet.logo as logo, LayoutSet.logoId as logoId, ");
			sb.append("LayoutSet.themeId as themeId, LayoutSet.colorSchemeId ");
			sb.append("as colorSchemeId, LayoutSet.wapThemeId as wapThemeId, ");
			sb.append("LayoutSet.wapColorSchemeId as wapColorSchemeId, ");
			sb.append("LayoutSet.css as css, LayoutSet.pageCount as ");
			sb.append("pageCount, LayoutSet.settings_ as settings, ");
			sb.append("LayoutSet.layoutSetPrototypeId as ");
			sb.append("layoutSetPrototypeId from UserGroup inner join ");
			sb.append("Group_ on Group_.classPK = UserGroup.userGroupId ");
			sb.append("inner join LayoutSet on (LayoutSet.groupId = ");
			sb.append("Group_.groupId) and (LayoutSet.pageCount > 0)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long userGroupId = rs.getLong("userGroupId");
				long companyId = rs.getLong("companyId");
				String userGroupName = rs.getString("userGroupName");
				long groupId = rs.getLong("groupId");
				long creatorUserId = rs.getLong("creatorUserId");
				boolean privateLayout = rs.getBoolean("privateLayout");

				long layoutSetPrototypeId = increment();

				addLayoutSetPrototype(
					layoutSetPrototypeId, companyId, userGroupName);

				long layoutSetPrototypeGroupId = addGroup(
					companyId, creatorUserId, userGroupName,
					layoutSetPrototypeId);

				addPermissions(companyId, creatorUserId, layoutSetPrototypeId);

				UserGroupTemplateInfo privateUserGroupTemplateInfo =
					buildUserGroupsTemplatesInfo(rs);

				boolean oldPrivateLayout =
					privateUserGroupTemplateInfo.isPrivateLayout();

				privateUserGroupTemplateInfo.setPrivateLayout(true);

				addLayoutSet(
					increment(), companyId, layoutSetPrototypeGroupId,
					layoutSetPrototypeId, privateUserGroupTemplateInfo);

				updateLayout(
					groupId, layoutSetPrototypeGroupId, oldPrivateLayout);

				UserGroupTemplateInfo publicUserGroupTemplateInfo =
					new UserGroupTemplateInfo();

				publicUserGroupTemplateInfo.setPrivateLayout(false);

				addLayoutSet(
					increment(), companyId, layoutSetPrototypeGroupId,
					layoutSetPrototypeId, publicUserGroupTemplateInfo);

				updateUserGroup(
					userGroupId, privateLayout, layoutSetPrototypeId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getActionIds(String name) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select sum(bitwiseValue) as actionIds from ResourceAction " +
					"where name = ?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("actionIds");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected long getClassNameId(String className) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, className);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("classNameId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected long getGroupId(long companyId, long classNameId, long classPK)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where (companyId = ?) and " +
					"(classNameId = ?) and (classPK = ?)");

			ps.setLong(1, companyId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected String getNameXML(String name) {
		StringBundler sb = new StringBundler(8);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<root default-locale=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\"><Name language-id=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\">");
		sb.append(name);
		sb.append("</Name></root>");

		return sb.toString();
	}

	protected long getRoleId(long companyId, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select roleId from Role_ where (companyId = ?) and (name = " +
					"?)");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("roleId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected void updateLayout(
			long oldGroupId, long newGroupId, boolean privateLayout)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Layout set groupId = ?, privateLayout = ? where " +
					"(groupId = ?) and (privateLayout = ?)");

			ps.setLong(1, newGroupId);
			ps.setBoolean(2, true);
			ps.setLong(3, oldGroupId);
			ps.setBoolean(4, privateLayout);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateUserGroup(
			long userGroupId, boolean privateLayout, long layoutSetPrototypeId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			String layoutSetPrototypeIdColumnName = null;

			if (privateLayout) {
				layoutSetPrototypeIdColumnName = "privateLayoutSetPrototypeId";
			}
			else {
				layoutSetPrototypeIdColumnName = "publicLayoutSetPrototypeId";
			}

			ps = con.prepareStatement(
				"update UserGroup set " + layoutSetPrototypeIdColumnName +
					" = ? where userGroupId = ?");

			ps.setLong(1, layoutSetPrototypeId);
			ps.setLong(2, userGroupId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}