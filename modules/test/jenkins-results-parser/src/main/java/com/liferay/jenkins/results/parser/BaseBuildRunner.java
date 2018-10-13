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

import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner<T extends BuildData>
	implements BuildRunner<T> {

	public T getBuildData() {
		return _buildData;
	}

	@Override
	public void run() {
		updateBuildDescription();

		setUpWorkspace();
	}

	@Override
	public void setUp() {
	}

	@Override
	public void tearDown() {
		tearDownWorkspace();
	}

	protected BaseBuildRunner(T buildData) {
		_buildData = buildData;

		_job = JobFactory.newJob(_buildData);

		_job.readJobProperties();
	}

	protected Job getJob() {
		return _job;
	}

	protected abstract void initWorkspace();

	protected void publishToUserContentDir(File file) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		String userContentRelativePath =
			_buildData.getUserContentRelativePath();

		userContentRelativePath = userContentRelativePath.replace(")", "\\)");
		userContentRelativePath = userContentRelativePath.replace("(", "\\(");

		try {
			String command = JenkinsResultsParserUtil.combine(
				"ssh -o NumberOfPasswordPrompts=0 ",
				_buildData.getMasterHostname(),
				" 'mkdir -p /opt/java/jenkins/userContent/",
				userContentRelativePath, "'");

			JenkinsResultsParserUtil.executeBashCommands(command);
		}
		catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}

		int maxRetries = 3;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String filePath = file.getCanonicalPath();

				if (file.isDirectory()) {
					filePath += "/";
				}

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -Ipqrs --chmod=go=rx --timeout=1200 ", filePath,
					" ", _buildData.getTopLevelMasterHostname(),
					"::usercontent/", userContentRelativePath);

				JenkinsResultsParserUtil.executeBashCommands(command);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to send the jenkins-report.html", e);
				}

				System.out.println(
					"Unable to execute bash commands, retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	protected void setUpWorkspace() {
		if (workspace == null) {
			initWorkspace();
		}

		workspace.setUp(getJob());
	}

	protected void tearDownWorkspace() {
		if (workspace == null) {
			initWorkspace();
		}

		workspace.tearDown();
	}

	protected void updateBuildDescription() {
		String buildDescription = _buildData.getBuildDescription();

		buildDescription = buildDescription.replaceAll("\"", "\\\\\"");
		buildDescription = buildDescription.replaceAll("\'", "\\\\\'");

		StringBuilder sb = new StringBuilder();

		sb.append("def job = Jenkins.instance.getItemByFullName(\"");
		sb.append(_buildData.getJobName());
		sb.append("\"); ");

		sb.append("def build = job.getBuildByNumber(");
		sb.append(_buildData.getBuildNumber());
		sb.append("); ");

		sb.append("build.description = \"");
		sb.append(buildDescription);
		sb.append("\";");

		JenkinsResultsParserUtil.executeJenkinsScript(
			_buildData.getMasterHostname(), "script=" + sb.toString());
	}

	protected Workspace workspace;

	private final T _buildData;
	private final Job _job;

}