/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Felipe Lorenz
 */
public class SynonymsConfigurationUpgradeProcess extends UpgradeProcess {

	public SynonymsConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Configuration_ where configurationId = ?")) {

			preparedStatement.setString(1, _PID);

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();

			String dictionaryString = resultSet.getString("dictionary");

			if (Validator.isNull(dictionaryString)) {
				return;
			}

			Dictionary<String, Object> dictionary = ConfigurationHandler.read(
				new UnsyncByteArrayInputStream(
					dictionaryString.getBytes(StringPool.UTF8)));

			List<String> synonymFilterNames = new ArrayList<>(
				Arrays.asList((String[])dictionary.get("filterNames")));

			synonymFilterNames.addAll(
				new ArrayList<>(Arrays.asList(_FILTER_NAMES)));

			Collections.sort(synonymFilterNames);

			dictionary.put(
				"filterNames", synonymFilterNames.toArray(new String[0]));

			Configuration configuration = _configurationAdmin.getConfiguration(
				_PID, "?");

			configuration.update(dictionary);
		}
	}

	private static final String[] _FILTER_NAMES = {
		"liferay_filter_synonym_ar", "liferay_filter_synonym_ca",
		"liferay_filter_synonym_de", "liferay_filter_synonym_fi",
		"liferay_filter_synonym_fr", "liferay_filter_synonym_hu",
		"liferay_filter_synonym_it", "liferay_filter_synonym_ja",
		"liferay_filter_synonym_nl", "liferay_filter_synonym_pt_BR",
		"liferay_filter_synonym_pt_PT", "liferay_filter_synonym_sv",
		"liferay_filter_synonym_zh"
	};

	private static final String _PID =
		"com.liferay.portal.search.tuning.synonyms.web.internal." +
			"configuration.SynonymsConfiguration";

	private final ConfigurationAdmin _configurationAdmin;

}