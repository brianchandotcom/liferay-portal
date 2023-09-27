/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class BuildDatabaseUtil {

	public static void clear() {
		File buildDir = _getBuildDir(null);

		File buildDatabaseFile = new File(
			buildDir, BuildDatabase.FILE_NAME_BUILD_DATABASE);

		if (buildDatabaseFile.exists()) {
			buildDatabaseFile.delete();
		}
	}

	public static BuildDatabase getBuildDatabase() {
		return getBuildDatabase(null);
	}

	public static BuildDatabase getBuildDatabase(Build build) {
		TopLevelBuild topLevelBuild = null;

		if (build != null) {
			topLevelBuild = build.getTopLevelBuild();
		}

		if ((build instanceof TopLevelBuild) || (topLevelBuild == null)) {
			File buildDir = _getBuildDir(build);

			synchronized (_buildDatabases) {
				BuildDatabase buildDatabase = _buildDatabases.get(buildDir);

				if (buildDatabase != null) {
					buildDatabase.readBuildDatabaseFile();

					return buildDatabase;
				}

				_downloadBuildDatabaseFile(buildDir, build);

				buildDatabase = new DefaultBuildDatabase(buildDir);

				_buildDatabases.put(buildDir, buildDatabase);

				return buildDatabase;
			}
		}

		return getBuildDatabase(topLevelBuild);
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

		if (buildDatabaseFile.exists() || (build == null)) {
			return;
		}

		if (build.isFromArchive()) {
			try {
				JenkinsResultsParserUtil.write(
					buildDatabaseFile,
					JenkinsResultsParserUtil.toString(
						build.getBuildURL() + "/build-database.json"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to write build-database.json", ioException);
			}

			return;
		}

		if (build instanceof TopLevelBuild) {
			_downloadBuildDatabaseFileFromTopLevelBuild(
				buildDatabaseFile, (TopLevelBuild)build);

			if (!JenkinsResultsParserUtil.isCINode()) {
				File defaultBuildDir = new File(
					JenkinsResultsParserUtil.getBuildDirPath());

				if (!defaultBuildDir.exists()) {
					defaultBuildDir.mkdirs();
				}

				File defaultBuildDatabaseFile = new File(
					defaultBuildDir, BuildDatabase.FILE_NAME_BUILD_DATABASE);

				try {
					Files.copy(
						buildDatabaseFile.toPath(),
						defaultBuildDatabaseFile.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		}
	}

	private static void _downloadBuildDatabaseFileFromDistNodes(
		File buildDatabaseFile, String distNodes, String distPath) {

		if (buildDatabaseFile.exists()) {
			return;
		}

		List<String> distNodesList = new ArrayList<>(
			Arrays.asList(distNodes.split(",")));

		while (!distNodesList.isEmpty()) {
			try {
				String distNode = JenkinsResultsParserUtil.getRandomString(
					distNodesList);

				distNodesList.remove(distNode);

				String[] commands = new String[2];

				commands[0] = JenkinsResultsParserUtil.combine(
					"mkdir -p ",
					JenkinsResultsParserUtil.escapeForBash(
						JenkinsResultsParserUtil.getCanonicalPath(
							buildDatabaseFile.getParentFile())));

				if (JenkinsResultsParserUtil.isOSX()) {
					commands[1] = JenkinsResultsParserUtil.combine(
						"timeout 1200 rsync -Iq \"root@", distNode, ":",
						JenkinsResultsParserUtil.escapeForBash(distPath), "/",
						BuildDatabase.FILE_NAME_BUILD_DATABASE, "\" ",
						JenkinsResultsParserUtil.escapeForBash(
							JenkinsResultsParserUtil.getCanonicalPath(
								buildDatabaseFile)));
				}
				else if (JenkinsResultsParserUtil.isWindows()) {
					commands[0] = JenkinsResultsParserUtil.combine(
						"mkdir -p ",
						JenkinsResultsParserUtil.getCanonicalPath(
							buildDatabaseFile.getParentFile()));

					distPath = distPath.replaceAll(
						"C:.*TEMP/dist", "/tmp/dist");

					File bashFile = new File(
						"C:/tmp/jenkins/" +
							JenkinsResultsParserUtil.getCurrentTimeMillis() +
								".sh");

					JenkinsResultsParserUtil.write(
						bashFile,
						JenkinsResultsParserUtil.combine(
							"#!/bin/sh\nscp \"", distNode, ":",
							JenkinsResultsParserUtil.escapeForBash(distPath),
							"/", BuildDatabase.FILE_NAME_BUILD_DATABASE, "\" ",
							JenkinsResultsParserUtil.escapeForBash(
								JenkinsResultsParserUtil.getCanonicalPath(
									buildDatabaseFile))));

					commands[1] =
						"/bin/sh " +
							JenkinsResultsParserUtil.getCanonicalPath(bashFile);
				}
				else {
					commands[1] = JenkinsResultsParserUtil.combine(
						"if ! ", "timeout 1200 rsync -Iq \"", distNode, ":",
						JenkinsResultsParserUtil.escapeForBash(distPath), "/",
						BuildDatabase.FILE_NAME_BUILD_DATABASE, "\" ",
						JenkinsResultsParserUtil.escapeForBash(
							JenkinsResultsParserUtil.getCanonicalPath(
								buildDatabaseFile)),
						"; then ", "timeout 1200 rsync -Iq ", distNode, ":",
						JenkinsResultsParserUtil.escapeForBash(distPath), "/",
						BuildDatabase.FILE_NAME_BUILD_DATABASE, " ",
						JenkinsResultsParserUtil.escapeForBash(
							JenkinsResultsParserUtil.getCanonicalPath(
								buildDatabaseFile)),
						"; fi");
				}

				Process process = JenkinsResultsParserUtil.executeBashCommands(
					true, new File("."), 10 * 60 * 1000, commands);

				if (process.exitValue() != 0) {
					String errorText = JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream());

					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to download ",
							BuildDatabase.FILE_NAME_BUILD_DATABASE, "\n\n",
							errorText));
				}

				break;
			}
			catch (IOException | RuntimeException | TimeoutException
						exception) {

				if (distNodesList.isEmpty()) {
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

		String buildDatabaseURL = JenkinsResultsParserUtil.getLocalURL(
			JenkinsResultsParserUtil.getBuildArtifactURL(
				topLevelBuild.getBuildURL(), buildDatabaseFile.getName()));

		if (!JenkinsResultsParserUtil.isCINode()) {
			try {
				JenkinsResultsParserUtil.write(
					buildDatabaseFile,
					JenkinsResultsParserUtil.toString(buildDatabaseURL));
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}

			return;
		}

		if (buildDatabaseFile.exists()) {
			return;
		}

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