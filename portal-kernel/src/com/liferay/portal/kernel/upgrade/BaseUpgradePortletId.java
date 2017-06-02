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

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
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
import java.util.Set;

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
		String typeSettings, String oldRootPortletId, String newRootPortletId) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String oldStagingPortletId = StagingUtil.getStagedPortletId(
			oldRootPortletId);

		if (!typeSettingsProperties.containsKey(oldStagingPortletId)) {
			return typeSettings;
		}

		String newStagingPortletId = StagingUtil.getStagedPortletId(
			newRootPortletId);

		String value = typeSettingsProperties.remove(oldStagingPortletId);

		typeSettingsProperties.setProperty(newStagingPortletId, value);

		return typeSettingsProperties.toString();
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId,
		boolean exactMatch) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		List<String> columnIds = _getLayoutColumnIds(typeSettingsProperties);

		columnIds.addAll(_getNestedPortletColumnIds(typeSettingsProperties));

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

			typeSettingsProperties.setProperty(
				columnId,
				StringUtil.merge(portletIds).concat(StringPool.COMMA));
		}

		return typeSettingsProperties.toString();
	}

	/**
	 * @deprecated As of 7.0.0
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
		StringBundler sb = new StringBundler(23);

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
		sb.append("_USER_%' OR typeSettings like '%");
		sb.append(StagingUtil.getStagedPortletId(portletId));
		sb.append("=%'");

		return sb.toString();
	}

	protected String[] getUninstanceablePortletIds() {
		return new String[0];
	}

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

		String sql =
			"select groupId, typeSettings from Group_ where " +
				getTypeSettingsCriteria(oldRootPortletId);

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId);

				updateGroup(groupId, newTypeSettings);
			}
		}
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

				String newPortletId = StringUtil.replaceFirst(
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

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId, newRootPortletId,
					exactMatch);

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
				"update ResourceAction set name = '" + newName +
					"' where name = '" + oldName + "'");
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

		StringBundler sb = new StringBundler(5);

		sb.append("select distinct primKey from ResourcePermission where ");
		sb.append("name = '");
		sb.append(oldRootPortletId);
		sb.append("' and scope = ");
		sb.append(ResourceConstants.SCOPE_INDIVIDUAL);

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ResourcePermission set primKey = ? where primKey " +
						"= ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String oldPrimKey = rs.getString("primKey");

				int pos = oldPrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

				if (pos != -1) {
					long plid = GetterUtil.getLong(
						oldPrimKey.substring(0, pos));

					String portletId = oldPrimKey.substring(
						pos + PortletConstants.LAYOUT_SEPARATOR.length());

					String instanceId = PortletIdCodec.decodeInstanceId(
						portletId);
					long userId = PortletIdCodec.decodeUserId(portletId);

					String newPortletId = PortletIdCodec.encode(
						newRootPortletId, userId, instanceId);

					String newPrimKey = PortletPermissionUtil.getPrimaryKey(
						plid, newPortletId);

					ps2.setString(1, newPrimKey);

					ps2.setString(2, oldPrimKey);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}

		if (updateName) {
			runSQL(
				"update ResourcePermission set primKey = '" + newRootPortletId +
					"' where primKey = '" + oldRootPortletId + "' and name = " +
						"'" + oldRootPortletId + "'");

			runSQL(
				"update ResourcePermission set name = '" + newRootPortletId +
					"' where name = '" + oldRootPortletId + "'");
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

				updateGroup(portletId, newPortletInstanceKey);
				updateInstanceablePortletPreferences(
					portletId, newPortletInstanceKey);
				updateResourcePermission(
					portletId, newPortletInstanceKey, false);
				updateLayoutRevisions(portletId, newPortletInstanceKey, true);
				updateLayouts(portletId, newPortletInstanceKey, true);
			}
		}
	}

	private List<String> _getLayoutColumnIds(
		UnicodeProperties typeSettingsProperties) {

		List<String> columnIds = new ArrayList<>();

		Set<String> keys = typeSettingsProperties.keySet();

		for (String key : keys) {
			if (StringUtil.startsWith(
					key, LayoutTypePortletConstants.COLUMN_PREFIX)) {

				columnIds.add(key);
			}
		}

		return columnIds;
	}

	private List<String> _getNestedPortletColumnIds(
		UnicodeProperties typeSettingsProperties) {

		if (!typeSettingsProperties.containsKey("nested-column-ids")) {
			return Collections.emptyList();
		}

		String[] nestedPortletColumnIds = StringUtil.split(
			typeSettingsProperties.getProperty("nested-column-ids"));

		return ListUtil.fromArray(nestedPortletColumnIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradePortletId.class);

}