/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RemoteExecutor;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class RsyncTestrayAttachmentUploader
	extends BaseTestrayAttachmentUploader {

	@Override
	public File getPreparedFilesBaseDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/prepared_rsync_logs");
	}

	@Override
	public URL getTestrayServerLogsURL() {
		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(getTestrayServerURL()),
					"/reports/production/logs"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
	public void upload() {
		if (_uploaded) {
			return;
		}

		prepareFiles();

		_rsyncDirs();

		_rsyncFiles();

		_uploaded = true;
	}

	protected RsyncTestrayAttachmentUploader(
		Build build, URL testrayServerURL) {

		super(build, testrayServerURL);
	}

	private String _getMasterHostname() {
		Build build = getBuild();

		JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

		return jenkinsMaster.getName();
	}

	private String _getTestrayMountDirPath() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				"testray.server.mount.dir[testray-1]");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _rsyncDirs() {
		RemoteExecutor remoteExecutor = new RemoteExecutor();

		List<String> commands = new ArrayList<>();

		List<File> preparedParentDirs = new ArrayList<>();

		for (File preparedFile : getPreparedFiles()) {
			File preparedParentDir = preparedFile.getParentFile();

			if ((preparedParentDir == null) ||
				!preparedParentDir.isDirectory() ||
				preparedParentDirs.contains(preparedParentDir)) {

				continue;
			}

			preparedParentDirs.add(preparedParentDir);

			commands.add(
				JenkinsResultsParserUtil.combine(
					"mkdir -p \"", _getTestrayMountDirPath(),
					"/jenkins/testray-results/production/logs/",
					JenkinsResultsParserUtil.getPathRelativeTo(
						preparedParentDir, getPreparedFilesBaseDir()),
					"\""));
		}

		remoteExecutor.execute(
			1, new String[] {"root@" + _getMasterHostname()},
			commands.toArray(new String[0]));
	}

	private void _rsyncFiles() {
		_rsyncDirs();

		String[] commands = null;

		if (JenkinsResultsParserUtil.isWindows()) {
			commands = new String[2];

			commands[0] = JenkinsResultsParserUtil.combine(
				"cd ",
				JenkinsResultsParserUtil.getCanonicalPath(
					getPreparedFilesBaseDir()));
			commands[1] = JenkinsResultsParserUtil.combine(
				"rsync -aqz --chmod=go=rx ./* \"root@", _getMasterHostname(),
				"::testray-results/production/logs/\"");
		}
		else {
			commands = new String[1];

			commands[0] = JenkinsResultsParserUtil.combine(
				"rsync -aqz --chmod=go=rx \"",
				JenkinsResultsParserUtil.getCanonicalPath(
					getPreparedFilesBaseDir()),
				"\"/* \"", _getMasterHostname(),
				"::testray-results/production/logs/\"");
		}

		try {
			JenkinsResultsParserUtil.executeBashCommands(commands);
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		for (File preparedFile : getPreparedFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ", String.valueOf(getTestrayServerLogsURL()), "/",
					JenkinsResultsParserUtil.getPathRelativeTo(
						preparedFile, getPreparedFilesBaseDir())));
		}
	}

	private boolean _uploaded;

}