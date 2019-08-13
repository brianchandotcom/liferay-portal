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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_3;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rodrigo Paulino
 */
public class UpgradeDataProviderOutputParameters extends UpgradeProcess {

	public UpgradeDataProviderOutputParameters(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(1);

		sb1.append("select * from DDMDataProviderInstance");

		StringBundler sb2 = new StringBundler(2);

		sb2.append("update DDMDataProviderInstance set definition = ? where ");
		sb2.append("dataProviderInstanceId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String definition = rs.getString("definition");

				long dataProviderInstanceId = rs.getLong(
					"dataProviderInstanceId");

				String uuid = rs.getString("uuid_");

				definition = updateDataProviderDefinition(
					uuid, dataProviderInstanceId, definition);

				ps2.setString(1, definition);

				ps2.setLong(2, dataProviderInstanceId);

				ps2.addBatch();
			}

			ps2.executeBatch();

			updateStructures();
		}
	}

	protected Long getJSONArrayFirstLongValue(String ddmDataProviderInstanceId)
		throws JSONException {

		JSONArray jsonArray2 = _jsonFactory.createJSONArray(
			ddmDataProviderInstanceId);

		return jsonArray2.getLong(0);
	}

	protected String getJSONArrayFirstStringValue(
			String ddmDataProviderInstanceOutput)
		throws JSONException {

		JSONArray jsonArray2 = _jsonFactory.createJSONArray(
			ddmDataProviderInstanceOutput);

		ddmDataProviderInstanceOutput = jsonArray2.getString(0);

		return ddmDataProviderInstanceOutput;
	}

	protected String updateDataProviderDefinition(
			String uuid, Long dataProviderInstanceId, String definition)
		throws JSONException {

		JSONObject jsonObject1 = _jsonFactory.createJSONObject(definition);

		JSONArray jsonArray1 = jsonObject1.getJSONArray("fieldValues");

		String instanceId = StringUtil.randomString();

		for (int i = 0; i < jsonArray1.length(); i++) {
			JSONObject jsonObject2 = jsonArray1.getJSONObject(i);

			String name1 = jsonObject2.getString("name");

			if (name1.equals("outputParameters")) {
				String outputParameterId = StringUtil.randomString();

				JSONObject jsonObject3 = JSONUtil.put(
					"instanceId", instanceId
				).put(
					"name", "outputParameterId"
				).put(
					"value", outputParameterId
				);

				JSONArray jsonArray2 = jsonObject2.getJSONArray(
					"nestedFieldValues");

				updateOutputParametersMaps(
					uuid, dataProviderInstanceId, outputParameterId,
					jsonArray2);

				jsonArray2.put(jsonObject3);
			}
		}

		return jsonObject1.toString();
	}

	protected void updateOutputParametersMaps(
		String uuid, Long dataProviderInstanceId, String outputParameterId,
		JSONArray jsonArray) {

		Map<String, JSONObject> outputParameterMap = JSONUtil.toJSONObjectMap(
			jsonArray, "name");

		JSONObject jsonObject = outputParameterMap.get("outputParameterName");

		String value = jsonObject.getString("value");

		Map<String, String> dataProviderOutputParametersMap =
			_instanceIdOutputParametersMap.get(dataProviderInstanceId);

		if (dataProviderOutputParametersMap == null) {
			dataProviderOutputParametersMap = new HashMap<>();

			_instanceIdOutputParametersMap.put(
				dataProviderInstanceId, dataProviderOutputParametersMap);

			_uuidOutputParametersMap.put(uuid, dataProviderOutputParametersMap);
		}

		dataProviderOutputParametersMap.put(value, outputParameterId);
	}

	protected void updateStructures() throws Exception {
		StringBundler sb1 = new StringBundler(2);

		sb1.append("update DDMStructure set definition = ? where structureId ");
		sb1.append("= ?");

		PreparedStatement ps1 =
			AutoBatchPreparedStatementUtil.concurrentAutoBatch(
				connection, sb1.toString());

		StringBundler sb2 = new StringBundler(2);

		sb2.append("update DDMStructureVersion set definition = ? where ");
		sb2.append("structureVersionId = ?");

		PreparedStatement ps2 =
			AutoBatchPreparedStatementUtil.concurrentAutoBatch(
				connection, sb2.toString());

		StringBundler sb3 = new StringBundler(10);

		sb3.append("select structureVersionId, DDMStructureVersion.");
		sb3.append("structureId, DDMStructureVersion.definition, case when ");
		sb3.append("DDMStructure.structureId is not null then true else ");
		sb3.append("false end as updateStructure from ");
		sb3.append("DDMDataProviderInstanceLink join DDMStructureVersion on ");
		sb3.append("DDMStructureVersion.structureId = ");
		sb3.append("DDMDataProviderInstanceLink.structureId left join ");
		sb3.append("DDMStructure on DDMStructure.structureId = ");
		sb3.append("DDMDataProviderInstanceLink.structureId and ");
		sb3.append("DDMStructure.version = DDMStructureVersion.version");

		PreparedStatement ps3 = connection.prepareStatement(sb3.toString());

		ResultSet rs3 = ps3.executeQuery();

		while (rs3.next()) {
			String definition = rs3.getString("definition");
			long structureId = rs3.getLong("structureId");
			long structureVersionId = rs3.getLong("structureVersionId");
			boolean updateStructure = rs3.getBoolean("updateStructure");

			JSONObject jsonObject = _jsonFactory.createJSONObject(definition);

			boolean updateStructureVersion = false;

			JSONArray rules = jsonObject.getJSONArray("rules");

			if (rules != null) {
				updateStructureVersion = updateStructureVersionRules(rules);
			}

			JSONArray fields = jsonObject.getJSONArray("fields");

			if (fields != null) {
				updateStructureVersion =
					updateStructureVersionFields(fields) ||
					updateStructureVersion;
			}

			if (updateStructureVersion &&
				!_updatedStructureVersions.contains(structureVersionId)) {

				definition = jsonObject.toString();

				ps2.setString(1, definition);

				ps2.setLong(2, structureVersionId);

				ps2.addBatch();

				_updatedStructureVersions.add(structureVersionId);
			}

			if (updateStructure && !_updatedStructures.contains(structureId)) {
				ps1.setString(1, definition);

				ps1.setLong(2, structureId);

				ps1.addBatch();

				_updatedStructures.add(structureId);
			}
		}

		ps2.executeBatch();
		ps1.executeBatch();
	}

	protected boolean updateStructureVersionFields(JSONArray jsonArray)
		throws JSONException {

		boolean updateStructureVersion = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String dataSourceType = jsonObject.getString("dataSourceType");

			if (dataSourceType.equals("data-provider")) {
				Long dataProviderInstanceId = getJSONArrayFirstLongValue(
					jsonObject.getString("ddmDataProviderInstanceId"));

				if (_instanceIdOutputParametersMap.containsKey(
						dataProviderInstanceId)) {

					String ddmDataProviderInstanceOutput =
						getJSONArrayFirstStringValue(
							jsonObject.getString(
								"ddmDataProviderInstanceOutput"));

					Map<String, String> dataProviderOutputParametersMap =
						_instanceIdOutputParametersMap.get(
							dataProviderInstanceId);

					String outputParameterId =
						dataProviderOutputParametersMap.get(
							ddmDataProviderInstanceOutput);

					jsonObject.put(
						"ddmDataProviderInstanceOutput",
						"[\"" + outputParameterId + "\"]");

					updateStructureVersion = true;
				}
			}
		}

		return updateStructureVersion;
	}

	protected boolean updateStructureVersionRules(JSONArray jsonArray) {
		boolean updateStructureVersion = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray actions = jsonObject.getJSONArray("actions");

			JSONArray newActions = _jsonFactory.createJSONArray();

			for (int j = 0; j < actions.length(); j++) {
				String action = actions.getString(j);

				if (action.startsWith("call")) {
					action = action.substring(6, action.length() - 2);

					String[] arguments = StringUtil.split(action, "', '");

					Map<String, String> dataProviderOutputParametersMap =
						_uuidOutputParametersMap.get(arguments[0]);

					String[] actionOutputs = StringUtil.split(
						arguments[2], CharPool.SEMICOLON);

					String newActionOutputs = "";

					for (String actionOutput : actionOutputs) {
						String[] actionOutputParts = StringUtil.split(
							actionOutput, CharPool.EQUAL);

						String outputParameterId =
							dataProviderOutputParametersMap.get(
								actionOutputParts[1]);

						newActionOutputs = StringBundler.concat(
							newActionOutputs, actionOutputParts[0],
							CharPool.EQUAL, outputParameterId,
							CharPool.SEMICOLON);
					}

					newActionOutputs = newActionOutputs.substring(
						0, newActionOutputs.length() - 1);

					action = StringBundler.concat(
						"call('", arguments[0], "', '", arguments[1], "', '",
						newActionOutputs, "')");

					updateStructureVersion = true;
				}

				newActions.put(action);
			}

			jsonObject.put("actions", newActions);
		}

		return updateStructureVersion;
	}

	private Map<Long, Map<String, String>> _instanceIdOutputParametersMap =
		new HashMap<>();
	private final JSONFactory _jsonFactory;
	private List<Long> _updatedStructures = new ArrayList<>();
	private List<Long> _updatedStructureVersions = new ArrayList<>();
	private Map<String, Map<String, String>> _uuidOutputParametersMap =
		new HashMap<>();

}