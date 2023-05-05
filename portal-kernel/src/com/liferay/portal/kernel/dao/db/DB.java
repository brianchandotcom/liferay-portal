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

import com.liferay.petra.function.UnsafeConsumer;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import javax.naming.NamingException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface DB {

	public static final int SQL_SIZE_NONE = -1;

	public static final int SQL_VARCHAR_MAX_SIZE = Integer.MAX_VALUE;

	public static final int SQL_VARCHAR_MAX_SIZE_THRESHOLD = 9999999;

	public void addIndexes(
			Connection connection, List<IndexMetadata> indexMetadatas)
		throws IOException, SQLException;

	public void alterColumnName(
			Connection connection, String tableName, String oldColumnName,
			String newColumnDefinition)
		throws Exception;

	public void alterColumnType(
			Connection connection, String tableName, String columnName,
			String newColumnType)
		throws Exception;

	public void alterTableAddColumn(
			Connection connection, String tableName, String columnName,
			String columnType)
		throws Exception;

	public void alterTableDropColumn(
			Connection connection, String tableName, String columnName)
		throws Exception;

	public String buildSQL(String template) throws IOException, SQLException;

	public void copyTableRows(
			Connection connection, String sourceTableName,
			String targetTableName)
		throws Exception;

	public void copyTableStructure(
			Connection connection, String tableName, String newTableName)
		throws Exception;

	public List<IndexMetadata> dropIndexes(
			Connection connection, String tableName, String columnName)
		throws IOException, SQLException;

	public DBType getDBType();

	public List<Index> getIndexes(Connection connection) throws SQLException;

	public ResultSet getIndexResultSet(Connection connection, String tableName)
		throws SQLException;

	public int getMajorVersion();

	public int getMinorVersion();

	public default String getNewUuidFunctionName() {
		return null;
	}

	public String getPopulateSQL(String databaseName, String sqlContent);

	public String[] getPrimaryKeyColumnNames(
			Connection connection, String tableName)
		throws SQLException;

	public String getRecreateSQL(String databaseName);

	public Integer getSQLType(String templateType);

	public Integer getSQLVarcharSize(String templateType);

	public String getTemplateBlob();

	public String getTemplateFalse();

	public String getTemplateTrue();

	public String getVersionString();

	public boolean isSupportsAlterColumnName();

	public boolean isSupportsAlterColumnType();

	public boolean isSupportsInlineDistinct();

	public default boolean isSupportsNewUuidFunction() {
		return false;
	}

	public boolean isSupportsQueryingAfterException();

	public boolean isSupportsScrollableResults();

	public boolean isSupportsStringCaseSensitiveQuery();

	public boolean isSupportsUpdateWithInnerJoin();

	public void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception;

	public void removePrimaryKey(Connection connection, String tableName)
		throws Exception;

	public default void runSQL(
			Connection connection, DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		String sql = dbTypeToSQLMap.get(getDBType());

		runSQL(connection, new String[] {sql});
	}

	public void runSQL(Connection connection, String sql)
		throws IOException, SQLException;

	public void runSQL(Connection connection, String[] sqls)
		throws IOException, SQLException;

	public default void runSQL(DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		String sql = dbTypeToSQLMap.get(getDBType());

		runSQL(new String[] {sql});
	}

	public void runSQL(String sql) throws IOException, SQLException;

	public void runSQL(String[] sqls) throws IOException, SQLException;

	public void runSQLTemplateString(
			Connection connection, String template, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery);

	public void updateIndexes(
			Connection connection, String tablesSQL, String indexesSQL,
			boolean dropStaleIndexes)
		throws Exception;

}