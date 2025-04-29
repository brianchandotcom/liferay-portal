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
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.BaseJakartaUpgradeProcess;
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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_companyLocalService.forEachCompany(
			company -> {
				_db.runSQL(
					StringBundler.concat(
						"create table ", _TABLE_NAME,
						" (mvccVersion LONG default 0 not null, uuid_ ",
						"VARCHAR(75) not null, ", _COLUMN_NAME_1,
						" TEXT null, ", _COLUMN_NAME_2, " VARCHAR(255) null, ",
						_COLUMN_NAME_3,
						" STRING null, primary key (mvccVersion, uuid_))"));

				_db.runSQL(
					StringBundler.concat(
						"insert into ", _TABLE_NAME, " (mvccVersion, uuid_, ",
						_COLUMN_NAME_1, ", ", _COLUMN_NAME_2, ", ",
						_COLUMN_NAME_3, ") values (0, 'uuid1', 'import ",
						"javax.portlet.Portlet', 'import ",
						"javax.portlet.Portlet', 'import ",
						"javax.portlet.Portlet')"));

				_db.runSQL(
					StringBundler.concat(
						"insert into ", _TABLE_NAME, " (mvccVersion, uuid_, ",
						_COLUMN_NAME_1, ", ", _COLUMN_NAME_2,
						") values (1, 'uuid2', 'import ",
						"javax.servlet.http.HttpServlet', 'import ",
						"javax.servlet.http.HttpServlet')"));
			});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _db.runSQL("drop table " + _TABLE_NAME));
	}

	@Test
	public void testUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BaseJakartaUpgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			upgrade();

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement("select * from " + _TABLE_NAME);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(0, resultSet.getLong(1));
				Assert.assertEquals("uuid1", resultSet.getString(2));
				Assert.assertEquals(
					"import jakarta.portlet.Portlet",
					resultSet.getString(_COLUMN_NAME_1));
				Assert.assertEquals(
					"import jakarta.portlet.Portlet",
					resultSet.getString(_COLUMN_NAME_2));
				Assert.assertEquals(
					"import jakarta.portlet.Portlet",
					resultSet.getString(_COLUMN_NAME_3));

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(1, resultSet.getLong(1));
				Assert.assertEquals("uuid2", resultSet.getString(2));
				Assert.assertEquals(
					"import jakarta.servlet.http.HttpServlet",
					resultSet.getString(_COLUMN_NAME_1));
				Assert.assertEquals(
					"import jakarta.servlet.http.HttpServlet",
					resultSet.getString(_COLUMN_NAME_2));
				Assert.assertNull(resultSet.getString(_COLUMN_NAME_3));

				Assert.assertFalse(resultSet.next());
			}

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 6, logEntries.size());

			AtomicInteger i = new AtomicInteger();

			long[] companyIds = ReflectionTestUtil.invoke(
				PortalInstancePool.class, "_getCompanyIdsBySQL", null);

			for (long companyId : companyIds) {
				_assertLogEntry(
					StringBundler.concat(
						"Table/column ", _TABLE_NAME, "/", _COLUMN_NAME_1,
						" for company ", companyId,
						" has been upgraded for next IDs:"),
					new HashSet<>(Arrays.asList("(0, uuid1)", "(1, uuid2)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());

				_assertLogEntry(
					StringBundler.concat(
						"Table/column ", _TABLE_NAME, "/", _COLUMN_NAME_2,
						" for company ", companyId,
						" has been upgraded for next IDs: "),
					new HashSet<>(Arrays.asList("(0, uuid1)", "(1, uuid2)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());

				_assertLogEntry(
					StringBundler.concat(
						"Table/column ", _TABLE_NAME, "/", _COLUMN_NAME_3,
						" for company ", companyId,
						" has been upgraded for next IDs: "),
					new HashSet<>(Arrays.asList("(0, uuid1)")),
					logEntries.get(
						i.getAndIncrement()
					).toString());
			}
		}
	}

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{_TABLE_NAME, _COLUMN_NAME_1}, {_TABLE_NAME, _COLUMN_NAME_2},
			{_TABLE_NAME, _COLUMN_NAME_3}
		};
	}

	private void _assertLogEntry(
		String expectedMessage, Set<String> expectedKeys, String logEntry) {

		Assert.assertTrue(logEntry, logEntry.contains(expectedMessage));

		for (String key : expectedKeys) {
			Assert.assertTrue(logEntry, logEntry.contains(key));
		}
	}

	private static final String _COLUMN_NAME_1 = "script1";

	private static final String _COLUMN_NAME_2 = "script2";

	private static final String _COLUMN_NAME_3 = "script3";

	private static final String _TABLE_NAME = "BaseJakartaUpgradeProcessTest";

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static DB _db;

}