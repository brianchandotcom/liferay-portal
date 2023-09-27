/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator;

import com.liferay.portal.tools.db.partition.virtual.instance.migrator.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.util.Validator;

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

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static void _exit(int code) {
		try {
			if (_sourceConnection != null) {
				_sourceConnection.close();
			}

			if (_targetConnection != null) {
				_targetConnection.close();
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
		options.addRequiredOption(
			"s", "source-jdbc-url", true, "Set the source JDBC URL.");
		options.addRequiredOption(
			"sp", "source-password", true, "Set the source password.");
		options.addRequiredOption(
			"su", "source-user", true, "Set the source user.");
		options.addRequiredOption(
			"t", "target-jdbc-url", true, "Set the target JDBC URL.");
		options.addRequiredOption(
			"tp", "target-password", true, "Set the target password.");
		options.addOption(
			"tsp", "target-schema-prefix", true,
			"Set the target schema prefix.");
		options.addRequiredOption(
			"tu", "target-user", true, "Set the target user.");

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
				_sourceConnection = DriverManager.getConnection(
					commandLine.getOptionValue("source-jdbc-url"),
					commandLine.getOptionValue("source-user"),
					commandLine.getOptionValue("source-password"));
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to connect to source with the specified " +
						"parameters:");

				sqlException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			if (!DatabaseUtil.hasSingleCompanyInfo(_sourceConnection)) {
				System.err.println("Source has more than one company info");

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			try {
				_targetConnection = DriverManager.getConnection(
					commandLine.getOptionValue("target-jdbc-url"),
					commandLine.getOptionValue("target-user"),
					commandLine.getOptionValue("target-password"));
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to connect to target with the specified " +
						"parameters:");

				sqlException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			if (!DatabaseUtil.isDefaultPartition(_targetConnection)) {
				System.err.println("Target is not the default partition");

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			if (commandLine.hasOption("target-schema-prefix")) {
				DatabaseUtil.setSchemaPrefix(
					commandLine.getOptionValue("target-schema-prefix"));
			}

			Recorder recorder = Validator.validateDatabases(
				_sourceConnection, _targetConnection);

			if (recorder.hasErrors() || recorder.hasWarnings()) {
				recorder.printMessages();

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

	private static Connection _sourceConnection;
	private static Connection _targetConnection;

}