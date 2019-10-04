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
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pavel Savinov
 */
public class UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayoutFriendlyURLs();
	}

	private void _upgradeAssetDisplayLayoutFriendlyURLs() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select LayoutFriendlyURL.plid from LayoutFriendlyURL ");
		sb.append("inner join Layout on Layout.plid = LayoutFriendlyURL.plid ");
		sb.append("where Layout.type_ = ? and ");
		sb.append("LayoutFriendlyURL.privateLayout = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutFriendlyURL set privateLayout = ? where " +
						"plid = ?"))) {

			ps1.setString(1, LayoutConstants.TYPE_ASSET_DISPLAY);
			ps1.setBoolean(2, true);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long plid = rs.getLong("plid");

					ps2.setBoolean(1, false);
					ps2.setLong(2, plid);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}