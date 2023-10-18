/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultBuildUpdater extends BaseBuildUpdater {

	@Override
	public void invoke() {
		Build build = getBuild();

		JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

		if (jenkinsMaster == null) {
			JenkinsCohort jenkinsCohort = build.getJenkinsCohort();

			jenkinsMaster = jenkinsCohort.getMostAvailableJenkinsMaster(
				build.getInvokedBatchSize(), build.getMinimumSlaveRAM(),
				build.getMaximumSlavesPerHost());

			build.setJenkinsMaster(jenkinsMaster);
		}

		JSONObject jsonObject = JenkinsResultsParserUtil.invokeJenkinsBuild(
			jenkinsMaster, build.getJobName(), build.getParameters());

		build.addInvocation(
			new Build.Invocation(
				build, jenkinsMaster, jsonObject.getLong("queueId")));
	}

	@Override
	public void reinvoke() {
		Build build = getBuild();

		JenkinsCohort jenkinsCohort = build.getJenkinsCohort();

		JenkinsMaster jenkinsMaster =
			jenkinsCohort.getMostAvailableJenkinsMaster(
				build.getInvokedBatchSize(), 24,
				build.getMaximumSlavesPerHost());

		build.setJenkinsMaster(jenkinsMaster);

		JSONObject jsonObject = JenkinsResultsParserUtil.invokeJenkinsBuild(
			jenkinsMaster, build.getJobName(), build.getParameters());

		build.addInvocation(
			new Build.Invocation(
				build, jenkinsMaster, jsonObject.getLong("queueId")));
	}

	protected DefaultBuildUpdater(Build build) {
		super(build);
	}

	@Override
	protected boolean isBuildCompleted() {
		Build build = getBuild();

		if (!_isBuildCompleted(build)) {
			return false;
		}

		if (build instanceof ParentBuild) {
			ParentBuild parentBuild = (ParentBuild)build;

			for (Build downstreamBuild : parentBuild.getDownstreamBuilds()) {
				if (!_isBuildCompleted(downstreamBuild)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	protected boolean isBuildFailing() {
		Build build = getBuild();

		JSONObject buildJSONObject = build.getBuildJSONObject("result");

		if (buildJSONObject == null) {
			return false;
		}

		String result = buildJSONObject.optString("result");

		if (!Objects.equals(result, "SUCCESS")) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean isBuildQueued() {
		try {
			JSONObject queueItemJSONObject = _getQueueItemJSONObject();

			if (queueItemJSONObject == null) {
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			Build build = getBuild();

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"[", build.getBuildName(), "] Unable to get queue item"));
		}

		return false;
	}

	protected boolean isBuildRunning() {
		try {
			JSONObject runningBuildJSONObject = _getRunningBuildJSONObject();

			if (runningBuildJSONObject == null) {
				return false;
			}

			Build build = getBuild();

			build.setBuildURL(runningBuildJSONObject.getString("url"));

			return true;
		}
		catch (Exception exception) {
			exception.printStackTrace();

			Build build = getBuild();

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"[", build.getBuildName(), "] Unable to get build item"));
		}

		return false;
	}

	private JSONObject _getQueueItemJSONObject() {
		try {
			Build build = getBuild();

			JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

			if (jenkinsMaster == null) {
				return null;
			}

			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					String.valueOf(jenkinsMaster.getURL()),
					"/queue/api/json?tree=items[id]"),
				false);

			JSONArray queueItemsJSONArray = jsonObject.getJSONArray("items");

			if (queueItemsJSONArray == null) {
				return null;
			}

			Build.Invocation currentInvocation = build.getCurrentInvocation();

			for (int i = 0; i < queueItemsJSONArray.length(); i++) {
				JSONObject queueItemJSONObject =
					queueItemsJSONArray.getJSONObject(i);

				if (Objects.equals(
						queueItemJSONObject.getLong("id"),
						currentInvocation.getQueueId())) {

					return queueItemJSONObject;
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return null;
	}

	private JSONObject _getRunningBuildJSONObject() {
		Build build = getBuild();

		Build.Invocation currentInvocation = build.getCurrentInvocation();

		int page = 0;

		while (true) {
			JSONArray runningBuildsJSONArray = _getRunningBuildsJSONArray(page);

			if (runningBuildsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < runningBuildsJSONArray.length(); i++) {
				JSONObject runningBuildJSONObject =
					runningBuildsJSONArray.getJSONObject(i);

				if (Objects.equals(
						runningBuildJSONObject.getLong("queueId"),
						currentInvocation.getQueueId())) {

					return runningBuildJSONObject;
				}
			}

			page++;
		}

		return null;
	}

	private JSONArray _getRunningBuildsJSONArray(final int page) {
		Retryable<JSONArray> retryable = new Retryable<JSONArray>(
			true, 2, 10, true) {

			@Override
			public JSONArray execute() {
				Build build = getBuild();

				JenkinsMaster jenkinsMaster = build.getJenkinsMaster();

				String url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						String.valueOf(jenkinsMaster.getURL()), "/job/",
						build.getJobName(),
						"/api/json?tree=allBuilds[queueId,url]{",
						String.valueOf(page * 100), ",",
						String.valueOf((page + 1) * 100), "}"));

				try {
					JSONObject jsonObject =
						JenkinsResultsParserUtil.toJSONObject(url, false);

					return jsonObject.getJSONArray("allBuilds");
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

		};

		return retryable.executeWithRetries();
	}

	private boolean _isBuildCompleted(Build build) {
		JSONObject buildJSONObject = build.getBuildJSONObject(
			"duration,result");

		if (buildJSONObject == null) {
			return false;
		}

		long duration = buildJSONObject.optLong("duration");
		String result = buildJSONObject.optString("result");

		if ((duration == 0) || JenkinsResultsParserUtil.isNullOrEmpty(result)) {
			return false;
		}

		return true;
	}

}