/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.recorder;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DBManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
		_dbManager = Mockito.mock(DBManager.class);

		Mockito.when(
			_dbManager.getDBType()
		).thenReturn(
			DBType.MYSQL
		);

		DBManagerUtil.setDBManager(_dbManager);

		_originalLog = ReflectionTestUtil.getFieldValue(
			UpgradeLogProgressTracker.class, "_log");
	}

	@After
	public void tearDown() {
		DBManagerUtil.setDBManager(null);

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

	@Test
	public void testCaptureProgressCorrectsTotalWhenCountDrops()
		throws Exception {

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			long driftedCount = _TOTAL_ROW_COUNT / 2;

			ResultSet wrappedResultSet = _executeQueryWithCount(
				_TOTAL_ROW_COUNT, driftedCount);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 2, driftedCount + 2);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testCaptureProgressLogsSQLOnceAcrossIntervals()
		throws Exception {

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			Mockito.when(
				log.isDebugEnabled()
			).thenReturn(
				true
			);

			UpgradeLogProgressTracker.start();

			ResultSet wrappedResultSet = _executeQueryWithCount(
				_TOTAL_ROW_COUNT);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			Mockito.verify(
				log, Mockito.times(1)
			).debug(
				StringBundler.concat(
					_getProgressId(wrappedResultSet), " is iterating SQL: ",
					_SELECT_SQL)
			);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testCaptureProgressLogsTentativeTotal() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			ResultSet wrappedResultSet = _executeQueryWithCount(
				_TOTAL_ROW_COUNT);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 1, _TOTAL_ROW_COUNT + 1);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testCaptureProgressLogsTotalWhenCountIsStable()
		throws Exception {

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			ResultSet wrappedResultSet = _executeQueryWithCount(
				_TOTAL_ROW_COUNT, _TOTAL_ROW_COUNT);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 2, _TOTAL_ROW_COUNT);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testClearParametersResetsUnsafeFlag() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_WITH_PARAMETER_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

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
				1, new ByteArrayInputStream(RandomTestUtil.randomBytes()));

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

			_resetLogTime(wrappedResultSet);

			wrappedResultSet.next();

			Mockito.verify(
				countPreparedStatement, Mockito.atLeastOnce()
			).executeQuery();
		}
		finally {
			UpgradeLogProgressTracker.stop();
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
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertLastKnownProgress(1, wrappedResultSet);

			wrappedResultSet.close();
			wrappedResultSet.close();

			_assertLastKnownProgress(1, wrappedResultSet);

			Mockito.verify(
				log, Mockito.times(1)
			).info(
				_getProgressId(wrappedResultSet) + " is finished."
			);
		}
		finally {
			UpgradeLogProgressTracker.stop();
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

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

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

	@Test
	public void testConcurrentResultSetIterationDoesNotCorruptProgresses()
		throws Exception {

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			List<Callable<Void>> tasks = new ArrayList<>();

			for (int i = 0; i < _THREAD_COUNT; i++) {
				tasks.add(
					() -> {
						for (int j = 0; j < _ITERATIONS_PER_THREAD; j++) {
							ResultSet resultSet = _mockSingleRowResultSet();

							ResultSet wrappedResultSet = _wrapResultSet(
								resultSet, _UPGRADE_PROCESS_CLASS_NAME);

							_resetLogTime(wrappedResultSet);

							Assert.assertTrue(wrappedResultSet.next());
							Assert.assertFalse(wrappedResultSet.next());

							wrappedResultSet.close();
						}

						return null;
					});
			}

			ExecutorService executorService = Executors.newFixedThreadPool(
				_THREAD_COUNT);

			try {
				for (Future<Void> future :
						executorService.invokeAll(
							tasks, _AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {

					future.get();
				}
			}
			finally {
				executorService.shutdownNow();
			}

			Map<String, Long> lastKnownProgresses =
				UpgradeLogProgressTracker.getLastKnownProgresses();

			Assert.assertTrue(lastKnownProgresses.isEmpty());
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testCountQueryAppliesQueryTimeout() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

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

			_resetLogTime(wrappedResultSet);

			wrappedResultSet.next();

			Mockito.verify(
				countPreparedStatement, Mockito.atLeastOnce()
			).setQueryTimeout(
				_COUNT_QUERY_TIMEOUT_SECONDS
			);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testCountQueryDoesNotPolluteSqlRecorder() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable thresholdSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_REPORT_SQL_STATEMENT_THRESHOLD",
					_SQL_STATEMENT_THRESHOLD)) {

			UpgradeLogProgressTracker.start();
			UpgradeSQLRecorder.start();

			Connection connection = Mockito.mock(Connection.class);

			PreparedStatement countPreparedStatement = Mockito.mock(
				PreparedStatement.class);

			PreparedStatement preparedStatement = Mockito.mock(
				PreparedStatement.class);

			Mockito.when(
				connection.prepareStatement(_SELECT_SQL)
			).thenReturn(
				preparedStatement
			);

			Mockito.when(
				connection.prepareStatement(
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
				preparedStatement.executeQuery()
			).thenReturn(
				resultSet
			);

			Connection recorderWrappedConnection =
				UpgradeSQLRecorder.getConnectionWrapper(
					connection, _UPGRADE_PROCESS_CLASS_NAME);

			Connection trackerWrappedConnection =
				UpgradeLogProgressTracker.wrap(
					recorderWrappedConnection, _UPGRADE_PROCESS_CLASS_NAME);

			PreparedStatement wrappedPreparedStatement =
				trackerWrappedConnection.prepareStatement(_SELECT_SQL);

			wrappedPreparedStatement.executeQuery();

			for (UpgradeSQLRecorder.FailedSQL failedSQL :
					UpgradeSQLRecorder.getFailedSQLs()) {

				String sql = failedSQL.getSQL();

				Assert.assertFalse(sql.contains("select count(1)"));
			}

			for (UpgradeSQLRecorder.RunningSQL runningSQL :
					UpgradeSQLRecorder.getRunningSQLs()) {

				String sql = runningSQL.getSQL();

				Assert.assertFalse(sql.contains("select count(1)"));
			}
		}
		finally {
			UpgradeSQLRecorder.stop();
			UpgradeLogProgressTracker.stop();
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
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			String columnName = RandomTestUtil.randomString();
			String columnValue = RandomTestUtil.randomString();

			ResultSet resultSet = Mockito.mock(ResultSet.class);

			Mockito.when(
				resultSet.getString(columnName)
			).thenReturn(
				columnValue
			);

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

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

	@Test
	public void testGetCountSQL() throws Exception {
		Assert.assertEquals(
			"select count(1) from (select * from Foo) count_",
			_invokeGetCountSQL(_SELECT_SQL));

		Assert.assertNull(_invokeGetCountSQL("update Foo set x = 1"));

		Assert.assertNull(_invokeGetCountSQL("selectivity_score"));

		String countSQL = _invokeGetCountSQL("select * from Foo order by id");

		String lowerCaseCountSQL = StringUtil.toLowerCase(countSQL);

		Assert.assertTrue(countSQL.contains("count_"));

		Assert.assertTrue(lowerCaseCountSQL.contains("order by id"));

		String countSQLWithSemicolon = _invokeGetCountSQL("select * from Foo;");

		Assert.assertFalse(countSQLWithSemicolon.contains(";) count_"));
	}

	@Test
	public void testGetCountSQLStripsOrderByOnSQLServer() throws Exception {
		Mockito.when(
			_dbManager.getDBType()
		).thenReturn(
			DBType.SQLSERVER
		);

		String countSQL = _invokeGetCountSQL("select * from Foo order by id");

		String lowerCaseCountSQL = StringUtil.toLowerCase(countSQL);

		Assert.assertFalse(lowerCaseCountSQL.contains("order by id"));

		Assert.assertTrue(countSQL.contains("count_"));

		String countSQLWithSemicolon = _invokeGetCountSQL(
			"select * from Foo order by id;");

		Assert.assertFalse(countSQLWithSemicolon.contains("order by id;"));
	}

	@Test
	public void testNestedResultSets() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet innerResultSet = _mockResultSet();
			ResultSet outerResultSet = _mockResultSet();

			Connection connection = Mockito.mock(Connection.class);

			Statement innerStatement = Mockito.mock(Statement.class);
			Statement outerStatement = Mockito.mock(Statement.class);

			Mockito.when(
				connection.createStatement()
			).thenReturn(
				innerStatement, outerStatement
			);

			Mockito.when(
				innerStatement.executeQuery(Mockito.anyString())
			).thenReturn(
				innerResultSet
			);

			Mockito.when(
				outerStatement.executeQuery(Mockito.anyString())
			).thenReturn(
				outerResultSet
			);

			Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
				connection, _UPGRADE_PROCESS_CLASS_NAME);

			Statement wrappedInnerStatement =
				wrappedConnection.createStatement();

			ResultSet wrappedInnerResultSet =
				wrappedInnerStatement.executeQuery("inner");

			Statement wrappedOuterStatement =
				wrappedConnection.createStatement();

			ResultSet wrappedOuterResultSet =
				wrappedOuterStatement.executeQuery("outer");

			_resetLogTime(wrappedInnerResultSet);

			_resetLogTime(wrappedOuterResultSet);

			Assert.assertTrue(wrappedInnerResultSet.next());
			Assert.assertTrue(wrappedOuterResultSet.next());

			Assert.assertNotEquals(
				_getProgressId(wrappedInnerResultSet),
				_getProgressId(wrappedOuterResultSet));

			_assertLastKnownProgress(1, wrappedInnerResultSet);
			_assertLastKnownProgress(1, wrappedOuterResultSet);

			wrappedInnerResultSet.close();

			_assertLastKnownProgress(1, wrappedInnerResultSet);
			_assertLastKnownProgress(1, wrappedOuterResultSet);

			wrappedOuterResultSet.close();

			_assertLastKnownProgress(1, wrappedOuterResultSet);
		}
		finally {
			UpgradeLogProgressTracker.stop();
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

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

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

	@Test
	public void testNextDoesNotLogOnEndOfResultSet() throws Exception {
		Log log = _getLog();

		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = Mockito.mock(ResultSet.class);

			Mockito.when(
				resultSet.next()
			).thenReturn(
				false
			);

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

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

	@Test
	public void testNextDropsTotalWhenRowCountExceedsTotal() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_SQL);

			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

			ResultSet resultSet = _mockResultSet();

			Mockito.when(
				wrappedPreparedStatement.executeQuery()
			).thenReturn(
				resultSet
			);

			ResultSet wrappedResultSet =
				wrappedPreparedStatement.executeQuery();

			_resetLogTime(wrappedResultSet);

			Object invocationHandler = ProxyUtil.getInvocationHandler(
				wrappedResultSet);

			ReflectionTestUtil.setFieldValue(
				invocationHandler, "_firstCount", _TOTAL_ROW_COUNT);
			ReflectionTestUtil.setFieldValue(
				invocationHandler, "_rowCount", _TOTAL_ROW_COUNT);
			ReflectionTestUtil.setFieldValue(
				invocationHandler, "_totalRowCount", _TOTAL_ROW_COUNT);
			ReflectionTestUtil.setFieldValue(
				invocationHandler, "_totalRowCountComputed", true);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, _TOTAL_ROW_COUNT + 1);

			Map<String, Long> lastKnownTotalCounts =
				UpgradeLogProgressTracker.getLastKnownTotalCounts();

			Assert.assertNull(
				lastKnownTotalCounts.get(_getProgressId(wrappedResultSet)));
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testNextLogReachesLogger() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				UpgradeLogProgressTracker.class.getName(),
				LoggerTestUtil.WARN)) {

			UpgradeLogProgressTracker.start();

			logCapture.resetPriority(LoggerTestUtil.INFO);

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			String progressId = _getProgressId(wrappedResultSet);

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				progressId + " is still executing. Processed 1 rows.",
				logEntry.getMessage());
		}
		finally {
			UpgradeLogProgressTracker.stop();
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
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 1);
			_assertLastKnownProgress(1, wrappedResultSet);
		}
		finally {
			UpgradeLogProgressTracker.stop();
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
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 1);
			_assertProgress(log, wrappedResultSet, 2);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testNextOmitsTotalWhenCountQueryFails() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			Log log = _getLog();

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

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

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertProgress(log, wrappedResultSet, 1);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testNextRemovesProgressOnEndOfResultSet() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = Mockito.mock(ResultSet.class);

			Mockito.when(
				resultSet.next()
			).thenReturn(
				true, false
			);

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertLastKnownProgress(1, wrappedResultSet);

			Assert.assertFalse(wrappedResultSet.next());

			Map<String, Long> lastKnownProgresses =
				UpgradeLogProgressTracker.getLastKnownProgresses();

			Assert.assertTrue(lastKnownProgresses.isEmpty());
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testSetObjectWithScalarIsAppliedToCountStatement()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_WITH_PARAMETER_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

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

	@Test
	public void testStartClearsProgresses() throws Exception {
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

			UpgradeLogProgressTracker.start();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Granular progress logging for upgrades is enabled. This may " +
					"decrease performance.",
				logEntry.getMessage());
			Assert.assertEquals("WARN", logEntry.getPriority());
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testStatementCloseFinishesTrackedResultSets() throws Exception {
		try (SafeCloseable enabledSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true);
			SafeCloseable intervalSafeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_INTERVAL", _LOG_PROGRESS_INTERVAL)) {

			UpgradeLogProgressTracker.start();

			ResultSet resultSet = _mockResultSet();

			ResultSet wrappedResultSet = _wrapResultSet(
				resultSet, _UPGRADE_PROCESS_CLASS_NAME);

			_resetLogTime(wrappedResultSet);

			Assert.assertTrue(wrappedResultSet.next());

			_assertLastKnownProgress(1, wrappedResultSet);

			Statement statement = wrappedResultSet.getStatement();

			statement.close();

			_assertLastKnownProgress(1, wrappedResultSet);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testStatementConfigSettersAreNotAppliedToCountStatement()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];

			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

			wrappedPreparedStatement.setFetchSize(RandomTestUtil.randomInt());
			wrappedPreparedStatement.setMaxRows(RandomTestUtil.randomInt());
			wrappedPreparedStatement.setQueryTimeout(
				RandomTestUtil.randomInt());

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

	@Test
	public void testUnsafeSetObjectWithStreamSkipsCount() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_WITH_PARAMETER_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

			InputStream inputStream = new ByteArrayInputStream(
				RandomTestUtil.randomBytes());

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

	@Test
	public void testUnsafeStreamSetterSkipsCount() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_WITH_PARAMETER_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];
			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

			InputStream inputStream = new ByteArrayInputStream(
				RandomTestUtil.randomBytes());

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

	@Test
	public void testWhitelistedSettersAreAppliedToCountStatement()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

			UpgradeLogProgressTracker.start();

			PreparedStatement[] preparedStatements = _mockPreparedStatements(
				_SELECT_WITH_PARAMETER_SQL);

			PreparedStatement countPreparedStatement = preparedStatements[0];

			PreparedStatement wrappedPreparedStatement = preparedStatements[1];

			wrappedPreparedStatement.setBoolean(1, true);
			wrappedPreparedStatement.setInt(2, RandomTestUtil.randomInt());
			wrappedPreparedStatement.setLong(3, RandomTestUtil.randomLong());
			wrappedPreparedStatement.setNull(4, Types.INTEGER);
			wrappedPreparedStatement.setString(
				5, RandomTestUtil.randomString());

			Mockito.verify(
				countPreparedStatement
			).setBoolean(
				1, true
			);

			Mockito.verify(
				countPreparedStatement
			).setInt(
				Mockito.eq(2), Mockito.anyInt()
			);

			Mockito.verify(
				countPreparedStatement
			).setLong(
				Mockito.eq(3), Mockito.anyLong()
			);

			Mockito.verify(
				countPreparedStatement
			).setNull(
				4, Types.INTEGER
			);

			Mockito.verify(
				countPreparedStatement
			).setString(
				Mockito.eq(5), Mockito.anyString()
			);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testWrapDisabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", false)) {

			UpgradeLogProgressTracker.start();

			Connection connection = Mockito.mock(Connection.class);

			Connection wrappedConnection = UpgradeLogProgressTracker.wrap(
				connection, _UPGRADE_PROCESS_CLASS_NAME);

			Assert.assertSame(connection, wrappedConnection);
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	@Test
	public void testWrapEnabled() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_LOG_PROGRESS_ENABLED", true)) {

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

			Statement wrappedStatement = wrappedConnection.createStatement();

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

			ResultSet preparedStatementResultSet =
				preparedStatement.executeQuery();

			Assert.assertTrue(
				ProxyUtil.isProxyClass(preparedStatementResultSet.getClass()));

			Mockito.when(
				connection.prepareCall(Mockito.anyString())
			).thenReturn(
				Mockito.mock(CallableStatement.class)
			);

			CallableStatement callableStatement = wrappedConnection.prepareCall(
				"call test()");

			Assert.assertTrue(
				ProxyUtil.isProxyClass(callableStatement.getClass()));

			Mockito.when(
				callableStatement.executeQuery()
			).thenReturn(
				Mockito.mock(ResultSet.class)
			);

			ResultSet callableStatementResultSet =
				callableStatement.executeQuery();

			Assert.assertTrue(
				ProxyUtil.isProxyClass(callableStatementResultSet.getClass()));
		}
		finally {
			UpgradeLogProgressTracker.stop();
		}
	}

	private void _assertLastKnownProgress(
		long expectedRowCount, ResultSet resultSet) {

		Map<String, Long> lastKnownProgresses =
			UpgradeLogProgressTracker.getLastKnownProgresses();

		Assert.assertEquals(
			Long.valueOf(expectedRowCount),
			lastKnownProgresses.get(_getProgressId(resultSet)));
	}

	private void _assertProgress(Log log, ResultSet resultSet, long rowCount) {
		Mockito.verify(
			log
		).info(
			StringBundler.concat(
				_getProgressId(resultSet), " is still executing. Processed ",
				rowCount, " rows.")
		);
	}

	private void _assertProgress(
		Log log, ResultSet resultSet, long rowCount, long totalCount) {

		long percentage = (rowCount * 100L) / totalCount;

		String progressId = _getProgressId(resultSet);

		Mockito.verify(
			log
		).info(
			StringBundler.concat(
				progressId, " is still executing. Processed ", rowCount, " of ",
				totalCount, " rows. (", percentage, "%)")
		);

		Map<String, Long> lastKnownTotalCounts =
			UpgradeLogProgressTracker.getLastKnownTotalCounts();

		Assert.assertEquals(
			Long.valueOf(totalCount), lastKnownTotalCounts.get(progressId));
	}

	private ResultSet _executeQueryWithCount(long... counts) throws Exception {
		PreparedStatement[] preparedStatements = _mockPreparedStatements(
			_SELECT_SQL);

		PreparedStatement countPreparedStatement = preparedStatements[0];
		PreparedStatement wrappedPreparedStatement = preparedStatements[1];

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

		Long[] additionalCounts = new Long[counts.length - 1];

		for (int i = 0; i < additionalCounts.length; i++) {
			additionalCounts[i] = counts[i + 1];
		}

		Mockito.when(
			countResultSet.getLong(1)
		).thenReturn(
			counts[0], additionalCounts
		);

		ResultSet resultSet = _mockResultSet();

		Mockito.when(
			wrappedPreparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);

		return wrappedPreparedStatement.executeQuery();
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

	private String _getProgressId(ResultSet resultSet) {
		return ReflectionTestUtil.getFieldValue(
			ProxyUtil.getInvocationHandler(resultSet), "_progressId");
	}

	private String _invokeGetCountSQL(String sql) throws Exception {
		Method method = UpgradeLogProgressTracker.class.getDeclaredMethod(
			"_getCountSQL", String.class);

		method.setAccessible(true);

		return (String)method.invoke(null, sql);
	}

	private PreparedStatement[] _mockPreparedStatements(String sql)
		throws Exception {

		Connection connection = Mockito.mock(Connection.class);

		PreparedStatement countPreparedStatement = Mockito.mock(
			PreparedStatement.class);

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			connection.prepareStatement(sql)
		).thenReturn(
			preparedStatement
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

	private ResultSet _mockResultSet() throws Exception {
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			resultSet.next()
		).thenReturn(
			true
		);

		return resultSet;
	}

	private ResultSet _mockSingleRowResultSet() throws Exception {
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			resultSet.next()
		).thenReturn(
			true, false
		);

		return resultSet;
	}

	private void _resetLogTime(ResultSet resultSet) {
		ReflectionTestUtil.setFieldValue(
			ProxyUtil.getInvocationHandler(resultSet), "_lastLogTime", 0L);
	}

	private ResultSet _wrapResultSet(
			ResultSet resultSet, String upgradeProcessClassName)
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

	private static final int _AWAIT_TIMEOUT_SECONDS = 30;

	private static final int _COUNT_QUERY_TIMEOUT_SECONDS = 10;

	private static final int _ITERATIONS_PER_THREAD = 10;

	private static final long _LOG_PROGRESS_INTERVAL = 1L;

	private static final String _SELECT_SQL = "select * from Foo";

	private static final String _SELECT_WITH_PARAMETER_SQL =
		"select * from Foo where x = ?";

	private static final long _SQL_STATEMENT_THRESHOLD = 0L;

	private static final int _THREAD_COUNT = 4;

	private static final long _TOTAL_ROW_COUNT = 200L;

	private static final String _UPGRADE_PROCESS_CLASS_NAME =
		"com.liferay.test.SampleUpgradeProcess";

	private DBManager _dbManager;
	private Log _originalLog;

}