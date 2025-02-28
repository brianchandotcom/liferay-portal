/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenji Heigel
 */
public class CloudStorageSyncUtil {

	public static final String GCP_BUCKET_PATH_JENKINS_CI_DATA =
		"gs://jenkins-ci-data";

	public static final String GCP_BUCKET_PATH_LIFERAY_RELEASE_CANDIDATES =
		"gs://liferay-releases-candidates";

	public static final String GCP_BUCKET_PATH_PATCHER_SHARED =
		"gs://patcher-shared";

	public static final String GCP_BUCKET_PATH_TESTRAY_RESULTS =
		"gs://testray-results";

	public static void copyGCPFile(String destination, String source)
		throws IOException {

		List<String> commands = new ArrayList<>();

		commands.add(_getGCPAuthenticationCommand(destination, source));

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud storage cp ");
		sb.append(source);
		sb.append(" ");
		sb.append(destination);

		commands.add(sb.toString());

		_executeCommands(commands.toArray(new String[0]));
	}

	public static void copyS3File(String destination, String source) {
		List<String> commands = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("aws s3 cp ");
		sb.append(source);
		sb.append(" ");
		sb.append(destination);

		commands.add(sb.toString());

		_executeCommands(commands.toArray(new String[0]));
	}

	public static String getSignedURL(int duration, String file, String url)
		throws IOException, TimeoutException {

		if (JenkinsResultsParserUtil.isNullOrEmpty(file) ||
			JenkinsResultsParserUtil.isNullOrEmpty(url)) {

			return null;
		}

		List<String> commands = new ArrayList<>();

		commands.add(_getGCPAuthenticationCommand(url, url));

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud storage sign-url ");
		sb.append(url);
		sb.append(" --private-key-file=");
		sb.append(file);
		sb.append(" --duration=");
		sb.append(duration);
		sb.append("m");

		commands.add(sb.toString());

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			true, commands.toArray(new String[0]));

		Matcher matcher = _signedURLPattern.matcher(
			JenkinsResultsParserUtil.readInputStream(process.getInputStream()));

		if (matcher.find()) {
			return matcher.group(0);
		}

		return null;
	}

	public static void syncGCPFiles(String destination, String source)
		throws IOException {

		List<String> commands = new ArrayList<>();

		commands.add(_getGCPAuthenticationCommand(destination, source));

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud storage rsync --recursive ");
		sb.append(source);
		sb.append(" ");
		sb.append(destination);

		commands.add(sb.toString());

		_executeCommands(commands.toArray(new String[0]));
	}

	public static void syncS3Files(String destination, String source) {
		List<String> commands = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("aws s3 sync ");
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

	private static String _getGCPAuthenticationCommand(
			String destination, String source)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud auth activate-service-account --key-file ");

		String gcpApplicationCredentialFilePath = null;

		if (destination.startsWith(GCP_BUCKET_PATH_JENKINS_CI_DATA) ||
			destination.startsWith(
				GCP_BUCKET_PATH_LIFERAY_RELEASE_CANDIDATES) ||
			source.startsWith(GCP_BUCKET_PATH_JENKINS_CI_DATA) ||
			source.startsWith(GCP_BUCKET_PATH_LIFERAY_RELEASE_CANDIDATES)) {

			gcpApplicationCredentialFilePath = _buildProperties.getProperty(
				"google.application.crendential.file[jenkins]");
		}
		else if (destination.startsWith(GCP_BUCKET_PATH_PATCHER_SHARED) ||
				 source.startsWith(GCP_BUCKET_PATH_PATCHER_SHARED)) {

			gcpApplicationCredentialFilePath = _buildProperties.getProperty(
				"google.application.crendential.file[patcher]");
		}
		else if (destination.startsWith(GCP_BUCKET_PATH_TESTRAY_RESULTS) ||
				 source.startsWith(GCP_BUCKET_PATH_TESTRAY_RESULTS)) {

			gcpApplicationCredentialFilePath = _buildProperties.getProperty(
				"google.application.crendential.file[testray]");
		}

		if (gcpApplicationCredentialFilePath != null) {
			File gcpApplicationCredentialFile = new File(
				gcpApplicationCredentialFilePath);

			if (gcpApplicationCredentialFile.exists()) {
				sb.append(gcpApplicationCredentialFilePath);

				return sb.toString();
			}
		}

		throw new IOException("Unable to find GCP application credential file");
	}

	private static final Properties _buildProperties;
	private static final Pattern _signedURLPattern = Pattern.compile(
		"https:\\/\\/storage.googleapis.com\\/.*");

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