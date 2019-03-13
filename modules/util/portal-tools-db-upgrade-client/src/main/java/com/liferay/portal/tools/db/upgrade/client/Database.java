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

package com.liferay.portal.tools.db.upgrade.client;

/**
 * @author David Truong
 */
public class Database {

	public static Database getDB2Database() {
		return new Database(
			"com.ibm.db2.jcc.DB2Driver", "jdbc:db2://", "localhost", 50000,
			"lportal",
			":deferPrepares=false;fullyMaterializeInputStreams=true;" +
				"fullyMaterializeLobData=true;progresssiveLocators=2;" +
					"progressiveStreaming=2;");
	}

	public static Database getMariaDBDatabase() {
		return new Database(
			"org.mariadb.jdbc.Driver", "jdbc:mariadb://", "localhost", 0,
			"lportal",
			"?useUnicode=true&characterEncoding=UTF-8" +
				"&useFastDateParsing=false");
	}

	public static Database getMySQLDatabase() {
		return new Database(
			"com.mysql.cj.jdbc.Driver", "jdbc:mysql://", "localhost", 0,
			"lportal",
			"?characterEncoding=UTF-8&dontTrackOpenResources=true" +
				"&holdResultsOpenOverStatementClose=true&serverTimezone=GMT" +
					"&useFastDateParsing=false&useUnicode=true");
	}

	public static Database getOracleDataSource() {
		return new Database(
			"oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@", "localhost", 1521,
			"xe", "");
	}

	public static Database getPostgreSQLDatabase() {
		return new Database(
			"org.postgresql.Driver", "jdbc:postgresql://", "localhost", 5432,
			"lportal", "");
	}

	public static Database getSQLServerDatabase() {
		return new Database(
			"com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://",
			"localhost", 0, "lportal", "");
	}

	public static Database getSybaseDatabase() {
		return new Database(
			"com.sybase.jdbc4.jdbc.SybDriver", "jdbc:sybase:Tds:", "localhost",
			5000, "lportal", "");
	}

	public String getClassName() {
		return _className;
	}

	public String getDatabaseName() {
		return _databaseName;
	}

	public String getHost() {
		return _host;
	}

	public int getPort() {
		return _port;
	}

	public String getProtocol() {
		return _protocol;
	}

	public String getURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(_protocol);
		sb.append(_host);

		if (_port > 0) {
			sb.append(':');
			sb.append(_port);
		}

		if (_protocol.contains("sqlserver")) {
			sb.append(";databaseName=");
		}
		else if (_protocol.contains("oracle")) {
			sb.append(":");
		}
		else {
			sb.append("/");
		}

		sb.append(_databaseName);
		sb.append(_params);

		return sb.toString();
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDatabaseName(String databaseName) {
		_databaseName = databaseName;
	}

	public void setHost(String host) {
		_host = host;
	}

	public void setParams(String params) {
		_params = params;
	}

	public void setPort(int port) {
		_port = port;
	}

	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	private Database(
		String className, String protocol, String host, int port,
		String databaseName, String params) {

		_className = className;
		_protocol = protocol;
		_host = host;
		_port = port;
		_databaseName = databaseName;
		_params = params;
	}

	private String _className;
	private String _databaseName;
	private String _host;
	private String _params;
	private int _port;
	private String _protocol;

}