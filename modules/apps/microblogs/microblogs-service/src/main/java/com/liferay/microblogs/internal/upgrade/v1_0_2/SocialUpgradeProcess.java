/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.upgrade.v1_0_2;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Matthew Kong
 */
public class SocialUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeMicroblogActivities();
	}

	private void _updateSocialActivity(long activityId, JSONObject jsonObject)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SocialActivity set extraData = ? where activityId = " +
					"?")) {

			preparedStatement.setString(1, jsonObject.toString());
			preparedStatement.setLong(2, activityId);

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeMicroblogActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select activityId, extraData from SocialActivity where " +
					"classNameId = ?")) {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(MicroblogsEntry.class));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long activityId = resultSet.getLong("activityId");

					String extraData = resultSet.getString("extraData");

					JSONObject extraDataJSONObject =
						JSONFactoryUtil.createJSONObject(extraData);

					long parentMicroblogsEntryId = extraDataJSONObject.getLong(
						"receiverMicroblogsEntryId");

					extraDataJSONObject.put(
						"parentMicroblogsEntryId", parentMicroblogsEntryId);

					extraDataJSONObject.remove("receiverMicroblogsEntryId");

					_updateSocialActivity(activityId, extraDataJSONObject);
				}
			}
		}
	}

}