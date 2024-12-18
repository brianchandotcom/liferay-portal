/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.dashboard.web.internal.upgrade.v1_0_1;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.dashboard.web.internal.constants.CommerceDashboardPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Michele Vigilante
 */
public class CommerceDashboardForecastsChartPortletPreferencesUpgradeProcess
	extends UpgradeProcess {

	public CommerceDashboardForecastsChartPortletPreferencesUpgradeProcess(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select PortletPreferenceValue.name, ",
						"PortletPreferenceValue.smallValue from ",
						"PortletPreferenceValue inner join PortletPreferences ",
						"on PortletPreferenceValue.portletPreferencesId = ",
						"PortletPreferences.portletPreferencesId where ",
						"PortletPreferences.portletId like '",
						CommerceDashboardPortletKeys.
							COMMERCE_DASHBOARD_FORECASTS_CHART,
						"%' and (PortletPreferenceValue.name = ",
						"'assetCategoryIds')"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferenceValue set name = ?, smallValue " +
						"= ? where name = ? and smallValue = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String name = resultSet.getString(1);
					String smallValues = resultSet.getString(2);

					updatePreparedStatement.setString(
						1, "assetCategoryExternalReferenceCodes");

					String[] assetCategoryIds = smallValues.split(
						StringPool.COMMA);

					String[] assetCategoryExternalReferenceCodes =
						new String[assetCategoryIds.length];

					for (int i = 0; i < assetCategoryIds.length; i++) {
						AssetCategory assetCategory =
							_assetCategoryLocalService.fetchAssetCategory(
								GetterUtil.getLong(assetCategoryIds[i]));

						if (assetCategory == null) {
							updatePreparedStatement.setString(2, null);
						}
						else {
							assetCategoryExternalReferenceCodes[i] =
								assetCategory.getExternalReferenceCode();
						}
					}

					updatePreparedStatement.setString(
						2,
						StringUtil.merge(assetCategoryExternalReferenceCodes));

					updatePreparedStatement.setString(3, name);
					updatePreparedStatement.setString(4, smallValues);

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;

}