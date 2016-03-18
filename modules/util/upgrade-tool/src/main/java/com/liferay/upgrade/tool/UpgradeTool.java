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

package com.liferay.upgrade.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jline.console.ConsoleReader;

/**
 * @author David Truong
 */
public class UpgradeTool {

	public static final String JAVA_HOME = System.getenv("JAVA_HOME");

	public static final Map<String, AppServer> appServers =
		new LinkedHashMap<>();
	public static final Map<String, DatabaseConnection> databases =
		new LinkedHashMap<>();

	static {
		databases.put("db2", DatabaseConnection.getDB2Connection());
		databases.put("mariadb", DatabaseConnection.getMariaDBConnection());
		databases.put("mysql", DatabaseConnection.getMySQLConnection());
		databases.put("oracle", DatabaseConnection.getOracleConnection());
		databases.put(
			"postgresql", DatabaseConnection.getPostgreSQLConnection());
		databases.put("sqlserver", DatabaseConnection.getSQLServerConnection());
		databases.put("sybase", DatabaseConnection.getSybaseConnection());
	}

	static {
		appServers.put("jboss", AppServer.getJBossEAP());
		appServers.put("jonas", AppServer.getJOnAS());
		appServers.put("resin", AppServer.getResin());
		appServers.put("tcserver", AppServer.getTCServer());
		appServers.put("tomcat", AppServer.getTomcat());
		appServers.put("weblogic", AppServer.getWeblogic());
		appServers.put("websphere", AppServer.getWebsphere());
		appServers.put("wildfly", AppServer.getWildfly());
	}

	public static void main(String[] args) {
		try {
			UpgradeTool upgradeTool = new UpgradeTool();

			upgradeTool.upgrade();
		}
		catch (Exception e) {
			System.err.println("Error running upgrade");

			e.printStackTrace();
		}
	}

	public UpgradeTool() throws Exception {
		_consoleReader = new ConsoleReader();

		_portalPropertiesFile = new File("portal-ext.properties");

		_portalProperties = new Properties();

		if (_portalPropertiesFile.exists()) {
			try (
				InputStream inputStream =
					new FileInputStream(_portalPropertiesFile)) {

				_portalProperties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Could not load portal-ext.properties");
			}
		}

		_serverPropertiesFile = new File("server.properties");

		_serverProperties = new Properties();

		if (_serverPropertiesFile.exists()) {
			try (
				InputStream inputStream =
					new FileInputStream(_serverPropertiesFile)) {

				_serverProperties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Could not load server.properties");
			}
		}
	}

	public void upgrade() throws Exception {
		verifyProperties();

		String classPath = _getClassPath();

		List commands = new ArrayList<>();

		if (JAVA_HOME != null) {
			commands.add(JAVA_HOME + "/bin/java");
		}
		else {
			commands.add("java");
		}

		commands.add("-Xmx2048m");
		commands.add("-Dfile.encoding=UTF8");
		commands.add("-Duser.country=US");
		commands.add("-Duser.language=en");
		commands.add("-Duser.timezone=GMT");
		commands.add("-cp");
		commands.add(classPath);
		commands.add("com.liferay.portal.tools.DBUpgrader");

		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);

		processBuilder.command(commands);

		Process process = processBuilder.start();

		try (InputStreamReader isr = new InputStreamReader(
				process.getInputStream());
			BufferedReader br = new BufferedReader(isr)) {

			String line;

			while ((line = br.readLine()) != null) {
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

		try (GogoTelnetClient client = new GogoTelnetClient()) {
			System.out.println("You are now connected to Gogo Shell");

			_printHelp();

			_consoleReader.setPrompt("g! ");

			String line;

			while ((line = _consoleReader.readLine()) != null) {
				if (line.equals("exit") || line.equals("quit")) {
					break;
				}
				else if (line.equals("help")) {
					_printHelp();
				}
				else {
					System.out.println(client.send(line));
				}
			}

			System.out.print(
				"Making sure all upgrades steps have been completed");

			boolean upgrading = true;

			while(upgrading) {
				System.out.print("...");

				String upgradeSteps =
					client.send("upgrade:list | grep Registered | grep step");

				upgrading = upgradeSteps.contains("true");
			}
			System.out.println("done.");

			System.out.println("Exiting Gogo Shell");

		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		process.getInputStream().close();
		process.getOutputStream().close();
		process.getErrorStream().close();

		if(!process.waitFor(5, TimeUnit.SECONDS)) {
			process.destroy();
		}
	}

	public void verifyProperties() {
		try {
			_verifyPortalProperties();

			_verifyServerProperties();

			_saveProperties();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private String _getClassPath() throws Exception {
		StringBuilder classPath = new StringBuilder();

		String liferayClassPath = System.getenv("LIFERAY_CLASSPATH");

		if ((liferayClassPath != null) && !liferayClassPath.equals("")) {
			classPath.append(liferayClassPath);

			classPath.append(File.pathSeparator);
		}

		_getLibs(classPath, new File("lib"));

		_getLibs(classPath, new File(_appServer.getDir(), "bin"));

		_getLibs(classPath, _appServer.getGlobalLibDir());

		classPath.append(_appServer.getPortalClassesDir().getCanonicalPath());

		_getLibs(classPath, _appServer.getPortalLibDir());

		return classPath.toString();
	}

	private void _getLibs(StringBuilder classPath, File dir) throws Exception {
		if (dir.exists() && dir.isDirectory()) {
			for (File lib : dir.listFiles()) {
				String fileName = lib.getName();

				if (lib.isFile() && fileName.endsWith("jar")) {
					classPath.append(
						lib.getCanonicalPath() + File.pathSeparator);
				}
				else if (lib.isDirectory()) {
					_getLibs(classPath, lib);
				}
			}
		}
	}

	private String _getRelativePath(File base, File path) {
		return _getRelativePath(base.toPath(), path.toPath());
	}

	private String _getRelativePath(Path base, Path path) {
		Path relative = base.relativize(path);

		return relative.toString();
	}

	private void _printHelp() {
		System.out.println("\nUpgrade commands:");
		System.out.println("exit or quit - exit Gogo Shell");
		System.out.println("help - show upgrade commands");
		System.out.println("upgrade:execute {module_name} - " +
			"Execute upgrade for that module");
		System.out.println("upgrade:list - List all registered upgrades");
		System.out.println("upgrade:list {module_name} - " +
			"List the upgrade steps required for that module");
		System.out.println("upgrade:list | grep Registered - " +
			"List upgrades that are registered and what version they are at");
		System.out.println("upgrade:list | grep Registered | grep steps - " +
			"List upgrades in progress");
		System.out.println("verify:execute {module_name} - execute a verifier");
		System.out.println("verify:list - List all registered verifiers");
	}

	private void _saveProperties() throws IOException {
		_store(_serverProperties, _serverPropertiesFile);

		_store(_portalProperties, _portalPropertiesFile);
	}

	private void _store(Properties props, File propertyFile)
		throws FileNotFoundException {

		try (PrintWriter pw = new PrintWriter(propertyFile)) {
			for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String)e.nextElement();
				pw.println(key + "=" + props.getProperty(key));
			}
		}
	}

	private void _verifyPortalProperties() throws IOException {
		String value = _portalProperties.getProperty("liferay.home");

		if ((value == null) || value.equals("")) {
			System.out.print("Please enter your Liferay home (../): ");

			String response = _consoleReader.readLine();

			if (response.equals("")) {
				response = "../";
			}

			File liferayHome = new File(response);

			_portalProperties.setProperty(
				"liferay.home", liferayHome.getCanonicalPath());

			for (String name : databases.keySet()) {
				System.out.println(name);
			}

			System.out.println("Please enter your database (mariadb): ");

			response = _consoleReader.readLine();

			if (response.equals("")) {
				response = "mariadb";
			}

			DatabaseConnection database = databases.get(response);

			System.out.println(
				"Please enter your database host (" + database.getHost() +
					"):");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				database.setHost(response);
			}

			System.out.println(
				"Please enter your database port (" +
					(database.getPort() > 0 ? database.getPort() : "none") +
						"):");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				if (response.equals("none")) {
					database.setPort(0);
				}
				else {
					database.setPort(Integer.parseInt(response));
				}
			}

			System.out.println(
				"Please enter your database name (" +
					database.getDatabaseName() + "):");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				database.setDatabaseName(response);
			}

			System.out.println("Please enter your database username: ");

			String username = _consoleReader.readLine();

			System.out.println("Please enter your database password: ");

			String password = _consoleReader.readLine();

			_portalProperties.setProperty(
				"jdbc.default.driverClassName", database.getClassName());
			_portalProperties.setProperty(
				"jdbc.default.url", database.getUrl());
			_portalProperties.setProperty("jdbc.default.username", username);
			_portalProperties.setProperty("jdbc.default.password", password);
		}
	}

	private void _verifyServerProperties() throws IOException {
		String value = _serverProperties.getProperty("dir");

		if ((value == null) || value.equals("")) {
			for (String appServer : appServers.keySet()) {
				System.out.println(appServer);
			}

			System.out.println("Please enter your app server (tomcat):");

			String response = _consoleReader.readLine();

			if (response.equals("")) {
				response = "tomcat";
			}

			_appServer = appServers.get(response);

			File dir = _appServer.getDir();
			File globalLibDir = _appServer.getGlobalLibDir();
			File portalDir = _appServer.getPortalDir();

			System.out.println(
				"Please enter your app server dir (" + dir + "):");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				_appServer.setDir(response);
			}

			System.out.println(
				"Please enter your global lib dir (" + globalLibDir + "):");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				_appServer.setGlobalLibDir(response);
			}

			System.out.println(
				"Please enter your portal dir (" + portalDir + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
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

	private AppServer _appServer;
	private final ConsoleReader _consoleReader;
	private final Properties _portalProperties;
	private final File _portalPropertiesFile;
	private final Properties _serverProperties;
	private final File _serverPropertiesFile;

}