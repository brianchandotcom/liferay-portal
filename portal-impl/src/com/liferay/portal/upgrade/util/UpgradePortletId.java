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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// This is only tested to work on instanceable portlets

		String[][] portletIdsArray = getPortletIdsArray();

		for (String[] portletIds : portletIdsArray) {
			String oldRootPortletId = portletIds[0];
			String newRootPortletId = portletIds[1];

			updatePortlet(oldRootPortletId, newRootPortletId);
			updateLayouts(oldRootPortletId, newRootPortletId);
		}
	}

	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"109", "1_WAR_webformportlet"
			},
			new String[] {
				"google_adsense_portlet_WAR_googleadsenseportlet",
				"1_WAR_googleadsenseportlet"
			},
			new String[] {
				"google_gadget_portlet_WAR_googlegadgetportlet",
				"1_WAR_googlegadgetportlet"
			},
			new String[] {
				"google_maps_portlet_WAR_googlemapsportlet",
				"1_WAR_googlemapsportlet"
			}
		};
	}

	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, portletId from " +
					"PortletPreferences where portletId like '" +
						oldRootPortletId + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				String portletId = rs.getString("portletId");

				String newPortletId = StringUtil.replace(
					portletId, oldRootPortletId, newRootPortletId);

				updatePortletPreference(portletPreferencesId, newPortletId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateLayout(
			long plid, String oldPortletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select typeSettings from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayouts(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"like '%" + oldRootPortletId + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldRootPortletId, newRootPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update Portlet set portletId = '" + newRootPortletId +
				"' where portletId = '" + oldRootPortletId + "'");

		runSQL(
			"update ResourceAction set name = '" + newRootPortletId +
				"' where name = '" + oldRootPortletId + "'");

		updateResourcePermission(oldRootPortletId, newRootPortletId, true);

		updateInstanceablePortletPreferences(
			oldRootPortletId, newRootPortletId);
	}

	protected void updatePortletPreference(
			long portletPreferencesId, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set portletId = ? where " +
					"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, portletId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateResourcePermission(
			long resourcePermissionId, String name, String primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update ResourcePermission set name = ?, primKey = ? where " +
					"resourcePermissionId = " + resourcePermissionId);

			ps.setString(1, name);
			ps.setString(2, primKey);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourcePermissionId, name, primKey from " +
					"ResourcePermission where name = '" + oldRootPortletId +
						"'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				String name = rs.getString("name");
				String primKey = rs.getString("primKey");

				String newName = name;

				if (updateName) {
					newName = StringUtil.replace(
						name, oldRootPortletId, newRootPortletId);
				}

				String newPrimKey = StringUtil.replace(
					primKey, oldRootPortletId, newRootPortletId);

				updateResourcePermission(
					resourcePermissionId, newName, newPrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}