/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_2;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joshua Cords
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	public SXPBlueprintUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPBlueprints();
	}

	private String _updateConfigurationStorage(String configurationJSON)
		throws Exception {

		if (Validator.isBlank(configurationJSON)) {
			return configurationJSON;
		}

		JSONObject configurationjsonObject = JSONFactoryUtil.createJSONObject(
			configurationJSON);

		JSONObject generalConfigurationjsonObject =
			configurationjsonObject.getJSONObject("generalConfiguration");

		JSONArray clauseContributorsExcludesjsonArray =
			generalConfigurationjsonObject.getJSONArray(
				"clauseContributorsExcludes");
		JSONArray clauseContributorsIncludesjsonArray =
			generalConfigurationjsonObject.getJSONArray(
				"clauseContributorsIncludes");

		if (clauseContributorsExcludesjsonArray.length() == 0) {
			generalConfigurationjsonObject.put(
				"clauseContributorsIncludes",
				_jsonFactory.createJSONArray(new String[] {"*"}));
		}
		else if (clauseContributorsIncludesjsonArray.length() == 0) {
			generalConfigurationjsonObject.put(
				"clauseContributorsExcludes",
				_jsonFactory.createJSONArray(new String[] {"*"}));
		}
		else {
			generalConfigurationjsonObject.put(
				"clauseContributorsExcludes", _jsonFactory.createJSONArray());
		}

		return configurationjsonObject.toString();
	}

	private void _upgradeSXPBlueprints() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select configurationJSON,sxpBlueprintId from SXPBlueprint " +
					"where schemaVersion = '1.0'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set configurationJSON = ?," +
						"schemaVersion = ? where sxpBlueprintId = ?")) {

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setString(
						1,
						_updateConfigurationStorage(
							resultSet1.getString("configurationJSON")));
					preparedStatement2.setString(2, "1.1");
					preparedStatement2.setLong(
						3, resultSet1.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final JSONFactory _jsonFactory;

}