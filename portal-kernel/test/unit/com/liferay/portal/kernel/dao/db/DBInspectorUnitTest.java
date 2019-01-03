/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(MockitoJUnitRunner.class)
public class DBInspectorUnitTest {

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

	private void _mockTableWithColumn(String tableName, String columnName)
		throws SQLException {

		_mockTableWithOrWithoutColumn(tableName, columnName, true);
	}

	private void _mockTableWithOrWithoutColumn(
			String tableName, String columnName, boolean hasColumn)
		throws SQLException {

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_connection.prepareStatement(Mockito.anyString())
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
				Mockito.anyString(), Mockito.anyString(),
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
		throws SQLException {

		_mockTableWithOrWithoutColumn(tableName, columnName, false);
	}

	private static final String _COLUMN_NAME = "column_name";

	private static final String _TABLE_NAME = "table_name";

	@Mock
	private Connection _connection;

	@Mock
	private DatabaseMetaData _databaseMetaData;

	@Mock
	private PreparedStatement _preparedStatement;

	@Mock
	private ResultSet _resultSet;

}