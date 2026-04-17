/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.report;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DuplicateUniqueFinderRowsCleaner;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder;

import java.nio.file.Files;
import java.nio.file.Path;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Mariano Álvaro Sáiz
 */
public class UpgradeReportTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_dataAccessMockedStatic = Mockito.mockStatic(DataAccess.class);
		_dbManagerUtilMockedStatic = Mockito.mockStatic(DBManagerUtil.class);
		_dbUpgraderMockedStatic = Mockito.mockStatic(DBUpgrader.class);

		_portalUpgradeProcessMockedStatic = Mockito.mockStatic(
			PortalUpgradeProcess.class);

		Connection connection = Mockito.mock(Connection.class);
		DatabaseMetaData databaseMetaData = Mockito.mock(
			DatabaseMetaData.class);
		ResultSet tablesResultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			connection.getMetaData()
		).thenReturn(
			databaseMetaData
		);

		Mockito.when(
			databaseMetaData.getTables(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			tablesResultSet
		);

		Mockito.when(
			tablesResultSet.next()
		).thenReturn(
			false
		);

		_dataAccessMockedStatic.when(
			DataAccess::getConnection
		).thenReturn(
			connection
		);

		DB db = Mockito.mock(DB.class);

		_dbManagerUtilMockedStatic.when(
			DBManagerUtil::getDB
		).thenReturn(
			db
		);

		Mockito.when(
			db.getDBType()
		).thenReturn(
			DBType.MYSQL
		);

		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		_dataAccessMockedStatic.close();
		_dbManagerUtilMockedStatic.close();
		_dbUpgraderMockedStatic.close();
		_portalUpgradeProcessMockedStatic.close();
	}

	@Test
	public void testGetReportDataDiagnostics() {
		_dataAccessMockedStatic.when(
			DataAccess::getConnection
		).thenReturn(
			Mockito.mock(Connection.class)
		);

		Mockito.when(
			_upgradeRecorder.getDataCleanUpMessages()
		).thenReturn(
			HashMapBuilder.<String, Map<String, Integer>>put(
				DuplicateUniqueFinderRowsCleaner.class.getName(),
				HashMapBuilder.put(
					"Deleted row from TestTable due to duplicate values", 1
				).build()
			).build()
		);

		Mockito.when(
			_upgradeRecorder.getUpgradeProcessMessages()
		).thenReturn(
			HashMapBuilder.<String, ArrayList<String>>put(
				UpgradeProcess.class.getName(),
				new ArrayList<String>() {
					{
						add(
							"Completed upgrade process a.test.UpgradeTest in " +
								"10000 ms");
						add(
							"Completed upgrade process com.test.UpgradeTest " +
								"in 20000 ms");
						add(
							StringBundler.concat(
								"Completed upgrade process ",
								"com.test.UpgradeTestTable - Modifying table ",
								"TestTable to alter the type of the ",
								"TestColumn testColumnName to testTypeName ",
								"null in 30000 ms"));
					}
				}
			).build()
		);

		Map<String, Object> reportDataDiagnostics = ReflectionTestUtil.invoke(
			new UpgradeReport(), "_getReportDataDiagnostics",
			new Class<?>[] {UpgradeRecorder.class}, _upgradeRecorder);

		List<?> dataCleanUp = (List<?>)reportDataDiagnostics.get(
			"data.clean.up");

		Assert.assertEquals(
			StringBundler.concat(
				"Class name: ",
				DuplicateUniqueFinderRowsCleaner.class.getName(),
				"\n\tDeleted row from TestTable due to duplicate values\n"),
			dataCleanUp.get(
				0
			).toString());

		List<?> runningUpgradeProcesses = (List<?>)reportDataDiagnostics.get(
			"longest.upgrade.processes");

		Assert.assertEquals(
			"com.test.UpgradeTestTable took 30000 ms to complete\n",
			runningUpgradeProcesses.get(
				0
			).toString());
		Assert.assertEquals(
			"com.test.UpgradeTest took 20000 ms to complete\n",
			runningUpgradeProcesses.get(
				1
			).toString());
		Assert.assertEquals(
			"a.test.UpgradeTest took 10000 ms to complete\n",
			runningUpgradeProcesses.get(
				2
			).toString());
		Assert.assertEquals(
			runningUpgradeProcesses.toString(), 3,
			runningUpgradeProcesses.size());

		List<String> actualKeys = new ArrayList<>(
			reportDataDiagnostics.keySet());

		List<String> expectedKeys = List.of(
			"execution.date", "execution.time", "errors", "failed.sqls",
			"warnings", "longest.upgrade.processes", "longest.running.sqls",
			"data.clean.up");

		Assert.assertEquals(expectedKeys, actualKeys);
	}

	@Test
	public void testGetReportDataWhenDLRootDirIsInvalid() throws Exception {
		Path tempFilePath = Files.createTempFile(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try {
			UpgradeReport upgradeReport = new UpgradeReport();

			ReflectionTestUtil.setFieldValue(
				upgradeReport, "_rootDir",
				tempFilePath.toAbsolutePath(
				).toString());

			Map<String, Object> reportData = ReflectionTestUtil.invoke(
				upgradeReport, "_getReportData",
				new Class<?>[] {UpgradeRecorder.class}, _upgradeRecorder);

			Map<String, Object> documentLibrary =
				(Map<String, Object>)reportData.get("document.library");

			Assert.assertNotNull(documentLibrary);

			Assert.assertEquals(
				"Unable to determine", documentLibrary.get("storage.size"));
		}
		finally {
			Files.deleteIfExists(tempFilePath);
		}
	}

	@Test
	public void testGetTableCounts() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		ResultSet countResultSet = Mockito.mock(ResultSet.class);
		DatabaseMetaData databaseMetaData = Mockito.mock(
			DatabaseMetaData.class);
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet tablesResultSet = Mockito.mock(ResultSet.class);

		_dataAccessMockedStatic.when(
			DataAccess::getConnection
		).thenReturn(
			connection
		);

		Mockito.when(
			connection.getMetaData()
		).thenReturn(
			databaseMetaData
		);

		Mockito.when(
			connection.prepareStatement(Mockito.anyString())
		).thenReturn(
			preparedStatement
		);

		Mockito.when(
			countResultSet.getLong("count")
		).thenReturn(
			3000000000L
		);

		Mockito.when(
			countResultSet.next()
		).thenReturn(
			true
		);

		Mockito.when(
			databaseMetaData.getTables(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			tablesResultSet
		);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			countResultSet
		);

		Mockito.when(
			tablesResultSet.getString("TABLE_NAME")
		).thenReturn(
			"HugeTable"
		);

		Mockito.when(
			tablesResultSet.next()
		).thenReturn(
			true, false
		);

		Map<String, Long> tableCounts = ReflectionTestUtil.invoke(
			new UpgradeReport(), "_getTableCounts", new Class<?>[0]);

		Assert.assertEquals(tableCounts.toString(), 1, tableCounts.size());
		Assert.assertEquals(
			tableCounts.toString(), Long.valueOf(3000000000L),
			tableCounts.get("HugeTable"));
	}

	private MockedStatic<DataAccess> _dataAccessMockedStatic;
	private MockedStatic<DBManagerUtil> _dbManagerUtilMockedStatic;
	private MockedStatic<DBUpgrader> _dbUpgraderMockedStatic;
	private MockedStatic<PortalUpgradeProcess>
		_portalUpgradeProcessMockedStatic;

	@Mock
	private UpgradeRecorder _upgradeRecorder;

}