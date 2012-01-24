/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UpgradeLayoutSetBranch extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select LayoutSetBranch.LayoutSetBranchId, ");
			sb.append("LayoutSetBranch.groupId, LayoutSetBranch.privateLayout");
			sb.append(" from LayoutSetBranch");
			
			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("LayoutSetBranch.groupId");
				long layoutSetBranchId = rs.getLong(
					"LayoutSetBranch.LayoutSetBranchId");
				boolean privateLayout = rs.getBoolean(
					"LayoutSetBranch.privateLayout");

				upgradeLayoutSetBranch(
					groupId, privateLayout, layoutSetBranchId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeLayoutSetBranch(
			long groupId, boolean privateLayout, long layoutSetBranchId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select LayoutSet.layoutSetId, ");
			sb.append("LayoutSet.themeId, LayoutSet.colorSchemeId, ");
			sb.append("LayoutSet.wapThemeId, LayoutSet.wapColorSchemeId, ");
			sb.append("LayoutSet.css, LayoutSet.settings_, ");
			sb.append("LayoutSet.layoutSetPrototypeUuid, ");
			sb.append("LayoutSet.layoutSetPrototypeLinkEnabled ");
			sb.append("from LayoutSet where groupId = ? and privateLayout = ?");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("LayoutSet.layoutSetId");
				String themeId = rs.getString("LayoutSet.themeId");
				String colorSchemeId = rs.getString("LayoutSet.colorSchemeId");
				String wapThemeId = rs.getString("LayoutSet.wapThemeId");
				String wapColorSchemeId = rs.getString(
					"LayoutSet.wapColorSchemeId");
				String css = rs.getString("LayoutSet.css");
				String settings = rs.getString("LayoutSet.settings_");
				String layoutSetPrototypeUuid = rs.getString(
					"LayoutSet.layoutSetPrototypeUuid");
				boolean layoutSetPrototypeLinkEnabled = rs.getBoolean(
					"LayoutSet.layoutSetPrototypeLinkEnabled");

				updateLayoutSetBranch(
					layoutSetBranchId, themeId, colorSchemeId, wapThemeId,
					wapColorSchemeId, css, settings, layoutSetPrototypeUuid,
					layoutSetPrototypeLinkEnabled);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}
	
	protected void updateLayoutSetBranch(
			long layoutSetBranchId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			String settings, String layoutSetPrototypeUuid,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("update LayoutSetBranch set themeId = ?, colorSchemeId = ?, ");
			sb.append("wapThemeId = ?, wapColorSchemeId = ?, css = ?, ");
			sb.append("settings_ = ?, layoutSetPrototypeUuid = ?, ");
			sb.append("layoutSetPrototypeLinkEnabled = ? ");
			sb.append("where layoutSetBranchId = ?");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, themeId);
			ps.setString(2, colorSchemeId);
			ps.setString(3, wapThemeId);
			ps.setString(4, wapColorSchemeId);
			ps.setString(5, css);
			ps.setString(6, settings);
			ps.setString(7, layoutSetPrototypeUuid);
			ps.setBoolean(8, layoutSetPrototypeLinkEnabled);
			ps.setLong(9, layoutSetBranchId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}