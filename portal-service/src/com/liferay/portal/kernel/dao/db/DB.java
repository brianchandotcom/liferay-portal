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

import aQute.bnd.annotation.ProviderType;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface DB {

	public static final int BARE = 0;

	public static final int DEFAULT = 1;

	public static final String[] TYPE_ALL = {
		DB.TYPE_DB2, DB.TYPE_HYPERSONIC, DB.TYPE_MYSQL, DB.TYPE_ORACLE,
		DB.TYPE_POSTGRESQL, DB.TYPE_SQLSERVER, DB.TYPE_SYBASE
	};

	public static final String TYPE_DB2 = "db2";

	public static final String TYPE_HYPERSONIC = "hypersonic";

	public static final String TYPE_MYSQL = "mysql";

	public static final String TYPE_ORACLE = "oracle";

	public static final String TYPE_POSTGRESQL = "postgresql";

	public static final String TYPE_SQLSERVER = "sqlserver";

	public static final String TYPE_SYBASE = "sybase";

	public void addIndexes(
			Connection con, String indexesSQL, Set<String> validIndexNames)
		throws IOException;

	public void buildCreateFile(String sqlDir, String databaseName)
		throws IOException;

	public void buildCreateFile(
			String sqlDir, String databaseName, int population)
		throws IOException;

	public String buildSQL(String template) throws IOException;

	public void buildSQLFile(String sqlDir, String fileName) throws IOException;

	public DBType getDBType();

	public List<Index> getIndexes(Connection con) throws SQLException;

	public int getMajorVersion();

	public int getMinorVersion();

	public String getTemplateFalse();

	public String getTemplateTrue();

	public String getVersionString();

	public long increment();

	public long increment(String name);

	public boolean isSupportsAlterColumnName();

	public boolean isSupportsAlterColumnType();

	public boolean isSupportsInlineDistinct();

	public boolean isSupportsQueryingAfterException();

	public boolean isSupportsScrollableResults();

	public boolean isSupportsStringCaseSensitiveQuery();

	public boolean isSupportsUpdateWithInnerJoin();

	public void runSQL(Connection con, String sql)
		throws IOException, SQLException;

	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException;

	public void runSQL(String sql) throws IOException, SQLException;

	public void runSQL(String[] sqls) throws IOException, SQLException;

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(
			Connection connection, String template, boolean evaluate,
			boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery);

	public void updateIndexes(
			Connection con, String tablesSQL, String indexesSQL,
			boolean dropStaleIndexes)
		throws IOException, SQLException;

}