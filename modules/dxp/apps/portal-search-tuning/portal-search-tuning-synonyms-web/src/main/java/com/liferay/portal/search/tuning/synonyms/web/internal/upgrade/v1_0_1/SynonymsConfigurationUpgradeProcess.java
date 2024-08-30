/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Felipe Lorenz
 */
public class SynonymsConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from Configuration_ where configurationId = ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"update Configuration_ set dictionary = ? where " +
					"configurationId = ?")) {

			preparedStatement1.setString(1, _CONFIGURATION_ID);

			ResultSet resultSet = preparedStatement1.executeQuery();

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				if (Validator.isNull(dictionaryString)) {
					return;
				}

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				Set<String> synonymFilterNameSet = SetUtil.fromArray(
					(String[])dictionary.get("filterNames"));

				synonymFilterNameSet.addAll(_filterNames);

				String[] synonymFilterNames = synonymFilterNameSet.toArray(
					new String[0]);

				Arrays.sort(synonymFilterNames);

				dictionary.put("filterNames", synonymFilterNames);

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				ConfigurationHandler.write(
					unsyncByteArrayOutputStream, dictionary);

				preparedStatement2.setString(
					1, unsyncByteArrayOutputStream.toString());

				preparedStatement2.setString(2, _CONFIGURATION_ID);

				preparedStatement2.executeUpdate();
			}
		}
	}

	private static final String _CONFIGURATION_ID =
		"com.liferay.portal.search.tuning.synonyms.web.internal." +
			"configuration.SynonymsConfiguration";

	private static final List<String> _filterNames = ListUtil.fromArray(
		"liferay_filter_synonym_ar", "liferay_filter_synonym_ca",
		"liferay_filter_synonym_de", "liferay_filter_synonym_fi",
		"liferay_filter_synonym_fr", "liferay_filter_synonym_hu",
		"liferay_filter_synonym_it", "liferay_filter_synonym_ja",
		"liferay_filter_synonym_nl", "liferay_filter_synonym_pt_BR",
		"liferay_filter_synonym_pt_PT", "liferay_filter_synonym_sv",
		"liferay_filter_synonym_zh");

}