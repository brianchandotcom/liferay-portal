/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * @author Kenji Heigel
 */
public class CloudStorageSyncUtil {

	public static final String GCP_BUCKET_PATH_JENKINS_CI_DATA =
		"gs://jenkins-ci-data";

	public static final String GCP_BUCKET_PATH_PATCHER_SHARED =
		"gs://patcher-shared";

	public static final String GCP_BUCKET_PATH_TESTRAY_RESULTS =
		"gs://testray-results";

	public static void syncGCPFiles(String source, String destination) {
		List<String> commands = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud auth activate-service-account --key-file ");

		if (source.startsWith(GCP_BUCKET_PATH_JENKINS_CI_DATA) ||
			destination.startsWith(GCP_BUCKET_PATH_JENKINS_CI_DATA)) {

			sb.append(
				_buildProperties.getProperty(
					"google.application.crendential.file[jenkins]"));

			commands.add(sb.toString());
		}
		else if (source.startsWith(GCP_BUCKET_PATH_PATCHER_SHARED) ||
				 destination.startsWith(GCP_BUCKET_PATH_PATCHER_SHARED)) {

			sb.append(
				_buildProperties.getProperty(
					"google.application.crendential.file[patcher]"));

			commands.add(sb.toString());
		}
		else if (source.startsWith(GCP_BUCKET_PATH_TESTRAY_RESULTS) ||
				 destination.startsWith(GCP_BUCKET_PATH_TESTRAY_RESULTS)) {

			sb.append(
				_buildProperties.getProperty(
					"google.application.crendential.file[testray]"));

			commands.add(sb.toString());
		}

		sb.setLength(0);

		sb.append("gcloud storage rsync --recursive ");
		sb.append(source);
		sb.append(" ");
		sb.append(destination);

		commands.add(sb.toString());

		_executeCommands(commands.toArray(new String[0]));
	}

	private static void _executeCommands(String... commands) {
		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				1000 * 60 * 10, commands);

			System.out.println(
				JenkinsResultsParserUtil.readInputStream(
					process.getInputStream()));

			if (process.exitValue() != 0) {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException("Unable to sync directories");
			}
		}
		catch (IOException | TimeoutException exception) {
			System.out.println("Unable to sync directories");

			throw new RuntimeException(exception);
		}
	}

	private static final Properties _buildProperties;

	static {
		_buildProperties = new Properties() {
			{
				try {
					putAll(JenkinsResultsParserUtil.getBuildProperties());
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		};
	}

}