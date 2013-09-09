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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CustomizedPages;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class UpgradeCustomizablePortlets extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select ownerId, ownerType, preferences from " +
					"PortalPreferences where preferences like " +
						"'%com.liferay.portal.model.CustomizedPages%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long ownerId = rs.getLong("ownerId");
				int ownerType = rs.getInt("ownerType");
				String preferences = rs.getString("preferences");

				PortalPreferencesWrapper portalPreferencesWrapper =
					getPortalPreferencesInstance(
						ownerId, ownerType, preferences);

				upgradeCustomizablePreferences(
					portalPreferencesWrapper, ownerId, ownerType, preferences);

				portalPreferencesWrapper.store();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected PortalPreferencesWrapper getPortalPreferencesInstance(
			long ownerId, int ownerType, String xml)
		throws SystemException {

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				ownerId, ownerType, xml);

		return new PortalPreferencesWrapper(portalPreferencesImpl);
	}

	protected PortletPreferences getPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, ownerId, ownerType, plid, " +
					"portletId, preferences from PortletPreferences where " +
						"ownerId = ?, ownerType = ?, plid = ?, portletId = ?");

			ps.setLong(1, ownerId);
			ps.setInt(2, ownerType);
			ps.setLong(3, plid);
			ps.setString(4, portletId);

			rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}

			PortletPreferences portletPreferences =
				new PortletPreferencesImpl();

			portletPreferences.setPortletPreferencesId(
				rs.getLong("portletPreferencesId"));
			portletPreferences.setOwnerId(rs.getLong("ownerId"));
			portletPreferences.setOwnerType(rs.getInt("ownerType"));
			portletPreferences.setPlid(rs.getLong("plid"));
			portletPreferences.setPortletId(rs.getString("portletId"));
			portletPreferences.setPreferences(rs.getString("preferences"));

			return portletPreferences;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String migratePortletPreferencesToUserPreferences(
			long userId, long plid, String portletId)
		throws Exception {

		if (!PortletConstants.hasInstanceId(portletId)) {
			return portletId;
		}

		String instanceId = PortletConstants.getInstanceId(portletId);

		String newPortletId = PortletConstants.assemblePortletId(
			portletId, userId, instanceId);

		updatePortletPreferences(userId, plid, portletId, newPortletId);

		return newPortletId;
	}

	protected void updatePortletPreferences(
			long userId, long plid, String portletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set ownerId = ?, ownerType = ?, " +
					"plid = ?, portletId = ? where ownerId = ? and " +
						"ownerType = ? and portletId = ?");

			ps.setLong(1, userId);
			ps.setInt(2, PortletKeys.PREFS_OWNER_TYPE_USER);
			ps.setLong(3, plid);
			ps.setString(4, newPortletId);
			ps.setLong(5, 0L);
			ps.setInt(6, PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
			ps.setString(7, portletId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeCustomizablePreferences(
			PortalPreferencesWrapper portalPreferencesWrapper, long ownerId,
			int ownerType, String preferences)
		throws Exception {

		PortalPreferencesImpl portalPreferencesImpl =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		int x = preferences.indexOf(_PREFIX);
		int y = -1;

		if (x != -1) {
			x += _PREFIX.length();
			y = preferences.indexOf(_SUFFIX, x);
		}
		else {
			return;
		}

		while (x != -1) {

			// <name>com.liferay.portal.model.CustomizedPages10415#column-1
			// </name>

			String[] parts = StringUtil.split(
				preferences.substring(x, y), StringPool.POUND);

			long plid = GetterUtil.getLong(parts[0]);
			String key = GetterUtil.getString(parts[1]);

			if (key.startsWith(LayoutTypePortletConstants.COLUMN_PREFIX)) {
				String value = portalPreferencesImpl.getValue(
					CustomizedPages.namespacePlid(plid), key);

				List<String> newPortletIds = new ArrayList<String>();

				for (String customPortletId : StringUtil.split(value)) {
					String newPortletId =
						migratePortletPreferencesToUserPreferences(
							ownerId, plid, customPortletId);

					newPortletIds.add(newPortletId);
				}

				value = StringUtil.merge(newPortletIds);

				portalPreferencesImpl.setValue(
					CustomizedPages.namespacePlid(plid), key, value);
			}

			x = preferences.indexOf(_PREFIX, y);
			y = -1;

			if (x != -1) {
				x += _PREFIX.length();
				y = preferences.indexOf(_SUFFIX, x);
			}
		}
	}

	private static final String _PREFIX =
		"<name>com.liferay.portal.model.CustomizedPages";

	private static final String _SUFFIX = "</name>";

}