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

package com.liferay.microblogs.upgrade.v1_0_2;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Matthew Kong
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeMicroblogActivities();
	}

	protected void updateSocialActivity(long activityId, JSONObject jsonObject)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update SocialActivity set extraData = ? where activityId = ?");

			ps.setString(1, jsonObject.toString());
			ps.setLong(2, activityId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeMicroblogActivities() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select activityId, extraData from SocialActivity where " +
					"classNameId = ?");

			ps.setLong(1, PortalUtil.getClassNameId(MicroblogsEntry.class));

			rs = ps.executeQuery();

			while (rs.next()) {
				long activityId = rs.getLong("activityId");
				String extraData = rs.getString("extraData");

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject(extraData);

				long parentMicroblogsEntryId = extraDataJSONObject.getLong(
					"receiverMicroblogsEntryId");

				extraDataJSONObject.put(
					"parentMicroblogsEntryId", parentMicroblogsEntryId);

				extraDataJSONObject.remove("receiverMicroblogsEntryId");

				updateSocialActivity(activityId, extraDataJSONObject);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

}