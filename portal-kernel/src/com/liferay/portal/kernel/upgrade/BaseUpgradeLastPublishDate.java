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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public abstract class BaseUpgradeLastPublishDate extends UpgradeProcess {

	protected void addLastPublishDateColumn(String tableName) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			if (hasColumn(tableName, "lastPublishDate")) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Table " + tableName +
							" already has the column lastPublishDate");
				}

				return;
			}

			runSQL(
				"alter table " + tableName + " add lastPublishDate DATE null");
		}
	}

	protected Date getLayoutSetLastPublishDate(long groupId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select settings_ from LayoutSet where groupId = ?")) {

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

				return null;
			}
		}
	}

	protected Date getPortletLastPublishDate(long groupId, String portletId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select preferences from PortletPreferences where plid = ? " +
					"and ownerType = ? and ownerId = ? and portletId = ?")) {

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

				return null;
			}
		}
	}

	protected List<Long> getStagedGroupIds() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where typeSettings like " +
					"'%staged=true%'");
			ResultSet rs = ps.executeQuery()) {

			List<Long> stagedGroupIds = new ArrayList<>();

			while (rs.next()) {
				long stagedGroupId = rs.getLong("groupId");

				stagedGroupIds.add(stagedGroupId);
			}

			return stagedGroupIds;
		}
	}

	protected void updateLastPublishDates(String portletId, String tableName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			List<Long> stagedGroupIds = getStagedGroupIds();

			for (long stagedGroupId : stagedGroupIds) {
				Date lastPublishDate = getPortletLastPublishDate(
					stagedGroupId, portletId);

				if (lastPublishDate == null) {
					lastPublishDate = getLayoutSetLastPublishDate(
						stagedGroupId);
				}

				if (lastPublishDate == null) {
					continue;
				}

				updateStagedModelLastPublishDates(
					stagedGroupId, tableName, lastPublishDate);
			}
		}
	}

	protected void updateStagedModelLastPublishDates(
			long groupId, String tableName, Date lastPublishDate)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update " + tableName + " set lastPublishDate = ? where " +
					"groupId = ?")) {

			ps.setDate(1, new java.sql.Date(lastPublishDate.getTime()));
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeLastPublishDate.class);

}