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
import com.liferay.portal.tools.db.virtual.instance.migration.internal.util.Version;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

				if (!_validate(_sourceConnection, _destinationConnection)) {
					_exitWithCode(ErrorCodes.VALIDATION_ERROR);
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

	private static void _printErrorMessages(
		List<String> modules, String message) {

		for (String module : modules) {
			System.out.println("ERROR: Module " + module + message);
		}
	}

	private static void _printWarningMessages(
		List<String> modules, String message) {

		for (String module : modules) {
			System.out.println("WARNING: Module " + module + message);
		}
	}

	private static boolean _validate(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		boolean valid = true;

		if (!_validateReleaseTableState(sourceConnection)) {
			System.out.println(
				"ERROR: Source database Release_ table has records with an " +
					"invalid state_");
			valid = false;
		}

		if (!_validateReleaseTableState(destinationConnection)) {
			System.out.println(
				"ERROR: Destination database Release_ table has records with " +
					"an invalid state_");
			valid = false;
		}

		valid &= _validateReleaseTableModules(
			sourceConnection, destinationConnection);

		return valid;
	}

	private static boolean _validateReleaseTableModules(
			Connection sourceConnection, Connection destinationConnection)
		throws SQLException {

		boolean valid = true;

		try (PreparedStatement preparedStatement1 =
				sourceConnection.prepareStatement(
					"select servletContextName, schemaVersion, verified from " +
						" Release_");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			List<String> missingModules = new ArrayList<>();
			List<String> missingServiceModules = new ArrayList<>();
			List<String> lowerVersionModules = new ArrayList<>();
			List<String> higherVersionModules = new ArrayList<>();
			List<String> sourceUnverifiedModules = new ArrayList<>();
			List<String> destinationUnverifiedModules = new ArrayList<>();

			while (resultSet1.next()) {
				String sourceServletContextName = resultSet1.getString(1);
				Version sourceVersion = Version.parseVersion(
					resultSet1.getString(2));
				boolean sourceVerified = resultSet1.getBoolean(3);

				try (PreparedStatement preparedStatement2 =
						destinationConnection.prepareStatement(
							"select servletContextName, schemaVersion, " +
								"verified from Release_ where " +
									"servletContextName = ?")) {

					preparedStatement2.setString(1, sourceServletContextName);

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						if (!resultSet2.next()) {
							if (sourceServletContextName.endsWith(".service")) {
								missingServiceModules.add(
									sourceServletContextName);
							}
							else {
								missingModules.add(sourceServletContextName);
							}

							continue;
						}

						Version destinationVersion = Version.parseVersion(
							resultSet2.getString(2));
						boolean destinationVerified = resultSet2.getBoolean(3);

						if (sourceVersion.compareTo(destinationVersion) < 0) {
							lowerVersionModules.add(sourceServletContextName);
						}
						else if (sourceVersion.compareTo(destinationVersion) >
									0) {

							higherVersionModules.add(sourceServletContextName);
						}

						if (sourceVerified && !destinationVerified) {
							destinationUnverifiedModules.add(
								sourceServletContextName);
						}
						else if (!sourceVerified && destinationVerified) {
							sourceUnverifiedModules.add(
								sourceServletContextName);
						}
					}
				}
			}

			_printErrorMessages(
				missingServiceModules,
				" will not be available in the destination");
			_printErrorMessages(
				lowerVersionModules,
				" needs to be upgraded in source database before the " +
					"migration");
			_printErrorMessages(
				higherVersionModules,
				" is in a lower version in destination database");
			_printErrorMessages(
				sourceUnverifiedModules,
				" needs to be verified in the source before the migration");
			_printErrorMessages(
				destinationUnverifiedModules,
				" needs to be verified in the destination before the " +
					"migration");
			_printWarningMessages(
				missingModules, " will not be available in the destination");

			if (!missingModules.isEmpty() || !missingServiceModules.isEmpty() ||
				!lowerVersionModules.isEmpty() ||
				!higherVersionModules.isEmpty() ||
				!sourceUnverifiedModules.isEmpty() ||
				!destinationUnverifiedModules.isEmpty()) {

				valid = false;
			}
		}

		return valid;
	}

	private static boolean _validateReleaseTableState(Connection connection)
		throws SQLException {

		boolean stateOk = true;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName from Release_ where state_ != 0;");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				stateOk = false;
			}
		}

		return stateOk;
	}

	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static Connection _destinationConnection;
	private static String _schemaPrefix = "lpartition_";
	private static Connection _sourceConnection;

}