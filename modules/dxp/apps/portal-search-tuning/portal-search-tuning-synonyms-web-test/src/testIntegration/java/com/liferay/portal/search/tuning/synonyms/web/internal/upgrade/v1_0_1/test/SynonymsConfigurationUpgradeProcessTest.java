/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Felipe Lorenz
 */
@RunWith(Arquillian.class)
public class SynonymsConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeSynonymsConfiguration() throws Exception {
		_addConfiguration();

		try {
			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _UPGRADE_VERSION);

			upgradeProcess.upgrade();

			_assertConfiguration();
		}
		finally {
			_removeConfiguration();
		}
	}

	private void _addConfiguration() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(
			unsyncByteArrayOutputStream,
			HashMapDictionaryBuilder.<String, Object>put(
				"filterNames", _FILTER_NAMES
			).build());

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, _CONFIGURATION_ID);
			preparedStatement.setString(
				2, unsyncByteArrayOutputStream.toString());

			preparedStatement.execute();
		}
	}

	private void _assertConfiguration() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", _CONFIGURATION_ID, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				String[] filterNameValues = (String[])dictionary.get(
					"filterNames");

				Assert.assertArrayEquals(
					_EXPECTED_FILTER_NAMES, filterNameValues);
			}
		}
	}

	private void _removeConfiguration() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId ='" +
				_CONFIGURATION_ID + "'");
	}

	private static final String _CONFIGURATION_ID =
		"com.liferay.portal.search.tuning.synonyms.web.internal." +
			"configuration.SynonymsConfiguration";

	private static final String[] _EXPECTED_FILTER_NAMES = {
		"custom_filter_synonym_ru", "liferay_filter_synonym_ar",
		"liferay_filter_synonym_ca", "liferay_filter_synonym_de",
		"liferay_filter_synonym_en", "liferay_filter_synonym_es",
		"liferay_filter_synonym_fi", "liferay_filter_synonym_fr",
		"liferay_filter_synonym_hu", "liferay_filter_synonym_it",
		"liferay_filter_synonym_ja", "liferay_filter_synonym_nl",
		"liferay_filter_synonym_pt_BR", "liferay_filter_synonym_pt_PT",
		"liferay_filter_synonym_sv", "liferay_filter_synonym_zh"
	};

	private static final String[] _FILTER_NAMES = {
		"custom_filter_synonym_ru", "liferay_filter_synonym_en",
		"liferay_filter_synonym_es", "liferay_filter_synonym_fr"
	};

	private static final String _UPGRADE_VERSION =
		"com.liferay.portal.search.tuning.synonyms.web.internal.upgrade." +
			"v1_0_1.SynonymsConfigurationUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.registry.SynonymsWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}