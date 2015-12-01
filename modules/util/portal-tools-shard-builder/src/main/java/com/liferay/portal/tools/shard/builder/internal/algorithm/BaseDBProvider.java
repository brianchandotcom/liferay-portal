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

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import com.liferay.portal.tools.shard.builder.exporter.ShardExporter;
import com.liferay.portal.tools.shard.builder.exporter.context.ExportContext;
import com.liferay.portal.tools.shard.builder.internal.DBProvider;
import com.liferay.portal.tools.shard.builder.internal.util.DBManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseDBProvider
	implements DBExporter, DBProvider, ShardExporter {

	public BaseDBProvider(Properties dbProperties) {
		this.dbProperties = dbProperties;

		HikariConfig properties = new HikariConfig(this.dbProperties);

		_dataSource = new HikariDataSource(properties);
	}

	@Override
	public void export(ExportContext exportContext) {
		ExportProcess exportProcess = new ExportProcess(this);

		try {
			exportProcess.export(exportContext);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateInsert(
			OutputStream outputStream, String tableName, String[] fields)
		throws IOException {

		if ((fields == null) || (fields.length == 0)) {
			throw new IllegalArgumentException("Fields cannot be empty");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append(" VALUES(");

		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];

			sb.append(field);

			if (i != (fields.length - 1)) {
				sb.append(", ");
			}
		}

		sb.append(")");

		String insert = sb.toString() + ";\n";

		outputStream.write(insert.getBytes());
	}

	@Override
	public List<String> getControlTableNames(String schemaName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<String> tableNames = new ArrayList<>();

		try {
			DataSource dataSource = getDataSource();

			con = dataSource.getConnection();

			ps = con.prepareStatement(getControlTablesSql(schemaName));

			rs = ps.executeQuery();

			while (rs.next()) {
				tableNames.add(rs.getString(getTableNameFieldName()));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			_dbManager.cleanUp(con, ps, rs);
		}

		return tableNames;
	}

	public abstract String getControlTablesSql(String schema);

	public DataSource getDataSource() {
		return _dataSource;
	}

	public String getDateTimeFormat() {
		return "YYYY-MM-DD HH:MM:SS";
	}

	@Override
	public List<String> getPartitionedTableNames(String schemaName) {
		return Collections.emptyList();
	}

	@Override
	public String serializeTableField(Object field) throws SQLException {
		StringBuilder sb = new StringBuilder(3);

		if ((field instanceof Date) || (field instanceof Timestamp)) {
			sb.append("'");
			sb.append(formatDateTime(field));
			sb.append("'");
		}
		else {
			sb.append("'");
			sb.append(field);
			sb.append("'");
		}

		return sb.toString();
	}

	@Override
	public void write(String tableName, OutputStream outputStream) {
		String sql = "SELECT * FROM " + tableName;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DataSource dataSource = getDataSource();

			con = dataSource.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				String[] fields = new String[columnCount];

				for (int i = 0; i < columnCount; i++) {
					fields[i] = serializeTableField(rs.getObject(i + 1));
				}

				generateInsert(outputStream, tableName, fields);
			}
		}
		catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			_dbManager.cleanUp(con, ps, rs);
		}
	}

	protected String formatDateTime(Object date) {
		DateFormat simpleDateFormat = new SimpleDateFormat(getDateTimeFormat());

		return simpleDateFormat.format(date);
	}

	protected final Properties dbProperties;

	private final DataSource _dataSource;
	private final DBManager _dbManager = new DBManager();

}