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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.v7_0_0.util.PortletPreferencesRow;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.BlogsPortletInstanceSettings;
import com.liferay.portlet.blogs.BlogsSettings;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.util.DLConstants;
import com.liferay.portlet.messageboards.MBSettings;
import com.liferay.portlet.messageboards.util.MBConstants;
import com.liferay.portlet.shopping.ShoppingSettings;
import com.liferay.portlet.shopping.util.ShoppingConstants;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.WikiSettings;
import com.liferay.portlet.wiki.util.WikiConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Enumeration;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradePortletSettings extends UpgradeProcess {

	protected void addPortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
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
			DataAccess.cleanUp(con, ps);
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

	@Override
	protected void doUpgrade() throws Exception {

		// Main portlets

		upgradeMainPortlet(
			PortletKeys.BLOGS, BlogsConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			BlogsPortletInstanceSettings.ALL_KEYS, BlogsSettings.ALL_KEYS);
		upgradeMainPortlet(
			PortletKeys.DOCUMENT_LIBRARY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);
		upgradeMainPortlet(
			PortletKeys.MESSAGE_BOARDS, MBConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			MBSettings.ALL_KEYS);
		upgradeMainPortlet(
			PortletKeys.SHOPPING, ShoppingConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			ShoppingSettings.ALL_KEYS);
		upgradeMainPortlet(
			PortletKeys.WIKI, WikiConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiPortletInstanceSettings.ALL_KEYS, WikiSettings.ALL_KEYS);

		// Display portlets

		upgradeDisplayPortlet(
			PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, DLSettings.ALL_KEYS);
		upgradeDisplayPortlet(
			PortletKeys.MEDIA_GALLERY_DISPLAY,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, DLSettings.ALL_KEYS);
		upgradeDisplayPortlet(
			PortletKeys.WIKI_DISPLAY, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiSettings.ALL_KEYS);
	}

	protected long getGroupId(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Layout where plid = ?");

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected ResultSet getPortletPreferencesResultSet(
			String portletId, int ownerType)
		throws Exception {

		Connection con = DataAccess.getUpgradeOptimizedConnection();

		PreparedStatement ps = con.prepareStatement(
			"select portletPreferencesId, ownerId, ownerType, plid, " +
				"portletId, preferences from PortletPreferences where " +
					"ownerType = ? and portletId = ?");

		ps.setInt(1, ownerType);
		ps.setString(2, portletId);

		return ps.executeQuery();
	}

	protected void resetPortletPreferencesValues(
			String portletId, int ownerType, String[] keys)
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

					for (String key : keys) {
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

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
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
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeDisplayPortlet(
			String portletId, int ownerType, String[] serviceKeys)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading display portlet " + portletId + " settings");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete service keys from portlet settings");
		}

		resetPortletPreferencesValues(portletId, ownerType, serviceKeys);

		resetPortletPreferencesValues(
			portletId, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, serviceKeys);
	}

	protected void upgradeMainPortlet(
			String portletId, String serviceName, int ownerType,
			String[] portletInstanceKeys, String[] serviceKeys)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading main portlet " + portletId + " settings");
		}

		copyPortletSettingsAsServiceSettings(portletId, ownerType, serviceName);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet instance keys from service settings");
		}

		resetPortletPreferencesValues(
			serviceName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
			portletInstanceKeys);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete service keys from portlet settings");
		}

		resetPortletPreferencesValues(portletId, ownerType, serviceKeys);

		resetPortletPreferencesValues(
			portletId, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, serviceKeys);
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

}