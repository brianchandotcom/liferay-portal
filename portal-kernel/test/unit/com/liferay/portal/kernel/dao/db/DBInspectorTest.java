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

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.liferay.portal.kernel.util.StringUtil;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Mariano Alvaro Saiz
 */
@RunWith(MockitoJUnitRunner.class)
public class DBInspectorTest {

	@Test
	public void testNoQueryNeededToCheckIfColumnExists() throws Exception {
		_givenTableWithColumn(_TABLE_NAME, _COLUMN_NAME);

		DBInspector dbInspector = new DBInspector(_connection);

		dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME);

		verify(_preparedStatement, never()).executeQuery();
	}

	private void _givenTableWithColumn(String tableName, String columnName)
		throws SQLException {

		_givenTableWithOrWithoutColumn(tableName, columnName, true);
	}

	private void _givenTableWithNoColumn(String tableName, String columnName)
		throws SQLException {

		_givenTableWithOrWithoutColumn(tableName, columnName, false);
	}

	private void _givenTableWithOrWithoutColumn(
			String tableName, String columnName, boolean withColumn)
		throws SQLException {

		when(_resultSet.next()).thenReturn(withColumn);
		when(_resultSet.getMetaData()).thenReturn(_resultSetMetadata);
		when(_preparedStatement.executeQuery()).thenReturn(_resultSet);
		when(_databaseMetadata.storesLowerCaseIdentifiers()).thenReturn(true);
		when(
			_databaseMetadata.getColumns(
				anyString(), anyString(), eq(StringUtil.toLowerCase(tableName)),
				eq(columnName))).thenReturn(_resultSet);
		when(
			_connection.prepareStatement(
				anyString())).thenReturn(_preparedStatement);
		when(_connection.getMetaData()).thenReturn(_databaseMetadata);
	}

	private static final String _COLUMN_NAME = "column_name";

	private static final String _TABLE_NAME = "table_name";

	@Mock
	private Connection _connection;

	@Mock
	private DatabaseMetaData _databaseMetadata;

	@Mock
	private PreparedStatement _preparedStatement;

	@Mock
	private ResultSet _resultSet;

	@Mock
	private ResultSetMetaData _resultSetMetadata;

}