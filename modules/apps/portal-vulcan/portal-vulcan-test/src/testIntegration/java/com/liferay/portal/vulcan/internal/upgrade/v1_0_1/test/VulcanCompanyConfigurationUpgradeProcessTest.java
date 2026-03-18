/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class VulcanCompanyConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(
			unsyncByteArrayOutputStream,
			HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				String.valueOf(TestPropsValues.getCompanyId())
			).put(
				"path", RandomTestUtil.randomString()
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

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId ='" +
				_CONFIGURATION_ID + "'");
	}

	@Test
	public void testUpgrade() throws Exception {
		_runUpgrade();

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", _CONFIGURATION_ID, "'"))) {

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				Object value = dictionary.get(
					ExtendedObjectClassDefinition.Scope.COMPANY.
						getPropertyKey());

				Assert.assertTrue(value instanceof Long);

				Assert.assertEquals(TestPropsValues.getCompanyId(), value);
			}
		}
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 0, 1));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		CacheRegistryUtil.clear();
	}

	private static final String _CONFIGURATION_ID =
		"com.liferay.portal.vulcan.internal.configuration." +
			"VulcanCompanyConfiguration.scoped~" +
				RandomTestUtil.randomString();

	@Inject(
		filter = "(&(component.name=com.liferay.portal.vulcan.internal.upgrade.registry.VulcanImplUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}