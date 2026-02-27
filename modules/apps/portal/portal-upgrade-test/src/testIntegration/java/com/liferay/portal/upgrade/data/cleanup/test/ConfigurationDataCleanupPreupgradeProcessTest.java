/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.ConfigurationDataCleanupPreupgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class ConfigurationDataCleanupPreupgradeProcessTest
	extends ConfigurationDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_originalCacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);

		ReflectionTestUtil.setFieldValue(
			PortalInstancePool.class, "_cacheEnabled", _originalCacheEnabled);
	}

	@Test
	public void testUpgrade() throws Exception {
		_testUpgrade(
			0L, _getNonexistentCompanyId(), "companyId", null, null, "Company");
		_testUpgrade(
			TestPropsValues.getCompanyId(), _getNonexistentCompanyId(),
			"companyId", TestPropsValues.getGroupId(), "groupId", "Company");
		_testUpgrade(
			TestPropsValues.getCompanyId(), _getNonexistentCompanyId(),
			"companyId", null, null, "Company");
		_testUpgrade(
			TestPropsValues.getGroupId(), _getNonexistentGroupId(), "groupId",
			TestPropsValues.getCompanyId(), "companyId", "Group_");
		_testUpgrade(
			TestPropsValues.getGroupId(), _getNonexistentGroupId(), "groupId",
			null, null, "Group_");

		long companyId = _getNonexistentCompanyId();

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DATABASE_PARTITION_ENABLED", true)) {

			runSQL(
				"insert into Company (mvccVersion, companyId) values (0 ," +
					companyId + ")");

			_testUpgrade(
				TestPropsValues.getCompanyId(), companyId, "companyId", null,
				null, "Company");
		}
		finally {
			runSQL("delete from Company where companyId = " + companyId);
		}
	}

	@Test
	public void testUpgradeWithNullDictionary() throws Exception {
		String configurationId = RandomTestUtil.randomString();

		try {
			try (PreparedStatement preparedStatement =
					_connection.prepareStatement(
						"insert into Configuration_ (configurationId, " +
							"dictionary) values (?, ?)")) {

				preparedStatement.setString(1, configurationId);
				preparedStatement.setString(2, null);

				preparedStatement.executeUpdate();
			}

			upgrade();
		}
		finally {
			runSQL(
				StringBundler.concat(
					"delete from Configuration_ where configurationId = '",
					configurationId, "'"));
		}
	}

	@Test
	public void testUpgradeWithoutOrphanConfigurations()
		throws DataCleanupPreupgradeException {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ConfigurationDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.ERROR)) {

			upgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 0, messages.size());
		}
	}

	protected long[] getGroupIds() throws Exception {
		List<Long> groupIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select groupId from Group_");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				groupIds.add(resultSet.getLong("groupId"));
			}
		}

		return ArrayUtil.toArray(groupIds.toArray(new Long[0]));
	}

	private long _getNonexistentCompanyId() throws Exception {
		Set<Long> companyIds = SetUtil.fromArray(
			PortalInstancePool.getCompanyIds());

		while (true) {
			long nonexistentCompanyId = RandomTestUtil.randomLong();

			if (!companyIds.contains(nonexistentCompanyId)) {
				return nonexistentCompanyId;
			}
		}
	}

	private long _getNonexistentGroupId() throws Exception {
		Set<Long> groupIds = SetUtil.fromArray(getGroupIds());

		while (true) {
			long nonexistentGroupId = RandomTestUtil.randomLong();

			if (!groupIds.contains(nonexistentGroupId)) {
				return nonexistentGroupId;
			}
		}
	}

	private void _testUpgrade(
			Long existentPrimaryKey, Long nonexistentPrimaryKey,
			String primaryKeyColumnName, Long secondaryKey,
			String secondaryKeyColumnName, String tableName)
		throws Exception {

		String existentConfigurationId = null;
		String nonexistentConfigurationId = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ConfigurationDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			Dictionary<String, Object> existentDictionary = null;
			Dictionary<String, Object> nonexistentDictionary = null;

			if ((secondaryKey != null) && (secondaryKeyColumnName != null)) {
				existentDictionary =
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, existentPrimaryKey
					).put(
						secondaryKeyColumnName, secondaryKey
					).build();
				nonexistentDictionary =
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, nonexistentPrimaryKey
					).put(
						secondaryKeyColumnName, secondaryKey
					).build();
			}
			else {
				existentDictionary =
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, existentPrimaryKey
					).build();
				nonexistentDictionary =
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, nonexistentPrimaryKey
					).build();
			}

			try (LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME, LoggerTestUtil.ERROR)) {

				existentConfigurationId =
					ConfigurationTestUtil.createFactoryConfiguration(
						ConfigurationDataCleanupPreupgradeProcessTest.class.
							getName(),
						existentDictionary);
				nonexistentConfigurationId =
					ConfigurationTestUtil.createFactoryConfiguration(
						ConfigurationDataCleanupPreupgradeProcessTest.class.
							getName(),
						nonexistentDictionary);
			}

			upgrade();

			List<String> messages = logCapture.getMessages();

			String logContext =
				messages.toString() + CompanyThreadLocal.getCompanyId();

			Assert.assertFalse(
				logContext,
				messages.contains(
					StringBundler.concat(
						"Table Configuration_, 1 row deleted because ",
						existentConfigurationId, " has scope ",
						primaryKeyColumnName, StringPool.SPACE,
						existentPrimaryKey, " that was not found in ",
						_dbInspector.normalizeName(tableName), ".",
						_dbInspector.normalizeName(primaryKeyColumnName))));
			Assert.assertTrue(
				logContext,
				messages.contains(
					StringBundler.concat(
						"Table Configuration_, 1 row deleted because ",
						nonexistentConfigurationId, " has scope ",
						primaryKeyColumnName, StringPool.SPACE,
						nonexistentPrimaryKey, " that was not found in ",
						_dbInspector.normalizeName(tableName), ".",
						_dbInspector.normalizeName(primaryKeyColumnName))));
		}
		finally {
			if (existentConfigurationId != null) {
				ConfigurationTestUtil.deleteConfiguration(
					existentConfigurationId);
			}

			if (nonexistentConfigurationId != null) {
				ConfigurationTestUtil.deleteConfiguration(
					nonexistentConfigurationId);
			}
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.configuration.admin.web.internal.configuration." +
			"persistence.listener." +
				"ConfigurationImportGlobalConfigurationModelListener";

	private Connection _connection;
	private DBInspector _dbInspector;
	private boolean _originalCacheEnabled;

}