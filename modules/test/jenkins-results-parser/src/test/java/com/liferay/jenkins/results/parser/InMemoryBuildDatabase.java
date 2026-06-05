/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class InMemoryBuildDatabase implements BuildDatabase {

	public InMemoryBuildDatabase() {
		_jsonObject.put(
			"builds", new JSONObject()
		).put(
			"jobs", new JSONObject()
		).put(
			"portal_fixpack_releases", new JSONObject()
		).put(
			"portal_hotfix_releases", new JSONObject()
		).put(
			"portal_releases", new JSONObject()
		).put(
			"properties", new JSONObject()
		).put(
			"pull_requests", new JSONObject()
		).put(
			"workspace_git_repositories", new JSONObject()
		).put(
			"workspaces", new JSONObject()
		);
	}

	@Override
	public File getBuildDatabaseFile() {
		return null;
	}

	@Override
	public JSONObject getBuildDataJSONObject(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		if (!buildsJSONObject.has(key)) {
			return new JSONObject();
		}

		return buildsJSONObject.getJSONObject(key);
	}

	@Override
	public JSONObject getBuildDataJSONObject(URL buildURL) {
		String buildURLString = buildURL.toString();

		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		for (Object key : buildsJSONObject.keySet()) {
			JSONObject buildJSONObject = buildsJSONObject.getJSONObject(
				key.toString());

			if (!buildJSONObject.has("build_url")) {
				continue;
			}

			if (buildURLString.equals(buildJSONObject.getString("build_url"))) {
				return buildJSONObject;
			}
		}

		return new JSONObject();
	}

	@Override
	public Job getJob(String key) {
		if (!hasJob(key)) {
			return null;
		}

		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		return JobFactory.newJob(jobsJSONObject.getJSONObject(key));
	}

	@Override
	public List<Job> getJobs() {
		List<Job> jobs = new ArrayList<>();

		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		for (String key : jobsJSONObject.keySet()) {
			JSONObject jobJSONObject = jobsJSONObject.getJSONObject(key);

			if ((jobJSONObject != null) && !jobJSONObject.isEmpty()) {
				jobs.add(JobFactory.newJob(jobJSONObject));
			}
		}

		return jobs;
	}

	@Override
	public JSONObject getJSONObject() {
		return new JSONObject(_jsonObject.toString());
	}

	@Override
	public PortalFixpackRelease getPortalFixpackRelease(String key) {
		if (!hasPortalFixpackRelease(key)) {
			return null;
		}

		JSONObject portalFixpackReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_fixpack_releases");

		return new PortalFixpackRelease(
			portalFixpackReleasesJSONObject.getJSONObject(key));
	}

	@Override
	public List<PortalFixpackRelease> getPortalFixpackReleases() {
		List<PortalFixpackRelease> portalFixpackReleases = new ArrayList<>();

		JSONObject portalFixpackReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_fixpack_releases");

		for (String key : portalFixpackReleasesJSONObject.keySet()) {
			JSONObject portalFixpackReleaseJSONObject =
				portalFixpackReleasesJSONObject.getJSONObject(key);

			if ((portalFixpackReleaseJSONObject != null) &&
				!portalFixpackReleaseJSONObject.isEmpty()) {

				portalFixpackReleases.add(
					new PortalFixpackRelease(portalFixpackReleaseJSONObject));
			}
		}

		return portalFixpackReleases;
	}

	@Override
	public PortalHotfixRelease getPortalHotfixRelease(String key) {
		if (!hasPortalHotfixRelease(key)) {
			return null;
		}

		JSONObject portalHotfixReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_hotfix_releases");

		return new PortalHotfixRelease(
			portalHotfixReleasesJSONObject.getJSONObject(key));
	}

	@Override
	public List<PortalHotfixRelease> getPortalHotfixReleases() {
		List<PortalHotfixRelease> portalHotfixReleases = new ArrayList<>();

		JSONObject portalHotfixReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_hotfix_releases");

		for (String key : portalHotfixReleasesJSONObject.keySet()) {
			JSONObject portalHotfixReleaseJSONObject =
				portalHotfixReleasesJSONObject.getJSONObject(key);

			if ((portalHotfixReleaseJSONObject != null) &&
				!portalHotfixReleaseJSONObject.isEmpty()) {

				portalHotfixReleases.add(
					new PortalHotfixRelease(portalHotfixReleaseJSONObject));
			}
		}

		return portalHotfixReleases;
	}

	@Override
	public PortalRelease getPortalRelease(String key) {
		if (!hasPortalRelease(key)) {
			return null;
		}

		JSONObject portalReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_releases");

		return new PortalRelease(portalReleasesJSONObject.getJSONObject(key));
	}

	@Override
	public List<PortalRelease> getPortalReleases() {
		List<PortalRelease> portalReleases = new ArrayList<>();

		JSONObject portalReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_releases");

		for (String key : portalReleasesJSONObject.keySet()) {
			JSONObject portalReleaseJSONObject =
				portalReleasesJSONObject.getJSONObject(key);

			if ((portalReleaseJSONObject != null) &&
				!portalReleaseJSONObject.isEmpty()) {

				portalReleases.add(new PortalRelease(portalReleaseJSONObject));
			}
		}

		return portalReleases;
	}

	@Override
	public Properties getProperties(String key) {
		return getProperties(key, null);
	}

	@Override
	public Properties getProperties(String key, Pattern pattern) {
		Properties properties = new Properties();

		if (!hasProperties(key)) {
			return properties;
		}

		JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
			"properties");

		JSONArray propertyJSONArray = propertiesJSONObject.getJSONArray(key);

		for (int i = 0; i < propertyJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertyJSONArray.getJSONObject(i);

			String propertyName = propertyJSONObject.getString("name");
			String propertyValue = propertyJSONObject.getString("value");

			if (pattern == null) {
				properties.setProperty(propertyName, propertyValue);

				continue;
			}

			Matcher matcher = pattern.matcher(propertyName);

			if (matcher.matches()) {
				properties.setProperty(propertyName, propertyValue);
			}
		}

		return properties;
	}

	@Override
	public PullRequest getPullRequest(String key) {
		if (!hasPullRequest(key)) {
			throw new RuntimeException(
				"Unable to find pull request for " + key);
		}

		JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
			"pull_requests");

		return PullRequestFactory.newPullRequest(
			pullRequestsJSONObject.getJSONObject(key));
	}

	@Override
	public List<PullRequest> getPullRequests() {
		List<PullRequest> pullRequests = new ArrayList<>();

		JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
			"pull_requests");

		for (String key : pullRequestsJSONObject.keySet()) {
			JSONObject pullRequestJSONObject =
				pullRequestsJSONObject.getJSONObject(key);

			if ((pullRequestJSONObject != null) &&
				!pullRequestJSONObject.isEmpty()) {

				pullRequests.add(
					PullRequestFactory.newPullRequest(pullRequestJSONObject));
			}
		}

		return pullRequests;
	}

	@Override
	public Workspace getWorkspace(String key) {
		if (!hasWorkspace(key)) {
			throw new RuntimeException("Unable to find workspace");
		}

		JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
			"workspaces");

		return WorkspaceFactory.newWorkspace(
			workspacesJSONObject.getJSONObject(key));
	}

	@Override
	public WorkspaceGitRepository getWorkspaceGitRepository(String key) {
		if (!hasWorkspaceGitRepository(key)) {
			throw new RuntimeException(
				"Unable to find workspace repository for " + key);
		}

		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		return GitRepositoryFactory.getWorkspaceGitRepository(
			workspaceGitRepositoriesJSONObject.getJSONObject(key));
	}

	@Override
	public List<Workspace> getWorkspaces() {
		List<Workspace> workspaces = new ArrayList<>();

		JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
			"workspaces");

		for (String key : workspacesJSONObject.keySet()) {
			JSONObject workspaceJSONObject = workspacesJSONObject.getJSONObject(
				key);

			if ((workspaceJSONObject != null) &&
				!workspaceJSONObject.isEmpty()) {

				workspaces.add(
					WorkspaceFactory.newWorkspace(workspaceJSONObject));
			}
		}

		return workspaces;
	}

	@Override
	public boolean hasBuildData(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		return buildsJSONObject.has(key);
	}

	@Override
	public boolean hasJob(String key) {
		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		if (jobsJSONObject.has(key)) {
			JSONObject jobJSONObject = jobsJSONObject.getJSONObject(key);

			if ((jobJSONObject != null) && !jobJSONObject.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasPortalFixpackRelease(String key) {
		JSONObject portalFixpackReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_fixpack_releases");

		return portalFixpackReleasesJSONObject.has(key);
	}

	@Override
	public boolean hasPortalHotfixRelease(String key) {
		JSONObject portalHotfixReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_hotfix_releases");

		return portalHotfixReleasesJSONObject.has(key);
	}

	@Override
	public boolean hasPortalRelease(String key) {
		JSONObject portalReleasesJSONObject = _jsonObject.getJSONObject(
			"portal_releases");

		return portalReleasesJSONObject.has(key);
	}

	@Override
	public boolean hasProperties(String key) {
		JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
			"properties");

		return propertiesJSONObject.has(key);
	}

	@Override
	public boolean hasPullRequest(String key) {
		JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
			"pull_requests");

		return pullRequestsJSONObject.has(key);
	}

	@Override
	public boolean hasWorkspace(String key) {
		JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
			"workspaces");

		return workspacesJSONObject.has(key);
	}

	@Override
	public boolean hasWorkspaceGitRepository(String key) {
		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		return workspaceGitRepositoriesJSONObject.has(key);
	}

	@Override
	public void putBuildData(String key, BuildData buildData) {
		synchronized (_lock) {
			JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

			buildsJSONObject.put(key, buildData.getJSONObject());
		}
	}

	@Override
	public void putJob(String key, Job job) {
		synchronized (_lock) {
			JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

			jobsJSONObject.put(key, job.getJSONObject());
		}
	}

	@Override
	public void putPortalFixpackRelease(
		String key, PortalFixpackRelease portalFixpackRelease) {

		synchronized (_lock) {
			JSONObject portalFixpackReleasesJSONObject =
				_jsonObject.getJSONObject("portal_fixpack_releases");

			portalFixpackReleasesJSONObject.put(
				key, portalFixpackRelease.getJSONObject());
		}
	}

	@Override
	public void putPortalHotfixRelease(
		String key, PortalHotfixRelease portalHotfixRelease) {

		synchronized (_lock) {
			JSONObject portalHotfixReleasesJSONObject =
				_jsonObject.getJSONObject("portal_hotfix_releases");

			portalHotfixReleasesJSONObject.put(
				key, portalHotfixRelease.getJSONObject());
		}
	}

	@Override
	public void putPortalRelease(String key, PortalRelease portalRelease) {
		synchronized (_lock) {
			JSONObject portalReleasesJSONObject = _jsonObject.getJSONObject(
				"portal_releases");

			portalReleasesJSONObject.put(key, portalRelease.getJSONObject());
		}
	}

	@Override
	public void putProperties(String key, File propertiesFile) {
		putProperties(key, propertiesFile, true);
	}

	@Override
	public void putProperties(
		String key, File propertiesFile, boolean writeFile) {

		putProperties(
			key, JenkinsResultsParserUtil.getProperties(propertiesFile),
			writeFile);
	}

	@Override
	public void putProperties(String key, Properties properties) {
		putProperties(key, properties, true);
	}

	@Override
	public void putProperties(
		String key, Properties properties, boolean writeFile) {

		synchronized (_lock) {
			JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
				"properties");

			propertiesJSONObject.put(key, _toJSONArray(properties));
		}
	}

	@Override
	public void putProperty(
		String key, String propertyName, String propertyValue) {

		putProperty(key, propertyName, propertyValue, true);
	}

	@Override
	public synchronized void putProperty(
		String key, String propertyName, String propertyValue,
		boolean writeFile) {

		Properties properties = getProperties(key);

		properties.setProperty(propertyName, propertyValue);

		putProperties(key, properties, writeFile);
	}

	@Override
	public void putPullRequest(String key, PullRequest pullRequest) {
		synchronized (_lock) {
			JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
				"pull_requests");

			pullRequestsJSONObject.put(key, pullRequest.getJSONObject());
		}
	}

	@Override
	public void putWorkspace(String key, Workspace workspace) {
		synchronized (_lock) {
			JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
				"workspaces");

			workspacesJSONObject.put(key, workspace.getJSONObject());
		}
	}

	@Override
	public void putWorkspaceGitRepository(
		String key, WorkspaceGitRepository workspaceGitRepository) {

		synchronized (_lock) {
			JSONObject workspaceGitRepositoriesJSONObject =
				_jsonObject.getJSONObject("workspace_git_repositories");

			workspaceGitRepositoriesJSONObject.put(
				key, workspaceGitRepository.getJSONObject());
		}
	}

	@Override
	public void read() {
		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public FilePropagator rsyncBuildDatabaseFile(
		List<String> distNodes, String distPath, String preDistCommand,
		String postDistCommand, int threadCount) {

		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public void rsyncBuildDatabaseFileToJenkinsMaster(
		String destinationDirPath, JenkinsMaster jenkinsMaster) {

		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	public void setJSONObject(JSONObject jsonObject) {
		synchronized (_lock) {
			_jsonObject = jsonObject;
		}
	}

	@Override
	public void uploadBuildDatabaseFileToCloudBucket() {
		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public void uploadBuildDatabaseFileToCloudBucket(String path) {
		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public void write() {
		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public void writeFilteredPropertiesToFile(
		String destFilePath, Pattern pattern, String key) {

		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	@Override
	public void writePropertiesToFile(String destFilePath, String key) {
		throw new UnsupportedOperationException(
			"InMemoryBuildDatabase has no backing file");
	}

	private JSONArray _toJSONArray(Properties properties) {
		JSONArray jsonArray = new JSONArray();

		int i = 0;

		for (String propertyName : properties.stringPropertyNames()) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"name", propertyName
			).put(
				"value", properties.getProperty(propertyName)
			);

			jsonArray.put(i, jsonObject);

			i++;
		}

		return jsonArray;
	}

	private JSONObject _jsonObject = new JSONObject();
	private final Object _lock = new Object();

}