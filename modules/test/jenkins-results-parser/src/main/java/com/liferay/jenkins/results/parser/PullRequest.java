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

	public static boolean isValidGitHubPullRequestURL(String gitHubURL) {
		Matcher matcher = _gitHubPullRequestURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public PullRequest(String gitHubURL) {
		this(gitHubURL, _TEST_SUITE_NAME_DEFAULT);
	}

	public PullRequest(String gitHubURL, String testSuiteName) {
		if ((testSuiteName == null) || testSuiteName.isEmpty()) {
			testSuiteName = _TEST_SUITE_NAME_DEFAULT;
		}

		_testSuiteName = testSuiteName;

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
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to post comment in GitHub pull request " + getURL(),
				ioe);
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
				gitHubApiUrl, jsonArray.toString());
		}
		catch (IOException ioe) {
			System.out.println("Unable to add label " + label.getName());

			ioe.printStackTrace();

			return false;
		}

		return true;
	}

	public void close() throws IOException {
		if (Objects.equals(getState(), "open")) {
			JSONObject postContentJSONObject = new JSONObject();

			postContentJSONObject.put("state", "closed");

			JenkinsResultsParserUtil.toString(
				_jsonObject.getString("url"), postContentJSONObject.toString());
		}

		_jsonObject.put("state", "closed");
	}

	public List<Comment> getComments() {
		List<Comment> comments = new ArrayList<>();

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteGitRepositoryName(), getOwnerUsername(),
			"issues/" + getNumber() + "/comments?page=");

		int page = 1;

		while (true) {
			try {
				JSONArray jsonArray = JenkinsResultsParserUtil.toJSONArray(
					gitHubApiUrl + page, false);

				if (jsonArray.length() == 0) {
					break;
				}

				for (int i = 0; i < jsonArray.length(); i++) {
					comments.add(new Comment(jsonArray.getJSONObject(i)));
				}

				page++;
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get pull request comments", ioe);
			}
		}

		return comments;
	}

	public GitHubRemoteGitCommit getGitHubRemoteGitCommit() {
		return GitCommitFactory.newGitHubRemoteGitCommit(
			getOwnerUsername(), getGitHubRemoteGitRepositoryName(),
			getSenderSHA());
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
		return _labels;
	}

	public String getLiferayRemoteBranchSHA() {
		RemoteGitBranch liferayRemoteGitBranch = getLiferayRemoteGitBranch();

		return liferayRemoteGitBranch.getSHA();
	}

	public RemoteGitBranch getLiferayRemoteGitBranch() {
		if (_liferayRemoteGitBranch == null) {
			_liferayRemoteGitBranch = GitUtil.getRemoteGitBranch(
				getUpstreamBranchName(), new File("."),
				"git@github.com:liferay/" + getGitRepositoryName());
		}

		return _liferayRemoteGitBranch;
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

	public String getReceiverUsername() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		JSONObject userJSONObject = baseJSONObject.getJSONObject("user");

		return userJSONObject.getString("login");
	}

	public String getSenderBranchName() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		return headJSONObject.getString("ref");
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

	public String getSenderUsername() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		JSONObject userJSONObject = headJSONObject.getJSONObject("user");

		return userJSONObject.getString("login");
	}

	public String getState() {
		return _jsonObject.getString("state");
	}

	public TestSuiteStatus getTestSuiteStatus() {
		return _testSuiteStatus;
	}

	public String getUpstreamBranchName() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		return baseJSONObject.getString("ref");
	}

	public String getUpstreamBranchSHA() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		return baseJSONObject.getString("sha");
	}

	public String getURL() {
		return JenkinsResultsParserUtil.getGitHubApiUrl(
			_gitHubRemoteGitRepositoryName, _ownerUsername, "pulls/" + _number);
	}

	public boolean hasLabel(String labelName) {
		for (GitHubRemoteGitRepository.Label label : _labels) {
			if (labelName.equals(label.getName())) {
				return true;
			}
		}

		return false;
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

	public void refresh() {
		try {
			_jsonObject = JenkinsResultsParserUtil.toJSONObject(
				getURL(), false);

			_labels.clear();

			JSONArray labelJSONArray = _jsonObject.getJSONArray("labels");

			for (int i = 0; i < labelJSONArray.length(); i++) {
				JSONObject labelJSONObject = labelJSONArray.getJSONObject(i);

				_labels.add(
					new GitHubRemoteGitRepository.Label(
						labelJSONObject, getGitHubRemoteGitRepository()));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
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
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to delete comment in GitHub pull request " + getURL(),
				ioe);
		}
	}

	public void removeLabel(String labelName) {
		if (!hasLabel(labelName)) {
			return;
		}

		String path = JenkinsResultsParserUtil.combine(
			"issues/", getNumber(), "/labels/", labelName);

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteGitRepositoryName(), getOwnerUsername(), path);

		try {
			JenkinsResultsParserUtil.toString(
				gitHubApiUrl, HttpRequestMethod.DELETE);

			refresh();
		}
		catch (IOException ioe) {
			System.out.println("Unable to remove label " + labelName);

			ioe.printStackTrace();
		}
	}

	public void resetAutoCloseCommentAvailable() {
		_autoCloseCommentAvailable = null;
	}

	public void setTestSuiteStatus(TestSuiteStatus testSuiteStatus) {
		setTestSuiteStatus(testSuiteStatus, null);
	}

	public void setTestSuiteStatus(
		TestSuiteStatus testSuiteStatus, String targetURL) {

		_testSuiteStatus = testSuiteStatus;

		StringBuilder sb = new StringBuilder();

		sb.append("ci:test");

		if (!_testSuiteName.equals(_TEST_SUITE_NAME_DEFAULT)) {
			sb.append(":");
			sb.append(_testSuiteName);
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

		if (testSuiteLabel == null) {
			if (gitHubRemoteGitRepository.addLabel(
					testSuiteStatus.getColor(), "", sb.toString())) {

				testSuiteLabel = gitHubRemoteGitRepository.getLabel(
					sb.toString());
			}
		}

		addLabel(testSuiteLabel);

		if (targetURL == null) {
			return;
		}

		if (testSuiteStatus == TestSuiteStatus.MISSING) {
			return;
		}

		GitHubRemoteGitCommit gitHubRemoteGitCommit =
			getGitHubRemoteGitCommit();

		GitHubRemoteGitCommit.Status status =
			GitHubRemoteGitCommit.Status.valueOf(testSuiteStatus.toString());

		String context = _TEST_SUITE_NAME_DEFAULT;

		if (!_testSuiteName.equals(_TEST_SUITE_NAME_DEFAULT)) {
			context = "liferay/ci:test:" + _testSuiteName;
		}

		sb = new StringBuilder();

		sb.append("\"ci:test");

		if (!_testSuiteName.equals(_TEST_SUITE_NAME_DEFAULT)) {
			sb.append(":");
			sb.append(_testSuiteName);
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
		body = body.replaceAll("(\\>)\\s+(\\<)", "$1$2");
		body = body.replace("&quot;", "\\&quot;");

		try {
			String editCommentURL = _jsonObject.getString("issue_url");

			editCommentURL = editCommentURL.replaceFirst(
				"issues/\\d+", "issues");

			return new Comment(
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						editCommentURL, "/comments/", id),
					false, HttpRequestMethod.PATCH));
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to update comment in GitHub pull request " + getURL(),
				ioe);
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
				return _ISO8601_UTC_DATE_FORMAT.parse(
					_commentJSONObject.getString("created_at"));
			}
			catch (ParseException pe) {
				throw new RuntimeException(
					"Unable to parse created date " +
						_commentJSONObject.getString("created_at"),
					pe);
			}
		}

		public String getId() {
			return String.valueOf(_commentJSONObject.getInt("id"));
		}

		public Date getModifiedDate() {
			try {
				return _ISO8601_UTC_DATE_FORMAT.parse(
					_commentJSONObject.getString("modified_at"));
			}
			catch (ParseException pe) {
				throw new RuntimeException(
					"Unable to parse modified date " +
						_commentJSONObject.getString("modified_at"),
					pe);
			}
		}

		private static final SimpleDateFormat _ISO8601_UTC_DATE_FORMAT;

		static {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm'Z'");

			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			_ISO8601_UTC_DATE_FORMAT = simpleDateFormat;
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

	protected String getIssueURL() {
		return _jsonObject.getString("issue_url");
	}

	protected void updateGithub() {
		JSONObject jsonObject = new JSONObject();

		List<String> labelNames = new ArrayList<>();

		for (GitHubRemoteGitRepository.Label label : _labels) {
			labelNames.add(label.getName());
		}

		jsonObject.put("labels", labelNames);

		try {
			JenkinsResultsParserUtil.toJSONObject(
				getIssueURL(), jsonObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final String _TEST_SUITE_NAME_DEFAULT = "default";

	private static final Pattern _gitHubPullRequestURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<owner>[^/]+)/",
			"(?<gitHubRemoteGitRepositoryName>[^/]+)/pull/(?<number>\\d+)"));

	private Boolean _autoCloseCommentAvailable;
	private GitHubRemoteGitRepository _gitHubRemoteGitRepository;
	private String _gitHubRemoteGitRepositoryName;
	private JSONObject _jsonObject;
	private final List<GitHubRemoteGitRepository.Label> _labels =
		new ArrayList<>();
	private RemoteGitBranch _liferayRemoteGitBranch;
	private Integer _number;
	private String _ownerUsername;
	private final String _testSuiteName;
	private TestSuiteStatus _testSuiteStatus = TestSuiteStatus.MISSING;

}