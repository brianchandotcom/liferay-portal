/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class Testray1TestrayCaseResult extends TestrayCaseResult {

	public TestrayAttachment getBuildResultTestrayAttachment() {
		_initTestrayAttachments();

		return _testrayAttachments.get("Build Result (Top Level)");
	}

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
	public URL getHistoryURL() {
		try {
			return new URL(getURL() + "/history");
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
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
	public List<TestrayAttachment> getTestrayAttachments() {
		_initTestrayAttachments();

		return new ArrayList<>(_testrayAttachments.values());
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

	public String getType() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getType();
	}

	public URL getURL() {
		return null;
	}

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

	private synchronized void _initTestrayAttachments() {
		if (_testrayAttachments != null) {
			return;
		}

		_testrayAttachments = new TreeMap<>();

		JSONObject jsonObject = getJSONObject();

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
	private TestrayCase _testrayCase;

}