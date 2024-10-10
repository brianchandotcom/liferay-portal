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
		_upgradeSXPBlueprints();
		_upgradeSXPElements();
	}

	private boolean _containsCategoryElement(
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

	private long[] _extractAssetCategoryIds(JSONObject termJSONObject) {
		long[] assetCategoryIds;

		JSONArray assetCategoryJSONArray = termJSONObject.getJSONArray(
			"assetCategoryIds");

		if (assetCategoryJSONArray == null) {
			JSONObject valueJSONObject = termJSONObject.getJSONObject(
				"assetCategoryIds");

			assetCategoryIds = new long[1];

			assetCategoryIds[0] = valueJSONObject.getLong("value");
		}
		else {
			assetCategoryIds = new long[assetCategoryJSONArray.length()];

			for (int k = 0; k < assetCategoryJSONArray.length(); k++) {
				assetCategoryIds[k] = assetCategoryJSONArray.getLong(k);
			}
		}

		return assetCategoryIds;
	}

	private String _getElementDefinitionJSON(String externalReferenceCode) {
		return StringUtil.read(
			getClass(),
			"dependencies/" + StringUtil.toLowerCase(externalReferenceCode) +
				".json");
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
			_log.error(
				"Unable to find assetCategory with id " + assetCategoryId);

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
			_log.error(
				"Unable to find assetCategory associated with " +
					assetCategoryId);

			throw exception;
		}
	}

	private JSONArray _translateIdsToExternalReferencesCodes(
			long[] assetCategoryIds)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (long assetCategoryId : assetCategoryIds) {
			jsonArray.put(_getExternalReferenceCode(assetCategoryId));
		}

		return jsonArray;
	}

	private void _upgradeConfiguration(JSONObject configurationJSONObject) {
		JSONObject queryConfigurationJSONObject =
			configurationJSONObject.getJSONObject("queryConfiguration");

		JSONArray queryEntriesJSONArray =
			queryConfigurationJSONObject.getJSONArray("queryEntries");

		for (int i = 0; i < queryEntriesJSONArray.length(); i++) {
			JSONObject queryEntryJSONObject =
				queryEntriesJSONArray.getJSONObject(i);

			JSONArray clausesJSONArray = queryEntryJSONObject.getJSONArray(
				"clauses");

			for (int j = 0; j < clausesJSONArray.length(); j++) {
				JSONObject clauseJSONObject = clausesJSONArray.getJSONObject(i);

				JSONObject queryJSONObject = clauseJSONObject.getJSONObject(
					"query");

				if (queryJSONObject.has("bool")) {
					JSONObject boolJSONObject = queryJSONObject.getJSONObject(
						"bool");

					JSONArray mustNotJSONArray = boolJSONObject.getJSONArray(
						"must_not");

					for (int k = 0; k < mustNotJSONArray.length(); k++) {
						JSONObject mustNotJSONObject =
							mustNotJSONArray.getJSONObject(k);

						if (!mustNotJSONObject.has("term")) {
							continue;
						}

						mustNotJSONObject.remove("term");

						mustNotJSONObject.put(
							"terms",
							JSONUtil.put(
								"groupAssetCategoryExternalReferenceCodes",
								"${configuration.group_asset_category_" +
									"external_reference_codes}"));

						break;
					}
				}
				else {
					queryJSONObject.remove("term");

					queryJSONObject.put(
						"terms",
						JSONUtil.put(
							"boost", "${configuration.boost}"
						).put(
							"groupAssetCategoryExternalReferenceCodes",
							"${configuration." +
								"group_asset_category_external_reference_codes}"
						));
				}
			}
		}
	}

	private void _upgradeConfigurationEntry(
			String externalReferenceCode,
			JSONObject configurationEntryJSONObject)
		throws Exception {

		JSONObject queryConfigurationEntryJSONObject =
			configurationEntryJSONObject.getJSONObject("queryConfiguration");

		JSONArray queryEntriesJSONArray =
			queryConfigurationEntryJSONObject.getJSONArray("queryEntries");

		for (int i = 0; i < queryEntriesJSONArray.length(); i++) {
			JSONObject queryEntryJSONObject =
				queryEntriesJSONArray.getJSONObject(i);

			JSONArray clausesJSONArray = queryEntryJSONObject.getJSONArray(
				"clauses");

			for (int k = 0; k < clausesJSONArray.length(); k++) {
				JSONObject clauseJSONObject = clausesJSONArray.getJSONObject(i);

				JSONObject queryJSONObject = clauseJSONObject.getJSONObject(
					"query");

				if (externalReferenceCode.startsWith(
						"BOOST_CONTENTS_IN_A_CATEGORY")) {

					_upgradeConfigurationEntryForBoostElements(queryJSONObject);
				}
				else if (externalReferenceCode.startsWith(
							"HIDE_CONTENTS_IN_A_CATEGORY")) {

					_upgradeConfigurationEntryForHideElements(queryJSONObject);
				}
			}
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

			assetCategoryIds = _extractAssetCategoryIds(termJSONObject);
			boost = assetCategoryIdsJSONObject.getDouble("boost");
		}
		else {
			JSONObject termsJSONObject = queryJSONObject.getJSONObject("terms");

			assetCategoryIds = _extractAssetCategoryIds(termsJSONObject);
			boost = termsJSONObject.getDouble("boost");
		}

		queryJSONObject.put(
			"terms",
			JSONUtil.put(
				"boost", boost
			).put(
				"groupAssetCategoryExternalReferenceCodes",
				_translateIdsToExternalReferencesCodes(assetCategoryIds)
			));
	}

	private void _upgradeConfigurationEntryForHideElements(
			JSONObject queryJSONObject)
		throws Exception {

		JSONObject boolJSONObject = queryJSONObject.getJSONObject("bool");

		JSONArray mustNotJSONArray = boolJSONObject.getJSONArray("must_not");

		for (int i = 0; i < mustNotJSONArray.length(); i++) {
			JSONObject mustNotJSONObject = mustNotJSONArray.getJSONObject(i);

			JSONObject termJSONObject = mustNotJSONObject.getJSONObject("term");

			long[] assetCategoryIds = _extractAssetCategoryIds(termJSONObject);

			mustNotJSONObject.remove("term");

			mustNotJSONObject.put(
				"terms",
				JSONUtil.put(
					"groupAssetCategoryExternalReferenceCodes",
					_translateIdsToExternalReferencesCodes(assetCategoryIds)));
		}
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
					String elementInstancesJSON = resultSet.getString(
						"elementInstancesJSON");

					try {
						ElementInstance[] elementInstances =
							ElementInstanceUtil.toElementInstances(
								elementInstancesJSON);

						if ((elementInstances == null) ||
							!_containsCategoryElement(elementInstances)) {

							continue;
						}

						JSONArray elementInstancesJSONArray =
							_jsonFactory.createJSONArray(elementInstancesJSON);

						for (int i = 0; i < elementInstancesJSONArray.length();
							 i++) {

							JSONObject elementInstanceJSONObject =
								elementInstancesJSONArray.getJSONObject(i);

							JSONObject sxpElementJSONObject =
								elementInstanceJSONObject.getJSONObject(
									"sxpElement");

							String externalReferenceCode =
								sxpElementJSONObject.getString(
									"externalReferenceCode");

							if (!ArrayUtil.contains(
									_EXTERNAL_REFERENCE_CODES,
									externalReferenceCode)) {

								continue;
							}

							_upgradeConfigurationEntry(
								externalReferenceCode,
								elementInstanceJSONObject.getJSONObject(
									"configurationEntry"));

							_upgradeSXPElement(
								elementInstanceJSONObject.getJSONObject(
									"sxpElement"));

							_upgradeUIConfigurationValues(
								elementInstanceJSONObject.getJSONObject(
									"uiConfigurationValues"));
						}

						preparedStatement2.setString(
							1, elementInstancesJSONArray.toString());

						preparedStatement2.setLong(
							2, resultSet.getLong("sxpBlueprintId"));

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

				preparedStatement2.executeBatch();
			}
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

	private void _upgradeSXPElements() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update SXPElement set elementDefinitionJSON = ? where " +
						"externalReferenceCode = ?")) {

			for (String externalReferenceCode : _EXTERNAL_REFERENCE_CODES) {
				preparedStatement.setString(
					1, _getElementDefinitionJSON(externalReferenceCode));
				preparedStatement.setString(2, externalReferenceCode);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private void _upgradeUIConfiguration(JSONObject uiConfigurationJSONObject) {
		JSONArray fieldSetsJSONArray = uiConfigurationJSONObject.getJSONArray(
			"fieldSets");

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldSetJSONObject = fieldSetsJSONArray.getJSONObject(i);

			JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
				"fields");

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
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
	}

	private void _upgradeUIConfigurationValues(
			JSONObject uiConfigurationValuesJSONObject)
		throws Exception {

		JSONArray groupAssetCategoryExternalReferenceCodesJSONArray =
			_jsonFactory.createJSONArray();

		if (uiConfigurationValuesJSONObject.has("asset_category_id")) {
			JSONObject assetCategoryIdsJSONObject =
				uiConfigurationValuesJSONObject.getJSONObject(
					"asset_category_id");

			groupAssetCategoryExternalReferenceCodesJSONArray.put(
				_createGroupAssetCategoryExternalReferenceCodesJSONObject(
					assetCategoryIdsJSONObject));

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