/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.sql.Connection;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 * @author Eric Yan
 */
public class DataSourceFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		_tempDir = FileUtil.createTempFolder();
	}

	@After
	public void tearDown() {
		FileUtil.deltree(_tempDir);
	}

	@Test
	public void testDestroyDataSource() throws Exception {

		// Destroy JDNI data source

		DataSource dataSource1 = DataSourceFactoryUtil.initDataSource(
			"org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:" + _tempDir.getAbsolutePath() + "/lportal;", "sa",
			StringPool.BLANK, StringPool.BLANK);

		NamingManager.setInitialContextFactoryBuilder(
			environment -> environment1 -> new InitialContext() {

				@Override
				public Object lookup(String name) {
					return dataSource1;
				}

			});

		DataSource dataSource2 = DataSourceFactoryUtil.initDataSource(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, "jdbc/test");

		try (Connection connection = dataSource2.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		DataSourceFactoryUtil.destroyDataSource(dataSource2);

		try (Connection connection = dataSource2.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		// Destroy other data source

		DataSourceFactoryUtil.destroyDataSource(dataSource1);

		try (Connection connection = dataSource1.getConnection()) {
			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"HikariDataSource HikariDataSource (HikariPool-1) has been " +
					"closed.",
				exception.getMessage());
		}
	}

	@Test
	public void testJNDIDataSourceFailure() throws Exception {
		PropsUtil.set(
			PropsKeys.JNDI_ENVIRONMENT + Context.INITIAL_CONTEXT_FACTORY,
			"org.apache.naming.java.javaURLContextFactory");

		Properties properties = new Properties();

		String jndiName = "jdbc/" + DataSourceFactoryTest.class.getName();

		properties.setProperty("jndi.name", jndiName);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				DataSourceFactoryUtil.class.getName(), LoggerTestUtil.ERROR)) {

			DataSourceFactoryUtil.initDataSource(properties);

			Assert.fail();
		}
		catch (NamingException namingException) {
			Assert.assertEquals(
				NameNotFoundException.class, namingException.getClass());
			Assert.assertEquals(
				String.format(
					"Name [java:comp/env/%s] is not bound in this Context. " +
						"Unable to find [java:comp].",
					jndiName),
				namingException.getMessage());
		}
	}

	@Test
	public void testRewriteJDBCURL() throws Exception {
		Assert.assertEquals(
			StringBundler.concat(
				"jdbc:mysql://localhost/lportal1?cachePrepStmts=",
				"true&characterEncoding=UTF-8&dontTrackOpenResources=true",
				"&holdResultsOpenOverStatementClose=true&prepStmtCacheSize=",
				"1000&prepStmtCacheSqlLimit=2048&rewriteBatchedStatements=",
				"true&serverTimezone=GMT&useFastDateParsing=false",
				"&useLocalSessionState=true&useLocalTransactionState=true",
				"&useUnicode=true"),
			_rewriteJDBCURL("jdbc:mysql://localhost/lportal1"));

		Assert.assertEquals(
			"jdbc:postgresql://localhost/lportal2?reWriteBatchedInserts=true",
			_rewriteJDBCURL("jdbc:postgresql://localhost/lportal2"));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"useBulkCopyForBatchInsert=true",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3"));
	}

	@Test
	public void testRewriteJDBCURLWithCustomParameters() throws Exception {
		String rewrittenMySQLJDBCURL = _rewriteJDBCURL(
			"jdbc:mysql://localhost/lportal1?customParam=customValue");

		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("cachePrepStmts=true"));
		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("characterEncoding=UTF-8"));
		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("customParam=customValue"));

		String rewrittenPostgreSQLJDBCURL = _rewriteJDBCURL(
			"jdbc:postgresql://localhost/lportal2?customParam=customValue");

		Assert.assertTrue(
			rewrittenPostgreSQLJDBCURL.contains("customParam=customValue"));
		Assert.assertTrue(
			rewrittenPostgreSQLJDBCURL.contains("reWriteBatchedInserts=true"));

		String rewrittenSQLServerJDBCURL = _rewriteJDBCURL(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"customParam=customValue");

		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains("customParam=customValue"));
		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains(
				"useBulkCopyForBatchInsert=true"));
	}

	@Test
	public void testRewriteJDBCURLWithExistingDefaultParameters()
		throws Exception {

		String rewrittenMySQLJDBCURL = _rewriteJDBCURL(
			"jdbc:mysql://localhost/lportal1?cachePrepStmts=false");

		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("cachePrepStmts=false"));
		Assert.assertFalse(
			rewrittenMySQLJDBCURL.contains("cachePrepStmts=true"));
		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("characterEncoding=UTF-8"));

		Assert.assertEquals(
			"jdbc:postgresql://localhost/lportal2?reWriteBatchedInserts=false",
			_rewriteJDBCURL(
				"jdbc:postgresql://localhost/lportal2?reWriteBatchedInserts=" +
					"false"));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"useBulkCopyForBatchInsert=false",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3;" +
					"useBulkCopyForBatchInsert=false"));
	}

	@Test
	public void testRewriteJDBCURLWithMalformedParameters() throws Exception {
		String rewrittenMySQLJDBCURL = _rewriteJDBCURL(
			"jdbc:mysql://localhost/lportal1?malformedParam");

		Assert.assertTrue(
			rewrittenMySQLJDBCURL.contains("cachePrepStmts=true"));
		Assert.assertTrue(rewrittenMySQLJDBCURL.contains("malformedParam"));

		String rewrittenPostgreSQLJDBCURL = _rewriteJDBCURL(
			"jdbc:postgresql://localhost/lportal2?malformedParam");

		Assert.assertTrue(
			rewrittenPostgreSQLJDBCURL.contains("malformedParam"));
		Assert.assertTrue(
			rewrittenPostgreSQLJDBCURL.contains("reWriteBatchedInserts=true"));

		String rewrittenSQLServerJDBCURL = _rewriteJDBCURL(
			"jdbc:sqlserver://localhost;databaseName=lportal3;malformedParam");

		Assert.assertTrue(rewrittenSQLServerJDBCURL.contains("malformedParam"));
		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains(
				"useBulkCopyForBatchInsert=true"));
	}

	private String _rewriteJDBCURL(String jdbcURL) throws Exception {
		return ReflectionTestUtil.invoke(
			DataSourceFactoryUtil.class, "_rewriteJDBCURL",
			new Class<?>[] {String.class}, jdbcURL);
	}

	private File _tempDir;

}