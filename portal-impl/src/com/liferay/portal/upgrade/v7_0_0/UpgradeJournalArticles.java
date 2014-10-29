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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.journal.model.JournalArticle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticles extends UpgradeBaseJournal {

	@Override
	protected void doUpgrade() throws Exception {
		updateLayouts();
		updatePreferences();
		updateResourcePermission();
	}

	protected long getCategoryId(long companyId, String type) throws Exception {
		if (Validator.isNull(type)) {
			return 0;
		}

		long groupId = getCompanyGroupId(companyId);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select categoryId from AssetCategory where groupId = " +
					groupId + " and name = '" + type + "'");

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("categoryId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getCompanyId(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("companyId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getGroupId(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getNewPreferences(long plid, String preferences)
		throws Exception {

		PortletPreferences oldPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferences);

		String ddmStructureKey = oldPortletPreferences.getValue(
			"ddmStructureKey", StringPool.BLANK);
		String pageUrl = oldPortletPreferences.getValue(
			"pageUrl", StringPool.BLANK);
		String orderByCol = oldPortletPreferences.getValue(
			"orderByCol", StringPool.BLANK);
		String orderByType = oldPortletPreferences.getValue(
			"orderByType", StringPool.BLANK);
		String type = oldPortletPreferences.getValue("type", StringPool.BLANK);
		long groupId = GetterUtil.getLong(
			oldPortletPreferences.getValue("groupId", StringPool.BLANK));
		int pageDelta = GetterUtil.getInteger(
			oldPortletPreferences.getValue("pageDelta", StringPool.BLANK));

		long companyId = getCompanyId(plid);

		PortletPreferences newPortletPreferences = new PortletPreferencesImpl();

		newPortletPreferences.setValue(
			"anyAssetType",
			String.valueOf(PortalUtil.getClassNameId(JournalArticle.class)));

		long structureId = getStructureId(companyId, plid, ddmStructureKey);

		if (structureId > 0) {
			newPortletPreferences.setValue(
				"anyClassTypeJournalArticleAssetRendererFactory",
				String.valueOf(structureId));
		}

		String assetLinkBehavior = "showFullContent";

		if (pageUrl.equals("viewInContext")) {
			assetLinkBehavior = "viewInPortlet";
		}

		newPortletPreferences.setValue("assetLinkBehavior", assetLinkBehavior);

		if (structureId > 0) {
			newPortletPreferences.setValue(
				"classTypeIds", String.valueOf(structureId));
		}

		newPortletPreferences.setValue("delta", String.valueOf(pageDelta));
		newPortletPreferences.setValue("displayStyle", "table");
		newPortletPreferences.setValue("metadataFields", "publish-date,author");
		newPortletPreferences.setValue("orderByColumn1", orderByCol);
		newPortletPreferences.setValue("orderByType1", orderByType);
		newPortletPreferences.setValue("paginationType", "none");

		long categoryId = getCategoryId(companyId, type);

		if (categoryId > 0) {
			newPortletPreferences.setValue(
				"queryAndOperator0", Boolean.TRUE.toString());
			newPortletPreferences.setValue(
				"queryContains0", Boolean.TRUE.toString());
			newPortletPreferences.setValue("queryName0", "assetCategories");
			newPortletPreferences.setValue(
				"queryValues0", String.valueOf(categoryId));
		}

		newPortletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());

		String groupName = String.valueOf(groupId);

		if (groupId == getGroupId(plid)) {
			groupName = "default";
		}

		newPortletPreferences.setValue("scopeIds", "Group_" + groupName);

		return PortletPreferencesFactoryUtil.toXML(newPortletPreferences);
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId, String newRootPortletId) {

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

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				if (!rootPortletId.equals(oldRootPortletId)) {
					continue;
				}

				portletIds[j] = getPortletId(portletId, newRootPortletId);
			}

			typeSettingsProperties.setProperty(
				column, StringUtil.merge(portletIds).concat(StringPool.COMMA));
		}

		return typeSettingsProperties.toString();
	}

	protected String getPortletId(
		String oldRootPortletId, String newRootPortletId) {

		long userId = PortletConstants.getUserId(oldRootPortletId);
		String instanceId = PortletConstants.getInstanceId(oldRootPortletId);

		return PortletConstants.assemblePortletId(
			newRootPortletId, userId, instanceId);
	}

	protected long getStructureId(
			long companyId, long plid, String ddmStructureKey)
		throws Exception {

		if (Validator.isNull(ddmStructureKey)) {
			return 0;
		}

		long groupId = getGroupId(plid);
		long companyGroupId = getCompanyGroupId(companyId);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select structureId from DDMStructure where (groupId = " +
					groupId + " or groupId = " + companyGroupId + ") and " +
					"structureKey = '" + ddmStructureKey + "'"
			);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("structureId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateLayouts() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"like '%" + _JOURNAL_CONTENT_LIST + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, _JOURNAL_CONTENT_LIST,
					PortletKeys.ASSET_PUBLISHER);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePreference(
			long portletPreferencesId, String oldPortletId,
			String newPreferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set preferences = ?, " +
					"portletId = ? where portletPreferencesId = " +
						portletPreferencesId);

			ps.setString(1, newPreferences);
			ps.setString(
				2, getPortletId(oldPortletId, PortletKeys.ASSET_PUBLISHER));

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updatePreferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, plid, portletId, preferences " +
					"from PortletPreferences where portletId like '%" +
					_JOURNAL_CONTENT_LIST + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long plid = rs.getLong("plid");
				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				String newPreferences = getNewPreferences(plid, preferences);

				updatePreference(
					portletPreferencesId, portletId, newPreferences);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourcePermission() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourcePermissionId, name, scope, primKey from " +
					"ResourcePermission where name = '" +
					_JOURNAL_CONTENT_LIST + "'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				String name = rs.getString("name");
				int scope = rs.getInt("scope");
				String primKey = rs.getString("primKey");

				if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
					int pos = primKey.indexOf(
						PortletConstants.LAYOUT_SEPARATOR);

					if (pos != -1) {
						long plid = GetterUtil.getLong(
							primKey.substring(0, pos));

						String portletId = primKey.substring(
							pos + PortletConstants.LAYOUT_SEPARATOR.length());

						String newPortletId = getPortletId(
							portletId, PortletKeys.ASSET_PUBLISHER);

						primKey = PortletPermissionUtil.getPrimaryKey(
							plid, newPortletId);
					}
				}

				updateResourcePermission(resourcePermissionId, name, primKey);
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourcePermission(
			long resourcePermissionId, String name, String primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update ResourcePermission set name = ?, primKey = ? where " +
					"resourcePermissionId = " + resourcePermissionId);

			ps.setString(1, name);
			ps.setString(2, primKey);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _JOURNAL_CONTENT_LIST = "62";

	private static Log _log = LogFactoryUtil.getLog(
		UpgradeJournalArticles.class);

}