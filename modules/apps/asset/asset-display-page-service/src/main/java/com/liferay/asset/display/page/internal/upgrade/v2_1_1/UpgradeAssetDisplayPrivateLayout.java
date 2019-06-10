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

package com.liferay.asset.display.page.internal.upgrade.v2_1_1;

import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pavel Savinov
 */
public class UpgradeAssetDisplayPrivateLayout extends UpgradeProcess {

	public UpgradeAssetDisplayPrivateLayout(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayouts();
	}

	private void _upgradeAssetDisplayLayouts() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select groupId, plid from Layout where privateLayout = ? " +
					"and type_ = ?");
			PreparedStatement ps2 = connection.prepareStatement(
				"update Layout set layoutId = ?, privateLayout = ? where " +
					"plid = ?")) {

			ps1.setBoolean(1, true);
			ps1.setString(2, LayoutConstants.TYPE_ASSET_DISPLAY);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long plid = rs.getLong("plid");

					ps2.setLong(
						1, _layoutLocalService.getNextLayoutId(groupId, false));
					ps2.setBoolean(2, false);
					ps2.setLong(3, plid);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;

}