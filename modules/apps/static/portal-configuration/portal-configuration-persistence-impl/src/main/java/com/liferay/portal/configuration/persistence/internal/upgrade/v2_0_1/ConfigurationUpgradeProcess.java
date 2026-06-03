/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.internal.upgrade.v2_0_1;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Thiago Buarque
 */
public class ConfigurationUpgradeProcess extends UpgradeProcess {

	public ConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin,
		GroupLocalService groupLocalService) {

		_configurationAdmin = configurationAdmin;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select configurationId, dictionary from Configuration_ " +
					"where dictionary like '%groupId=%'");

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String configurationId = resultSet.getString("configurationId");

				Dictionary<String, Object> dictionary = _toDictionary(
					resultSet.getString("dictionary"));

				Long companyId = _getCompanyId(configurationId, dictionary);

				if (companyId == null) {
					continue;
				}

				dictionary.put(
					ExtendedObjectClassDefinition.Scope.COMPANY.
						getPropertyKey(),
					companyId);

				Configuration configuration =
					_configurationAdmin.getConfiguration(configurationId, "?");

				configuration.update(dictionary);
			}
		}
	}

	private Long _getCompanyId(
		String configurationId, Dictionary<String, Object> dictionary) {

		long groupId = GetterUtil.getLong(
			dictionary.get(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey()));

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Skipping configuration \"", configurationId,
						"\" because group \"", groupId,
						"\" does not exist for company \"",
						CompanyThreadLocal.getCompanyId(), "\""));
			}

			return null;
		}

		return group.getCompanyId();
	}

	private Dictionary<String, Object> _toDictionary(String dictionaryString)
		throws IOException {

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StandardCharsets.UTF_8));

		return ConfigurationHandler.read(unsyncByteArrayInputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUpgradeProcess.class);

	private final ConfigurationAdmin _configurationAdmin;
	private final GroupLocalService _groupLocalService;

}