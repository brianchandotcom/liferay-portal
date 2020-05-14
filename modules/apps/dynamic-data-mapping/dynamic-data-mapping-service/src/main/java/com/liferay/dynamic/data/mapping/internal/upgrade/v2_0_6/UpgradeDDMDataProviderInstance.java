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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_6;

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
public class UpgradeDDMDataProviderInstance extends UpgradeProcess {

	public UpgradeDDMDataProviderInstance(JSONFactory jsonFactory) {
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
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return 0;
		}
	}

	private String _extractDDMDataProviderInstanceOutputName(String json) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(json);

			return jsonArray.getString(0);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	private String _updateDDMDataProviderInstance(
			long ddmDataProviderInstanceId, String definition, String uuid)
		throws JSONException {

		String instanceId = StringUtil.randomString();

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
						"instanceId", instanceId
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
					String[] arguments = StringUtil.split(action, "', '");

					String uuid = arguments[0].substring(6);

					Map<String, String> ddmDataProviderOutputParameters =
						_ddmDataProviderInstanceOutputParametersUUID.get(uuid);

					String actionOutputs = arguments[2].substring(
						0, arguments[2].length() - 2);

					String[] separetedActionOutputs = StringUtil.split(
						actionOutputs, CharPool.SEMICOLON);

					String newActionOutputs = "";

					for (String actionOutput : separetedActionOutputs) {
						String[] actionOutputParts = StringUtil.split(
							actionOutput, CharPool.EQUAL);

						newActionOutputs = StringBundler.concat(
							newActionOutputs, actionOutputParts[0],
							CharPool.EQUAL,
							ddmDataProviderOutputParameters.get(
								actionOutputParts[1]),
							CharPool.SEMICOLON);
					}

					if (newActionOutputs.length() > 0) {
						newActionOutputs = newActionOutputs.substring(
							0, newActionOutputs.length() - 1);
					}

					action = StringBundler.concat(
						"call('", uuid, "', '", arguments[1], "', '",
						newActionOutputs, "')");

					updated =
						updated || !newActionOutputs.equals(actionOutputs);
				}

				newActionsJSONArray.put(action);
			}

			ruleJSONObject.put("actions", newActionsJSONArray);
		}

		return updated;
	}

	private boolean _updateDDMStructure(JSONObject jsonObject) {
		boolean updatedRules = false;

		JSONArray rulesJSONArray = jsonObject.getJSONArray("rules");

		if (rulesJSONArray != null) {
			updatedRules = _updateDDMDataProviderRules(rulesJSONArray);
		}

		boolean updatedFields = false;

		JSONArray fieldsJSONArray = jsonObject.getJSONArray("fields");

		if (fieldsJSONArray != null) {
			updatedFields = _updateFieldsWithDataProviderAssigned(
				fieldsJSONArray);
		}

		if (updatedRules || updatedFields) {
			return true;
		}

		return false;
	}

	private void _updateDDMStructures() throws Exception {
		StringBundler sb = new StringBundler(7);

		sb.append("select DDMStructure.structureId, DDMStructure.definition ");
		sb.append("from DDMDataProviderInstanceLink join DDMStructureVersion ");
		sb.append("on DDMStructureVersion.structureId = ");
		sb.append("DDMDataProviderInstanceLink.structureId left join ");
		sb.append("DDMStructure on DDMStructure.structureId = ");
		sb.append("DDMDataProviderInstanceLink.structureId and ");
		sb.append("DDMStructure.version = DDMStructureVersion.version");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(
						rs.getString(2));

					boolean updated = _updateDDMStructure(jsonObject);

					long structureId = rs.getLong(1);

					if (updated &&
						!_updatedStructureIds.contains(structureId)) {

						ps2.setString(1, jsonObject.toString());

						ps2.setLong(2, structureId);

						ps2.addBatch();

						_updatedStructureIds.add(structureId);
					}

					ps3.setLong(1, structureId);

					try (ResultSet rs2 = ps3.executeQuery()) {
						while (rs2.next()) {
							jsonObject = _jsonFactory.createJSONObject(
								rs2.getString("definition"));

							updated = _updateDDMStructure(jsonObject);

							long structureVersionId = rs2.getLong(
								"structureVersionId");

							if (updated &&
								!_updatedStructureVersionIds.contains(
									structureVersionId)) {

								ps4.setString(1, jsonObject.toString());

								ps4.setLong(2, structureVersionId);

								ps4.addBatch();

								_updatedStructureVersionIds.add(
									structureVersionId);
							}
						}
					}
				}

				ps2.executeBatch();

				ps4.executeBatch();
			}
		}
	}

	private boolean _updateFieldsWithDataProviderAssigned(
		JSONArray fieldsJSONArray) {

		boolean updated = false;

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if (!StringUtil.equals(
					fieldJSONObject.getString("dataSourceType"),
					"[\"data-provider\"]")) {

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
		UpgradeDDMDataProviderInstance.class);

	private Map<Long, Map<String, String>>
		_ddmDataProviderInstanceOutputParametersInstanceId = new HashMap<>();
	private Map<String, Map<String, String>>
		_ddmDataProviderInstanceOutputParametersUUID = new HashMap<>();
	private final JSONFactory _jsonFactory;
	private List<Long> _updatedStructureIds = new ArrayList<>();
	private List<Long> _updatedStructureVersionIds = new ArrayList<>();

}