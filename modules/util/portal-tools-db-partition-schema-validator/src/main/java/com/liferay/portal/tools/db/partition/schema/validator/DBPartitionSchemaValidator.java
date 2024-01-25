/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class DBPartitionSchemaValidator {

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].endsWith("help"))) {

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Partition Schema Validator", options);

			return;
		}

		CommandLineParser commandLineParser = new DefaultParser();

		CommandLine commandLine = commandLineParser.parse(options, args);

		_dbName = commandLine.getOptionValue("db-name");

		String jdbcURL = _DEFAULT_JDBC_URL.replace("db-name", _dbName);

		if (commandLine.hasOption("jdbc-url")) {
			jdbcURL = commandLine.getOptionValue("jdbc-url");
		}

		String password = commandLine.getOptionValue("password");
		String user = commandLine.getOptionValue("user");

		try {
			_connection = DriverManager.getConnection(jdbcURL, user, password);

			_debug = commandLine.hasOption("debug");

			boolean defaultPartition = true;

			for (long companyId : _getCompanyIds()) {
				if (defaultPartition) {
					_validatePartition(
						companyId, _getDefaultPartitionName(), true);

					defaultPartition = false;

					continue;
				}

				if (commandLine.hasOption("schema-prefix")) {
					_schemaPrefix = commandLine.getOptionValue("schema-prefix");
				}

				_validatePartition(companyId, _schemaPrefix + companyId, false);
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
				"select companyId from Company order by createDate asc")) {

			List<Long> companyIds = new ArrayList<>();

			while (resultSet.next()) {
				companyIds.add(resultSet.getLong("companyId"));
			}

			return companyIds;
		}
	}

	private static String _getDefaultPartitionName() throws SQLException {
		return _connection.getCatalog();
	}

	private static int _getInvalidRecordsCount(
			long companyId, String partitionName, String tableName,
			boolean defaultPartition)
		throws SQLException {

		String query =
			"select count(*) from " + partitionName + "." + tableName +
				" where companyId != " + companyId;

		if (defaultPartition) {
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
			"Set the schema prefix for nondefault partitions.");
		options.addRequiredOption(
			"d", "db-name", true, "Set the database name.");
		options.addRequiredOption(
			"t", "db-type", true, "Set database type [mysql, postgresql].");
		options.addRequiredOption(
			"p", "password", true, "Set database user password.");
		options.addRequiredOption(
			"u", "user", true, "Set the database user name.");

		return options;
	}

	private static boolean _hasColumn(
			String partitionName, String tableName, String columnName)
		throws Exception {

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				partitionName, partitionName, tableName, columnName)) {

			if (!resultSet.next()) {
				return false;
			}

			return true;
		}
	}

	private static void _validatePartition(
			long companyId, String partitionName, boolean defaultPartition)
		throws Exception {

		if (defaultPartition) {
			System.out.println(
				"Validating default partition for company ID " + companyId);
		}
		else {
			System.out.println(
				"Validating partition " + partitionName + " for company ID " +
					companyId);
		}

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getTables(
				partitionName, partitionName, null, new String[] {"TABLE"})) {

			boolean validPartition = true;

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");

				if (_controlTableNames.contains(tableName)) {
					if (_debug) {
						System.out.println(tableName + " is control table");
					}

					continue;
				}

				if (_hasColumn(partitionName, tableName, "companyId")) {
					int count = _getInvalidRecordsCount(
						companyId, partitionName, tableName, defaultPartition);

					if (count > 0) {
						System.out.println(
							"Table " + tableName + " contains " + count +
								" records with an invalid company ID");

						validPartition = false;
					}
					else if (_debug) {
						System.out.println(
							"Table " + tableName + " does not contain " +
								"invalid records");
					}
				}
			}

			if (validPartition) {
				if (defaultPartition) {
					System.out.println(
						"Validation passed successfully for default partition");
				}
				else {
					System.out.println(
						"Validation passed successfully for partition " +
							partitionName);
				}
			}
		}
	}

	private static final String _DEFAULT_JDBC_URL =
		"jdbc:mysql://localhost/db-name?characterEncoding=UTF-8&" +
			"dontTrackOpenResources=true&holdResultsOpenOverStatementClose=" +
				"true&serverTimezone=GMT&useFastDateParsing=false&useUnicode=" +
					"true";

	private static Connection _connection;
	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static String _dbName;
	private static boolean _debug;
	private static String _schemaPrefix = "lpartition_";

}