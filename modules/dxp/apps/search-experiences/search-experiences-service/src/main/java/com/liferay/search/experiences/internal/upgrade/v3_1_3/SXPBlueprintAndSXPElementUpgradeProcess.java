/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Objects;

/**
 * @author Joshua Cords, Felipe Lorenz
 */
public class SXPBlueprintAndSXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPBlueprints();
		_upgradeSXPElement();
	}

	private JSONObject _createScopeGroupExternalReferenceCodesJSONObject(
			JSONObject scopeGroupIDJSONObject)
		throws Exception {

		long groupId = scopeGroupIDJSONObject.getLong("value");

		Group group = _getGroup(groupId);

		return JSONUtil.put(
			"label",
			StringBundler.concat(
				group.getDescriptiveName(), " (ERC: ",
				group.getExternalReferenceCode(), ")")
		).put(
			"value", group.getExternalReferenceCode()
		);
	}

	private long[] _extractScopeGroupIds(JSONObject termsJSONObject) {
		JSONArray scopeGroupIdJSONArray = JSONUtil.getValueAsJSONArray(
			termsJSONObject, "JSONArray/scopeGroupId");

		return JSONUtil.toLongArray(scopeGroupIdJSONArray);
	}

	private String _fixElementInstancesJSON(String elementInstanceJSON)
		throws Exception {

		if (Validator.isBlank(elementInstanceJSON)) {
			return elementInstanceJSON;
		}

		JSONArray elementInstanceJSONArray = JSONFactoryUtil.createJSONArray(
			elementInstanceJSON);

		for (int i = 0; i < elementInstanceJSONArray.length(); i++) {
			JSONObject elementInstanceJSONObject =
				elementInstanceJSONArray.getJSONObject(i);

			JSONObject sxpElementJSONObject =
				elementInstanceJSONObject.getJSONObject("sxpElement");

			if (sxpElementJSONObject == null) {
				continue;
			}

			String sxpElementExternalReferenceCode =
				sxpElementJSONObject.getString("externalReferenceCode");

			if (!Objects.equals(
					sxpElementExternalReferenceCode,
					"LIMIT_SEARCH_TO_THESE_SITES")) {

				continue;
			}

			_upgradeConfigurationEntry(elementInstanceJSONObject);

			_upgradeSXPElement(sxpElementJSONObject);

			_upgradeUIConfigurationValues(elementInstanceJSONObject);
		}

		return elementInstanceJSONArray.toString();
	}

	private Group _getGroup(long groupId) throws Exception {
		try {
			return GroupLocalServiceUtil.getGroup(groupId);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to find group with id " + groupId);
			}

			throw exception;
		}
	}

	private boolean _hasLimitSearchToTheseSites(
		ElementInstance[] elementInstances) {

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = elementInstance.getSxpElement();

			if (Objects.equals(
					sxpElement.getExternalReferenceCode(),
					"LIMIT_SEARCH_TO_THESE_SITES")) {

				return true;
			}
		}

		return false;
	}

	private JSONArray _translateIdsToExternalReferencesCodes(long[] groupIds)
		throws Exception {

		JSONArray scopeGroupExternalReferenceCodeJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (long groupId : groupIds) {
			Group group = _getGroup(groupId);

			scopeGroupExternalReferenceCodeJSONArray.put(
				group.getExternalReferenceCode());
		}

		return scopeGroupExternalReferenceCodeJSONArray;
	}

	private void _upgradeConfiguration(JSONObject configurationJSONObject) {
		JSONObject queryJSONObject = JSONUtil.getValueAsJSONObject(
			configurationJSONObject, "JSONObject/queryConfiguration",
			"JSONArray/queryEntries", "JSONObject/0", "JSONArray/clauses",
			"JSONObject/0", "JSONObject/query");

		JSONObject termsJSONObject = queryJSONObject.getJSONObject("terms");

		termsJSONObject.put(
			"scopeGroupExternalReferenceCode",
			"${configuration.scope_group_external_reference_codes}");
		termsJSONObject.remove("scopeGroupId");
	}

	private void _upgradeConfigurationEntry(
			JSONObject elementInstanceJSONObject)
		throws Exception {

		JSONObject queryJSONObject = JSONUtil.getValueAsJSONObject(
			elementInstanceJSONObject, "JSONObject/configurationEntry",
			"JSONObject/queryConfiguration", "JSONArray/queryEntries",
			"JSONObject/0", "JSONArray/clauses", "JSONObject/0",
			"JSONObject/query");

		JSONObject termsJSONObject = queryJSONObject.getJSONObject("terms");

		long[] groupIds = _extractScopeGroupIds(termsJSONObject);

		queryJSONObject.put(
			"terms",
			JSONUtil.put(
				"scopeGroupExternalReferenceCode",
				_translateIdsToExternalReferencesCodes(groupIds)));
	}

	private void _upgradeSXPBlueprints() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpBlueprintId, elementInstancesJSON from " +
					"SXPBlueprint");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ? where " +
						"sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					_upgradeSXPElementsInSXPBlueprint(
						preparedStatement2, resultSet);
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSXPElement() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SXPElement set elementDefinitionJSON = ? where " +
					"externalReferenceCode = 'LIMIT_SEARCH_TO_THESE_SITES'")) {

			preparedStatement.setString(
				1,
				StringUtil.read(
					getClass(),
					"dependencies/limit_search_to_these_sites.json"));

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeSXPElement(JSONObject sxpElementJSONObject) {
		JSONObject elementDefinitionJSONObject =
			sxpElementJSONObject.getJSONObject("elementDefinition");

		_upgradeConfiguration(
			elementDefinitionJSONObject.getJSONObject("configuration"));

		_upgradeUIConfiguration(
			elementDefinitionJSONObject.getJSONObject("uiConfiguration"));
	}

	private void _upgradeSXPElementsInSXPBlueprint(
			PreparedStatement preparedStatement2, ResultSet resultSet)
		throws SQLException {

		String elementInstancesJSON = resultSet.getString(
			"elementInstancesJSON");

		ElementInstance[] elementInstances =
			ElementInstanceUtil.toElementInstances(elementInstancesJSON);

		if ((elementInstances == null) ||
			!_hasLimitSearchToTheseSites(elementInstances)) {

			return;
		}

		try {
			preparedStatement2.setString(
				1, _fixElementInstancesJSON(elementInstancesJSON));

			preparedStatement2.setLong(2, resultSet.getLong("sxpBlueprintId"));

			preparedStatement2.addBatch();
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to upgrade SXPBlueprint " +
						resultSet.getLong("sxpBlueprintId"),
					exception);
			}
		}
	}

	private void _upgradeUIConfiguration(JSONObject uiConfigurationJSONObject) {
		JSONArray fieldsJSONArray = JSONUtil.getValueAsJSONArray(
			uiConfigurationJSONObject, "JSONArray/fieldSets", "JSONObject/0",
			"JSONArray/fields");

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			fieldJSONObject.put(
				"helpText", "scope-group-external-reference-codes-help"
			).put(
				"label", "scope-group-external-reference-codes"
			).put(
				"name", "scope_group_external_reference_codes"
			).remove(
				"helpTextLocalized"
			);

			fieldJSONObject.remove("labelLocalized");
		}
	}

	private void _upgradeUIConfigurationValues(JSONObject jsonObject1)
		throws Exception {

		JSONObject uiConfigurationValuesJSONObject = jsonObject1.getJSONObject(
			"uiConfigurationValues");

		JSONArray scopeGroupIdsJSONArray =
			uiConfigurationValuesJSONObject.getJSONArray("scope_group_ids");

		if (scopeGroupIdsJSONArray == null) {
			return;
		}

		JSONArray groupIdsExternalReferenceCodesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < scopeGroupIdsJSONArray.length(); i++) {
			JSONObject scopeGroupIDJSONObject =
				scopeGroupIdsJSONArray.getJSONObject(i);

			groupIdsExternalReferenceCodesJSONArray.put(
				_createScopeGroupExternalReferenceCodesJSONObject(
					scopeGroupIDJSONObject));
		}

		uiConfigurationValuesJSONObject.put(
			"scope_group_external_reference_codes",
			groupIdsExternalReferenceCodesJSONArray
		).remove(
			"scope_group_ids"
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintAndSXPElementUpgradeProcess.class);

}