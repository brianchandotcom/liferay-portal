/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Map;

import org.apache.derby.tools.ij;

/**
 * @author Brian Wing Shun Chan
 */
public class DBLoader {

	public static void loadHypersonic(Connection con, String fileName)
		throws Exception {

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(fileName));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("//")) {
				sb.append(line);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sql =
						StringUtil.replace(
							sql,
							new String[] {"\\\"", "\\\\", "\\n", "\\r"},
							new String[] {"\"", "\\", "\\u000a", "\\u000a"});

					sb.setIndex(0);

					try {
						PreparedStatement ps = con.prepareStatement(sql);

						ps.executeUpdate();

						ps.close();
					}
					catch (Exception e) {
						System.out.println(sql);

						throw e;
					}
				}
			}
		}

		unsyncBufferedReader.close();
	}

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String databaseName = arguments.get("db.database.name");
		String databaseType = arguments.get("db.database.type");
		String sqlDir = arguments.get("db.sql.dir");
		String fileName = arguments.get("db.file.name");

		new DBLoader(databaseName, databaseType, sqlDir, fileName);
	}

	public DBLoader(
		String databaseName, String databaseType, String sqlDir,
		String fileName) {

		try {
			_databaseName = databaseName;
			_databaseType = databaseType;
			_sqlDir = sqlDir;
			_fileName = fileName;

			if (_databaseType.equals("derby")) {
				_loadDerby();
			}
			else if (_databaseType.equals("hypersonic")) {
				_loadHypersonic();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _loadDerby() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		Connection con = DriverManager.getConnection(
			"jdbc:derby:" + _sqlDir + "/" + _databaseName + ";create=true", "",
			"");

		if (Validator.isNull(_fileName)) {
			_loadDerby(con, _sqlDir + "/portal/portal-derby.sql");
			_loadDerby(con, _sqlDir + "/indexes.sql");
		}
		else {
			_loadDerby(con, _sqlDir + "/" + _fileName);
		}

		con.close();

		try {
			con = DriverManager.getConnection(
				"jdbc:derby:" + _sqlDir + "/" + _databaseName +
					";shutdown=true",
				"", "");
		}
		catch (SQLException sqle) {
			String sqlState = sqle.getSQLState();

			if (!sqlState.equals("08006")) {
				throw sqle;
			}
		}
	}

	private void _loadDerby(Connection con, String fileName) throws Exception {
		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(_fileUtil.read(fileName)));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("--")) {
				sb.append(line);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sql =
						StringUtil.replace(
							sql,
							new String[] {"\\'", "\\\"", "\\\\", "\\n", "\\r"},
							new String[] {"''", "\"", "\\", "\n", "\r"});

					sql = sql.substring(0, sql.length() - 1);

					sb.setIndex(0);

					if (sql.startsWith("commit")) {
						continue;
					}

					ij.runScript(
						con,
						new UnsyncByteArrayInputStream(
							sql.getBytes(StringPool.UTF8)),
						StringPool.UTF8, new UnsyncByteArrayOutputStream(),
						StringPool.UTF8);
				}
			}
		}

		unsyncBufferedReader.close();
	}

	private void _loadHypersonic() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		// See LEP-2927. Appending ;shutdown=true to the database connection URL
		// guarantees that ${_databaseName}.log is purged.

		Connection con = DriverManager.getConnection(
			"jdbc:hsqldb:" + _sqlDir + "/" + _databaseName + ";shutdown=true",
			"sa", "");

		if (Validator.isNull(_fileName)) {
			loadHypersonic(con, _sqlDir + "/portal/portal-hypersonic.sql");
			loadHypersonic(con, _sqlDir + "/indexes.sql");
		}
		else {
			loadHypersonic(con, _sqlDir + "/" + _fileName);
		}

		// Shutdown Hypersonic

		Statement statement = con.createStatement();

		statement.execute("SHUTDOWN COMPACT");

		statement.close();

		con.close();

		// Hypersonic will encode unicode characters twice, this will undo
		// it

		String content = _fileUtil.read(
			_sqlDir + "/" + _databaseName + ".script");

		content = StringUtil.replace(content, "\\u005cu", "\\u");

		_fileUtil.write(_sqlDir + "/" + _databaseName + ".script", content);
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private String _databaseName;
	private String _databaseType;
	private String _fileName;
	private String _sqlDir;

}