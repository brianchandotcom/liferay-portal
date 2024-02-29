/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.LiferayInstance;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.util.Validator;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.util.VersionDeserializer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Luis Ortiz
 */
public class DBPartitionVirtualInstanceMigrationValidator {

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
		if (code != _LIFERAY_COMMON_EXIT_CODE_OK) {
			System.exit(code);
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption("h", "help", false, "Display options.");
		options.addRequiredOption("s", "source-file", true, "Source file.");
		options.addRequiredOption("t", "target-file", true, "Target file.");

		return options;
	}

	private static void _main(String[] args) {
		Options options = _getOptions();

		if ((args.length != 0) &&
			(args[0].equals("-h") || args[0].endsWith("help"))) {

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools Database Partition Virtual Instance " +
					"Migration Validator",
				options);

			return;
		}

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

			if (!_targetLiferayInstance.isExtractedCompanyDefault()) {
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

			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools Database Partition Virtual Instance " +
					"Migration Validator",
				options);

			_exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		_exit(_LIFERAY_COMMON_EXIT_CODE_OK);
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

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

	private static LiferayInstance _sourceLiferayInstance;
	private static LiferayInstance _targetLiferayInstance;

}