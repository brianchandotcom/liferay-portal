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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeInstanceablePortletIds();
		upgradeUninstanceablePortletIds();
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		boolean exactMatch) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		for (int i = 1; i <= 10; i++) {
			String column = LayoutTypePortletConstants.COLUMN_PREFIX + i;

			if (!typeSettingsProperties.containsKey(column)) {
				continue;
			}

			String[] portletIds = StringUtil.split(
				typeSettingsProperties.getProperty(column));

			for (int j = 0; j < portletIds.length; j++) {
				String portletId = portletIds[j];

				if (exactMatch) {
					if (portletId.equals(oldRootPortletId)) {
						portletIds[j] = newRootPortletId;
					}

					continue;
				}

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				if (!rootPortletId.equals(oldRootPortletId)) {
					continue;
				}

				long userId = PortletConstants.getUserId(portletId);
				String instanceId = PortletConstants.getInstanceId(portletId);

				portletIds[j] = PortletConstants.assemblePortletId(
					newRootPortletId, userId, instanceId);
			}

			typeSettingsProperties.setProperty(
				column, StringUtil.merge(portletIds).concat(StringPool.COMMA));
		}

		return typeSettingsProperties.toString();
	}

	protected String[][] getRenamePortletIdsArray() {
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

	protected String[] getUninstanceablePortletIds() {
		return new String[0];
	}

	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append("select portletPreferencesId, portletId from ");
		sb.append("PortletPreferences where portletId = '");
		sb.append(oldRootPortletId);
		sb.append("' OR portletId like '");
		sb.append(oldRootPortletId);
		sb.append("_INSTANCE_%' OR portletId like '");
		sb.append(oldRootPortletId);
		sb.append("_USER_%_INSTANCE_%'");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				String portletId = rs.getString("portletId");

				String newPortletId = StringUtil.replace(
					portletId, oldRootPortletId, newRootPortletId);

				updatePortletPreference(portletPreferencesId, newPortletId);
			}
		}
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid)) {

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
	}

	protected void updateLayout(
			long plid, String oldPortletId, String newPortletId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select typeSettings from Layout where plid = " + plid);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void updateLayouts(
			String oldRootPortletId, String newRootPortletId,
			boolean exactMatch)
		throws Exception {

		StringBundler sb = new StringBundler(18);

		sb.append("select plid, typeSettings from Layout where ");
		sb.append("typeSettings like '%=");
		sb.append(oldRootPortletId);
		sb.append(",%' OR typeSettings like '%=");
		sb.append(oldRootPortletId);
		sb.append("\n%' OR typeSettings like '%,");
		sb.append(oldRootPortletId);
		sb.append(",%' OR typeSettings like '%,");
		sb.append(oldRootPortletId);
		sb.append("\n%' OR typeSettings like '%=");
		sb.append(oldRootPortletId);
		sb.append("_INSTANCE_%' OR typeSettings like '%,");
		sb.append(oldRootPortletId);
		sb.append("_INSTANCE_%' OR typeSettings like '%=");
		sb.append(oldRootPortletId);
		sb.append("_USER_%' OR typeSettings like '%,");
		sb.append(oldRootPortletId);
		sb.append("_USER_%'");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					exactMatch);

				updateLayout(plid, newTypeSettings);
			}
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			runSQL(
				"update Portlet set portletId = '" + newRootPortletId +
					"' where portletId = '" + oldRootPortletId + "'");

			updateResourceAction(oldRootPortletId, newRootPortletId);

			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void updatePortletPreference(
			long portletPreferencesId, String portletId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update PortletPreferences set portletId = ? where " +
					"portletPreferencesId = " + portletPreferencesId)) {

			ps.setString(1, portletId);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
	}

	protected void updateResourceAction(String oldName, String newName)
		throws Exception {

		runSQL(
			"update ResourceAction set name = '" + newName +
				"' where name = '" + oldName + "'");
	}

	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select resourcePermissionId, name, scope, primKey from " +
					"ResourcePermission where name = '" + oldRootPortletId +
						"'");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ResourcePermission set name = ?, primKey = ? " +
						"where resourcePermissionId = ?");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				String name = rs.getString("name");
				int scope = rs.getInt("scope");
				String primKey = rs.getString("primKey");

				String newName = name;

				if (updateName) {
					newName = newRootPortletId;
				}

				if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
					int pos = primKey.indexOf(
						PortletConstants.LAYOUT_SEPARATOR);

					if (pos != -1) {
						long plid = GetterUtil.getLong(
							primKey.substring(0, pos));

						String portletId = primKey.substring(
							pos + PortletConstants.LAYOUT_SEPARATOR.length());

						String instanceId = PortletConstants.getInstanceId(
							portletId);
						long userId = PortletConstants.getUserId(portletId);

						String newPortletId =
							PortletConstants.assemblePortletId(
								newRootPortletId, userId, instanceId);

						primKey = PortletPermissionUtil.getPrimaryKey(
							plid, newPortletId);
					}

					if (name.equals(primKey)) {
						primKey = newName;
					}
				}

				ps2.setString(1, newName);
				ps2.setString(2, primKey);
				ps2.setLong(3, resourcePermissionId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
	}

	protected void upgradeInstanceablePortletIds() throws Exception {

		// Rename instanceable portlet IDs. We expect the root form of the
		// portlet ID because we will rename all instances of the portlet ID.

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[][] renamePortletIdsArray = getRenamePortletIdsArray();

			for (String[] renamePortletIds : renamePortletIdsArray) {
				String oldRootPortletId = renamePortletIds[0];
				String newRootPortletId = renamePortletIds[1];

				updatePortlet(oldRootPortletId, newRootPortletId);
				updateLayouts(oldRootPortletId, newRootPortletId, false);
			}
		}
	}

	protected void upgradeUninstanceablePortletIds() throws Exception {

		// Rename uninstanceable portlet IDs to instanceable portlet IDs

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[] uninstanceablePortletIds = getUninstanceablePortletIds();

			for (String portletId : uninstanceablePortletIds) {
				PortletInstance portletInstance =
					PortletInstance.fromPortletInstanceKey(portletId);

				if (portletInstance.hasInstanceId()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Portlet " + portletId +
								" is already instanceable");
					}

					continue;
				}

				PortletInstance newPortletInstance = new PortletInstance(
					portletId);

				String newPortletInstanceKey =
					newPortletInstance.getPortletInstanceKey();

				updateResourcePermission(
					portletId, newPortletInstanceKey, false);
				updateInstanceablePortletPreferences(
					portletId, newPortletInstanceKey);
				updateLayouts(portletId, newPortletInstanceKey, true);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}