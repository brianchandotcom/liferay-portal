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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LogAssertionTestRule;
import com.liferay.portal.tools.HypersonicLoader;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;

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

		Properties properties = new SortedProperties();

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR),
			String.valueOf(System.currentTimeMillis()));

		_initProperties(properties, tempDir.getAbsolutePath());

		try {
			new SampleSQLBuilder(properties, new DataFactory(properties));

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
		properties.put("sample.sql.db.type", "hypersonic");
		properties.put("sample.sql.max.asset.category.count", "1");
		properties.put(
			"sample.sql.max.asset.entry.to.asset.category.count", "1");
		properties.put("sample.sql.max.asset.entry.to.asset.tag.count", "1");
		properties.put("sample.sql.max.asset.publisher.page.count", "2");
		properties.put("sample.sql.max.asset.tag.count", "1");
		properties.put("sample.sql.max.asset.vocabulary.count", "1");
		properties.put("sample.sql.max.blogs.entry.comment.count", "1");
		properties.put("sample.sql.max.blogs.entry.count", "1");
		properties.put("sample.sql.max.commerce.product.count", "1");
		properties.put("sample.sql.max.commerce.product.definition.count", "1");
		properties.put("sample.sql.max.commerce.product.instance.count", "1");
		properties.put("sample.sql.max.content.layout.count", "1");
		properties.put("sample.sql.max.ddl.custom.field.count", "1");
		properties.put("sample.sql.max.ddl.record.count", "1");
		properties.put("sample.sql.max.ddl.record.set.count", "1");
		properties.put("sample.sql.max.dl.file.entry.count", "1");
		properties.put("sample.sql.max.dl.file.entry.size", "1");
		properties.put("sample.sql.max.dl.folder.count", "1");
		properties.put("sample.sql.max.dl.folder.depth", "1");
		properties.put("sample.sql.max.group.count", "1");
		properties.put("sample.sql.max.journal.article.count", "1");
		properties.put("sample.sql.max.journal.article.page.count", "1");
		properties.put("sample.sql.max.journal.article.size", "1");
		properties.put("sample.sql.max.journal.article.version.count", "1");
		properties.put("sample.sql.max.mb.category.count", "1");
		properties.put("sample.sql.max.mb.message.count", "1");
		properties.put("sample.sql.max.mb.thread.count", "1");
		properties.put("sample.sql.max.user.count", "1");
		properties.put("sample.sql.max.user.to.group.count", "1");
		properties.put("sample.sql.max.wiki.node.count", "1");
		properties.put("sample.sql.max.wiki.page.comment.count", "1");
		properties.put("sample.sql.max.wiki.page.count", "1");
		properties.put("sample.sql.optimize.buffer.size", "8192");
		properties.put(
			"sample.sql.output.csv.file.names",
			"assetPublisher,blog,company,cpFriendlyURLEntry,documentLibrary," +
				"dynamicDataList,fragment,layout,mbCategory,mbThread," +
					"repository,wiki");
		properties.put("sample.sql.output.dir", outputDir);
		properties.put("sample.sql.output.merge", "true");
		properties.put(
			"sample.sql.script",
			"com/liferay/portal/tools/sample/sql/builder/dependencies" +
				"/sample.ftl");
		properties.put("sample.sql.virtual.hostname", "localhost");
	}

	private void _loadHypersonic(String sqlDir, String outputDir)
		throws Exception {

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(
				"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa",
				"");

			HypersonicLoader.loadHypersonic(
				connection, sqlDir + "/portal/portal-hypersonic.sql");
			HypersonicLoader.loadHypersonic(
				connection, sqlDir + "/indexes/indexes-hypersonic.sql");

			_loadServiceComponentsSQL(connection);

			HypersonicLoader.loadHypersonic(
				connection, outputDir + "/sample-hypersonic.sql");

			statement = connection.createStatement();

			statement.execute("SHUTDOWN COMPACT");
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}
	}

	private void _loadServiceComponentsSQL(Connection connection)
		throws Exception {

		DBManagerUtil.setDB(DBType.HYPERSONIC, null);

		Enumeration<URL> tablesURLs = _getServiceComponentsTablesSQLURLs();

		while (tablesURLs.hasMoreElements()) {
			_runSQL(connection, tablesURLs.nextElement());
		}

		Enumeration<URL> indexesURLs = _getServiceComponentsIndexesSQLURLs();

		while (tablesURLs.hasMoreElements()) {
			_runSQL(connection, indexesURLs.nextElement());
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