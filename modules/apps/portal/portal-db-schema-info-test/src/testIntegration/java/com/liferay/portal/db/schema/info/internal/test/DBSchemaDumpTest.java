/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.schema.info.internal.test.helper.ConfigurationTestHelper;
import com.liferay.portal.db.schema.info.internal.test.util.ConfigurationValidationTestUtil;
import com.liferay.portal.db.schema.info.internal.test.util.DatabaseValidationTestUtil;
import com.liferay.portal.db.schema.info.internal.test.util.LoggingValidationTestUtil;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBSchemaDumpTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DBType dbType = DBManagerUtil.getDBType();

		Assume.assumeTrue(
			(dbType == DBType.MYSQL) || (dbType == DBType.POSTGRESQL));
	}

	@Before
	public void setUp() throws Exception {
		_databaseType = String.valueOf(DBManagerUtil.getDBType());

		File folder = FileUtil.createTempFolder();

		_dumpPath = folder.getAbsolutePath();

		_configurationTestHelper = new ConfigurationTestHelper(
			_configurationAdmin, _persistenceManager);
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.deltree(_dumpPath);

		_configurationTestHelper.deleteConfiguration();
	}

	@Test
	public void testCopyDatabaseConfiguration() throws Exception {
		try (LogCapture logCapture =
				LoggingValidationTestUtil.getLogCapture()) {

			_configurationTestHelper.deployConfiguration(
				_PID, _databaseType, _dumpPath);

			DatabaseValidationTestUtil.assertDatabaseDumpMirrorsCurrentDatabase(
				_dumpPath);

			ConfigurationValidationTestUtil.
				assertConfigurationIsDeletedAfterDeploy(
					_configurationTestHelper, _PID);
			LoggingValidationTestUtil.assertStartEndIsLogged(logCapture);
		}
	}

	private static final String _PID =
		"com.liferay.portal.db.schema.info.internal.configuration." +
			"DBSchemaDumpConfiguration";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private ConfigurationTestHelper _configurationTestHelper;
	private String _databaseType;
	private String _dumpPath;

	@Inject
	private PersistenceManager _persistenceManager;

}