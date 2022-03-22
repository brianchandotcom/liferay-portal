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

import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PullRequest {

	public static String getURL(
		String username, String repositoryName, String pullRequestNumber) {

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(username);
		sb.append("/");
		sb.append(repositoryName);
		sb.append("/pull/");
		sb.append(pullRequestNumber);

		return sb.toString();
	}

	public static boolean isValidGitHubPullRequestURL(String gitHubURL) {
		Matcher matcher = _gitHubPullRequestURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public Comment addComment(String body) {
		body = body.replaceAll("(\\>)\\s+(\\<)", "$1$2");
		body = body.replace("&quot;", "\\&quot;");

		JSONObject dataJSONObject = new JSONObject();

		dataJSONObject.put("body", body);

		try {
			JSONObject responseJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						_jsonObject.getString("issue_url"), "/comments"),
					dataJSONObject.toString());

			return new Comment(responseJSONObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to post comment in GitHub pull request " + getURL(),
				ioException);
		}
	}

	public boolean addLabel(GitHubRemoteGitRepository.Label label) {
		if ((label == null) || hasLabel(label.getName())) {
			return true;
		}

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			getGitHubRemoteGitRepository();

		GitHubRemoteGitRepository.Label gitRepositoryLabel =
			gitHubRemoteGitRepository.getLabel(label.getName());

		if (gitRepositoryLabel == null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"GitHubRemoteGitRepository.Label ", label.getName(),
					" does not exist in ", getGitHubRemoteGitRepositoryName()));

			return false;
		}

		JSONArray jsonArray = new JSONArray();

		jsonArray.put(label.getName());

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteGitRepositoryName(), getOwnerUsername(),
			"issues/" + getNumber() + "/labels");

		try {
			JenkinsResultsParserUtil.toString(
				gitHubApiUrl, false, HttpRequestMethod.POST,
				jsonArray.toString());
		}
		catch (IOException ioException) {
			System.out.println("Unable to add label " + label.getName());

			ioException.printStackTrace();

			return false;
		}

		return true;
	}

	public void close() {
		if (Objects.equals(getState(), "open")) {
			JSONObject postContentJSONObject = new JSONObject();

			postContentJSONObject.put("state", "closed");

			try {
				JenkinsResultsParserUtil.toString(
					_jsonObject.getString("url"), false, HttpRequestMethod.POST,
					postContentJSONObject.toString());
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to close pull request " + getHtmlURL(),
					ioException);
			}
		}

		_jsonObject.put("state", "closed");
	}

	public String getCIMergeSHA() {
		getFileNames();

		return _ciMergeSHA;
	}

	public String getCIMergeSubrepo() {
		for (String fileName : getFileNames()) {
			if (fileName.endsWith("/ci-merge")) {
				return fileName.replace("/ci-merge", "");
			}
		}

		throw new IllegalStateException("Unable to find ci-merge file");
	}

	public List<Comment> getComments() {
		if (_comments != null) {
			return _comments;
		}

		_comments = new ArrayList<>();

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteGitRepositoryName(), getOwnerUsername(),
			"issues/" + getNumber() + "/comments?per_page=100&page=");

		for (int pageNumber = 1;
			 pageNumber <=
				 JenkinsResultsParserUtil.PAGES_GITHUB_API_PAGES_SIZE_MAX;
			 pageNumber++) {

			try {
				JSONArray commentJSONArray =
					JenkinsResultsParserUtil.toJSONArray(
						gitHubApiUrl + pageNumber, false);

				if (commentJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < commentJSONArray.length(); i++) {
					_comments.add(
						new Comment(commentJSONArray.getJSONObject(i)));
				}

				if (commentJSONArray.length() < JenkinsResultsParserUtil.
						PER_PAGE_GITHUB_API_PAGES_SIZE_MAX) {

					break;
				}

				if (pageNumber ==
						JenkinsResultsParserUtil.
							PAGES_GITHUB_API_PAGES_SIZE_MAX) {

					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Too many comments (>",
							String.valueOf(_gitHubRemoteGitCommits.size()),
							") found for ", "pull request ", getHtmlURL()));
				}
			}
			catch (IOException ioException) {
				_comments = null;

				throw new RuntimeException(
					"Unable to get pull request comments", ioException);
			}
		}

		return _comments;
	}

	public String getCommonParentSHA() {
		if (_commonParentSHA == null) {
			_initCommits();
		}

		return _commonParentSHA;
	}

	public List<String> getFileNames() {
		if (!_fileNames.isEmpty()) {
			return _fileNames;
		}

		_ciMergeSHA = "";

		String filesURL = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", getReceiverUsername(), "/",
			getGitHubRemoteGitRepositoryName(), "/pulls/", getNumber(),
			"/files");

		try {
			JSONArray filesJSONArray = JenkinsResultsParserUtil.toJSONArray(
				filesURL, false);

			for (int j = 0; j < filesJSONArray.length(); j++) {
				JSONObject fileJSONObject = filesJSONArray.getJSONObject(j);

				String fileName = fileJSONObject.getString("filename");

				_fileNames.add(fileName);

				if (fileName.endsWith("/ci-merge")) {
					String patch = fileJSONObject.getString("patch");

					Matcher matcher = _ciMergeSHAPattern.matcher(patch);

					if (matcher.find()) {
						String sha = matcher.group(1);

						if (!matcher.find()) {
							_ciMergeSHA = sha;
						}
					}
				}
			}

			return _fileNames;
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get pull request file names", ioException);
		}
	}

	public List<GitHubRemoteGitCommit> getGitHubRemoteCommits() {
		if (_gitHubRemoteGitCommits == null) {
			_initCommits();
		}

		return _gitHubRemoteGitCommits;
	}

	public GitHubRemoteGitCommit getGitHubRemoteGitCommit() {
		if (_gitHubRemoteGitCommits == null) {
			_initCommits();
		}

		return _gitHubRemoteGitCommits.get(_gitHubRemoteGitCommits.size() - 1);
	}

	public GitHubRemoteGitRepository getGitHubRemoteGitRepository() {
		if (_gitHubRemoteGitRepository == null) {
			_gitHubRemoteGitRepository =
				(GitHubRemoteGitRepository)
					GitRepositoryFactory.getRemoteGitRepository(
						"github.com", _gitHubRemoteGitRepositoryName,
						getOwnerUsername());
		}

		return _gitHubRemoteGitRepository;
	}

	public String getGitHubRemoteGitRepositoryName() {
		return _gitHubRemoteGitRepositoryName;
	}

	public String getGitRepositoryName() {
		return getGitHubRemoteGitRepositoryName();
	}

	public String getHtmlURL() {
		return _jsonObject.getString("html_url");
	}

	public String getJSON() {
		return _jsonObject.toString(4);
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public List<GitHubRemoteGitRepository.Label> getLabels() {
		if (_labels == null) {
			_refreshJSONObject();

			JSONArray labelJSONArray = _jsonObject.getJSONArray("labels");

			_labels = new ArrayList<>(labelJSONArray.length());

			for (int i = 0; i < labelJSONArray.length(); i++) {
				JSONObject labelJSONObject = labelJSONArray.getJSONObject(i);

				_labels.add(
					new GitHubRemoteGitRepository.Label(
						labelJSONObject, getGitHubRemoteGitRepository()));
			}
		}

		return _labels;
	}

	public String getLocalSenderBranchName() {
		return JenkinsResultsParserUtil.combine(
			getSenderUsername(), "-", getNumber(), "-", getSenderBranchName());
	}

	public String getNumber() {
		return String.valueOf(_number);
	}

	public String getOwnerUsername() {
		return _ownerUsername;
	}

	public List<String> getPassingTestSuites() {
		List<String> testSuiteNames = new ArrayList<>();

		JSONArray statusesJSONArray = getSenderSHAStatusesJSONArray();

		for (int i = 0; i < statusesJSONArray.length(); i++) {
			JSONObject jsonObject = statusesJSONArray.getJSONObject(i);

			Matcher matcher = _liferayContextPattern.matcher(
				jsonObject.getString("context"));

			if (!matcher.find()) {
				continue;
			}

			String testSuiteName = matcher.group("testSuiteName");

			if (testSuiteNames.contains(testSuiteName) ||
				!Objects.equals("success", jsonObject.getString("state"))) {

				continue;
			}

			testSuiteNames.add(testSuiteName);
		}

		return testSuiteNames;
	}

	public String getReceiverUsername() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		JSONObject userJSONObject = baseJSONObject.getJSONObject("user");

		return userJSONObject.getString("login");
	}

	public String getSenderBranchName() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		return headJSONObject.getString("ref");
	}

	public RemoteGitBranch getSenderRemoteGitBranch() {
		if (_senderRemoteGitBranch == null) {
			_senderRemoteGitBranch = GitUtil.getRemoteGitBranch(
				getSenderBranchName(), new File(""), getSenderRemoteURL());
		}

		return _senderRemoteGitBranch;
	}

	public String getSenderRemoteURL() {
		return JenkinsResultsParserUtil.combine(
			"git@github.com:", getSenderUsername(), "/",
			getGitHubRemoteGitRepositoryName());
	}

	public String getSenderSHA() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		return headJSONObject.getString("sha");
	}

	public JSONArray getSenderSHAStatusesJSONArray() {
		JSONArray statusesJSONArray = null;

		try {
			statusesJSONArray = JenkinsResultsParserUtil.toJSONArray(
				_jsonObject.getString("statuses_url"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return statusesJSONArray;
	}

	public JSONObject getSenderSHAStatusJSONObject() {
		JSONObject statusJSONObject = null;

		try {
			String statusURL = _jsonObject.getString("statuses_url");

			statusURL = statusURL.replace("statuses", "status");

			statusJSONObject = JenkinsResultsParserUtil.toJSONObject(statusURL);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return statusJSONObject;
	}

	public String getSenderUsername() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		JSONObject userJSONObject = headJSONObject.getJSONObject("user");

		return userJSONObject.getString("login");
	}

	public String getState() {
		return _jsonObject.getString("state");
	}

	public List<String> getStatusDescriptions() {
		GitHubRemoteGitCommit gitHubRemoteGitCommit =
			getGitHubRemoteGitCommit();

		return gitHubRemoteGitCommit.getStatusDescriptions();
	}

	public String getTitle() {
		return _jsonObject.getString("title");
	}

	public String getUpstreamBranchSHA() {
		RemoteGitBranch upstreamRemoteGitBranch = getUpstreamRemoteGitBranch();

		return upstreamRemoteGitBranch.getSHA();
	}

	public RemoteGitBranch getUpstreamRemoteGitBranch() {
		if (_liferayRemoteGitBranch == null) {
			_liferayRemoteGitBranch = GitUtil.getRemoteGitBranch(
				getUpstreamRemoteGitBranchName(), new File("."),
				"git@github.com:liferay/" + getGitRepositoryName());
		}

		return _liferayRemoteGitBranch;
	}

	public String getUpstreamRemoteGitBranchName() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		return baseJSONObject.getString("ref");
	}

	public String getURL() {
		return JenkinsResultsParserUtil.getGitHubApiUrl(
			_gitHubRemoteGitRepositoryName, _ownerUsername, "pulls/" + _number);
	}

	public boolean hasLabel(String labelName) {
		for (GitHubRemoteGitRepository.Label label : getLabels()) {
			if (labelName.equals(label.getName())) {
				return true;
			}
		}

		return false;
	}

	public boolean hasRequiredPassingTestSuites() {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String requiredPassingSuites = JenkinsResultsParserUtil.getProperty(
			buildProperties, "pull.request.forward.required.passing.suites",
			getGitRepositoryName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(requiredPassingSuites)) {
			return true;
		}

		List<String> passingTestSuites = getPassingTestSuites();

		for (String requiredPassingSuite : requiredPassingSuites.split(",")) {
			if (!passingTestSuites.contains(requiredPassingSuite)) {
				return false;
			}
		}

		return true;
	}

	public boolean isAutoCloseCommentAvailable() {
		if (_autoCloseCommentAvailable != null) {
			return _autoCloseCommentAvailable;
		}

		List<Comment> comments = getComments();

		for (Comment comment : comments) {
			String commentBody = comment.getBody();

			if (commentBody.contains("auto-close=\"false\"")) {
				_autoCloseCommentAvailable = true;

				return _autoCloseCommentAvailable;
			}
		}

		_autoCloseCommentAvailable = false;

		return _autoCloseCommentAvailable;
	}

	public boolean isMergeSubrepoRequest() {
		for (String filename : getFileNames()) {
			if (filename.endsWith("/ci-merge")) {
				return true;
			}
		}

		return false;
	}

	public boolean isValidCIMergeFile() {
		List<String> fileNames = getFileNames();

		if ((fileNames.size() == 1) && isMergeSubrepoRequest()) {
			return true;
		}

		return false;
	}

	public void lock() {
		try {
			JenkinsResultsParserUtil.toString(
				getIssueURL() + "/lock", false, HttpRequestMethod.PUT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to lock pull request " + getHtmlURL(), ioException);
		}
	}

	public void refresh() {
		_comments = null;
		_commonParentSHA = null;
		_gitHubRemoteGitCommits = null;
		_labels = null;

		getLabels();
	}

	public void removeComment(Comment comment) {
		removeComment(comment.getId());
	}

	public void removeComment(String id) {
		String editCommentURL = _jsonObject.getString("issue_url");

		editCommentURL = editCommentURL.replaceFirst("issues/\\d+", "issues");

		try {
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.combine(
					editCommentURL, "/comments/", id),
				false, HttpRequestMethod.DELETE);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to delete comment in GitHub pull request " + getURL(),
				ioException);
		}
	}

	public void removeLabel(String labelName) {
		if (!hasLabel(labelName)) {
			return;
		}

		String path = JenkinsResultsParserUtil.combine(
			"issues/", getNumber(), "/labels/",
			JenkinsResultsParserUtil.fixURL(labelName));

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteGitRepositoryName(), getOwnerUsername(), path);

		try {
			JenkinsResultsParserUtil.toString(
				gitHubApiUrl, false, HttpRequestMethod.DELETE);

			_labels = null;
		}
		catch (IOException ioException) {
			System.out.println("Unable to remove label " + labelName);

			ioException.printStackTrace();
		}
	}

	public void resetAutoCloseCommentAvailable() {
		_autoCloseCommentAvailable = null;
	}

	public void setTestSuiteStatus(
		String testSuiteName, TestSuiteStatus testSuiteStatus) {

		setTestSuiteStatus(testSuiteName, testSuiteStatus, null);
	}

	public void setTestSuiteStatus(
		String testSuiteName, TestSuiteStatus testSuiteStatus,
		String targetURL) {

		setTestSuiteStatus(testSuiteName, testSuiteStatus, targetURL, null);
	}

	public void setTestSuiteStatus(
		String testSuiteName, TestSuiteStatus testSuiteStatus, String targetURL,
		String senderSHA) {

		StringBuilder sb = new StringBuilder();

		sb.append("ci:test");

		if (!testSuiteName.equals(_NAME_TEST_SUITE_DEFAULT)) {
			sb.append(":");
			sb.append(testSuiteName);
		}

		sb.append(" ");

		String testSuiteLabelPrefix = sb.toString();

		List<String> oldLabelNames = new ArrayList<>();

		for (GitHubRemoteGitRepository.Label label : getLabels()) {
			String name = label.getName();

			if (name.startsWith(testSuiteLabelPrefix)) {
				oldLabelNames.add(label.getName());
			}
		}

		for (String oldLabelName : oldLabelNames) {
			removeLabel(oldLabelName);
		}

		sb.append(" - ");
		sb.append(StringUtils.lowerCase(testSuiteStatus.toString()));

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			getGitHubRemoteGitRepository();

		GitHubRemoteGitRepository.Label testSuiteLabel =
			gitHubRemoteGitRepository.getLabel(sb.toString());

		if ((testSuiteLabel == null) &&
			gitHubRemoteGitRepository.addLabel(
				testSuiteStatus.getColor(), "", sb.toString())) {

			testSuiteLabel = gitHubRemoteGitRepository.getLabel(sb.toString());
		}

		addLabel(testSuiteLabel);

		if ((targetURL == null) ||
			(testSuiteStatus == TestSuiteStatus.MISSING)) {

			return;
		}

		GitHubRemoteGitCommit gitHubRemoteGitCommit =
			getGitHubRemoteGitCommit();

		if ((senderSHA != null) && senderSHA.matches("[0-9a-f]{7,40}")) {
			gitHubRemoteGitCommit = GitCommitFactory.newGitHubRemoteGitCommit(
				getOwnerUsername(), getGitHubRemoteGitRepositoryName(),
				senderSHA);
		}

		GitHubRemoteGitCommit.Status status =
			GitHubRemoteGitCommit.Status.valueOf(testSuiteStatus.toString());

		String context = _NAME_TEST_SUITE_DEFAULT;

		if (!testSuiteName.equals(_NAME_TEST_SUITE_DEFAULT)) {
			context = "liferay/ci:test:" + testSuiteName;
		}

		sb = new StringBuilder();

		sb.append("\"ci:test");

		if (!testSuiteName.equals(_NAME_TEST_SUITE_DEFAULT)) {
			sb.append(":");
			sb.append(testSuiteName);
		}

		sb.append("\"");

		if ((testSuiteStatus == TestSuiteStatus.ERROR) ||
			(testSuiteStatus == TestSuiteStatus.FAILURE)) {

			sb.append(" has FAILED.");
		}
		else if (testSuiteStatus == TestSuiteStatus.PENDING) {
			sb.append(" is running.");
		}
		else if (testSuiteStatus == TestSuiteStatus.SUCCESS) {
			sb.append(" has PASSED.");
		}

		gitHubRemoteGitCommit.setStatus(
			status, context, sb.toString(), targetURL);
	}

	public Comment updateComment(Comment comment) {
		return updateComment(comment.getBody(), comment.getId());
	}

	public Comment updateComment(String body, String id) {
		JSONObject jsonObject = new JSONObject();

		body = body.replaceAll("(\\>)\\s+(\\<)", "$1$2");
		body = body.replace("&quot;", "\\&quot;");

		jsonObject.put("body", body);

		try {
			String editCommentURL = _jsonObject.getString("issue_url");

			editCommentURL = editCommentURL.replaceFirst(
				"issues/\\d+", "issues");

			return new Comment(
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						editCommentURL, "/comments/", id),
					false, HttpRequestMethod.PATCH, jsonObject.toString()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to update comment in GitHub pull request " + getURL(),
				ioException);
		}
	}

	public static class Comment {

		public Comment(JSONObject commentJSONObject) {
			_commentJSONObject = commentJSONObject;
		}

		public String getBody() {
			return _commentJSONObject.getString("body");
		}

		public Date getCreatedDate() {
			try {
				return _UtcIso8601SimpleDateFormat.parse(
					_commentJSONObject.getString("created_at"));
			}
			catch (ParseException parseException) {
				throw new RuntimeException(
					"Unable to parse created date " +
						_commentJSONObject.getString("created_at"),
					parseException);
			}
		}

		public String getId() {
			return String.valueOf(_commentJSONObject.getInt("id"));
		}

		public Date getModifiedDate() {
			try {
				return _UtcIso8601SimpleDateFormat.parse(
					_commentJSONObject.getString("modified_at"));
			}
			catch (ParseException parseException) {
				throw new RuntimeException(
					"Unable to parse modified date " +
						_commentJSONObject.getString("modified_at"),
					parseException);
			}
		}

		public String getUserLogin() {
			JSONObject userJSONObject = _commentJSONObject.getJSONObject(
				"user");

			return userJSONObject.getString("login");
		}

		private static final SimpleDateFormat _UtcIso8601SimpleDateFormat;

		static {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			_UtcIso8601SimpleDateFormat = simpleDateFormat;
		}

		private final JSONObject _commentJSONObject;

	}

	public static enum TestSuiteStatus {

		ERROR("fccdcc"), FAILURE("fccdcc"), MISSING("eeeeee"),
		PENDING("fff4c9"), SUCCESS("c7e8cb");

		public String getColor() {
			return _color;
		}

		private TestSuiteStatus(String color) {
			_color = color;
		}

		private final String _color;

	}

	protected PullRequest(JSONObject jsonObject) {
		JSONObject baseJSONObject = jsonObject.getJSONObject("base");

		JSONObject repoJSONObject = baseJSONObject.getJSONObject("repo");

		_gitHubRemoteGitRepositoryName = repoJSONObject.getString("name");

		_number = jsonObject.getInt("number");

		JSONObject ownerJSONObject = repoJSONObject.getJSONObject("owner");

		_ownerUsername = ownerJSONObject.getString("login");

		_jsonObject = jsonObject;
	}

	protected PullRequest(String gitHubURL) {
		Matcher matcher = _gitHubPullRequestURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		_gitHubRemoteGitRepositoryName = matcher.group(
			"gitHubRemoteGitRepositoryName");
		_number = Integer.parseInt(matcher.group("number"));
		_ownerUsername = matcher.group("owner");

		refresh();
	}

	protected String getIssueURL() {
		return _jsonObject.getString("issue_url");
	}

	protected void updateGithub() {
		JSONObject jsonObject = new JSONObject();

		List<String> labelNames = new ArrayList<>();

		for (GitHubRemoteGitRepository.Label label : getLabels()) {
			labelNames.add(label.getName());
		}

		jsonObject.put("labels", labelNames);

		try {
			JenkinsResultsParserUtil.toJSONObject(
				getIssueURL(), jsonObject.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _initCommits() {
		String commitsURL = _jsonObject.getString("commits_url");

		_gitHubRemoteGitCommits = new ArrayList<>();

		try {
			for (int pageNumber = 1;
				 pageNumber <=
					 JenkinsResultsParserUtil.PAGES_GITHUB_API_PAGES_SIZE_MAX;
				 pageNumber++) {

				JSONArray commitsJSONArray =
					JenkinsResultsParserUtil.toJSONArray(
						JenkinsResultsParserUtil.combine(
							commitsURL, "?per_page=100&page=",
							String.valueOf(pageNumber)));

				if (commitsJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < commitsJSONArray.length(); i++) {
					JSONObject commitJSONObject =
						commitsJSONArray.getJSONObject(i);

					_gitHubRemoteGitCommits.add(
						GitCommitFactory.newGitHubRemoteGitCommit(
							getOwnerUsername(), getGitRepositoryName(),
							commitJSONObject.getString("sha"),
							commitJSONObject));
				}

				if (pageNumber == 1) {
					JSONObject firstCommitJSONObject =
						commitsJSONArray.getJSONObject(0);

					JSONArray parentsJSONArray =
						firstCommitJSONObject.getJSONArray("parents");

					JSONObject firstParentJSONObject =
						parentsJSONArray.getJSONObject(0);

					_commonParentSHA = firstParentJSONObject.getString("sha");
				}

				if (commitsJSONArray.length() < JenkinsResultsParserUtil.
						PER_PAGE_GITHUB_API_PAGES_SIZE_MAX) {

					break;
				}

				if (pageNumber ==
						JenkinsResultsParserUtil.
							PAGES_GITHUB_API_PAGES_SIZE_MAX) {

					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Too many GitHub remote commits (>",
							String.valueOf(_gitHubRemoteGitCommits.size()),
							") found for ", "pull request ", getHtmlURL()));
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get GitHub remote commits for pull request " +
					getHtmlURL(),
				ioException);
		}
	}

	private void _refreshJSONObject() {
		try {
			_jsonObject = JenkinsResultsParserUtil.toJSONObject(
				getURL(), false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _NAME_TEST_SUITE_DEFAULT = "default";

	private static final Pattern _ciMergeSHAPattern = Pattern.compile(
		"\\+([0-9a-f]{40})");
	private static final Pattern _gitHubPullRequestURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<owner>[^/]+)/",
			"(?<gitHubRemoteGitRepositoryName>[^/]+)/pull/(?<number>\\d+)"));
	private static final Pattern _liferayContextPattern = Pattern.compile(
		"liferay/ci:test:(?<testSuiteName>[^:]+)");

	private Boolean _autoCloseCommentAvailable;
	private String _ciMergeSHA = "";
	private List<Comment> _comments;
	private String _commonParentSHA;
	private final List<String> _fileNames = new ArrayList<>();
	private List<GitHubRemoteGitCommit> _gitHubRemoteGitCommits;
	private GitHubRemoteGitRepository _gitHubRemoteGitRepository;
	private final String _gitHubRemoteGitRepositoryName;
	private JSONObject _jsonObject;
	private List<GitHubRemoteGitRepository.Label> _labels;
	private RemoteGitBranch _liferayRemoteGitBranch;
	private final Integer _number;
	private final String _ownerUsername;
	private RemoteGitBranch _senderRemoteGitBranch;

}