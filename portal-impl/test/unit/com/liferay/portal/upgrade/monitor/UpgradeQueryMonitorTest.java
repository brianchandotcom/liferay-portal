/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.monitor;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class UpgradeQueryMonitorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		UpgradeQueryMonitor.stop();
	}

	@Test
	public void testPoll() throws Exception {
		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				UpgradeQueryMonitor.class.getName(), LoggerTestUtil.WARN)) {

			Connection connection = Mockito.mock(Connection.class);

			DataSource dataSource = Mockito.mock(DataSource.class);

			Mockito.when(
				dataSource.getConnection()
			).thenReturn(
				connection
			);

			DB db = Mockito.mock(DB.class);

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDB
			).thenReturn(
				db
			);

			String id1 = RandomTestUtil.randomString();
			String id2 = RandomTestUtil.randomString();
			String id3 = RandomTestUtil.randomString();
			String id4 = RandomTestUtil.randomString();
			String query1 = RandomTestUtil.randomString();
			String query2 = RandomTestUtil.randomString();
			String query3 = RandomTestUtil.randomString();
			String query4 = RandomTestUtil.randomString();

			String expectedMessage1 = StringBundler.concat(
				"Query \"", query1, "\" (id ", id1,
				"), which has been running for 30 seconds, is locked.");
			String expectedMessage2 = StringBundler.concat(
				"Query \"", query2, "\" (id ", id2,
				"), which has been running for 300 seconds, is locked.");
			String expectedMessage3 = StringBundler.concat(
				"Query \"", query3, "\" (id ", id3,
				"), which has been running for 600 seconds, is locked.");
			String expectedMessage4 = StringBundler.concat(
				"Query \"", query4, "\" (id ", id4,
				"), which has been running for 900 seconds, is locked.");

			DataSource originalDataSource = InfrastructureUtil.getDataSource();

			InfrastructureUtil.setDataSource(dataSource);

			try {
				Mockito.when(
					db.getLockedQueryInfos(connection)
				).thenReturn(
					Collections.emptyList()
				);

				ReflectionTestUtil.invoke(
					UpgradeQueryMonitor.class, "_poll", new Class<?>[0]);

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertTrue(logEntries.isEmpty());

				Mockito.when(
					db.getLockedQueryInfos(connection)
				).thenReturn(
					Collections.singletonList(
						new DB.QueryInfo(
							30000, id1, query1, RandomTestUtil.randomString(),
							RandomTestUtil.randomString()))
				);

				ReflectionTestUtil.invoke(
					UpgradeQueryMonitor.class, "_poll", new Class<?>[0]);

				logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry1 = logEntries.get(0);

				Assert.assertEquals(expectedMessage1, logEntry1.getMessage());
				Assert.assertEquals("WARN", logEntry1.getPriority());

				Mockito.when(
					db.getLockedQueryInfos(connection)
				).thenReturn(
					Arrays.asList(
						new DB.QueryInfo(
							300000, id2, query2, RandomTestUtil.randomString(),
							RandomTestUtil.randomString()),
						new DB.QueryInfo(
							600000, id3, query3, RandomTestUtil.randomString(),
							RandomTestUtil.randomString()),
						new DB.QueryInfo(
							900000, id4, query4, RandomTestUtil.randomString(),
							RandomTestUtil.randomString()))
				);

				ReflectionTestUtil.invoke(
					UpgradeQueryMonitor.class, "_poll", new Class<?>[0]);

				logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 4, logEntries.size());

				LogEntry logEntry2 = logEntries.get(1);
				LogEntry logEntry3 = logEntries.get(2);
				LogEntry logEntry4 = logEntries.get(3);

				Assert.assertEquals(expectedMessage2, logEntry2.getMessage());
				Assert.assertEquals(expectedMessage3, logEntry3.getMessage());
				Assert.assertEquals(expectedMessage4, logEntry4.getMessage());
			}
			finally {
				InfrastructureUtil.setDataSource(originalDataSource);
			}
		}
	}

	@Test
	public void testPollDisablesMonitoringOnSQLException() throws Exception {
		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				UpgradeQueryMonitor.class.getName(), LoggerTestUtil.WARN)) {

			Connection connection = Mockito.mock(Connection.class);

			DataSource dataSource = Mockito.mock(DataSource.class);

			Mockito.when(
				dataSource.getConnection()
			).thenReturn(
				connection
			);

			DB db = Mockito.mock(DB.class);

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDB
			).thenReturn(
				db
			);

			String exceptionMessage = RandomTestUtil.randomString();

			Mockito.when(
				db.getLockedQueryInfos(connection)
			).thenThrow(
				new SQLException(exceptionMessage)
			);

			DataSource originalDataSource = InfrastructureUtil.getDataSource();

			InfrastructureUtil.setDataSource(dataSource);

			try {
				Assert.assertThrows(
					SQLException.class,
					() -> ReflectionTestUtil.invoke(
						UpgradeQueryMonitor.class, "_poll", new Class<?>[0]));

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry1 = logEntries.get(0);

				Assert.assertEquals(
					StringBundler.concat(
						"Upgrade query monitoring was disabled. Unable to ",
						"detect locked queries: ", exceptionMessage),
					logEntry1.getMessage());
			}
			finally {
				InfrastructureUtil.setDataSource(originalDataSource);
			}
		}
	}

	@Test
	public void testStart() throws Exception {

		// disabled

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_QUERY_MONITOR_ENABLED", false)) {

			UpgradeQueryMonitor.start();

			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE));
		}

		// enabled

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_QUERY_MONITOR_ENABLED", true)) {

			UpgradeQueryMonitor.start();

			ScheduledExecutorService scheduledExecutorService =
				ReflectionTestUtil.getFieldValue(
					UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE);

			Assert.assertNotNull(scheduledExecutorService);

			UpgradeQueryMonitor.start();

			Assert.assertSame(
				scheduledExecutorService,
				ReflectionTestUtil.getFieldValue(
					UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE));
		}
	}

	@Test
	public void testStop() throws Exception {

		// not started

		UpgradeQueryMonitor.stop();

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE));

		// started

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"UPGRADE_QUERY_MONITOR_ENABLED", true)) {

			UpgradeQueryMonitor.start();

			Assert.assertNotNull(
				ReflectionTestUtil.getFieldValue(
					UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE));

			UpgradeQueryMonitor.stop();

			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					UpgradeQueryMonitor.class, _SCHEDULED_EXECUTOR_SERVICE));
		}
	}

	private static final String _SCHEDULED_EXECUTOR_SERVICE =
		"_scheduledExecutorService";

}