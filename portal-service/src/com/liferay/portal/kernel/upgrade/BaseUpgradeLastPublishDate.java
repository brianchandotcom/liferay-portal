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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.util.PortletKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public abstract class BaseUpgradeLastPublishDate extends UpgradeProcess {

	protected Date getLayoutSetLastPublishDate(long groupId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select settings_ from LayoutSet where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				UnicodeProperties settingsProperties = new UnicodeProperties(
					true);

				settingsProperties.load(rs.getString("settings_"));

				String lastPublishDateString = settingsProperties.getProperty(
					"last-publish-date");

				if (Validator.isNotNull(lastPublishDateString)) {
					return new Date(GetterUtil.getLong(lastPublishDateString));
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected Date getPortletLastPublishDate(long groupId, String portletId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select preferences from PortletPreferences where plid = ? " +
					"and ownerType = ? and ownerId = ? and portletId = ?");

			ps.setLong(1, LayoutConstants.DEFAULT_PLID);
			ps.setInt(2, PortletKeys.PREFS_OWNER_TYPE_GROUP);
			ps.setLong(3, groupId);
			ps.setString(4, portletId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String preferences = rs.getString("preferences");

				if (Validator.isNotNull(preferences)) {
					int x = preferences.lastIndexOf(
						"last-publish-date</name><value>");

					if (x < 0) {
						break;
					}

					int y = preferences.indexOf("</value>", x);

					String lastPublishDateString = preferences.substring(x, y);

					if (Validator.isNotNull(lastPublishDateString)) {
						return new Date(
							GetterUtil.getLong(lastPublishDateString));
					}
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected List<Long> getStagedGroupIds() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select groupId from Group_ where typeSettings like " +
					"'%staged=true%'");

			rs = ps.executeQuery();

			List<Long> stagedGroupIds = new ArrayList<>();

			while (rs.next()) {
				long stagedGroupId = rs.getLong("groupId");

				stagedGroupIds.add(stagedGroupId);
			}

			return stagedGroupIds;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected void updateLastPublishDates(String portletId, String tableName)
		throws Exception {

		List<Long> stagedGroupIds = getStagedGroupIds();

		for (long stagedGroupId : stagedGroupIds) {
			Date lastPublishDate = getPortletLastPublishDate(
				stagedGroupId, portletId);

			if (lastPublishDate == null) {
				lastPublishDate = getLayoutSetLastPublishDate(stagedGroupId);
			}

			if (lastPublishDate == null) {
				continue;
			}

			updateStagedModelLastPublishDates(
				stagedGroupId, tableName, lastPublishDate);
		}
	}

	protected void updateStagedModelLastPublishDates(
			long groupId, String tableName, Date lastPublishDate)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update " + tableName + " set lastPublishDate = ? where " +
					"groupId = ?");

			ps.setDate(1, new java.sql.Date(lastPublishDate.getTime()));
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

}