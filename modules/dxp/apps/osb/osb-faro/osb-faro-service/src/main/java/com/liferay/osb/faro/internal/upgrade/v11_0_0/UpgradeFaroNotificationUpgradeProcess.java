/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.internal.upgrade.v11_0_0;

import com.liferay.osb.faro.constants.FaroNotificationConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Geyson Silva
 */
public class UpgradeFaroNotificationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"create table OSBFaro_FaroNotification (faroNotificationId ",
				"LONG not null primary key, groupId LONG, userId LONG, ",
				"createTime LONG, modifiedTime LONG, ownerId LONG, scope ",
				"VARCHAR(75) null, read_ BOOLEAN, type_ VARCHAR(75) null, ",
				"subtype VARCHAR(75) null)"));

		_notifyFaroProjects();
	}

	private void _addFaroNotification(long groupId) throws SQLException {
		String sql = StringBundler.concat(
			"insert into OSBFaro_FaroNotification (faroNotificationId, ",
			"groupId, createTime, modifiedTime, ownerId, scope, read_, type_, ",
			"subtype) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setLong(1, increment());
			preparedStatement.setLong(2, groupId);

			long now = System.currentTimeMillis();

			preparedStatement.setLong(3, now);
			preparedStatement.setLong(4, now);

			preparedStatement.setLong(5, groupId);
			preparedStatement.setString(
				6, FaroNotificationConstants.SCOPE_WORKSPACE);
			preparedStatement.setBoolean(7, false);
			preparedStatement.setString(
				8, FaroNotificationConstants.TYPE_MODAL);
			preparedStatement.setString(
				9, FaroNotificationConstants.SUBTYPE_TIME_ZONE_ADMIN);

			preparedStatement.executeUpdate();
		}
	}

	private void _notifyFaroProjects() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId from OSBFaro_FaroProject");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				_addFaroNotification(resultSet.getLong(1));
			}
		}
	}

}