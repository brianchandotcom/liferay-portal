/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

		build.reset();

		build.setStatus("queued");
	}

	@Override
	public void reset() {
		super.reset();

		_buildCompleted = null;
		_buildFailing = null;
	}

	protected DefaultBuildUpdater(Build build) {
		super(build);
	}

	@Override
	protected boolean isBuildCompleted() {
		if (_buildCompleted != null) {
			return _buildCompleted;
		}

		Build build = getBuild();

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

		if (build instanceof ParentBuild) {
			ParentBuild parentBuild = (ParentBuild)build;

			for (Build downstreamBuild : parentBuild.getDownstreamBuilds()) {
				if (!downstreamBuild.isCompleted()) {
					return false;
				}
			}
		}

		_buildCompleted = true;

		return _buildCompleted;
	}

	@Override
	protected boolean isBuildFailing() {
		if (_buildFailing != null) {
			return _buildFailing;
		}

		Build build = getBuild();

		String result = build.getResult();

		if (result == null) {
			JSONObject buildJSONObject = build.getBuildJSONObject("result");

			if (buildJSONObject == null) {
				return true;
			}

			result = buildJSONObject.optString("result");
		}

		if (!Objects.equals(result, "SUCCESS")) {
			return true;
		}

		_buildFailing = false;

		return _buildFailing;
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

	@Override
	protected boolean isBuildRunning() {
		try {
			JSONObject buildJSONObject = _getBuildJSONObject();

			if (buildJSONObject == null) {
				return false;
			}

			Build build = getBuild();

			build.setBuildURL(buildJSONObject.getString("url"));

			build.saveBuildURLInBuildDatabase();

			Build.Invocation buildInvocation = build.getCurrentInvocation();

			buildInvocation.setQueueId(buildJSONObject.getLong("queueId"));

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

	private JSONObject _getBuildJSONObject() {
		Build build = getBuild();

		Build.Invocation currentInvocation = build.getCurrentInvocation();

		long currentQueueId = currentInvocation.getQueueId();

		JenkinsMaster jenkinsMaster = currentInvocation.getJenkinsMaster();

		List<JSONObject> buildJSONObjects = jenkinsMaster.getBuildJSONObjects(
			build.getJobName());

		for (JSONObject buildJSONObject : buildJSONObjects) {
			if (currentQueueId > 0) {
				if (Objects.equals(
						buildJSONObject.getLong("queueId"), currentQueueId)) {

					return buildJSONObject;
				}

				continue;
			}

			if (_matchesBuildParameters(_getBuildParameters(buildJSONObject))) {
				Build.Invocation previousInvocation =
					build.getPreviousInvocation();

				if ((previousInvocation != null) &&
					Objects.equals(
						previousInvocation.getBuildURL(),
						buildJSONObject.optString("url"))) {

					continue;
				}

				return buildJSONObject;
			}
		}

		return null;
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

			List<JSONObject> queueItemJSONObjects = new ArrayList<>(
				jenkinsMaster.getQueueItemJSONObjects());

			String jenkinsJobName = build.getJobName();

			Build.Invocation currentInvocation = build.getCurrentInvocation();

			long currentQueueId = currentInvocation.getQueueId();

			for (JSONObject queueItemJSONObject : queueItemJSONObjects) {
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
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return null;
	}

	private Build.Invocation _invoke(JenkinsMaster jenkinsMaster) {
		Build build = getBuild();

		try {
			StringBuilder sb = new StringBuilder();

			sb.append(jenkinsMaster.getURL());
			sb.append("/job/");
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
			System.out.println("WARNING: Unable to invoke Jenkins using curl");

			try {
				JSONObject jsonObject =
					JenkinsResultsParserUtil.invokeJenkinsBuild(
						jenkinsMaster, build.getJobName(),
						build.getParameters());

				return new Build.Invocation(
					build, jenkinsMaster, jsonObject.getLong("queueId"));
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
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

	private Boolean _buildCompleted;
	private Boolean _buildFailing;

}