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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Douglas Wong
 */
public class VerifySQLServer extends VerifyProcess {

	protected void convertColumnsToUnicode() {
		dropNonunicodeTableIndexes();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(12);

			sb.append("select sysobjects.name as table_name, syscolumns.name ");
			sb.append("AS column_name, systypes.name as data_type, ");
			sb.append("syscolumns.length, syscolumns.isnullable as ");
			sb.append("is_nullable FROM sysobjects inner join syscolumns on ");
			sb.append("sysobjects.id = syscolumns.id inner join systypes on ");
			sb.append("syscolumns.xtype = systypes.xtype where ");
			sb.append("(sysobjects.xtype = 'U') and (sysobjects.category != ");
			sb.append("2) and ");
			sb.append(_FILTER_NONUNICODE_DATA_TYPES);
			sb.append(" and ");
			sb.append(_FILTER_EXCLUDED_TABLES);
			sb.append(" order by sysobjects.name, syscolumns.colid");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString("table_name");

				if (!isPortalTableName(tableName)) {
					continue;
				}

				String columnName = rs.getString("column_name");
				String dataType = rs.getString("data_type");
				int length = rs.getInt("length");
				boolean nullable = rs.getBoolean("is_nullable");

				if (dataType.equals("varchar")) {
					convertVarcharColumn(
						tableName, columnName, length, nullable);
				}
				else if (dataType.equals("ntext") || dataType.equals("text")) {
					convertTextColumn(tableName, columnName, nullable);
				}
			}

			for (String addPrimaryKeySQL : _addPrimaryKeySQLs) {
				runSQL(addPrimaryKeySQL);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void convertTextColumn(
			String tableName, String columnName, boolean nullable)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updating " + tableName + "." + columnName +" to use " +
					"nvarchar(max)");
		}

		StringBundler sb = new StringBundler(4);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" add temp nvarchar(max)");

		if (!nullable) {
			sb.append(" not null");
		}

		runSQL(sb.toString());

		runSQL("update " + tableName + " set temp = " + columnName);

		runSQL("alter table " + tableName + " drop column " + columnName);

		runSQL(
			"exec sp_rename \'" + tableName + ".temp\', \'" + columnName +
				"\', \'column\'");
	}

	protected void convertVarcharColumn(
			String tableName, String columnName, int length, boolean nullable)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updating " + tableName + "." + columnName +
					" to use nvarchar");
		}

		StringBundler sb = new StringBundler(8);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" alter column ");
		sb.append(columnName);
		sb.append(" nvarchar(");

		if (length == -1) {
			sb.append("max");
		}
		else {
			sb.append(length);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		if (!nullable) {
			sb.append(" not null");
		}

		runSQL(sb.toString());
	}

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_SQLSERVER)) {
			return;
		}

		convertColumnsToUnicode();
	}

	protected void dropNonunicodeTableIndexes() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(15);

			sb.append("select distinct sysobjects.name as table_name, ");
			sb.append("sysindexes.name as index_name FROM sysobjects inner ");
			sb.append("join sysindexes on sysobjects.id = sysindexes.id ");
			sb.append("inner join syscolumns on sysobjects.id = ");
			sb.append("syscolumns.id inner join sysindexkeys on ");
			sb.append("((sysobjects.id = sysindexkeys.id) and ");
			sb.append("(syscolumns.colid = sysindexkeys.colid) and ");
			sb.append("(sysindexes.indid = sysindexkeys.indid)) inner join ");
			sb.append("systypes on syscolumns.xtype = systypes.xtype where ");
			sb.append("(sysobjects.type = 'U') and (sysobjects.category != ");
			sb.append("2) and ");
			sb.append(_FILTER_NONUNICODE_DATA_TYPES);
			sb.append(" and ");
			sb.append(_FILTER_EXCLUDED_TABLES);
			sb.append(" order by sysobjects.name, sysindexes.name");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString("table_name");

				if (!isPortalTableName(tableName)) {
					continue;
				}

				String indexName = rs.getString("index_name");

				if (_log.isInfoEnabled()) {
					_log.info("Dropping index " + tableName + "." + indexName);
				}

				String indexNameUpperCase = StringUtil.toUpperCase(indexName);

				if (indexNameUpperCase.startsWith("PK")) {
					String primaryKeyColumnNames = StringUtil.merge(
						getPrimaryKeyColumnNames(indexName));

					runSQL(
						"alter table " + tableName + " drop constraint " +
							indexName);

					_addPrimaryKeySQLs.add(
						"alter table " + tableName + " add primary key (" +
							primaryKeyColumnNames + ")");
				}
				else {
					runSQL("drop index " + indexName + " on " + tableName);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected List<String> getPrimaryKeyColumnNames(String indexName) {
		List<String> columnNames = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(10);

			sb.append("select distinct syscolumns.name as column_name from ");
			sb.append("sysobjects inner join syscolumns on sysobjects.id = ");
			sb.append("syscolumns.id inner join sysindexes on ");
			sb.append("sysobjects.id = sysindexes.id inner join sysindexkeys ");
			sb.append("on ((sysobjects.id = sysindexkeys.id) and ");
			sb.append("(syscolumns.colid = sysindexkeys.colid) and ");
			sb.append("(sysindexes.indid = sysindexkeys.indid)) where ");
			sb.append("sysindexes.name = '");
			sb.append(indexName);
			sb.append("'");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String columnName = rs.getString("column_name");

				columnNames.add(columnName);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return columnNames;
	}

	private static final String _FILTER_EXCLUDED_TABLES =
		"(sysobjects.name not like 'Counter') and (sysobjects.name not like " +
			"'QUARTZ%')";

	private static final String _FILTER_NONUNICODE_DATA_TYPES =
		"((systypes.name = 'ntext') OR (systypes.name = 'text') OR " +
			"(systypes.name = 'varchar'))";

	private static final Log _log = LogFactoryUtil.getLog(
		VerifySQLServer.class);

	private final List<String> _addPrimaryKeySQLs = new ArrayList<>();

}