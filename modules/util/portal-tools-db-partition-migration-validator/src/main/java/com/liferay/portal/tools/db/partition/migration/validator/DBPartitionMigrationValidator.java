/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.migration.validator.util.ValidatorUtil;
import com.liferay.portal.tools.db.partition.migration.validator.util.VersionDeserializer;

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
public class DBPartitionMigrationValidator {

	public static void main(String[] args) {
		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].equals("-help") ||
			 args[0].equals("--help"))) {

			_printHelp();

			return;
		}

		for (String arg : args) {
			if (arg.equals("-e") || arg.equals("-export") ||
				arg.equals("--export")) {

				_export(ArrayUtil.remove(args, arg));

				_exit(_LIFERAY_COMMON_EXIT_CODE_OK);
			}
			else if (arg.equals("-v") || arg.equals("-validate") ||
					 arg.equals("--validate")) {

				_validate(ArrayUtil.remove(args, arg));

				_exit(_LIFERAY_COMMON_EXIT_CODE_OK);
			}
		}

		_printHelp();
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

		System.exit(code);
	}

	private static void _export(String[] args) {
		Options options = _getExportOptions();

		CommandLine commandLine = null;

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			commandLine = commandLineParser.parse(options, args);
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());

			_printHelp();

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

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
				"Unable to connect to database with the specified parameters:");

			sqlException.printStackTrace();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}

		try {
			String exportFilePath = _write(
				DatabaseUtil.exportLiferayInstance(_connection),
				commandLine.getOptionValue("output-dir"));

			System.out.println(
				"Export file generated successfully in " + exportFilePath);
		}
		catch (Exception exception) {
			System.out.println("Unable to generate the export file:");

			exception.printStackTrace();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static Options _getExportOptions() {
		Options options = new Options();

		options.addOption("d", "output-dir", true, "Set the output directory.");
		options.addRequiredOption("j", "jdbc-url", true, "Set the JDBC URL.");
		options.addRequiredOption(
			"p", "password", true, "Set the database user password.");
		options.addOption(
			"s", "schema-name", true,
			"Set the database schema name for the source partition.");
		options.addRequiredOption(
			"u", "user", true, "Set the database user name.");

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

		options.addRequiredOption(
			"s", "source-file", true, "Set the path to the source file.");
		options.addRequiredOption(
			"t", "target-file", true, "Set the path to the target file.");

		return options;
	}

	private static void _printHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();

		PrintWriter printWriter = new PrintWriter(System.out);

		helpFormatter.printUsage(
			printWriter, _HELP_WIDTH,
			"./db_partition_migration_validator.sh <OPERATION_MODE> " +
				"[OPERATION_PARAMETERS]");
		helpFormatter.printWrapped(
			printWriter, _HELP_WIDTH, "\nOperation mode:");
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

	private static LiferayInstance _read(String path) throws IOException {
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

	private static void _validate(String[] args) {
		Options options = _getValidationOptions();

		CommandLine commandLine = null;

		try {
			CommandLineParser commandLineParser = new DefaultParser();

			commandLine = commandLineParser.parse(options, args);
		}
		catch (ParseException parseException) {
			System.err.println(parseException.getMessage());

			_printHelp();

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		try {
			_sourceLiferayInstance = _read(
				commandLine.getOptionValue("source-file"));
		}
		catch (IOException ioException) {
			System.err.println(
				"Unable to read source file with the specified parameters:");

			ioException.printStackTrace();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}

		try {
			_targetLiferayInstance = _read(
				commandLine.getOptionValue("target-file"));
		}
		catch (IOException ioException) {
			System.err.println(
				"Unable to read target file with the specified parameters:");

			ioException.printStackTrace();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}

		if (!_targetLiferayInstance.isExportedCompanyDefault()) {
			System.err.println("Target is not the default partition");

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}

		Recorder recorder = ValidatorUtil.validateDatabases(
			_sourceLiferayInstance, _targetLiferayInstance);

		if (recorder.hasErrors() || recorder.hasWarnings()) {
			recorder.printMessages();

			_exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static String _write(LiferayInstance liferayInstance, String path)
		throws IOException {

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

				throw new IOException("Path " + path + " is not writable");
			}
		}

		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
			}
		};

		DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();

		prettyPrinter.indentArraysWith(
			DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

		objectMapper.setDefaultPrettyPrinter(prettyPrinter);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

		File exportFile = new File(
			exportDir,
			StringBundler.concat(
				simpleDateFormat.format(liferayInstance.getDate()), "_export_",
				liferayInstance.getExportedCompanyId(), ".json"));

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