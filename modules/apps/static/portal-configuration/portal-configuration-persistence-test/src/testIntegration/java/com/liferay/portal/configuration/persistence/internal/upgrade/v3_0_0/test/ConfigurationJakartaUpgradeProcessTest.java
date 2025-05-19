/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ConfigurationJakartaUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			StringBundler.concat(
				"insert into Configuration_ (configurationId, dictionary) ",
				"values ('", _JAVAX_CONFIGURATION_ID, "', 'key=",
				_JAVAX_CONFIGURATION_ID, "')"));

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<?> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.portal.configuration.persistence." +
								"internal.upgrade.v3_0_0." +
									"ConfigurationJakartaUpgradeProcess")) {

						_upgradeProcess = (UpgradeProcess)upgradeStep;
					}
				}
			});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL(
			"delete from Configuration_ where configurationId = '" +
				_JAKARTA_CONFIGURATION_ID + "'");

		_db.runSQL(
			"delete from Configuration_ where configurationId = '" +
				_JAVAX_CONFIGURATION_ID + "'");
	}

	@Test
	public void testUpgrade() throws Exception {
		_upgradeProcess.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", _JAKARTA_CONFIGURATION_ID, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				resultSet.getString(1), "key=" + _JAKARTA_CONFIGURATION_ID,
				resultSet.getString(1));
		}
	}

	private static final String _JAKARTA_CONFIGURATION_ID =
		"jakarta.servlet.test." +
			"ConfigurationJakartaUpgradeProcessTestConfiguration";

	private static final String _JAVAX_CONFIGURATION_ID =
		"javax.servlet.test." +
			"ConfigurationJakartaUpgradeProcessTestConfiguration";

	private static DB _db;
	private static UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.portal.configuration.persistence.internal.upgrade.registry.ConfigurationPersistenceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}