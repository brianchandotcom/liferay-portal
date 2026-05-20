/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

		UpgradeProcessUtil.setUpgradeClient(true);
	}

	@After
	public void tearDown() throws Exception {
		UpgradeProcessUtil.setUpgradeClient(false);

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
		_assertRewrittenJDBCURL(
			_JDBC_URL_MYSQL, _JDBC_URL_MYSQL + "?" + _DEFAULT_PARAMETERS_MYSQL);
		_assertRewrittenJDBCURL(
			_JDBC_URL_POSTGRESQL,
			_JDBC_URL_POSTGRESQL + "?" + _DEFAULT_PARAMETERS_POSTGRESQL);
		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER,
			_JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME + ";" +
				_DEFAULT_PARAMETERS_SQL_SERVER,
			12, 4);
	}

	@Test
	public void testRewriteJDBCURLForSQLServerWithDriverVersion()
		throws Exception {

		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER, _JDBC_URL_SQL_SERVER, 11, 4);
		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER, _JDBC_URL_SQL_SERVER, 12, 3);

		String jdbcURL =
			_JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME + ";" +
				_DEFAULT_PARAMETERS_SQL_SERVER;

		_assertRewrittenJDBCURL(_JDBC_URL_SQL_SERVER, jdbcURL, 12, 4);
		_assertRewrittenJDBCURL(_JDBC_URL_SQL_SERVER, jdbcURL, 12, 5);
		_assertRewrittenJDBCURL(_JDBC_URL_SQL_SERVER, jdbcURL, 13, 0);
	}

	@Test
	public void testRewriteJDBCURLForSQLServerWithRuntimeClient()
		throws Exception {

		UpgradeProcessUtil.setUpgradeClient(false);

		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER, _JDBC_URL_SQL_SERVER, 12, 4);
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
				_JDBC_URL_SQL_SERVER, _rewriteJDBCURL(_JDBC_URL_SQL_SERVER));
		}
	}

	@Test
	public void testRewriteJDBCURLWithCustomParameters() throws Exception {
		String parameter = "userParameter=userValue";

		_assertRewrittenJDBCURL(
			_JDBC_URL_MYSQL + "?" + parameter,
			StringBundler.concat(
				_JDBC_URL_MYSQL, "?", _DEFAULT_PARAMETERS_MYSQL, "&",
				parameter));
		_assertRewrittenJDBCURL(
			_JDBC_URL_POSTGRESQL + "?" + parameter,
			StringBundler.concat(
				_JDBC_URL_POSTGRESQL, "?", _DEFAULT_PARAMETERS_POSTGRESQL, "&",
				parameter));
		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER + ";" + parameter,
			StringBundler.concat(
				_JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME, ";",
				_DEFAULT_PARAMETERS_SQL_SERVER, ";", parameter),
			12, 4);
	}

	@Test
	public void testRewriteJDBCURLWithExistingDefaultParameters()
		throws Exception {

		String jdbcURL = _JDBC_URL_MYSQL + "?cachePrepStmts=false";
		String parameters = StringUtil.removeSubstring(
			_DEFAULT_PARAMETERS_MYSQL, "cachePrepStmts=true&");

		_assertRewrittenJDBCURL(jdbcURL, jdbcURL + "&" + parameters);

		jdbcURL = _JDBC_URL_POSTGRESQL + "?reWriteBatchedInserts=false";

		_assertRewrittenJDBCURL(jdbcURL, jdbcURL);

		jdbcURL = _JDBC_URL_SQL_SERVER + ";useBulkCopyForBatchInsert=false";

		_assertRewrittenJDBCURL(
			jdbcURL,
			StringBundler.concat(
				_JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME,
				";bulkCopyForBatchInsertTableLock=true;databaseName=lportal3;",
				"useBulkCopyForBatchInsert=false"),
			12, 4);
	}

	@Test
	public void testRewriteJDBCURLWithMalformedParameters() throws Exception {
		String parameter = "valuelessParameter";

		_assertRewrittenJDBCURL(
			_JDBC_URL_MYSQL + "?" + parameter,
			StringBundler.concat(
				_JDBC_URL_MYSQL, "?", _DEFAULT_PARAMETERS_MYSQL, "&",
				parameter));

		_assertRewrittenJDBCURL(
			_JDBC_URL_POSTGRESQL + "?" + parameter,
			StringBundler.concat(
				_JDBC_URL_POSTGRESQL, "?", _DEFAULT_PARAMETERS_POSTGRESQL, "&",
				parameter));

		_assertRewrittenJDBCURL(
			_JDBC_URL_SQL_SERVER + ";" + parameter,
			StringBundler.concat(
				_JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME, ";",
				_DEFAULT_PARAMETERS_SQL_SERVER, ";", parameter),
			12, 4);
	}

	private void _assertRewrittenJDBCURL(String jdbcURL, String expectedURL)
		throws Exception {

		Assert.assertEquals(expectedURL, _rewriteJDBCURL(jdbcURL));
	}

	private void _assertRewrittenJDBCURL(
			String jdbcURL, String expectedURL, int majorVersion,
			int minorVersion)
		throws Exception {

		Assert.assertEquals(
			expectedURL, _rewriteJDBCURL(jdbcURL, majorVersion, minorVersion));
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

	private static final String _DEFAULT_PARAMETERS_MYSQL =
		StringBundler.concat(
			"cachePrepStmts=true&characterEncoding=UTF-8&",
			"dontTrackOpenResources=true&",
			"holdResultsOpenOverStatementClose=true&",
			"prepStmtCacheSize=1000&prepStmtCacheSqlLimit=2048&",
			"rewriteBatchedStatements=true&serverTimezone=GMT&",
			"useFastDateParsing=false&useLocalSessionState=true&",
			"useLocalTransactionState=true&useUnicode=true");

	private static final String _DEFAULT_PARAMETERS_POSTGRESQL =
		"reWriteBatchedInserts=true";

	private static final String _DEFAULT_PARAMETERS_SQL_SERVER =
		"bulkCopyForBatchInsertTableLock=true;databaseName=lportal3;" +
			"useBulkCopyForBatchInsert=true";

	private static final String _JDBC_URL_MYSQL =
		"jdbc:mysql://localhost/lportal1";

	private static final String _JDBC_URL_POSTGRESQL =
		"jdbc:postgresql://localhost/lportal2";

	private static final String _JDBC_URL_SQL_SERVER =
		DataSourceFactoryTest._JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME +
			";databaseName=lportal3";

	private static final String _JDBC_URL_SQL_SERVER_WITHOUT_DATABASE_NAME =
		"jdbc:sqlserver://localhost";

	private Path _path;

}