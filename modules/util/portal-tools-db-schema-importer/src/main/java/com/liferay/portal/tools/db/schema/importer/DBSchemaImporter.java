/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.liferay.portal.tools.db.schema.importer.jdbc.DataSourceFactoryUtil;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaImporter {

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) && args[0].equals("--help")) {
			new HelpFormatter(
			).printHelp(
				"Liferay Portal Tools Database Schema Importer", options
			);

			System.exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		CommandLineParser commandLineParser = new DefaultParser();

		CommandLine commandLine = null;

		try {
			commandLine = commandLineParser.parse(options, args);
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());

			new HelpFormatter(
			).printHelp(
				"Liferay Portal Tools Database Schema Importer", options
			);

			System.exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		try {
			DataSourceFactoryUtil.setBatchSize(
				commandLine.getOptionValue("target-jdbc-batch-size"));
			DataSourceFactoryUtil.setFetchSize(
				commandLine.getOptionValue("source-jdbc-fetch-size"));

			new DBSchemaImporterProcess(
				commandLine.getOptionValue("path"),
				commandLine.getOptionValue("source-jdbc-url"),
				commandLine.getOptionValue("source-password"),
				commandLine.getOptionValue("source-user"),
				commandLine.getOptionValue("target-jdbc-url"),
				commandLine.getOptionValue("target-password"),
				commandLine.getOptionValue("target-user")
			).run();

			System.exit(_LIFERAY_COMMON_EXIT_CODE_OK);
		}
		catch (Exception exception) {
			exception.printStackTrace(System.err);

			System.exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(null, "help", false, "Print help message.");
		options.addRequiredOption(
			null, "path", true, "Set the path of the source SQL files.");
		options.addOption(
			null, "source-jdbc-fetch-size", true,
			"Set the source JDBC ResultSet fetch size.");
		options.addRequiredOption(
			null, "source-jdbc-url", true, "Set the source JDBC URL.");
		options.addRequiredOption(
			null, "source-password", true,
			"Set the source database user password.");
		options.addRequiredOption(
			null, "source-user", true, "Set the source database user.");
		options.addOption(
			null, "target-jdbc-batch-size", true,
			"Set the source JDBC batch size.");
		options.addRequiredOption(
			null, "target-jdbc-url", true, "Set the target JDBC URL.");
		options.addRequiredOption(
			null, "target-password", true,
			"Set the target database user password.");
		options.addRequiredOption(
			null, "target-user", true, "Set the target database user.");

		return options;
	}

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

}