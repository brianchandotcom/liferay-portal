/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaImporter {

	public static void main(String[] args) throws Exception {
		Options options = _getOptions();

		if ((args.length != 0) && args[0].equals("--help")) {
			HelpFormatter helpFormatter = new HelpFormatter();

			helpFormatter.printHelp(
				"Liferay Portal Tools Database Schema Importer", options);

			System.exit(_LIFERAY_COMMON_EXIT_CODE_HELP);
		}

		CommandLineParser commandLineParser = new DefaultParser();

		try {
			CommandLine commandLine = commandLineParser.parse(options, args);

			_getDataSource(
				commandLine.getOptionValue("source-jdbc-url"),
				commandLine.getOptionValue("source-password"),
				commandLine.getOptionValue("source-user"));

			_getDataSource(
				commandLine.getOptionValue("target-jdbc-url"),
				commandLine.getOptionValue("target-password"),
				commandLine.getOptionValue("target-user"));

			System.exit(_LIFERAY_COMMON_EXIT_CODE_OK);
		}
		catch (Exception exception) {
			System.exit(_LIFERAY_COMMON_EXIT_CODE_BAD);
		}
	}

	private static DataSource _getDataSource(
			String jdbcURL, String password, String userName)
		throws Exception {

		String driverClassName = _MYSQL_DRIVER;

		if (jdbcURL.indexOf("postgresql") > 0) {
			driverClassName = _POSTGRESQL_DRIVER;
		}

		Class.forName(driverClassName);

		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(jdbcURL);
		hikariConfig.setPassword(password);
		hikariConfig.setUsername(userName);

		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setIdleTimeout(600000);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMaxLifetime(0);
		hikariConfig.setMinimumIdle(10);
		hikariConfig.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");

		return new HikariDataSource(hikariConfig);
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(null, "help", false, "Print help message.");
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

	/**
	 * https://github.com/liferay/liferay-docker/blob/master/_liferay_common.sh
	 */
	private static final int _LIFERAY_COMMON_EXIT_CODE_BAD = 1;

	private static final int _LIFERAY_COMMON_EXIT_CODE_HELP = 2;

	private static final int _LIFERAY_COMMON_EXIT_CODE_OK = 0;

	private static final String _MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

	private static final String _POSTGRESQL_DRIVER = "org.postgresql.Driver";

}