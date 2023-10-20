/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
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

		build.addInvocation(_invoke(jenkinsMaster));
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

		build.addInvocation(_invoke(jenkinsMaster));
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

			Build build = getBuild();

			Build.Invocation buildInvocation = build.getCurrentInvocation();

			buildInvocation.setQueueId(queueItemJSONObject.getLong("id"));

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

			Build.Invocation buildInvocation = build.getCurrentInvocation();

			buildInvocation.setQueueId(
				runningBuildJSONObject.getLong("queueId"));

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

	private Map<String, String> _getBuildParameters(JSONObject jsonObject) {
		Map<String, String> buildParameters = new HashMap<>();

		if (!jsonObject.has("actions")) {
			return buildParameters;
		}

		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		if (actionsJSONArray.length() <= 0) {
			return buildParameters;
		}

		JSONArray parametersJSONArray = null;

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

			if (!actionJSONObject.has("parameters")) {
				continue;
			}

			parametersJSONArray = actionJSONObject.getJSONArray("parameters");
		}

		if ((parametersJSONArray == null) || parametersJSONArray.isEmpty()) {
			return buildParameters;
		}

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			JSONObject parameterJSONObject = parametersJSONArray.getJSONObject(
				i);

			buildParameters.put(
				parameterJSONObject.getString("name"),
				parameterJSONObject.getString("value"));
		}

		return buildParameters;
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
					String.valueOf(jenkinsMaster.getURL()), "/queue/api/json?",
					"tree=items[actions[parameters[name,value]],id,task[url]]"),
				false);

			JSONArray queueItemsJSONArray = jsonObject.getJSONArray("items");

			if (queueItemsJSONArray == null) {
				return null;
			}

			String jenkinsJobName = build.getJobName();

			Build.Invocation currentInvocation = build.getCurrentInvocation();

			long currentQueueId = currentInvocation.getQueueId();

			for (int i = 0; i < queueItemsJSONArray.length(); i++) {
				JSONObject queueItemJSONObject =
					queueItemsJSONArray.getJSONObject(i);

				if (currentQueueId > 0) {
					if (Objects.equals(
							queueItemJSONObject.getLong("id"),
							currentQueueId)) {

						return queueItemJSONObject;
					}

					continue;
				}

				JSONObject taskJSONObject = queueItemJSONObject.getJSONObject(
					"task");

				String taskURL = taskJSONObject.getString("url");

				if (!taskURL.contains("/job/" + jenkinsJobName)) {
					continue;
				}

				if (_matchesBuildParameters(
						_getBuildParameters(queueItemJSONObject))) {

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

		long currentQueueId = currentInvocation.getQueueId();

		int page = 0;

		while (true) {
			JSONArray runningBuildsJSONArray = _getRunningBuildsJSONArray(page);

			if (runningBuildsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < runningBuildsJSONArray.length(); i++) {
				JSONObject runningBuildJSONObject =
					runningBuildsJSONArray.getJSONObject(i);

				if (currentQueueId > 0) {
					if (Objects.equals(
							runningBuildJSONObject.getLong("queueId"),
							currentQueueId)) {

						return runningBuildJSONObject;
					}

					continue;
				}

				if (_matchesBuildParameters(
						_getBuildParameters(runningBuildJSONObject))) {

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
						build.getJobName(), "/api/json?tree=allBuilds[",
						"actions[parameters[name,value]],queueId,url]{",
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

	private Build.Invocation _invoke(JenkinsMaster jenkinsMaster) {
		Build build = getBuild();

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.invokeJenkinsBuild(
				jenkinsMaster, build.getJobName(), build.getParameters());

			return new Build.Invocation(
				build, jenkinsMaster, jsonObject.getLong("queueId"));
		}
		catch (Exception exception) {
			System.out.println(
				"WARNING: Unable to invoke jenkins using groovy");

			try {
				StringBuilder sb = new StringBuilder();

				sb.append(jenkinsMaster.getURL());
				sb.append("job/");
				sb.append(build.getJobName());
				sb.append("/buildWithParameters?token=");
				sb.append(
					JenkinsResultsParserUtil.getBuildProperty(
						"jenkins.authentication.token"));

				Map<String, String> buildParameters = new HashMap<>(
					build.getParameters());

				for (Map.Entry<String, String> buildParameter :
						buildParameters.entrySet()) {

					String buildParameterName = buildParameter.getKey();

					if (!buildParameterName.matches("[A-Z0-9_]+")) {
						continue;
					}

					sb.append("&");
					sb.append(buildParameterName);
					sb.append("=");
					sb.append(buildParameter.getValue());
				}

				JenkinsResultsParserUtil.toString(sb.toString());

				return new Build.Invocation(build, jenkinsMaster);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
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

	private boolean _matchesBuildParameters(
		Map<String, String> buildParameters) {

		Build build = getBuild();

		for (Map.Entry<String, String> buildParameter :
				buildParameters.entrySet()) {

			String parameterValue = build.getParameterValue(
				buildParameter.getKey());

			if (JenkinsResultsParserUtil.isNullOrEmpty(parameterValue) ||
				Objects.equals(buildParameter.getValue(), parameterValue)) {

				continue;
			}

			return false;
		}

		return true;
	}

}