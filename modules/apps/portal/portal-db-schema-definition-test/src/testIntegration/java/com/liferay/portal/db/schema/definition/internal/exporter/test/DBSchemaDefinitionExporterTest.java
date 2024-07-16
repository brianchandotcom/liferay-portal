/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.schema.definition.internal.test.util.ConfigurationTestUtil;
import com.liferay.portal.db.schema.definition.internal.test.util.DatabaseTestUtil;
import com.liferay.portal.db.schema.definition.internal.test.util.ObjectTestUtil;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		ObjectTestUtil.createObjectsData();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ObjectTestUtil.removeObjectsData();
	}

	@Before
	public void setUp() throws Exception {
		_databaseType = String.valueOf(DBManagerUtil.getDBType());
		_folder = FileUtil.createTempFolder();
	}

	@After
	public void tearDown() throws Exception {
		Files.deleteIfExists(ConfigurationTestUtil.getConfigurationPath(_PID));

		FileUtil.deltree(_folder);
	}

	@Test
	public void testExportImportDBSchemaDefinition() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.db.schema.definition.internal.exporter." +
					"DBSchemaDefinitionExporter",
				LoggerTestUtil.INFO)) {

			ConfigurationTestUtil.deployConfiguration(
				_configurationAdmin, _databaseType, _folder.getAbsolutePath(),
				_PID);

			_assertImportDBSchemaDefinition(
				new File(_folder, "tables.sql"),
				new File(_folder, "indexes.sql"));

			Assert.assertFalse(
				Files.exists(ConfigurationTestUtil.getConfigurationPath(_PID)));
			Assert.assertNull(
				_configurationAdmin.listConfigurations(
					"(service.pid=" + _PID + ")"));
			Assert.assertNull(
				ReflectionTestUtil.invoke(
					_persistenceManager, "_getDictionary",
					new Class<?>[] {String.class}, _PID));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			List<String> logMessages = new ArrayList<>();

			for (LogEntry entry : logEntries) {
				logMessages.add(entry.getMessage());
			}

			Assert.assertEquals(
				"Start database schema definition export", logMessages.get(0));
			Assert.assertEquals(
				"Finished database schema definition export to " +
					_folder.getAbsolutePath(),
				logMessages.get(1));
		}
	}

	private void _assertImportDBSchemaDefinition(
			File tablesSQLFile, File indexesSQLFile)
		throws Exception {

		DatabaseTestUtil.createSchema(_COPY_DB_SCHEMA_NAME);

		DataSource copyDataSource = null;

		try {
			copyDataSource = DatabaseTestUtil.initDataSource(
				_COPY_DB_SCHEMA_NAME);

			DatabaseTestUtil.importFile(tablesSQLFile, copyDataSource);

			_assertTables(copyDataSource);

			DatabaseTestUtil.importFile(indexesSQLFile, copyDataSource);

			_assertIndexes(copyDataSource);
		}
		finally {
			DatabaseTestUtil.dropSchema(_COPY_DB_SCHEMA_NAME);

			if (copyDataSource != null) {
				DatabaseTestUtil.destroyDataSource(copyDataSource);
			}
		}
	}

	private void _assertIndexes(DataSource copyDataSource) throws Exception {
		List<String> copyIndexColumnNames =
			DatabaseTestUtil.getIndexColumnNames(copyDataSource);
		List<String> indexColumnNames = DatabaseTestUtil.getIndexColumnNames(
			InfrastructureUtil.getDataSource());

		Assert.assertEquals(
			StringUtils.difference(
				copyIndexColumnNames.toString(), indexColumnNames.toString()),
			indexColumnNames.size(), copyIndexColumnNames.size());

		for (int i = 0; i < indexColumnNames.size(); i++) {
			Assert.assertEquals(
				indexColumnNames.get(i), copyIndexColumnNames.get(i));
		}
	}

	private void _assertTables(DataSource copyDataSource) throws Exception {
		List<String> copyTableColumnNames =
			DatabaseTestUtil.getTableColumnNames(copyDataSource);
		List<String> tableColumnNames = DatabaseTestUtil.getTableColumnNames(
			InfrastructureUtil.getDataSource());

		Assert.assertEquals(
			StringUtils.difference(
				copyTableColumnNames.toString(), tableColumnNames.toString()),
			tableColumnNames.size(), copyTableColumnNames.size());

		for (int i = 0; i < tableColumnNames.size(); i++) {
			Assert.assertEquals(
				tableColumnNames.get(i), copyTableColumnNames.get(i));
		}
	}

	private static final String _COPY_DB_SCHEMA_NAME =
		RandomTestUtil.randomString();

	private static final String _PID =
		"com.liferay.portal.db.schema.definition.internal.configuration." +
			"DBSchemaDefinitionExporterConfiguration";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private String _databaseType;
	private File _folder;

	@Inject
	private PersistenceManager _persistenceManager;

}