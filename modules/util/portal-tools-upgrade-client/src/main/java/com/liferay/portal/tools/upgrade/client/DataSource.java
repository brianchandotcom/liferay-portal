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

package com.liferay.portal.tools.upgrade.client;

/**
 * @author David Truong
 */
public class DataSource {

	public static DataSource getDB2() {
		return new DataSource(
			"com.ibm.db2.jcc.DB2Driver", "jdbc:db2://", "localhost", 50000,
			"/lportal",
			":deferPrepares=false;fullyMaterializeInputStreams=true;" +
				"fullyMaterializeLobData=true;progresssiveLocators=2;" +
					"progressiveStreaming=2;");
	}

	public static DataSource getMariaDB() {
		return new DataSource(
			"org.mariadb.jdbc.Driver", "jdbc:mariadb://", "localhost", 0,
			"/lportal",
			"?useUnicode=true&characterEncoding=UTF-8" +
				"&useFastDateParsing=false");
	}

	public static DataSource getMySQL() {
		return new DataSource(
			"com.mysql.jdbc.Driver", "jdbc:mysql://", "localhost", 0,
			"/lportal",
			"?characterEncoding=UTF-8&dontTrackOpenResources=true" +
				"&holdResultsOpenOverStatementClose=true" +
					"&useFastDateParsing=false&useUnicode=true");
	}

	public static DataSource getOracle() {
		return new DataSource(
			"oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@", "localhost", 1521,
			":xe", "");
	}

	public static DataSource getPostgreSQL() {
		return new DataSource(
			"org.postgresql.Driver", "jdbc:postgresql://", "localhost", 5432,
			"/lportal", "");
	}

	public static DataSource getSQLServer() {
		return new DataSource(
			"com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://",
			"localhost", 0, "/lportal", "");
	}

	public static DataSource getSybase() {
		return new DataSource(
			"com.sybase.jdbc4.jdbc.SybDriver", "jdbc:sybase:Tds:", "localhost",
			5000, "/lportal", "");
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

	public String getUrl() {
		StringBuilder sb = new StringBuilder();

		sb.append(_protocol);
		sb.append(_host);

		if (_port > 0) {
			sb.append(':');
			sb.append(_port);
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

	private DataSource(
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