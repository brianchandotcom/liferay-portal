/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_2_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joshua Cords
 */
public class SXPBlueprintCollectionProviderUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId from PortalPreferenceValue where key_ = " +
					"'LPS-129412' and smallValue = 'true'")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					_upgradeSXPBlueprints(resultSet.getLong(1));
				}
			}
		}
	}

	private String _updateConfigurationStorage(
			String configurationJSON, long blueprintId)
		throws Exception {

		if (Validator.isBlank(configurationJSON)) {
			return configurationJSON;
		}

		try {
			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configurationJSON);

			JSONObject generalConfigurationJSONObject =
				configurationJSONObject.getJSONObject("generalConfiguration");

			if (generalConfigurationJSONObject == null) {
				return configurationJSON;
			}

			generalConfigurationJSONObject.put(
				"collectionProvider", true
			).put(
				"collectionProviderType", AssetEntry.class.getName()
			).put(
				"legacyAssetCollectionProvider", true
			);

			return configurationJSONObject.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to upgrade blueprint " + blueprintId, exception);
			}

			return configurationJSON;
		}
	}

	private void _upgradeSXPBlueprints(long companyId) throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat(
				"select configurationJSON, sxpBlueprintId from SXPBlueprint ",
				"where companyId = ", companyId));

			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set configurationJSON = ? where " +
						"sxpBlueprintId = ?")) {

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setString(
						1,
						_updateConfigurationStorage(
							resultSet1.getString("configurationJSON"),
							resultSet1.getLong("sxpBlueprintId")));
					preparedStatement2.setLong(
						2, resultSet1.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintCollectionProviderUpgradeProcess.class);

}