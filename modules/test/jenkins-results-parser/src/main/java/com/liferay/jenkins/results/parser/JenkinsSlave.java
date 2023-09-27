/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsSlave implements JenkinsNode<JenkinsSlave> {

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

		_jenkinsMaster = JenkinsMaster.getInstance(jenkinsMasterName);

		if (hostname.equals(jenkinsMasterName)) {
			_name = "master";
		}
		else {
			_name = hostname;
		}

		JSONObject jenkinsSlaveJSONObject = JenkinsAPIUtil.getAPIJSONObject(
			getComputerURL(), "displayName,idle,offline");

		update(jenkinsSlaveJSONObject);
	}

	@Override
	public int compareTo(JenkinsSlave jenkinsSlave) {
		return _name.compareTo(jenkinsSlave.getName());
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof JenkinsSlave) {
			JenkinsSlave jenkinsSlave = (JenkinsSlave)object;

			if (compareTo(jenkinsSlave) == 0) {
				return true;
			}

			return false;
		}

		return super.equals(object);
	}

	public String getComputerURL() {
		String name = getName();

		if (name.equals("master")) {
			name = "(" + name + ")";
		}

		return JenkinsResultsParserUtil.combine(
			_jenkinsMaster.getURL(), "/computer/", name);
	}

	public Build getCurrentBuild() {
		JSONObject jsonObject = JenkinsAPIUtil.getAPIJSONObject(
			getComputerURL(), "executors[currentExecutable[url]]");

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

	@Override
	public JenkinsCohort getJenkinsCohort() {
		return _jenkinsMaster.getJenkinsCohort();
	}

	@Override
	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		String hashCodeString = _jenkinsMaster.getName() + "_" + _name;

		return hashCodeString.hashCode();
	}

	public boolean isIdle() {
		return _idle;
	}

	public boolean isOffline() {
		return _offline;
	}

	public boolean isReachable() {
		return JenkinsResultsParserUtil.isServerPortReachable(getName(), 22);
	}

	public void takeSlavesOffline(String offlineReason) {
		_setSlaveStatus(offlineReason, true);
	}

	public void takeSlavesOnline(String offlineReason) {
		_setSlaveStatus(offlineReason, false);
	}

	@Override
	public String toString() {
		return getName();
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

			System.out.println(
				JenkinsResultsParserUtil.executeJenkinsScript(
					_jenkinsMaster.getName(), script));
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