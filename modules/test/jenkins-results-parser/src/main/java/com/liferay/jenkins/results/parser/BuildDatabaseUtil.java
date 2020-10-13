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
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class BuildDatabaseUtil {

	public static BuildDatabase getBuildDatabase() {
		return getBuildDatabase(null, null, true);
	}

	public static BuildDatabase getBuildDatabase(Build build) {
		return getBuildDatabase(_getDistPath(build), build, true);
	}

	public static BuildDatabase getBuildDatabase(
		String baseDirPath, boolean download) {

		return getBuildDatabase(baseDirPath, null, download);
	}

	public static BuildDatabase getBuildDatabase(
		String baseDirPath, Build build, boolean download) {

		if (_buildDatabase != null) {
			return _buildDatabase;
		}

		if (baseDirPath == null) {
			baseDirPath = System.getenv("WORKSPACE");

			if (baseDirPath == null) {
				throw new RuntimeException("Please set WORKSPACE");
			}
		}

		File baseDir = new File(baseDirPath);

		if (!baseDir.exists()) {
			baseDir.mkdirs();
		}

		String distNodes = System.getenv("DIST_NODES");
		String distPath = System.getenv("DIST_PATH");

		if ((distNodes != null) && (distPath != null) && download) {
			_downloadBuildDatabaseFile(baseDir, distNodes, distPath);
		}
		else if ((build instanceof TopLevelBuild) && download) {
			_downloadBuildDatabaseFile(baseDir, build);
		}

		_buildDatabase = new DefaultBuildDatabase(baseDir);

		return _buildDatabase;
	}

	public static void reset() {
		_buildDatabase = null;
	}

	private static void _downloadBuildDatabaseFile(File baseDir, Build build) {
		JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

		String masterName = jenkinsMaster.getName();

		String jobName = build.getJobName();
		int buildNumber = build.getBuildNumber();

		if (JenkinsResultsParserUtil.isCINode()) {
			String filePath =
				"/opt/java/jenkins/userContent/jobs/" + jobName + "/builds/" +
					buildNumber;

			_downloadBuildDatabaseFile(baseDir, masterName, filePath);

			return;
		}

		try {
			String buildDatabaseURL =
				"http://" + masterName + "/userContent/jobs/" + jobName +
					"/builds/" + buildNumber + "/" +
						BuildDatabase.FILE_NAME_BUILD_DATABASE;

			JenkinsResultsParserUtil.write(
				baseDir + "/" + BuildDatabase.FILE_NAME_BUILD_DATABASE,
				JenkinsResultsParserUtil.toString(buildDatabaseURL));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static void _downloadBuildDatabaseFile(
		File baseDir, String distNodes, String distPath) {

		File buildDatabaseFile = new File(
			baseDir, BuildDatabase.FILE_NAME_BUILD_DATABASE);

		if (buildDatabaseFile.exists()) {
			return;
		}

		if (!JenkinsResultsParserUtil.isCINode()) {
			try {
				JenkinsResultsParserUtil.write(buildDatabaseFile, "{}");
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

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
					"time rsync -Iqs --timeout=1200 ", distNode, ":", distPath,
					"/", BuildDatabase.FILE_NAME_BUILD_DATABASE, " ",
					JenkinsResultsParserUtil.getCanonicalPath(
						buildDatabaseFile));

				command = command.replaceAll("\\(", "\\\\(");
				command = command.replaceAll("\\)", "\\\\)");

				Process process = JenkinsResultsParserUtil.executeBashCommands(
					true, command);

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

	private static String _getDistPath(Build build) {
		StringBuilder sb = new StringBuilder();

		if (JenkinsResultsParserUtil.isWindows()) {
			sb.append("C:");
		}

		sb.append("/tmp/jenkins/");

		JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(build.getJobName());
		sb.append("/");
		sb.append(build.getBuildNumber());

		return sb.toString();
	}

	private static BuildDatabase _buildDatabase;

}