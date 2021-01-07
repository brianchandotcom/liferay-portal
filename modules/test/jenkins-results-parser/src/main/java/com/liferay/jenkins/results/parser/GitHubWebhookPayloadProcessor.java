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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 */
public class GitHubWebhookPayloadProcessor {

	public GitHubWebhookPayloadProcessor(String payloadJSONSource) {
		_payloadJSONObject = new PayloadJSONObject(payloadJSONSource);

		Class<?> clazz = getClass();

		try {
			_jenkinsProperties.load(
				clazz.getResourceAsStream("/jenkins.properties"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load jenkins.properties", ioException);
		}

		JenkinsResultsParserUtil.setBuildProperties(
			_URLS_JENKINS_BUILD_PROPERTIES);
	}

	public void addTestPullRequestQueryString(String queryString) {
		_testPullRequestQueryStrings.put(
			queryString, System.currentTimeMillis());
	}

	public void addTestPullRequestURL(String url) {
		_testPullRequestURLs.put(url, System.currentTimeMillis());
	}

	public String getCIJobName(
		PullRequestTesterParameters pullRequestTesterParameters) {

		if (_ciForwardEligible) {
			return "forward-pullrequest";
		}

		String ciReevaluateBuildId =
			pullRequestTesterParameters.getCiReevaluateBuildId();

		if (ciReevaluateBuildId != null) {
			return "test-portal-evaluate-pullrequest";
		}

		String ciTestSuiteName =
			pullRequestTesterParameters.getCiTestSuiteName();

		if (ciTestSuiteName.equals("sf")) {
			return "test-portal-source-format";
		}

		PullRequest pullRequest = pullRequestTesterParameters.getPullRequest();

		String repositoryName = pullRequest.getGitRepositoryName();

		if (repositoryName.equals("liferay-jenkins-ee")) {
			return "test-jenkins-acceptance-pullrequest";
		}

		StringBuilder sb = new StringBuilder();

		if (repositoryName.startsWith("com-liferay-")) {
			sb.append("test-subrepository-acceptance-pullrequest");
		}
		else if (repositoryName.startsWith("liferay-plugins")) {
			sb.append("test-plugins-acceptance-pullrequest");
		}
		else if (repositoryName.startsWith("liferay-portal")) {
			sb.append("test-portal-acceptance-pullrequest");
		}

		sb.append("(");

		String upstreamBranchName = pullRequest.getUpstreamBranchName();

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			pullRequest.getGitHubRemoteGitRepository();

		if (gitHubRemoteGitRepository.isSubrepository() &&
			(upstreamBranchName != null)) {

			sb.append(upstreamBranchName);
		}
		else {
			sb.append(pullRequest.getSenderBranchName());
		}

		sb.append(")");

		return sb.toString();
	}

	public List<String> getCITestAutoTestSuiteNames(PullRequest pullRequest) {
		String ciTestAutoRecipientsProperty =
			JenkinsResultsParserUtil.getCIProperty(
				pullRequest.getUpstreamBranchName(), "ci.test.auto.recipients",
				pullRequest.getGitHubRemoteGitRepositoryName());

		if ((ciTestAutoRecipientsProperty == null) ||
			ciTestAutoRecipientsProperty.isEmpty()) {

			return Collections.emptyList();
		}

		Pattern pattern = Pattern.compile(
			pullRequest.getOwnerUsername() + "\\[(?<testSuiteNames>[^\\]]+)]");

		for (String ciTestAutoRecipient :
				ciTestAutoRecipientsProperty.split("\\s*,\\s*")) {

			Matcher matcher = pattern.matcher(ciTestAutoRecipient);

			if (!matcher.matches()) {
				continue;
			}

			String ciTestAutoTestSuiteNames = matcher.group("testSuiteNames");

			return Arrays.asList(ciTestAutoTestSuiteNames.split(":"));
		}

		return Collections.emptyList();
	}

	public Set<String> getPassingTestSuites() throws JSONException {
		if ((_passingTestSuites != null) && !_passingTestSuites.isEmpty()) {
			return _passingTestSuites;
		}

		_passingTestSuites = new HashSet<>();

		PullRequest pullRequest = _payloadJSONObject.getPullRequest();

		for (String statusDescription : pullRequest.getStatusDescriptions()) {
			Matcher matcher = _passingTestSuiteStatusDescriptionPattern.matcher(
				statusDescription);

			if (matcher.matches()) {
				_passingTestSuites.add(matcher.group("testSuiteName"));
			}
		}

		return _passingTestSuites;
	}

	public List<String> getTestPullRequestQueryStrings() {
		long expiredTime = getTestPullRequestQueryStringExpiredTime();

		for (Map.Entry<String, Long> testPullRequestQueryStringsEntry :
				_testPullRequestQueryStrings.entrySet()) {

			long time = testPullRequestQueryStringsEntry.getValue();

			if (time < expiredTime) {
				_testPullRequestQueryStrings.remove(
					testPullRequestQueryStringsEntry.getKey());
			}
		}

		return new ArrayList<>(_testPullRequestQueryStrings.keySet());
	}

	public List<String> getTestPullRequestURLs() {
		long expiredTime = getTestPullRequestURLExpiredTime();

		for (Map.Entry<String, Long> testPullRequestQueryStringsEntry :
				_testPullRequestQueryStrings.entrySet()) {

			long time = testPullRequestQueryStringsEntry.getValue();

			if (time < expiredTime) {
				_testPullRequestURLs.remove(
					testPullRequestQueryStringsEntry.getKey());
			}
		}

		return new ArrayList<>(_testPullRequestURLs.keySet());
	}

	public void invokePullRequestTester(
		String masterURL,
		PullRequestTesterParameters pullRequestTesterParameters) {

		PullRequest pullRequest = pullRequestTesterParameters.getPullRequest();

		String invocationURL = JenkinsResultsParserUtil.combine(
			masterURL, "/job/", getCIJobName(pullRequestTesterParameters),
			"/buildWithParameters?",
			pullRequestTesterParameters.toQueryString());

		addTestPullRequestURL(invocationURL);

		if (isJenkinsJobEnabled()) {
			processURL(invocationURL);

			String ciReevaluateBuildId =
				pullRequestTesterParameters.getCiReevaluateBuildId();

			if (_ciForwardEligible ||
				JenkinsResultsParserUtil.isNullOrEmpty(ciReevaluateBuildId)) {

				return;
			}

			String publicJobURL = JenkinsResultsParserUtil.getRemoteURL(
				invocationURL.replaceFirst("(.*)/.*", "$1"));

			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Pull request test invoked at ", publicJobURL, "."));
			}

			pullRequest.setTestSuiteStatus(
				pullRequestTesterParameters.getCiTestSuiteName(),
				PullRequest.TestSuiteStatus.PENDING, publicJobURL);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Pull request test URL: " + invocationURL);
			}
		}
	}

	public boolean isGitHubAutopullEnabled() {
		String gitHubAutopullEnabled = _jenkinsProperties.getProperty(
			"github.autopull.enabled");

		if (!isBlank(gitHubAutopullEnabled) &&
			gitHubAutopullEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isGitHubCacheEnabled() {
		String gitHubCacheEnabled = _jenkinsProperties.getProperty(
			"github.cache.enabled");

		if (!isBlank(gitHubCacheEnabled) && gitHubCacheEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isGitHubMirrorEnabled() {
		String gitHubMirrorEnabled = _jenkinsProperties.getProperty(
			"github.mirror.enabled");

		if (!isBlank(gitHubMirrorEnabled) &&
			gitHubMirrorEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isGitHubPostEnabled() {
		String gitHubPostEnabled = _jenkinsProperties.getProperty(
			"github.post.enabled");

		if (!isBlank(gitHubPostEnabled) && gitHubPostEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isGitHubSubrepoSyncEnabled() {
		String gitHubSubrepoSyncEnabled = _jenkinsProperties.getProperty(
			"github.subrepo.sync.enabled");

		if (!isBlank(gitHubSubrepoSyncEnabled) &&
			gitHubSubrepoSyncEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isJenkinsJobEnabled() {
		String jenkinsJobEnabled = _jenkinsProperties.getProperty(
			"jenkins.job.enabled");

		if (!isBlank(jenkinsJobEnabled) && jenkinsJobEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isValidAutopull(String repo) {
		if (repo.startsWith("com-liferay-")) {
			return true;
		}

		return false;
	}

	public boolean isValidCIMergeFile(PullRequest pullRequest) {
		List<String> filenames = pullRequest.getFilenames();

		if (filenames.size() > 1) {
			return false;
		}

		for (String filename : filenames) {
			if (filename.endsWith("/ci-merge")) {
				return true;
			}
		}

		return false;
	}

	public void process() {
		try {
			_jenkinsBuildProperties =
				JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		if (_payloadJSONObject.isPusher()) {
			syncAutopull();
			syncMirror();
			syncSubrepo();
		}

		if (_payloadJSONObject.isCommentCreated()) {
			_processCommentCreated();

			return;
		}

		if (_payloadJSONObject.isPullRequestOpened()) {
			_processPullRequestOpened();
		}

		if (_payloadJSONObject.isPullRequestSynchronize()) {
			_processPullRequestSynchronize();
		}
	}

	public String processURL(String url) {
		return processURL(url, null, HttpRequestMethod.GET);
	}

	public String processURL(String url, String body) {
		return processURL(url, body, HttpRequestMethod.POST);
	}

	public String processURL(
		String url, String body, HttpRequestMethod method) {

		if ((method == HttpRequestMethod.PATCH) ||
			(method == HttpRequestMethod.POST)) {

			if (body == null) {
				throw new IllegalArgumentException(
					method.toString() + " method requires a body");
			}

			body = JenkinsResultsParserUtil.combine(
				"token=",
				_jenkinsBuildProperties.getProperty(
					"jenkins.authentication.token"),
				"&", body);
		}
		else if ((method == HttpRequestMethod.DELETE) ||
				 (method == HttpRequestMethod.PUT)) {

			body = JenkinsResultsParserUtil.combine(
				"token=",
				_jenkinsBuildProperties.getProperty(
					"jenkins.authentication.token"));
		}
		else if (method == HttpRequestMethod.GET) {
			if (body != null) {
				throw new IllegalArgumentException(
					method.toString() + " method should not have a body");
			}

			String prefix = "&";

			if (url.contains("?")) {
				prefix = "?";
			}

			url = JenkinsResultsParserUtil.combine(
				url, prefix, "token=",
				_jenkinsBuildProperties.getProperty(
					"jenkins.authentication.token"));
		}

		try {
			return JenkinsResultsParserUtil.toString(url, false, method, body);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to retrieve url " + url, ioException);
		}
	}

	public void removeTestPullRequestQueryString(String queryString) {
		_testPullRequestQueryStrings.remove(queryString);
	}

	public void removeTestPullRequestURL(String url) {
		_testPullRequestURLs.remove(url);
	}

	protected void commentMergeSubrepoPullRequest(PullRequest pullRequest) {
		try {
			String currentSHA = "";

			StringBuilder sb = new StringBuilder();

			sb.append("https://raw.githubusercontent.com/liferay/");
			sb.append(pullRequest.getGitHubRemoteGitRepositoryName());
			sb.append("/");
			sb.append(pullRequest.getUpstreamBranchName());
			sb.append("/");
			sb.append(pullRequest.getCIMergeSubrepo());
			sb.append("/.gitrepo");

			String gitrepoContent = processURL(sb.toString());

			Matcher matcher = _gitrepoSHAPattern.matcher(gitrepoContent);

			while (matcher.find()) {
				currentSHA = matcher.group(1);
			}

			matcher = _gitrepoRepoPattern.matcher(gitrepoContent);

			String repo = "";

			while (matcher.find()) {
				repo = matcher.group(1);
			}

			String mergeSHA = pullRequest.getCIMergeSHA();

			String compareURL =
				"https://github.com/liferay/" + repo + "/compare/" +
					currentSHA + "..." + mergeSHA;

			if (_log.isInfoEnabled()) {
				_log.info("Subrepo compare URL: " + compareURL);
			}

			String message =
				"Subrepo changes: " + compareURL +
					"\n\nci:test:sf and ci:test:relevant must pass in order " +
						"for auto-merge to initiate.";

			pullRequest.addComment(message);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip generation of the ci:merge diff because of an " +
						"exception",
					exception);
			}
		}
	}

	protected String formatCSV(String string) {
		string = string.replaceAll("\\s*,\\s*", ", ");

		string = string.replaceFirst(",\\s+$", "");

		return string.replaceFirst("(.*), (.+)", "$1 or $2");
	}

	protected List<String> getAllowedSenderUsernames(PullRequest pullRequest) {
		String allowedSenderNamesProperty =
			JenkinsResultsParserUtil.getCIProperty(
				pullRequest.getUpstreamBranchName(),
				JenkinsResultsParserUtil.combine(
					"allowed.sender.names[", pullRequest.getOwnerUsername(),
					"]"),
				pullRequest.getGitHubRemoteGitRepositoryName());

		if ((allowedSenderNamesProperty == null) ||
			allowedSenderNamesProperty.isEmpty()) {

			return Collections.emptyList();
		}

		return Arrays.asList(allowedSenderNamesProperty.split("\\s*,\\s*"));
	}

	protected List<String> getBuildURLs(JSONObject payloadJSONObject) {
		List<String> buildURLs = new ArrayList<>();

		String commentsURL = payloadJSONObject.optString("comments_url");

		if (isBlank(commentsURL)) {
			JSONObject issueJSONObject = payloadJSONObject.optJSONObject(
				"issue");

			commentsURL = issueJSONObject.getString("comments_url");
		}

		JSONArray commentsJSONArray = new JSONArray(processURL(commentsURL));

		for (int i = 0; i < commentsJSONArray.length(); i++) {
			JSONObject commentJSONObject = commentsJSONArray.getJSONObject(i);

			String body = commentJSONObject.getString("body");

			Matcher buildURLMatcher = _buildURLPattern.matcher(body);

			if (buildURLMatcher.find()) {
				buildURLs.add(buildURLMatcher.group("buildURL"));
			}
		}

		return buildURLs;
	}

	protected String[] getCIForwardRequiredPassingSuites() {
		String ciForwardRequiredPassingSuites =
			_jenkinsBuildProperties.getProperty(
				"pull.request.forward.required.passing.suites", "");

		if (ciForwardRequiredPassingSuites.isEmpty()) {
			return new String[0];
		}

		return ciForwardRequiredPassingSuites.split("\\s*,\\s*");
	}

	protected String[] getCIForwardRequiredTestSuites() {
		String ciForwardRequiredTestSuites =
			_jenkinsBuildProperties.getProperty(
				"pull.request.forward.required.test.suites", "");

		if (ciForwardRequiredTestSuites.isEmpty()) {
			return new String[0];
		}

		return ciForwardRequiredTestSuites.split("\\s*,\\s*");
	}

	protected String getCompanionBranchName(String branchName) {
		if (branchName.contains("-private")) {
			return branchName.replace("-private", "");
		}

		return branchName + "-private";
	}

	protected List<String> getJiraProjectKeys(PullRequest pullRequest) {
		if (_jiraProjectKeys != null) {
			return _jiraProjectKeys;
		}

		String branchName = pullRequest.getUpstreamBranchName();

		String repositoryName = pullRequest.getGitRepositoryName();

		String jiraProjectKeysProperty = JenkinsResultsParserUtil.getCIProperty(
			branchName, "jira.project.keys", repositoryName);

		if ((jiraProjectKeysProperty != null) &&
			jiraProjectKeysProperty.isEmpty()) {

			_jiraProjectKeys = Arrays.asList(
				jiraProjectKeysProperty.split("\\s*,\\s*"));
		}

		return _jiraProjectKeys;
	}

	protected String getSubrepoCentralMergePullRequestRecipientName(
		String refName) {

		return _jenkinsBuildProperties.getProperty(
			JenkinsResultsParserUtil.combine(
				"subrepo.merge.receiver.name[", refName, "]"),
			"liferay");
	}

	protected String getSubrepoPath(JSONObject payloadJSONObject) {
		JSONObject headCommitJSONObject = payloadJSONObject.optJSONObject(
			"head_commit");

		if (headCommitJSONObject == null) {
			return null;
		}

		String commitMessage = headCommitJSONObject.optString("message");

		if ((commitMessage != null) && commitMessage.contains("LPS-0 Clear")) {
			String subrepoPath = commitMessage.replaceAll(".* ", "");

			if (subrepoPath.startsWith("modules/apps") ||
				subrepoPath.startsWith("modules/private/apps")) {

				return subrepoPath;
			}
		}

		JSONArray modifiedJSONArray = headCommitJSONObject.getJSONArray(
			"modified");

		for (int i = 0; i < modifiedJSONArray.length(); i++) {
			String fileName = modifiedJSONArray.getString(i);

			if (fileName.endsWith(".gitrepo")) {
				String subrepoPath = fileName.replaceAll("/.gitrepo", "");

				if (subrepoPath.startsWith("modules/apps") ||
					subrepoPath.startsWith("modules/private/apps")) {

					return subrepoPath;
				}
			}
		}

		return null;
	}

	protected long getTestPullRequestQueryStringExpiredTime() {
		//return System.currentTimeMillis() - 3600000; // 1 hour

		return System.currentTimeMillis() - 21600000; // 6 hours
	}

	protected long getTestPullRequestURLExpiredTime() {
		//return System.currentTimeMillis() - 3600000; // 1 hour

		return System.currentTimeMillis() - 21600000; // 6 hours
	}

	protected boolean hasLiferayEmailAddress(String githubUsername) {
		if (githubUsername.equals("liferay")) {
			return true;
		}

		JSONObject jsonObject = new JSONObject(
			processURL("https://api.github.com/users/" + githubUsername));

		String emailAddress = jsonObject.optString("email");

		if ((emailAddress != null) && emailAddress.endsWith("@liferay.com")) {
			return true;
		}

		return false;
	}

	protected boolean hasValidJIRAReferences(PullRequest pullRequest) {
		String ownerUsername = pullRequest.getOwnerUsername();

		if (!ownerUsername.equals("brianchandotcom")) {
			return true;
		}

		List<String> jiraProjectKeys = getJiraProjectKeys(pullRequest);

		if (jiraProjectKeys.isEmpty()) {
			return true;
		}

		for (GitCommit commit : pullRequest.getGitHubRemoteCommits()) {
			String message = commit.getMessage();

			if (message.contains("subrepo:ignore")) {
				return true;
			}

			boolean hasJIRAProjectKey = false;

			for (String jiraProjectKey : _jiraProjectKeys) {
				if (message.contains(jiraProjectKey + "-")) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Contains JIRA project keys " + jiraProjectKey);
					}

					hasJIRAProjectKey = true;
				}
			}

			String emailAddress = commit.getEmailAddress();

			if (emailAddress.equals("brian.chan@liferay.com") ||
				emailAddress.equals("continuous-integration@liferay.com") ||
				emailAddress.equals("samuel.tran@liferay.com")) {

				if (_log.isInfoEnabled()) {
					_log.info("Allow commit from Brian, Sam, or CI");
				}

				continue;
			}

			if (!hasJIRAProjectKey) {
				return false;
			}
		}

		return true;
	}

	protected void invokeCIStart(
		JenkinsMaster jenkinsMaster, JSONObject payloadJSONObject) {

		try {
			String masterURL;

			if (jenkinsMaster != null) {
				masterURL = jenkinsMaster.getURL();
			}
			else {
				try {
					masterURL = JenkinsResultsParserUtil.combine(
						LoadBalancerUtil.getMostAvailableMasterURL(
							"base.invocation.url", "http://test-1.liferay.com"),
						"/");
				}
				catch (Exception exception) {
					masterURL = "http://test-1.liferay.com/";
				}
			}

			masterURL += "job/liferay-ci-start/buildWithParameters";

			String jenkinsAuthenticationToken =
				JenkinsResultsParserUtil.getBuildProperty(
					"jenkins.authentication.token");

			String postString = JenkinsResultsParserUtil.combine(
				"token=", jenkinsAuthenticationToken,
				"&GITHUB_WEBHOOK_PAYLOAD=", payloadJSONObject.toString());

			JenkinsResultsParserUtil.toString(masterURL, postString, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to invoke liferay-ci-start", exception);
			}
		}
	}

	protected void invokeCIStart(JSONObject payloadJSONObject) {
		invokeCIStart(null, payloadJSONObject);
	}

	protected boolean isBlank(String string) {
		if (string == null) {
			return true;
		}

		string = string.trim();

		if (string.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isBotPush(JSONObject payloadJSONObject) {
		if (isBlank(getSubrepoPath(payloadJSONObject))) {
			return false;
		}

		return true;
	}

	protected boolean isLiferayUser(String gitHubUsername) {
		if (gitHubUsername.equals("liferay")) {
			return true;
		}

		JSONArray jsonArray = new JSONArray(
			processURL(
				JenkinsResultsParserUtil.combine(
					"https://api.github.com/users/", gitHubUsername, "/orgs")));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String organizationLogin = jsonObject.getString("login");

			if (organizationLogin.equals("liferay")) {
				if (_log.isInfoEnabled()) {
					_log.info("Valid Liferay member: " + gitHubUsername);
				}

				return true;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Invalid Liferay member: " + gitHubUsername);
		}

		return false;
	}

	protected boolean isSynchronizeablePullRequest(PullRequest pullRequest) {
		String receiverUsername = pullRequest.getReceiverUsername();

		if (receiverUsername.equals("brianchandotcom")) {
			return false;
		}

		return true;
	}

	protected boolean isTestablePullRequest(PullRequest pullRequest) {
		String branchName = pullRequest.getUpstreamBranchName();

		String repositoryName = pullRequest.getGitRepositoryName();

		List<String> ciEnabledBranchNames = _getCIEnabledBranchNames(
			repositoryName);

		if (!ciEnabledBranchNames.contains(branchName)) {
			StringBuilder sb = new StringBuilder(4);

			sb.append("Closing pull request because pulls for reference ");
			sb.append(branchName);
			sb.append(" should not be sent to repository ");
			sb.append(repositoryName);

			if (_log.isInfoEnabled()) {
				_log.info(sb.toString());
			}

			pullRequest.addComment(sb.toString());

			pullRequest.close();

			return false;
		}

		String ownerUsername = pullRequest.getOwnerUsername();

		if (!_whiteListedOwnerNames.isEmpty() &&
			!_whiteListedOwnerNames.contains(ownerUsername)) {

			String message = JenkinsResultsParserUtil.combine(
				"Skip pull request because the owner ", ownerUsername,
				" is not on the whitelist\n\nPull request tests have been ",
				"temporarily suspended.");

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			return false;
		}

		String senderUsername = pullRequest.getSenderUsername();

		if (ownerUsername.equals(
				getSubrepoCentralMergePullRequestRecipientName(branchName)) &&
			pullRequest.isMergeSubrepoRequest()) {

			if (!pullRequest.isValidCIMergeFile()) {
				String message = JenkinsResultsParserUtil.combine(
					"Closing pull request because a subrepo merge ",
					"request must only contain a single change to a ",
					"single ci-merge file.");

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return false;
			}

			String sha = pullRequest.getCIMergeSHA();

			if (sha.equals("")) {
				String message =
					"Closing pull request because the ci-merge file " +
						"modification is missing or incorrectly formatted";

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return false;
			}
		}

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			pullRequest.getGitHubRemoteGitRepository();

		if (ownerUsername.equals("liferay") &&
			!gitHubRemoteGitRepository.isSubrepository() &&
			!repositoryName.equals("liferay-portal-ee")) {

			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Skip pull request because it is a pull sent to ",
						"Liferay that is not a subrepo request"));
			}

			return false;
		}

		if (_whiteListedRepositoryMultiPattern.matches(repositoryName) ==
				null) {

			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Skip pull request because the repository ",
						repositoryName, " is not on the whitelist"));
			}

			return false;
		}

		if (!isLiferayUser(ownerUsername)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Skip pull request because the owner ", ownerUsername,
						" does not have access"));
			}

			return false;
		}

		if (!isLiferayUser(senderUsername)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Skip pull request because the tester ", senderUsername,
						" does not have access"));
			}

			if (hasLiferayEmailAddress(senderUsername)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Your pull request was not tested because you ");
				sb.append("are not a member of the Liferay organization. ");
				sb.append("Please make sure that you have been added and ");
				sb.append("that your organization membership is set as ");
				sb.append("Public. See https://help.github.com/articles");
				sb.append("/publicizing-or-hiding-organization-");
				sb.append("membership for more information.");

				pullRequest.addComment(sb.toString());
			}

			return false;
		}

		String githubCIUsername = _jenkinsBuildProperties.getProperty(
			"github.ci.username");

		if (ownerUsername.equals("brianchandotcom") &&
			branchName.equals("master") &&
			repositoryName.equals("liferay-portal") &&
			!senderUsername.equals(githubCIUsername)) {

			StringBuilder sb = new StringBuilder(4);

			sb.append("Closing pull request because all `liferay-portal` pull");
			sb.append("requests sent to Brian Chan must be sent by using ");
			sb.append("`ci:forward` on a pull request that was sent to ");
			sb.append("someone else.");

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return false;
		}

		List<String> allowedSenderUsernames = getAllowedSenderUsernames(
			pullRequest);

		if (!allowedSenderUsernames.contains(senderUsername)) {
			StringBuilder sb = new StringBuilder(7);

			sb.append("Closing pull request because ");
			sb.append(senderUsername);
			sb.append(" is not an allowed sender on this branch. Please ");
			sb.append("resend this pull request to one of the following ");
			sb.append("allowed senders: ");
			sb.append(
				JenkinsResultsParserUtil.join(", ", allowedSenderUsernames));
			sb.append(".");

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return false;
		}

		List<String> collaboratorUsernames =
			gitHubRemoteGitRepository.getCollaboratorUsernames();

		if (!collaboratorUsernames.contains("liferay-continuous-integration")) {
			if (_log.isInfoEnabled()) {
				StringBuilder sb = new StringBuilder(4);

				sb.append("Skip pull request because ");
				sb.append("liferay-continuous-integration does not have ");
				sb.append("write access to ");
				sb.append(gitHubRemoteGitRepository.getHtmlURL());

				_log.info(sb.toString());
			}

			StringBuilder sb = new StringBuilder(5);

			sb.append("Your pull request was not tested because your ");
			sb.append("repository has not set liferay-continuous-integration ");
			sb.append("as a collaborator. See https://grow.liferay.com/share/");
			sb.append("Pull+Request+Tester+for+Liferay+Developers for more ");
			sb.append("information.");

			pullRequest.addComment(sb.toString());

			return false;
		}

		if (!hasValidJIRAReferences(pullRequest)) {
			StringBuilder sb = new StringBuilder(7);

			sb.append("Closing pull request because at least one commit ");
			sb.append("message is missing a reference to a required JIRA ");
			sb.append("project: ");
			sb.append(
				JenkinsResultsParserUtil.join(
					", ", getJiraProjectKeys(pullRequest)));
			sb.append(". Please verify that the JIRA project keys are ");
			sb.append("specified in ci.properties in the liferay-portal ");
			sb.append("repository.");

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return false;
		}

		// TODO: Ensure every commit has a required key. Make sure all keys are
		// valid via https://issues.liferay.com/rest/api/2/issue/LPS-5331. Make
		// sure sender is a team member of the component via
		// https://api.github.com/search/users?q=brian.chan@liferay.com+in%3A
		// email&type=Users

		return true;
	}

	protected boolean isValidPullRequestRefSHA(
		PullRequest pullRequest, String refSHA) {

		StringBuilder sb = new StringBuilder();

		sb.append("https://api.github.com/repos/liferay/");
		sb.append(pullRequest.getGitHubRemoteGitRepositoryName());
		sb.append("/commits/");
		sb.append(refSHA);

		JSONObject commitJSONObject = new JSONObject(processURL(sb.toString()));

		if (!commitJSONObject.has("sha")) {
			return false;
		}

		return true;
	}

	protected String join(String[] array) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			if ((i + 1) == array.length) {
				sb.append(" or ");
			}

			sb.append(array[i]);

			if ((i + 1) < array.length) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	protected void mergeSubrepo(PullRequest pullRequest, boolean force) {
		String ownerUsername = pullRequest.getOwnerUsername();

		String branchName = pullRequest.getUpstreamBranchName();

		String subrepoCentralMergePullRequestRecipientName =
			getSubrepoCentralMergePullRequestRecipientName(branchName);

		if (!ownerUsername.equals(
				subrepoCentralMergePullRequestRecipientName)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip merge subrepo because the user is not " +
						subrepoCentralMergePullRequestRecipientName);
			}
		}

		String repositoryName = pullRequest.getGitHubRemoteGitRepositoryName();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("branch", branchName);
		jsonObject.put("command", "pull");
		jsonObject.put("pullRequestNumber", pullRequest.getNumber());
		jsonObject.put("repo", repositoryName);

		try {
			if (!pullRequest.isValidCIMergeFile()) {
				String message = JenkinsResultsParserUtil.combine(
					"Closing pull request because a subrepo merge request ",
					"must only contain a single change to a single ",
					"ci-merge file");

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return;
			}
		}
		catch (Exception exception) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message, exception);
			}

			pullRequest.addComment(message + ".");

			return;
		}

		String sha = pullRequest.getCIMergeSHA();

		if (sha.equals("")) {
			String message =
				"Closing pull request because the ci-merge file modification " +
					"is missing or incorrectly formatted";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo sha: " + sha);
		}

		jsonObject.put("sha", sha);

		String subrepo = pullRequest.getCIMergeSubrepo();

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo name: " + subrepo);
		}

		jsonObject.put("subrepo", subrepo);

		String statusURL =
			"https://api.github.com/repos/" +
				subrepoCentralMergePullRequestRecipientName + "/" +
					repositoryName + "/commits/" + pullRequest.getSenderSHA() +
						"/status";

		JSONArray statusesJSONArray = null;

		for (int i = 0; i < 3; i++) {
			try {
				JSONObject statusJSONObject = new JSONObject(
					processURL(statusURL));

				statusesJSONArray = statusJSONObject.getJSONArray("statuses");

				break;
			}
			catch (Exception exception) {
				if (_log.isInfoEnabled()) {
					_log.info("Retrying " + statusURL, exception);
				}

				JenkinsResultsParserUtil.sleep(1000);
			}
		}

		if (statusesJSONArray == null) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo force: " + force);
		}

		if (!force) {
			Map<String, String> statuses = new HashMap<>();

			for (int i = 0; i < statusesJSONArray.length(); i++) {
				JSONObject statusJSONObject = statusesJSONArray.getJSONObject(
					i);

				String context = statusJSONObject.getString("context");
				String state = statusJSONObject.getString("state");

				statuses.put(context, state);
			}

			boolean validStatus = false;

			if (statuses.containsKey("liferay/ci:test:relevant") &&
				statuses.containsKey("liferay/ci:test:sf")) {

				String relevantStatus = statuses.get(
					"liferay/ci:test:relevant");
				String sfStatus = statuses.get("liferay/ci:test:sf");

				if (relevantStatus.equals("success") &&
					sfStatus.equals("success")) {

					validStatus = true;
				}
			}

			if (!validStatus) {
				String message =
					"Skip merge subrepo because tests have not passed";

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return;
			}
		}

		String gitHubWebSubrepoHostname = _jenkinsProperties.getProperty(
			"github.web.subrepo.hostname");

		try {
			jsonObject.put("remove", "true");

			processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to remove key from subrepo processor queue",
					exception);
			}
		}

		jsonObject.remove("remove");

		try {
			String subrepoJSON = processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());

			JSONObject subrepoJSONObject = new JSONObject(subrepoJSON);

			int queueSize = subrepoJSONObject.getInt("queueSize");

			String message =
				"This subrepo merge request was added to the processor queue " +
					"at position " + queueSize;

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message + ".");
		}
		catch (Exception exception) {
			exception.printStackTrace();

			String message = "Skip merge subrepo because of an internal error";

			if (_log.isInfoEnabled()) {
				_log.info(message, exception);
			}

			pullRequest.addComment(message + ".");
		}
	}

	protected void openPullRequest(PullRequest pullRequest) {
		if (!isGitHubPostEnabled()) {
			return;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("state", "open");

		String repositoryName = pullRequest.getGitHubRemoteGitRepositoryName();

		String pullRequestJSON = processURL(
			JenkinsResultsParserUtil.combine(
				"https://api.github.com/repos/", pullRequest.getOwnerUsername(),
				"/", repositoryName, "/pulls/",
				String.valueOf(pullRequest.getNumber())),
			jsonObject.toString(), HttpRequestMethod.PATCH);

		JSONObject pullRequestJSONObject = new JSONObject(pullRequestJSON);

		JSONArray errorsJSONArray = pullRequestJSONObject.optJSONArray(
			"errors");

		if (errorsJSONArray == null) {
			return;
		}

		for (int i = 0; i < errorsJSONArray.length(); i++) {
			JSONObject errorJSONObject = errorsJSONArray.getJSONObject(i);

			String message = errorJSONObject.optString("message");

			if (message == null) {
				continue;
			}

			pullRequest.addComment("GitHub error message: " + message);
		}
	}

	protected void stopJenkinsTests() {
		String ciStopSuite = null;

		JSONObject commentJSONObject = _payloadJSONObject.optJSONObject(
			"comment");

		if (commentJSONObject != null) {
			String body = commentJSONObject.optString("body");

			String regex = "ci:stop:([^:\\s]+).*";

			if ((body != null) && body.matches(regex)) {
				ciStopSuite = body.replaceAll(regex, "$1");
			}
		}

		for (String buildURL : getBuildURLs(_payloadJSONObject)) {
			if (ciStopSuite != null) {
				Map<String, String> buildParameters =
					JenkinsResultsParserUtil.getBuildParameters(buildURL);

				String ciTestSuite = buildParameters.get("CI_TEST_SUITE");

				if ((ciTestSuite == null) || !ciTestSuite.equals(ciStopSuite)) {
					continue;
				}
			}

			String jenkinsAdminUserToken = _jenkinsProperties.getProperty(
				"jenkins.admin.user.token");

			try {
				JenkinsStopBuildUtil.stopBuild(
					buildURL, "jenkins-admin", jenkinsAdminUserToken);
			}
			catch (Exception exception) {
				throw new RuntimeException(
					"Unable to stop build: " + buildURL, exception);
			}
		}
	}

	protected void syncAutopull() {
		String branch = _payloadJSONObject.getPusherBranch();

		if ((branch == null) || branch.isEmpty()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull branch: " + branch);
		}

		String ownerName = _payloadJSONObject.getStringByPath(
			"repository/owner/name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull owner: " + ownerName);
		}

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync autopull because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = _payloadJSONObject.getStringByPath("repository/name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull repo: " + repo);
		}

		if (!isValidAutopull(repo)) {
			_log.info("Skip sync autopull because the repo name is invalid");

			return;
		}

		String jenkinsAccessToken = _jenkinsProperties.getProperty(
			"jenkins.access.token");

		List<String> urls = new ArrayList<>();

		urls.add(
			JenkinsResultsParserUtil.combine(
				"http://test-1-0/job/merge-central-subrepository(", branch,
				")/buildWithParameters?token=", jenkinsAccessToken));
		urls.add(
			JenkinsResultsParserUtil.combine(
				"http://test-1-0/job/merge-central-subrepository(",
				getCompanionBranchName(branch), ")/buildWithParameters?token=",
				jenkinsAccessToken));

		for (String url : urls) {
			if (isGitHubAutopullEnabled()) {
				try {
					processURL(url);
				}
				catch (Exception exception) {
					if (_log.isInfoEnabled()) {
						_log.info("Skip sync autopull", exception);
					}

					return;
				}
			}
			else if (_log.isInfoEnabled()) {
				_log.info("Sync autopull URL: " + url);
			}
		}
	}

	protected void syncMirror() {
		if (!isGitHubMirrorEnabled()) {
			return;
		}

		String ownerName = _payloadJSONObject.getStringByPath(
			"repository/owner/name");

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync mirror because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = _payloadJSONObject.getStringByPath("repository/name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync mirror repo: " + repo);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("repo", repo);

		String sha = _payloadJSONObject.getString("after");

		if (_log.isInfoEnabled()) {
			_log.info("Sync mirror sha: " + sha);
		}

		jsonObject.put("sha", sha);

		String gitHubWebMirrorHostname = _jenkinsProperties.getProperty(
			"github.web.mirror.hostname");

		try {
			processURL(
				"http://" + gitHubWebMirrorHostname + "/osb-github-web/mirror",
				jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info("Skip sync mirror", exception);
			}
		}
	}

	protected void syncSubrepo() {
		if (!isGitHubSubrepoSyncEnabled()) {
			return;
		}

		String branch = _payloadJSONObject.getPusherBranch();

		if ((branch == null) || branch.isEmpty()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo branch: " + branch);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("branch", branch);

		String ownerName = _payloadJSONObject.getStringByPath(
			"repository/owner/name");

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync subrepo because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = _payloadJSONObject.getStringByPath("repository/name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo repo: " + repo);
		}

		jsonObject.put("repo", repo);

		String sha = _payloadJSONObject.getString("after");

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo sha: " + sha);
		}

		jsonObject.put("sha", sha);

		jsonObject.put("pullRequestNumber", "0");

		String command = "push";
		String propertyName = "github.web.subrepo.hostname";
		String subrepo = "all";

		if (isBotPush(_payloadJSONObject)) {
			command = "release";
			propertyName = "github.web.release.hostname";
			subrepo = getSubrepoPath(_payloadJSONObject);
		}

		jsonObject.put("command", command);

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo command: " + command);
		}

		jsonObject.put("subrepo", subrepo);

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo argument: " + subrepo);
		}

		String gitHubWebSubrepoHostname = _jenkinsProperties.getProperty(
			propertyName);

		try {
			processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info("Skip sync subrepo", exception);
			}
		}
	}

	protected void testPullRequest(
		PullRequestTesterParameters pullRequestTesterParameters) {

		if (!isTestablePullRequest(
				pullRequestTesterParameters.getPullRequest())) {

			return;
		}

		String pullRequestTesterQueryString =
			pullRequestTesterParameters.toQueryString();

		addTestPullRequestQueryString(pullRequestTesterQueryString);

		boolean gitHubWebhookPullRequestMirroring = Boolean.parseBoolean(
			_jenkinsBuildProperties.getProperty(
				"github.webhook.pullrequest.mirroring",
				Boolean.FALSE.toString()));

		if (gitHubWebhookPullRequestMirroring) {
			String masterURL;

			try {
				masterURL = LoadBalancerUtil.getMostAvailableMasterURL(
					"base.invocation.url", "http://test-1.liferay.com");
			}
			catch (Exception exception) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Setting base invocation URL to " +
							"http://test-1.liferay.com because load balancer " +
								"threw an exception");
				}

				masterURL = "http://test-1.liferay.com";
			}

			invokePullRequestTester(masterURL, pullRequestTesterParameters);
		}
	}

	private List<String> _getCIEnabledBranchNames(String repositoryName) {
		String ciEnabledBranchNames = _jenkinsBuildProperties.getProperty(
			JenkinsResultsParserUtil.combine(
				"github.ci.enabled.branch.names[", repositoryName, "]"),
			"");

		return Arrays.asList(ciEnabledBranchNames.split(","));
	}

	private void _processCommentCreated() {
		JSONObject commentJSONObject = _payloadJSONObject.getJSONObject(
			"comment");

		String body = commentJSONObject.getString("body");

		JSONObject userJSONObject = commentJSONObject.getJSONObject("user");

		String login = userJSONObject.getString("login");

		if (body.startsWith("ci:") && !body.contains("ci:help") &&
			!isLiferayUser(login)) {

			if (_log.isInfoEnabled()) {
				_log.info(
					JenkinsResultsParserUtil.combine(
						"Skip CI action because ", login,
						" is not a Liferay member"));
			}

			if (hasLiferayEmailAddress(login)) {
				PullRequest pullRequest = _payloadJSONObject.getPullRequest();

				StringBuilder sb = new StringBuilder();

				sb.append("You cannot perform that action because you ");
				sb.append("are not a member of the Liferay organization. ");
				sb.append("Please make sure that you have been added and ");
				sb.append("that your organization membership is set as ");
				sb.append("Public. See https://help.github.com/articles");
				sb.append("/publicizing-or-hiding-organization-");
				sb.append("membership for more information.");

				pullRequest.addComment(sb.toString());
			}

			return;
		}

		PullRequest pullRequest = _payloadJSONObject.getPullRequest();

		PullRequestTesterParameters pullRequestTesterParameters =
			new PullRequestTesterParameters(pullRequest);

		if (body.startsWith("ci:close")) {
			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered close pull request");
			}

			pullRequest.close();
		}

		if (body.startsWith("ci:forward")) {
			pullRequestTesterParameters.setCiForwardReceiverUsername(
				_jenkinsBuildProperties.getProperty(
					"pull.request.forward.default.receiver.username"));

			String[] ciForwardRequiredTestSuites =
				getCIForwardRequiredTestSuites();

			if (ciForwardRequiredTestSuites.length == 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("There are no required test suites specified ");
				sb.append("for `ci:forward`.\nNo test will be triggered.");
				sb.append("\nIf you think this is a mistake please ");
				sb.append("contact the CI Infrastructure team.");

				pullRequest.addComment(sb.toString());

				return;
			}

			String[] ciForwardRequiredPassingSuites =
				getCIForwardRequiredPassingSuites();

			if (ciForwardRequiredPassingSuites.length == 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("There are no required passing suites specified");
				sb.append(" for `ci:forward`.\nNo test will be triggered.");
				sb.append("\nIf you think this is a mistake please ");
				sb.append("contact the CI Infrastructure team.");

				pullRequest.addComment(sb.toString());

				return;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("CI is automatically triggering the following ");
			sb.append("test suites:\n");

			for (String ciForwardRequiredTestSuite :
					ciForwardRequiredTestSuites) {

				sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
				sb.append(ciForwardRequiredTestSuite);
				sb.append("**\n");
			}

			sb.append("\n");
			sb.append("The pull request will automatically be forwarded ");
			sb.append("to the user `");
			sb.append(
				pullRequestTesterParameters.getCiForwardReceiverUsername());
			sb.append("` if the following test suites pass:\n");

			_ciForwardEligible = true;

			Set<String> passingTestSuites = getPassingTestSuites();

			for (String ciForwardRequiredPassingSuite :
					ciForwardRequiredPassingSuites) {

				sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
				sb.append(ciForwardRequiredPassingSuite);
				sb.append("**\n");

				if (!passingTestSuites.contains(
						ciForwardRequiredPassingSuite)) {

					_ciForwardEligible = false;
				}
			}

			pullRequest.addComment(sb.toString());

			List<String> skippedTestSuites = new ArrayList<>(
				ciForwardRequiredTestSuites.length);

			for (String ciForwardRequiredTestSuite :
					ciForwardRequiredTestSuites) {

				if (passingTestSuites.contains(ciForwardRequiredTestSuite)) {
					skippedTestSuites.add(ciForwardRequiredTestSuite);

					continue;
				}

				pullRequestTesterParameters.setCiTestSuiteName(
					ciForwardRequiredTestSuite);

				testPullRequest(pullRequestTesterParameters);
			}

			if (!skippedTestSuites.isEmpty()) {
				sb = new StringBuilder();

				sb.append("Skipping previously passed test suites:\n");

				for (String skippedTestSuite : skippedTestSuites) {
					sb.append("`ci:test:");
					sb.append(skippedTestSuite);
					sb.append("`\n");
				}

				if (_log.isInfoEnabled()) {
					_log.info(sb.toString());
				}

				pullRequest.addComment(sb.toString());
			}

			if (_ciForwardEligible) {
				testPullRequest(pullRequestTesterParameters);
			}
		}

		if (body.startsWith("ci:help")) {
			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered help message");
			}

			StringBuilder sb = new StringBuilder();

			sb.append("## Available CI commands:\n");
			sb.append("#### ci:close\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Close the pull request.\n");

			String ciForwardReceiverUsername =
				_jenkinsBuildProperties.getProperty(
					"pull.request.forward.default.receiver.username");

			if ((ciForwardReceiverUsername != null) &&
				!ciForwardReceiverUsername.isEmpty()) {

				sb.append("#### ci:forward\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request ");
				sb.append(" and forward the pullrequest to `");
				sb.append(ciForwardReceiverUsername);
				sb.append("` if the required test suites pass.\n");
			}

			sb.append("#### ci:merge[:force]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Merge in the changes from ");
			sb.append("the subrepo. All tests must pass before this ");
			sb.append("command will successfully run. Optionally use the ");
			sb.append("force flag to bypass failed tests.\n");
			sb.append("#### ci:reevaluate:[buildID]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Reevaluate the pull request ");
			sb.append("result from a generated build ID.\n");
			sb.append("#### ci:reopen\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Reopen the pull request.\n");
			sb.append("#### ci:stop[:suite]\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Stop all currrently ");
			sb.append("running tests. Optionally specify the name of the ");
			sb.append("test suite.\n");

			String ciTestAvailableSuites =
				JenkinsResultsParserUtil.getCIProperty(
					pullRequest.getUpstreamBranchName(),
					"ci.test.available.suites",
					pullRequest.getGitHubRemoteGitRepositoryName());

			if (ciTestAvailableSuites != null) {
				sb.append("#### ci:test[:suite][:SHA|nocompile|");
				sb.append("norebase]\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request.");
				sb.append("Optionally specify the name of the test suite ");
				sb.append("and/or optionally specify the upstream SHA to ");
				sb.append("test against or use \"nocompile\" to test ");
				sb.append("with a prebuilt bundle or use \"norebase\" to ");
				sb.append("test without rebasing.");
				sb.append("\n\n&nbsp;&nbsp;&nbsp;&nbsp;List of ");
				sb.append("available test suites:\n");

				for (String ciTestAvailableSuite :
						ciTestAvailableSuites.split(",")) {

					sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
					sb.append(ciTestAvailableSuite);
					sb.append("** - ");

					String ciTestSuiteDescription =
						JenkinsResultsParserUtil.getCIProperty(
							pullRequest.getUpstreamBranchName(),
							JenkinsResultsParserUtil.combine(
								"ci.test.suite.description[",
								ciTestAvailableSuite, "]"),
							pullRequest.getGitHubRemoteGitRepositoryName());

					if (ciTestSuiteDescription != null) {
						sb.append(ciTestSuiteDescription);
					}
					else {
						sb.append("No description is available.");
					}

					sb.append("\n");
				}
			}
			else {
				sb.append("#### ci:test[:SHA|nocompile|norebase]\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request");
				sb.append(". Optionally specify the upstream SHA to test ");
				sb.append("against or use \"nocompile\" to test with a ");
				sb.append("prebuilt bundle or use \"norebase\" to test ");
				sb.append("without rebasing.\n");
			}

			sb.append("\n");
			sb.append("For more details, see ");
			sb.append("[GROW](https://grow.liferay.com/share/CI+");
			sb.append("liferay-continuous-integration+GitHub+Commands).");

			pullRequest.addComment(sb.toString());
		}

		if (body.startsWith("ci:merge")) {
			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered merge subrepo");
			}

			if (body.startsWith("ci:merge:force")) {
				if (!login.equals("brianchandotcom") &&
					!login.equals("brianwulbern") && !login.equals("jpince") &&
					!login.equals("pyoo47") && !login.equals("shuyangzhou") &&
					!login.equals("stsquared99")) {

					String message = "Only Brian Chan can force a merge";

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					pullRequest.addComment(message + ".");

					return;
				}

				mergeSubrepo(pullRequest, true);
			}
			else {
				mergeSubrepo(pullRequest, false);
			}
		}

		if (body.startsWith("ci:reevaluate")) {
			Matcher matcher = _reevaluatePattern.matcher(body);

			StringBuilder sb = new StringBuilder();

			if (matcher.find()) {
				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered reevaluate");
				}

				String buildID = matcher.group("buildID");

				pullRequestTesterParameters.setCiReevaluateBuildId(buildID);

				sb.append("CI is reevaluating the build with build ID: `");
				sb.append(buildID);
				sb.append("` against the latest valid upstream results.");

				pullRequest.addComment(sb.toString());

				testPullRequest(pullRequestTesterParameters);
			}
			else {
				sb.append("There is a missing or invalid build ID. ");
				sb.append("No reevaluation was triggered.");

				pullRequest.addComment(sb.toString());
			}
		}

		if (body.startsWith("ci:reopen")) {
			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered open pull request");
			}

			openPullRequest(pullRequest);
		}

		if (body.startsWith("ci:retest") || body.startsWith("ci:test")) {
			Matcher matcher = _testPattern.matcher(body);

			if (matcher.find()) {
				String testOption1 = matcher.group("testOption1");

				String rebaseSHA = null;
				String testSuite = null;

				if (testOption1.matches("[0-9a-f]{7,40}")) {
					if (isValidPullRequestRefSHA(pullRequest, testOption1)) {
						rebaseSHA = testOption1;
					}
					else {
						StringBuilder sb = new StringBuilder();

						sb.append("The test option '");
						sb.append(testOption1);
						sb.append("' matching SHA pattern [0-9a-f]{7,40}");
						sb.append(" is not a valid upstream SHA.\n");
						sb.append("The test will start with '");
						sb.append(testOption1);
						sb.append("' as the test suite");

						String message = sb.toString();

						if (_log.isInfoEnabled()) {
							_log.info(message);
						}

						pullRequest.addComment(message + ".");

						testSuite = testOption1;
					}
				}
				else if (testOption1.equals("forward")) {
					StringBuilder sb = new StringBuilder();

					sb.append("The test will not be initiated because ");
					sb.append("`ci:test:forward` is not a valid command. ");
					sb.append("Please use `ci:forward` instead");

					String message = sb.toString();

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					pullRequest.addComment(message + ".");

					return;
				}
				else if (testOption1.equals("nocompile") ||
						 testOption1.equals("norebase")) {

					rebaseSHA = testOption1;
				}
				else {
					testSuite = testOption1;
				}

				String testOption2 = matcher.group("testOption2");

				if (testOption2 != null) {
					if (testOption2.matches("[0-9a-f]{7,40}") ||
						testOption2.equals("nocompile") ||
						testOption2.equals("norebase")) {

						rebaseSHA = testOption2;
					}
					else if (testOption2.equals("forward")) {
						StringBuilder sb = new StringBuilder(5);

						sb.append("The test will not be initiated ");
						sb.append("because ");
						sb.append("`ci:test:[testsuite]:forward` ");
						sb.append("is not a valid command. Please use");
						sb.append("`ci:forward` instead");

						String message = sb.toString();

						if (_log.isInfoEnabled()) {
							_log.info(message);
						}

						pullRequest.addComment(message + ".");

						return;
					}
				}

				if ((rebaseSHA != null) && rebaseSHA.equals("nocompile")) {
					String distPortalBundlesBuildURL =
						JenkinsResultsParserUtil.getDistPortalBundlesBuildURL(
							pullRequest.getUpstreamBranchName());

					String message = null;

					if (distPortalBundlesBuildURL != null) {
						pullRequestTesterParameters.setPortalBundlesDistURL(
							distPortalBundlesBuildURL);

						rebaseSHA = processURL(
							distPortalBundlesBuildURL + "/git-hash");

						rebaseSHA = rebaseSHA.trim();

						message = "The test will run with a prebuilt bundle.";
					}
					else {
						message = "No valid prebuilt bundle is available.";

						rebaseSHA = null;
					}

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					pullRequest.addComment(message + ".");
				}
				else if ((rebaseSHA != null) && rebaseSHA.equals("norebase")) {
					String message = "The test will run without rebasing.";

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					pullRequest.addComment(message + ".");

					rebaseSHA = pullRequest.getCommonParentSHA();
				}

				if (testSuite != null) {
					pullRequestTesterParameters.setCiTestSuiteName(testSuite);

					if (testSuite.equals("gauntlet") &&
						!login.equals("CsabaTurcsan") &&
						!login.equals("Hanlf") && !login.equals("HarryC0204") &&
						!login.equals("Songyuewen") &&
						!login.equals("SylviaLuan") &&
						!login.equals("ZoltanTakacs") &&
						!login.equals("brianchandotcom") &&
						!login.equals("brianwulbern") &&
						!login.equals("ctampoya") &&
						!login.equals("gergelyszaz") &&
						!login.equals("jpince") &&
						!login.equals("kiyoshilee") &&
						!login.equals("lesliewong92") &&
						!login.equals("liferay-continuous-integration-hu") &&
						!login.equals("michaelhashimoto") &&
						!login.equals("michaelprigge") &&
						!login.equals("pyoo47") &&
						!login.equals("sharonchoi") &&
						!login.equals("shuyangzhou") &&
						!login.equals("stsquared99") &&
						!login.equals("suilin") && !login.equals("vicnate5") &&
						!login.equals("xbrianlee") &&
						!login.equals("yunlinsun")) {

						String message =
							"You do not have permission to run the test " +
								"gauntlet";

						if (_log.isInfoEnabled()) {
							_log.info(message);
						}

						pullRequest.addComment(message + ".");

						return;
					}
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered test");
			}

			testPullRequest(pullRequestTesterParameters);
		}

		if (body.startsWith("ci:stop")) {
			if (_log.isInfoEnabled()) {
				_log.info("Comment triggered stop");
			}

			stopJenkinsTests();
		}
	}

	private void _processPullRequestOpened() {
		PullRequest pullRequest = _payloadJSONObject.getPullRequest();

		PullRequestTesterParameters pullRequestTesterParameters =
			new PullRequestTesterParameters(pullRequest);

		List<PullRequest.Comment> pullRequestComments =
			pullRequest.getComments();

		PullRequest.Comment initialComment = pullRequestComments.get(0);

		String body = initialComment.getBody();

		String githubCIUsername = _jenkinsBuildProperties.getProperty(
			"github.ci.username");
		String ownerName = pullRequest.getOwnerUsername();
		String pullRequestForwardDefaultReceiverUsername =
			_jenkinsBuildProperties.getProperty(
				"pull.request.forward.default.receiver.username");
		String refName = pullRequest.getUpstreamBranchName();
		String repositoryName = pullRequest.getGitHubRemoteGitRepositoryName();
		String senderName = pullRequest.getSenderUsername();

		if (body.startsWith("Forwarded from:") &&
			ownerName.equals(pullRequestForwardDefaultReceiverUsername) &&
			senderName.equals(githubCIUsername)) {

			StringBuilder sb = new StringBuilder(2);

			sb.append("To conserve resources, the PR Tester does not ");
			sb.append("automatically run for forwarded pull requests.");

			pullRequest.addComment(sb.toString());

			return;
		}

		if (ownerName.equals(
				getSubrepoCentralMergePullRequestRecipientName(refName)) &&
			pullRequest.isMergeSubrepoRequest()) {

			commentMergeSubrepoPullRequest(pullRequest);

			pullRequestTesterParameters.setCiTestSuiteName("relevant");

			testPullRequest(pullRequestTesterParameters);

			pullRequestTesterParameters.setCiTestSuiteName("sf");

			testPullRequest(pullRequestTesterParameters);

			return;
		}

		if (ownerName.equals("brianchandotcom") &&
			repositoryName.contains("liferay-portal")) {

			if (!isTestablePullRequest(pullRequest)) {
				return;
			}

			if (refName.startsWith("ee-")) {
				StringBuilder sb = new StringBuilder(4);

				sb.append("CI is automatically triggering &quot;ci:");
				sb.append("test:sf&quot; for this pull to run Source ");
				sb.append("Formatter.\n\nComment &quot;ci:test&quot; ");
				sb.append("to run the full PR Tester for this pull.");

				pullRequest.addComment(sb.toString());
			}
			else {
				StringBuilder sb = new StringBuilder(6);

				sb.append("CI is automatically triggering &quot;ci:");
				sb.append("test:sf&quot; and &quot;ci:test:relevant");
				sb.append("&quot; for this pull to run Source ");
				sb.append("Formatter and relevant tests.\n\nComment ");
				sb.append("&quot;ci:test&quot; to run the full PR ");
				sb.append("Tester for this pull.");

				pullRequest.addComment(sb.toString());

				pullRequestTesterParameters.setCiTestSuiteName("relevant");

				testPullRequest(pullRequestTesterParameters);
			}

			pullRequestTesterParameters.setCiTestSuiteName("sf");

			testPullRequest(pullRequestTesterParameters);

			return;
		}

		if (!ownerName.equals("liferay")) {
			List<String> ciTestAutoTestSuiteNames = getCITestAutoTestSuiteNames(
				pullRequest);

			if (!ciTestAutoTestSuiteNames.isEmpty() &&
				isTestablePullRequest(pullRequest)) {

				StringBuilder sb = new StringBuilder();

				sb.append("CI is automatically triggering the following ");
				sb.append("test suites:\n");

				for (String ciTestAutoTestSuiteName :
						ciTestAutoTestSuiteNames) {

					sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
					sb.append(ciTestAutoTestSuiteName);
					sb.append("**\n");
				}

				pullRequest.addComment(sb.toString());

				for (String ciTestAutoTestSuiteName :
						ciTestAutoTestSuiteNames) {

					pullRequestTesterParameters.setCiTestSuiteName(
						ciTestAutoTestSuiteName);

					testPullRequest(pullRequestTesterParameters);
				}

				return;
			}
		}

		if (isLiferayUser(pullRequest.getSenderUsername())) {
			StringBuilder sb = new StringBuilder(7);

			sb.append("To conserve resources, the PR Tester does not ");
			sb.append("automatically run for every pull.\n\nIf your ");
			sb.append("code changes were already tested in another ");
			sb.append("pull, reference that pull in this pull so ");
			sb.append("the test results can be analyzed.\n\nIf your ");
			sb.append("pull was never tested, comment ");
			sb.append("&quot;ci:test&quot; to run the PR Tester for ");
			sb.append("this pull.");

			GitHubRemoteGitRepository gitHubRemoteGitRepository =
				pullRequest.getGitHubRemoteGitRepository();

			if (!ownerName.equals("liferay") ||
				gitHubRemoteGitRepository.isSubrepository() ||
				repositoryName.equals("liferay-portal-ee")) {

				pullRequest.addComment(sb.toString());
			}
		}
	}

	private void _processPullRequestSynchronize() {
		PullRequest pullRequest = _payloadJSONObject.getPullRequest();

		if (isSynchronizeablePullRequest(pullRequest)) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Synchronize triggered close pull request");
		}

		String message = JenkinsResultsParserUtil.combine(
			"Closing and locking pull request because pull requests ",
			"sent to this user may not be updated. Please resend ",
			"this pull request.");

		pullRequest.addComment(message);

		pullRequest.close();

		pullRequest.lock();

		stopJenkinsTests();
	}

	private static final String[] _URLS_JENKINS_BUILD_PROPERTIES = {
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/build.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/build.properties"
	};

	private static Log _log = LogFactory.getLog(
		GitHubWebhookPayloadProcessor.class);

	private static Pattern _buildURLPattern = Pattern.compile(
		"Build[\\w\\s]*started.*Job Link: <a href=\"(?<buildURL>[^\"]+)\"");
	private static Set<String> _passingTestSuites;
	private static Pattern _reevaluatePattern = Pattern.compile(
		"ci:reevaluate:(?<buildID>[\\d]+_[\\d]+)");
	private static Pattern _testPattern = Pattern.compile(
		"ci:(re)?test:(?<testOption1>[^:\\s]+)(:(?<testOption2>[^:\\s]+))?");
	private static final List<String> _whiteListedOwnerNames =
		Collections.emptyList();
	private static final MultiPattern _whiteListedRepositoryMultiPattern =
		new MultiPattern(
			"liferay-jenkins-ee", "liferay-plugins(-ee)?",
			"liferay-portal(-ee)?", "com-liferay-.*");

	private boolean _ciForwardEligible;
	private Pattern _gitrepoRepoPattern = Pattern.compile(
		"remote = .*/([^\\.]*)\\.git");
	private Pattern _gitrepoSHAPattern = Pattern.compile(
		"commit = ([0-9a-f]{40})");
	/*Arrays.asList(
		"brianchandotcom", "liferay-continuous-integration", "liferay",
		"pyoo47", "shuyangzhou", "stsquared99");*/
	private Properties _jenkinsBuildProperties;
	private Properties _jenkinsProperties = new Properties();
	private List<String> _jiraProjectKeys;
	private Pattern _passingTestSuiteStatusDescriptionPattern = Pattern.compile(
		"\"ci:test:(?<testSuiteName>[^\"]+)\"\\s*has PASSED.");
	private final PayloadJSONObject _payloadJSONObject;
	private final Map<String, Long> _testPullRequestQueryStrings =
		new ConcurrentHashMap<>(100);
	private final Map<String, Long> _testPullRequestURLs =
		new ConcurrentHashMap<>(100);

	private static class PayloadJSONObject extends JSONObject {

		public PayloadJSONObject(String source) {
			super(source);
		}

		public PullRequest getPullRequest() {
			if (_pullRequest == null) {
				_pullRequest = new PullRequest(
					getStringByPath("pull_request/html_url"));
			}

			return _pullRequest;
		}

		public String getPusherBranch() {
			if (!isPusher()) {
				throw new IllegalStateException(
					"Unable to find branch in a non-pusher payload");
			}

			Matcher matcher = _branchPattern.matcher(getString("ref"));

			if (matcher.matches()) {
				return matcher.group(1);
			}

			return null;
		}

		public String getStringByPath(String path) {
			return _getStringByPath(this, path);
		}

		public boolean isCommentCreated() {
			String action = getAction();

			if (!action.equals("created")) {
				return false;
			}

			if (optJSONObject("comment") == null) {
				return false;
			}

			return true;
		}

		public boolean isPullRequestOpened() {
			String pullRequestAction = getPullRequestAction();

			if (pullRequestAction.equals("opened")) {
				return true;
			}

			return false;
		}

		public boolean isPullRequestSynchronize() {
			String pullRequestAction = getPullRequestAction();

			if (pullRequestAction.equals("synchronize")) {
				return true;
			}

			return false;
		}

		public boolean isPusher() {
			if (optJSONObject("pusher") != null) {
				return true;
			}

			return false;
		}

		protected String getAction() {
			String action = optString("action");

			if (action == null) {
				action = "";
			}

			return action;
		}

		protected String getPullRequestAction() {
			if (optJSONObject("pull_request") == null) {
				return "";
			}

			return getAction();
		}

		private String _getStringByPath(JSONObject jsonObject, String path) {
			Matcher matcher = _jsonPathPattern.matcher(path);

			matcher.find();

			String key = matcher.group(1);

			String remainingPath = matcher.group(2);

			if ((remainingPath == null) || remainingPath.isEmpty()) {
				return jsonObject.getString(key);
			}

			return _getStringByPath(getJSONObject(key), remainingPath);
		}

		private static final Pattern _branchPattern = Pattern.compile(
			"refs/heads/(.*)");
		private static final Pattern _jsonPathPattern = Pattern.compile(
			"([^/]+)/*(.*)");

		private PullRequest _pullRequest;

	}

	private static class PullRequestTesterParameters
		extends HashMap<String, String> {

		public PullRequestTesterParameters(PullRequest pullRequest) {
			_pullRequest = pullRequest;

			Properties jenkinsBuildProperties;

			try {
				jenkinsBuildProperties =
					JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to get build properties", ioException);
			}

			put(_KEY_CI_TEST_SUITE_NAME, "default");

			put(
				_KEY_JENKINS_ACCESS_TOKEN,
				jenkinsBuildProperties.getProperty("jenkins.access.token"));

			String ciForwardReceiverUsername =
				jenkinsBuildProperties.getProperty(
					"pull.request.forward.default.receiver.username");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					ciForwardReceiverUsername)) {

				setCiForwardReceiverUsername(ciForwardReceiverUsername);
			}
		}

		public String getCiForwardReceiverUsername() {
			return get(_KEY_CI_FORWARD_RECEIVER_USERNAME);
		}

		public String getCiReevaluateBuildId() {
			return get(_KEY_CI_REEVALUATE_BUILD_ID);
		}

		public String getCiTestSuiteName() {
			return get(_KEY_CI_TEST_SUITE_NAME);
		}

		public PullRequest getPullRequest() {
			return _pullRequest;
		}

		public void setCiForwardReceiverUsername(
			String ciForwardReceiverUsername) {

			put(_KEY_CI_FORWARD_RECEIVER_USERNAME, ciForwardReceiverUsername);
		}

		public void setCiReevaluateBuildId(String ciReevaluateBuildId) {
			put(_KEY_CI_REEVALUATE_BUILD_ID, ciReevaluateBuildId);
		}

		public void setCiTestSuiteName(String ciTestSuiteName) {
			if (JenkinsResultsParserUtil.isNullOrEmpty(ciTestSuiteName)) {
				ciTestSuiteName = "default";
			}

			put(_KEY_CI_TEST_SUITE_NAME, ciTestSuiteName);
		}

		public void setPortalBundlesDistURL(String portalBundlesDistURL) {
			put(_KEY_PORTAL_BUNDLES_DIST_URL, portalBundlesDistURL);
		}

		public String toQueryString() {
			StringBuilder sb = new StringBuilder();

			for (Map.Entry<String, String> entry : entrySet()) {
				_appendParameter(sb, entry.getKey(), entry.getValue());
			}

			_appendParameter(
				sb, _KEY_PULL_REQUEST_NUMBER, _pullRequest.getNumber());
			_appendParameter(
				sb, "GITHUB_ORIGIN_NAME",
				_encode(_pullRequest.getSenderUsername()));
			_appendParameter(
				sb, "GITHUB_RECEIVER_USERNAME",
				_pullRequest.getReceiverUsername());
			_appendParameter(
				sb, "GITHUB_SENDER_BRANCH_NAME",
				_pullRequest.getSenderBranchName());
			_appendParameter(
				sb, "GITHUB_SENDER_BRANCH_SHA", _pullRequest.getSenderSHA());
			_appendParameter(
				sb, "GITHUB_SENDER_USERNAME",
				_encode(_pullRequest.getSenderUsername()));
			_appendParameter(
				sb, "GITHUB_UPSTREAM_BRANCH_NAME",
				_pullRequest.getUpstreamBranchName());
			_appendParameter(
				sb, "GITHUB_UPSTREAM_BRANCH_SHA",
				_pullRequest.getUpstreamBranchSHA());
			_appendParameter(sb, "PULL_REQUEST_URL", _pullRequest.getHtmlURL());
			_appendParameter(
				sb, "REPOSITORY_NAME", _pullRequest.getGitRepositoryName());

			return sb.toString();
		}

		private void _appendParameter(
			StringBuilder sb, String name, String value) {

			if (JenkinsResultsParserUtil.isNullOrEmpty(value)) {
				return;
			}

			if (sb.length() > 0) {
				sb.append("&");
			}

			sb.append(name);
			sb.append("=");
			sb.append(value);
		}

		private String _encode(String string) {
			try {
				return URLEncoder.encode(string, "UTF-8");
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				throw new RuntimeException(
					"Unsupported Encoding: UTF-8",
					unsupportedEncodingException);
			}
		}

		private static final String _KEY_CI_FORWARD_RECEIVER_USERNAME =
			"CI_FORWARD_RECEIVER_USERNAME";

		private static final String _KEY_CI_REEVALUATE_BUILD_ID = "BUILD_ID";

		private static final String _KEY_CI_TEST_SUITE_NAME = "CI_TEST_SUITE";

		private static final String _KEY_JENKINS_ACCESS_TOKEN = "token";

		private static final String _KEY_PORTAL_BUNDLES_DIST_URL =
			"PORTAL_BUNDLES_DIST_URL";

		private static final String _KEY_PULL_REQUEST_NUMBER =
			"GITHUB_PULL_REQUEST_NUMBER";

		private PullRequest _pullRequest;

	}

}