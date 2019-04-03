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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner<T extends BuildData, S extends Workspace>
	implements BuildRunner<T, S> {

	@Override
	public T getBuildData() {
		return _buildData;
	}

	@Override
	public S getWorkspace() {
		return _workspace;
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
		cleanUpHostServices();

		tearDownWorkspace();
	}

	protected BaseBuildRunner(T buildData) {
		_buildData = buildData;

		_job = JobFactory.newJob(_buildData);

		_job.readJobProperties();
	}

	protected void cleanUpHostServices() {
		Host host = _buildData.getHost();

		host.cleanUpServices();
	}

	protected Job getJob() {
		return _job;
	}

	protected List<JSONObject> getPreviousBuildJSONObjects() {
		if (_previousBuildJSONObjects != null) {
			return _previousBuildJSONObjects;
		}

		_previousBuildJSONObjects = new ArrayList<>();

		BuildData buildData = getBuildData();

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(buildData.getJobURL()) +
					"api/json");

			JSONArray buildsJSONArray = jsonObject.getJSONArray("builds");

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

				_previousBuildJSONObjects.add(
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							buildJSONObject.getString("url") + "api/json")));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return _previousBuildJSONObjects;
	}

	protected abstract void initWorkspace();

	protected void keepJenkinsBuild(boolean keepLogs) {
		JenkinsResultsParserUtil.keepJenkinsBuild(
			keepLogs, _buildData.getBuildNumber(), _buildData.getJobName(),
			_buildData.getMasterHostname());
	}

	protected void publishToUserContentDir(File file) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		String userContentRelativePath =
			_buildData.getUserContentRelativePath();

		userContentRelativePath = userContentRelativePath.replace(")", "\\)");
		userContentRelativePath = userContentRelativePath.replace("(", "\\(");

		RemoteExecutor remoteExecutor = new RemoteExecutor();

		int returnCode = remoteExecutor.execute(
			1, new String[] {_buildData.getMasterHostname()},
			new String[] {
				"mkdir -p /opt/java/jenkins/userContent/" +
					userContentRelativePath
			});

		if (returnCode != 0) {
			throw new RuntimeException("Unable to create target directory");
		}

		int maxRetries = 3;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -Ipqrs --chmod=go=rx --timeout=1200 ",
					JenkinsResultsParserUtil.getCanonicalPath(file), " ",
					_buildData.getTopLevelMasterHostname(), "::usercontent/",
					userContentRelativePath);

				JenkinsResultsParserUtil.executeBashCommands(command);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to send " + file.getName(), e);
				}

				System.out.println(
					"Unable to execute bash commands, retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	protected void retirePreviousBuilds() {
		long allowedBuildAge = 7 * _MILLISECONDS_PER_DAY;

		String allowedBuildAgeInDays = System.getenv(
			"ALLOWED_BUILD_AGE_IN_DAYS");

		if ((allowedBuildAgeInDays != null) &&
			!allowedBuildAgeInDays.isEmpty()) {

			allowedBuildAge =
				Integer.parseInt(allowedBuildAgeInDays) * _MILLISECONDS_PER_DAY;
		}

		long allowedBuildStartTime =
			System.currentTimeMillis() - allowedBuildAge;

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			long previousBuildStartTime = previousBuildJSONObject.getLong(
				"timestamp");

			if (previousBuildStartTime > allowedBuildStartTime) {
				continue;
			}

			try {
				JSONObject injectedEnvVarsJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							previousBuildJSONObject.getString("url") +
								"/injectedEnvVars/api/json"));

				JSONObject envMapJSONObject =
					injectedEnvVarsJSONObject.getJSONObject("envMap");

				JenkinsResultsParserUtil.keepJenkinsBuild(
					false,
					Integer.valueOf(envMapJSONObject.getString("BUILD_NUMBER")),
					envMapJSONObject.getString("JOB_NAME"),
					envMapJSONObject.getString("HOSTNAME"));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	protected void setUpWorkspace() {
		if (_workspace == null) {
			initWorkspace();
		}

		_workspace.setBuildData(getBuildData());

		_workspace.setJob(getJob());

		_workspace.setUp();
	}

	protected void setWorkspace(S workspace) {
		_workspace = workspace;
	}

	protected void tearDownWorkspace() {
		if (_workspace == null) {
			initWorkspace();
		}

		_workspace.tearDown();
	}

	protected void updateBuildDescription() {
		JenkinsResultsParserUtil.updateBuildDescription(
			_buildData.getBuildDescription(), _buildData.getBuildNumber(),
			_buildData.getJobName(), _buildData.getMasterHostname());
	}

	private static final long _MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

	private final T _buildData;
	private final Job _job;
	private List<JSONObject> _previousBuildJSONObjects;
	private S _workspace;

}