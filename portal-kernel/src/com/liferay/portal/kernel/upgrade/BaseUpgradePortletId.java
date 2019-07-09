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

package com.liferay.portal.kernel.upgrade;

import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeInstanceablePortletIds();
		upgradeUninstanceablePortletIds();
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldPropertyId, String newPropertyId) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String value = typeSettingsProperties.remove(oldPropertyId);

		if (value != null) {
			typeSettingsProperties.setProperty(newPropertyId, value);
		}

		return typeSettingsProperties.toString();
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		boolean exactMatch) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		for (Map.Entry<String, String> entry :
				typeSettingsProperties.entrySet()) {

			String typeSettingId = entry.getKey();

			if (!LayoutTypePortletConstants.hasPortletIds(typeSettingId)) {
				continue;
			}

			String[] portletIds = StringUtil.split(entry.getValue());

			for (int j = 0; j < portletIds.length; j++) {
				String portletId = portletIds[j];

				if (exactMatch) {
					if (portletId.equals(oldRootPortletId)) {
						portletIds[j] = newRootPortletId;
					}

					continue;
				}

				String rootPortletId = PortletIdCodec.decodePortletName(
					portletId);

				if (!rootPortletId.equals(oldRootPortletId)) {
					continue;
				}

				long userId = PortletIdCodec.decodeUserId(portletId);
				String instanceId = PortletIdCodec.decodeInstanceId(portletId);

				portletIds[j] = PortletIdCodec.encode(
					newRootPortletId, userId, instanceId);
			}

			String portletIdsString = StringUtil.merge(portletIds);

			typeSettingsProperties.setProperty(typeSettingId, portletIdsString);
		}

		return typeSettingsProperties.toString();
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		List<String> columnIds, boolean exactMatch) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		return getNewTypeSettings(
			typeSettings, oldRootPortletId, newRootPortletId, exactMatch);
	}

	protected String[][] getRenamePortletIdsArray() {
		return new String[0][0];
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

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected void updateGroup(long groupId, String typeSettings)
		throws Exception {

		String sql =
			"update Group_ set typeSettings = ? where groupId = " + groupId;

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

	protected void updateGroup(String oldRootPortletId, String newRootPortletId)
		throws Exception {

		String oldStagedPortletId = _getStagedPortletId(oldRootPortletId);

		String sql1 = StringBundler.concat(
			"select groupId, typeSettings from Group_ where typeSettings like ",
			"'%", oldStagedPortletId, "%'");

		String sql2 = "update Group_ set typeSettings = ? where groupId = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sql2);
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldStagedPortletId,
					_getStagedPortletId(newRootPortletId));

				ps2.setString(1, newTypeSettings);

				ps2.setLong(2, groupId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update PortletPreferences set portletId = '", newRootPortletId,
				"' where portletId = '", oldRootPortletId, "'"));

		if (!newRootPortletId.contains("_INSTANCE_")) {
			runSQL(
				StringBundler.concat(
					"update PortletPreferences set portletId = replace(",
					"portletId, '", oldRootPortletId, "_INSTANCE_', '",
					newRootPortletId, "_INSTANCE_') where portletId like '",
					oldRootPortletId, "_INSTANCE_%'"));
		}

		runSQL(
			StringBundler.concat(
				"update PortletPreferences set portletId = replace(portletId, ",
				"'", oldRootPortletId, "_USER_', '", newRootPortletId,
				"_USER_') where portletId like '", oldRootPortletId,
				"_USER_%'"));
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

		String sql1 =
			"select layoutRevisionId, typeSettings from LayoutRevision where " +
				getTypeSettingsCriteria(oldRootPortletId);
		String sql2 =
			"update LayoutRevision set typeSettings = ? where " +
				"layoutRevisionId = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sql2);
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long layoutRevisionId = rs.getLong("layoutRevisionId");

				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					exactMatch);

				ps2.setString(1, newTypeSettings);

				ps2.setLong(2, layoutRevisionId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void updateLayouts(
			String oldRootPortletId, String newRootPortletId,
			boolean exactMatch)
		throws Exception {

		String sql1 =
			"select plid, typeSettings from Layout where " +
				getTypeSettingsCriteria(oldRootPortletId);
		String sql2 = "update Layout set typeSettings = ? where plid = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sql2);
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long plid = rs.getLong("plid");

				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					exactMatch);

				ps2.setString(1, newTypeSettings);

				ps2.setLong(2, plid);

				ps2.addBatch();
			}

			ps2.executeBatch();
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
			StringBundler.concat(
				"update Portlet set portletId = '", newRootPortletId,
				"' where portletId = '", oldRootPortletId, "'"));
	}

	protected void updateResourceAction(String oldName, String newName)
		throws Exception {

		List<String> actionIds = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select actionId from ResourceAction where name = '" + newName +
					"'");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				actionIds.add(rs.getString("actionId"));
			}
		}

		if (actionIds.isEmpty()) {
			runSQL(
				StringBundler.concat(
					"update ResourceAction set name = '", newName,
					"' where name = '", oldName, "'"));
		}
		else {
			StringBundler sb = new StringBundler(5 + 3 * actionIds.size());

			sb.append("update ResourceAction set name = '");
			sb.append(newName);
			sb.append("' where name = '");
			sb.append(oldName);
			sb.append("' and actionId not in (");

			for (int i = 0; i < actionIds.size(); i++) {
				sb.append("'");
				sb.append(actionIds.get(i));

				if (i == (actionIds.size() - 1)) {
					sb.append("')");
				}
				else {
					sb.append("', ");
				}
			}

			runSQL(sb.toString());

			runSQL("delete from ResourceAction where name = '" + oldName + "'");
		}
	}

	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update ResourcePermission set primKey = replace(primKey, ",
				"'_LAYOUT_", oldRootPortletId, "', '_LAYOUT_", newRootPortletId,
				"') where name = '", oldRootPortletId,
				"' and primKey like '%_LAYOUT_", oldRootPortletId, "'"));

		if (!newRootPortletId.contains("_INSTANCE_")) {
			runSQL(
				StringBundler.concat(
					"update ResourcePermission set primKey = replace(",
					"primKey, '_LAYOUT_", oldRootPortletId, "_INSTANCE_', ",
					"'_LAYOUT_", newRootPortletId, "_INSTANCE_') where name = ",
					"'", oldRootPortletId, "' and primKey like '%_LAYOUT_",
					oldRootPortletId, "_INSTANCE_%'"));
		}

		if (updateName) {
			runSQL(
				StringBundler.concat(
					"update ResourcePermission set primKey = '",
					newRootPortletId, "' where name = '", oldRootPortletId,
					"' and primKey = '", oldRootPortletId, "'"));

			runSQL(
				StringBundler.concat(
					"update ResourcePermission set name = '", newRootPortletId,
					"' where name = '", oldRootPortletId, "'"));
		}
	}

	protected void updateUserNotificationDelivery(
			String oldPortletId, String newPortletId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update UserNotificationDelivery set portletId = '",
				newPortletId, "' where portletId = '", oldPortletId, "'"));
	}

	protected void updateUserNotificationEvent(
			String oldPortletId, String newPortletId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update UserNotificationEvent set type_ = '", newPortletId,
				"' where type_ = '", oldPortletId, "'"));
	}

	protected void upgradeInstanceablePortletIds() throws Exception {

		// Rename instanceable portlet IDs. We expect the root form of the
		// portlet ID because we will rename all instances of the portlet ID.

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[][] renamePortletIdsArray = getRenamePortletIdsArray();

			for (String[] renamePortletIds : renamePortletIdsArray) {
				String oldRootPortletId = renamePortletIds[0];
				String newRootPortletId = renamePortletIds[1];

				updateGroup(oldRootPortletId, newRootPortletId);
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

	private String _getStagedPortletId(String portletId) {
		String key = portletId;

		if (key.startsWith(StagingConstants.STAGED_PORTLET)) {
			return key;
		}

		return StagingConstants.STAGED_PORTLET.concat(portletId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradePortletId.class);

}