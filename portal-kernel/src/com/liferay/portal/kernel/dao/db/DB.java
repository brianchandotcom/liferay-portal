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

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface DB {

	public static final int BARE = 0;

	public static final int DEFAULT = 1;

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

	public default String getNewUuidFunctionName() {
		return null;
	}

	public Integer getSQLType(String templateType);

	public String getTemplateBlob();

	public String getTemplateFalse();

	public String getTemplateTrue();

	public String getVersionString();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment()}
	 */
	@Deprecated
	public long increment();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment(String)}
	 */
	@Deprecated
	public long increment(String name);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment(String, int)}
	 */
	@Deprecated
	public long increment(String name, int size);

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

	public default void runSQL(Connection con, DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		String sql = dbTypeToSQLMap.get(getDBType());

		runSQL(con, new String[] {sql});
	}

	public void runSQL(Connection con, String sql)
		throws IOException, SQLException;

	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException;

	public default void runSQL(DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		String sql = dbTypeToSQLMap.get(getDBType());

		runSQL(new String[] {sql});
	}

	public void runSQL(String sql) throws IOException, SQLException;

	public void runSQL(String[] sqls) throws IOException, SQLException;

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(
			Connection connection, String template, boolean failOnError)
		throws IOException, NamingException, SQLException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #runSQLTemplateString(Connection, String, boolean)}
	 */
	@Deprecated
	public void runSQLTemplateString(
			Connection connection, String template, boolean evaluate,
			boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #runSQLTemplateString(String, boolean)}
	 */
	@Deprecated
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