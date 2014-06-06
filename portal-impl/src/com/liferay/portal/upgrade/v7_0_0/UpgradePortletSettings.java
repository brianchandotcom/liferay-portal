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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.tools.Visitor;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.BlogsPortletInstanceSettings;
import com.liferay.portlet.blogs.BlogsSettings;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.bookmarks.BookmarksSettings;
import com.liferay.portlet.bookmarks.util.BookmarksConstants;
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
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ReadOnlyException;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradePortletSettings extends UpgradeProcess {

	protected void createPortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(9);

			sb.append("insert into PortletPreferences (portletPreferencesId, ");
			sb.append("ownerId, ownerType, plid, portletId, preferences, ");
			sb.append("mvccVersion) values (?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, portletPreferencesRow.portletPreferencesId);
			ps.setLong(2, portletPreferencesRow.ownerId);
			ps.setInt(3, portletPreferencesRow.ownerType);
			ps.setLong(4, portletPreferencesRow.plid);
			ps.setString(5, portletPreferencesRow.portletId);
			ps.setString(6, portletPreferencesRow.preferences);
			ps.setLong(7, portletPreferencesRow.mvccVersion);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void createServiceSettings(
			final String portletId, final int ownerType,
			final String serviceName)
		throws PortalException, SystemException {

		try {
			visitPortletPreferences(
				portletId, ownerType,
				new Visitor<PortletPreferencesRow>() {

					@Override
					public void visit(
							PortletPreferencesRow portletPreferencesRow)
						throws SQLException {

						portletPreferencesRow.portletPreferencesId =
							increment();
						portletPreferencesRow.ownerType =
							PortletKeys.PREFS_OWNER_TYPE_GROUP;
						portletPreferencesRow.portletId = serviceName;

						if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
							long plid = portletPreferencesRow.plid;

							long groupId = getGroupIdFromPlid(plid);

							portletPreferencesRow.ownerId = groupId;
							portletPreferencesRow.plid = 0;

							_logCopyOfServiceSettings(
								portletId, plid, serviceName, groupId);
						}

						createPortletPreferences(portletPreferencesRow);
					}

				});
		}
		catch (SQLException sqle) {
			throw new PortalException(
				"Unable to create service settings for portlet "+portletId,
				sqle);
		}
		catch (RuntimeException re) {
			throw new SystemException(
				"Unable to create service settings for portlet "+portletId, re);
		}
	}

	@Override
	protected void doUpgrade() throws PortalException, SystemException {

		// Main portlets

		upgradeMainPortlet(
			PortletKeys.BLOGS, BlogsConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			BlogsPortletInstanceSettings.ALL_KEYS, BlogsSettings.ALL_KEYS);

		upgradeMainPortlet(
			PortletKeys.BOOKMARKS, BookmarksConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, StringPool.EMPTY_ARRAY,
			BookmarksSettings.ALL_KEYS);

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

	protected long getGroupIdFromPlid(long plid) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = "select groupId from Layout where plid = ?";

			ps = con.prepareStatement(sql);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected void resetPortletPreferencesValues(
			String portletId, int ownerType, final String[] keys)
		throws PortalException, SystemException {

		try {
			visitPortletPreferences(
				portletId, ownerType,
				new Visitor<PortletPreferencesRow>() {

					@Override
					public void visit(
							PortletPreferencesRow portletPreferencesRow)
						throws ReadOnlyException, SQLException {

						javax.portlet.PortletPreferences
							javaxPortletPreferences =
								PortletPreferencesFactoryUtil.fromDefaultXML(
									portletPreferencesRow.preferences);

						Enumeration<String> names =
							javaxPortletPreferences.getNames();

						List<String> keysToReset = new ArrayList<String>();

						while (names.hasMoreElements()) {
							String name = names.nextElement();

							for (String key : keys) {
								if (name.startsWith(key)) {
									keysToReset.add(name);

									break;
								}
							}
						}

						for (String keyToReset : keysToReset) {
							javaxPortletPreferences.reset(keyToReset);
						}

						portletPreferencesRow.preferences =
							PortletPreferencesFactoryUtil.toXML(
								javaxPortletPreferences);

						updatePortletPreferences(portletPreferencesRow);
					}

				});
		}
		catch (SQLException sqle) {
			throw new PortalException(
				"Unable to clean keys with ownerType "+ ownerType +" for " +
				"portlet "+portletId, sqle);
		}
		catch (RuntimeException re) {
			throw new SystemException(
				"Unable to clean keys with ownerType "+ ownerType +" for " +
				"portlet "+portletId, re);
		}
	}

	protected void updatePortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(9);

			sb.append(
				"update PortletPreferences set ownerId = ?, ownerType = ?, " +
				"plid = ?, portletId = ?, preferences = ?, mvccVersion = ? " +
				"where portletPreferencesId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, portletPreferencesRow.ownerId);
			ps.setInt(2, portletPreferencesRow.ownerType);
			ps.setLong(3, portletPreferencesRow.plid);
			ps.setString(4, portletPreferencesRow.portletId);
			ps.setString(5, portletPreferencesRow.preferences);
			ps.setLong(6, portletPreferencesRow.mvccVersion);
			ps.setLong(7, portletPreferencesRow.portletPreferencesId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeDisplayPortlet(
			String portletId, int ownerType, String[] serviceKeys)
		throws PortalException, SystemException {

		resetPortletPreferencesValues(portletId, ownerType, serviceKeys);
	}

	protected void upgradeMainPortlet(
			String portletId, String serviceName, int ownerType,
			String[] portletInstanceKeys, String[] serviceKeys)
		throws PortalException, SystemException {

		createServiceSettings(portletId, ownerType, serviceName);

		resetPortletPreferencesValues(
			serviceName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
			portletInstanceKeys);

		resetPortletPreferencesValues(portletId, ownerType, serviceKeys);
	}

	protected void visitPortletPreferences(
			String portletId, int ownerType,
			Visitor<PortletPreferencesRow> visitor)
		throws RuntimeException, SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select portletPreferencesId, ownerId, ownerType, ");
			sb.append("plid, portletId, preferences from PortletPreferences ");
			sb.append("where ownerType = ? and portletId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setInt(1, ownerType);
			ps.setString(2, portletId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PortletPreferencesRow portletPreferencesRow =
					new PortletPreferencesRow();

				portletPreferencesRow.portletPreferencesId = rs.getLong(
					"portletPreferencesId");
				portletPreferencesRow.ownerId = rs.getLong("ownerId");
				portletPreferencesRow.ownerType = rs.getInt("ownerType");
				portletPreferencesRow.plid = rs.getLong("plid");
				portletPreferencesRow.portletId = rs.getString("portletId");
				portletPreferencesRow.preferences = GetterUtil.getString(
					rs.getString("preferences"));

				visitor.visit(portletPreferencesRow);
			}
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected static class PortletPreferencesRow {

		public long portletPreferencesId;
		public long ownerId;
		public int ownerType;
		public long plid;
		public String portletId;
		public String preferences;
		public long mvccVersion;
	}

	private void _logCopyOfServiceSettings(
		String portletId, long plid, final String serviceName, long groupId) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Copying settings of portlet " + portletId + " placed in " +
					"layout " + plid + " to service " + serviceName + " in "+
					"group " + groupId);
		}
	}

	private void _logPortletUpgrade(String portletId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading portlet " + portletId + " settings");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UpgradePortletSettings.class);

}