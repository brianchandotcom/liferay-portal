/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.BuildReportFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class Testray1TestrayCaseResult extends TestrayCaseResult {

	@Override
	public String getCaseID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.optString("testrayCaseId");
	}

	@Override
	public String getComponentName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("testrayComponentName");
	}

	@Override
	public String getErrors() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.optString("errors");
	}

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.optLong("testrayCaseResultId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.optString("testrayCaseName");
	}

	@Override
	public int getPriority() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getPriority();
	}

	@Override
	public String getPullRequestAuthor() {
		if (!JenkinsResultsParserUtil.isNullOrEmpty(_pullRequestAuthor)) {
			return _pullRequestAuthor;
		}

		URL topLevelBuildURL = _getTopLevelBuildURL();

		if (topLevelBuildURL == null) {
			_pullRequestAuthor = "Unknown";

			return _pullRequestAuthor;
		}

		try {
			TopLevelBuildReport topLevelBuildReport =
				BuildReportFactory.newTopLevelBuildReport(topLevelBuildURL);

			Map<String, String> buildParameters =
				topLevelBuildReport.getBuildParameters();

			_pullRequestAuthor = buildParameters.get("GITHUB_SENDER_USERNAME");
		}
		catch (Exception exception) {
			exception.printStackTrace();

			_pullRequestAuthor = "Unknown";
		}

		return _pullRequestAuthor;
	}

	@Override
	public Status getStatus() {
		JSONObject jsonObject = getJSONObject();

		int statusID = jsonObject.optInt("status");

		return Status.get(statusID);
	}

	@Override
	public String getSubcomponentNames() {
		return "";
	}

	@Override
	public String getTeamName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("testrayTeamName");
	}

	@Override
	public TestrayCase getTestrayCase() {
		if (_testrayCase != null) {
			return _testrayCase;
		}

		TestrayServer testrayServer = getTestrayServer();

		String testrayCaseURLPath = JenkinsResultsParserUtil.combine(
			"/home/-/testray/cases/", getCaseID(), ".json");

		try {
			_testrayCase = TestrayFactory.newTestrayCase(
				getTestrayProject(),
				new JSONObject(testrayServer.requestGet(testrayCaseURLPath)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayCase;
	}

	@Override
	public List<TestrayCaseResult> getTestrayCaseResultHistory(int maxCount) {
		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestGet(
					"/home/-/testray/case_results/" + getID() +
						"/history.json"));

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				testrayCaseResults.add(
					TestrayFactory.newTestrayCaseResult(
						testrayServer, dataJSONArray.getJSONObject(i)));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testrayCaseResults;
	}

	@Override
	public String getType() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getType();
	}

	@Override
	public URL getURL() {
		TestrayServer testrayServer = getTestrayServer();

		try {
			return new URL(
				testrayServer.getURL(),
				"home/-/testray/case_results/" + getID());
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
	public String[] getWarnings() {
		JSONObject jsonObject = getJSONObject();

		JSONArray jsonArray = jsonObject.optJSONArray("warnings");

		if (jsonArray == null) {
			return null;
		}

		String[] warnings = new String[jsonArray.length()];

		for (int i = 0; i < warnings.length; i++) {
			warnings[i] = jsonArray.optString(i);
		}

		return warnings;
	}

	protected Testray1TestrayCaseResult(
		TestrayBuild testrayBuild, JSONObject jsonObject) {

		super(testrayBuild, jsonObject);
	}

	protected Testray1TestrayCaseResult(
		TestrayServer testrayServer, JSONObject jsonObject) {

		super(testrayServer, jsonObject);
	}

	@Override
	protected synchronized void initTestrayAttachments() {
		if (testrayAttachments != null) {
			return;
		}

		testrayAttachments = new TreeMap<>();

		JSONObject jsonObject = getJSONObject();

		JSONObject attachmentsJSONObject = jsonObject.optJSONObject(
			"attachments");

		for (String name : attachmentsJSONObject.keySet()) {
			TestrayAttachment testrayAttachment =
				TestrayFactory.newTestrayAttachment(
					this, name, attachmentsJSONObject.getString(name));

			testrayAttachments.put(
				testrayAttachment.getName(), testrayAttachment);
		}
	}

	private URL _getTopLevelBuildURL() {
		JSONObject jsonObject = getJSONObject();

		JSONObject attachmentsJSONObject = jsonObject.getJSONObject(
			"attachments");

		for (String key : attachmentsJSONObject.keySet()) {
			Matcher testrayAttachmentMatcher =
				_testrayAttachmentPattern.matcher(
					attachmentsJSONObject.getString(key));

			if (!testrayAttachmentMatcher.find()) {
				continue;
			}

			try {
				return new URL(
					JenkinsResultsParserUtil.combine(
						"http://",
						testrayAttachmentMatcher.group(
							"topLevelMasterHostname"),
						"/job/",
						testrayAttachmentMatcher.group("topLevelJobName"), "/",
						testrayAttachmentMatcher.group("topLevelBuildNumber")));
			}
			catch (MalformedURLException malformedURLException) {
			}
		}

		return null;
	}

	private static final Pattern _testrayAttachmentPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(?<startYearMonth>\\d{4}-\\d{2})/",
			"(?<topLevelMasterHostname>test-\\d+-\\d+)/",
			"(?<topLevelJobName>[^/]+)/(?<topLevelBuildNumber>\\d+)/.*"));

	private String _pullRequestAuthor;
	private TestrayCase _testrayCase;

}