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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		StringBundler sb = new StringBundler(2);

		sb.append("update DDMDataProviderInstance set definition = ? where ");
		sb.append("dataProviderInstanceId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMDataProviderInstance");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				ps2.setString(
					1,
					_updateDDMDataProviderInstance(
						rs.getLong("dataProviderInstanceId"),
						rs.getString("definition"), rs.getString("uuid_")));

				ps2.setLong(2, rs.getLong("dataProviderInstanceId"));

				ps2.addBatch();
			}

			ps2.executeBatch();

			_updateDDMStructures();
		}
	}

	private long _extractDDMDataProviderInstanceId(String json) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(json);

			return jsonArray.getLong(0);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return 0;
		}
	}

	private String _extractDDMDataProviderInstanceOutputName(String json) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(json);

			return jsonArray.getString(0);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return StringPool.BLANK;
		}
	}

	private String _updateDDMDataProviderInstance(
			long ddmDataProviderInstanceId, String definition, String uuid)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldValuesJSONArray = definitionJSONObject.getJSONArray(
			"fieldValues");

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			if (StringUtil.equals(
					fieldValueJSONObject.getString("name"),
					"outputParameters")) {

				String outputParameterId = StringUtil.randomString();

				JSONArray nestedFieldValuesJSONArray =
					fieldValueJSONObject.getJSONArray("nestedFieldValues");

				_updateDDMDataProviderInstanceOutputParameters(
					ddmDataProviderInstanceId, nestedFieldValuesJSONArray,
					outputParameterId, uuid);

				nestedFieldValuesJSONArray.put(
					JSONUtil.put(
						"instanceId", StringUtil.randomString()
					).put(
						"name", "outputParameterId"
					).put(
						"value", outputParameterId
					));
			}
		}

		return definitionJSONObject.toString();
	}

	private void _updateDDMDataProviderInstanceOutputParameters(
		long ddmDataProviderInstanceId, JSONArray fieldValuesJSONArray,
		String outputParameterId, String uuid) {

		Map<String, String> ddmDataProviderInstanceOutputParameterValues =
			_ddmDataProviderInstanceOutputParametersInstanceId.get(
				ddmDataProviderInstanceId);

		if (ddmDataProviderInstanceOutputParameterValues == null) {
			ddmDataProviderInstanceOutputParameterValues = new HashMap<>();

			_ddmDataProviderInstanceOutputParametersInstanceId.put(
				ddmDataProviderInstanceId,
				ddmDataProviderInstanceOutputParameterValues);

			_ddmDataProviderInstanceOutputParametersUUID.put(
				uuid, ddmDataProviderInstanceOutputParameterValues);
		}

		Map<String, JSONObject> outputParameters = JSONUtil.toJSONObjectMap(
			fieldValuesJSONArray, "name");

		JSONObject jsonObject = outputParameters.get("outputParameterName");

		if (jsonObject != null) {
			ddmDataProviderInstanceOutputParameterValues.put(
				jsonObject.getString("value"), outputParameterId);
		}
	}

	private boolean _updateDDMDataProviderRules(JSONArray rulesJSONArray) {
		boolean updated = false;

		for (int i = 0; i < rulesJSONArray.length(); i++) {
			JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(i);

			JSONArray newActionsJSONArray = _jsonFactory.createJSONArray();

			List<String> actions = JSONUtil.toStringList(
				ruleJSONObject.getJSONArray("actions"));

			for (String action : actions) {
				if (action.startsWith("call")) {
					action = action.substring(6, action.length() - 2);

					String[] arguments = StringUtil.split(action, "', '");

					Map<String, String> ddmDataProviderOutputParameters =
						_ddmDataProviderInstanceOutputParametersUUID.get(
							arguments[0]);

					String[] actionOutputs = StringUtil.split(
						arguments[2], CharPool.SEMICOLON);

					String newActionOutputs = "";

					for (String actionOutput : actionOutputs) {
						String[] actionOutputParts = StringUtil.split(
							actionOutput, CharPool.EQUAL);

						newActionOutputs = StringBundler.concat(
							newActionOutputs, actionOutputParts[0],
							CharPool.EQUAL,
							ddmDataProviderOutputParameters.get(
								actionOutputParts[1]),
							CharPool.SEMICOLON);
					}

					newActionOutputs = newActionOutputs.substring(
						0, newActionOutputs.length() - 1);

					action = StringBundler.concat(
						"call('", arguments[0], "', '", arguments[1], "', '",
						newActionOutputs, "')");

					updated = true;
				}

				newActionsJSONArray.put(action);
			}

			ruleJSONObject.put("actions", newActionsJSONArray);
		}

		return updated;
	}

	private void _updateDDMStructures() throws Exception {
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
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				rs3.getString("definition"));

			boolean rulesUpdated = false;

			JSONArray rulesJSONArray = jsonObject.getJSONArray("rules");

			if (rulesJSONArray != null) {
				rulesUpdated = _updateDDMDataProviderRules(rulesJSONArray);
			}

			boolean fieldsUpdated = false;

			JSONArray fieldsJSONArray = jsonObject.getJSONArray("fields");

			if (fieldsJSONArray != null) {
				fieldsUpdated = _updateFieldsWithDataProviderAssigned(
					fieldsJSONArray);
			}

			long structureVersionId = rs3.getLong("structureVersionId");

			if ((rulesUpdated || fieldsUpdated) &&
				!_updatedStructureVersionIds.contains(structureVersionId)) {

				ps2.setString(1, jsonObject.toString());

				ps2.setLong(2, structureVersionId);

				ps2.addBatch();

				_updatedStructureVersionIds.add(structureVersionId);
			}

			long structureId = rs3.getLong("structureId");

			if (rs3.getBoolean("updateStructure") &&
				!_updatedStructureIds.contains(structureId)) {

				ps1.setString(1, jsonObject.toString());

				ps1.setLong(2, structureId);

				ps1.addBatch();

				_updatedStructureIds.add(structureId);
			}
		}

		ps2.executeBatch();
		ps1.executeBatch();
	}

	private boolean _updateFieldsWithDataProviderAssigned(
		JSONArray fieldsJSONArray) {

		boolean updated = false;

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if (!StringUtil.equals(
					fieldJSONObject.getString("dataSourceType"),
					"data-provider")) {

				continue;
			}

			long ddmDataProviderInstanceId = _extractDDMDataProviderInstanceId(
				fieldJSONObject.getString("ddmDataProviderInstanceId"));

			if (_ddmDataProviderInstanceOutputParametersInstanceId.containsKey(
					ddmDataProviderInstanceId)) {

				String ddmDataProviderInstanceOutputName =
					_extractDDMDataProviderInstanceOutputName(
						fieldJSONObject.getString(
							"ddmDataProviderInstanceOutput"));

				Map<String, String> ddmDataProviderOutputParameters =
					_ddmDataProviderInstanceOutputParametersInstanceId.get(
						ddmDataProviderInstanceId);

				String outputParameterId = ddmDataProviderOutputParameters.get(
					ddmDataProviderInstanceOutputName);

				fieldJSONObject.put(
					"ddmDataProviderInstanceOutput",
					"[\"" + outputParameterId + "\"]");

				updated = true;
			}
		}

		return updated;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDataProviderOutputParameters.class);

	private Map<Long, Map<String, String>>
		_ddmDataProviderInstanceOutputParametersInstanceId = new HashMap<>();
	private Map<String, Map<String, String>>
		_ddmDataProviderInstanceOutputParametersUUID = new HashMap<>();
	private final JSONFactory _jsonFactory;
	private List<Long> _updatedStructureIds = new ArrayList<>();
	private List<Long> _updatedStructureVersionIds = new ArrayList<>();

}