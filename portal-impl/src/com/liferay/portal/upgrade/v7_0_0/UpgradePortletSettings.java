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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Set;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public abstract class UpgradePortletSettings extends UpgradeProcess {

	public UpgradePortletSettings(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	protected void copyPortletSettingsAsServiceSettings(
			String portletId, int ownerType, String serviceName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Copy portlet settings as service settings");
		}

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select PortletPreferences.portletPreferencesId, ",
						"PortletPreferences.ownerId, PortletPreferences.plid, ",
						"Layout.groupId from PortletPreferences left join ",
						"Layout on Layout.plid = PortletPreferences.plid ",
						"where PortletPreferences.ownerType = ", ownerType,
						" and PortletPreferences.portletId = '", portletId,
						"'"));
			ResultSet rs = selectPreparedStatement.executeQuery()) {

			while (rs.next()) {
				long oldPortletPreferencesId = rs.getLong(1);

				long ownerId = 0;
				long plid = 0;

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
					ownerId = rs.getLong(3);
					plid = 0;
				}
				else {
					ownerId = rs.getLong(1);
					plid = rs.getLong(2);
				}

				long newPortletPreferencesId = increment();

				try (PreparedStatement insertPreparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"insert into PortletPreferences (mvccVersion, ",
								"ctCollectionId, portletPreferencesId, ",
								"ownerId, ownerType, plid, portletId) values ",
								"(0, 0, ?, ?, ?, ?, ?)"))) {

					insertPreparedStatement.setLong(1, newPortletPreferencesId);
					insertPreparedStatement.setLong(2, ownerId);
					insertPreparedStatement.setInt(
						3, PortletKeys.PREFS_OWNER_TYPE_GROUP);
					insertPreparedStatement.setLong(4, plid);
					insertPreparedStatement.setString(5, serviceName);

					insertPreparedStatement.executeUpdate();

					_copyPortletPreferenceValues(
						oldPortletPreferencesId, newPortletPreferencesId);
				}
				catch (SQLException sqlException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to copy portlet preferences " +
								oldPortletPreferencesId,
							sqlException);
					}
				}
			}
		}
	}

	protected void resetPortletPreferencesValues(
			String portletId, int ownerType,
			SettingsDescriptor settingsDescriptor)
		throws Exception {

		Set<String> allKeys = settingsDescriptor.getAllKeys();

		if (allKeys.isEmpty()) {
			return;
		}

		StringBundler sb = new StringBundler(allKeys.size() + 8);

		sb.append("delete from PortletPreferenceValue where ");
		sb.append("PortletPreferenceValue.portletPreferencesId in (select ");
		sb.append("PortletPreferences.portletPreferencesId from ");
		sb.append("PortletPreferences where PortletPreferences.ownerType = ");
		sb.append(ownerType);
		sb.append(" and PortletPreferences.portletId = '");
		sb.append(portletId);
		sb.append("') and PortletPreferenceValue.name in (?");

		for (int i = 1; i < allKeys.size(); i++) {
			sb.append(", ?");
		}

		sb.append(")");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			int i = 0;

			for (String key : allKeys) {
				preparedStatement.setString(++i, key);
			}

			preparedStatement.executeUpdate();
		}
	}

	protected void upgradeDisplayPortlet(
			String portletId, String serviceName, int ownerType)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(portletId)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Upgrading display portlet " + portletId + " settings");
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Delete service keys from portlet settings");
			}

			SettingsDescriptor settingsDescriptor =
				_settingsFactory.getSettingsDescriptor(serviceName);

			resetPortletPreferencesValues(
				portletId, ownerType, settingsDescriptor);

			resetPortletPreferencesValues(
				portletId, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				settingsDescriptor);
		}
	}

	protected void upgradeMainPortlet(
			String portletId, String serviceName, int ownerType,
			boolean resetPortletInstancePreferences)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(portletId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Upgrading main portlet " + portletId + " settings");
			}

			copyPortletSettingsAsServiceSettings(
				portletId, ownerType, serviceName);

			if (resetPortletInstancePreferences) {
				SettingsDescriptor portletInstanceSettingsDescriptor =
					_settingsFactory.getSettingsDescriptor(portletId);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Delete portlet instance keys from service settings");
				}

				resetPortletPreferencesValues(
					serviceName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
					portletInstanceSettingsDescriptor);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Delete service keys from portlet settings");
			}

			SettingsDescriptor serviceSettingsDescriptor =
				_settingsFactory.getSettingsDescriptor(serviceName);

			resetPortletPreferencesValues(
				portletId, ownerType, serviceSettingsDescriptor);

			resetPortletPreferencesValues(
				portletId, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				serviceSettingsDescriptor);
		}
	}

	private void _copyPortletPreferenceValues(
			long oldPortletPreferencesId, long newPortletPreferencesId)
		throws SQLException {

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select portletPreferenceValueId from ",
						"PortletPreferenceValue where portletPreferencesId = ",
						"?"));
			PreparedStatement insertPreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into PortletPreferenceValue (mvccVersion, ",
							"ctCollectionId, portletPreferenceValueId, ",
							"companyId, portletPreferencesId, index_, ",
							"largeValue, name, readOnly, smallValue) select 0 ",
							"as mvccVersion, 0 as ctCollectionId, ? as ",
							"portletPreferenceValueId, TEMP_TABLE.companyId, ",
							"? as portletPreferencesId, TEMP_TABLE.index_, ",
							"TEMP_TABLE.largeValue, TEMP_TABLE.name, ",
							"TEMP_TABLE.readOnly, TEMP_TABLE.smallValue from ",
							"PortletPreferenceValue TEMP_TABLE where ",
							"TEMP_TABLE.portletPreferenceValueId = ?")))) {

			selectPreparedStatement.setLong(1, oldPortletPreferencesId);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					insertPreparedStatement.setLong(
						1, increment(PortletPreferenceValue.class.getName()));
					insertPreparedStatement.setLong(2, newPortletPreferencesId);
					insertPreparedStatement.setLong(3, resultSet.getLong(1));

					insertPreparedStatement.addBatch();
				}

				insertPreparedStatement.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletSettings.class);

	private final SettingsFactory _settingsFactory;

}