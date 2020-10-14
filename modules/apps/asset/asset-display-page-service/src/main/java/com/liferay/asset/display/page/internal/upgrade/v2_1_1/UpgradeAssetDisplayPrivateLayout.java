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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pavel Savinov
 * @author Roberto Díaz
 */
public class UpgradeAssetDisplayPrivateLayout extends UpgradeProcess {

	public UpgradeAssetDisplayPrivateLayout(
		LayoutLocalService layoutLocalService,
		ResourceLocalService resourceLocalService) {

		_layoutLocalService = layoutLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeAssetDisplayLayouts();
	}

	private void _addResources(long groupId, long plid) throws PortalException {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		_resourceLocalService.addResources(
			layout.getCompanyId(), groupId, layout.getUserId(),
			Layout.class.getName(), layout.getPlid(), false, true, true);
	}

	private String _getFriendlyURL(
			PreparedStatement ps, long groupId, String friendlyURL,
			long ctCollectionId)
		throws SQLException {

		String initialFriendlyURL = friendlyURL;

		ps.setLong(1, groupId);
		ps.setBoolean(2, false);
		ps.setString(3, friendlyURL);
		ps.setLong(4, ctCollectionId);

		for (int i = 1;; i++) {
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

	private void _upgradeAssetDisplayLayouts() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select groupId, friendlyURL, plid, ctCollectionId from " +
					"Layout where privateLayout = ? and type_ = ?");
			PreparedStatement ps2 = connection.prepareStatement(
				"select plid from Layout where groupId = ? and privateLayout " +
					"= ? and friendlyURL = ? and ctCollectionId = ?");
			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update Layout set layoutId = ?, privateLayout = ?, " +
						"friendlyURL = ? where plid = ?"));
			PreparedStatement ps4 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutFriendlyURL set privateLayout = ?, " +
						"friendlyURL = ? where plid = ?"))) {

			ps1.setBoolean(1, true);
			ps1.setString(2, LayoutConstants.TYPE_ASSET_DISPLAY);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					String friendlyURL = rs.getString("friendlyURL");
					long plid = rs.getLong("plid");
					long ctCollectionId = rs.getLong("ctCollectionId");

					_addResources(groupId, plid);

					String newfriendlyURL = _getFriendlyURL(
						ps2, groupId, friendlyURL, ctCollectionId);

					ps3.setLong(
						1, _layoutLocalService.getNextLayoutId(groupId, false));
					ps3.setBoolean(2, false);
					ps3.setString(3, newfriendlyURL);
					ps3.setLong(4, plid);

					ps3.addBatch();

					ps4.setBoolean(1, false);
					ps4.setString(2, newfriendlyURL);
					ps4.setLong(3, plid);

					ps4.addBatch();
				}

				ps3.executeBatch();

				ps4.executeBatch();
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;
	private final ResourceLocalService _resourceLocalService;

}