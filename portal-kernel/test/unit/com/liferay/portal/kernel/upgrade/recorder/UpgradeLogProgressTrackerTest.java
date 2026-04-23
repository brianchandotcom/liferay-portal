/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.recorder;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.lang.reflect.Method;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author István András Dézsi
 */
public class UpgradeLogProgressTrackerTest {

	@Before
	public void setUp() {
		_originalLog = ReflectionTestUtil.getFieldValue(
			UpgradeLogProgressTracker.class, "_log");
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			UpgradeLogProgressTracker.class, "_log", _originalLog);
	}

	@Test
	public void testBaseDBProcessGetConnectionCallsWrap() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			MockedStatic<UpgradeLogProgressTracker>
				upgradeLogProgressTrackerMockedStatic = Mockito.mockStatic(
					UpgradeLogProgressTracker.class,
					Mockito.CALLS_REAL_METHODS)) {

			UpgradeLogProgressTracker.start();

			try {
				upgradeLogProgressTrackerMockedStatic.when(
					() -> UpgradeLogProgressTracker.wrap(
						Mockito.any(Connection.class), Mockito.anyString())
				).thenAnswer(
					invocation -> invocation.getArgument(0)
				);

				Method method = BaseDBProcess.class.getDeclaredMethod(
					"getConnection");

				method.setAccessible(true);

				method.invoke(
					new BaseDBProcess() {
					});

				upgradeLogProgressTrackerMockedStatic.verify(
					() -> UpgradeLogProgressTracker.wrap(
						Mockito.any(Connection.class), Mockito.anyString()));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testCloseIsIdempotent() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet resultSet = _mockResultSet();

				ResultSet wrappedResultSet = _wrapResultSet(
					upgradeProcessClassName, resultSet);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedResultSet.next());

				Map<String, Long> lastKnownProgresses =
					UpgradeLogProgressTracker.getLastKnownProgresses();

				String registryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_registryKey");

				Assert.assertEquals(
					Long.valueOf(1L), lastKnownProgresses.get(registryKey));

				wrappedResultSet.close();

				Assert.assertNull(lastKnownProgresses.get(registryKey));

				Mockito.verify(
					log, Mockito.times(1)
				).info(
					registryKey + " finished."
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testCloseWithoutProgressDoesNotLog() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", Long.MAX_VALUE)) {

			UpgradeLogProgressTracker.start();

			try {
				ResultSet resultSet = _mockResultSet();

				ResultSet wrappedResultSet = _wrapResultSet(
					"com.liferay.test.SampleUpgradeProcess", resultSet);

				Assert.assertTrue(wrappedResultSet.next());

				wrappedResultSet.close();

				Mockito.verify(
					log, Mockito.never()
				).info(
					Mockito.anyString()
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testDelegationSafety() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				String columnName = RandomTestUtil.randomString();
				String columnValue = RandomTestUtil.randomString();

				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					resultSet.getString(columnName)
				).thenReturn(
					columnValue
				);

				ResultSet wrappedResultSet = _wrapResultSet(
					"com.liferay.test.SampleUpgradeProcess", resultSet);

				Assert.assertEquals(
					columnValue, wrappedResultSet.getString(columnName));

				Mockito.verify(
					log, Mockito.never()
				).info(
					Mockito.anyString()
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNestedResultSets() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet innerResultSet = _mockResultSet();
				ResultSet outerResultSet = _mockResultSet();

				Connection connection = Mockito.mock(Connection.class);
				Statement innerStatement = Mockito.mock(Statement.class);
				Statement outerStatement = Mockito.mock(Statement.class);

				Mockito.when(
					connection.createStatement()
				).thenReturn(
					outerStatement, innerStatement
				);

				Mockito.when(
					outerStatement.executeQuery(Mockito.anyString())
				).thenReturn(
					outerResultSet
				);

				Mockito.when(
					innerStatement.executeQuery(Mockito.anyString())
				).thenReturn(
					innerResultSet
				);

				Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
					connection, upgradeProcessClassName);

				ResultSet wrappedOuterResultSet =
					wrappedConnection.createStatement(
					).executeQuery(
						"outer"
					);
				ResultSet wrappedInnerResultSet =
					wrappedConnection.createStatement(
					).executeQuery(
						"inner"
					);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedOuterResultSet),
					"_lastLogTime", 0L);
				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedInnerResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedOuterResultSet.next());
				Assert.assertTrue(wrappedInnerResultSet.next());

				String innerRegistryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedInnerResultSet),
					"_registryKey");
				String outerRegistryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedOuterResultSet),
					"_registryKey");

				Assert.assertNotEquals(outerRegistryKey, innerRegistryKey);

				Map<String, Long> lastKnownProgresses =
					UpgradeLogProgressTracker.getLastKnownProgresses();

				Assert.assertEquals(
					Long.valueOf(1L),
					lastKnownProgresses.get(outerRegistryKey));
				Assert.assertEquals(
					Long.valueOf(1L),
					lastKnownProgresses.get(innerRegistryKey));

				wrappedInnerResultSet.close();

				Assert.assertNull(lastKnownProgresses.get(innerRegistryKey));
				Assert.assertEquals(
					Long.valueOf(1L),
					lastKnownProgresses.get(outerRegistryKey));

				wrappedOuterResultSet.close();

				Assert.assertNull(lastKnownProgresses.get(outerRegistryKey));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNextDoesNotLogBeforeInterval() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", Long.MAX_VALUE)) {

			UpgradeLogProgressTracker.start();

			try {
				ResultSet resultSet = _mockResultSet();

				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet wrappedResultSet = _wrapResultSet(
					upgradeProcessClassName, resultSet);

				Assert.assertTrue(wrappedResultSet.next());

				Mockito.verify(
					log, Mockito.never()
				).info(
					Mockito.anyString()
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNextDoesNotLogOnEndOfResultSet() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					resultSet.next()
				).thenReturn(
					false
				);

				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet wrappedResultSet = _wrapResultSet(
					upgradeProcessClassName, resultSet);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertFalse(wrappedResultSet.next());

				Mockito.verify(
					log, Mockito.never()
				).info(
					Mockito.anyString()
				);

				Map<String, Long> lastKnownProgresses =
					UpgradeLogProgressTracker.getLastKnownProgresses();

				Assert.assertTrue(lastKnownProgresses.isEmpty());
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNextLogReachesLogger() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			try {
				UpgradeLogProgressTracker.start();

				try (LogCapture logCapture =
						LoggerTestUtil.configureLog4JLogger(
							UpgradeLogProgressTracker.class.getName(),
							LoggerTestUtil.WARN)) {

					logCapture.resetPriority(LoggerTestUtil.INFO);

					ResultSet resultSet = _mockResultSet();

					String upgradeProcessClassName =
						"com.liferay.test.SampleUpgradeProcess";

					ResultSet wrappedResultSet = _wrapResultSet(
						upgradeProcessClassName, resultSet);

					ReflectionTestUtil.setFieldValue(
						ProxyUtil.getInvocationHandler(wrappedResultSet),
						"_lastLogTime", 0L);

					Assert.assertTrue(wrappedResultSet.next());

					List<LogEntry> logEntries = logCapture.getLogEntries();

					String registryKey = ReflectionTestUtil.getFieldValue(
						ProxyUtil.getInvocationHandler(wrappedResultSet),
						"_registryKey");

					Assert.assertEquals(
						logEntries.toString(), 1, logEntries.size());

					Assert.assertEquals(
						registryKey + " is still executing. Processed 1 rows.",
						logEntries.get(
							0
						).getMessage());
				}
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNextLogsAfterIntervalWithClassName() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet resultSet = _mockResultSet();

				ResultSet wrappedResultSet = _wrapResultSet(
					upgradeProcessClassName, resultSet);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedResultSet.next());

				String registryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_registryKey");

				Mockito.verify(
					log
				).info(
					registryKey + " is still executing. Processed 1 rows."
				);

				Map<String, Long> lastKnownProgresses =
					UpgradeLogProgressTracker.getLastKnownProgresses();

				Assert.assertEquals(
					Long.valueOf(1L), lastKnownProgresses.get(registryKey));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testNextLogsRepeatedlyAcrossIntervals() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				String upgradeProcessClassName =
					"com.liferay.test.SampleUpgradeProcess";

				ResultSet resultSet = _mockResultSet();

				ResultSet wrappedResultSet = _wrapResultSet(
					upgradeProcessClassName, resultSet);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedResultSet.next());

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedResultSet.next());

				String registryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_registryKey");

				Mockito.verify(
					log
				).info(
					registryKey + " is still executing. Processed 1 rows."
				);

				Mockito.verify(
					log
				).info(
					registryKey + " is still executing. Processed 2 rows."
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testStartClearsRegistry() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", false)) {

			Map<String, Long> lastKnownProgresses =
				ReflectionTestUtil.getFieldValue(
					UpgradeLogProgressTracker.class, "_lastKnownProgresses");

			lastKnownProgresses.put(
				"com.liferay.test.StaleUpgradeProcess",
				RandomTestUtil.randomLong());

			Assert.assertFalse(lastKnownProgresses.isEmpty());

			UpgradeLogProgressTracker.start();

			Assert.assertTrue(lastKnownProgresses.isEmpty());
		}
	}

	@Test
	public void testStartWarnsWhenEnabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				UpgradeLogProgressTracker.class.getName(),
				LoggerTestUtil.WARN)) {

			try {
				UpgradeLogProgressTracker.start();

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals("WARN", logEntry.getPriority());
				Assert.assertEquals(
					"Granular progress logging for upgrades is enabled. This " +
						"may decrease performance.",
					logEntry.getMessage());
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testWrapDisabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", false)) {

			UpgradeLogProgressTracker.start();

			try {
				Connection connection = Mockito.mock(Connection.class);

				Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
					connection, "com.liferay.test.SampleUpgradeProcess");

				Assert.assertSame(connection, wrappedConnection);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testWrapEnabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			try {
				UpgradeLogProgressTracker.start();

				Connection connection = Mockito.mock(Connection.class);

				Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
					connection, "com.liferay.test.SampleUpgradeProcess");

				Assert.assertNotSame(connection, wrappedConnection);

				Statement statement = Mockito.mock(Statement.class);

				Mockito.when(
					connection.createStatement()
				).thenReturn(
					statement
				);

				Statement wrappedStatement =
					wrappedConnection.createStatement();

				Assert.assertSame(
					wrappedConnection, wrappedStatement.getConnection());

				Assert.assertTrue(
					ProxyUtil.isProxyClass(wrappedStatement.getClass()));

				Mockito.verify(
					statement, Mockito.never()
				).getConnection();

				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					wrappedStatement.executeQuery(Mockito.anyString())
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet = wrappedStatement.executeQuery(
					"select 1");

				Assert.assertSame(
					wrappedStatement, wrappedResultSet.getStatement());

				Assert.assertTrue(
					ProxyUtil.isProxyClass(wrappedResultSet.getClass()));

				Mockito.verify(
					resultSet, Mockito.never()
				).getStatement();

				Mockito.when(
					connection.prepareStatement(Mockito.anyString())
				).thenReturn(
					Mockito.mock(PreparedStatement.class)
				);

				PreparedStatement preparedStatement =
					wrappedConnection.prepareStatement("select 1");

				Assert.assertTrue(
					ProxyUtil.isProxyClass(preparedStatement.getClass()));

				Mockito.when(
					preparedStatement.executeQuery()
				).thenReturn(
					Mockito.mock(ResultSet.class)
				);

				Assert.assertTrue(
					ProxyUtil.isProxyClass(
						preparedStatement.executeQuery(
						).getClass()));

				Mockito.when(
					connection.prepareCall(Mockito.anyString())
				).thenReturn(
					Mockito.mock(CallableStatement.class)
				);

				CallableStatement callableStatement =
					wrappedConnection.prepareCall("call test()");

				Assert.assertTrue(
					ProxyUtil.isProxyClass(callableStatement.getClass()));

				Mockito.when(
					callableStatement.executeQuery()
				).thenReturn(
					Mockito.mock(ResultSet.class)
				);

				Assert.assertTrue(
					ProxyUtil.isProxyClass(
						callableStatement.executeQuery(
						).getClass()));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	private Log _getLog() {
		Log log = Mockito.mock(Log.class);

		Mockito.when(
			log.isInfoEnabled()
		).thenReturn(
			true
		);

		ReflectionTestUtil.setFieldValue(
			UpgradeLogProgressTracker.class, "_log", log);

		return log;
	}

	private ResultSet _mockResultSet() throws Exception {
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			resultSet.next()
		).thenReturn(
			true
		);

		return resultSet;
	}

	private ResultSet _wrapResultSet(
			String upgradeProcessClassName, ResultSet resultSet)
		throws Exception {

		Connection connection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);

		Mockito.when(
			connection.createStatement()
		).thenReturn(
			statement
		);

		Mockito.when(
			statement.executeQuery(Mockito.anyString())
		).thenReturn(
			resultSet
		);

		Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
			connection, upgradeProcessClassName);

		Statement wrappedStatement = wrappedConnection.createStatement();

		return wrappedStatement.executeQuery("select 1");
	}

	private Log _originalLog;

}