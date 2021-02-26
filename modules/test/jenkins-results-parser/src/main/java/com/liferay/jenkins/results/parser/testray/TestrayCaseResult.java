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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Attachment> getAttachments() {
		List<Attachment> attachments = new ArrayList<>();

		if (jsonObject.optJSONObject("attachments") != null) {
			JSONObject attachmentsJSONObject = jsonObject.optJSONObject(
				"attachments");

			for (String key : attachmentsJSONObject.keySet()) {
				Attachment attachment = new Attachment(
					this, key, attachmentsJSONObject.getString(key));

				attachments.add(attachment);
			}
		}

		return attachments;
	}

	public String getCaseID() {
		return jsonObject.optString("testrayCaseId");
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

	public Status getStatus() {
		int statusID = jsonObject.optInt("status");

		return Status.get(statusID);
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public TestrayServer getTestrayServer() {
		return _testrayBuild.getTestrayServer();
	}

	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
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

	public static class Attachment {

		public Attachment(
			TestrayCaseResult testrayCaseResult, String name, String value) {

			_testrayCaseResult = testrayCaseResult;
			_name = name;
			_value = value;
		}

		public Attachment(
			TestrayCaseResult testrayCaseResult,
			TestrayS3Object testrayS3Object, String name) {

			_testrayCaseResult = testrayCaseResult;
			_testrayS3Object = testrayS3Object;
			_name = name;

			_value = testrayS3Object.getKey();
		}

		public String getName() {
			return _name;
		}

		public URL getURL() {
			if (_testrayS3Object != null) {
				return _testrayS3Object.getURL();
			}

			TestrayServer testrayServer = _testrayCaseResult.getTestrayServer();

			try {
				return new URL(
					testrayServer.getURL(),
					JenkinsResultsParserUtil.combine(
						"/reports/production/logs/", getValue()));
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		public String getValue() {
			return _value;
		}

		private final String _name;
		private final TestrayCaseResult _testrayCaseResult;
		private TestrayS3Object _testrayS3Object;
		private final String _value;

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

	protected final JSONObject jsonObject;

	private final TestrayBuild _testrayBuild;
	private TopLevelBuild _topLevelBuild;

}