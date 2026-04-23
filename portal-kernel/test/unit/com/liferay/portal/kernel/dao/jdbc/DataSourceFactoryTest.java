/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Tina Tian
 * @author Eric Yan
 */
public class DataSourceFactoryTest {

	@Before
	public void setUp() throws Exception {
		_path = Files.createTempDirectory(
			DataSourceFactoryTest.class.getName());
	}

	@After
	public void tearDown() throws Exception {
		if (_path == null) {
			return;
		}

		Files.walkFileTree(
			_path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioException)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testDestroyDataSource() throws Exception {
		DataSource dataSource1 = DataSourceFactoryUtil.initDataSource(
			"org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:" + _path.toAbsolutePath() + "/lportal;", "sa",
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
	public void testInitDataSource() throws Exception {
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
				"jdbc:sqlserver://localhost;databaseName=lportal3", 12, 4));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3", 11, 4));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3", 12, 3));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"useBulkCopyForBatchInsert=true",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3", 12, 5));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"useBulkCopyForBatchInsert=true",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3", 13, 0));
	}

	@Test
	public void testRewriteJDBCURLForSQLServerWithUnregisteredDriver()
		throws Exception {

		SQLException sqlException = new SQLException("No suitable driver");

		try (MockedStatic<DriverManager> driverManagerMockedStatic =
				Mockito.mockStatic(DriverManager.class)) {

			driverManagerMockedStatic.when(
				() -> DriverManager.getDriver(Mockito.anyString())
			).thenThrow(
				sqlException
			);

			Assert.assertEquals(
				"jdbc:sqlserver://localhost;databaseName=lportal3",
				_rewriteJDBCURL(
					"jdbc:sqlserver://localhost;databaseName=lportal3"));
		}
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
				"customParam=customValue",
			12, 4);

		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains("customParam=customValue"));
		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains(
				"useBulkCopyForBatchInsert=true"));

		rewrittenSQLServerJDBCURL = _rewriteJDBCURL(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"customParam=customValue",
			11, 4);

		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains("customParam=customValue"));
		Assert.assertFalse(
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
					"useBulkCopyForBatchInsert=false",
				12, 4));

		Assert.assertEquals(
			"jdbc:sqlserver://localhost;databaseName=lportal3;" +
				"useBulkCopyForBatchInsert=false",
			_rewriteJDBCURL(
				"jdbc:sqlserver://localhost;databaseName=lportal3;" +
					"useBulkCopyForBatchInsert=false",
				11, 4));
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
			"jdbc:sqlserver://localhost;databaseName=lportal3;malformedParam",
			12, 4);

		Assert.assertTrue(rewrittenSQLServerJDBCURL.contains("malformedParam"));
		Assert.assertTrue(
			rewrittenSQLServerJDBCURL.contains(
				"useBulkCopyForBatchInsert=true"));

		rewrittenSQLServerJDBCURL = _rewriteJDBCURL(
			"jdbc:sqlserver://localhost;databaseName=lportal3;malformedParam",
			11, 4);

		Assert.assertTrue(rewrittenSQLServerJDBCURL.contains("malformedParam"));
		Assert.assertFalse(
			rewrittenSQLServerJDBCURL.contains(
				"useBulkCopyForBatchInsert=true"));
	}

	private String _rewriteJDBCURL(String jdbcURL) throws Exception {
		return ReflectionTestUtil.invoke(
			DataSourceFactoryUtil.class, "_rewriteJDBCURL",
			new Class<?>[] {String.class}, jdbcURL);
	}

	private String _rewriteJDBCURL(
			String jdbcURL, int majorVersion, int minorVersion)
		throws Exception {

		try (MockedStatic<DriverManager> driverManagerMockedStatic =
				Mockito.mockStatic(DriverManager.class)) {

			Driver mockDriver = Mockito.mock(Driver.class);

			driverManagerMockedStatic.when(
				() -> DriverManager.getDriver(Mockito.anyString())
			).thenReturn(
				mockDriver
			);

			Mockito.when(
				mockDriver.getMajorVersion()
			).thenReturn(
				majorVersion
			);

			Mockito.when(
				mockDriver.getMinorVersion()
			).thenReturn(
				minorVersion
			);

			return _rewriteJDBCURL(jdbcURL);
		}
	}

	private Path _path;

}