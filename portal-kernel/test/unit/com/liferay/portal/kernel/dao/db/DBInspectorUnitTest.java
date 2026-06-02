/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBInspectorUnitTest {

	@After
	public void tearDown() {
		DBInspector.clearSchemaSnapshot();
	}

	@Test
	public void testArgumentMetaDataIsUsedToNormalizeName() throws Exception {
		DBInspector dbInspector = new DBInspector(_connection);

		DatabaseMetaData databaseMetaData = Mockito.mock(
			DatabaseMetaData.class);

		Mockito.when(
			databaseMetaData.storesLowerCaseIdentifiers()
		).thenReturn(
			true
		);

		dbInspector.normalizeName(_TABLE_NAME, databaseMetaData);

		Mockito.verify(
			_connection, Mockito.never()
		).getMetaData();

		Mockito.verify(
			databaseMetaData, Mockito.times(1)
		).storesLowerCaseIdentifiers();
	}

	@Test
	public void testHasColumnIsCaseInsensitive() throws Exception {
		_mockTableWithColumn(_TABLE_NAME, StringUtil.toLowerCase(_COLUMN_NAME));

		DBInspector dbInspector = new DBInspector(_connection);

		Assert.assertTrue(
			dbInspector.hasColumn(
				_TABLE_NAME, StringUtil.toUpperCase(_COLUMN_NAME)));
	}

	@Test
	public void testHasColumnReturnsFalseWithoutExistingColumn()
		throws Exception {

		_mockTableWithoutColumn(_TABLE_NAME, _COLUMN_NAME);

		DBInspector dbInspector = new DBInspector(_connection);

		Assert.assertFalse(dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME));
	}

	@Test
	public void testHasColumnReturnsTrueWithExistingColumn() throws Exception {
		_mockTableWithColumn(_TABLE_NAME, _COLUMN_NAME);

		DBInspector dbInspector = new DBInspector(_connection);

		Assert.assertTrue(dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME));
	}

	@Test
	public void testHasColumnSkipsQueryWithExistingColumn() throws Exception {
		_mockTableWithColumn(_TABLE_NAME, _COLUMN_NAME);

		DBInspector dbInspector = new DBInspector(_connection);

		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);

		Mockito.verify(
			_preparedStatement, Mockito.never()
		).executeQuery();
	}

	@Test
	public void testHasIndexIsCaseInsensitive() throws Exception {
		String indexName = "IX_40A51197";

		try (MockedStatic<DBManagerUtil> dbManagerUtilMockedStatic =
				Mockito.mockStatic(DBManagerUtil.class)) {

			Mockito.when(
				_connection.getMetaData()
			).thenReturn(
				_databaseMetaData
			);

			Mockito.when(
				_databaseMetaData.storesLowerCaseIdentifiers()
			).thenReturn(
				true
			);

			DB db = Mockito.mock(DB.class);

			dbManagerUtilMockedStatic.when(
				DBManagerUtil::getDB
			).thenReturn(
				db
			);

			Mockito.when(
				db.getIndexResultSet(
					Mockito.eq(_connection), Mockito.anyString(),
					Mockito.anyBoolean())
			).thenReturn(
				_resultSet
			);

			Mockito.when(
				_resultSet.next()
			).thenReturn(
				true, false
			);

			Mockito.when(
				_resultSet.getString("index_name")
			).thenReturn(
				indexName
			);

			DBInspector dbInspector = new DBInspector(_connection);

			Assert.assertTrue(
				dbInspector.hasIndex(
					"friendlyurlentrylocalization", indexName));
		}
	}

	@Test
	public void testHasTableUsesSnapshotCache() throws Exception {
		Mockito.when(
			_connection.getCatalog()
		).thenReturn(
			_CATALOG_NAME
		);

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.storesLowerCaseIdentifiers()
		).thenReturn(
			true
		);

		ResultSet tablesResultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			tablesResultSet.next()
		).thenReturn(
			true, false
		);

		Mockito.when(
			tablesResultSet.getString("TABLE_NAME")
		).thenReturn(
			_TABLE_NAME
		);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.eq(_CATALOG_NAME), Mockito.nullable(String.class),
				Mockito.isNull(), Mockito.any(String[].class))
		).thenReturn(
			tablesResultSet
		);

		DBInspector dbInspector = new DBInspector(_connection);

		DBInspector.beginSchemaSnapshot();

		dbInspector.getTableNames(null);

		dbInspector.hasTable(_TABLE_NAME);
		dbInspector.hasTable(_TABLE_NAME);

		Mockito.verify(
			_databaseMetaData, Mockito.times(1)
		).getTables(
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()
		);
	}

	@Test
	public void testIsObjectTable() {
		DBInspector dbInspector = new DBInspector(_connection);

		try (MockedStatic<PortalInstancePool> portalInstancePoolMockedStatic =
				Mockito.mockStatic(PortalInstancePool.class)) {

			portalInstancePoolMockedStatic.when(
				PortalInstancePool::getCompanyIds
			).thenReturn(
				new long[] {1L}
			);

			Assert.assertTrue(dbInspector.isObjectTable("L_1_tableName"));
			Assert.assertTrue(dbInspector.isObjectTable("l_1_tableName"));
			Assert.assertTrue(dbInspector.isObjectTable("r_tableName"));
		}
	}

	@Test
	public void testIsObjectTableFilterByCompanyIds() {
		DBInspector dbInspector = new DBInspector(_connection);

		List<Long> companyIds = List.of(1L);

		Assert.assertFalse(
			dbInspector.isObjectTable(companyIds, "L_2_tableName"));
		Assert.assertFalse(
			dbInspector.isObjectTable(companyIds, "l_2_tableName"));
		Assert.assertTrue(
			dbInspector.isObjectTable(companyIds, "L_1_tableName"));
		Assert.assertTrue(
			dbInspector.isObjectTable(companyIds, "l_1_tableName"));
		Assert.assertTrue(dbInspector.isObjectTable(companyIds, "r_tableName"));
	}

	@Test
	public void testSchemaSnapshotBeginEnablesCache() throws Exception {
		_mockTableWithColumn(_TABLE_NAME, _COLUMN_NAME);

		Mockito.when(
			_connection.getCatalog()
		).thenReturn(
			_CATALOG_NAME
		);

		DBInspector dbInspector = new DBInspector(_connection);

		DBInspector.beginSchemaSnapshot();

		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);
		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);

		Mockito.verify(
			_databaseMetaData, Mockito.times(1)
		).getColumns(
			Mockito.nullable(String.class), Mockito.nullable(String.class),
			Mockito.anyString(), Mockito.anyString()
		);
	}

	@Test
	public void testSchemaSnapshotClearDisablesCache() throws Exception {
		_mockTableWithColumn(_TABLE_NAME, _COLUMN_NAME);

		Mockito.when(
			_connection.getCatalog()
		).thenReturn(
			_CATALOG_NAME
		);

		DBInspector dbInspector = new DBInspector(_connection);

		DBInspector.beginSchemaSnapshot();
		DBInspector.clearSchemaSnapshot();

		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);
		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);

		Mockito.verify(
			_databaseMetaData, Mockito.times(2)
		).getColumns(
			Mockito.nullable(String.class), Mockito.nullable(String.class),
			Mockito.anyString(), Mockito.anyString()
		);
	}

	private void _mockTableWithColumn(String tableName, String columnName)
		throws Exception {

		_mockTableWithOrWithoutColumn(tableName, columnName, true);
	}

	private void _mockTableWithOrWithoutColumn(
			String tableName, String columnName, boolean hasColumn)
		throws Exception {

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_connection.prepareStatement(Mockito.nullable(String.class))
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_databaseMetaData.getColumns(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq(StringUtil.toLowerCase(tableName)),
				Mockito.eq(columnName))
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_databaseMetaData.storesLowerCaseIdentifiers()
		).thenReturn(
			true
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			hasColumn
		);
	}

	private void _mockTableWithoutColumn(String tableName, String columnName)
		throws Exception {

		_mockTableWithOrWithoutColumn(tableName, columnName, false);
	}

	private static final String _CATALOG_NAME = "test_catalog";

	private static final String _COLUMN_NAME = "column_name";

	private static final String _TABLE_NAME = "table_name";

	private final Connection _connection = Mockito.mock(Connection.class);
	private final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	private final PreparedStatement _preparedStatement = Mockito.mock(
		PreparedStatement.class);
	private final ResultSet _resultSet = Mockito.mock(ResultSet.class);

}