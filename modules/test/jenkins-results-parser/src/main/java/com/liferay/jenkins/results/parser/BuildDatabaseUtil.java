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
		if (_buildDatabase != null) {
			return _buildDatabase;
		}

		String workspace = System.getenv("WORKSPACE");

		if (workspace == null) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		File baseDir = new File(workspace);

		if (!baseDir.exists()) {
			baseDir.mkdir();
		}

		String distNodes = System.getenv("DIST_NODES");
		String distPath = System.getenv("DIST_PATH");

		if ((distNodes != null) && (distPath != null)) {
			_downloadBuildDatabaseFile(baseDir, distNodes, distPath);
		}

		_buildDatabase = new DefaultBuildDatabase(baseDir);

		return _buildDatabase;
	}

	private static void _downloadBuildDatabaseFile(
		File baseDir, String distNodes, String distPath) {

		File buildDatabaseFile = new File(
			baseDir, BuildDatabase.BUILD_DATABASE_FILE_NAME);

		if (buildDatabaseFile.exists()) {
			return;
		}

		if (!JenkinsResultsParserUtil.isCINode()) {
			try {
				JenkinsResultsParserUtil.write(buildDatabaseFile, "{}");
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}

			return;
		}

		int maxRetries = 3;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String distNode = JenkinsResultsParserUtil.getRandomString(
					Arrays.asList(distNodes.split(",")));

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -Iqs --timeout=1200 ", distNode, ":", distPath,
					"/", BuildDatabase.BUILD_DATABASE_FILE_NAME, " ",
					buildDatabaseFile.getCanonicalPath());

				command = command.replaceAll("\\(", "\\\\(");
				command = command.replaceAll("\\)", "\\\\)");

				JenkinsResultsParserUtil.executeBashCommands(command);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to get ",
							BuildDatabase.BUILD_DATABASE_FILE_NAME, " file"),
						e);
				}

				System.out.println(
					"Unable to execute bash commands, retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	private static BuildDatabase _buildDatabase;

}