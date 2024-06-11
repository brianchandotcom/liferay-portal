/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.schema.definition.internal.test.helper.ConfigurationTestHelper;
import com.liferay.portal.db.schema.definition.internal.test.util.DatabaseTestUtil;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.List;

import javax.sql.DataSource;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
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
public class DBSchemaDefinitionExporterTest {

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

		_path = folder.getAbsolutePath();

		_configurationTestHelper = new ConfigurationTestHelper(
			_configurationAdmin, _persistenceManager);
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.deltree(_path);

		_configurationTestHelper.deleteConfiguration();
	}

	@Test
	public void testCopyDatabaseConfiguration() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.db.schema.definition.internal." +
					"DBSchemaDefinitionExporter",
				LoggerTestUtil.INFO)) {

			_configurationTestHelper.deployConfiguration(
				_PID, _databaseType, _path);

			_assertDatabaseDumpMirrorsCurrentDatabase(_path);

			Assert.assertTrue(
				_configurationTestHelper.isConfigurationFileDeleted());
			Assert.assertTrue(_configurationTestHelper.isDictionaryNull(_PID));
			Assert.assertTrue(
				_configurationTestHelper.isListConfigurationsNull(_PID));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			Assert.assertEquals(
				"Database schema definition export started", logEntries.get(0));
			Assert.assertEquals(
				"Database schema definition export finished",
				logEntries.get(1));
		}
	}

	private void _assertDatabaseDumpMirrorsCurrentDatabase(String path)
		throws Exception {

		DatabaseTestUtil.createSchema(_COPY_SCHEMA_NAME);

		DataSource targetDataSource = null;

		try {
			targetDataSource = DatabaseTestUtil.initSchemaDataSource(
				_COPY_SCHEMA_NAME);

			DatabaseTestUtil.importFileTo(
				new File(path, "tables.sql"), targetDataSource);
			DatabaseTestUtil.importFileTo(
				new File(path, "indexes.sql"), targetDataSource);

			_assertSameIndexesStructure(targetDataSource);
			_assertSameTablesStructure(targetDataSource);
		}
		finally {
			DatabaseTestUtil.dropSchema(_COPY_SCHEMA_NAME);

			if (targetDataSource != null) {
				DatabaseTestUtil.destroyDataSource(targetDataSource);
			}
		}
	}

	private void _assertSameIndexesStructure(DataSource targetDataSource)
		throws Exception {

		List<String> sourceIndexes = DatabaseTestUtil.getSourceIndexes();
		List<String> targetIndexes = DatabaseTestUtil.getTargetIndexes(
			targetDataSource);

		Assert.assertEquals(
			targetIndexes.toString(), sourceIndexes.size(),
			targetIndexes.size());

		for (int i = 0; i < sourceIndexes.size(); i++) {
			Assert.assertEquals(sourceIndexes.get(i), targetIndexes.get(i));
		}
	}

	private void _assertSameTablesStructure(DataSource targetDataSource)
		throws Exception {

		List<String> sourceColumns = DatabaseTestUtil.getSourceTables();
		List<String> targetColumns = DatabaseTestUtil.getTargetTables(
			targetDataSource);

		Assert.assertEquals(
			targetColumns.toString(), sourceColumns.size(),
			targetColumns.size());

		for (int i = 0; i < sourceColumns.size(); i++) {
			Assert.assertEquals(sourceColumns.get(i), targetColumns.get(i));
		}
	}

	private static final String _COPY_SCHEMA_NAME = "copyschema";

	private static final String _PID =
		"com.liferay.portal.db.schema.definition.internal.configuration." +
			"DBSchemaDefinitionExporterConfiguration";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private ConfigurationTestHelper _configurationTestHelper;
	private String _databaseType;
	private String _path;

	@Inject
	private PersistenceManager _persistenceManager;

}