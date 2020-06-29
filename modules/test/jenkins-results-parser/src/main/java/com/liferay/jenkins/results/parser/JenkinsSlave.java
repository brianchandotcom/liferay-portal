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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsSlave {

	public JenkinsSlave() {
		this(
			JenkinsResultsParserUtil.getHostName(
				JenkinsResultsParserUtil.getHostIPAddress()));
	}

	public JenkinsSlave(String hostname) {
		hostname = hostname.replaceAll("([^\\.]+).*", "$1");

		String jenkinsMasterName =
			JenkinsResultsParserUtil.getJenkinsMasterName(hostname);

		if (jenkinsMasterName == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find Jenkins master name for Jenkins slave ",
					"hostname ", hostname));
		}

		_jenkinsMaster = new JenkinsMaster(jenkinsMasterName);

		String jenkinsSlaveJSONObjectURL = JenkinsResultsParserUtil.getLocalURL(
			JenkinsResultsParserUtil.combine(
				_jenkinsMaster.getURL(), "/computer/", hostname,
				"/api/json?tree=displayName,", "idle,offline"));

		JSONObject jenkinsSlaveJSONObject = null;

		try {
			jenkinsSlaveJSONObject = JenkinsResultsParserUtil.toJSONObject(
				jenkinsSlaveJSONObjectURL, false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to retrieve Jenkins slave node JSON object from " +
					jenkinsSlaveJSONObjectURL,
				ioException);
		}

		_name = jenkinsSlaveJSONObject.getString("displayName");

		update(jenkinsSlaveJSONObject);
	}

	public Build getCurrentBuild() {
		JSONObject jsonObject = null;

		String jsonObjectURL = JenkinsResultsParserUtil.combine(
			_jenkinsMaster.getURL(), "computer/", getName(),
			"/api/json?tree=executors[currentExecutable[url]]");

		try {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				jsonObjectURL, false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to determine current build", ioException);
		}

		JSONArray jsonArray = jsonObject.getJSONArray("executors");

		jsonObject = jsonArray.getJSONObject(0);

		JSONObject currentExecutableJSONObject = jsonObject.getJSONObject(
			"currentExecutable");

		String buildURL = currentExecutableJSONObject.optString("url");

		if (buildURL == null) {
			return null;
		}

		return BuildFactory.newBuild(buildURL, null);
	}

	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	public String getName() {
		return _name;
	}

	public boolean isIdle() {
		return _idle;
	}

	public boolean isOffline() {
		return _offline;
	}

	public void takeSlavesOffline(String offlineReason) {
		_setSlaveStatus(offlineReason, true);
	}

	public void takeSlavesOnline(String offlineReason) {
		_setSlaveStatus(offlineReason, false);
	}

	public void update() {
		_jenkinsMaster.update();
	}

	protected JenkinsSlave(
		JenkinsMaster jenkinsMaster, JSONObject jenkinsSlaveJSONObject) {

		_jenkinsMaster = jenkinsMaster;
		_name = jenkinsSlaveJSONObject.getString("displayName");

		update(jenkinsSlaveJSONObject);
	}

	protected void update(JSONObject jenkinsSlaveJSONObject) {
		_idle = jenkinsSlaveJSONObject.getBoolean("idle");
		_offline = jenkinsSlaveJSONObject.getBoolean("offline");
	}

	private void _setSlaveStatus(String offlineReason, boolean offlineStatus) {
		try {
			Class<?> clazz = JenkinsSlave.class;

			String script = JenkinsResultsParserUtil.readInputStream(
				clazz.getResourceAsStream(
					"dependencies/set-slave-status.groovy"));

			script = script.replace("${slaves}", _name);
			script = script.replace(
				"${offline.reason}",
				offlineReason.replaceAll("\n", "<br />\\\\n"));
			script = script.replace(
				"${offline.status}", String.valueOf(offlineStatus));

			JenkinsResultsParserUtil.executeJenkinsScript(
				_jenkinsMaster.getName(), script);
		}
		catch (IOException ioException) {
			System.out.println("Unable to set the status for slaves: " + _name);

			ioException.printStackTrace();
		}
	}

	private boolean _idle;
	private final JenkinsMaster _jenkinsMaster;
	private final String _name;
	private boolean _offline;

}