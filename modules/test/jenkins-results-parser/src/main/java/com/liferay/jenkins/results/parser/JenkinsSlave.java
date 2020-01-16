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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsSlave {

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