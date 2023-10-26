/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.jethr0.Jethr0Client;
import com.liferay.jenkins.results.parser.jethr0.Jethr0ClientFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Jethr0BuildUpdater
	extends BaseBuildUpdater implements MessageListener {

	public String getJenkinsBuildID() {
		return _jenkinsBuildID;
	}

	public String getMessageSelector() {
		return JenkinsResultsParserUtil.combine(
			"(jenkinsBuildId = '", String.valueOf(_jenkinsBuildID),
			"') AND (jethr0JobId = '", String.valueOf(_jethr0JobId), "')");
	}

	@Override
	public void invoke() {
		Build build = getBuild();

		_invoke(build.getMinimumSlaveRAM(), build.getMaximumSlavesPerHost());
	}

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage)message;

		try {
			JSONObject jsonObject = new JSONObject(textMessage.getText());

			String status = jsonObject.getString("status");

			if (Objects.equals(status, "completed")) {
				_processCompletedBuild(jsonObject);
			}
			else if (Objects.equals(status, "queued")) {
				_processQueuedBuild(jsonObject);
			}
			else if (Objects.equals(status, "running")) {
				_processRunningBuild(jsonObject);
			}
		}
		catch (JMSException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void reinvoke() {
		Build build = getBuild();

		_invoke(24, build.getMaximumSlavesPerHost());
	}

	protected Jethr0BuildUpdater(Build build, long jethr0JobId) {
		super(build);

		_jethr0JobId = jethr0JobId;

		_jenkinsBuildID = jethr0JobId + "__" + build.getBuildName();

		try {
			_jethr0Client = Jethr0ClientFactory.newJethr0Client(
				build.getJenkinsMaster());
		}
		catch (Exception exception) {
			exception.printStackTrace();

			throw new RuntimeException(exception);
		}
	}

	@Override
	protected boolean isBuildCompleted() {
		if (Objects.equals(_jethr0Status, "completed")) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBuildFailing() {
		if (!Objects.equals(_jethr0Result, "passed")) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBuildQueued() {
		if (Objects.equals(_jethr0Status, "queued")) {
			return true;
		}

		return false;
	}

	protected boolean isBuildRunning() {
		if (Objects.equals(_jethr0Status, "running")) {
			return true;
		}

		return false;
	}

	@Override
	protected void runCompleted() {
		super.runCompleted();

		_jethr0Client.unsubscribe(this);
	}

	@Override
	protected void runStarting() {
		_jethr0Client.subscribe(this);

		_jethr0Result = null;
		_jethr0Status = null;

		super.runStarting();
	}

	private JenkinsMaster _getJenkinsMaster(String buildURL) {
		if (JenkinsResultsParserUtil.isURL(buildURL)) {
			return null;
		}

		Matcher matcher = _jenkinsBuildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			return null;
		}

		return JenkinsMaster.getInstance(matcher.group("masterHostname"));
	}

	private void _invoke(int maximumSlavesPerHost, int minimumSlaveRAM) {
		Build build = getBuild();

		Map<String, String> buildParameters = new HashMap<>(
			build.getParameters());

		buildParameters.put("JENKINS_BUILD_ID", getJenkinsBuildID());
		buildParameters.put(
			"MAX_NODE_COUNT", String.valueOf(maximumSlavesPerHost));
		buildParameters.put("MIN_NODE_RAM", String.valueOf(minimumSlaveRAM));

		_jethr0Client.createBuild(
			build.getJobName(), buildParameters, _jethr0JobId,
			build.getBuildName());

		build.addInvocation(new Build.Invocation(build));
	}

	private void _processCompletedBuild(JSONObject jsonObject) {
		String jenkinsBuildURL = jsonObject.getString("jenkinsBuildURL");

		JenkinsMaster jenkinsMaster = _getJenkinsMaster(jenkinsBuildURL);

		Build build = getBuild();

		Build.Invocation buildInvocation = build.getCurrentInvocation();

		buildInvocation.setBuildURL(jenkinsBuildURL);
		buildInvocation.setJenkinsMaster(jenkinsMaster);

		build.setBuildURL(jenkinsBuildURL);
		build.setJenkinsMaster(jenkinsMaster);

		_jethr0Result = jsonObject.getString("result");
		_jethr0Status = "completed";
	}

	private void _processQueuedBuild(JSONObject jsonObject) {
		Build build = getBuild();

		Build.Invocation buildInvocation = build.getCurrentInvocation();

		buildInvocation.setBuildURL(jsonObject.getString("jethr0BuildURL"));

		_jethr0Status = "queued";
	}

	private void _processRunningBuild(JSONObject jsonObject) {
		Build build = getBuild();

		Build.Invocation buildInvocation = build.getCurrentInvocation();

		String jenkinsBuildURL = jsonObject.getString("jenkinsBuildURL");

		JenkinsMaster jenkinsMaster = _getJenkinsMaster(jenkinsBuildURL);

		buildInvocation.setBuildURL(jenkinsBuildURL);

		buildInvocation.setJenkinsMaster(jenkinsMaster);

		build.setBuildURL(jenkinsBuildURL);

		build.setJenkinsMaster(jenkinsMaster);

		_jethr0Status = "running";
	}

	private static final Pattern _jenkinsBuildURLPattern = Pattern.compile(
		"https?://(?<masterHostname>test-\\d+-\\d+)(.liferay.com)?/.+");

	private final String _jenkinsBuildID;
	private final Jethr0Client _jethr0Client;
	private final long _jethr0JobId;
	private String _jethr0Result;
	private String _jethr0Status;

}