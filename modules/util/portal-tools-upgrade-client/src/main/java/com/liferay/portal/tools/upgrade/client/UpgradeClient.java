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

package com.liferay.portal.tools.upgrade.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jline.console.ConsoleReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author David Truong
 */
public class UpgradeClient {

	public static final String JAVA_HOME = System.getenv("JAVA_HOME");

	public static void main(String[] args) {
		try {
			Options options = _getOptions();

			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			if (commandLine.hasOption("help")) {
				HelpFormatter helpFormatter = new HelpFormatter();

				helpFormatter.printHelp(
					"Liferay Database Upgrade Tool", options);

				return;
			}

			String jvmOpts;

			if (commandLine.hasOption("jvmOpts")) {
				jvmOpts = commandLine.getOptionValue("jvmOpts");
			}
			else {
				jvmOpts =
					"-Xmx2048m -Dfile.encoding=UTF8 -Duser.country=US " +
						"-Duser.language=en -Duser.timezone=GMT";
			}

			File logFile;

			if (commandLine.hasOption("logFile")) {
				logFile = new File(commandLine.getOptionValue("logFile"));
			}
			else {
				logFile = new File("upgrade.log");
			}

			if (logFile.exists()) {
				String logFileName = logFile.getName();

				logFile.renameTo(
					new File(logFileName + "." + logFile.lastModified()));

				logFile = new File(logFileName);
			}

			UpgradeClient upgradeClient = new UpgradeClient(jvmOpts, logFile);

			upgradeClient.upgrade();
		}
		catch (ParseException pe) {
			System.err.println("Unable to parse command line properties");

			pe.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Error running upgrade");

			e.printStackTrace();
		}
	}

	public UpgradeClient(String jvmOpts, File logFile) throws IOException {
		_consoleReader = new ConsoleReader();

		_dataSourcePropertiesFile = new File(
			"portal-upgrade-datasource.properties");

		_dataSourceProperties = _readProperties(_dataSourcePropertiesFile);

		_jvmOpts = jvmOpts;

		_logFile = logFile;

		_serverPropertiesFile = new File("server.properties");

		_serverProperties = _readProperties(_serverPropertiesFile);

		_upgradePropertiesFile = new File("portal-upgrade-ext.properties");

		_upgradeProperties = _readProperties(_upgradePropertiesFile);
	}

	public void upgrade() throws IOException {
		verifyProperties();

		System.setOut(
			new TeePrintStream(new FileOutputStream(_logFile), System.out));

		String classPath = _getClassPath();

		List<String> commands = new ArrayList<>();

		if (JAVA_HOME != null) {
			commands.add(JAVA_HOME + "/bin/java");
		}
		else {
			commands.add("java");
		}

		commands.addAll(Arrays.asList(_jvmOpts.split(" ")));
		commands.add("-Dexternal-properties=portal-upgrade.properties");
		commands.add("-cp");
		commands.add(classPath);
		commands.add("com.liferay.portal.tools.DBUpgrader");

		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.command(commands);
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				process.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader)) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.equals(
						"Running modules upgrades. Connect to your Gogo " +
							"Shell to check the status.")) {

					break;
				}
				else {
					System.out.println(line);
				}
			}

			System.out.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		boolean upgrading = true;

		while (upgrading) {
			try (GogoTelnetClient gogoTelnetClient = new GogoTelnetClient()) {
				System.out.println("You are now connected to Gogo Shell");

				_printHelp();

				_consoleReader.setPrompt("g! ");

				String line;

				while ((line = _consoleReader.readLine()) != null) {
					if (line.equals("exit") || line.equals("quit")) {
						break;
					}
					else if (line.equals("upgrade:help")) {
						_printHelp();
					}
					else {
						System.out.println(gogoTelnetClient.send(line));
					}
				}

				System.out.print(
					"Making sure all upgrades steps have been completed");

				String upgradeSteps = gogoTelnetClient.send(
					"upgrade:list | grep Registered | grep step");

				upgrading = upgradeSteps.contains("true");

				if (upgrading) {
					System.out.println(
						"...one of your upgrades is still running or failed");
					System.out.println("Are you sure you want to exit (y/N)?");

					_consoleReader.setPrompt("");

					String response = _consoleReader.readLine();

					if (response.equals("y")) {
						upgrading = false;
					}
				}
				else {
					System.out.println("...done.");
				}
			}
			catch (Exception e) {
				upgrading = false;
			}
		}

		System.out.println("Exiting Gogo Shell");

		_close(process.getInputStream());
		_close(process.getOutputStream());
		_close(process.getErrorStream());

		process.destroy();
	}

	public void verifyProperties() {
		try {
			_verifyDataSourceProperties();

			_verifyServerProperties();

			_verifyUpgradeProperties();

			_saveProperties();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(new Option("h", "help", false, "print this message"));
		options.addOption(
			new Option(
				"j", "jvmOpts", true, "set the JVM_OPTS of the upgrade"));
		options.addOption(new Option("l", "logFile", true, "name of log file"));

		return options;
	}

	private void _appendLibs(StringBuilder classPath, File dir)
		throws IOException {

		if (dir.exists() && dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				String fileName = file.getName();

				if (file.isFile() && fileName.endsWith("jar")) {
					classPath.append(
						file.getCanonicalPath() + File.pathSeparator);
				}
				else if (file.isDirectory()) {
					_appendLibs(classPath, file);
				}
			}
		}
	}

	private void _close(Closeable closeable) throws IOException {
		closeable.close();
	}

	private String _getClassPath() throws IOException {
		StringBuilder classPath = new StringBuilder();

		String liferayClassPath = System.getenv("LIFERAY_CLASSPATH");

		if ((liferayClassPath != null) && !liferayClassPath.isEmpty()) {
			classPath.append(liferayClassPath);

			classPath.append(File.pathSeparator);
		}

		_appendLibs(classPath, new File("lib"));
		_appendLibs(classPath, new File("."));
		_appendLibs(classPath, new File(_appServer.getDir(), "bin"));
		_appendLibs(classPath, _appServer.getGlobalLibDir());

		File portalClassesDir = _appServer.getPortalClassesDir();

		classPath.append(portalClassesDir.getCanonicalPath());

		_appendLibs(classPath, _appServer.getPortalLibDir());

		return classPath.toString();
	}

	private String _getRelativePath(File baseFile, File pathFile) {
		return _getRelativePath(baseFile.toPath(), pathFile.toPath());
	}

	private String _getRelativePath(Path basePath, Path path) {
		Path relativePath = basePath.relativize(path);

		return relativePath.toString();
	}

	private void _printHelp() {
		System.out.println("\nUpgrade commands:");
		System.out.println("exit or quit - exit Gogo Shell");
		System.out.println("upgrade:help - show upgrade commands");
		System.out.println(
			"upgrade:execute {module_name} - Execute upgrade for that module");
		System.out.println("upgrade:list - List all registered upgrades");
		System.out.println(
			"upgrade:list {module_name} - " +
				"List the upgrade steps required for that module");
		System.out.println(
			"upgrade:list | grep Registered - " +
				"List upgrades that are registered and what version they are " +
					"at");
		System.out.println(
			"upgrade:list | grep Registered | grep steps - " +
				"List upgrades in progress");
		System.out.println("verify:execute {module_name} - execute a verifier");
		System.out.println("verify:list - List all registered verifiers");
	}

	private Properties _readProperties(File file) {
		Properties properties = new Properties();

		if (file.exists()) {
			try (InputStream inputStream = new FileInputStream(file)) {
				properties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Unable to load " + file);
			}
		}

		return properties;
	}

	private void _saveProperties() throws IOException {
		_store(_dataSourceProperties, _dataSourcePropertiesFile);
		_store(_serverProperties, _serverPropertiesFile);
		_store(_upgradeProperties, _upgradePropertiesFile);
	}

	private void _store(Properties properties, File file) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(file)) {
			Enumeration<?> enumeration = properties.propertyNames();

			while (enumeration.hasMoreElements()) {
				String key = (String)enumeration.nextElement();
				String value = properties.getProperty(key);

				printWriter.println(key + "=" + value);
			}
		}
	}

	private void _verifyDataSourceProperties() throws IOException {
		String value = _dataSourceProperties.getProperty(
			"jdbc.default.driverClassName");

		if ((value == null) || value.isEmpty()) {
			String response;

			DataSource dataSource = null;

			while (dataSource == null) {
				System.out.print("[ ");

				for (String name : _dataSources.keySet()) {
					System.out.print(name + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your database (mysql): ");

				response = _consoleReader.readLine();

				if (response.isEmpty()) {
					response = "mysql";
				}

				dataSource = _dataSources.get(response);

				if (dataSource == null) {
					System.err.println(
						response + " is not a supported database");
				}
			}

			System.out.println(
				"Please enter your database JDBC driver protocol (" +
					dataSource.getProtocol() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setProtocol(response);
			}

			System.out.println(
				"Please enter your database JDBC driver class name(" +
					dataSource.getClassName() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setClassName(response);
			}

			System.out.println(
				"Please enter your database host (" + dataSource.getHost() +
					"): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setHost(response);
			}

			String portString;

			if (dataSource.getPort() > 0) {
				portString = String.valueOf(dataSource.getPort());
			}
			else {
				portString = "none";
			}

			System.out.println(
				"Please enter your database port (" + portString + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				if (response.equals("none")) {
					dataSource.setPort(0);
				}
				else {
					dataSource.setPort(Integer.parseInt(response));
				}
			}

			System.out.println(
				"Please enter your database name (" +
					dataSource.getDatabaseName() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setDatabaseName(response);
			}

			System.out.println("Please enter your database username: ");

			String username = _consoleReader.readLine();

			System.out.println("Please enter your database password: ");

			String password = _consoleReader.readLine();

			_dataSourceProperties.setProperty(
				"jdbc.default.driverClassName", dataSource.getClassName());
			_dataSourceProperties.setProperty(
				"jdbc.default.url", dataSource.getUrl());
			_dataSourceProperties.setProperty(
				"jdbc.default.username", username);
			_dataSourceProperties.setProperty(
				"jdbc.default.password", password);
		}
	}

	private void _verifyServerProperties() throws IOException {
		String value = _serverProperties.getProperty("dir");

		if ((value == null) || value.isEmpty()) {
			String response;

			while (_appServer == null) {
				System.out.print("[ ");

				for (String appServer : _appServers.keySet()) {
					System.out.print(appServer + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your app server (tomcat): ");

				response = _consoleReader.readLine();

				if (response.isEmpty()) {
					response = "tomcat";
				}

				_appServer = _appServers.get(response);

				if (_appServer == null) {
					System.err.println(
						response + " is not a supported app server");
				}
			}

			File dir = _appServer.getDir();
			File globalLibDir = _appServer.getGlobalLibDir();
			File portalDir = _appServer.getPortalDir();

			System.out.println(
				"Please enter your app server dir (" + dir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setDir(response);
			}

			System.out.println(
				"Please enter your global lib dir (" + globalLibDir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setGlobalLibDir(response);
			}

			System.out.println(
				"Please enter your portal dir (" + portalDir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setPortalDir(response);
			}

			_serverProperties.setProperty("dir", dir.getCanonicalPath());
			_serverProperties.setProperty(
				"global.dir.lib", _getRelativePath(dir, globalLibDir));
			_serverProperties.setProperty(
				"portal.dir", _getRelativePath(dir, portalDir));
		}
		else {
			_appServer = new AppServer(
				value, _serverProperties.getProperty("global.dir.lib"),
				_serverProperties.getProperty("portal.dir"));
		}
	}

	private void _verifyUpgradeProperties() throws IOException {
		String value = _upgradeProperties.getProperty("liferay.home");

		if ((value == null) || value.isEmpty()) {
			System.out.println("Please enter your Liferay home (../): ");

			String response = _consoleReader.readLine();

			if (response.isEmpty()) {
				response = "../";
			}

			File liferayHome = new File(response);

			_upgradeProperties.setProperty(
				"liferay.home", liferayHome.getCanonicalPath());
		}
	}

	private static final Map<String, AppServer> _appServers =
		new LinkedHashMap<>();
	private static final Map<String, DataSource> _dataSources =
		new LinkedHashMap<>();

	static {
		_appServers.put("jboss", AppServer.getJBossEAP());
		_appServers.put("jonas", AppServer.getJOnAS());
		_appServers.put("resin", AppServer.getResin());
		_appServers.put("tcserver", AppServer.getTCServer());
		_appServers.put("tomcat", AppServer.getTomcat());
		_appServers.put("weblogic", AppServer.getWeblogic());
		_appServers.put("websphere", AppServer.getWebsphere());
		_appServers.put("wildfly", AppServer.getWildFly());

		_dataSources.put("db2", DataSource.getDB2());
		_dataSources.put("mariadb", DataSource.getMariaDB());
		_dataSources.put("mysql", DataSource.getMySQL());
		_dataSources.put("oracle", DataSource.getOracle());
		_dataSources.put("postgresql", DataSource.getPostgreSQL());
		_dataSources.put("sqlserver", DataSource.getSQLServer());
		_dataSources.put("sybase", DataSource.getSybase());
	}

	private AppServer _appServer;
	private final ConsoleReader _consoleReader;
	private final Properties _dataSourceProperties;
	private final File _dataSourcePropertiesFile;
	private final String _jvmOpts;
	private final File _logFile;
	private final Properties _serverProperties;
	private final File _serverPropertiesFile;
	private final Properties _upgradeProperties;
	private final File _upgradePropertiesFile;

}