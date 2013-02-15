/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.social.WikiActivityKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Sergio Sanchez
 */
public class UpgradeWikiSocialActivities extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWikiPageSocialActivities();
	}

	private void addUpdatedSocialActivity(
			long groupId, long companyId, long userId, Timestamp modifiedDate,
			long classNameId, long resourcePrimKey, double version)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("version", version);

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into SocialActivity (activityId, groupId, companyId, " +
					"userId, createDate, mirrorActivityId, classNameId, " +
						"classPK, type_, extraData, receiverUserId) values (" +
							"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, increment());
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setLong(5, modifiedDate.getTime());
			ps.setInt(6, 0);
			ps.setLong(7, classNameId);
			ps.setLong(8, resourcePrimKey);

			if (version > 1.0) {
				ps.setInt(9, WikiActivityKeys.UPDATE_PAGE);
			}
			else {
				ps.setInt(9, WikiActivityKeys.ADD_PAGE);
			}

			ps.setString(10, extraDataJSONObject.toString());
			ps.setInt(11, 0);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void updateWikiPageSocialActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(WikiPage.class);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			runSQL(
				"delete from SocialActivity where classNameId = " +
					classNameId);

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, " +
					"resourcePrimKey, version from WikiPage");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				double version = rs.getDouble("version");

				addUpdatedSocialActivity(
					groupId, companyId, userId, modifiedDate, classNameId,
					resourcePrimKey, version);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}