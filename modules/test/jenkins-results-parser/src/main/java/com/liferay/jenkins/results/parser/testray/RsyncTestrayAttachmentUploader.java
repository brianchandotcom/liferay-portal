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
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RemoteExecutor;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public class RsyncTestrayAttachmentUploader
	extends BaseTestrayAttachmentUploader {

	public RsyncTestrayAttachmentUploader(Build build, URL testrayServerURL) {
		super(build);

		_testrayServerURL = testrayServerURL;
	}

	@Override
	public void upload() {
		if (_uploaded) {
			return;
		}

		TestrayAttachmentRecorder testrayAttachmentRecorder =
			getTestrayAttachmentRecorder();

		testrayAttachmentRecorder.record();

		makeRemoteDestDirs();

		rsync();

		_uploaded = true;
	}

	protected void makeRemoteDestDirs() {
		RemoteExecutor remoteExecutor = new RemoteExecutor();

		List<String> commands = new ArrayList<>();

		List<File> sourceTestrayLogsParentDirs = new ArrayList<>();

		for (File sourceTestrayLogsFile : _getSourceTestrayLogsFiles()) {
			File sourceTestrayLogsParentDir =
				sourceTestrayLogsFile.getParentFile();

			if ((sourceTestrayLogsParentDir == null) ||
				!sourceTestrayLogsParentDir.isDirectory()) {

				continue;
			}

			if (sourceTestrayLogsParentDirs.contains(
					sourceTestrayLogsParentDir)) {

				continue;
			}

			sourceTestrayLogsParentDirs.add(sourceTestrayLogsParentDir);

			commands.add(
				JenkinsResultsParserUtil.combine(
					"mkdir -p \"", _getTestrayMountDirPath(),
					"/jenkins/testray-results/production/logs/",
					JenkinsResultsParserUtil.getPathRelativeTo(
						sourceTestrayLogsParentDir, _getSourceTestrayLogsDir()),
					"\""));
		}

		remoteExecutor.execute(
			1, new String[] {_getMasterHostname()},
			commands.toArray(new String[0]));
	}

	protected void rsync() {
		String command = JenkinsResultsParserUtil.combine(
			"rsync -aqz --chmod=go=rx \"",
			JenkinsResultsParserUtil.getCanonicalPath(
				_getSourceTestrayLogsDir()),
			"\"/* \"", _getMasterHostname(),
			"::testray-results/production/logs/\"");

		try {
			JenkinsResultsParserUtil.executeBashCommands(command);
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}

		for (File sourceTestrayLogsFile : _getSourceTestrayLogsFiles()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Uploaded ", String.valueOf(_getTestrayServerURL()),
					"/reports/production/logs/",
					JenkinsResultsParserUtil.getPathRelativeTo(
						sourceTestrayLogsFile, _getSourceTestrayLogsDir())));
		}
	}

	private String _getMasterHostname() {
		Build build = getBuild();

		JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

		return jenkinsMaster.getName();
	}

	private File _getSourceTestrayLogsDir() {
		TestrayAttachmentRecorder testrayAttachmentRecorder =
			getTestrayAttachmentRecorder();

		return testrayAttachmentRecorder.getTestrayLogsDir();
	}

	private List<File> _getSourceTestrayLogsFiles() {
		if (_sourceTestrayLogsFiles != null) {
			return _sourceTestrayLogsFiles;
		}

		_sourceTestrayLogsFiles = JenkinsResultsParserUtil.findFiles(
			_getSourceTestrayLogsDir(), ".*");

		return _sourceTestrayLogsFiles;
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

	private URL _getTestrayServerURL() {
		return _testrayServerURL;
	}

	private List<File> _sourceTestrayLogsFiles;
	private final URL _testrayServerURL;
	private boolean _uploaded;

}