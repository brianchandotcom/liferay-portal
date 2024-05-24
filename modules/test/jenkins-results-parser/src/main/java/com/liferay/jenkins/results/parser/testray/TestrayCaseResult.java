/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestrayCaseResult {

	public static final String[] FIELD_NAMES = {
		"attachments", "caseToCaseResult", "componentToCaseResult",
		"dateCreated", "dateModified", "dueStatus{key name}", "errors", "id",
		"startDate"
	};

	public TestrayAttachment getBuildResultTestrayAttachment() {
		initTestrayAttachments();

		return testrayAttachments.get("Build Result (Top Level)");
	}

	public String getCaseID() {
		TestrayComponent testrayComponent = getTestrayComponent();

		if (testrayComponent == null) {
			return null;
		}

		return String.valueOf(testrayComponent.getID());
	}

	public String getComponentName() {
		TestrayComponent testrayComponent = getTestrayComponent();

		if (testrayComponent == null) {
			return null;
		}

		return testrayComponent.getName();
	}

	public String getErrors() {
		return _jsonObject.optString("errors");
	}

	public URL getHistoryURL() {
		try {
			return new URL(getURL() + "/history");
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public long getID() {
		return _jsonObject.optLong("id");
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		TestrayCase testrayCase = getTestrayCase();

		if (testrayCase == null) {
			return null;
		}

		return testrayCase.getName();
	}

	public int getPriority() {
		TestrayCase testrayCase = getTestrayCase();

		if (testrayCase == null) {
			return 0;
		}

		return testrayCase.getPriority();
	}

	public Status getStatus() {
		JSONObject dueStatusJSONObject = _jsonObject.getJSONObject("dueStatus");

		return Status.valueOf(dueStatusJSONObject.getString("key"));
	}

	public String getSubcomponentNames() {
		return "";
	}

	public String getTeamName() {
		if (_testrayComponent == null) {
			return null;
		}

		TestrayTeam testrayTeam = _testrayComponent.getTestrayTeam();

		return testrayTeam.getName();
	}

	public List<TestrayAttachment> getTestrayAttachments() {
		initTestrayAttachments();

		return new ArrayList<>(testrayAttachments.values());
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public TestrayCase getTestrayCase() {
		return _testrayCase;
	}

	public TestrayComponent getTestrayComponent() {
		return _testrayComponent;
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

		if (testrayCase == null) {
			return null;
		}

		return testrayCase.getType();
	}

	public URL getURL() {
		TestrayBuild testrayBuild = getTestrayBuild();

		try {
			return new URL(testrayBuild.getURL() + "/case-result/" + getID());
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public String[] getWarnings() {
		return null;
	}

	public static enum Status {

		BLOCKED(4, "blocked"), DIDNOTRUN(6, "dnr"), FAILED(3, "failed"),
		INPROGRESS(1, "in-progress"), PASSED(2, "passed"),
		TESTFIX(7, "test-fix"), UNTESTED(1, "untested");

		public static Status get(Integer id) {
			return _statuses.get(id);
		}

		public static List<Status> getFailedStatuses() {
			return Arrays.asList(
				BLOCKED, DIDNOTRUN, FAILED, INPROGRESS, TESTFIX, UNTESTED);
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

	protected TestrayCaseResult(
		TestrayBuild testrayBuild, JSONObject jsonObject) {

		_testrayBuild = testrayBuild;

		_jsonObject = jsonObject;

		JSONObject componentJSONObject = _jsonObject.optJSONObject(
			"componentToCaseResult");

		if (componentJSONObject != null) {
			TestrayProject testrayProject = testrayBuild.getTestrayProject();

			_testrayComponent = testrayProject.getTestrayComponentByID(
				componentJSONObject.getLong("id"));
		}

		JSONObject caseJSONObject = _jsonObject.optJSONObject(
			"caseToCaseResult");

		if (caseJSONObject != null) {
			_testrayCase = TestrayFactory.newTestrayCase(
				testrayBuild.getTestrayProject(), caseJSONObject);
		}
	}

	protected TestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		_testrayBuild = testrayBuild;
		_topLevelBuild = topLevelBuild;

		_jsonObject = new JSONObject();
	}

	protected synchronized void initTestrayAttachments() {
		if (testrayAttachments != null) {
			return;
		}

		testrayAttachments = new TreeMap<>();

		String attachments = _jsonObject.getString("attachments");

		JSONArray attachmentsJSONArray;

		try {
			attachmentsJSONArray = new JSONArray(attachments);
		}
		catch (JSONException jsonException) {
			return;
		}

		for (int i = 0; i < attachmentsJSONArray.length(); i++) {
			JSONObject attachmentJSONObject =
				attachmentsJSONArray.getJSONObject(i);

			URL url;

			try {
				url = new URL(attachmentJSONObject.getString("url"));
			}
			catch (MalformedURLException malformedURLException) {
				url = null;
			}

			TestrayAttachment testrayAttachment =
				TestrayFactory.newTestrayAttachment(
					this, attachmentJSONObject.getString("name"),
					attachmentJSONObject.getString("value"), url);

			testrayAttachments.put(
				testrayAttachment.getName(), testrayAttachment);
		}
	}

	protected Map<String, TestrayAttachment> testrayAttachments;

	private final JSONObject _jsonObject;
	private final TestrayBuild _testrayBuild;
	private TestrayCase _testrayCase;
	private TestrayComponent _testrayComponent;
	private TopLevelBuild _topLevelBuild;

}