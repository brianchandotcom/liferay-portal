/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestrayCaseResult {

	public TestrayAttachment getBuildResultTestrayAttachment() {
		return null;
	}

	public String getCaseID() {
		return null;
	}

	public String getComponentName() {
		return null;
	}

	public String getErrors() {
		return null;
	}

	public URL getHistoryURL() {
		return null;
	}

	public long getID() {
		return _jsonObject.optLong("id");
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return null;
	}

	public int getPriority() {
		TestrayCase testrayCase = getTestrayCase();

		return testrayCase.getPriority();
	}

	public Status getStatus() {
		return null;
	}

	public String getSubcomponentNames() {
		return "";
	}

	public String getTeamName() {
		return null;
	}

	public List<TestrayAttachment> getTestrayAttachments() {
		return null;
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public TestrayCase getTestrayCase() {
		return null;
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
				"home/-/testray/case_results/" + getID());
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public String[] getWarnings() {
		return null;
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

	protected TestrayCaseResult(
		TestrayBuild testrayBuild, JSONObject jsonObject) {

		_testrayBuild = testrayBuild;
		_jsonObject = jsonObject;
	}

	protected TestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild) {

		_testrayBuild = testrayBuild;
		_topLevelBuild = topLevelBuild;

		_jsonObject = new JSONObject();
	}

	private final JSONObject _jsonObject;
	private final TestrayBuild _testrayBuild;
	private TopLevelBuild _topLevelBuild;

}