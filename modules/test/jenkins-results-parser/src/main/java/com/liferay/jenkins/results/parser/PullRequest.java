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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PullRequest {

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

		_number = Integer.parseInt(matcher.group("number"));
		_repositoryName = matcher.group("repository");
		_ownerUsername = matcher.group("owner");

		refresh();
	}

	public void addLabel(Label... labels) {
		boolean addedLabel = false;

		for (Label label : labels) {
			if (_labels.contains(label)) {
				continue;
			}

			_labels.add(label);

			addedLabel = true;
		}

		if (addedLabel) {
			updateGithub();
		}
	}

	public String getHtmlURL() {
		return _jsonObject.getString("html_url");
	}

	public List<Label> getLabels() {
		return _labels;
	}

	public String getLabelsURL() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		JSONObject repoJSONObject = baseJSONObject.getJSONObject("repo");

		String labelsURL = repoJSONObject.getString("labels_url");

		return StringUtils.replace(labelsURL, "{/name}", "");
	}

	public String getNumber() {
		return String.valueOf(_number);
	}

	public String getOwnerUsername() {
		return _ownerUsername;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getSenderBranchName() {
		JSONObject headJSONObject = _jsonObject.getJSONObject("head");

		return headJSONObject.getString("ref");
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

	public TestSuiteStatus getTestSuiteStatus() {
		return _testSuiteStatus;
	}

	public String getUpstreamBranchName() {
		JSONObject baseJSONObject = _jsonObject.getJSONObject("base");

		return baseJSONObject.getString("ref");
	}

	public void refresh() {
		try {
			_jsonObject = JenkinsResultsParserUtil.toJSONObject(getURL());

			_labels.clear();

			JSONArray labelJSONArray = _jsonObject.getJSONArray("labels");

			for (int i = 0; i < labelJSONArray.length(); i++) {
				JSONObject labelJSONObject = labelJSONArray.getJSONObject(i);

				_labels.add(LabelFactory.newLabel(labelJSONObject));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void setTestSuiteStatus(TestSuiteStatus testSuiteStatus) {
		_testSuiteStatus = testSuiteStatus;

		removeTestSuiteLabels();

		StringBuilder sb = new StringBuilder();

		sb.append("ci:test");

		if (!_testSuiteName.equals(_TEST_SUITE_NAME_DEFAULT)) {
			sb.append(":");
			sb.append(_testSuiteName);
		}

		sb.append(" - ");
		sb.append(StringUtils.lowerCase(testSuiteStatus.toString()));

		addLabel(
			LabelFactory.newLabel(
				getLabelsURL(), sb.toString(), testSuiteStatus.getColor()));
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

	protected String getURL() {
		return JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", _ownerUsername, "/",
			_repositoryName, "/pulls/", _number.toString());
	}

	protected void removeTestSuiteLabels() {
		String testSuiteStatusLowerCase = StringUtils.lowerCase(
			_testSuiteStatus.toString());

		List<Label> testSuiteLabels = new ArrayList<>();

		for (Label label : _labels) {
			String labelName = label.getName();

			Matcher matcher = _testSuiteLabelNamePattern.matcher(labelName);

			if (!matcher.find()) {
				continue;
			}

			String testSuiteName = matcher.group("testSuiteName");

			if (testSuiteName == null) {
				testSuiteName = _TEST_SUITE_NAME_DEFAULT;
			}

			if (!testSuiteName.equals(_testSuiteName)) {
				continue;
			}

			if (testSuiteStatusLowerCase.equals(
					matcher.group("testSuiteStatus"))) {

				continue;
			}

			testSuiteLabels.add(label);
		}

		if (testSuiteLabels.isEmpty()) {
			return;
		}

		for (Label testSuiteLabel : testSuiteLabels) {
			_labels.remove(testSuiteLabel);
		}

		updateGithub();
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
		"https://github.com/(?<owner>[^/]+)/(?<repository>[^/]+)/pull/" +
			"(?<number>\\d+)");
	private static final Pattern _testSuiteLabelNamePattern = Pattern.compile(
		"ci:test(:(?<testSuiteName>[^\\s]+))? - (?<testSuiteStatus>[^\\s]+)");

	private JSONObject _jsonObject;
	private final List<Label> _labels = new ArrayList<>();
	private Integer _number;
	private String _ownerUsername;
	private String _repositoryName;
	private final String _testSuiteName;
	private TestSuiteStatus _testSuiteStatus = TestSuiteStatus.MISSING;

}