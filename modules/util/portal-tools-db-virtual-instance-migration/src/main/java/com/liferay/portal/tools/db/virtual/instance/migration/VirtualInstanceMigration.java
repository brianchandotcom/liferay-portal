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

package com.liferay.portal.tools.db.virtual.instance.migration;

import com.liferay.portal.tools.db.virtual.instance.migration.error.ErrorCodes;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Luis Ortiz
 */
public class VirtualInstanceMigration {

	public static void main(String[] args) {
		try {
			Options options = _getOptions();

			if ((args.length != 0) &&
				(args[0].equals("-h") || args[0].endsWith("help"))) {

				HelpFormatter helpFormatter = new HelpFormatter();

				helpFormatter.printHelp(
					"Liferay Portal Tools DB Virtual Instance Migration",
					options);

				return;
			}

			try {
				CommandLineParser commandLineParser = new DefaultParser();

				CommandLine commandLine = commandLineParser.parse(
					options, args);

				String sourceJdbcUrl = commandLine.getOptionValue(
					"source-jdbc-url");
				String sourceUser = commandLine.getOptionValue("source-user");
				String sourcePassword = commandLine.getOptionValue(
					"source-password");

				String destinationJdbcUrl = commandLine.getOptionValue(
					"destination-jdbc-url");
				String destinationUser = commandLine.getOptionValue(
					"destination-user");
				String destinationPassword = commandLine.getOptionValue(
					"destination-password");

				try {
					_sourceConnection = DriverManager.getConnection(
						sourceJdbcUrl, sourceUser, sourcePassword);
				}
				catch (SQLException sqlException) {
					System.err.println(
						"ERROR: Not possible to get source database " +
							"connection with specified parameters:");
					sqlException.printStackTrace();

					_exitWithCode(ErrorCodes.BAD_SOURCE_PARAMETERS);
				}

				try {
					_destinationConnection = DriverManager.getConnection(
						destinationJdbcUrl, destinationUser,
						destinationPassword);
				}
				catch (SQLException sqlException) {
					System.err.println(
						"ERROR: Not possible to get destination database " +
							"connection with specified parameters:");
					sqlException.printStackTrace();

					_exitWithCode(ErrorCodes.BAD_DESTINATION_PARAMETERS);
				}

				if (commandLine.hasOption("destination-schema-prefix")) {
					_schemaPrefix = commandLine.getOptionValue(
						"destination-schema-prefix");
				}

				String destinationSchemaName = _getSchemaName(
					destinationJdbcUrl);

				if (!_checkIsDefaultPartition(
						_destinationConnection, destinationSchemaName)) {

					System.err.println(
						"ERROR: Destination database is not the default " +
							"partition");

					_exitWithCode(ErrorCodes.DESTINATION_NOT_DEFAULT);
				}
			}
			catch (ParseException parseException) {
				System.err.println(
					"ERROR: Unable to parse command line properties: " +
						parseException.getMessage());
				System.err.println();

				HelpFormatter helpFormatter = new HelpFormatter();

				helpFormatter.printHelp(
					"Liferay Portal Tools DB Virtual Instance Migration",
					options);

				_exitWithCode(ErrorCodes.BAD_INPUT_ARGUMENTS);
			}

			_exitWithCode(ErrorCodes.SUCCESS);
		}
		catch (Exception exception) {
			System.err.println("Unexpected error:");
			exception.printStackTrace();
			_exitWithCode(ErrorCodes.UNEXPECTED_ERROR);
		}
	}

	private static boolean _checkIsDefaultPartition(
			Connection connection, String schemaName)
		throws SQLException {

		boolean defaultPartition = true;

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		for (String tableName : _controlTableNames) {
			try (ResultSet resultSet = databaseMetaData.getTables(
					schemaName, schemaName, tableName,
					new String[] {"TABLE"})) {

				if (!resultSet.next()) {
					defaultPartition = false;

					break;
				}
			}
		}

		return defaultPartition;
	}

	private static void _exitWithCode(int code) {
		try {
			if (_sourceConnection != null) {
				_sourceConnection.close();
			}

			if (_destinationConnection != null) {
				_sourceConnection.close();
			}
		}
		catch (SQLException sqlException) {
			System.err.println(sqlException);
		}

		if (code != ErrorCodes.SUCCESS) {
			System.exit(code);
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addRequiredOption(
			"d", "destination-jdbc-url", true,
			"Set the JDBC url for the destination database.");
		options.addRequiredOption(
			"dp", "destination-password", true,
			"Set the destination database user password.");
		options.addOption(
			"dsp", "destination-schema-prefix", true,
			"Set the schema prefix for nondefault databases in destination " +
				"database.");
		options.addRequiredOption(
			"du", "destination-user", true,
			"Set the destination database user name.");
		options.addOption("h", "help", false, "Print help message.");
		options.addRequiredOption(
			"s", "source-jdbc-url", true,
			"Set the JDBC url for the source database.");
		options.addRequiredOption(
			"sp", "source-password", true,
			"Set the source database user password.");
		options.addRequiredOption(
			"su", "source-user", true, "Set the source database user name.");

		return options;
	}

	private static String _getSchemaName(String jdbcUrl) {
		String schemaName;

		int paramsIndex = jdbcUrl.indexOf("?");

		if (paramsIndex == -1) {
			schemaName = jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1);
		}
		else {
			String onlyUrl = jdbcUrl.substring(0, paramsIndex);

			schemaName = onlyUrl.substring(onlyUrl.lastIndexOf("/") + 1);
		}

		return schemaName;
	}

	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static Connection _destinationConnection;
	private static String _schemaPrefix = "lpartition_";
	private static Connection _sourceConnection;

}