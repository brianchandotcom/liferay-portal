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
public class UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayoutFriendlyURLs();
	}

	private String _getFriendlyURL(
			PreparedStatement ps, long groupId, String friendlyURL,
			String languageId)
		throws SQLException {

		String initialFriendlyURL = friendlyURL;

		ps.setLong(1, groupId);
		ps.setBoolean(2, false);
		ps.setString(3, friendlyURL);
		ps.setString(4, languageId);

		for (int i = 0;; i++) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					friendlyURL = initialFriendlyURL + StringPool.DASH + i;

					ps.setString(3, friendlyURL);
				}
				else {
					break;
				}
			}
		}

		return friendlyURL;
	}

	private void _upgradeAssetDisplayLayoutFriendlyURLs() throws Exception {
		StringBundler sb1 = new StringBundler(7);

		sb1.append("select distinct LayoutFriendlyURL.groupId, ");
		sb1.append("LayoutFriendlyURL.groupId, ");
		sb1.append("LayoutFriendlyURL.friendlyURL, ");
		sb1.append("LayoutFriendlyURL.languageId from LayoutFriendlyURL ");
		sb1.append("inner join Layout on Layout.plid = ");
		sb1.append("LayoutFriendlyURL.plid where Layout.type_ = ? and ");
		sb1.append("LayoutFriendlyURL.privateLayout = ?");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("select LayoutFriendlyURL.layoutFriendlyURLid from ");
		sb2.append("LayoutFriendlyURL where LayoutFriendlyURL.groupId = ? ");
		sb2.append("and LayoutFriendlyURL.privateLayout = ? and ");
		sb2.append("LayoutFriendlyURL.friendlyURL = ? and ");
		sb2.append("LayoutFriendlyURL.languageId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 = connection.prepareStatement(sb2.toString());
			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutFriendlyURL set privateLayout = ?," +
						"friendlyURL = ? where plid = ?"))) {

			ps1.setString(1, LayoutConstants.TYPE_ASSET_DISPLAY);
			ps1.setBoolean(2, true);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					String friendlyURL = rs.getString("friendlyURL");
					String languageId = rs.getString("languageId");
					long plid = rs.getLong("plid");

					String newFriendlyURL = _getFriendlyURL(
						ps2, groupId, friendlyURL, languageId);

					if (!newFriendlyURL.equals(friendlyURL)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"FriendlyURL for plid ", plid,
									" in groupId ", groupId, " changed from ",
									friendlyURL, " to ", newFriendlyURL));
						}
					}

					ps3.setBoolean(1, false);
					ps3.setString(2, newFriendlyURL);
					ps3.setLong(2, plid);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout.class);

}