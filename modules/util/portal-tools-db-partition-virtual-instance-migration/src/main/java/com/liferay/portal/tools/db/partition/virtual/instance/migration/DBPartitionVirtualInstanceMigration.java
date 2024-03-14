/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.LiferayInstance;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.util.Validator;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.util.VersionDeserializer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Luis Ortiz
 */
public class DBPartitionVirtualInstanceMigration {

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

	private static void _executeDataExport(String[] args) throws Exception {
		Options options = _getExportOptions();

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			String jdbcUrl = DatabaseUtil.replaceSchemaName(
				commandLine.getOptionValue("jdbc-url"),
				commandLine.getOptionValue("schema-name"));

			try {
				_connection = DriverManager.getConnection(
					jdbcUrl, commandLine.getOptionValue("user"),
					commandLine.getOptionValue("password"));
			}
			catch (SQLException sqlException) {
				System.err.println(
					"Unable to connect to database with the specified " +
						"parameters:");

				sqlException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			String exportFilePath = _writeToFile(
				DatabaseUtil.exportLiferayInstance(_connection),
				commandLine.getOptionValue("output-dir"));

			System.out.println(
				"Export file generated successfully in " + exportFilePath);
		}
		catch (ParseException parseException) {
			System.err.println("Unable to parse command line properties:");

			parseException.printStackTrace();

			_printHelp();

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}
	}

	private static void _executeDataValidation(String[] args) throws Exception {
		Options options = _getValidationOptions();

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			try {
				_sourceLiferayInstance = _readFromFile(
					commandLine.getOptionValue("source-file"));
			}
			catch (IOException ioException) {
				System.err.println(
					"Unable to read source file with the specified " +
						"parameters:");

				ioException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			if (!Validator.isSingleCompany(_sourceLiferayInstance)) {
				System.err.println("Source has more than one company");

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			try {
				_targetLiferayInstance = _readFromFile(
					commandLine.getOptionValue("target-file"));
			}
			catch (IOException ioException) {
				System.err.println(
					"Unable to read target file with the specified " +
						"parameters:");

				ioException.printStackTrace();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			if (!_targetLiferayInstance.isExportedCompanyDefault()) {
				System.err.println("Target is not the default partition");

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}

			Recorder recorder = Validator.validateDatabases(
				_sourceLiferayInstance, _targetLiferayInstance);

			if (recorder.hasErrors() || recorder.hasWarnings()) {
				recorder.printMessages();

				_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
			}
		}
		catch (ParseException parseException) {
			System.err.println("Unable to parse command line properties:");

			parseException.printStackTrace();

			_printHelp();

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
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

	private static Options _getExportOptions() {
		Options options = new Options();

		options.addOption("d", "output-dir", true, "Output directory.");
		options.addRequiredOption("j", "jdbc-url", true, "JDBC URL.");
		options.addRequiredOption(
			"p", "password", true, "Database user password.");
		options.addOption(
			"s", "schema-name", true, "Database schema name to be exported.");
		options.addRequiredOption("u", "user", true, "Database user name.");

		return options;
	}

	private static Options _getMainOptions() {
		OptionGroup optionGroup = new OptionGroup();

		optionGroup.addOption(
			new Option("e", "export", false, "Execute data export."));
		optionGroup.addOption(
			new Option("v", "validate", false, "Execute data validation."));

		Options options = new Options();

		options.addOption("h", "help", false, "Display options.");
		options.addOptionGroup(optionGroup);

		return options;
	}

	private static Options _getValidationOptions() {
		Options options = new Options();

		options.addRequiredOption("s", "source-file", true, "Source file.");
		options.addRequiredOption("t", "target-file", true, "Target file.");

		return options;
	}

	private static void _main(String[] args) throws Exception {
		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].equals("-help") ||
			 args[0].equals("--help"))) {

			_printHelp();

			return;
		}

		boolean toolExecuted = false;

		for (String arg : args) {
			if (arg.equals("-e") || arg.equals("-export") ||
				arg.equals("--export")) {

				_executeDataExport(ArrayUtil.remove(args, arg));
				toolExecuted = true;

				break;
			}
			else if (arg.equals("-v") || arg.equals("-validate") ||
					 arg.equals("--validate")) {

				_executeDataValidation(ArrayUtil.remove(args, arg));
				toolExecuted = true;

				break;
			}
		}

		if (!toolExecuted) {
			_printHelp();

			return;
		}

		_exit(_LIFERAY_COMMON_EXIT_CODE_OK);
	}

	private static void _printHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();

		PrintWriter printWriter = new PrintWriter(System.out);

		helpFormatter.printUsage(
			printWriter, _HELP_WIDTH,
			"./db_partition_virtual_instance_migration.sh OPERATION_MODE " +
				"[OPERATION_PARAMETERS]");
		helpFormatter.printWrapped(
			printWriter, _HELP_WIDTH, "\nMain operation mode:");
		helpFormatter.printOptions(
			printWriter, _HELP_WIDTH, _getMainOptions(), _HELP_LEFT_PAD,
			_HELP_DESC_PAD);
		helpFormatter.printWrapped(
			printWriter, _HELP_WIDTH, _HELP_DESC_PAD,
			"\nData Export parameters:");
		helpFormatter.printOptions(
			printWriter, _HELP_WIDTH, _getExportOptions(), _HELP_LEFT_PAD,
			_HELP_DESC_PAD);
		helpFormatter.printWrapped(
			printWriter, _HELP_WIDTH, "\nData Validation parameters:");
		helpFormatter.printOptions(
			printWriter, _HELP_WIDTH, _getValidationOptions(), _HELP_LEFT_PAD,
			_HELP_DESC_PAD);

		printWriter.flush();
		printWriter.close();
	}

	private static LiferayInstance _readFromFile(String path)
		throws IOException {

		ObjectMapper objectMapper = new ObjectMapper() {
			{
				SimpleModule simpleModule = new SimpleModule();

				simpleModule.addDeserializer(
					Version.class, new VersionDeserializer());

				registerModule(simpleModule);
			}
		};

		return objectMapper.readValue(new File(path), LiferayInstance.class);
	}

	private static String _writeToFile(
			LiferayInstance liferayInstance, String path)
		throws Exception {

		File exportDir = null;

		if (path == null) {
			exportDir = new File(".", "exports");

			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
		}

		if (exportDir == null) {
			exportDir = new File(path);

			if ((!exportDir.exists() && !exportDir.mkdirs()) ||
				!Files.isWritable(exportDir.toPath())) {

				throw new Exception("Unable to generate the export at " + path);
			}
		}

		ObjectMapper objectMapper = new ObjectMapper() {
			{
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
			}
		};

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		File exportFile = new File(
			exportDir,
			StringBundler.concat(
				simpleDateFormat.format(liferayInstance.getDate()), "_export_",
				liferayInstance.getExportedCompanyId(), ".data"));

		objectMapper.writeValue(exportFile, liferayInstance);

		return exportFile.getCanonicalPath();
	}

	private static final int _HELP_DESC_PAD = 5;

	private static final int _HELP_LEFT_PAD = 2;

	private static final int _HELP_WIDTH = 80;

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

	private static Connection _connection;
	private static LiferayInstance _sourceLiferayInstance;
	private static LiferayInstance _targetLiferayInstance;

}