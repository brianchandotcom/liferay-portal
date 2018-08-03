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

import com.liferay.jenkins.results.parser.GitHubRemoteRepository.Label;
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

	public static boolean isValidHtmlURL(String htmlURL) {
		Matcher matcher = _htmlURLPattern.matcher(htmlURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public PullRequest(String htmlURL) {
		this(htmlURL, _TEST_SUITE_NAME_DEFAULT);
	}

	public PullRequest(String htmlURL, String testSuiteName) {
		if ((testSuiteName == null) || testSuiteName.isEmpty()) {
			testSuiteName = _TEST_SUITE_NAME_DEFAULT;
		}

		_testSuiteName = testSuiteName;

		Matcher matcher = _htmlURLPattern.matcher(htmlURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + htmlURL);
		}

		_gitHubRemoteRepositoryName = matcher.group(
			"gitHubRemoteRepositoryName");
		_number = Integer.parseInt(matcher.group("number"));
		_ownerUsername = matcher.group("owner");

		refresh();
	}

	public Comment addComment(String body) {
		body = body.replaceAll("(\\>)\\s+(\\<)", "$1$2");

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
				"Unable to post comment in GitHub pull request " +
					getURL(),
				ioe);
		}
	}

	public boolean addLabel(Label label) {
		if ((label == null) || hasLabel(label.getName())) {
			return true;
		}

		GitHubRemoteRepository gitHubRemoteRepository =
			getGitHubRemoteRepository();

		Label repositoryLabel = gitHubRemoteRepository.getLabel(
			label.getName());

		if (repositoryLabel == null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Label ", label.getName(), " does not exist in ",
					getGitHubRemoteRepositoryName()));

			return false;
		}

		JSONArray jsonArray = new JSONArray();

		jsonArray.put(label.getName());

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteRepositoryName(), getOwnerUsername(),
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
			getGitHubRemoteRepositoryName(), getOwnerUsername(),
			"issues/" + getNumber() + "/comments?page=");

		int page = 1;

		while (true) {
			try {
				JSONArray jsonArray = JenkinsResultsParserUtil.toJSONArray(
					gitHubApiUrl + page);

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

	public Commit getCommit() {
		return CommitFactory.newCommit(
			getOwnerUsername(), getGitHubRemoteRepositoryName(),
			getSenderSHA());
	}

	public GitHubRemoteRepository getGitHubRemoteRepository() {
		if (_gitHubRemoteRepository == null) {
			_gitHubRemoteRepository =
				(GitHubRemoteRepository)RepositoryFactory.getRemoteRepository(
					"github.com", _gitHubRemoteRepositoryName,
					getOwnerUsername());
		}

		return _gitHubRemoteRepository;
	}

	public String getGitHubRemoteRepositoryName() {
		return _gitHubRemoteRepositoryName;
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

	public List<Label> getLabels() {
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
				"git@github.com/liferay/" + getRepositoryName());
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

	public String getRepositoryName() {
		return getGitHubRemoteRepositoryName();
	}

	public String getSenderBranchName() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		return headJSONObject.getString("ref");
	}

	public String getSenderRemoteURL() {
		return JenkinsResultsParserUtil.combine(
			"git@github.com:", getSenderUsername(), "/",
			getGitHubRemoteRepositoryName());
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
			_gitHubRemoteRepositoryName, _ownerUsername, "pulls/" + _number);
	}

	public boolean hasLabel(String labelName) {
		for (Label label : _labels) {
			if (labelName.equals(label.getName())) {
				return true;
			}
		}

		return false;
	}

	public boolean isAutoCloseCommentAvailable() {
		List<Comment> comments = getComments();

		for (Comment comment : comments) {
			String commentBody = comment.getBody();

			if (commentBody.contains("auto-close=\"false\"")) {
				return true;
			}
		}

		return false;
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
					new Label(labelJSONObject, getGitHubRemoteRepository()));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void removeLabel(String labelName) {
		if (!hasLabel(labelName)) {
			return;
		}

		String path = JenkinsResultsParserUtil.combine(
			"issues/", getNumber(), "/labels/", labelName);

		String gitHubApiUrl = JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitHubRemoteRepositoryName(), getOwnerUsername(), path);

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

		String testSuiteLabelPrefix = sb.toString();

		List<String> oldLabelNames = new ArrayList<>();

		for (Label label : getLabels()) {
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

		GitHubRemoteRepository gitHubRemoteRepository =
			getGitHubRemoteRepository();

		Label testSuiteLabel = gitHubRemoteRepository.getLabel(sb.toString());

		if (testSuiteLabel == null) {
			if (gitHubRemoteRepository.addLabel(
					testSuiteStatus.getColor(), "", sb.toString())) {

				testSuiteLabel = gitHubRemoteRepository.getLabel(sb.toString());
			}
		}

		addLabel(testSuiteLabel);

		if (targetURL == null) {
			return;
		}

		if (testSuiteStatus == TestSuiteStatus.MISSING) {
			return;
		}

		Commit commit = getCommit();

		Commit.Status status = Commit.Status.valueOf(
			testSuiteStatus.toString());

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

		commit.setStatus(status, context, sb.toString(), targetURL);
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

		for (Label label : _labels) {
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

	private static final Pattern _htmlURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<owner>[^/]+)/",
			"(?<gitHubRemoteRepositoryName>[^/]+)/pull/(?<number>\\d+)"));

	private GitHubRemoteRepository _gitHubRemoteRepository;
	private String _gitHubRemoteRepositoryName;
	private JSONObject _jsonObject;
	private final List<Label> _labels = new ArrayList<>();
	private RemoteGitBranch _liferayRemoteGitBranch;
	private Integer _number;
	private String _ownerUsername;
	private final String _testSuiteName;
	private TestSuiteStatus _testSuiteStatus = TestSuiteStatus.MISSING;

}