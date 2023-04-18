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
import java.sql.DriverManager;
import java.sql.SQLException;

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

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].endsWith("help"))) {

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Virtual Instance Migration", options);

			return;
		}

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

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
					"ERROR: Not possible to get source database connection " +
						"with specified parameters:");
				sqlException.printStackTrace();

				_exitWithCode(ErrorCodes.BAD_SOURCE_PARAMETERS);
			}

			try {
				_destinationConnection = DriverManager.getConnection(
					destinationJdbcUrl, destinationUser, destinationPassword);
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
		}
		catch (ParseException parseException) {
			System.err.println(
				"ERROR: Unable to parse command line properties: " +
					parseException.getMessage());
			System.err.println();

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Virtual Instance Migration", options);

			_exitWithCode(ErrorCodes.BAD_INPUT_ARGUMENTS);
		}

		_exitWithCode(ErrorCodes.SUCCESS);
	}

	private static void _exitWithCode(int code) throws SQLException {
		if (_sourceConnection != null) {
			_sourceConnection.close();
		}

		if (_destinationConnection != null) {
			_sourceConnection.close();
		}

		if (code != ErrorCodes.SUCCESS) {
			System.exit(code);
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addRequiredOption(
			"s", "source-jdbc-url", true,
			"Set the JDBC url for the source database.");
		options.addRequiredOption(
			"su", "source-user", true, "Set the source database user name.");
		options.addRequiredOption(
			"sp", "source-password", true,
			"Set the source database user password.");
		options.addRequiredOption(
			"d", "destination-jdbc-url", true,
			"Set the JDBC url for the destination database.");
		options.addRequiredOption(
			"du", "destination-user", true,
			"Set the destination database user name.");
		options.addRequiredOption(
			"dp", "destination-password", true,
			"Set the destination database user password.");
		options.addOption(
			"dsp", "destination-schema-prefix", true,
			"Set the schema prefix for nondefault databases in destination " +
				"database.");
		options.addOption("h", "help", false, "Print help message.");

		return options;
	}

	private static Connection _destinationConnection;
	private static String _schemaPrefix = "lpartition_";
	private static Connection _sourceConnection;

}