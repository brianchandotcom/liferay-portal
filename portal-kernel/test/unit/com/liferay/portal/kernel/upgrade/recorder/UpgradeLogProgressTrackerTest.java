/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.recorder;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

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
	public void testBuildCountSQL() throws Exception {
		Assert.assertEquals(
			"select count(1) from (select * from Foo) tempCountTable_",
			_invokeBuildCountSQL(_SELECT_SQL));

		Assert.assertNull(_invokeBuildCountSQL("call foo()"));

		Assert.assertNull(_invokeBuildCountSQL("update Foo set x = 1"));
		Assert.assertNull(
			_invokeBuildCountSQL("insert into Foo (x) values (1)"));
		Assert.assertNull(_invokeBuildCountSQL("delete from Foo"));
		Assert.assertNull(_invokeBuildCountSQL("create table Foo (x int)"));

		Assert.assertNull(_invokeBuildCountSQL("selectivity_score"));

		String result = _invokeBuildCountSQL("select * from Foo order by id");

		Assert.assertTrue(result.contains("tempCountTable_"));
		Assert.assertTrue(
			StringUtil.toLowerCase(
				result
			).contains(
				"order by id"
			));

		String resultWithSemicolon = _invokeBuildCountSQL("select * from Foo;");

		Assert.assertFalse(resultWithSemicolon.contains(";) tempCountTable_"));
	}

	@Test
	public void testBuildCountSQLStripsOrderByOnSQLServer() throws Exception {
		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class)) {

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDBType
			).thenReturn(
				DBType.SQLSERVER
			);

			String result = _invokeBuildCountSQL(
				"select * from Foo order by id");

			Assert.assertTrue(result.contains("tempCountTable_"));
			Assert.assertFalse(
				StringUtil.toLowerCase(
					result
				).contains(
					"order by id"
				));

			String resultWithSemicolon = _invokeBuildCountSQL(
				"select * from Foo order by id;");

			Assert.assertFalse(resultWithSemicolon.contains("order by id;"));
		}
	}

	@Test
	public void testClearParametersResetsUnsafeFlag() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_WITH_PARAMETER_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				ResultSet countResultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenReturn(
					countResultSet
				);

				Mockito.when(
					countResultSet.next()
				).thenReturn(
					true
				);

				Mockito.when(
					countResultSet.getLong(1)
				).thenReturn(
					RandomTestUtil.randomLong()
				);

				wrappedPreparedStatement.setBinaryStream(
					1, new ByteArrayInputStream(new byte[] {1}));
				wrappedPreparedStatement.clearParameters();
				wrappedPreparedStatement.setInt(1, RandomTestUtil.randomInt());

				Mockito.verify(
					countPreparedStatement
				).clearParameters();

				Mockito.verify(
					countPreparedStatement
				).setInt(
					Mockito.eq(1), Mockito.anyInt()
				);

				ResultSet resultSet = _mockResultSet();

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet =
					wrappedPreparedStatement.executeQuery();

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				wrappedResultSet.next();

				Mockito.verify(
					countPreparedStatement, Mockito.atLeastOnce()
				).executeQuery();
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
				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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
				wrappedResultSet.close();

				Assert.assertNull(lastKnownProgresses.get(registryKey));

				Mockito.verify(
					log, Mockito.times(1)
				).info(
					registryKey + " is finished."
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
					_UPGRADE_PROCESS_CLASS_NAME, resultSet);

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
	public void testConcurrentResultSetIterationDoesNotCorruptRegistry()
		throws Exception {

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				int iterationsPerThread = 200;
				int threadCount = 4;

				AtomicReference<Throwable> throwableReference =
					new AtomicReference<>();

				CountDownLatch countDownLatch = new CountDownLatch(threadCount);

				ExecutorService executorService = Executors.newFixedThreadPool(
					threadCount);

				try {
					for (int i = 0; i < threadCount; i++) {
						executorService.submit(
							() -> {
								try {
									for (int j = 0; j < iterationsPerThread;
										 j++) {

										ResultSet resultSet = _mockResultSet();

										ResultSet wrappedResultSet =
											_wrapResultSet(
												_UPGRADE_PROCESS_CLASS_NAME,
												resultSet);

										wrappedResultSet.next();
										wrappedResultSet.close();
									}
								}
								catch (Throwable throwable) {
									throwableReference.compareAndSet(
										null, throwable);
								}
								finally {
									countDownLatch.countDown();
								}
							});
					}

					Assert.assertTrue(
						countDownLatch.await(30, TimeUnit.SECONDS));
				}
				finally {
					executorService.shutdown();
				}

				Throwable throwable = throwableReference.get();

				if (throwable != null) {
					throw new AssertionError("Worker thread failed", throwable);
				}

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
	public void testCountQueryAppliesTenSecondTimeout() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				ResultSet countResultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenReturn(
					countResultSet
				);

				ResultSet resultSet = _mockResultSet();

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet =
					wrappedPreparedStatement.executeQuery();

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				wrappedResultSet.next();

				Mockito.verify(
					countPreparedStatement, Mockito.atLeastOnce()
				).setQueryTimeout(
					10
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testCountQueryDoesNotPolluteSqlRecorder() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable thresholdSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_REPORT_SQL_STATEMENT_THRESHOLD", 0L)) {

			UpgradeLogProgressTracker.start();
			UpgradeSQLRecorder.start();

			try {
				Connection underlyingConnection = Mockito.mock(
					Connection.class);

				PreparedStatement countPreparedStatement = Mockito.mock(
					PreparedStatement.class);

				PreparedStatement mainPreparedStatement = Mockito.mock(
					PreparedStatement.class);

				Mockito.when(
					underlyingConnection.prepareStatement(_SELECT_SQL)
				).thenReturn(
					mainPreparedStatement
				);

				Mockito.when(
					underlyingConnection.prepareStatement(
						Mockito.startsWith("select count(1) from ("))
				).thenReturn(
					countPreparedStatement
				);

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenThrow(
					new SQLException("count failed")
				);

				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					mainPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				Connection recorderWrappedConnection =
					UpgradeSQLRecorder.getConnectionWrapper(
						underlyingConnection, _UPGRADE_PROCESS_CLASS_NAME);

				Connection trackerWrappedConnection =
					UpgradeLogProgressTracker.wrap(
						recorderWrappedConnection, _UPGRADE_PROCESS_CLASS_NAME);

				PreparedStatement wrappedPreparedStatement =
					trackerWrappedConnection.prepareStatement(_SELECT_SQL);

				wrappedPreparedStatement.executeQuery();

				for (UpgradeSQLRecorder.FailedSQL failedSQL :
						UpgradeSQLRecorder.getFailedSQLs()) {

					Assert.assertFalse(
						failedSQL.getSQL(
						).contains(
							"select count(1)"
						));
				}

				for (UpgradeSQLRecorder.RunningSQL runningSQL :
						UpgradeSQLRecorder.getRunningSQLs()) {

					Assert.assertFalse(
						runningSQL.getSQL(
						).contains(
							"select count(1)"
						));
				}
			}
			finally {
				UpgradeSQLRecorder.stop();
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testCountQueryFailureDegrades() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenThrow(
					new SQLException("count failed")
				);

				ResultSet resultSet = _mockResultSet();

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet =
					wrappedPreparedStatement.executeQuery();

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
					_UPGRADE_PROCESS_CLASS_NAME, resultSet);

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
				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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

				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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

				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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
	public void testNextDropsTotalWhenRowCountExceedsIt() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				ResultSet countResultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenReturn(
					countResultSet
				);

				Mockito.when(
					countResultSet.next()
				).thenReturn(
					true
				);

				Mockito.when(
					countResultSet.getLong(1)
				).thenReturn(
					10L
				);

				ResultSet resultSet = _mockResultSet();

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet =
					wrappedPreparedStatement.executeQuery();

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);
				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_rowCount", 20L);

				Assert.assertTrue(wrappedResultSet.next());

				String registryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_registryKey");

				Mockito.verify(
					log
				).info(
					registryKey + " is still executing. Processed 21 rows."
				);

				Map<String, Long> lastKnownTotalCounts =
					UpgradeLogProgressTracker.getLastKnownTotalCounts();

				Assert.assertNull(lastKnownTotalCounts.get(registryKey));
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
						_UPGRADE_PROCESS_CLASS_NAME;

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
				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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
				String upgradeProcessClassName = _UPGRADE_PROCESS_CLASS_NAME;

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
	public void testNextLogsWithTotalAndPercentage() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				ResultSet countResultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					countPreparedStatement.executeQuery()
				).thenReturn(
					countResultSet
				);

				Mockito.when(
					countResultSet.next()
				).thenReturn(
					true
				);

				Mockito.when(
					countResultSet.getLong(1)
				).thenReturn(
					200L
				);

				ResultSet resultSet = _mockResultSet();

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				ResultSet wrappedResultSet =
					wrappedPreparedStatement.executeQuery();

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
					registryKey +
						" is still executing. Processed 1 of 200 rows. (0%)"
				);

				Map<String, Long> lastKnownTotalCounts =
					UpgradeLogProgressTracker.getLastKnownTotalCounts();

				Assert.assertEquals(
					Long.valueOf(200L), lastKnownTotalCounts.get(registryKey));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testParameterMirroringWhitelist() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_WITH_PARAMETER_SQL);

				PreparedStatement countPreparedStatement = statements[0];

				PreparedStatement wrappedPreparedStatement = statements[1];

				wrappedPreparedStatement.setInt(1, RandomTestUtil.randomInt());
				wrappedPreparedStatement.setLong(
					2, RandomTestUtil.randomLong());
				wrappedPreparedStatement.setString(
					3, RandomTestUtil.randomString());
				wrappedPreparedStatement.setNull(4, Types.INTEGER);
				wrappedPreparedStatement.setBoolean(5, true);

				Mockito.verify(
					countPreparedStatement
				).setInt(
					Mockito.eq(1), Mockito.anyInt()
				);

				Mockito.verify(
					countPreparedStatement
				).setLong(
					Mockito.eq(2), Mockito.anyLong()
				);

				Mockito.verify(
					countPreparedStatement
				).setString(
					Mockito.eq(3), Mockito.anyString()
				);

				Mockito.verify(
					countPreparedStatement
				).setNull(
					4, Types.INTEGER
				);

				Mockito.verify(
					countPreparedStatement
				).setBoolean(
					5, true
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testSafeSetObjectWithScalarMirrors() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_WITH_PARAMETER_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				Long value = RandomTestUtil.randomLong();

				wrappedPreparedStatement.setObject(1, value);

				Mockito.verify(
					countPreparedStatement
				).setObject(
					1, value
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
	public void testStatementCloseFinishesTrackedResultSets() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", 1L)) {

			UpgradeLogProgressTracker.start();

			try {
				ResultSet resultSet = _mockResultSet();

				ResultSet wrappedResultSet = _wrapResultSet(
					_UPGRADE_PROCESS_CLASS_NAME, resultSet);

				ReflectionTestUtil.setFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_lastLogTime", 0L);

				Assert.assertTrue(wrappedResultSet.next());

				String registryKey = ReflectionTestUtil.getFieldValue(
					ProxyUtil.getInvocationHandler(wrappedResultSet),
					"_registryKey");

				Map<String, Long> lastKnownProgresses =
					UpgradeLogProgressTracker.getLastKnownProgresses();

				Assert.assertEquals(
					Long.valueOf(1L), lastKnownProgresses.get(registryKey));

				Statement wrappedStatement = wrappedResultSet.getStatement();

				wrappedStatement.close();

				Assert.assertNull(lastKnownProgresses.get(registryKey));
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testStatementConfigSettersNotMirrored() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_SQL);

				PreparedStatement countPreparedStatement = statements[0];

				PreparedStatement wrappedPreparedStatement = statements[1];

				wrappedPreparedStatement.setFetchSize(100);
				wrappedPreparedStatement.setMaxRows(50);
				wrappedPreparedStatement.setQueryTimeout(30);

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).setFetchSize(
					Mockito.anyInt()
				);

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).setMaxRows(
					Mockito.anyInt()
				);

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).setQueryTimeout(
					Mockito.anyInt()
				);
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testUnsafeSetObjectWithStreamSkipsCount() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_WITH_PARAMETER_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				InputStream inputStream = new ByteArrayInputStream(
					new byte[] {1, 2});

				wrappedPreparedStatement.setObject(1, inputStream);

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).setObject(
					Mockito.anyInt(), Mockito.any()
				);

				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				wrappedPreparedStatement.executeQuery();

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).executeQuery();
			}
			finally {
				UpgradeLogProgressTracker.stop();
			}
		}
	}

	@Test
	public void testUnsafeStreamSetterSkipsCount() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			try {
				PreparedStatement[] statements = _setUpPreparedMirror(
					_SELECT_WITH_PARAMETER_SQL);

				PreparedStatement countPreparedStatement = statements[0];
				PreparedStatement wrappedPreparedStatement = statements[1];

				InputStream inputStream = new ByteArrayInputStream(
					new byte[] {1, 2});

				wrappedPreparedStatement.setBinaryStream(1, inputStream);

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).setBinaryStream(
					Mockito.anyInt(), Mockito.any()
				);

				ResultSet resultSet = Mockito.mock(ResultSet.class);

				Mockito.when(
					wrappedPreparedStatement.executeQuery()
				).thenReturn(
					resultSet
				);

				wrappedPreparedStatement.executeQuery();

				Mockito.verify(
					countPreparedStatement, Mockito.never()
				).executeQuery();
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
					connection, _UPGRADE_PROCESS_CLASS_NAME);

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
					connection, _UPGRADE_PROCESS_CLASS_NAME);

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

	private String _invokeBuildCountSQL(String sql) throws Exception {
		Method method = UpgradeLogProgressTracker.class.getDeclaredMethod(
			"_buildCountSQL", String.class);

		method.setAccessible(true);

		return (String)method.invoke(null, sql);
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

	private PreparedStatement[] _setUpPreparedMirror(String sql)
		throws Exception {

		Connection connection = Mockito.mock(Connection.class);

		PreparedStatement countPreparedStatement = Mockito.mock(
			PreparedStatement.class);

		PreparedStatement mainPreparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			connection.prepareStatement(sql)
		).thenReturn(
			mainPreparedStatement
		);

		Mockito.when(
			connection.prepareStatement(
				Mockito.startsWith("select count(1) from ("))
		).thenReturn(
			countPreparedStatement
		);

		Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
			connection, _UPGRADE_PROCESS_CLASS_NAME);

		PreparedStatement wrappedPreparedStatement =
			wrappedConnection.prepareStatement(sql);

		return new PreparedStatement[] {
			countPreparedStatement, wrappedPreparedStatement
		};
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

	private static final String _SELECT_SQL = "select * from Foo";

	private static final String _SELECT_WITH_PARAMETER_SQL =
		"select * from Foo where x = ?";

	private static final String _UPGRADE_PROCESS_CLASS_NAME =
		"com.liferay.test.SampleUpgradeProcess";

	private Log _originalLog;

}