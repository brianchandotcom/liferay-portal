/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.spring.hibernate.SpringHibernateThreadLocalUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.util.List;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpSession;

/**
 * @author Tina Tian
 */
public class DynamicDataSourceTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
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

		DataSourceFactory dataSourceFactory = new DataSourceFactoryImpl();

		DataSource readDataSource = dataSourceFactory.initDataSource(
			"org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:" + _tempDir.getAbsolutePath() + "/lportal-read;",
			"sa", StringPool.BLANK, StringPool.BLANK);

		DataSource writeDataSource = dataSourceFactory.initDataSource(
			"org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:" + _tempDir.getAbsolutePath() + "/lportal-write;",
			"sa", StringPool.BLANK, StringPool.BLANK);

		_dynamicDataSource = new DynamicDataSource(
			readDataSource, writeDataSource);

		_currentTransactionReadOnlyThreadLocal =
			ReflectionTestUtil.getFieldValue(
				SpringHibernateThreadLocalUtil.class,
				"_currentTransactionReadOnlyThreadLocal");

		_writeDataSourceThreadLocal = ReflectionTestUtil.getFieldValue(
			DynamicDataSource.class, "_writeDataSourceThreadLocal");
	}

	@After
	public void tearDown() {
		FileUtil.deltree(_tempDir);

		_currentTransactionReadOnlyThreadLocal.remove();
		_writeDataSourceThreadLocal.remove();
	}

	@Test
	public void testGetDataSource() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"JDBC_READ_DATA_SOURCE_UNAVAILABLE_TIMEOUT", 0)) {

			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), true, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), true, false);
			_testGetDataSource(
				_dynamicDataSource.getReadDataSource(),
				List.of("Returning read data source"), false, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), false, false);
		}
	}

	@Test
	public void testGetDataSourceWithTimeout() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"JDBC_READ_DATA_SOURCE_UNAVAILABLE_TIMEOUT",
					Long.MAX_VALUE)) {

			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), true, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of(
					"No context HTTP session exists, skipped setting the " +
						"write data source last used date",
					"Returning write data source"),
				true, false);
			_testGetDataSource(
				_dynamicDataSource.getReadDataSource(),
				List.of(
					"No context HTTP session exists, skipped getting the " +
						"write data source last used date",
					"Returning read data source"),
				false, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of(
					"No context HTTP session exists, skipped setting the " +
						"write data source last used date",
					"Returning write data source"),
				false, false);
		}
	}

	@Test
	public void testGetDataSourceWithTimeoutAndHttpSession() {
		String sessionId = RandomTestUtil.randomString();

		PortalSessionContext.put(sessionId, new MockHttpSession());

		ThreadLocal<String> sessionIdThreadLocal =
			ReflectionTestUtil.getFieldValue(
				PortalSessionThreadLocal.class, "_sessionId");

		sessionIdThreadLocal.set(sessionId);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"JDBC_READ_DATA_SOURCE_UNAVAILABLE_TIMEOUT",
					Long.MAX_VALUE)) {

			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), true, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), true, false);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), false, true);
			_testGetDataSource(
				_dynamicDataSource.getWriteDataSource(),
				List.of("Returning write data source"), false, false);
		}
		finally {
			sessionIdThreadLocal.remove();
		}
	}

	private void _testGetDataSource(
		DataSource expectedDataSource, List<String> expectedLogMessages,
		boolean writeDataSource, boolean currentTransactionReadOnly) {

		_writeDataSourceThreadLocal.set(writeDataSource);

		if (currentTransactionReadOnly) {
			_currentTransactionReadOnlyThreadLocal.set(true);
		}
		else {
			_currentTransactionReadOnlyThreadLocal.remove();
		}

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				"com.liferay.portal.dao.jdbc.util.DynamicDataSource",
				Level.FINEST)) {

			Assert.assertSame(
				expectedDataSource,
				ReflectionTestUtil.invoke(
					_dynamicDataSource, "_getDataSource", new Class<?>[0]));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			if (expectedLogMessages == null) {
				Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
			}
			else {
				Assert.assertEquals(
					logEntries.toString(), expectedLogMessages.size(),
					logEntries.size());

				for (int i = 0; i < expectedLogMessages.size(); i++) {
					LogEntry logEntry = logEntries.get(i);

					Assert.assertEquals(
						expectedLogMessages.get(i), logEntry.getMessage());
				}
			}
		}
	}

	private ThreadLocal<Boolean> _currentTransactionReadOnlyThreadLocal;
	private DynamicDataSource _dynamicDataSource;
	private File _tempDir;
	private ThreadLocal<Boolean> _writeDataSourceThreadLocal;

}