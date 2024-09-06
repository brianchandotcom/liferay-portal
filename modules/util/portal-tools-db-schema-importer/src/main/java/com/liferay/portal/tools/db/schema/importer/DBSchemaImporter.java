/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.ConnectionConfigUtil;

import java.io.File;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

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
			_printHelpAndExit(options);
		}

		CommandLineParser commandLineParser = new DefaultParser();

		CommandLine commandLine = null;

		try {
			commandLine = commandLineParser.parse(options, args);
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());

			_printHelpAndExit(options);
		}

		try {
			ConnectionConfigUtil.setBatchSize(
				commandLine.getOptionValue("jdbc-batch-size"));
			ConnectionConfigUtil.setFetchSize(
				commandLine.getOptionValue("jdbc-fetch-size"));

			DBSchemaImporterProcess dbSchemaImporterProcess =
				new DBSchemaImporterProcess(
					commandLine.getOptionValue("path"),
					commandLine.getOptionValue("source-jdbc-url"),
					commandLine.getOptionValue("source-password"),
					commandLine.getOptionValue("source-user"),
					commandLine.getOptionValue("target-jdbc-url"),
					commandLine.getOptionValue("target-password"),
					commandLine.getOptionValue("target-user"));

			dbSchemaImporterProcess.run();

			_generateReport(
				dbSchemaImporterProcess, commandLine.getOptionValue("path"));

			System.exit(_LIFERAY_COMMON_EXIT_CODE_OK);
		}
		catch (Exception exception) {
			exception.printStackTrace(System.err);

			System.exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static void _generateReport(
			DBSchemaImporterProcess dbSchemaImporterProcess, String dirName)
		throws Exception {

		try (PrintWriter printWriter = new PrintWriter(
				new File(dirName, "db_schema_import_report.info"))) {

			printWriter.println(
				StringUtil.merge(
					new Object[] {
						"Export date: " + _simpleDateFormat.format(new Date()),
						dbSchemaImporterProcess.getReleaseInfo(),
						StringPool.NEW_LINE, StringPool.NEW_LINE,
						_getReportInfo(dbSchemaImporterProcess)
					},
					StringPool.NEW_LINE));
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(null, "help", false, "Print help message.");
		options.addOption(
			null, "jdbc-batch-size", true,
			"Set the JDBC batch size. Default 2500.");
		options.addOption(
			null, "jdbc-fetch-size", true,
			"Set the JDBC result set fetch size. Default 2500.");
		options.addRequiredOption(
			null, "path", true, "Set the path of the source SQL files.");
		options.addRequiredOption(
			null, "source-jdbc-url", true, "Set the source JDBC URL.");
		options.addRequiredOption(
			null, "source-password", true,
			"Set the source database user password.");
		options.addRequiredOption(
			null, "source-user", true, "Set the source database user.");
		options.addRequiredOption(
			null, "target-jdbc-url", true, "Set the target JDBC URL.");
		options.addRequiredOption(
			null, "target-password", true,
			"Set the target database user password.");
		options.addRequiredOption(
			null, "target-user", true, "Set the target database user.");

		return options;
	}

	private static String _getReportInfo(
		DBSchemaImporterProcess dbSchemaImporterProcess) {

		List<String> reportInfo = dbSchemaImporterProcess.getReportInfo();

		StringBundler sb = new StringBundler(reportInfo.size() * 2);

		for (String partitionReportInfo : reportInfo) {
			sb.append(partitionReportInfo);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private static void _printHelpAndExit(Options options) {
		new HelpFormatter(
		).printHelp(
			"Liferay Portal Tools Database Schema Importer", options
		);

		System.exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
	}

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

	private static final SimpleDateFormat _simpleDateFormat =
		new SimpleDateFormat(DateUtil.ISO_8601_PATTERN);

}