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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.layouts.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class UpgradeCustomizablePortlets extends UpgradeProcess {

	public static String namespacePlid(long plid) {
		return "com.liferay.portal.model.CustomizedPages".concat(
			String.valueOf(plid));
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeCustomizablePreferences();
	}

	protected PortalPreferencesWrapper getPortalPreferencesInstance(
		long ownerId, int ownerType, String xml) {

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				ownerId, ownerType, xml);

		return new PortalPreferencesWrapper(portalPreferencesImpl);
	}

	protected PortletPreferences getPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("select portletPreferencesId, ownerId, ownerType, plid, ");
		sb.append("portletId, preferences from PortletPreferences where ");
		sb.append("ownerId = ?, ownerType = ?, plid = ?, portletId = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, ownerId);
			ps.setInt(2, ownerType);
			ps.setLong(3, plid);
			ps.setString(4, portletId);

			try (ResultSet rs = ps.executeQuery()) {
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
		}
	}

	protected void upgradeCustomizablePreferences() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select ownerId, ownerType, preferences from " +
					"PortalPreferences where preferences like " +
						"'%com.liferay.portal.model.CustomizedPages%'");
			ResultSet rs = ps.executeQuery()) {

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

			if (LayoutTypePortletConstants.isLayoutTemplateColumnName(key)) {
				String value = portalPreferencesImpl.getValue(
					namespacePlid(plid), key);

				List<String> newPortletIds = new ArrayList<>();

				StringBundler sb = new StringBundler(4);

				sb.append("update PortletPreferences set ownerId = ?, ");
				sb.append("ownerType = ?, plid = ?, portletId = ? where ");
				sb.append("ownerId = ? and ownerType = ? and plid = ? and ");
				sb.append("portletId = ?");

				try (PreparedStatement ps = connection.prepareStatement(
						sb.toString())) {

					for (String customPortletId : StringUtil.split(value)) {
						String newPortletId = null;

						if (!PortletIdCodec.hasInstanceId(customPortletId)) {
							newPortletIds.add(customPortletId);
						}
						else {
							String instanceId = PortletIdCodec.decodeInstanceId(
								customPortletId);

							newPortletId = PortletIdCodec.encode(
								PortletIdCodec.decodePortletName(
									customPortletId),
								ownerId, instanceId);

							ps.setLong(1, ownerId);
							ps.setInt(2, PortletKeys.PREFS_OWNER_TYPE_USER);
							ps.setLong(3, plid);
							ps.setString(4, newPortletId);
							ps.setLong(5, 0L);
							ps.setInt(6, PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
							ps.setLong(7, plid);
							ps.setString(8, newPortletId);

							newPortletIds.add(newPortletId);

							ps.addBatch();
						}
					}

					ps.executeBatch();
				}

				value = StringUtil.merge(newPortletIds);

				portalPreferencesImpl.setValue(namespacePlid(plid), key, value);
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