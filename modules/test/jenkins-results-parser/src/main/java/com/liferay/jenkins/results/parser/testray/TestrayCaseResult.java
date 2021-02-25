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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestrayCaseResult {

	public TestrayCaseResult(TestrayBuild testrayBuild, JSONObject jsonObject) {
		_testrayBuild = testrayBuild;
		_jsonObject = jsonObject;
	}

	public Map<String, String> getAttachments() {
		Map<String, String> attachments = new HashMap<>();

		if (_jsonObject.optJSONObject("attachments") != null) {
			JSONObject attachmentsJSONObject = _jsonObject.optJSONObject(
				"attachments");

			for (String key : attachmentsJSONObject.keySet()) {
				attachments.put(key, attachmentsJSONObject.getString(key));
			}
		}

		return attachments;
	}

	public String getCaseID() {
		return _jsonObject.optString("testrayCaseId");
	}

	public String getErrors() {
		return _jsonObject.optString("errors");
	}

	public String getID() {
		return _jsonObject.optString("testrayCaseResultId");
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return _jsonObject.optString("testrayCaseName");
	}

	public String getStatus() {
		int statusID = _jsonObject.optInt("status");

		Status status = Status.get(statusID);

		return status.getName();
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public String[] getWarnings() {
		JSONArray jsonArray = _jsonObject.optJSONArray("warnings");

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

		FAILED(3, "Failed"), PASSED(2, "Passed");

		public static Status get(Integer id) {
			return _statuses.get(id);
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

	private final JSONObject _jsonObject;
	private final TestrayBuild _testrayBuild;

}