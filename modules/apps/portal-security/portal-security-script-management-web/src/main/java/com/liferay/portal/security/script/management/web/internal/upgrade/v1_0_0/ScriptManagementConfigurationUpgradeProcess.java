/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.script.management.configuration.ScriptManagementConfiguration;
import com.liferay.portal.workflow.kaleo.definition.util.WorkflowDefinitionContentUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Feliphe Marinho
 */
public class ScriptManagementConfigurationUpgradeProcess
	extends UpgradeProcess {

	public ScriptManagementConfigurationUpgradeProcess(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
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
				String content = WorkflowDefinitionContentUtil.toXML(
					resultSet1.getString(1));

				if (content.contains(
						"<script-language>groovy</script-language>")) {

					return true;
				}
			}
		}

		return false;
	}

	private final ConfigurationProvider _configurationProvider;

}