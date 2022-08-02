/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v1_3_1;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.model.impl.SXPElementImpl;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementDefinitionUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.UnpackUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wade Cao
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

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
					String elementInstancesJSON = resultSet.getString(
						"elementInstancesJSON");

					try {
						ElementInstanceUtil.toElementInstances(
							elementInstancesJSON);

						continue;
					}
					catch (RuntimeException runtimeException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Element instances JSON corrupted with ID " +
									resultSet.getLong("sxpBlueprintId"));
						}
					}

					preparedStatement2.setString(
						1, _getElementInstancesJSON(elementInstancesJSON));

					preparedStatement2.setLong(
						2, resultSet.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private com.liferay.search.experiences.model.SXPElement _fetchSXPElement(
			long sxpElementId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from SXPElement where sxpElementId = " +
					sxpElementId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				return _setSXPElement(resultSet);
			}
		}

		return null;
	}

	private com.liferay.search.experiences.model.SXPElement _fetchSXPElement(
			String externalReferenceCode, String title)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select * from SXPElement where externalReferenceCode != '",
					externalReferenceCode, "' and title like '%", title, "%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				return _setSXPElement(resultSet);
			}
		}

		return null;
	}

	private ElementInstance[] _getCustomElementInstances(String[] titleNames)
		throws Exception {

		List<ElementInstance> elementInstances = new ArrayList<>();

		for (String titleName : titleNames) {
			String externalReferenceCode = StringUtil.replace(
				StringUtil.toUpperCase(titleName), CharPool.SPACE,
				CharPool.UNDERLINE);

			com.liferay.search.experiences.model.SXPElement
				serviceBuilderSXPElement = _fetchSXPElement(
					externalReferenceCode, titleName);

			if (serviceBuilderSXPElement == null) {
				continue;
			}

			_getElementDefinition(elementInstances, serviceBuilderSXPElement);
		}

		return (ElementInstance[])elementInstances.toArray(
			new ElementInstance[0]);
	}

	private void _getElementDefinition(
			List<ElementInstance> elementInstances,
			com.liferay.search.experiences.model.SXPElement
				serviceBuilderSXPElement)
		throws Exception {

		ElementDefinition elementDefinition = ElementDefinitionUtil.unpack(
			ElementDefinition.toDTO(
				serviceBuilderSXPElement.getElementDefinitionJSON()));

		String configurationJSON = String.valueOf(
			elementDefinition.getConfiguration());

		ElementInstance elementInstance = ElementInstance.unsafeToDTO("{}");

		elementInstance.setConfigurationEntry(
			Configuration.toDTO(configurationJSON));
		elementInstance.setSxpElement(
			SXPElementUtil.toSXPElement(
				JSONUtil.put(
					"elementDefinition",
					JSONFactoryUtil.createJSONObject(
						elementDefinition.toString())
				).toString()));

		elementInstances.add(elementInstance);
	}

	private String _getElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		List<String> categories = _getFieldValues(
			elementInstancesJSON, "\"category\": \"(\\w+?)\",");

		ElementInstance[] sxpElementElementInstances =
			_getSXPElementElementInstances(elementInstancesJSON);

		List<String> title_i18nList = _getFieldValues(
			elementInstancesJSON, "\"title_i18n\": \\{(.*?)\\}");

		ElementInstance[] uiConfigurationValuesElementInstances =
			_getUIConfigurationValuesElementInstance(elementInstancesJSON);

		ElementInstance[] customElementInstances = _getCustomElementInstances(
			_getTitleNames(title_i18nList));

		int categoryIndex = 0;
		int customElementInstanceIndex = 0;
		int sxpElementElementInstanceIndex = 0;

		List<ElementInstance> elementInstances = new ArrayList<>();

		for (String category : categories) {
			ElementInstance elementInstance = null;

			if (Objects.equals(category, "custom")) {
				if (customElementInstanceIndex <
						customElementInstances.length) {

					elementInstance =
						customElementInstances[customElementInstanceIndex++];

					String[] languageIds = _getLanguageIds(title_i18nList);
					String[] titleNames = _getTitleNames(title_i18nList);

					SXPElement sxpElement = elementInstance.getSxpElement();

					sxpElement.setTitle_i18n(
						HashMapBuilder.put(
							languageIds[categoryIndex],
							titleNames[categoryIndex]
						).build());
				}
				else {
					elementInstance = new ElementInstance();
				}
			}
			else {
				elementInstance =
					sxpElementElementInstances
						[sxpElementElementInstanceIndex++];
			}

			ElementInstance uiConfigurationValuesElementInstance =
				uiConfigurationValuesElementInstances[categoryIndex++];

			elementInstance.setUiConfigurationValues(
				_toMap(
					uiConfigurationValuesElementInstance.
						getUiConfigurationValues()));

			elementInstances.add(elementInstance);
		}

		return Arrays.toString(
			elementInstances.toArray(new ElementInstance[0]));
	}

	private List<String> _getFieldValues(String jsonString, String regex) {
		List<String> fieldValues = new ArrayList<>();

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(jsonString);

		while (matcher.find()) {
			fieldValues.add(matcher.group(1));
		}

		return fieldValues;
	}

	private String[] _getLanguageIds(List<String> title_i18nList) {
		String[] languageIds = new String[title_i18nList.size()];

		int i = 0;

		for (String title : title_i18nList) {
			String[] languageIdTitleNames = StringUtil.split(
				title, CharPool.COLON);

			if (languageIdTitleNames.length < 1) {
				languageIds[i++] = StringPool.BLANK;

				continue;
			}

			languageIds[i++] = StringUtil.replace(
				StringUtil.trim(languageIdTitleNames[0]), CharPool.QUOTE,
				StringPool.BLANK);
		}

		return languageIds;
	}

	private ElementInstance[] _getSXPElementElementInstances(
			String elementInstancesJSON)
		throws Exception {

		List<String> sxpElementIds = _getFieldValues(
			elementInstancesJSON, "\"id\": (\\w+?),");

		List<ElementInstance> elementInstances = new ArrayList<>();

		for (String sxpElementId : sxpElementIds) {
			com.liferay.search.experiences.model.SXPElement
				serviceBuilderSXPElement = _fetchSXPElement(
					Long.valueOf(sxpElementId));

			_getElementDefinition(elementInstances, serviceBuilderSXPElement);
		}

		return (ElementInstance[])elementInstances.toArray(
			new ElementInstance[0]);
	}

	private String[] _getTitleNames(List<String> title_i18nList) {
		String[] titleNames = new String[title_i18nList.size()];

		int i = 0;

		for (String title : title_i18nList) {
			String[] languageIdTitleNames = StringUtil.split(
				title, CharPool.COLON);

			if (languageIdTitleNames.length <= 1) {
				titleNames[i++] = StringPool.BLANK;

				continue;
			}

			titleNames[i++] = StringUtil.replace(
				StringUtil.trim(languageIdTitleNames[1]), CharPool.QUOTE,
				StringPool.BLANK);
		}

		return titleNames;
	}

	private ElementInstance[] _getUIConfigurationValuesElementInstance(
		String elementInstancesJSON) {

		List<String> uiConfigurationValuesList = _getFieldValues(
			elementInstancesJSON, "\"uiConfigurationValues\": \\{(.*?)\\}\\}");

		ElementInstance[] elementInstances =
			new ElementInstance[uiConfigurationValuesList.size()];

		int i = 0;

		for (String uiConfigurationValues : uiConfigurationValuesList) {
			elementInstances[i++] = ElementInstance.unsafeToDTO(
				"{\"uiConfigurationValues\": {" + uiConfigurationValues + "}}");
		}

		return elementInstances;
	}

	private com.liferay.search.experiences.model.SXPElement _setSXPElement(
			ResultSet resultSet)
		throws SQLException {

		com.liferay.search.experiences.model.SXPElement sxpElement =
			new SXPElementImpl();

		sxpElement.setMvccVersion(resultSet.getLong("mvccVersion"));
		sxpElement.setUuid(resultSet.getString("uuid_"));
		sxpElement.setExternalReferenceCode(
			resultSet.getString("externalReferenceCode"));
		sxpElement.setSXPElementId(resultSet.getLong("sxpElementId"));
		sxpElement.setCompanyId(resultSet.getLong("companyId"));
		sxpElement.setUserId(resultSet.getLong("userId"));
		sxpElement.setUserName(resultSet.getString("userName"));
		sxpElement.setCreateDate(resultSet.getDate("createDate"));
		sxpElement.setModifiedDate(resultSet.getDate("modifiedDate"));
		sxpElement.setDescription(resultSet.getString("description"));
		sxpElement.setElementDefinitionJSON(
			resultSet.getString("elementDefinitionJSON"));
		sxpElement.setHidden(resultSet.getBoolean("hidden_"));
		sxpElement.setReadOnly(resultSet.getBoolean("readOnly"));
		sxpElement.setSchemaVersion(resultSet.getString("schemaVersion"));
		sxpElement.setTitle(resultSet.getString("title"));
		sxpElement.setType(resultSet.getInt("type_"));
		sxpElement.setVersion(resultSet.getString("version"));
		sxpElement.setStatus(resultSet.getInt("status"));

		return sxpElement;
	}

	private Map<String, Object> _toMap(Map<String, Object> map)
		throws Exception {

		Map<String, Object> hashMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			hashMap.put(entry.getKey(), UnpackUtil.unpack(entry.getValue()));
		}

		return hashMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUpgradeProcess.class);

}