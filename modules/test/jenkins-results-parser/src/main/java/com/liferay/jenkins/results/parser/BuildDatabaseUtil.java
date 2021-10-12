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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class BuildDatabaseUtil {

	public static BuildDatabase getBuildDatabase() {
		return getBuildDatabase(null);
	}

	public static BuildDatabase getBuildDatabase(Build build) {
		File buildDir = _getBuildDir(build);

		synchronized (_buildDatabases) {
			BuildDatabase buildDatabase = _buildDatabases.get(buildDir);

			if (buildDatabase != null) {
				return buildDatabase;
			}

			_downloadBuildDatabaseFile(buildDir, build);

			buildDatabase = new DefaultBuildDatabase(buildDir);

			_buildDatabases.put(buildDir, buildDatabase);

			return buildDatabase;
		}
	}

	private static void _downloadBuildDatabaseFile(File buildDir, Build build) {
		if (!buildDir.exists()) {
			buildDir.mkdirs();
		}

		File buildDatabaseFile = new File(
			buildDir, BuildDatabase.FILE_NAME_BUILD_DATABASE);

		if (buildDatabaseFile.exists()) {
			return;
		}

		String distNodes = System.getenv("DIST_NODES");
		String distPath = System.getenv("DIST_PATH");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(distNodes) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(distPath)) {

			_downloadBuildDatabaseFileFromDistNodes(
				buildDatabaseFile, distNodes, distPath);
		}

		if (buildDatabaseFile.exists()) {
			return;
		}

		if (build instanceof TopLevelBuild) {
			_downloadBuildDatabaseFileFromTopLevelBuild(
				buildDatabaseFile, (TopLevelBuild)build);
		}
	}

	private static void _downloadBuildDatabaseFileFromDistNodes(
		File buildDatabaseFile, String distNodes, String distPath) {

		if (buildDatabaseFile.exists()) {
			return;
		}

		int maxRetries = 5;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String distNode = JenkinsResultsParserUtil.getRandomString(
					Arrays.asList(distNodes.split(",")));

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -Iq --timeout=1200 \"", distNode, ":", distPath,
					"/", BuildDatabase.FILE_NAME_BUILD_DATABASE, "\" ",
					JenkinsResultsParserUtil.getCanonicalPath(
						buildDatabaseFile));

				command = command.replaceAll("\\(", "\\\\(");
				command = command.replaceAll("\\)", "\\\\)");

				Process process = JenkinsResultsParserUtil.executeBashCommands(
					true, new File("."), 10 * 60 * 1000, command);

				if (process.exitValue() != 0) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to download ",
							BuildDatabase.FILE_NAME_BUILD_DATABASE));
				}

				break;
			}
			catch (IOException | RuntimeException | TimeoutException
						exception) {

				if (retries == maxRetries) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to get ",
							BuildDatabase.FILE_NAME_BUILD_DATABASE, " file"),
						exception);
				}

				System.out.println(
					"Unable to execute bash commands, retrying... ");

				exception.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	private static void _downloadBuildDatabaseFileFromTopLevelBuild(
		File buildDatabaseFile, TopLevelBuild topLevelBuild) {

		if (buildDatabaseFile.exists()) {
			return;
		}

		String buildDatabaseURL = JenkinsResultsParserUtil.getLocalURL(
			JenkinsResultsParserUtil.getBuildArtifactURL(
				topLevelBuild.getBuildURL(), buildDatabaseFile.getName()));

		String buildDatabaseFilePath = buildDatabaseURL.replaceAll(
			".*/(userContent/.*)", "/opt/java/jenkins/$1");

		buildDatabaseFilePath = buildDatabaseFilePath.replace("%28", "(");
		buildDatabaseFilePath = buildDatabaseFilePath.replace("%29", ")");

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				JenkinsResultsParserUtil.combine(
					"ssh ", jenkinsMaster.getName(), " ls \"",
					JenkinsResultsParserUtil.escapeForBash(
						buildDatabaseFilePath),
					"\""));

			if (process.exitValue() != 0) {
				return;
			}
		}
		catch (IOException | TimeoutException exception) {
			return;
		}

		try {
			_downloadBuildDatabaseFileFromDistNodes(
				buildDatabaseFile, jenkinsMaster.getName(),
				buildDatabaseFilePath.replaceAll("(.*)/[^/]+", "$1"));

			return;
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		try {
			JenkinsResultsParserUtil.write(
				buildDatabaseFile,
				JenkinsResultsParserUtil.toString(buildDatabaseURL));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static File _getBuildDir(Build build) {
		if (build != null) {
			return new File(build.getBuildDirPath());
		}

		String buildDir = System.getenv("BUILD_DIR");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildDir)) {
			return new File(buildDir);
		}

		return new File(JenkinsResultsParserUtil.getBuildDirPath());
	}

	private static final Map<File, BuildDatabase> _buildDatabases =
		new HashMap<>();

}