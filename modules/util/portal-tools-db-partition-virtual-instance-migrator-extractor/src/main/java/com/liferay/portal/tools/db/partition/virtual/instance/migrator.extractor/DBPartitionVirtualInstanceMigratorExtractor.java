/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor;

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
public class DBPartitionVirtualInstanceMigratorExtractor {

	public static void main(String[] args) {
		try {
			_main(args);
		}
		catch (Exception exception) {
			System.err.println("Unexpected error:");

			exception.printStackTrace();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static void _exit(int code) {
		try {
			if (_connection != null) {
				_connection.close();
			}
		}
		catch (SQLException sqlException) {
			System.err.println(sqlException);
		}

		if (code != _LIFERAY_COMMON_EXIT_CODE_OK) {
			System.exit(code);
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption("h", "help", false, "Print help message.");
		options.addRequiredOption("url", "jdbc-url", true, "Set the JDBC URL.");
		options.addRequiredOption(
			"pass", "password", true, "Set the password.");
		options.addRequiredOption("user", "user", true, "Set the user.");

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

			try {
				_connection = DriverManager.getConnection(
					commandLine.getOptionValue("jdbc-url"),
					commandLine.getOptionValue("user"),
					commandLine.getOptionValue("password"));
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to connect to database with the specified " +
						"parameters:");

				sqlException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}
		}
		catch (ParseException parseException) {
			System.err.println("Unable to parse command line properties:");

			parseException.printStackTrace();

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools DB Partition Virtual Instance Migrator",
				options);

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		_exit(_LIFERAY_COMMON_EXIT_CODE_OK);
	}

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

	private static Connection _connection;

}