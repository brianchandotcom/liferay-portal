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
import com.liferay.portal.kernel.exception.SystemException;
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
import java.sql.SQLException;

/**
 * @author Miguel Pastor
 */
public class UpgradeUserGroup extends UpgradeProcess {

	protected long addLayoutSet(
			long companyId, long groupId, long layoutSetPrototypeId,
			UserGroupTemplateInfo userGroupTemplateInfo)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append(
				"insert into LayoutSet (layoutSetId,groupId,companyId,");
			sb.append("privateLayout,logo,logoId,themeId,colorSchemeId,");
			sb.append("wapThemeId,wapColorSchemeId,css,pageCount,");
			sb.append("settings_,layoutSetPrototypeId) values ");
			sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			long layoutSetId = increment();

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

	protected long addLayoutSetPrototype(
		long companyId, String name) throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("insert into LayoutSetPrototype (layoutSetPrototypeId,");
			sb.append("companyId,name, description, settings_,active_)");
			sb.append(" values (?,?,?,?,NULL,1)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			long layoutSetPrototypeId = increment();
			ps.setLong(1, layoutSetPrototypeId);
			ps.setLong(2, companyId);
			ps.setString(3, composeI18N(name));
			ps.setString(4, name);

			ps.execute();

			return layoutSetPrototypeId;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long addLayoutSetPrototypeGroup(
			long companyId, long layoutSetPrototypeId, long creatorUserId,
			String groupName)
		throws Exception {

		long groupId = findGroupByC_C_C(
			companyId, layoutSetPrototypeClassNameId,
			layoutSetPrototypeId);

		if (groupId > 0) {
			return groupId;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into Group_ (companyId, creatorUserId, ");
			sb.append("classNameId, classPK, parentGroupId, liveGroupId, ");
			sb.append("name, description, type_, typeSettings, friendlyURL, ");
			sb.append("site, active_,groupId) values (?, ?, ?, ?, 0, 0, ?, ");
			sb.append("NULL, 0, NULL, ?, 0, 1, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, companyId);
			ps.setLong(2, creatorUserId);
			ps.setLong(3, layoutSetPrototypeClassNameId);
			ps.setLong(4, layoutSetPrototypeId);

			long layoutSetPrototypeGroupId = increment();

			String autoGroupName =
				layoutSetPrototypeGroupId + StringPool.MINUS + groupName;

			ps.setString(5, autoGroupName);
			ps.setString(6, String.valueOf(
				StringPool.SLASH + "template-"+layoutSetPrototypeId));
			ps.setLong(7, layoutSetPrototypeGroupId);

			ps.execute();

			return layoutSetPrototypeGroupId;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	protected void addPermission(
			long companyId, String actionId, long resourceId)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("insert into Permission_ (companyId, actionId, ");
			sb.append("resourceId, permissionId) values (?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, companyId);
			ps.setString(2, actionId);
			ps.setLong(3, resourceId);
			ps.setLong(4, increment());

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected long addResource(long primKey)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;

		long resourceId = increment();

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("insert into Resource_ (codeId, primKey, resourceId)");
			sb.append(" values (?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, increment());
			ps.setString(2, String.valueOf(primKey));
			ps.setLong(3, resourceId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return resourceId;
	}

	protected void addResourcePermission(
			long companyId, long userId, long layoutSetPrototypeId)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("insert into ResourcePermission (companyId, name,");
			sb.append("scope, primKey, roleId, ownerId, actionIds, ");
			sb.append("resourcePermissionId) values (?,?,?,?,?,?,?,?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, companyId);
			ps.setString(2, LAYOUTSETPROTOTYPE_CLASSNAME);
			ps.setLong(3, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setString(4, String.valueOf(layoutSetPrototypeId));
			ps.setLong(5, getOwnerRole(companyId, RoleConstants.OWNER));
			ps.setLong(6, userId);
			ps.setLong(
				7, getResourceActionIds(LAYOUTSETPROTOTYPE_CLASSNAME));
			ps.setLong(8, increment());

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addPermissionsForLayoutSetPrototype(
			long companyId, long userId, long layoutSetPrototypeId)
		throws SQLException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			addResourcePermission(companyId, userId, layoutSetPrototypeId);

		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			addResource(companyId);

			long layoutSetPrototypeResourceId =
				addResource(layoutSetPrototypeId);

			addPermission(
				companyId, ActionKeys.DELETE, layoutSetPrototypeResourceId);
			addPermission(
				companyId, ActionKeys.PERMISSIONS,
				layoutSetPrototypeResourceId);
			addPermission(
				companyId, ActionKeys.UPDATE, layoutSetPrototypeResourceId);
			addPermission(
				companyId, ActionKeys.VIEW, layoutSetPrototypeResourceId);
		}

	}

	protected String composeI18N(String str) {
		StringBundler sb = new StringBundler(8);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<root default-locale=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\"><Name language-id=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\">");
		sb.append(str);
		sb.append("</Name></root>");

		return sb.toString();
	}

	protected UserGroupTemplateInfo createUserGroupsTemplatesInfo(ResultSet rs)
		throws SQLException {

		UserGroupTemplateInfo userGroupTemplateInfo =
			new UserGroupTemplateInfo();

		userGroupTemplateInfo.setCompanyId(rs.getLong("companyId"));
		userGroupTemplateInfo.setUserGroupId(rs.getLong("userGroupId"));
		userGroupTemplateInfo.setGroupId(rs.getLong("groupId"));
		userGroupTemplateInfo.setLayoutSetId(rs.getLong("layoutSetId"));
		userGroupTemplateInfo.setPrivateLayout(rs.getBoolean("privateLayout"));
		userGroupTemplateInfo.setLogo(rs.getShort("logo"));
		userGroupTemplateInfo.setLogoId(rs.getLong("logoId"));
		userGroupTemplateInfo.setThemeId(rs.getString("themeId"));
		userGroupTemplateInfo.setColorSchemeId(rs.getString("colorSchemeId"));
		userGroupTemplateInfo.setWapThemeId(rs.getString("wapThemeId"));
		userGroupTemplateInfo.setWapColorSchemeId(
			rs.getString("wapColorSchemeId"));
		userGroupTemplateInfo.setCss(rs.getString("css"));
		userGroupTemplateInfo.setPageCount(rs.getLong("pageCount"));
		userGroupTemplateInfo.setSettings(rs.getString("settings"));
		userGroupTemplateInfo.setLayoutSetPrototypeId(
			rs.getLong("layoutSetPrototypeId"));

		return userGroupTemplateInfo;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateUserGroupTemplates();

	}

	protected long findGroupByC_C_C(
			long companyId, long classNameId, long classPK)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("select groupId as groupId from Group_ where ");
			sb.append("companyId = ? AND classNameId = ?  AND classPK = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

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

	protected long getClassNameId(String className) {

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
		catch (SQLException e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected long getOwnerRole(long companyId, String roleName)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("select roleId from Role_ where companyId = ? AND ");
			sb.append("name = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, companyId);
			ps.setString(2, roleName);

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

	protected long getResourceActionIds(String name)
		throws SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("select sum(bitwiseValue) as actionIds from ");
			sb.append("ResourceAction where name = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

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

	protected void updateLayouts(
			long oldGroupId, long newGroupId, boolean privateLayout)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("update Layout set groupId = ? , privateLayout = TRUE");
			sb.append(" where groupId = ? AND privateLayout = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, newGroupId);
			ps.setLong(2, oldGroupId);
			ps.setBoolean(3, privateLayout);

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayoutSetPrototypeId(
			long companyId, long userGroupId, long layoutSetPrototypeId,
			String layoutSetPrototypeIdColumnName)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("update UserGroup set ");
			sb.append(layoutSetPrototypeIdColumnName);
			sb.append("= ? where companyId = ? AND userGroupId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, layoutSetPrototypeId);
			ps.setLong(2, companyId);
			ps.setLong(3, userGroupId);

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateUserGroupTemplates() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(14);

			sb.append("select ug.companyId as companyId,ug.userGroupId ");
			sb.append("as userGroupId,ug.name as userGroupName,");
			sb.append("g.groupId as groupId,g.creatorUserId as creatorUserId,");
			sb.append("ls.layoutSetId as layoutSetId,");
			sb.append("ls.privateLayout as privateLayout, ls.logo as logo,");
			sb.append("ls.logoId as logoId,ls.themeId as themeId,");
			sb.append("ls.colorSchemeId as colorSchemeId,");
			sb.append("ls.wapThemeId as wapThemeId,ls.wapColorSchemeId ");
			sb.append("as wapColorSchemeId ,ls.css as css,");
			sb.append("ls.pageCount as pageCount,ls.settings_ as settings,");
			sb.append("ls.layoutSetPrototypeId as layoutSetPrototypeId ");
			sb.append("from UserGroup ug  inner join Group_ g on ");
			sb.append("ug.userGroupId=g.classPK inner join LayoutSet ls on ");
			sb.append("(g.groupId=ls.groupId AND ls.pageCount > 0)");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String userGroupName = rs.getString("userGroupName");
				long groupId = rs.getLong("groupId");
				long creatorUserId = rs.getLong("creatorUserId");
				long userGroupId = rs.getLong("userGroupId");
				boolean privateLayout = rs.getBoolean("privateLayout");

				// Add a new LayoutSetPrototype and Group for each user group

				long layoutSetPrototypeId = addLayoutSetPrototype(
					companyId, userGroupName);

				long layoutSetPrototypeGroupId = addLayoutSetPrototypeGroup(
					companyId, layoutSetPrototypeId, creatorUserId,
					userGroupName);

				addPermissionsForLayoutSetPrototype(
					companyId, creatorUserId, layoutSetPrototypeId);

				// Create private LayoutSet and move the pages to it

				UserGroupTemplateInfo userGroupTemplateInfo =
					createUserGroupsTemplatesInfo(rs);

				boolean originalPrivateLayout =
					userGroupTemplateInfo.isPrivateLayout();

				userGroupTemplateInfo.setPrivateLayout(true);

				addLayoutSet(
					companyId, layoutSetPrototypeGroupId, layoutSetPrototypeId,
					userGroupTemplateInfo);

				updateLayouts(
					groupId, layoutSetPrototypeGroupId, originalPrivateLayout);

				// Create public LayoutSet

				UserGroupTemplateInfo publicLayoutSet =
					new UserGroupTemplateInfo();

				publicLayoutSet.setPrivateLayout(false);

				addLayoutSet(
					companyId, layoutSetPrototypeGroupId, layoutSetPrototypeId,
					publicLayoutSet);

				// Store the layoutSetPrototype id in the UserGroup

				String layoutSetPrototypeIdColumnName =
					privateLayout ? "privateLayoutSetPrototypeId" :
						"publicLayoutSetPrototypeId";

				updateLayoutSetPrototypeId(
					companyId, userGroupId, layoutSetPrototypeId,
					layoutSetPrototypeIdColumnName);

			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected static final String LAYOUTSETPROTOTYPE_CLASSNAME =
		LayoutSetPrototype.class.getName();

	protected final long layoutSetPrototypeClassNameId =
		getClassNameId(LAYOUTSETPROTOTYPE_CLASSNAME);

}