/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.ConfigurationDataCleanupPreupgradeProcess;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_originalCacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PortalInstancePool.class, "_cacheEnabled", _originalCacheEnabled);
	}

	@Test
	public void testUpgrade() throws Exception {
		connection = _connection;

		_test(
			"companyId", "Company", TestPropsValues.getCompanyId(),
			_getNonexistentCompanyId());
		_test(
			"groupId", "Group_", TestPropsValues.getGroupId(),
			_getNonexistentGroupId());
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

	private void _test(
			String primaryKeyColumnName, String tableName, long existentId,
			long nonexistentId)
		throws Exception {

		String existentConfigurationId = null;
		String nonexistentConfigurationId = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ConfigurationDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			existentConfigurationId =
				ConfigurationTestUtil.createFactoryConfiguration(
					ConfigurationDataCleanupPreupgradeProcessTest.class.
						getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, existentId
					).build());

			nonexistentConfigurationId =
				ConfigurationTestUtil.createFactoryConfiguration(
					ConfigurationDataCleanupPreupgradeProcessTest.class.
						getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						primaryKeyColumnName, nonexistentId
					).build());

			upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			List<String> logMessages = new ArrayList<>();

			for (LogEntry logEntry : logEntries) {
				logMessages.add(logEntry.getMessage());
			}

			Assert.assertFalse(
				logMessages.contains(
					StringBundler.concat(
						"Deleted configuration ", existentConfigurationId,
						". Reason: ", primaryKeyColumnName, " ", existentId,
						" was not found in ", tableName, ".", primaryKeyColumnName)));

			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"Deleted configuration ", nonexistentConfigurationId,
						". Reason: ", primaryKeyColumnName, " ", nonexistentId,
						" was not found in ", tableName, ".", primaryKeyColumnName)));
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(existentConfigurationId);
			ConfigurationTestUtil.deleteConfiguration(
				nonexistentConfigurationId);
		}
	}

	private static Connection _connection;
	private static boolean _originalCacheEnabled;

}