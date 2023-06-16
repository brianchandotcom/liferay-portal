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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator;

import com.liferay.portal.tools.db.partition.virtual.instance.migrator.error.ErrorCodes;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.recorder.Recorder;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.util.Validator;

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
public class DBPartitionVirtualInstanceMigrator {

	public static void main(String[] args) {
		try {
			_main(args);
		}
		catch (Exception exception) {
			System.err.println("Unexpected error:");

			exception.printStackTrace();

			_exit(ErrorCodes.UNEXPECTED_ERROR);
		}
	}

	private static void _exit(int code) {
		try {
			if (_destinationConnection != null) {
				_destinationConnection.close();
			}

			if (_sourceConnection != null) {
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
			"Set the destination JDBC URL.");
		options.addRequiredOption(
			"dp", "destination-password", true,
			"Set the destination password.");
		options.addOption(
			"dsp", "destination-schema-prefix", true,
			"Set the destination schema prefix.");
		options.addRequiredOption(
			"du", "destination-user", true,
			"Set the destination user.");
		options.addOption("h", "help", false, "Print help message.");
		options.addRequiredOption(
			"s", "source-jdbc-url", true,
			"Set the source JDBC URL.");
		options.addRequiredOption(
			"sp", "source-password", true,
			"Set the source password.");
		options.addRequiredOption(
			"su", "source-user", true, "Set the source user.");

		return options;
	}

	private static void _main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].endsWith("help"))) {

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Partition Virtual Instance Migrator",
				options);

			return;
		}

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			String sourceJdbcURL = commandLine.getOptionValue(
				"source-jdbc-url");
			String sourceUser = commandLine.getOptionValue("source-user");
			String sourcePassword = commandLine.getOptionValue(
				"source-password");

			String destinationJdbcURL = commandLine.getOptionValue(
				"destination-jdbc-url");
			String destinationUser = commandLine.getOptionValue(
				"destination-user");
			String destinationPassword = commandLine.getOptionValue(
				"destination-password");

			try {
				_sourceConnection = DriverManager.getConnection(
					sourceJdbcURL, sourceUser, sourcePassword);
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to get source database connection with the " +
						"specified parameters:");

				sqlException.printStackTrace();

				_exit(ErrorCodes.BAD_SOURCE_PARAMETERS);
			}

			try {
				_destinationConnection = DriverManager.getConnection(
					destinationJdbcURL, destinationUser, destinationPassword);
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to get destination database connection with the " +
						"specified parameters:");

				sqlException.printStackTrace();

				_exit(ErrorCodes.BAD_DESTINATION_PARAMETERS);
			}

			if (commandLine.hasOption("destination-schema-prefix")) {
				DatabaseUtil.setSchemaPrefix(
					commandLine.getOptionValue("destination-schema-prefix"));
			}

			if (!DatabaseUtil.isSingleVirtualInstance(_sourceConnection)) {
				System.err.println(
					"Source database has several instances. That is not " +
						"supported by the tool");

				_exit(ErrorCodes.SOURCE_MULTI_INSTANCES);
			}

			if (!DatabaseUtil.isDefaultPartition(_destinationConnection)) {
				System.err.println(
					"Destination database is not the default partition");

				_exit(ErrorCodes.DESTINATION_NOT_DEFAULT);
			}

			Recorder recorder = Validator.validateDatabases(
				_sourceConnection, _destinationConnection);

			if (recorder.hasErrors() || recorder.hasWarnings()) {
				recorder.printMessages();
				_exit(ErrorCodes.VALIDATION_ERROR);
			}

			System.out.println("All validations passed successfully");
		}
		catch (ParseException parseException) {
			System.err.println(
				"Unable to parse command line properties: " +
					parseException.getMessage());

			System.err.println();

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Partition Virtual Instance Migrator",
				options);

			_exit(ErrorCodes.BAD_INPUT_ARGUMENTS);
		}

		_exit(ErrorCodes.SUCCESS);
	}

	private static Connection _destinationConnection;
	private static Connection _sourceConnection;

}