/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.sql.Connection;
import java.sql.SQLException;

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
public class DataCleanupPreupgradeProcessUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		DataCleanupPreupgradeProcessUtil.disableCache();
	}

	@Test
	public void testGetPrimaryKeyColumnName() throws Exception {
		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class)) {

			DB db = Mockito.mock(DB.class);

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDB
			).thenReturn(
				db
			);

			Connection connection1 = Mockito.mock(Connection.class);

			String tableName = RandomTestUtil.randomString();

			Mockito.when(
				db.getPrimaryKeyColumnNames(connection1, tableName)
			).thenReturn(
				new String[0]
			);

			Connection connection2 = Mockito.mock(Connection.class);

			String primaryKeyColumnName = RandomTestUtil.randomString();

			Mockito.when(
				db.getPrimaryKeyColumnNames(connection2, tableName)
			).thenReturn(
				new String[] {primaryKeyColumnName}
			);

			String catalog1 = RandomTestUtil.randomString();
			String catalog2 = RandomTestUtil.randomString();
			String schema1 = RandomTestUtil.randomString();
			String schema2 = RandomTestUtil.randomString();

			DBInspector dbInspector1 = _mockDBInspector(catalog1, schema1);
			DBInspector dbInspector2 = _mockDBInspector(catalog2, schema1);
			DBInspector dbInspector3 = _mockDBInspector(catalog1, schema2);

			_testGetPrimaryKeyColumnName(
				connection1, connection2, db, dbInspector1, dbInspector2, null,
				primaryKeyColumnName, tableName);
			_testGetPrimaryKeyColumnName(
				connection2, connection1, db, dbInspector2, dbInspector1,
				primaryKeyColumnName, null, tableName);
			_testGetPrimaryKeyColumnName(
				connection1, connection2, db, dbInspector1, dbInspector3, null,
				primaryKeyColumnName, tableName);
			_testGetPrimaryKeyColumnName(
				connection2, connection1, db, dbInspector3, dbInspector1,
				primaryKeyColumnName, null, tableName);

			DBInspector dbInspector4 = Mockito.mock(DBInspector.class);

			Mockito.when(
				dbInspector4.getCatalog()
			).thenThrow(
				new SQLException()
			);

			Assert.assertThrows(
				SQLException.class,
				() -> DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
					connection1, dbInspector4, tableName));
		}
	}

	private DBInspector _mockDBInspector(String catalog, String schema)
		throws Exception {

		DBInspector dbInspector = Mockito.mock(DBInspector.class);

		Mockito.when(
			dbInspector.getCatalog()
		).thenReturn(
			catalog
		);

		Mockito.when(
			dbInspector.getSchema()
		).thenReturn(
			schema
		);

		return dbInspector;
	}

	private void _testGetPrimaryKeyColumnName(
			Connection connection1, Connection connection2, DB db,
			DBInspector dbInspector1, DBInspector dbInspector2,
			String expectedPrimaryKeyColumnName1,
			String expectedPrimaryKeyColumnName2, String tableName)
		throws Exception {

		DataCleanupPreupgradeProcessUtil.enableCache();

		Mockito.clearInvocations(db);

		Assert.assertEquals(
			expectedPrimaryKeyColumnName1,
			DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
				connection1, dbInspector1, tableName));
		Assert.assertEquals(
			expectedPrimaryKeyColumnName1,
			DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
				connection1, dbInspector1, tableName));
		Assert.assertEquals(
			expectedPrimaryKeyColumnName2,
			DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
				connection2, dbInspector2, tableName));
		Assert.assertEquals(
			expectedPrimaryKeyColumnName2,
			DataCleanupPreupgradeProcessUtil.getPrimaryKeyColumnName(
				connection2, dbInspector2, tableName));

		Mockito.verify(
			db, Mockito.times(1)
		).getPrimaryKeyColumnNames(
			connection1, tableName
		);

		Mockito.verify(
			db, Mockito.times(1)
		).getPrimaryKeyColumnNames(
			connection2, tableName
		);

		DataCleanupPreupgradeProcessUtil.disableCache();
	}

}