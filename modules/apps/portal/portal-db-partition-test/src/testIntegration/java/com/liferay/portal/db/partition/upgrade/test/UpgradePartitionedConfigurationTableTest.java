/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradePartitionedConfigurationTableTest
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();

		addDBPartitions();

		insertPartitionRequiredData();

		insertPartitionData();

		_companyId = TestPropsValues.getCompanyId();

		_dataSource = InfrastructureUtil.getDataSource();

		_defaultSchemaName = connection.getCatalog();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		deletePartitionRequiredData();

		removeDBPartitions();

		disableDBPartition();
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		DBPartitionUtil.forEachCompanyId(
			companyId -> {
				if (companyId != _companyId) {
					db.runSQL("drop table if exists Configuration_");
					db.runSQL(
						StringBundler.concat(
							"create or replace view Configuration_ as select ",
							"* from ", _defaultSchemaName, ".Configuration_"));
				}
			});

		_omniAdminUser = UserTestUtil.addOmniAdminUser();

		Group group = null;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(COMPANY_IDS[0])) {

			group = GroupTestUtil.addGroup(
				COMPANY_IDS[0], _omniAdminUser.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);
		}

		Map<Long, ConfigurationEntry> validConfigurationEntries =
			HashMapBuilder.<Long, ConfigurationEntry>put(
				_companyId,
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.SYSTEM, null)
			).put(
				0L,
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
					RandomTestUtil.randomLong())
			).put(
				COMPANY_IDS[0],
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.GROUP,
					group.getGroupId())
			).put(
				COMPANY_IDS[1],
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.COMPANY, COMPANY_IDS[1])
			).build();

		long randomCompanyId = RandomTestUtil.randomLong();
		long randomGroupId = RandomTestUtil.randomLong();

		Map<Long, ConfigurationEntry> invalidConfigurationEntries =
			HashMapBuilder.<Long, ConfigurationEntry>put(
				randomCompanyId,
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.COMPANY,
					randomCompanyId)
			).put(
				randomGroupId,
				new ConfigurationEntry(
					ExtendedObjectClassDefinition.Scope.GROUP, randomGroupId)
			).build();

		try {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"insert into Configuration_ (configurationId, " +
							"dictionary) values (?, ?)")) {

				for (ConfigurationEntry configurationEntry :
						validConfigurationEntries.values()) {

					preparedStatement.setString(1, configurationEntry.getPid());
					preparedStatement.setString(
						2, configurationEntry.getDictionary());

					preparedStatement.executeUpdate();
				}

				for (ConfigurationEntry configurationEntry :
						invalidConfigurationEntries.values()) {

					preparedStatement.setString(1, configurationEntry.getPid());
					preparedStatement.setString(
						2, configurationEntry.getDictionary());

					preparedStatement.executeUpdate();
				}
			}

			Bundle bundle = BundleUtil.getBundle(
				SystemBundleUtil.getBundleContext(),
				"com.liferay.portal.configuration.persistence.impl");

			Class<?> upgradeProcessClass = bundle.loadClass(
				_UPGRADE_CLASS_NAME);

			UpgradeProcess upgradeProcess =
				(UpgradeProcess)upgradeProcessClass.newInstance();

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_UPGRADE_CLASS_NAME, LoggerTestUtil.INFO)) {

				upgradeProcess.upgrade();

				String logMessage = StringUtil.merge(
					logCapture.getLogEntries(), StringPool.NEW_LINE);

				for (Map.Entry<Long, ConfigurationEntry>
						configurationEntryEntry :
							invalidConfigurationEntries.entrySet()) {

					ConfigurationEntry configurationEntry =
						configurationEntryEntry.getValue();

					Assert.assertTrue(
						logMessage.contains(
							StringBundler.concat(
								StringUtil.upperCaseFirstLetter(
									configurationEntry.getScope(
									).getValue()),
								" scope configuration with ID ",
								configurationEntry.getPid(),
								" has been removed because the ",
								configurationEntry.getScope(
								).getValue(),
								" ID ", configurationEntryEntry.getKey(),
								" does not exists")));
				}
			}

			for (Map.Entry<Long, ConfigurationEntry> configurationEntryEntry :
					validConfigurationEntries.entrySet()) {

				ConfigurationEntry configurationEntry =
					configurationEntryEntry.getValue();
				Long companyId = configurationEntryEntry.getKey();

				DBPartitionUtil.forEachCompanyId(
					currentCompanyId -> {
						try (Connection connection =
								_dataSource.getConnection();
							PreparedStatement preparedStatement =
								connection.prepareStatement(
									"select dictionary from Configuration_ " +
										"where configurationId = ?")) {

							preparedStatement.setString(
								1, configurationEntry.getPid());

							try (ResultSet resultSet =
									preparedStatement.executeQuery()) {

								if ((companyId == 0) ||
									Objects.equals(
										currentCompanyId, companyId)) {

									Assert.assertTrue(resultSet.next());
									Assert.assertEquals(
										configurationEntry.getDictionary(),
										resultSet.getString(1));
								}
								else {
									Assert.assertFalse(resultSet.next());
								}
							}
						}
					});
			}
		}
		finally {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"delete from Configuration_ where configurationId = " +
							"?")) {

				for (ConfigurationEntry configurationEntry :
						validConfigurationEntries.values()) {

					preparedStatement.setString(1, configurationEntry.getPid());

					preparedStatement.executeUpdate();
				}
			}
		}
	}

	private String _convertDictionaryValue(Object value) {
		if (value instanceof Long) {
			return StringBundler.concat("L\"", value, StringPool.QUOTE);
		}

		return StringBundler.concat(StringPool.QUOTE, value, StringPool.QUOTE);
	}

	private static final String _UPGRADE_CLASS_NAME =
		"com.liferay.portal.configuration.persistence.internal.upgrade." +
			"v2_0_0.ConfigurationUpgradeProcess";

	private static long _companyId;
	private static DataSource _dataSource;
	private static String _defaultSchemaName;

	@DeleteAfterTestRun
	private User _omniAdminUser;

	private class ConfigurationEntry {

		public ConfigurationEntry(
			ExtendedObjectClassDefinition.Scope scope, Object scopeValue) {

			_scope = scope;

			_pid =
				UpgradePartitionedConfigurationTableTest.class.getName() + "~" +
					RandomTestUtil.randomString();

			String dictionary = "";

			if (scopeValue != null) {
				dictionary = StringBundler.concat(
					scope.getPropertyKey(), StringPool.EQUAL,
					_convertDictionaryValue(scopeValue));
			}

			dictionary = StringBundler.concat(
				dictionary, StringPool.NEW_LINE, RandomTestUtil.randomString(),
				StringPool.EQUAL, StringPool.QUOTE,
				RandomTestUtil.randomString(), StringPool.QUOTE);

			_dictionary = dictionary;
		}

		public String getDictionary() {
			return _dictionary;
		}

		public String getPid() {
			return _pid;
		}

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		private final String _dictionary;
		private final String _pid;
		private final ExtendedObjectClassDefinition.Scope _scope;

	}

}