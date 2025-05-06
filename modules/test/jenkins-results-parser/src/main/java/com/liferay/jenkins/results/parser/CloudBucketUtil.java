/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenji Heigel
 */
public class CloudBucketUtil {

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

		_executeCommands(
			_getGCPAuthenticationCommand(destination, source),
			_getFileTransferCommand("gcloud storage cp", destination, source));
	}

	public static void copyS3File(String destination, String source) {
		_executeCommands(
			_getFileTransferCommand(
				"aws s3 cp --no-progress", destination, source));

		Matcher destinationS3ObjectPathMatcher = _s3ObjectPathPattern.matcher(
			destination);

		if (destinationS3ObjectPathMatcher.find()) {
			createS3ObjectRef(destination);
		}

		Matcher sourceS3ObjectPathMatcher = _s3ObjectPathPattern.matcher(
			source);

		if (sourceS3ObjectPathMatcher.find()) {
			createS3ObjectRef(source);
		}
	}

	public static void createS3ObjectRef(String s3ObjectPath) {
		_validateS3ObjectPath(s3ObjectPath);

		createS3ObjectRef(s3ObjectPath, s3ObjectPath);
	}

	public static void createS3ObjectRef(
		String sourceS3ObjectPath, String destinationS3ObjectPath) {

		_validateS3ObjectPath(sourceS3ObjectPath);
		_validateS3ObjectPath(destinationS3ObjectPath);

		File destinationS3ObjectFile = _getS3ObjectRefFile(
			destinationS3ObjectPath);

		if (destinationS3ObjectFile.exists()) {
			return;
		}

		try {
			File parentFile = destinationS3ObjectFile.getParentFile();

			parentFile.mkdirs();

			JenkinsResultsParserUtil.write(
				destinationS3ObjectFile, sourceS3ObjectPath);

			System.out.println("Created " + destinationS3ObjectFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static String getSignedURL(int duration, String file, String url)
		throws IOException, TimeoutException {

		if (JenkinsResultsParserUtil.isNullOrEmpty(file) ||
			JenkinsResultsParserUtil.isNullOrEmpty(url)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("gcloud storage sign-url ");
		sb.append(url);
		sb.append(" --private-key-file=");
		sb.append(file);
		sb.append(" --duration=");
		sb.append(duration);
		sb.append("m");

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			true, _getGCPAuthenticationCommand(url, url), sb.toString());

		Matcher matcher = _signedURLPattern.matcher(
			JenkinsResultsParserUtil.readInputStream(process.getInputStream()));

		if (matcher.find()) {
			return matcher.group(0);
		}

		return null;
	}

	public static String listGCPFiles(String path)
		throws IOException, TimeoutException {

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			true, _getGCPAuthenticationCommand(path, path),
			"gcloud storage ls " + _escapeParentheses(path));

		return JenkinsResultsParserUtil.readInputStream(
			process.getInputStream());
	}

	public static String listS3Files(String path)
		throws IOException, TimeoutException {

		if (!path.endsWith("/")) {
			path += "/";
		}

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			true, "aws s3 ls " + _escapeParentheses(path));

		return JenkinsResultsParserUtil.readInputStream(
			process.getInputStream());
	}

	public static void syncGCPFiles(String destination, String source)
		throws IOException {

		_executeCommands(
			_getGCPAuthenticationCommand(destination, source),
			_getFileTransferCommand(
				"gcloud storage rsync --recursive", destination, source));
	}

	public static void syncS3Files(String destination, String source) {
		_executeCommands(
			_getFileTransferCommand(
				"aws s3 sync --no-progress", destination, source));

		try {
			Matcher destinationS3ObjectPathMatcher =
				_s3ObjectPathPattern.matcher(destination);

			if (destinationS3ObjectPathMatcher.find()) {
				Matcher listS3FilesMatcher = _listS3FilesPattern.matcher(
					listS3Files(destination));

				while (listS3FilesMatcher.find()) {
					createS3ObjectRef(
						JenkinsResultsParserUtil.combine(
							destination, "/",
							listS3FilesMatcher.group("fileName")));
				}
			}

			Matcher sourceS3ObjectPathMatcher = _s3ObjectPathPattern.matcher(
				source);

			if (sourceS3ObjectPathMatcher.find()) {
				Matcher listS3FilesMatcher = _listS3FilesPattern.matcher(
					listS3Files(source));

				while (listS3FilesMatcher.find()) {
					createS3ObjectRef(
						JenkinsResultsParserUtil.combine(
							source, "/", listS3FilesMatcher.group("fileName")));
				}
			}
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static String _escapeParentheses(String s) {
		s = s.replace("(", "\\(");
		s = s.replace(")", "\\)");

		return s;
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

	private static String _getFileTransferCommand(
		String command, String destination, String source) {

		StringBuilder sb = new StringBuilder();

		sb.append(command);
		sb.append(" ");
		sb.append(_escapeParentheses(source));
		sb.append(" ");
		sb.append(_escapeParentheses(destination));

		return sb.toString();
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

	private static File _getS3ObjectRefFile(String s3ObjectPath) {
		Matcher s3ObjectPathMatcher = _s3ObjectPathPattern.matcher(
			s3ObjectPath);

		if (!s3ObjectPathMatcher.find()) {
			throw new RuntimeException(
				"Invalid S3 object path: " + s3ObjectPath);
		}

		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
				JenkinsResultsParserUtil.getBuildProperty("mirrors.cache.dir"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		sb.append("/s3/");
		sb.append(s3ObjectPathMatcher.group("bucketName"));
		sb.append("/");
		sb.append(s3ObjectPathMatcher.group("objectPath"));
		sb.append(".s3.ref");

		return new File(sb.toString());
	}

	private static void _validateS3ObjectPath(String s3ObjectPath) {
		Matcher s3ObjectPathMatcher = _s3ObjectPathPattern.matcher(
			s3ObjectPath);

		if (!s3ObjectPathMatcher.find()) {
			throw new RuntimeException(
				"Invalid S3 object path: " + s3ObjectPath);
		}
	}

	private static final Properties _buildProperties;
	private static final Pattern _listS3FilesPattern = Pattern.compile(
		"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} +\\d+ (?<fileName>.+)");
	private static final Pattern _s3ObjectPathPattern = Pattern.compile(
		"s3://(?<bucketName>[^/]+)/(?<objectPath>.+)");
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