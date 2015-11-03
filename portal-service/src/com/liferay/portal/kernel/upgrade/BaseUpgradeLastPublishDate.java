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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public class BaseUpgradeLastPublishDate extends UpgradeProcess {

	protected Date getLayoutSetLastPublishDate(Connection con, long groupId)
		throws Exception {

		String sql = "select settings_ from LayoutSet where groupId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, groupId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					UnicodeProperties settingsProperties =
						new UnicodeProperties(true);

					settingsProperties.load(rs.getString("settings_"));

					String lastPublishDateString =
						settingsProperties.getProperty("last-publish-date");

					if (Validator.isNotNull(lastPublishDateString)) {
						return new Date(
							GetterUtil.getLong(lastPublishDateString));
					}
				}
			}

			return null;
		}
	}

	protected Date getPortletLastPublishDate(
			Connection con, long groupId, String portletId)
		throws Exception {

		String sql =
			"select preferences from PortletPreferences where plid = ? and " +
				"ownerType = ? and ownerId = ? and portletId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, LayoutConstants.DEFAULT_PLID);
			ps.setInt(2, PortletKeys.PREFS_OWNER_TYPE_GROUP);
			ps.setLong(3, groupId);
			ps.setString(4, portletId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String preferences = rs.getString("preferences");

					if (Validator.isNotNull(preferences)) {
						int x = preferences.lastIndexOf(
							"last-publish-date</name><value>");

						if (x < 0) {
							break;
						}

						int y = preferences.indexOf("</value>", x);

						String lastPublishDateString = preferences.substring(
							x, y);

						if (Validator.isNotNull(lastPublishDateString)) {
							return new Date(
								GetterUtil.getLong(lastPublishDateString));
						}
					}
				}
			}

			return null;
		}
	}

	protected List<Long> getStagedGroupIds(Connection con) throws Exception {
		String sql =
			"select groupId from Group_ where typeSettings like " +
				"'%staged=true%'";

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			List<Long> stagedGroupIds = new ArrayList<>();

			while (rs.next()) {
				long stagedGroupId = rs.getLong("groupId");

				stagedGroupIds.add(stagedGroupId);
			}

			return stagedGroupIds;
		}
	}

	protected void updateLastPublishDates(
			Connection con, String portletId, String tableName)
		throws Exception {

		List<Long> stagedGroupIds = getStagedGroupIds(con);

		for (long stagedGroupId : stagedGroupIds) {
			Date lastPublishDate = getPortletLastPublishDate(
				con, stagedGroupId, portletId);

			if (lastPublishDate == null) {
				lastPublishDate = getLayoutSetLastPublishDate(
					con, stagedGroupId);
			}

			if (lastPublishDate == null) {
				continue;
			}

			updateStagedModelLastPublishDates(
				con, stagedGroupId, tableName, lastPublishDate);
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *            #updateLastPublishDates(Connection, String, String)}
	 */
	@Deprecated
	protected void updateLastPublishDates(String portletId, String tableName)
		throws Exception {

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			updateLastPublishDates(con, portletId, tableName);
		}
	}

	protected void updateStagedModelLastPublishDates(
			Connection con, long groupId, String tableName,
			Date lastPublishDate)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set lastPublishDate = ? where groupId = ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			ps.setDate(1, new java.sql.Date(lastPublishDate.getTime()));
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
	}

}