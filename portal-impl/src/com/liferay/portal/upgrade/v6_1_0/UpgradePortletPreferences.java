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
import com.liferay.portal.kernel.upgrade.CamelCaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Julio Camarero
 * @author Douglas Wong
 */
public class UpgradePortletPreferences
	extends CamelCaseUpgradePortletPreferences {

	protected void addPortalPreferences(
			long ownerId, int ownerType, String preferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into PortalPreferences (portalPreferencesId, " +
					"ownerId, ownerType, preferences) values (?, ?, ?, ?)");

			ps.setLong(1, increment());
			ps.setLong(2, ownerId);
			ps.setInt(3, ownerType);
			ps.setString(4, preferences);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId,
			String preferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into PortletPreferences (portletPreferencesId, " +
					"ownerId, ownerType, plid, portletId, preferences) " +
						"values (?, ?, ?, ?, ?, ?)");

			ps.setLong(1, increment());
			ps.setLong(2, ownerId);
			ps.setInt(3, ownerType);
			ps.setLong(4, plid);
			ps.setString(5, portletId);
			ps.setString(6, preferences);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updatePortalPreferences();
		updatePortletPreferencesOwner();

		upgrade(UpgradeCommunityProperties.class);

		runSQL(
			"create index IX_D1F795F1 on PortalPreferences (ownerId, " +
				"ownerType)");
	}

	protected long getOwnerId(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Layout where plid = " + plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	@Override
	protected String[] getPortletIds() {
		return _CAMEL_CASE_UPGRADE_PORTLET_IDS;
	}

	protected long getPortletPreferencesId(
			long ownerId, int ownerType, long plid, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId from PortletPreferences where " +
					"ownerId = ? and ownerType = ? and plid = ? and " +
						"portletId = ?");

			ps.setLong(1, ownerId);
			ps.setInt(2, ownerType);
			ps.setLong(3, plid);
			ps.setString(4, portletId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("portletPreferencesId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected void updatePortalPreferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select ownerId, ownerType, preferences from " +
					"PortletPreferences where portletId = ?");

			ps.setString(1, PortletKeys.LIFERAY_PORTAL);

			rs = ps.executeQuery();

			while (rs.next()) {
				long ownerId = rs.getLong("ownerId");
				int ownerType = rs.getInt("ownerType");
				String preferences = rs.getString("preferences");

				addPortalPreferences(ownerId, ownerType, preferences);
			}

			runSQL(
				"delete from PortletPreferences where portletId = '" +
					PortletKeys.LIFERAY_PORTAL + "'");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortletPreferencesOwner() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(8);

			sb.append("select portletPreferencesId, plid, portletId, ");
			sb.append("preferences from PortletPreferences where ownerId = ");
			sb.append(PortletKeys.PREFS_OWNER_ID_DEFAULT);
			sb.append(" and ownerType = ");
			sb.append(PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
			sb.append(" and portletId in ('8', '19', '33')");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				long ownerId = getOwnerId(plid);

				if (ownerId == 0) {
					continue;
				}

				long portletPreferencesId = getPortletPreferencesId(
					ownerId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
					PortletKeys.PREFS_PLID_SHARED, portletId);

				if (portletPreferencesId != 0) {
					continue;
				}

				addPortletPreferences(
					ownerId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
					PortletKeys.PREFS_PLID_SHARED, portletId, preferences);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[] _CAMEL_CASE_UPGRADE_PORTLET_IDS = {
		"15", "19", "20", "33", "34", "36", "39_INSTANCE_%", "47_INSTANCE_%",
		"48_INSTANCE_%", "54_INSTANCE_%", "56_INSTANCE_%", "59_INSTANCE_%",
		"62_INSTANCE_%", "71_INSTANCE_%", "73_INSTANCE_%", "77",
		"82_INSTANCE_%", "85_INSTANCE_%", "100", "101_INSTANCE_%",
		"102_INSTANCE_%", "114", "115", "118_INSTANCE_%", "122_INSTANCE_%"
	};

}