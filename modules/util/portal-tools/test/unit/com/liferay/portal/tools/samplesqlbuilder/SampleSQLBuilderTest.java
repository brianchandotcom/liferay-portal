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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.tools.DBLoader;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Properties;

import org.junit.Test;

/**
 * @author Tina Tian
 */
public class SampleSQLBuilderTest {

	@Test
	public void testGenerateAndInsertSampleSQL() throws Exception {
		ToolDependencies.wireBasic();

		Properties properties = new SortedProperties();

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR),
			String.valueOf(System.currentTimeMillis()));

		_initProperties(properties, tempDir.getAbsolutePath());

		try {
			new SampleSQLBuilder(properties, new DataFactory(properties));

			_loadHypersonic("sql", tempDir.getAbsolutePath());
		}
		finally {
			FileUtil.deltree(tempDir);
		}
	}

	private void _initProperties(Properties properties, String outputDir) {
		properties.put(
			"sample.sql.asset.publisher.query.name", "assetCategories");
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
			"assetPublisher,blog,company,documentLibrary,dynamicDataList," +
				"layout,messageBoard,repository,wiki");
		properties.put("sample.sql.output.dir", outputDir);
		properties.put("sample.sql.output.merge", "true");
		properties.put(
			"sample.sql.script",
			"com/liferay/portal/tools/samplesqlbuilder/dependencies" +
				"/sample.ftl");
		properties.put("sample.sql.virtual.hostname", "localhost");
	}

	private void _loadHypersonic(String sqlDir, String outputDir)
		throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(
				"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa",
				"");

			DBLoader.loadHypersonic(
				connection, sqlDir + "/portal/portal-hypersonic.sql");
			DBLoader.loadHypersonic(
				connection, sqlDir + "/indexes/indexes-hypersonic.sql");
			DBLoader.loadHypersonic(
				connection, outputDir + "/sample-hypersonic.sql");

			statement = connection.createStatement();

			statement.execute("SHUTDOWN COMPACT");
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}
	}

}