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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.BuildDatabaseUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsConsoleTextLoader;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3ObjectImporter {

	public TestrayS3ObjectImporter(Build build) {
		if (build == null) {
			throw new RuntimeException("Please set a build");
		}

		_build = build;

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
			_build);

		_startProperties = buildDatabase.getProperties(
			_build.getJobVariant() + "/start.properties");
	}

	public void record() {
		JenkinsResultsParserUtil.delete(_getTestrayLogsDir());

		_recordJenkinsConsole();
		_recordLiferayLogs();
		_recordLiferayOSGiLogs();
	}

	public void upload() {
		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Objects(_getTestrayLogsDir());
	}

	private File _convertToGzipFile(File file) {
		File gzipFile = new File(file.getParent(), file.getName() + ".gz");

		JenkinsResultsParserUtil.gzip(file, gzipFile);

		JenkinsResultsParserUtil.delete(file);

		return gzipFile;
	}

	private List<File> _getLiferayBundlesDirs() {
		List<File> liferayBundlesDirs = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		if (portalGitWorkingDirectory == null) {
			return liferayBundlesDirs;
		}

		Properties appServerProperties =
			portalGitWorkingDirectory.getAppServerProperties();

		File appServerParentDir = new File(
			JenkinsResultsParserUtil.getProperty(
				appServerProperties, "app.server.parent.dir"));

		for (File siblingFile :
				JenkinsResultsParserUtil.findSiblingFiles(appServerParentDir)) {

			if (!siblingFile.isDirectory()) {
				continue;
			}

			String siblingFileName = siblingFile.getName();

			Matcher matcher = _bundlesDirNamePattern.matcher(siblingFileName);

			if (!matcher.find()) {
				continue;
			}

			liferayBundlesDirs.add(siblingFile);
		}

		return liferayBundlesDirs;
	}

	private int _getLiferayLogMaxSize() {
		int liferayLogMaxSizeInMB = 10;

		try {
			liferayLogMaxSizeInMB = Integer.parseInt(
				JenkinsResultsParserUtil.getBuildProperty(
					"testray.liferay.log.max.size.in.mb"));
		}
		catch (IOException ioException) {
		}

		return liferayLogMaxSizeInMB * 1024 * 1024;
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		if (_portalGitWorkingDirectory != null) {
			return _portalGitWorkingDirectory;
		}

		String portalUpstreamBranchName = _startProperties.getProperty(
			"PORTAL_UPSTREAM_BRANCH_NAME");

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				portalUpstreamBranchName);

		return _portalGitWorkingDirectory;
	}

	private File _getTestrayLogsBuildDir() {
		StringBuilder sb = new StringBuilder();

		Date topLevelStartDate = new Date(
			Long.parseLong(
				_startProperties.getProperty("TOP_LEVEL_START_TIME")));

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				topLevelStartDate, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_MASTER_HOSTNAME"));
		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_JOB_NAME"));
		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_BUILD_NUMBER"));
		sb.append("/");
		sb.append(_build.getJobVariant());
		sb.append("/");

		sb.append(
			JenkinsResultsParserUtil.getAxisVariable(_build.getBuildURL()));

		return new File(_getTestrayLogsDir(), sb.toString());
	}

	private File _getTestrayLogsDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/logs");
	}

	private void _recordJenkinsConsole() {
		JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
			new JenkinsConsoleTextLoader(_build.getBuildURL());

		File jenkinsConsoleFile = new File(
			_getTestrayLogsBuildDir(), "jenkins-console.txt");

		try {
			JenkinsResultsParserUtil.write(
				jenkinsConsoleFile, jenkinsConsoleTextLoader.getConsoleText());

			_convertToGzipFile(jenkinsConsoleFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _recordLiferayLogs() {
		for (File liferayBundlesDir : _getLiferayBundlesDirs()) {
			File liferayLogsDir = new File(liferayBundlesDir, "logs");

			if (!liferayLogsDir.exists()) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			for (File liferayLogFile :
					JenkinsResultsParserUtil.findFiles(
						liferayLogsDir, "liferay\\..*\\.log")) {

				try {
					sb.append(JenkinsResultsParserUtil.read(liferayLogFile));

					sb.append("\n");
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

			int liferayLogMaxSizeInMB = _getLiferayLogMaxSize();

			if (sb.length() > liferayLogMaxSizeInMB) {
				sb.setLength(liferayLogMaxSizeInMB);
			}

			Matcher matcher = _bundlesDirNamePattern.matcher(
				liferayBundlesDir.getName());

			if (!matcher.find()) {
				continue;
			}

			File liferayLogFile = new File(
				_getTestrayLogsBuildDir(),
				JenkinsResultsParserUtil.combine(
					"liferay-log", matcher.group("bundlesSuffix"), ".txt"));

			try {
				JenkinsResultsParserUtil.write(liferayLogFile, sb.toString());
			}
			catch (IOException ioException) {
				continue;
			}

			_convertToGzipFile(liferayLogFile);
		}
	}

	private void _recordLiferayOSGiLogs() {
		for (File liferayBundlesDir : _getLiferayBundlesDirs()) {
			File liferayOSGiStateDir = new File(
				liferayBundlesDir, "osgi/state");

			if (!liferayOSGiStateDir.exists()) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			for (File liferayOSGiLogFile :
					JenkinsResultsParserUtil.findFiles(
						liferayOSGiStateDir.getParentFile(), ".*\\.log")) {

				try {
					sb.append(
						JenkinsResultsParserUtil.read(liferayOSGiLogFile));

					sb.append("\n");
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

			int liferayLogMaxSize = _getLiferayLogMaxSize();

			if (sb.length() > liferayLogMaxSize) {
				sb.setLength(liferayLogMaxSize);
			}

			Matcher matcher = _bundlesDirNamePattern.matcher(
				liferayBundlesDir.getName());

			if (!matcher.find()) {
				continue;
			}

			File liferayOSGiLogFile = new File(
				_getTestrayLogsBuildDir(),
				JenkinsResultsParserUtil.combine(
					"liferay-osgi-log", matcher.group("bundlesSuffix"),
					".txt"));

			try {
				JenkinsResultsParserUtil.write(
					liferayOSGiLogFile, sb.toString());
			}
			catch (IOException ioException) {
				continue;
			}

			_convertToGzipFile(liferayOSGiLogFile);
		}
	}

	private static final Pattern _bundlesDirNamePattern = Pattern.compile(
		"bundles(?<bundlesSuffix>.*)");

	private final Build _build;
	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final Properties _startProperties;

}