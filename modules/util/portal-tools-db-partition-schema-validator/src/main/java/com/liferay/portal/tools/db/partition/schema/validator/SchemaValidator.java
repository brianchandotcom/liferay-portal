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

package com.liferay.portal.tools.db.partition.schema.validator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * @author Alberto Chaparro
 */
public class SchemaValidator {

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].endsWith("help"))) {

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal DB Partition Schema Validator", options);

			return;
		}

		CommandLineParser commandLineParser = new DefaultParser();

		CommandLine commandLine = commandLineParser.parse(options, args);

		_schemaName = commandLine.getOptionValue("db-schema");

		String jdbcURL = _DEFAULT_JDBC_URL.replace("db-schema", _schemaName);

		if (commandLine.hasOption("jdbc-url")) {
			jdbcURL = commandLine.getOptionValue("jdbc-url");
		}

		String password = commandLine.getOptionValue("password");
		String user = commandLine.getOptionValue("user");

		try {
			_connection = DriverManager.getConnection(jdbcURL, user, password);

			_debug = commandLine.hasOption("debug");

			boolean defaultSchema = true;

			for (long companyId : _getCompanyIds()) {
				if (defaultSchema) {
					_validateSchema(companyId, _schemaName, true);

					defaultSchema = false;

					continue;
				}

				if (commandLine.hasOption("schema-prefix")) {
					_schemaPrefix = commandLine.getOptionValue("schema-prefix");
				}

				_validateSchema(companyId, _schemaPrefix + companyId, false);
			}
		}
		finally {
			if (_connection != null) {
				_connection.close();
			}
		}
	}

	private static List<Long> _getCompanyIds() throws Exception {
		try (Statement statement = _connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select companyId from Company order by companyId asc")) {

			List<Long> companyIds = new ArrayList<>();

			while (resultSet.next()) {
				companyIds.add(resultSet.getLong("companyId"));
			}

			return companyIds;
		}
	}

	private static int _getInvalidRecordsCount(
			long companyId, String schemaName, String tableName,
			boolean defaultSchema)
		throws SQLException {

		String query =
			"select count(*) from " + schemaName + "." + tableName + " where " +
				"companyId != " + companyId;

		if (defaultSchema) {
			query += " and companyId != 0";
		}

		int count = 0;

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				query);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				count = resultSet.getInt(1);

				if (tableName.equals("DLFileEntryType") && (count == 1)) {
					return 0;
				}
			}
		}

		return count;
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption("a", "debug", false, "Print all log traces.");
		options.addOption("h", "help", false, "Print help message.");
		options.addOption("j", "jdbc-url", true, "Set the JDBC url.");
		options.addOption(
			"s", "schema-prefix", true,
			"Set the schema prefix for nondefault databases.");
		options.addRequiredOption(
			"d", "db-schema", true, "Set the default database schema name.");
		options.addRequiredOption(
			"p", "password", true, "Set database user password.");
		options.addRequiredOption(
			"u", "user", true, "Set the database user name.");

		return options;
	}

	private static boolean _hasColumn(
			String schemaName, String tableName, String columnName)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				schemaName, schemaName, tableName, columnName)) {

			if (!resultSet.next()) {
				return false;
			}

			return true;
		}
	}

	private static void _validateSchema(
			long companyId, String schemaName, boolean defaultSchema)
		throws Exception {

		System.out.println(
			"Validating schema " + schemaName + " for company ID " + companyId);

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getTables(
				schemaName, schemaName, null, new String[] {"TABLE"})) {

			boolean validSchema = true;

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");

				if (_controlTableNames.contains(tableName)) {
					if (_debug) {
						System.out.println(tableName + " is control table");
					}

					continue;
				}

				if (_hasColumn(schemaName, tableName, "companyId")) {
					int count = _getInvalidRecordsCount(
						companyId, schemaName, tableName, defaultSchema);

					if (count > 0) {
						System.out.println(
							"Table " + tableName + " contains " + count +
								" records with an invalid company ID");

						validSchema = false;
					}
					else if (_debug) {
						System.out.println(
							"Table " + tableName + " does not contain " +
								"invalid records");
					}
				}
			}

			if (validSchema) {
				System.out.println(
					"Validation passed successfully for schema " + schemaName);
			}
		}
	}

	private static final String _DEFAULT_JDBC_URL =
		"jdbc:mysql://localhost/db-schema?characterEncoding=UTF-8&" +
			"dontTrackOpenResources=true&holdResultsOpenOverStatementClose=" +
				"true&serverTimezone=GMT&useFastDateParsing=false&useUnicode=" +
					"true";

	private static Connection _connection;
	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static boolean _debug;
	private static String _schemaName;
	private static String _schemaPrefix = "lpartition_";

}