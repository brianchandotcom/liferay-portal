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

		String jdbcUrl = _DEFAULT_JDBC_URL.replace("{db-schema}", _schemaName);

		if (commandLine.hasOption("jdbc-url")) {
			jdbcUrl = commandLine.getOptionValue("jdbc-url");
		}

		if (commandLine.hasOption("schema-prefix")) {
			_schema_prefix = commandLine.getOptionValue("schema-prefix");
		}

		String password = commandLine.getOptionValue("password");
		String user = commandLine.getOptionValue("user");

		try {
			_connection = DriverManager.getConnection(jdbcUrl, user, password);

			boolean debug = commandLine.hasOption("debug");

			boolean defaultSchema = true;

			for (Long companyId : _getCompanies()) {
				if (defaultSchema) {
					_validateSchema(companyId, _schemaName, true, debug);

					defaultSchema = false;

					continue;
				}

				String schemaName = _schema_prefix + companyId;

				_validateSchema(companyId, schemaName, false, debug);
			}
		}
		finally {
			if (_connection != null) {
				_connection.close();
			}
		}
	}

	private static List<Long> _getCompanies() throws Exception {
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

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption("a", "debug", false, "Print all log traces.");
		options.addOption("h", "help", false, "Print help message.");
		options.addOption("j", "jdbc-url", true, "JDBC url.");
		options.addOption(
			"s", "schema-prefix", true,
			"Schema prefix for non-default databases.");

		options.addRequiredOption(
			"d", "db-schema", true, "Default database schema name.");
		options.addRequiredOption(
			"p", "password", true, "Database user password.");
		options.addRequiredOption("u", "user", true, "Database user name.");

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
			Long companyId, String schemaName, boolean defaultSchema,
			boolean debug)
		throws Exception {

		System.out.println("# Validating schema " + schemaName);

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getTables(
				schemaName, schemaName, null, new String[] {"TABLE"})) {

			boolean found = false;

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");

				if (_controlTableNames.contains(tableName)) {
					if (debug) {
						System.out.println(tableName + " is control table");
					}

					continue;
				}

				if (_hasColumn(schemaName, tableName, "companyId")) {
					String fullTableName = schemaName + _PERIOD + tableName;

					String query =
						"select count(*) from " + fullTableName + " where " +
							"companyId != " + companyId;

					if (defaultSchema) {
						query += " and companyId != 0";
					}

					try (PreparedStatement preparedStatement =
							_connection.prepareStatement(query);
						ResultSet resultSet1 =
							preparedStatement.executeQuery()) {

						if (resultSet1.next()) {
							int count = resultSet1.getInt(1);

							if (tableName.equals("DLFileEntryType") &&
								(count == 1)) {

								continue;
							}

							if (count > 0) {
								found = true;

								System.out.println(
									"Table " + fullTableName + " contains " +
										resultSet1.getInt(1) + " records " +
											"with an invalid companyId");
							}
							else if (debug) {
								System.out.println(
									"Table " + fullTableName + " does not " +
										"contain invalid records");
							}
						}
					}
				}
			}

			if (!found) {
				System.out.println("No records with invalid companyId found");
			}
		}
	}

	private static final String _DEFAULT_JDBC_URL =
		"jdbc:mysql://localhost/{db-schema}?characterEncoding=UTF-8&" +
			"dontTrackOpenResources=true&holdResultsOpenOverStatementClose=" +
				"true&serverTimezone=GMT&useFastDateParsing=false&useUnicode=" +
					"true";

	private static final String _PERIOD = ".";

	private static Connection _connection;
	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static String _schema_prefix = "lpartition_";
	private static String _schemaName;

}