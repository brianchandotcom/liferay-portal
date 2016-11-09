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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.v7_0_0.util.PortletPreferencesRow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Set;

import javax.portlet.ReadOnlyException;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public abstract class UpgradePortletSettings extends UpgradeProcess {

	public UpgradePortletSettings() {
		_settingsFactory = SettingsFactoryUtil.getSettingsFactory();
	}

	public UpgradePortletSettings(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void addPortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws Exception {

		String sql =
			"insert into PortletPreferences (mvccVersion, " +
				"portletPreferencesId, ownerId, ownerType, plid, portletId, " +
					"preferences) values (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, portletPreferencesRow.getMvccVersion());
			ps.setLong(2, portletPreferencesRow.getPortletPreferencesId());
			ps.setLong(3, portletPreferencesRow.getOwnerId());
			ps.setInt(4, portletPreferencesRow.getOwnerType());
			ps.setLong(5, portletPreferencesRow.getPlid());
			ps.setString(6, portletPreferencesRow.getPortletId());
			ps.setString(7, portletPreferencesRow.getPreferences());

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to add portlet preferences " +
						portletPreferencesRow.getPortletPreferencesId(),
					sqle);
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void copyPortletSettingsAsServiceSettings(
			String portletId, int ownerType, String serviceName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Copy portlet settings as service settings");
		}

		String insertSQL =
			"insert into PortletPreferences (mvccVersion, " +
				"portletPreferencesId, ownerId, ownerType, plid, portletId, " +
					"preferences) values (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps1 = getPortletPreferencesPreparedStatement(
				portletId, ownerType);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertSQL)) {

			while (rs.next()) {
				long ownerId = rs.getLong("ownerId");
				long plid = rs.getLong("plid");

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
					ownerId = getGroupId(plid);

					plid = 0;

					_logCopyPortletSettings(
						portletId, plid, serviceName, ownerId);
				}

				ps2.setLong(1, 0);
				ps2.setLong(2, increment());
				ps2.setLong(3, ownerId);
				ps2.setInt(4, PortletKeys.PREFS_OWNER_TYPE_GROUP);
				ps2.setLong(5, plid);
				ps2.setString(6, serviceName);
				ps2.setString(7, rs.getString("preferences"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected long getGroupId(long plid) throws Exception {
		long groupId = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Layout where plid = ?")) {

			ps.setLong(1, plid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					groupId = rs.getLong("groupId");
				}
			}
		}

		return groupId;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected PreparedStatement getPortletPreferencesPreparedStatement(
			String portletId, int ownerType)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("select portletPreferencesId, ownerId, ownerType, plid, ");
		sb.append("portletId, preferences from PortletPreferences where ");
		sb.append("ownerType = ? and portletId = ? and preferences not like ");
		sb.append("'%<portlet-preferences %/>%'");

		PreparedStatement ps = connection.prepareStatement(sb.toString());

		ps.setInt(1, ownerType);
		ps.setString(2, portletId);

		return ps;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void resetPortletPreferencesValues(
			String portletId, int ownerType,
			SettingsDescriptor settingsDescriptor)
		throws Exception {

		try (PreparedStatement ps1 = getPortletPreferencesPreparedStatement(
				portletId, ownerType);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, _UPDATE)) {

			while (rs.next()) {
				_resetAndOptionallyAddBatch(
					ps2, rs, settingsDescriptor.getAllKeys());
			}

			ps2.executeBatch();
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void updatePortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update PortletPreferences set mvccVersion = ?, ownerId = ?, " +
					"ownerType = ?, plid = ?, portletId = ?, preferences = ? " +
						"where portletPreferencesId = ?")) {

			ps.setLong(1, portletPreferencesRow.getMvccVersion());
			ps.setLong(2, portletPreferencesRow.getOwnerId());
			ps.setInt(3, portletPreferencesRow.getOwnerType());
			ps.setLong(4, portletPreferencesRow.getPlid());
			ps.setString(5, portletPreferencesRow.getPortletId());
			ps.setString(6, portletPreferencesRow.getPreferences());
			ps.setLong(7, portletPreferencesRow.getPortletPreferencesId());

			ps.executeUpdate();
		}
	}

	protected void upgradeDisplayPortlet(
			String portletId, String serviceName, int ownerType)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading display portlet " + portletId + " settings");
		}

		SettingsDescriptor settingsDescriptor =
			_settingsFactory.getSettingsDescriptor(serviceName);

		Set<String> serviceKeys = settingsDescriptor.getAllKeys();

		try (LoggingTimer loggingTimer = new LoggingTimer(portletId);
			PreparedStatement ps1 = connection.prepareStatement(
				"select portletPreferencesId, preferences from " +
					"PortletPreferences " + _WHERE_CLAUSE)) {

			ps1.setInt(1, ownerType);
			ps1.setInt(2, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED);
			ps1.setString(3, portletId);

			try (ResultSet rs = ps1.executeQuery();
					PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection, _UPDATE)) {

				while (rs.next()) {
					_resetAndOptionallyAddBatch(ps2, rs, serviceKeys);
				}

				ps2.executeBatch();
			}
		}
	}

	protected void upgradeMainPortlet(
			String portletId, String serviceName, int ownerType,
			boolean resetPortletInstancePreferences)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading main portlet " + portletId + " settings");
		}

		SettingsDescriptor portletSettingsDescriptor =
			_settingsFactory.getSettingsDescriptor(portletId);

		Set<String> portletKeys = portletSettingsDescriptor.getAllKeys();

		SettingsDescriptor serviceSettingsDescriptor =
			_settingsFactory.getSettingsDescriptor(serviceName);

		Set<String> serviceKeys = serviceSettingsDescriptor.getAllKeys();

		String selectSQL =
			"select portletPreferencesId, ownerId, ownerType, " +
				"PortletPreferences.plid, portletId, preferences from " +
					"PortletPreferences " + _WHERE_CLAUSE;

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
			selectSQL = StringUtil.replace(
				selectSQL, " from PortletPreferences",
				", Layout.groupId from PortletPreferences inner join Layout " +
					"on PortletPreferences.plid = Layout.plid");
		}

		String insertSQL =
			"insert into PortletPreferences (mvccVersion, " +
				"portletPreferencesId, ownerId, ownerType, plid, portletId, " +
					"preferences) values (?, ?, ?, ?, ?, ?, ?)";

		try (LoggingTimer loggingTimer = new LoggingTimer(portletId);
			PreparedStatement ps1 = connection.prepareStatement(
				selectSQL)) {

			ps1.setInt(1, ownerType);
			ps1.setInt(2, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED);
			ps1.setString(3, portletId);

			try (ResultSet rs = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, insertSQL);
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _UPDATE)) {

				while (rs.next()) {
					if (portletId.equals(rs.getString("portletId")) &&
						(ownerType == rs.getInt("ownerType"))) {

						long ownerId = rs.getLong("ownerId");
						long plid = rs.getLong("plid");
						String preferences = rs.getString("preferences");

						if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
							ownerId = rs.getLong("groupId");

							plid = 0;

							_logCopyPortletSettings(
								portletId, plid, serviceName, ownerId);
						}

						if (resetPortletInstancePreferences) {
							if (_log.isDebugEnabled()) {
								_log.debug(
									"Delete portlet instance keys from " +
										"service settings");
							}

							preferences = _resetPreferences(
								preferences, portletKeys);
						}

						ps2.setLong(1, 0);
						ps2.setLong(2, increment());
						ps2.setLong(3, ownerId);
						ps2.setInt(4, PortletKeys.PREFS_OWNER_TYPE_GROUP);
						ps2.setLong(5, plid);
						ps2.setString(6, serviceName);
						ps2.setString(7, preferences);

						ps2.addBatch();
					}

					_resetAndOptionallyAddBatch(ps3, rs, serviceKeys);
				}

				ps2.executeBatch();

				ps3.executeBatch();
			}
		}
	}

	private void _logCopyPortletSettings(
		String portletId, long plid, String serviceName, long ownerId) {

		if (!_log.isInfoEnabled()) {
			return;
		}

		StringBundler sb = new StringBundler(8);

		sb.append("Copying portlet ");
		sb.append(portletId);
		sb.append(" settings from layout ");
		sb.append(plid);
		sb.append(" to service ");
		sb.append(serviceName);
		sb.append(" in group ");
		sb.append(ownerId);

		_log.info(sb.toString());
	}

	private void _resetAndOptionallyAddBatch(
			PreparedStatement ps, ResultSet rs, Set<String> allKeys)
		throws Exception {

		String oldPreferences = rs.getString("preferences");

		String newPreferences = _resetPreferences(oldPreferences, allKeys);

		if (!oldPreferences.equals(newPreferences)) {
			ps.setString(1, newPreferences);
			ps.setLong(2, rs.getLong("portletPreferencesId"));

			ps.addBatch();
		}
	}

	private String _resetPreferences(String preferences, Set<String> keys)
		throws ReadOnlyException {

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferences);

		Enumeration<String> names = jxPortletPreferences.getNames();

		boolean modified = false;

		while (names.hasMoreElements()) {
			String name = names.nextElement();

			for (String key : keys) {
				if (name.startsWith(key)) {
					jxPortletPreferences.reset(key);

					modified = true;

					break;
				}
			}
		}

		if (!modified) {
			return preferences;
		}

		return PortletPreferencesFactoryUtil.toXML(jxPortletPreferences);
	}

	private static final String _UPDATE =
		"update PortletPreferences set preferences = ? where " +
			"portletPreferencesId = ?";

	private static final String _WHERE_CLAUSE =
		"where (ownerType = ? or ownerType = ?) and portletId = ? and " +
			"preferences not like '%<portlet-preferences %/>%'";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletSettings.class);

	private final SettingsFactory _settingsFactory;

}