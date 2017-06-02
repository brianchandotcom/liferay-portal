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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portal.kernel.upgrade.BaseUpgradePortletId}
 */
@Deprecated
public class UpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeInstanceablePortletIds();
		upgradeUninstanceablePortletIds();
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		boolean exactMatch) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.portal.kernel.upgrade.BaseUpgradePortletId");
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		List<String> columnIds, boolean exactMatch) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		for (String columnId : columnIds) {
			if (!typeSettingsProperties.containsKey(columnId)) {
				continue;
			}

			String[] portletIds = StringUtil.split(
				typeSettingsProperties.getProperty(columnId));

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
				columnId,
				StringUtil.merge(portletIds).concat(StringPool.COMMA));
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

	protected String getTypeSettingsCriteria(String portletId) {
		StringBundler sb = new StringBundler(21);

		sb.append("typeSettings like '%=");
		sb.append(portletId);
		sb.append(",%' OR typeSettings like '%=");
		sb.append(portletId);
		sb.append("\n%' OR typeSettings like '%=");
		sb.append(portletId);
		sb.append("%' OR typeSettings like '%,");
		sb.append(portletId);
		sb.append(",%' OR typeSettings like '%,");
		sb.append(portletId);
		sb.append("\n%' OR typeSettings like '%,");
		sb.append(portletId);
		sb.append("%' OR typeSettings like '%=");
		sb.append(portletId);
		sb.append("_INSTANCE_%' OR typeSettings like '%,");
		sb.append(portletId);
		sb.append("_INSTANCE_%' OR typeSettings like '%=");
		sb.append(portletId);
		sb.append("_USER_%' OR typeSettings like '%,");
		sb.append(portletId);
		sb.append("_USER_%'");

		return sb.toString();
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

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferences set portletId = ? where " +
						"portletPreferencesId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				String portletId = rs.getString("portletId");

				String newPortletId = StringUtil.replace(
					portletId, oldRootPortletId, newRootPortletId);

				ps2.setString(1, newPortletId);

				ps2.setLong(2, portletPreferencesId);

				ps2.addBatch();
			}

			ps2.executeBatch();
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

	protected void updateLayoutRevision(
			long layoutRevisionId, String typeSettings)
		throws Exception {

		String sql =
			"update LayoutRevision set typeSettings = ? where " +
				"layoutRevisionId = " + layoutRevisionId;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
	}

	protected void updateLayoutRevisions(
			String oldRootPortletId, String newRootPortletId,
			boolean exactMatch)
		throws Exception {

		String sql =
			"select layoutRevisionId, typeSettings from LayoutRevision where " +
				getTypeSettingsCriteria(oldRootPortletId);

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long layoutRevisionId = rs.getLong("layoutRevisionId");
				String typeSettings = rs.getString("typeSettings");

				List<String> layoutColumnIds = _getLayoutColumnIds();

				layoutColumnIds.addAll(
					_getNestedPortletColumnIds(typeSettings));

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					layoutColumnIds, exactMatch);

				updateLayoutRevision(layoutRevisionId, newTypeSettings);
			}
		}
	}

	protected void updateLayouts(
			String oldRootPortletId, String newRootPortletId,
			boolean exactMatch)
		throws Exception {

		String sql =
			"select plid, typeSettings from Layout where " +
				getTypeSettingsCriteria(oldRootPortletId);

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				List<String> layoutColumnIds = _getLayoutColumnIds();

				layoutColumnIds.addAll(
					_getNestedPortletColumnIds(typeSettings));

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					layoutColumnIds, exactMatch);

				updateLayout(plid, newTypeSettings);
			}
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			updatePortletId(oldRootPortletId, newRootPortletId);

			updateResourceAction(oldRootPortletId, newRootPortletId);

			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateUserNotificationDelivery(oldRootPortletId, newRootPortletId);

			updateUserNotificationEvent(oldRootPortletId, newRootPortletId);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void updatePortletId(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update Portlet set portletId = '" + newRootPortletId +
				"' where portletId = '" + oldRootPortletId + "'");
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

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select resourcePermissionId, name, scope, primKey from " +
					"ResourcePermission where name = '" + oldRootPortletId +
						"'");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ResourcePermission set name = ?, primKey = ? " +
						"where resourcePermissionId = ?");
			ResultSet rs = ps1.executeQuery()) {

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

	protected void updateUserNotificationDelivery(
			String oldPortletId, String newPortletId)
		throws Exception {

		runSQL(
			"update UserNotificationDelivery set portletId = '" + newPortletId +
				"' where portletId = '" + oldPortletId + "'");
	}

	protected void updateUserNotificationEvent(
			String oldPortletId, String newPortletId)
		throws Exception {

		runSQL(
			"update UserNotificationEvent set type_ = '" + newPortletId +
				"' where type_ = '" + oldPortletId + "'");
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
				updateLayoutRevisions(
					oldRootPortletId, newRootPortletId, false);
				updateLayouts(oldRootPortletId, newRootPortletId, false);
			}
		}
	}

	protected void upgradeUninstanceablePortletIds() throws Exception {

		// Rename uninstanceable portlet IDs to instanceable portlet IDs

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[] uninstanceablePortletIds = getUninstanceablePortletIds();

			for (String portletId : uninstanceablePortletIds) {
				if (PortletIdCodec.hasInstanceId(portletId)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Portlet " + portletId +
								" is already instanceable");
					}

					continue;
				}

				PortletIdCodec.validatePortletName(portletId);

				String newPortletInstanceKey = PortletIdCodec.encode(portletId);

				updateInstanceablePortletPreferences(
					portletId, newPortletInstanceKey);
				updateResourcePermission(
					portletId, newPortletInstanceKey, false);
				updateLayoutRevisions(portletId, newPortletInstanceKey, true);
				updateLayouts(portletId, newPortletInstanceKey, true);
			}
		}
	}

	private List<String> _getLayoutColumnIds() {
		List<String> columnIds = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			columnIds.add(LayoutTypePortletConstants.COLUMN_PREFIX + i);
		}

		return columnIds;
	}

	private List<String> _getNestedPortletColumnIds(String typeSettings) {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		if (!typeSettingsProperties.containsKey("nested-column-ids")) {
			return Collections.emptyList();
		}

		String[] nestedPortletColumnIds = StringUtil.split(
			typeSettingsProperties.getProperty("nested-column-ids"));

		return ListUtil.fromArray(nestedPortletColumnIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}