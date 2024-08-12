/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.schema.definition.internal.test.util.ConfigurationTestUtil;
import com.liferay.portal.db.schema.definition.internal.test.util.DatabaseTestUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBSchemaDefinitionExporterTest
	extends BaseDBSchemaDefinitionExporterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeFalse(DBPartition.isPartitionEnabled());

		BaseDBSchemaDefinitionExporterTestCase.assumeDB();
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBSchemaDefinitionExporterTestCase.setUpClassDefault();
	}

	@Test
	public void testExportImportDBSchemaDefinition() throws Exception {
		testExportImportDBSchemaDefinition(
			() -> _assertImportDBSchemaDefinition(
				new File(folder, "tables.sql"),
				new File(folder, "indexes.sql")));
	}

	@Test
	public void testExportImportReport() throws Exception {
		ConfigurationTestUtil.deployConfiguration(
			configurationAdmin, databaseType, folder.getAbsolutePath(), PID);

		String content = FileUtil.read(
			new File(folder, "db_schema_definition_export_report.info"));

		Assert.assertTrue(content.endsWith("Missing tables:"));
	}

	@Test
	public void testExportImportReportWithMissingTable() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("create table TestTable (testColumn bigint primary key)");

		try {
			ConfigurationTestUtil.deployConfiguration(
				configurationAdmin, databaseType, folder.getAbsolutePath(),
				PID);

			String content = FileUtil.read(
				new File(folder, "db_schema_definition_export_report.info"));

			Assert.assertTrue(
				content.contains(
					"Missing tables: " + StringUtil.toLowerCase("TestTable")));
		}
		finally {
			db.runSQL("DROP_TABLE_IF_EXISTS(TestTable)");
		}
	}

	private void _assertImportDBSchemaDefinition(
			File tablesSQLFile, File indexesSQLFile)
		throws Exception {

		DatabaseTestUtil.createSchema(COPY_DB_SCHEMA_NAME);

		DataSource copyDataSource = null;

		try {
			copyDataSource = DatabaseTestUtil.initDataSource(
				COPY_DB_SCHEMA_NAME);

			DatabaseTestUtil.importFile(tablesSQLFile, copyDataSource);

			assertTables(InfrastructureUtil.getDataSource(), copyDataSource);

			DatabaseTestUtil.importFile(indexesSQLFile, copyDataSource);

			assertIndexes(InfrastructureUtil.getDataSource(), copyDataSource);
		}
		finally {
			DatabaseTestUtil.dropSchema(COPY_DB_SCHEMA_NAME);

			if (copyDataSource != null) {
				DatabaseTestUtil.destroyDataSource(copyDataSource);
			}
		}
	}

}