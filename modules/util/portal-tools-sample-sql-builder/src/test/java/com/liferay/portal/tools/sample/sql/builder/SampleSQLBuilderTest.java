/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LogAssertionTestRule;
import com.liferay.portal.tools.HypersonicLoader;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Enumeration;
import java.util.Properties;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class SampleSQLBuilderTest {

	@ClassRule
	public static final LogAssertionTestRule logAssertionTestRule =
		LogAssertionTestRule.INSTANCE;

	@Test
	public void testFreemarkerTemplateContent() throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(
			"/com/liferay/portal/tools/sample/sql/builder/dependencies" +
				"/sample.ftl");

		String fileContent = new String(
			Files.readAllBytes(Paths.get(url.toURI())), StringPool.UTF8);

		Assert.assertTrue(
			"sample.ftl must end with " + _SAMPLE_FTL_END,
			fileContent.endsWith(_SAMPLE_FTL_END));
	}

	@Test
	public void testGenerateAndInsertSampleSQL() throws Exception {
		ToolDependencies.wireBasic();

		DBManagerUtil.setDB(DBType.HYPERSONIC, null);

		Properties properties = new Properties();

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR),
			String.valueOf(System.currentTimeMillis()));

		_initProperties(properties, tempDir.getAbsolutePath());

		File tempPropertiesFile = File.createTempFile("test", ".properties");

		try (Writer writer = new FileWriter(tempPropertiesFile)) {
			properties.store(writer, null);

			System.setProperty(
				"sample-sql-properties", tempPropertiesFile.getAbsolutePath());

			new SampleSQLBuilder();

			_loadHypersonic("../../../sql", tempDir.getAbsolutePath());
		}
		finally {
			FileUtil.deltree(tempDir);
		}
	}

	private ClassLoader _getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private Enumeration<URL> _getServiceComponentsIndexesSQLURLs()
		throws Exception {

		ClassLoader classLoader = _getClassLoader();

		return classLoader.getResources("META-INF/sql/indexes.sql");
	}

	private Enumeration<URL> _getServiceComponentsTablesSQLURLs()
		throws Exception {

		ClassLoader classLoader = _getClassLoader();

		return classLoader.getResources("META-INF/sql/tables.sql");
	}

	private void _initProperties(Properties properties, String outputDir) {
		properties.put(
			BenchmarksPropsKeys.COMMERCE_LAYOUT_EXCLUDED_PORTLETS,
			StringPool.BLANK);
		properties.put(BenchmarksPropsKeys.DB_TYPE, "hypersonic");
		properties.put(BenchmarksPropsKeys.MAX_ASSET_CATEGORY_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_ASSET_ENTRY_TO_ASSET_CATEGORY_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_ASSET_ENTRY_TO_ASSET_TAG_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_ASSETPUBLISHER_PAGE_COUNT, "2");
		properties.put(BenchmarksPropsKeys.MAX_ASSET_TAG_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_ASSET_VUCABULARY_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_BLOGS_ENTRY_COMMENT_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_BLOGS_ENTRY_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_ACCOUNT_ENTRY_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_COMMERCE_CATALOG_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_COMMERCE_GROUP_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_INVENTORY_WAREHOUSE_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_INVENTORY_WAREHOUSE_ITEM_QUANTITY,
			"1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_ORDER_STATUS_CANCELLED_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_ORDER_STATUS_OPEN_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_ORDER_STATUS_PENDING_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_COMMERCE_PRICE_LIST_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_COMMERCE_PRODUCT_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_PRODUCT_DEFINITION_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_PRODUCT_INSTANCE_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_COMMERCE_PRODUCT_OPTION_CATEGORY_COUNT,
			"1");
		properties.put(BenchmarksPropsKeys.MAX_CONTENT_LAYOUT_COUNT, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_CP_DEFINITION_ATTACHMENT_TYPE_IMAGE_COUNT,
			"1");
		properties.put(
			BenchmarksPropsKeys.MAX_CP_DEFINITION_ATTACHMENT_TYPE_PDF_COUNT,
			"1");
		properties.put(
			BenchmarksPropsKeys.
				MAX_CP_DEFINITION_SPECIFICATION_OPTION_VALUE_COUNT,
			"1");
		properties.put(
			BenchmarksPropsKeys.MAX_CP_SPECIFICATION_OPTION_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DDL_CUSTOM_FIELD_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DDL_RECORD_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DDL_RECORD_SET_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DL_FILE_ENTRY_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DL_FILE_ENTRY_SIZE, "1");
		properties.put(BenchmarksPropsKeys.MAX_DL_FOLDER_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_DL_FOLDER_DEPTH, "1");
		properties.put(BenchmarksPropsKeys.MAX_GROUP_COUNT, "2");
		properties.put(BenchmarksPropsKeys.MAX_JOURNAL_ARTICLE_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_JOURNAL_ARTICLE_PAGE_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_JOURNAL_ARTICLE_SIZE, "1");
		properties.put(
			BenchmarksPropsKeys.MAX_JOURNAL_ARTICLE_VERSION_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_MB_CATEGORY_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_MB_MESSAGE_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_MB_THREAD_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_SEGMENTS_ENTRY_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_USER_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_USER_TO_GROUP_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_WIKI_NODE_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_WIKI_PAGE_COMMENT_COUNT, "1");
		properties.put(BenchmarksPropsKeys.MAX_WIKI_PAGE_COUNT, "1");
		properties.put(BenchmarksPropsKeys.OPTIMIZE_BUFFER_SIZE, "8192");
		properties.put(
			BenchmarksPropsKeys.OUTPUT_CSV_FILE_NAMES,
			StringBundler.concat(
				"assetPublisher,blog,company,commerceInventoryWarehouseItem,",
				"commerceOrder,commerceProduct,cpDefinition,documentLibrary,",
				"dynamicDataList,fragment,layout,mbCategory,mbThread,",
				"repository,wiki"));
		properties.put(BenchmarksPropsKeys.OUTPUT_DIR, outputDir);
		properties.put(BenchmarksPropsKeys.OUTPUT_MERGE, "true");
		properties.put(
			BenchmarksPropsKeys.SCRIPT,
			"com/liferay/portal/tools/sample/sql/builder/dependencies" +
				"/sample.ftl");
		properties.put(BenchmarksPropsKeys.SEARCH_BAR_ENABLED, "true");
		properties.put(BenchmarksPropsKeys.VIRTUAL_HOST_NAME, "localhost");
	}

	private void _loadHypersonic(String sqlDir, String outputDir)
		throws Exception {

		try (Connection connection = DriverManager.getConnection(
				"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa",
				"")) {

			HypersonicLoader.loadHypersonic(
				connection, sqlDir + "/portal/portal-hypersonic.sql");
			HypersonicLoader.loadHypersonic(
				connection, sqlDir + "/indexes/indexes-hypersonic.sql");

			_loadServiceComponentsSQL(connection);

			HypersonicLoader.loadHypersonic(
				connection, outputDir + "/sample-hypersonic.sql");

			try (Statement statement = connection.createStatement()) {
				statement.execute("SHUTDOWN COMPACT");
			}
		}
	}

	private void _loadServiceComponentsSQL(Connection connection)
		throws Exception {

		DBManagerUtil.setDB(DBType.HYPERSONIC, null);

		Enumeration<URL> tablesURLEnumeration =
			_getServiceComponentsTablesSQLURLs();

		while (tablesURLEnumeration.hasMoreElements()) {
			_runSQL(connection, tablesURLEnumeration.nextElement());
		}

		Enumeration<URL> indexesURLEnumeration =
			_getServiceComponentsIndexesSQLURLs();

		while (indexesURLEnumeration.hasMoreElements()) {
			_runSQL(connection, indexesURLEnumeration.nextElement());
		}
	}

	private void _runSQL(Connection connection, URL url) throws Exception {
		DB db = DBManagerUtil.getDB();

		String sql = StringUtil.read(url.openStream());

		db.runSQLTemplateString(connection, sql, true);
	}

	private static final String _SAMPLE_FTL_END =
		"<#include \"counters.ftl\">\n\nCOMMIT_TRANSACTION";

}