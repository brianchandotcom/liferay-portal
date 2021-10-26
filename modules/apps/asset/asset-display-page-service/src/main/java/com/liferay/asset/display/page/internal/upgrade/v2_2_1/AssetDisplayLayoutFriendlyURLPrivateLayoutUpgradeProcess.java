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

package com.liferay.asset.display.page.internal.upgrade.v2_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pavel Savinov
 * @author Roberto Díaz
 */
public class AssetDisplayLayoutFriendlyURLPrivateLayoutUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayoutFriendlyURLs();
	}

	private String _getFriendlyURL(
			PreparedStatement preparedStatement, long groupId,
			String friendlyURL, String languageId)
		throws SQLException {

		String initialFriendlyURL = friendlyURL;

		preparedStatement.setLong(1, groupId);
		preparedStatement.setBoolean(2, false);
		preparedStatement.setString(3, friendlyURL);
		preparedStatement.setString(4, languageId);

		for (int i = 0;; i++) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					friendlyURL = initialFriendlyURL + StringPool.DASH + i;

					preparedStatement.setString(3, friendlyURL);
				}
				else {
					break;
				}
			}
		}

		return friendlyURL;
	}

	private void _upgradeAssetDisplayLayoutFriendlyURLs() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select distinct LayoutFriendlyURL.plid, ",
					"LayoutFriendlyURL.groupId, ",
					"LayoutFriendlyURL.friendlyURL, ",
					"LayoutFriendlyURL.languageId from LayoutFriendlyURL ",
					"inner join Layout on Layout.plid = ",
					"LayoutFriendlyURL.plid where Layout.type_ = ? and ",
					"LayoutFriendlyURL.privateLayout = ?"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select LayoutFriendlyURL.layoutFriendlyURLid from ",
					"LayoutFriendlyURL where LayoutFriendlyURL.groupId = ? ",
					"and LayoutFriendlyURL.privateLayout = ? and ",
					"LayoutFriendlyURL.friendlyURL = ? and ",
					"LayoutFriendlyURL.languageId = ?"));
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutFriendlyURL set privateLayout = ?," +
						"friendlyURL = ? where plid = ?")) {

			preparedStatement1.setString(1, LayoutConstants.TYPE_ASSET_DISPLAY);
			preparedStatement1.setBoolean(2, true);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					String friendlyURL = resultSet.getString("friendlyURL");
					String languageId = resultSet.getString("languageId");
					long plid = resultSet.getLong("plid");

					String newFriendlyURL = _getFriendlyURL(
						preparedStatement2, groupId, friendlyURL, languageId);

					if (!newFriendlyURL.equals(friendlyURL)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"FriendlyURL for PLID ", plid, " in group ",
									groupId, " changed from ", friendlyURL,
									" to ", newFriendlyURL));
						}
					}

					preparedStatement3.setBoolean(1, false);
					preparedStatement3.setString(2, newFriendlyURL);
					preparedStatement3.setLong(3, plid);

					preparedStatement3.addBatch();
				}

				preparedStatement3.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayLayoutFriendlyURLPrivateLayoutUpgradeProcess.class);

}