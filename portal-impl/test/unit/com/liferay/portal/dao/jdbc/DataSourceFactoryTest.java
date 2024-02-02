/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.sql.Connection;

import javax.naming.InitialContext;
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

		_tempFolder = FileUtil.createTempFolder();

		_dataSource = _dataSourceFactory.initDataSource(
			"org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:" + _tempFolder.getAbsolutePath() + "/lportal;", "sa",
			StringPool.BLANK, StringPool.BLANK);
	}

	@After
	public void tearDown() {
		FileUtil.deltree(_tempFolder);
	}

	@Test
	public void testDestroyDataSource() throws Exception {

		// Test 1, destroy JDNI data source

		NamingManager.setInitialContextFactoryBuilder(
			environment -> environment1 -> new InitialContext() {

				@Override
				public Object lookup(String name) {
					return _dataSource;
				}

			});

		DataSource dataSource = _dataSourceFactory.initDataSource(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, "jdbc/test");

		try (Connection connection = dataSource.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		_dataSourceFactory.destroyDataSource(dataSource);

		try (Connection connection = dataSource.getConnection()) {
			Assert.assertFalse(connection.isClosed());
		}

		// Test 2, destroy non JDNI data source

		_dataSourceFactory.destroyDataSource(_dataSource);

		try (Connection connection = _dataSource.getConnection()) {
			Assert.fail("Data source should be closed");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"HikariDataSource HikariDataSource (HikariPool-1) has been " +
					"closed.",
				exception.getMessage());
		}
	}

	private DataSource _dataSource;
	private final DataSourceFactory _dataSourceFactory =
		new DataSourceFactoryImpl();
	private File _tempFolder;

}