/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Gustavo Lima
 */
public class SXPBlueprintAndSXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPElement();

		_upgradeSXPBlueprint();
	}

	private String _fixElementDefinition(String elementDefinition)
		throws Exception {

		if (Validator.isBlank(elementDefinition)) {
			return elementDefinition;
		}

		JSONObject sxpElementJSONObject = JSONFactoryUtil.createJSONObject(
			elementDefinition);

		if (sxpElementJSONObject == null) {
			return elementDefinition;
		}

		JSONObject uiConfigurationJSONObject =
			sxpElementJSONObject.getJSONObject("uiConfiguration");

		if (uiConfigurationJSONObject == null) {
			return elementDefinition;
		}

		JSONArray fieldSetsJSONArray = uiConfigurationJSONObject.getJSONArray(
			"fieldSets");

		if (fieldSetsJSONArray == null) {
			return elementDefinition;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldSetJSONObject = fieldSetsJSONArray.getJSONObject(i);

			JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
				"fields");

			if (fieldsJSONArray == null) {
				continue;
			}

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

				JSONArray defaultValueJSONArray = fieldJSONObject.getJSONArray(
					"defaultValue");

				JSONObject defaultValueJSONObject =
					fieldJSONObject.getJSONObject("defaultValue");

				if (defaultValueJSONArray != null) {
					fieldJSONObject.remove("defaultValue");
					fieldJSONObject.put("fieldMappings", defaultValueJSONArray);
				}
				else if (defaultValueJSONObject != null) {
					fieldJSONObject.put("defaultValue", StringPool.BLANK);
				}
			}
		}

		return sxpElementJSONObject.toString();
	}

	private String _fixElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		if (Validator.isBlank(elementInstancesJSON)) {
			return elementInstancesJSON;
		}

		JSONArray elementInstancesJSONArray = JSONFactoryUtil.createJSONArray(
			elementInstancesJSON);

		for (int i = 0; i < elementInstancesJSONArray.length(); i++) {
			JSONObject elementInstanceJSONObject =
				elementInstancesJSONArray.getJSONObject(i);

			if (elementInstanceJSONObject == null) {
				continue;
			}

			JSONObject sxpElementJSONObject =
				elementInstanceJSONObject.getJSONObject("sxpElement");

			if (sxpElementJSONObject == null) {
				continue;
			}

			JSONObject elementDefinitionJSONObject =
				sxpElementJSONObject.getJSONObject("elementDefinition");

			if (elementDefinitionJSONObject == null) {
				continue;
			}

			sxpElementJSONObject.put(
				"elementDefinition",
				JSONFactoryUtil.createJSONObject(
					_fixElementDefinition(
						elementDefinitionJSONObject.toString())));
		}

		return elementInstancesJSONArray.toString();
	}

	private void _upgradeSXPBlueprint() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpBlueprintId, elementInstancesJSON from " +
					"SXPBlueprint");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ? where " +
						"sxpBlueprintId = ?")) {

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setString(
						1,
						_fixElementInstancesJSON(
							resultSet1.getString("elementInstancesJSON")));
					preparedStatement2.setLong(
						2, resultSet1.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSXPElement() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select elementDefinitionJSON, sxpElementId from SXPElement");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPElement set elementDefinitionJSON = ? where " +
						"sxpElementId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						_fixElementDefinition(
							resultSet.getString("elementDefinitionJSON")));
					preparedStatement2.setLong(
						2, resultSet.getLong("sxpElementId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}