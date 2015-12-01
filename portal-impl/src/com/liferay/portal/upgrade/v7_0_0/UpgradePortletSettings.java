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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v7_0_0.util.PortletPreferencesRow;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Enumeration;

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

	protected void addPortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"insert into PortletPreferences (mvccVersion, " +
					"portletPreferencesId, ownerId, ownerType, plid, " +
						"portletId, preferences) values (?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, portletPreferencesRow.getMvccVersion());
			ps.setLong(2, portletPreferencesRow.getPortletPreferencesId());
			ps.setLong(3, portletPreferencesRow.getOwnerId());
			ps.setInt(4, portletPreferencesRow.getOwnerType());
			ps.setLong(5, portletPreferencesRow.getPlid());
			ps.setString(6, portletPreferencesRow.getPortletId());
			ps.setString(7, portletPreferencesRow.getPreferences());

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void copyPortletSettingsAsServiceSettings(
			String portletId, int ownerType, String serviceName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Copy portlet settings as service settings");
		}

		ResultSet rs = null;

		try {
			rs = getPortletPreferencesResultSet(portletId, ownerType);

			while (rs.next()) {
				PortletPreferencesRow portletPreferencesRow =
					getPortletPreferencesRow(rs);

				portletPreferencesRow.setPortletPreferencesId(increment());
				portletPreferencesRow.setOwnerType(
					PortletKeys.PREFS_OWNER_TYPE_GROUP);
				portletPreferencesRow.setPortletId(serviceName);

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
					long plid = portletPreferencesRow.getPlid();

					long groupId = getGroupId(plid);

					portletPreferencesRow.setOwnerId(groupId);
					portletPreferencesRow.setPlid(0);

					if (_log.isInfoEnabled()) {
						StringBundler sb = new StringBundler(8);

						sb.append("Copying portlet ");
						sb.append(portletId);
						sb.append(" settings from layout ");
						sb.append(plid);
						sb.append(" to service ");
						sb.append(serviceName);
						sb.append(" in group ");
						sb.append(groupId);

						_log.info(sb.toString());
					}
				}

				addPortletPreferences(portletPreferencesRow);
			}
		}
		finally {
			DataAccess.deepCleanUp(rs);
		}
	}

	protected long getGroupId(long plid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			ps = connection.prepareStatement(
				"select groupId from Layout where plid = ?");

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}

		return groupId;
	}

	protected ResultSet getPortletPreferencesResultSet(
			String portletId, int ownerType)
		throws Exception {

		PreparedStatement ps = connection.prepareStatement(
			"select portletPreferencesId, ownerId, ownerType, plid, " +
				"portletId, preferences from PortletPreferences where " +
					"ownerType = ? and portletId = ?");

		ps.setInt(1, ownerType);
		ps.setString(2, portletId);

		return ps.executeQuery();
	}

	protected void resetPortletPreferencesValues(
			String portletId, int ownerType,
			SettingsDescriptor settingsDescriptor)
		throws Exception {

		ResultSet rs = null;

		try {
			rs = getPortletPreferencesResultSet(portletId, ownerType);

			while (rs.next()) {
				PortletPreferencesRow portletPreferencesRow =
					getPortletPreferencesRow(rs);

				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portletPreferencesRow.getPreferences());

				Enumeration<String> names = jxPortletPreferences.getNames();

				while (names.hasMoreElements()) {
					String name = names.nextElement();

					for (String key : settingsDescriptor.getAllKeys()) {
						if (name.startsWith(key)) {
							jxPortletPreferences.reset(key);

							break;
						}
					}
				}

				portletPreferencesRow.setPreferences(
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

				updatePortletPreferences(portletPreferencesRow);
			}
		}
		finally {
			DataAccess.deepCleanUp(rs);
		}
	}

	protected void updatePortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update PortletPreferences set mvccVersion = ?, ownerId = ?, " +
					"ownerType = ?, plid = ?, portletId = ?, preferences = ? " +
						"where portletPreferencesId = ?");

			ps.setLong(1, portletPreferencesRow.getMvccVersion());
			ps.setLong(2, portletPreferencesRow.getOwnerId());
			ps.setInt(3, portletPreferencesRow.getOwnerType());
			ps.setLong(4, portletPreferencesRow.getPlid());
			ps.setString(5, portletPreferencesRow.getPortletId());
			ps.setString(6, portletPreferencesRow.getPreferences());
			ps.setLong(7, portletPreferencesRow.getPortletPreferencesId());

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeDisplayPortlet(
			String portletId, String serviceName, int ownerType)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading display portlet " + portletId + " settings");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete service keys from portlet settings");
		}

		SettingsDescriptor settingsDescriptor =
			_settingsFactory.getSettingsDescriptor(serviceName);

		resetPortletPreferencesValues(portletId, ownerType, settingsDescriptor);

		resetPortletPreferencesValues(
			portletId, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
			settingsDescriptor);
	}

	protected void upgradeMainPortlet(
			String portletId, String serviceName, int ownerType,
			boolean resetPortletInstancePreferences)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading main portlet " + portletId + " settings");
		}

		copyPortletSettingsAsServiceSettings(portletId, ownerType, serviceName);

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

	private PortletPreferencesRow getPortletPreferencesRow(ResultSet rs)
		throws Exception {

		return new PortletPreferencesRow(
			rs.getLong("portletPreferencesId"), rs.getLong("ownerId"),
			rs.getInt("ownerType"), rs.getLong("plid"),
			rs.getString("portletId"), rs.getString("preferences"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletSettings.class);

	private final SettingsFactory _settingsFactory;

}