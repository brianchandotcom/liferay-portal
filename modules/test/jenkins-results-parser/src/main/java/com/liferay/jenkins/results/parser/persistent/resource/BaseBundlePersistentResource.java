/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.BuildFactory;
import com.liferay.jenkins.results.parser.JenkinsAPIUtil;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.SubrepositoryWorkspace;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.Workspace;
import com.liferay.jenkins.results.parser.WorkspaceGitRepository;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBundlePersistentResource
	extends BasePersistentResource {

	@Override
	public String getBaseS3ObjectPath() {
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
				JenkinsResultsParserUtil.getBuildProperty(
					"cloud.ci.s3.bucket.persistent.resources.archives.path"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		WorkspaceGitRepository bundleWorkspaceGitRepository =
			getBundleWorkspaceGitRepository();

		sb.append("/");
		sb.append(getType());
		sb.append("/");
		sb.append(bundleWorkspaceGitRepository.getName());
		sb.append("/");
		sb.append(bundleWorkspaceGitRepository.getBaseBranchSHA());
		sb.append("/");
		sb.append(bundleWorkspaceGitRepository.getSenderBranchSHA());

		return sb.toString();
	}

	protected BaseBundlePersistentResource(
		BuildDatabase buildDatabase, TopLevelBuild topLevelBuild) {

		super(buildDatabase);

		_topLevelBuild = topLevelBuild;
	}

	@Override
	protected Set<String> getArtifactNames() {
		Set<String> artifactNames = new HashSet<>();

		try {
			String artifactNamesString =
				JenkinsResultsParserUtil.getBuildProperty(
					"persistent.resource.artifact.names[" + getType() + "]",
					getUpstreamBranchName());

			if (JenkinsResultsParserUtil.isNullOrEmpty(artifactNamesString)) {
				return artifactNames;
			}

			Collections.addAll(artifactNames, artifactNamesString.split(","));
		}
		catch (IOException ioException) {
		}

		return artifactNames;
	}

	protected abstract WorkspaceGitRepository getBundleWorkspaceGitRepository();

	@Override
	protected String getCurrentTopLevelBuildURL() {
		if (_topLevelBuild != null) {
			return _topLevelBuild.getBuildURL();
		}

		return super.getCurrentTopLevelBuildURL();
	}

	@Override
	protected String getStatusMessage() {
		Status status = getStatus();

		if (status == Status.FAILED) {
			return "Failed to build bundles at " + getProducerBuildURL();
		}
		else if (status == Status.IN_PROGRESS) {
			if (isController()) {
				return "Building bundles at " + getProducerBuildURL();
			}

			return "Waiting for bundles at " + getProducerBuildURL();
		}
		else if (status == Status.IN_QUEUE) {
			return "In queue at " + _getProducerJobURL();
		}
		else if (status == Status.SUCCESS) {
			return "Completed bundles at " + getProducerBuildURL();
		}

		return "Not started";
	}

	protected String getUpstreamBranchName() {
		WorkspaceGitRepository workspaceGitRepository =
			getBundleWorkspaceGitRepository();

		return workspaceGitRepository.getUpstreamBranchName();
	}

	protected synchronized Workspace getWorkspace() {
		if (_workspace != null) {
			return _workspace;
		}

		String primaryGitDirectoryName = getStartProperty(
			"PRIMARY_GIT_DIRECTORY_NAME");

		BuildDatabase buildDatabase = getBuildDatabase();

		if (!buildDatabase.hasWorkspace(primaryGitDirectoryName)) {
			return null;
		}

		_workspace = buildDatabase.getWorkspace(primaryGitDirectoryName);

		if (_workspace instanceof SubrepositoryWorkspace) {
			String portalUpstreamBranchName = getStartProperty(
				"PORTAL_UPSTREAM_BRANCH_NAME");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					portalUpstreamBranchName)) {

				SubrepositoryWorkspace subrepositoryWorkspace =
					(SubrepositoryWorkspace)_workspace;

				subrepositoryWorkspace.setPortalUpstreamBranchName(
					portalUpstreamBranchName);
			}
		}

		return _workspace;
	}

	protected void start() {
		StringBuilder sb = new StringBuilder();

		sb.append(_JOB_VARIANT);
		sb.append("/start.properties");

		String key = sb.toString();

		Properties startProperties = getStartProperties();

		BuildDatabase buildDatabase = getBuildDatabase();

		buildDatabase.putProperties(key, startProperties, true);

		buildDatabase.uploadBuildDatabaseFileToCloudBucket();

		Map<String, String> buildParameters = new HashMap<>();

		for (String startPropertyName : startProperties.stringPropertyNames()) {
			String startPropertyValue = JenkinsResultsParserUtil.getProperty(
				startProperties, startPropertyName);

			if (JenkinsResultsParserUtil.isNullOrEmpty(startPropertyValue)) {
				continue;
			}

			buildParameters.put(startPropertyName, startPropertyValue);
		}

		buildParameters.put("AXIS_VARIABLE", _getAxisVariable());
		buildParameters.put("BUILD_PRIORITY", _BUILD_PRIORITY);
		buildParameters.put("JOB_VARIANT", _JOB_VARIANT);
		buildParameters.put("SLAVE_LABEL", _SLAVE_LABEL);

		JenkinsMaster producerJenkinsMaster =
			JenkinsResultsParserUtil.getMostAvailableJenkinsMaster(
				_getBaseInvocationURL(), 1, _SLAVE_LABEL);

		long producerQueueId = JenkinsResultsParserUtil.invokeJenkinsBuild(
			producerJenkinsMaster, _JOB_NAME, buildParameters);

		setControllerBuildURL(getCurrentTopLevelBuildURL());
		setProducerJenkinsMaster(producerJenkinsMaster);
		setProducerQueueId(producerQueueId);

		setStatus(Status.IN_QUEUE);

		save();

		print("Start building bundles at " + _getProducerJobURL());
	}

	@Override
	protected void update() {
		JSONObject dataJSONObject = getDataJSONObject();

		if (dataJSONObject == null) {
			start();

			return;
		}

		if (!isController()) {
			setControllerBuildURL(
				dataJSONObject.optString("controller_build_url"));

			String producerBuildURL = dataJSONObject.optString(
				"producer_build_url");

			setProducerBuildURL(producerBuildURL);

			if (JenkinsResultsParserUtil.isURL(producerBuildURL)) {
				_updateBuild(producerBuildURL);
			}

			setProducerJenkinsMaster(null);

			String producerJenkinsMasterName = dataJSONObject.optString(
				"producer_jenkins_master");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					producerJenkinsMasterName)) {

				setProducerJenkinsMaster(
					JenkinsMaster.getInstance(producerJenkinsMasterName));
			}

			setProducerQueueId(dataJSONObject.optLong("producer_queue_id"));
			setStatus(Status.valueOf(dataJSONObject.getString("status")));

			if (isMissing()) {
				_missingCount++;

				if (_missingCount >= _MAX_MISSING_COUNT) {
					start();

					_missingCount = 0;

					return;
				}
			}

			return;
		}

		Status status = getStatus();

		if (status == Status.IN_QUEUE) {
			JenkinsMaster producerJenkinsMaster = getProducerJenkinsMaster();

			long producerQueueId = getProducerQueueId();

			for (JenkinsMaster.QueueItem queueItem :
					producerJenkinsMaster.getQueueItems()) {

				if (queueItem.getId() == producerQueueId) {
					return;
				}
			}

			String producerBuildURL = JenkinsResultsParserUtil.getBuildURL(
				_JOB_NAME, producerJenkinsMaster, producerQueueId);

			if (JenkinsResultsParserUtil.isURL(producerBuildURL)) {
				setStatus(Status.IN_PROGRESS);

				setProducerBuildURL(producerBuildURL);
				setProducerJenkinsMaster(producerJenkinsMaster);
				setProducerQueueId(producerQueueId);

				_updateBuild(producerBuildURL);

				save();

				return;
			}

			print("WARNING: Unable to find queue item");

			return;
		}

		String producerBuildURL = getProducerBuildURL();

		if (!JenkinsResultsParserUtil.isURL(producerBuildURL)) {
			print("WARNING: Unable to find producer build url");

			return;
		}

		if (status == Status.IN_PROGRESS) {
			JSONObject apiJSONObject = JenkinsAPIUtil.getAPIJSONObject(
				producerBuildURL);

			String result = apiJSONObject.optString("result");

			if (JenkinsResultsParserUtil.isNullOrEmpty(result)) {
				return;
			}

			_updateBuild(producerBuildURL);

			if (Objects.equals(result, "SUCCESS")) {
				setStatus(Status.SUCCESS);
			}
			else {
				_failCount++;

				if (_failCount <= _MAX_FAIL_COUNT) {
					print(
						"Retry " + _failCount + " of " + _MAX_FAIL_COUNT +
							" due to FAILURE in " + getProducerBuildURL());

					start();

					return;
				}

				setStatus(Status.FAILED);
			}

			save();
		}
	}

	private String _getAxisVariable() {
		return String.valueOf(getType());
	}

	private String _getBaseInvocationURL() {
		try {
			String serverType = "production";

			String topLevelBuildURL = getCurrentTopLevelBuildURL();

			if (topLevelBuildURL.contains("test-5")) {
				serverType = "staging";
			}

			return JenkinsResultsParserUtil.getBuildProperty(
				"github.webhook.base.invocation.url", serverType);
		}
		catch (IOException ioException) {
			return _BASE_INVOCATION_URL;
		}
	}

	private String _getProducerJobURL() {
		JenkinsMaster producerJenkinsMaster = getProducerJenkinsMaster();

		if (producerJenkinsMaster == null) {
			return null;
		}

		return producerJenkinsMaster.getRemoteURL() + "job/" + _JOB_NAME;
	}

	private void _updateBuild(String producerBuildURL) {
		if (!JenkinsResultsParserUtil.isURL(producerBuildURL) ||
			(_topLevelBuild == null)) {

			return;
		}

		if (_build == null) {
			_build = BuildFactory.newBuild(producerBuildURL, _topLevelBuild);

			_topLevelBuild.addDownstreamBuild(_build);
		}
		else if (Objects.equals(producerBuildURL, _build.getBuildURL())) {
			return;
		}

		_build.setBuildURL(producerBuildURL);

		_build.saveBuildURLInBuildDatabase();

		_topLevelBuild.update();

		BuildDatabase buildDatabase = getBuildDatabase();

		buildDatabase.uploadBuildDatabaseFileToCloudBucket();

		buildDatabase.rsyncBuildDatabaseFileToJenkinsMaster(
			JenkinsResultsParserUtil.combine(
				System.getenv("JENKINS_HOME"), "/userContent/jobs/",
				getStartProperty("TOP_LEVEL_JOB_NAME"), "/builds/",
				getStartProperty("TOP_LEVEL_BUILD_NUMBER")),
			JenkinsMaster.getInstance(
				getStartProperty("TOP_LEVEL_MASTER_HOSTNAME")));
	}

	private static final String _BASE_INVOCATION_URL =
		"http://test-1.liferay.com";

	private static final String _BUILD_PRIORITY = "2";

	private static final String _JOB_NAME = "app-server-bundle-builder";

	private static final String _JOB_VARIANT = "app-server-bundle-builder";

	private static final int _MAX_FAIL_COUNT = 2;

	private static final int _MAX_MISSING_COUNT = 2;

	private static final String _SLAVE_LABEL = "slave-bundle-builder";

	private Build _build;
	private int _failCount;
	private int _missingCount;
	private final TopLevelBuild _topLevelBuild;
	private Workspace _workspace;

}