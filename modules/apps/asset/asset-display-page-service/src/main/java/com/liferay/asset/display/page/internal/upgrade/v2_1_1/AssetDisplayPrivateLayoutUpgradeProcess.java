/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.upgrade.v2_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
public class AssetDisplayPrivateLayoutUpgradeProcess extends UpgradeProcess {

	public AssetDisplayPrivateLayoutUpgradeProcess(
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
			PreparedStatement preparedStatement, long ctCollectionId,
			long groupId, String friendlyURL)
		throws SQLException {

		String initialFriendlyURL = friendlyURL;

		preparedStatement.setLong(1, ctCollectionId);
		preparedStatement.setLong(2, groupId);
		preparedStatement.setBoolean(3, false);
		preparedStatement.setString(4, friendlyURL);

		for (int i = 1;; i++) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					friendlyURL = initialFriendlyURL + StringPool.DASH + i;

					preparedStatement.setString(4, friendlyURL);
				}
				else {
					break;
				}
			}
		}

		return friendlyURL;
	}

	private void _upgradeAssetDisplayLayouts() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, groupId, friendlyURL, plid from " +
					"Layout where privateLayout = ? and type_ = ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select plid from Layout where ctCollectionId = ? and " +
					"groupId = ? and privateLayout = ? and friendlyURL = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set privateLayout = ?, layoutId = ?, " +
						"friendlyURL = ? where plid = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutFriendlyURL set privateLayout = ?, " +
						"friendlyURL = ? where plid = ?")) {

			preparedStatement1.setBoolean(1, true);
			preparedStatement1.setString(2, LayoutConstants.TYPE_ASSET_DISPLAY);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long ctCollectionId = resultSet.getLong("ctCollectionId");
					long groupId = resultSet.getLong("groupId");
					String friendlyURL = resultSet.getString("friendlyURL");
					long plid = resultSet.getLong("plid");

					_addResources(groupId, plid);

					String newFriendlyURL = _getFriendlyURL(
						preparedStatement2, ctCollectionId, groupId,
						friendlyURL);

					if (!newFriendlyURL.equals(friendlyURL)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Friendly URL for PLID ", plid,
									" in group ", groupId, " changed from ",
									friendlyURL, " to ", newFriendlyURL));
						}
					}

					preparedStatement3.setBoolean(1, false);
					preparedStatement3.setLong(
						2, _layoutLocalService.getNextLayoutId(groupId, false));
					preparedStatement3.setString(3, newFriendlyURL);
					preparedStatement3.setLong(4, plid);

					preparedStatement3.addBatch();

					preparedStatement4.setBoolean(1, false);
					preparedStatement4.setString(2, newFriendlyURL);
					preparedStatement4.setLong(3, plid);

					preparedStatement4.addBatch();
				}

				preparedStatement3.executeBatch();

				preparedStatement4.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPrivateLayoutUpgradeProcess.class);

	private final LayoutLocalService _layoutLocalService;
	private final ResourceLocalService _resourceLocalService;

}