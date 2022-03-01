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
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsConsoleTextLoader;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayAttachmentRecorder {

	public void record() {
		if (_recorded) {
			return;
		}

		JenkinsResultsParserUtil.delete(getRecordedFilesBaseDir());

		try {
			_recordJenkinsConsole();

			if (_build instanceof TopLevelBuild) {
				_recordBuildResult();
				_recordJobSummary();
				_recordJenkinsReport();
			}
			else {
				_recordLiferayLogs();
				_recordLiferayOSGiLogs();
				_recordPoshiReportFiles();
				_recordPoshiWarnings();
			}
		}
		catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(
				"Unable to record attachments for: " + _build.getBuildURL());

			illegalArgumentException.printStackTrace();
		}

		_recorded = true;
	}

	protected TestrayAttachmentRecorder(Build build) {
		if (build == null) {
			throw new RuntimeException("Please set a build");
		}

		_build = build;

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
			_build);

		String jobVariant = _build.getParameterValue("JOB_VARIANT");

		if (jobVariant == null) {
			jobVariant = "";
		}
		else {
			jobVariant += "/";
		}

		_startProperties = buildDatabase.getProperties(
			jobVariant + "start.properties");
	}

	protected File getRecordedFilesBaseDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/recorded_logs");
	}

	protected String getRelativeBuildDirPath() {
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

		if (!(_build instanceof TopLevelBuild)) {
			sb.append(_build.getJobVariant());
			sb.append("/");

			sb.append(
				JenkinsResultsParserUtil.getAxisVariable(_build.getBuildURL()));
		}

		return sb.toString();
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
				JenkinsResultsParserUtil.findSiblingFiles(
					appServerParentDir, true)) {

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

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			return null;
		}

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				portalUpstreamBranchName);

		return _portalGitWorkingDirectory;
	}

	private GitWorkingDirectory _getQAWebsitesGitWorkingDirectory() {
		if (_qaWebsitesGitWorkingDirectory != null) {
			return _qaWebsitesGitWorkingDirectory;
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String upstreamBranchName = "master";

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.repository", upstreamBranchName);

		_qaWebsitesGitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		return _qaWebsitesGitWorkingDirectory;
	}

	private File _getRecordedFilesBuildDir() {
		return new File(getRecordedFilesBaseDir(), getRelativeBuildDirPath());
	}

	private void _recordBuildResult() {
		if (!(_build instanceof TopLevelBuild)) {
			return;
		}

		TopLevelBuild topLevelBuild = (TopLevelBuild)_build;

		JSONObject jsonObject = topLevelBuild.getBuildResultsJSONObject(
			null, null,
			new String[] {
				"buildResults", "buildURL", "duration", "errorDetails", "name",
				"stopWatchRecords", "status"
			});

		File buildResultsJSONObjectFile = new File(
			_getRecordedFilesBuildDir(), "build-result.json");

		try {
			JenkinsResultsParserUtil.write(
				buildResultsJSONObjectFile, jsonObject.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _recordJenkinsConsole() {
		JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
			new JenkinsConsoleTextLoader(_build.getBuildURL());

		File jenkinsConsoleFile = new File(
			_getRecordedFilesBuildDir(), "jenkins-console.txt");

		try {
			JenkinsResultsParserUtil.write(
				jenkinsConsoleFile, jenkinsConsoleTextLoader.getConsoleText());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _recordJenkinsReport() {
		if (!(_build instanceof TopLevelBuild)) {
			return;
		}

		TopLevelBuild topLevelBuild = (TopLevelBuild)_build;

		Element jenkinsReportElement = topLevelBuild.getJenkinsReportElement();

		File jenkinsReportFile = new File(
			_getRecordedFilesBuildDir(), "jenkins-report.html");

		try {
			JenkinsResultsParserUtil.write(
				jenkinsReportFile,
				StringEscapeUtils.unescapeXml(
					Dom4JUtil.format(jenkinsReportElement, true)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _recordJobSummary() {
		if (!(_build instanceof TopLevelBuild)) {
			return;
		}

		TopLevelBuild topLevelBuild = (TopLevelBuild)_build;

		File jobSummaryFile = new File(
			topLevelBuild.getJobSummaryDir(), "index.html");

		if (!jobSummaryFile.exists()) {
			return;
		}

		try {
			JenkinsResultsParserUtil.copy(
				jobSummaryFile,
				new File(
					_getRecordedFilesBuildDir(), "job-summary/index.html"));
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
				_getRecordedFilesBuildDir(),
				JenkinsResultsParserUtil.combine(
					"liferay-log", matcher.group("bundlesSuffix"), ".txt"));

			try {
				JenkinsResultsParserUtil.write(liferayLogFile, sb.toString());
			}
			catch (IOException ioException) {
			}
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

			String liferayOSGiLogFileContent = sb.toString();

			liferayOSGiLogFileContent = liferayOSGiLogFileContent.trim();

			if (liferayOSGiLogFileContent.length() == 0) {
				continue;
			}

			Matcher matcher = _bundlesDirNamePattern.matcher(
				liferayBundlesDir.getName());

			if (!matcher.find()) {
				continue;
			}

			File liferayOSGiLogFile = new File(
				_getRecordedFilesBuildDir(),
				JenkinsResultsParserUtil.combine(
					"liferay-osgi-log", matcher.group("bundlesSuffix"),
					".txt"));

			try {
				JenkinsResultsParserUtil.write(
					liferayOSGiLogFile, liferayOSGiLogFileContent);
			}
			catch (IOException ioException) {
			}
		}
	}

	private void _recordPoshiReportFiles() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		List<File> testResultsDirs = new ArrayList<>();

		if (portalGitWorkingDirectory != null) {
			File testResultsDir = new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"portal-web/test-results");

			if (testResultsDir.exists()) {
				testResultsDirs.add(testResultsDir);
			}
		}

		if (testResultsDirs.isEmpty()) {
			GitWorkingDirectory qaWebsitesGitWorkingDirectory =
				_getQAWebsitesGitWorkingDirectory();

			if (qaWebsitesGitWorkingDirectory != null) {
				testResultsDirs.addAll(
					JenkinsResultsParserUtil.findDirs(
						qaWebsitesGitWorkingDirectory.getWorkingDirectory(),
						"test-results"));
			}
		}

		if (testResultsDirs.isEmpty()) {
			return;
		}

		for (File testResultsDir : testResultsDirs) {
			List<File> poshiReportIndexFiles =
				JenkinsResultsParserUtil.findFiles(
					testResultsDir, "index.html");

			for (File poshiReportIndexFile : poshiReportIndexFiles) {
				File sourcePoshiReportDir =
					poshiReportIndexFile.getParentFile();

				File poshiReportDir = new File(
					_getRecordedFilesBuildDir(),
					sourcePoshiReportDir.getName());

				try {
					JenkinsResultsParserUtil.copy(
						sourcePoshiReportDir, poshiReportDir);
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}

				File indexFile = new File(poshiReportDir, "index.html");

				if (!indexFile.exists()) {
					continue;
				}

				try {
					String content = JenkinsResultsParserUtil.read(indexFile);

					for (File poshiReportJPGFile :
							JenkinsResultsParserUtil.findFiles(
								poshiReportDir, ".*\\.jpg")) {

						String poshiReportJPGFileName =
							poshiReportJPGFile.getName();

						if (content.contains("/" + poshiReportJPGFileName)) {
							continue;
						}

						System.out.println(
							"Removing unreferenced file " + poshiReportJPGFile);

						JenkinsResultsParserUtil.delete(poshiReportJPGFile);
					}
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		}
	}

	private void _recordPoshiWarnings() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		if (portalGitWorkingDirectory == null) {
			return;
		}

		File sourcePoshiWarningsFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"poshi-warnings.xml");

		if (!sourcePoshiWarningsFile.exists()) {
			return;
		}

		File poshiWarningsFile = new File(
			_getRecordedFilesBuildDir(), "poshi-warnings.xml");

		try {
			JenkinsResultsParserUtil.copy(
				sourcePoshiWarningsFile, poshiWarningsFile);

			String content = JenkinsResultsParserUtil.read(poshiWarningsFile);

			if (content.matches("\\s*")) {
				return;
			}

			JenkinsResultsParserUtil.write(
				poshiWarningsFile,
				JenkinsResultsParserUtil.combine(
					"<?xml version=\"1.0\"?>\n<values>\n", content,
					"\n</values>"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Pattern _bundlesDirNamePattern = Pattern.compile(
		"bundles(?<bundlesSuffix>.*)");

	private final Build _build;
	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private GitWorkingDirectory _qaWebsitesGitWorkingDirectory;
	private boolean _recorded;
	private final Properties _startProperties;

}