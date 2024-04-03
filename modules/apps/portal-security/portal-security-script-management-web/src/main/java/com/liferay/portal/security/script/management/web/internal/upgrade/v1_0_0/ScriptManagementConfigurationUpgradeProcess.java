/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.script.management.configuration.ScriptManagementConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * @author Feliphe Marinho
 */
public class ScriptManagementConfigurationUpgradeProcess
	extends UpgradeProcess {

	public ScriptManagementConfigurationUpgradeProcess(
		ConfigurationProvider configurationProvider, JSONFactory jsonFactory) {

		_configurationProvider = configurationProvider;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (_hasGroovyScriptUses()) {
			return;
		}

		_configurationProvider.saveSystemConfiguration(
			ScriptManagementConfiguration.class,
			HashMapDictionaryBuilder.<String, Object>put(
				"allowScriptContentBeExecutedOrIncluded", false
			).build());
	}

	private boolean _hasGroovyScriptUses() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					"select KaleoDefinition.content from KaleoDefinition " +
						"where KaleoDefinition.active_ = [$TRUE$]"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select 1 from ObjectAction where ",
						"ObjectAction.active_ = [$TRUE$] and ",
						"ObjectAction.objectActionExecutorKey = 'groovy'")));
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select 1 from ObjectValidationRule where ",
						"ObjectValidationRule.active_ = [$TRUE$] and ",
						"ObjectValidationRule.engine = 'groovy'")));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			ResultSet resultSet3 = preparedStatement3.executeQuery()) {

			if (resultSet2.next() || resultSet3.next()) {
				return true;
			}

			while (resultSet1.next()) {
				if (_hasWorkflowDefinitionGroovyScriptUse(resultSet1)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _hasWorkflowDefinitionGroovyScriptUse(ResultSet resultSet)
		throws Exception {

		Queue<Map<String, Object>> queue = new LinkedList<>();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			resultSet.getString(1));

		queue.add(jsonObject.toMap());

		while (!queue.isEmpty()) {
			Map<String, Object> jsonObjectsMap = queue.poll();

			for (Map.Entry<String, Object> entry : jsonObjectsMap.entrySet()) {
				if (entry.getValue() instanceof List) {
					if (Objects.equals(entry.getKey(), "#cdata-value")) {
						continue;
					}

					queue.addAll((List<Map<String, Object>>)entry.getValue());
				}
				else if (jsonObjectsMap.size() == 2) {
					if (Objects.equals(
							jsonObjectsMap.get("#tag-name"),
							"script-language") &&
						Objects.equals(
							jsonObjectsMap.get("#value"), "groovy")) {

						return true;
					}
				}
			}
		}

		return false;
	}

	private final ConfigurationProvider _configurationProvider;
	private final JSONFactory _jsonFactory;

}