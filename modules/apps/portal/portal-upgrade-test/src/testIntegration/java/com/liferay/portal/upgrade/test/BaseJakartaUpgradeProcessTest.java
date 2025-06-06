/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.BaseJakartaUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class BaseJakartaUpgradeProcessTest extends BaseJakartaUpgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_companyLocalService.forEachCompany(
			company -> _db.runSQL(
				StringBundler.concat(
					"create table ", _TABLE_NAME,
					" (mvccVersion LONG default 0 not null, uuid_ VARCHAR(75) ",
					"not null, ", _COLUMN_NAME_1, " TEXT null, ",
					_COLUMN_NAME_2, " VARCHAR(255) null, ", _COLUMN_NAME_3,
					" STRING null, ", _COLUMN_NAME_4,
					" TEXT null, primary key (mvccVersion, uuid_))")));
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("drop table " + _TABLE_NAME));
	}

	@Test
	public void testUpgradeWithCustomSeparators() throws Exception {
		_initialString = "import javax$portlet$Portlet";
		_resultString = "import jakarta$portlet$Portlet";

		_insertInitialData();

		_testUpgrade(
			new BaseJakartaUpgradeProcessTest() {

				@Override
				public char[] getCustomSeparators() {
					return new char[] {'$'};
				}

			});
	}

	@Test
	public void testUpgradeWithoutCustomSeparators() throws Exception {
		_initialString = "import javax.portlet.Portlet";
		_resultString = "import jakarta.portlet.Portlet";

		_insertInitialData();

		_testUpgrade(this);
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{_TABLE_NAME, _COLUMN_NAME_1}, {_TABLE_NAME, _COLUMN_NAME_2},
			{_TABLE_NAME, _COLUMN_NAME_3}, {_TABLE_NAME, _COLUMN_NAME_4}
		};
	}

	private void _assertLogEntry(
		String expectedMessage, Set<String> expectedKeys, String logEntry) {

		Assert.assertTrue(logEntry, logEntry.contains(expectedMessage));

		for (String key : expectedKeys) {
			Assert.assertTrue(logEntry, logEntry.contains(key));
		}
	}

	private void _insertInitialData() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"insert into ", _TABLE_NAME, " (mvccVersion, uuid_, ",
						_COLUMN_NAME_1, ", ", _COLUMN_NAME_2, ", ",
						_COLUMN_NAME_3, ") values (0, 'uuid1', '",
						_initialString, "', '", _initialString, "', '",
						_initialString, "')"));

				_db.runSQL(
					StringBundler.concat(
						"insert into ", _TABLE_NAME, " (mvccVersion, uuid_, ",
						_COLUMN_NAME_1, ", ", _COLUMN_NAME_2,
						") values (1, 'uuid2', '", _initialString, "', '",
						_initialString, "')"));
			});
	}

	private void _testUpgrade(UpgradeProcess upgradeProcess) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BaseJakartaUpgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			upgradeProcess.upgrade();

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select * from " + _TABLE_NAME + " order by uuid_ asc");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(0, resultSet.getLong(1));
				Assert.assertEquals("uuid1", resultSet.getString(2));
				Assert.assertEquals(
					_resultString, resultSet.getString(_COLUMN_NAME_1));
				Assert.assertEquals(
					_resultString, resultSet.getString(_COLUMN_NAME_2));
				Assert.assertEquals(
					_resultString, resultSet.getString(_COLUMN_NAME_3));
				Assert.assertNull(resultSet.getString(_COLUMN_NAME_4));

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(1, resultSet.getLong(1));
				Assert.assertEquals("uuid2", resultSet.getString(2));
				Assert.assertEquals(
					_resultString, resultSet.getString(_COLUMN_NAME_1));
				Assert.assertEquals(
					_resultString, resultSet.getString(_COLUMN_NAME_2));
				Assert.assertNull(resultSet.getString(_COLUMN_NAME_3));
				Assert.assertNull(resultSet.getString(_COLUMN_NAME_4));

				Assert.assertFalse(resultSet.next());
			}

			List<LogEntry> logEntries = logCapture.getLogEntries();

			int logEntriesSize = 4;

			if (DBPartition.isPartitionEnabled()) {
				logEntriesSize *= PortalInstancePool.getCompanyIds().length;
			}

			Assert.assertEquals(
				logEntries.toString(), logEntriesSize, logEntries.size());

			AtomicInteger i = new AtomicInteger();

			long[] companyIds = ReflectionTestUtil.invoke(
				PortalInstancePool.class, "_getCompanyIdsBySQL", null);

			String companyIdMessage = "";

			for (long companyId : companyIds) {
				if (DBPartition.isPartitionEnabled()) {
					companyIdMessage = " for company " + companyId;
				}

				_assertLogEntry(
					StringBundler.concat(
						"Table ", _TABLE_NAME, " column ", _COLUMN_NAME_1,
						companyIdMessage,
						" has been updated for records with primary keys ",
						"(mvccVersion, uuid_): "),
					new HashSet<>(Arrays.asList("(0, uuid1)", "(1, uuid2)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());

				_assertLogEntry(
					StringBundler.concat(
						"Table ", _TABLE_NAME, " column ", _COLUMN_NAME_2,
						companyIdMessage,
						" has been updated for records with primary keys ",
						"(mvccVersion, uuid_): "),
					new HashSet<>(Arrays.asList("(0, uuid1)", "(1, uuid2)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());

				_assertLogEntry(
					StringBundler.concat(
						"Table ", _TABLE_NAME, " column ", _COLUMN_NAME_3,
						companyIdMessage,
						" has been updated for records with primary keys ",
						"(mvccVersion, uuid_): "),
					new HashSet<>(Arrays.asList("(0, uuid1)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());

				_assertLogEntry(
					StringBundler.concat(
						"Table ", _TABLE_NAME, " column ", _COLUMN_NAME_4,
						companyIdMessage,
						" has not been updated for any record"),
					new HashSet<>(),
					logEntries.get(
						i.getAndIncrement()
					).toString());
			}
		}
	}

	private static final String _COLUMN_NAME_1 = "script1";

	private static final String _COLUMN_NAME_2 = "script2";

	private static final String _COLUMN_NAME_3 = "script3";

	private static final String _COLUMN_NAME_4 = "script4";

	private static final String _TABLE_NAME = "BaseJakartaUpgradeProcessTest";

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static DB _db;

	private String _initialString;
	private String _resultString;

}