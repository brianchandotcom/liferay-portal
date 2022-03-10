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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestrayCaseResult {

	public TestrayCaseResult(TestrayBuild testrayBuild, JSONObject jsonObject) {
		_testrayBuild = testrayBuild;
		this.jsonObject = jsonObject;
	}

	public TestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		_testrayBuild = testrayBuild;
		_topLevelBuild = topLevelBuild;
		jsonObject = new JSONObject();
	}

	public TestrayAttachment getBuildResultTestrayAttachment() {
		_initTestrayAttachments();

		return _testrayAttachments.get("Build Result (Top Level)");
	}

	public String getCaseID() {
		return jsonObject.optString("testrayCaseId");
	}

	public String getComponentName() {
		return jsonObject.getString("testrayComponentName");
	}

	public String getErrors() {
		return jsonObject.optString("errors");
	}

	public String getID() {
		return jsonObject.optString("testrayCaseResultId");
	}

	public JSONObject getJSONObject() {
		return jsonObject;
	}

	public String getName() {
		return jsonObject.optString("testrayCaseName");
	}

	public int getPriority() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getPriority();
	}

	public Status getStatus() {
		int statusID = jsonObject.optInt("status");

		return Status.get(statusID);
	}

	public String getSubcomponentNames() {
		return "";
	}

	public String getTeamName() {
		return jsonObject.getString("testrayTeamName");
	}

	public List<TestrayAttachment> getTestrayAttachments() {
		_initTestrayAttachments();

		return new ArrayList<>(_testrayAttachments.values());
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public TestrayCase getTestrayCase() {
		if (_testrayCase != null) {
			return _testrayCase;
		}

		TestrayServer testrayServer = getTestrayServer();

		String testrayCaseURL = JenkinsResultsParserUtil.combine(
			String.valueOf(testrayServer.getURL()), "/home/-/testray/cases/",
			getCaseID(), ".json");

		try {
			_testrayCase = new TestrayCase(
				getTestrayProject(),
				JenkinsResultsParserUtil.toJSONObject(testrayCaseURL));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayCase;
	}

	public TestrayProject getTestrayProject() {
		return _testrayBuild.getTestrayProject();
	}

	public TestrayServer getTestrayServer() {
		return _testrayBuild.getTestrayServer();
	}

	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	public String getType() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getType();
	}

	public URL getURL() {
		TestrayServer testrayServer = getTestrayServer();

		try {
			return new URL(
				testrayServer.getURL(),
				JenkinsResultsParserUtil.combine(
					"home/-/testray/case_results/", getID()));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public String[] getWarnings() {
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

	public static enum Status {

		BLOCKED(4, "blocked"), DID_NOT_RUN(6, "dnr"), FAILED(3, "failed"),
		IN_PROGRESS(1, "in-progress"), PASSED(2, "passed"),
		TEST_FIX(7, "test-fix"), UNTESTED(1, "untested");

		public static Status get(Integer id) {
			return _statuses.get(id);
		}

		public static List<Status> getFailedStatuses() {
			return Arrays.asList(
				BLOCKED, DID_NOT_RUN, FAILED, IN_PROGRESS, TEST_FIX, UNTESTED);
		}

		public Integer getID() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		private Status(Integer id, String name) {
			_id = id;
			_name = name;
		}

		private static Map<Integer, Status> _statuses = new HashMap<>();

		static {
			for (Status status : values()) {
				_statuses.put(status.getID(), status);
			}
		}

		private final Integer _id;
		private final String _name;

	}

	protected final JSONObject jsonObject;

	private synchronized void _initTestrayAttachments() {
		if (_testrayAttachments != null) {
			return;
		}

		_testrayAttachments = new TreeMap<>();

		JSONObject attachmentsJSONObject = jsonObject.optJSONObject(
			"attachments");

		for (String name : attachmentsJSONObject.keySet()) {
			TestrayAttachment testrayAttachment =
				TestrayFactory.newTestrayAttachment(
					this, name, attachmentsJSONObject.getString(name));

			_testrayAttachments.put(
				testrayAttachment.getName(), testrayAttachment);
		}
	}

	private Map<String, TestrayAttachment> _testrayAttachments;
	private final TestrayBuild _testrayBuild;
	private TestrayCase _testrayCase;
	private TopLevelBuild _topLevelBuild;

}