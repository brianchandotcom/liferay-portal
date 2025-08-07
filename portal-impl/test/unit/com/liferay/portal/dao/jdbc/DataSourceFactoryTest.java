/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
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

import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
		_dbManagerUtilMockedStatic.when(
			() -> DBManagerUtil.getDBType(Mockito.any())
		).thenReturn(
			null
		);

		_dialectDetectorMockedStatic.when(
			() -> DialectDetector.getDialect(Mockito.any())
		).thenReturn(
			null
		);

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
		_dbManagerUtilMockedStatic.close();
		_dialectDetectorMockedStatic.close();

		FileUtil.deltree(_tempDir);
	}

	@Test
	public void testDestroyDataSource() throws Exception {

		// Destroy JDNI data source

		DataSource dataSource1 = _dataSourceFactory.initDataSource(
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

		DataSource dataSource2 = _dataSourceFactory.initDataSource(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, "jdbc/test");

		try (Connection connection = dataSource2.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		_dataSourceFactory.destroyDataSource(dataSource2);

		try (Connection connection = dataSource2.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		// Destroy other data source

		_dataSourceFactory.destroyDataSource(dataSource1);

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
				DataSourceFactoryImpl.class.getName(), LoggerTestUtil.ERROR)) {

			_dataSourceFactory.initDataSource(properties);

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

	private final DataSourceFactory _dataSourceFactory =
		new DataSourceFactoryImpl();
	private final MockedStatic<DBManagerUtil> _dbManagerUtilMockedStatic =
		Mockito.mockStatic(DBManagerUtil.class);
	private final MockedStatic<DialectDetector> _dialectDetectorMockedStatic =
		Mockito.mockStatic(DialectDetector.class);
	private File _tempDir;

}