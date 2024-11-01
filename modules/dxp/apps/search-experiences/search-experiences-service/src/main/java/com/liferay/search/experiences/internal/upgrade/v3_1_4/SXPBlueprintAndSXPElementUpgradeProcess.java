/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_4;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joshua Cords
 */
public class SXPBlueprintAndSXPElementUpgradeProcess extends UpgradeProcess {

	public SXPBlueprintAndSXPElementUpgradeProcess(
		AssetCategoryLocalService assetCategoryLocalService,
		GroupLocalService groupLocalService, JSONFactory jsonFactory) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_groupLocalService = groupLocalService;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
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
					_upgradeSXPBlueprint(preparedStatement2, resultSet);
				}

				preparedStatement2.executeBatch();
			}
		}

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update SXPElement set elementDefinitionJSON = ? where " +
						"externalReferenceCode = ?")) {

			for (String externalReferenceCode : _EXTERNAL_REFERENCE_CODES) {
				preparedStatement.setString(
					1,
					StringUtil.read(
						getClass(),
						"dependencies/" +
							StringUtil.toLowerCase(externalReferenceCode) +
								".json"));
				preparedStatement.setString(2, externalReferenceCode);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private JSONArray _createExternalReferencesCodesJSONArray(
			long[] assetCategoryIds)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (long assetCategoryId : assetCategoryIds) {
			jsonArray.put(_getExternalReferenceCode(assetCategoryId));
		}

		return jsonArray;
	}

	private JSONObject
			_createGroupAssetCategoryExternalReferenceCodesJSONObject(
				JSONObject assetCategoryIdJSONObject)
		throws Exception {

		long assetCategoryId = assetCategoryIdJSONObject.getLong("value");

		return JSONUtil.put(
			"label", _getLabel(assetCategoryId)
		).put(
			"value", _getExternalReferenceCode(assetCategoryId)
		);
	}

	private String _fixElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		JSONArray elementInstancesJSONArray = _jsonFactory.createJSONArray(
			elementInstancesJSON);

		for (int i = 0; i < elementInstancesJSONArray.length(); i++) {
			JSONObject elementInstanceJSONObject =
				elementInstancesJSONArray.getJSONObject(i);

			JSONObject sxpElementJSONObject =
				elementInstanceJSONObject.getJSONObject("sxpElement");

			String externalReferenceCode = sxpElementJSONObject.getString(
				"externalReferenceCode");

			if (!ArrayUtil.contains(
					_EXTERNAL_REFERENCE_CODES, externalReferenceCode)) {

				continue;
			}

			_upgradeConfigurationEntry(
				elementInstanceJSONObject, externalReferenceCode);
			_upgradeElementDefinition(elementInstanceJSONObject);
			_upgradeUIConfigurationValues(elementInstanceJSONObject);
		}

		return elementInstancesJSONArray.toString();
	}

	private long[] _getAssetCategoryIds(JSONObject termJSONObject) {
		long[] assetCategoryIds = null;

		Object object = JSONUtil.getValue(
			termJSONObject, "Object/assetCategoryIds");

		if (object instanceof JSONArray) {
			assetCategoryIds = JSONUtil.toLongArray((JSONArray)object);
		}
		else {
			assetCategoryIds = new long[1];

			assetCategoryIds[0] = JSONUtil.getValueAsLong(
				object, "Object/value");
		}

		return assetCategoryIds;
	}

	private String _getExternalReferenceCode(long assetCategoryId)
		throws Exception {

		try {
			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(assetCategoryId);

			Group group = _groupLocalService.getGroup(
				assetCategory.getGroupId());

			return group.getExternalReferenceCode() + "&&" +
				assetCategory.getExternalReferenceCode();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get external reference code for asset " +
						"category ID " + assetCategoryId,
					exception);
			}

			throw exception;
		}
	}

	private String _getLabel(long assetCategoryId) throws Exception {
		try {
			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(assetCategoryId);

			return StringBundler.concat(
				assetCategory.getName(), " (ERC: ",
				assetCategory.getExternalReferenceCode(), ")");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get asset category " + assetCategoryId);
			}

			throw exception;
		}
	}

	private boolean _hasAssetCategoryElement(
		ElementInstance[] elementInstances) {

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = elementInstance.getSxpElement();

			if (ArrayUtil.contains(
					_EXTERNAL_REFERENCE_CODES,
					sxpElement.getExternalReferenceCode())) {

				return true;
			}
		}

		return false;
	}

	private void _upgradeConfigurationEntry(
			JSONObject elementInstanceJSONObject, String externalReferenceCode)
		throws Exception {

		JSONObject queryJSONObject = JSONUtil.getValueAsJSONObject(
			elementInstanceJSONObject, "JSONObject/configurationEntry",
			"JSONObject/queryConfiguration", "JSONArray/queryEntries",
			"JSONObject/0", "JSONArray/clauses", "JSONObject/0",
			"JSONObject/query");

		if (externalReferenceCode.startsWith("BOOST_CONTENTS_IN_A_CATEGORY")) {
			_upgradeConfigurationEntryForBoostElements(queryJSONObject);
		}
		else if (externalReferenceCode.startsWith(
					"HIDE_CONTENTS_IN_A_CATEGORY")) {

			_upgradeConfigurationEntryForHideElements(queryJSONObject);
		}
	}

	private void _upgradeConfigurationEntryForBoostElements(
			JSONObject queryJSONObject)
		throws Exception {

		long[] assetCategoryIds = null;
		double boost = 0;

		if (queryJSONObject.has("term")) {
			JSONObject termJSONObject = queryJSONObject.getJSONObject("term");

			queryJSONObject.remove("term");

			JSONObject assetCategoryIdsJSONObject =
				termJSONObject.getJSONObject("assetCategoryIds");

			assetCategoryIds = _getAssetCategoryIds(termJSONObject);
			boost = assetCategoryIdsJSONObject.getDouble("boost");
		}
		else {
			JSONObject termsJSONObject = queryJSONObject.getJSONObject("terms");

			assetCategoryIds = _getAssetCategoryIds(termsJSONObject);
			boost = termsJSONObject.getDouble("boost");
		}

		queryJSONObject.put(
			"terms",
			JSONUtil.put(
				"boost", boost
			).put(
				"groupAssetCategoryExternalReferenceCodes",
				_createExternalReferencesCodesJSONArray(assetCategoryIds)
			));
	}

	private void _upgradeConfigurationEntryForHideElements(
			JSONObject queryJSONObject)
		throws Exception {

		JSONObject mustNotJSONObject = JSONUtil.getValueAsJSONObject(
			queryJSONObject, "JSONObject/bool", "JSONArray/must_not",
			"JSONObject/0");

		mustNotJSONObject.put(
			"terms",
			JSONUtil.put(
				"groupAssetCategoryExternalReferenceCodes",
				_createExternalReferencesCodesJSONArray(
					_getAssetCategoryIds(
						mustNotJSONObject.getJSONObject("term"))))
		).remove(
			"term"
		);
	}

	private void _upgradeElementDefinition(
		JSONObject elementInstanceJSONObject) {

		JSONObject queryJSONObject = JSONUtil.getValueAsJSONObject(
			elementInstanceJSONObject, "JSONObject/sxpElement",
			"JSONObject/elementDefinition", "JSONObject/configuration",
			"JSONObject/queryConfiguration", "JSONArray/queryEntries",
			"JSONObject/0", "JSONArray/clauses", "JSONObject/0",
			"JSONObject/query");

		if (queryJSONObject.has("bool")) {
			JSONArray mustNotJSONArray = JSONUtil.getValueAsJSONArray(
				queryJSONObject, "JSONObject/bool", "JSONArray/must_not");

			for (int i = 0; i < mustNotJSONArray.length(); i++) {
				JSONObject mustNotJSONObject = mustNotJSONArray.getJSONObject(
					i);

				if (!mustNotJSONObject.has("term")) {
					continue;
				}

				mustNotJSONObject.put(
					"terms",
					JSONUtil.put(
						"groupAssetCategoryExternalReferenceCodes",
						"${configuration.group_asset_category_" +
							"external_reference_codes}")
				).remove(
					"term"
				);

				break;
			}
		}
		else {
			queryJSONObject.put(
				"terms",
				JSONUtil.put(
					"boost", "${configuration.boost}"
				).put(
					"groupAssetCategoryExternalReferenceCodes",
					"${configuration." +
						"group_asset_category_external_reference_codes}"
				)
			).remove(
				"term"
			);
		}

		JSONArray fieldsJSONArray = JSONUtil.getValueAsJSONArray(
			elementInstanceJSONObject, "JSONObject/sxpElement",
			"JSONObject/elementDefinition", "JSONObject/uiConfiguration",
			"JSONArray/fieldSets", "JSONObject/0", "JSONArray/fields");

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String fieldName = fieldJSONObject.getString("name");

			if (!fieldName.startsWith("asset_category_id")) {
				continue;
			}

			fieldJSONObject.put(
				"label", "asset-category-external-reference-codes"
			).put(
				"name", "group_asset_category_external_reference_codes"
			).put(
				"type", "multiselect"
			).remove(
				"labelLocalized"
			);

			break;
		}
	}

	private void _upgradeSXPBlueprint(
			PreparedStatement preparedStatement2, ResultSet resultSet)
		throws SQLException {

		try {
			String elementInstancesJSON = resultSet.getString(
				"elementInstancesJSON");

			ElementInstance[] elementInstances =
				ElementInstanceUtil.toElementInstances(elementInstancesJSON);

			if ((elementInstances == null) ||
				!_hasAssetCategoryElement(elementInstances)) {

				return;
			}

			preparedStatement2.setString(
				1, _fixElementInstancesJSON(elementInstancesJSON));
			preparedStatement2.setLong(2, resultSet.getLong("sxpBlueprintId"));

			preparedStatement2.addBatch();
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to upgrade search experiences blueprint " +
						resultSet.getLong("sxpBlueprintId"),
					exception);
			}
		}
	}

	private void _upgradeUIConfigurationValues(
			JSONObject elementInstanceJSONObject)
		throws Exception {

		JSONObject uiConfigurationValuesJSONObject =
			elementInstanceJSONObject.getJSONObject("uiConfigurationValues");

		JSONArray groupAssetCategoryExternalReferenceCodesJSONArray =
			_jsonFactory.createJSONArray();

		if (uiConfigurationValuesJSONObject.has("asset_category_id")) {
			groupAssetCategoryExternalReferenceCodesJSONArray.put(
				_createGroupAssetCategoryExternalReferenceCodesJSONObject(
					uiConfigurationValuesJSONObject.getJSONObject(
						"asset_category_id")));

			uiConfigurationValuesJSONObject.remove("asset_category_id");
		}
		else {
			JSONArray assetCategoryIdsJSONArray =
				uiConfigurationValuesJSONObject.getJSONArray(
					"asset_category_ids");

			for (int i = 0; i < assetCategoryIdsJSONArray.length(); i++) {
				groupAssetCategoryExternalReferenceCodesJSONArray.put(
					_createGroupAssetCategoryExternalReferenceCodesJSONObject(
						assetCategoryIdsJSONArray.getJSONObject(i)));
			}

			uiConfigurationValuesJSONObject.remove("asset_category_ids");
		}

		uiConfigurationValuesJSONObject.put(
			"group_asset_category_external_reference_codes",
			groupAssetCategoryExternalReferenceCodesJSONArray);
	}

	private static final String[] _EXTERNAL_REFERENCE_CODES = {
		"BOOST_CONTENTS_IN_A_CATEGORY",
		"BOOST_CONTENTS_IN_A_CATEGORY_BY_KEYWORD_MATCH",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_PERIOD_OF_TIME",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_NEW_USER_ACCOUNTS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_THE_TIME_OF_DAY",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_USER_SEGMENTS",
		"HIDE_CONTENTS_IN_A_CATEGORY",
		"HIDE_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintAndSXPElementUpgradeProcess.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final GroupLocalService _groupLocalService;
	private final JSONFactory _jsonFactory;

}